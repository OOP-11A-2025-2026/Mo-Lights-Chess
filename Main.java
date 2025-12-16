import java.util.List;
import java.util.Scanner;
import exceptions.*;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("   Welcome to Mo-Lights Chess!  ");
        System.out.println("=================================");
        
        // Main menu loop
        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Quit");
            System.out.print("\nEnter your choice (1-3): ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    // Start new game
                    ChessEngine engine = new ChessEngine();
                    playGame(engine);
                    break;
                    
                case "2":
                    // Load game
                    System.out.print("Enter filename to load: ");
                    String filename = scanner.nextLine().trim();
                    if (!filename.endsWith(".pgn")) {
                        filename += ".pgn";
                    }
                    try {
                        ChessEngine loadedEngine = new ChessEngine();
                        PGNReader reader = new PGNReader(loadedEngine);
                        reader.readPGN(filename);
                        System.out.println("Game loaded successfully!");
                        System.out.println("Moves loaded: " + loadedEngine.getMoveLog().size());
                        playGame(loadedEngine);
                    } catch (ChessFileException e) {
                        System.out.println("File error: " + e.getMessage());
                    } catch (PGNParseException e) {
                        System.out.println("Parse error: " + e.getMessage());
                    }
                    break;
                    
                case "3":
                    System.out.println("\nThanks for playing Mo-Lights Chess!");
                    running = false;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                    break;
            }
        }
        
        scanner.close();
    }
    
    private static void playGame(ChessEngine engine) {
        System.out.println("\n=================================");
        System.out.println("         GAME STARTED            ");
        System.out.println("=================================");
        System.out.println("\nCommands:");
        System.out.println("  - Enter move: e2 e4 (from square to square)");
        System.out.println("  - Undo: undo");
        System.out.println("  - Show moves: moves");
        System.out.println("  - Save game: save <filename>");
        System.out.println("  - Main menu: menu");
        System.out.println("  - Help: help");
        System.out.println();
        
        // Game loop
        boolean gameRunning = true;
        while (gameRunning) {
            // Display current board
            System.out.println("\n" + engine.getCurrentTurn().toUpperCase() + "'s turn:");
            engine.getBoard().printBoard();
            
            // Get user input
            System.out.print("\nEnter command: ");
            String input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                continue;
            }
            
            // Parse command
            String[] tokens = input.split("\\s+");
            String command = tokens[0].toLowerCase();
            
            switch (command) {
                case "menu":
                    System.out.println("Returning to main menu...");
                    gameRunning = false;
                    break;
                    
                case "quit":
                case "exit":
                    System.out.println("Returning to main menu...");
                    gameRunning = false;
                    break;
                    
                case "undo":
                    try {
                        engine.undoMove();
                        System.out.println("Move undone!");
                    } catch (GameStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (InvalidSquareException e) {
                        System.out.println("Internal error: " + e.getMessage());
                    }
                    break;
                    
                case "moves":
                    try {
                        List<Move> availableMoves = engine.getAllLegalMoves();
                        System.out.println("\nLegal moves for " + engine.getCurrentTurn() + ":");
                        for (Move move : availableMoves) {
                            System.out.print(move.getStartSquare().getAlgebraicNotation() + "->" + move.getEndSquare().getAlgebraicNotation() + " ");
                        }
                        System.out.println("\n(Total: " + availableMoves.size() + " moves)");
                    } catch (InvalidSquareException e) {
                        System.out.println("Internal error: " + e.getMessage());
                    }
                    break;
                    
                case "save":
                    if (tokens.length < 2) {
                        System.out.println("Usage: save <filename>");
                    } else {
                        try {
                            PGNWriter writer = new PGNWriter(engine);
                            String filename = tokens[1];
                            if (!filename.endsWith(".pgn")) {
                                filename += ".pgn";
                            }
                            writer.writePGN(filename, "Casual Game", "White Player", "Black Player", "*");
                            System.out.println("Game saved to " + filename);
                        } catch (ChessFileException e) {
                            System.out.println("Error saving game: " + e.getMessage());
                        }
                    }
                    break;
                    
                case "help":
                    System.out.println("\nCommands:");
                    System.out.println("  - Enter move: e2 e4 (from square to square)");
                    System.out.println("  - Undo: undo");
                    System.out.println("  - Show moves: moves");
                    System.out.println("  - Save game: save <filename>");
                    System.out.println("  - Main menu: menu");
                    System.out.println("  - Help: help");
                    break;
                    
                default:
                    // Try to parse as a move (e.g., "e2 e4")
                    if (tokens.length == 2) {
                        String fromSquare = tokens[0].toLowerCase();
                        String toSquare = tokens[1].toLowerCase();
                        
                        try {
                            // Validate square notation
                            if (!isValidSquare(fromSquare) || !isValidSquare(toSquare)) {
                                System.out.println("Invalid square notation. Use format like: e2 e4");
                                continue;
                            }
                            
                            Square start = engine.getBoard().getSquare(fromSquare);
                            Square end = engine.getBoard().getSquare(toSquare);
                            
                            // Check if there's a piece on the start square
                            if (!start.hasPiece()) {
                                System.out.println("No piece on " + fromSquare);
                                continue;
                            }
                            
                            // Check if it's the right color's turn
                            if (!start.getPiece().getColor().equals(engine.getCurrentTurn())) {
                                System.out.println("It's " + engine.getCurrentTurn() + "'s turn!");
                                continue;
                            }
                            
                            // Find the matching legal move
                            List<Move> legalMoves = engine.getAllLegalMoves();
                            Move selectedMove = null;
                            
                            for (Move move : legalMoves) {
                                if (move.getStartSquare().equals(start) && move.getEndSquare().equals(end)) {
                                    // If pawn promotion, ask which piece
                                    if (move.getIsPawnPromotion()) {
                                        System.out.print("Promote to (Q/R/B/N): ");
                                        String promotion = scanner.nextLine().trim().toUpperCase();
                                        
                                        // Find the promotion move
                                        for (Move promMove : legalMoves) {
                                            if (promMove.getStartSquare().equals(start) && 
                                                promMove.getEndSquare().equals(end) && 
                                                promMove.getIsPawnPromotion()) {
                                                String pieceType = promMove.getPawnPromotionPiece().getType();
                                                if ((promotion.equals("Q") && pieceType.equals("Queen")) ||
                                                    (promotion.equals("R") && pieceType.equals("Rook")) ||
                                                    (promotion.equals("B") && pieceType.equals("Bishop")) ||
                                                    (promotion.equals("N") && pieceType.equals("Knight"))) {
                                                    selectedMove = promMove;
                                                    break;
                                                }
                                            }
                                        }
                                        if (selectedMove == null) {
                                            selectedMove = move; // Default to queen
                                        }
                                    } else {
                                        selectedMove = move;
                                    }
                                    break;
                                }
                            }
                            
                            if (selectedMove != null) {
                                engine.makeMove(selectedMove);
                                System.out.println("Move made: " + fromSquare + " -> " + toSquare);
                                
                                // Check for special moves
                                if (selectedMove.getPieceCaptured() != null) {
                                    System.out.println("Captured " + selectedMove.getPieceCaptured().getType() + "!");
                                }
                                if (selectedMove.getKingSideCastle()) {
                                    System.out.println("Kingside castling!");
                                }
                                if (selectedMove.getQueenSideCastle()) {
                                    System.out.println("Queenside castling!");
                                }
                                if (selectedMove.getIsEnpassant()) {
                                    System.out.println("En passant!");
                                }
                            } else {
                                System.out.println("Illegal move! That move is not allowed.");
                                System.out.println("Type 'moves' to see all legal moves.");
                            }
                            
                        } catch (InvalidMoveException e) {
                            System.out.println("Invalid move: " + e.getMessage());
                        } catch (InvalidSquareException e) {
                            System.out.println("Invalid square: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid command. Type 'help' for commands.");
                    }
                    break;
            }
        }
    }
    
    // Helper method to validate square notation (e.g., "e2", "a8")
    private static boolean isValidSquare(String square) {
        if (square.length() != 2) return false;
        char file = square.charAt(0);
        char rank = square.charAt(1);
        return (file >= 'a' && file <= 'h') && (rank >= '1' && rank <= '8');
    }
}
