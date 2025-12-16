import java.util.ArrayList;
import java.util.List;
import pieces.*;
import exceptions.*;

/**
 * Parser for Standard Algebraic Notation (SAN) in chess.
 * Converts algebraic notation strings (e.g., "e4", "Nf3", "Bxe5", "O-O") 
 * into Move objects that can be executed by the ChessEngine.
 * Also generates SAN notation with check (+) and checkmate (#) symbols.
 */
public class AlgebraicNotationParser {

    private ChessEngine engine;

    public AlgebraicNotationParser(ChessEngine engine) {
        this.engine = engine;
    }

    /**
     * Generates Standard Algebraic Notation for a move with check/checkmate symbols.
     * This method should be called AFTER the move has been made on the board.
     * 
     * @param move The move to convert to SAN
     * @return SAN string with check (+) or checkmate (#) symbols if applicable
     */
    public String toAlgebraicNotation(Move move) throws InvalidSquareException, GameStateException {
        StringBuilder san = new StringBuilder();
        
        // Handle castling
        if (move.getKingSideCastle()) {
            san.append("O-O");
        } else if (move.getQueenSideCastle()) {
            san.append("O-O-O");
        } else {
            Piece piece = move.getPieceMoved();
            Square start = move.getStartSquare();
            Square end = move.getEndSquare();
            
            // For non-pawn pieces, add the piece letter
            if (!piece.getType().equals("Pawn")) {
                san.append(piece.getPieceLetter());
                
                // Add disambiguation if needed
                String disambiguation = getDisambiguation(move);
                san.append(disambiguation);
            } else if (move.getPieceCaptured() != null || move.getIsEnpassant()) {
                // For pawn captures, add the starting file
                char file = (char)('a' + start.getCol());
                san.append(file);
            }
            
            // Add capture symbol if applicable
            if (move.getPieceCaptured() != null || move.getIsEnpassant()) {
                san.append("x");
            }
            
            // Add destination square
            san.append(end.getAlgebraicNotation());
            
            // Add promotion
            if (move.getIsPawnPromotion()) {
                san.append("=").append(move.getPawnPromotionPiece().getPieceLetter());
            }
        }
        
        // Add check or checkmate symbol
        String opponentColor = engine.getCurrentTurn(); // After move, it's opponent's turn
        if (engine.isCheckmate()) {
            san.append("#");
        } else if (engine.isInCheck(opponentColor)) {
            san.append("+");
        }
        
        return san.toString();
    }

    /**
     * Determines if disambiguation is needed for a piece move.
     * Returns the disambiguation string (file, rank, or both).
     */
    private String getDisambiguation(Move move) throws InvalidSquareException, GameStateException {
        Piece piece = move.getPieceMoved();
        Square start = move.getStartSquare();
        Square end = move.getEndSquare();
        
        // Get all legal moves for the current position
        List<Move> allMoves = engine.getAllLegalMoves();
        
        // Find other pieces of the same type that can move to the same square
        List<Move> ambiguousMoves = new ArrayList<>();
        for (Move m : allMoves) {
            if (m.getPieceMoved().getType().equals(piece.getType()) &&
                m.getPieceMoved().getColor().equals(piece.getColor()) &&
                m.getEndSquare().equals(end) &&
                !m.getStartSquare().equals(start)) {
                ambiguousMoves.add(m);
            }
        }
        
        if (ambiguousMoves.isEmpty()) {
            return ""; // No disambiguation needed
        }
        
        // Check if file disambiguation is sufficient
        boolean fileUnique = true;
        for (Move m : ambiguousMoves) {
            if (m.getStartSquare().getCol() == start.getCol()) {
                fileUnique = false;
                break;
            }
        }
        
        if (fileUnique) {
            return "" + (char)('a' + start.getCol());
        }
        
        // Check if rank disambiguation is sufficient
        boolean rankUnique = true;
        for (Move m : ambiguousMoves) {
            if (m.getStartSquare().getRow() == start.getRow()) {
                rankUnique = false;
                break;
            }
        }
        
        if (rankUnique) {
            return "" + (8 - start.getRow());
        }
        
        // Need full square disambiguation
        return start.getAlgebraicNotation();
    }

    /**
     * Parses a Standard Algebraic Notation string and returns the corresponding Move.
     * 
     * @param san The algebraic notation string (e.g., "e4", "Nf3", "Bxe5", "O-O")
     * @return The Move object corresponding to the notation, or null if no match found
     * @throws PGNParseException if there's an error during parsing
     */
    public Move parseMove(String san) throws PGNParseException {
        // Remove check and checkmate symbols if present
        san = san.replaceAll("[+#]", "");
        
        List<Move> possibleMoves;
        try {
            possibleMoves = engine.getAllLegalMoves();
        } catch (InvalidSquareException | GameStateException e) {
            throw new PGNParseException(san, "Internal error: " + e.getMessage());
        }

        // Handle castling
        if (san.equals("O-O") || san.equals("0-0")) {
            for (Move m : possibleMoves) {
                if (m.getKingSideCastle()) return m;
            }
        } else if (san.equals("O-O-O") || san.equals("0-0-0")) {
            for (Move m : possibleMoves) {
                if (m.getQueenSideCastle()) return m;
            }
        } else {
            // Match normal moves
            for (Move m : possibleMoves) {
                if (moveMatchesSAN(m, san)) return m;
            }
        }

        return null;
    }

    /**
     * Checks if a Move matches the given Standard Algebraic Notation string.
     * 
     * @param move The Move to check
     * @param san The algebraic notation string
     * @return true if the Move matches the notation, false otherwise
     */
    private boolean moveMatchesSAN(Move move, String san) {
        // Parse the SAN notation
        Piece piece = move.getPieceMoved();
        Square start = move.getStartSquare();
        Square end = move.getEndSquare();
        
        // Handle pawn moves
        if (piece.getType().equals("Pawn")) {
            // Check if it's a capture (e.g., "exd5")
            if (san.contains("x")) {
                // Format: file + x + destination (e.g., "exd5")
                String startFile = "" + (char)('a' + start.getCol());
                String expectedSAN = startFile + "x" + end.getAlgebraicNotation();
                
                // Check for promotion
                if (move.getIsPawnPromotion()) {
                    String promotionLetter = move.getPawnPromotionPiece().getPieceLetter();
                    expectedSAN += "=" + promotionLetter;
                }
                
                return san.equals(expectedSAN);
            } else {
                // Non-capturing pawn move (e.g., "e4")
                String expectedSAN = end.getAlgebraicNotation();
                
                // Check for promotion
                if (move.getIsPawnPromotion()) {
                    String promotionLetter = move.getPawnPromotionPiece().getPieceLetter();
                    expectedSAN += "=" + promotionLetter;
                }
                
                return san.equals(expectedSAN);
            }
        }
        
        // Handle piece moves (Knight, Bishop, Rook, Queen, King)
        String pieceLetter = piece.getPieceLetter();
        String destSquare = end.getAlgebraicNotation();
        
        // Basic pattern: piece letter + optional disambiguation + optional capture + destination
        // e.g., "Nf3", "Nbd7", "Bxd2", "R1e8"
        
        if (!san.startsWith(pieceLetter)) {
            return false;
        }
        
        if (!san.endsWith(destSquare)) {
            return false;
        }
        
        // Extract middle part (between piece letter and destination)
        String middle = san.substring(pieceLetter.length());
        if (middle.endsWith(destSquare)) {
            middle = middle.substring(0, middle.length() - destSquare.length());
        }
        
        // Remove 'x' if present
        middle = middle.replace("x", "");
        
        // If there's no disambiguation, just check destination matches
        if (middle.isEmpty()) {
            return true;
        }
        
        // If there's disambiguation, check if it matches the start square
        String startFile = "" + (char)('a' + start.getCol());
        String startRank = "" + (8 - start.getRow());
        
        if (middle.length() == 1) {
            // Single character disambiguation (file or rank)
            return middle.equals(startFile) || middle.equals(startRank);
        } else if (middle.length() == 2) {
            // Full square disambiguation
            return middle.equals(start.getAlgebraicNotation());
        }
        
        return false;
    }
}

