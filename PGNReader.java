import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import pieces.*;
import exceptions.*;

public class PGNReader {

    private ChessEngine engine;

    public PGNReader(ChessEngine engine) {
        this.engine = engine;
    }

    public List<Move> readPGN(String filePath) throws ChessFileException, PGNParseException {
        List<Move> moveLog = engine.getMoveLog();
        try {
            String content = Files.readString(Path.of(filePath));

            // Remove headers
            content = content.replaceAll("\\[.*?\\]", "");
            // Remove comments { ... }
            content = content.replaceAll("\\{.*?\\}", "");
            // Remove game result
            content = content.replaceAll("1-0|0-1|1/2-1/2", "");
            // Normalize whitespace
            content = content.replaceAll("\\s+", " ").trim();
            // Split moves
            String[] tokens = content.split(" ");

            for (String token : tokens) {
                if (token.isBlank()) continue;
                if (token.matches("\\d+\\.")) continue; // skip move numbers
                if (token.matches("1-0|0-1|1/2-1/2|\\*")) continue; // skip results
                String san = token.trim();
                // Remove check/mate symbols
                san = san.replaceAll("[+#]", "");
                Move move = findMoveFromSAN(san);
                if (move != null) {
                    try {
                        engine.makeMove(move);
                    } catch (InvalidMoveException | InvalidSquareException e) {
                        throw new PGNParseException(san, e.getMessage());
                    }
                } else {
                    throw new PGNParseException(san, "No matching legal move found");
                }
            }

        } catch (IOException e) {
            throw new ChessFileException(filePath, "read", e);
        }

        return moveLog;
    }

    private Move findMoveFromSAN(String san) throws PGNParseException {
        List<Move> possibleMoves;
        try {
            possibleMoves = engine.getAllLegalMoves();
        } catch (InvalidSquareException e) {
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
