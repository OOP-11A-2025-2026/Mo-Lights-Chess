import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class PGNReader {

    private ChessEngine engine;

    public PGNReader(ChessEngine engine) {
        this.engine = engine;
    }

    public List<Move> readPGN(String filePath) {
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
                    engine.makeMove(move);
                } else {
                    System.err.println("Could not parse SAN: " + token);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return moveLog;
    }

    private Move findMoveFromSAN(String san) {
        List<Move> possibleMoves = engine.getAllLegalMoves();

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
        String generatedSAN = move.toString();
        // Remove spaces to match formatting
        generatedSAN = generatedSAN.replaceAll("\\s+", "");
        san = san.replaceAll("\\s+", "");
        return generatedSAN.equals(san);
    }
}
