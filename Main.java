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
        System.out.println("  - Enter move (algebraic): e4, Nf3, Bxe5, O-O");
        System.out.println("  - Enter move (coordinate): e2 e4, g1 f3");
        System.out.println("  - Undo: undo");
        System.out.println("  - Show moves: moves");
        System.out.println("  - Save game: save <filename>");
        System.out.println("  - Main menu: menu");
        System.out.println("  - Help: help");
        System.out.println();
        
        // Game loop
        boolean gameRunning = true;
        while (gameRunning) {
            // Check for checkmate or stalemate
            try {
                if (engine.isCheckmate()) {
                    System.out.println("\n" + engine.getCurrentTurn().toUpperCase() + "'s turn:");
                    engine.getBoard().printBoard();
                    String winner = engine.getCurrentTurn().equals("white") ? "BLACK" : "WHITE";
                    System.out.println("\n=================================");
                    System.out.println("         CHECKMATE!              ");
                    System.out.println("      " + winner + " WINS!           ");
                    System.out.println("=================================");
                    gameRunning = false;
                    continue;
                }
                
                if (engine.isStalemate()) {
                    System.out.println("\n" + engine.getCurrentTurn().toUpperCase() + "'s turn:");
                    engine.getBoard().printBoard();
                    System.out.println("\n=================================");
                    System.out.println("         STALEMATE!              ");
                    System.out.println("        GAME IS A DRAW           ");
                    System.out.println("=================================");
                    gameRunning = false;
                    continue;
                }
            } catch (InvalidSquareException | GameStateException e) {
                System.out.println("Internal error checking game state: " + e.getMessage());
            }
            
            // Display current board
            System.out.println("\n" + engine.getCurrentTurn().toUpperCase() + "'s turn:");
            engine.getBoard().printBoard();
            
            // Check if current player is in check
            try {
                if (engine.isInCheck(engine.getCurrentTurn())) {
                    System.out.println("\n*** CHECK! ***");
                }
            } catch (InvalidSquareException | GameStateException e) {
                System.out.println("Internal error checking for check: " + e.getMessage());
            }
            
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
                    } catch (InvalidSquareException | GameStateException e) {
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
                    System.out.println("  - Enter move (algebraic): e4, Nf3, Bxe5, O-O, e8=Q");
                    System.out.println("  - Enter move (coordinate): e2 e4, g1 f3");
                    System.out.println("  - Undo: undo");
                    System.out.println("  - Show moves: moves");
                    System.out.println("  - Save game: save <filename>");
                    System.out.println("  - Main menu: menu");
                    System.out.println("  - Help: help");
                    break;
                    
                default:
                    // Try to parse as algebraic notation first (single token)
                    if (tokens.length == 1) {
                        String algebraic = tokens[0];
                        
                        try {
                            AlgebraicNotationParser parser = new AlgebraicNotationParser(engine);
                            Move selectedMove = parser.parseMove(algebraic);
                            
                            if (selectedMove != null) {
                                // Handle pawn promotion if needed
                                if (selectedMove.getIsPawnPromotion() && !algebraic.contains("=")) {
                                    // Promotion not specified in notation, ask user
                                    System.out.print("Promote to (Q/R/B/N): ");
                                    String promotion = scanner.nextLine().trim().toUpperCase();
                                    
                                    // Find the right promotion move
                                    List<Move> legalMoves = engine.getAllLegalMoves();
                                    Move promotionMove = null;
                                    
                                    for (Move promMove : legalMoves) {
                                        if (promMove.getStartSquare().equals(selectedMove.getStartSquare()) && 
                                            promMove.getEndSquare().equals(selectedMove.getEndSquare()) && 
                                            promMove.getIsPawnPromotion()) {
                                            String pieceType = promMove.getPawnPromotionPiece().getType();
                                            if ((promotion.equals("Q") && pieceType.equals("Queen")) ||
                                                (promotion.equals("R") && pieceType.equals("Rook")) ||
                                                (promotion.equals("B") && pieceType.equals("Bishop")) ||
                                                (promotion.equals("N") && pieceType.equals("Knight"))) {
                                                promotionMove = promMove;
                                                break;
                                            }
                                        }
                                    }
                                    
                                    if (promotionMove != null) {
                                        selectedMove = promotionMove;
                                    }
                                }
                                
                                engine.makeMove(selectedMove);
                                
                                // Generate proper algebraic notation with check/checkmate symbols
                                try {
                                    String moveNotation = parser.toAlgebraicNotation(selectedMove);
                                    System.out.println("Move made: " + moveNotation);
                                } catch (Exception e) {
                                    System.out.println("Move made: " + algebraic);
                                }
                                
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
                                if (selectedMove.getIsPawnPromotion()) {
                                    System.out.println("Pawn promoted to " + selectedMove.getPawnPromotionPiece().getType() + "!");
                                }
                            } else {
                                System.out.println("Illegal move! That move is not allowed.");
                                System.out.println("Type 'moves' to see all legal moves.");
                            }
                            
                        } catch (PGNParseException e) {
                            System.out.println("Error parsing move: " + e.getMessage());
                        } catch (InvalidMoveException e) {
                            System.out.println("Invalid move: " + e.getMessage());
                        } catch (InvalidSquareException | GameStateException e) {
                            System.out.println("Internal error: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    }
                    // Try to parse as coordinate notation (two tokens: e.g., "e2 e4")
                    else if (tokens.length == 2) {
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
                                
                                // Generate proper algebraic notation with check/checkmate symbols
                                try {
                                    AlgebraicNotationParser parser = new AlgebraicNotationParser(engine);
                                    String moveNotation = parser.toAlgebraicNotation(selectedMove);
                                    System.out.println("Move made: " + fromSquare + " -> " + toSquare + " (" + moveNotation + ")");
                                } catch (Exception e) {
                                    System.out.println("Move made: " + fromSquare + " -> " + toSquare);
                                }
                                
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
                                if (selectedMove.getIsPawnPromotion()) {
                                    System.out.println("Pawn promoted to " + selectedMove.getPawnPromotionPiece().getType() + "!");
                                }
                            } else {
                                System.out.println("Illegal move! That move is not allowed.");
                                System.out.println("Type 'moves' to see all legal moves.");
                            }
                            
                        } catch (InvalidMoveException e) {
                            System.out.println("Invalid move: " + e.getMessage());
                        } catch (InvalidSquareException | GameStateException e) {
                            System.out.println("Invalid square: " + e.getMessage());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Invalid command. Type 'help' for commands.");
                        System.out.println("To move: use algebraic notation (e.g., 'e4', 'Nf3') or coordinate notation (e.g., 'e2 e4')");
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
