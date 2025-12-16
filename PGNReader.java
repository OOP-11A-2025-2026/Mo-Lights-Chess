import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import exceptions.*;

public class PGNReader {

    private ChessEngine engine;
    private AlgebraicNotationParser parser;

    public PGNReader(ChessEngine engine) {
        this.engine = engine;
        this.parser = new AlgebraicNotationParser(engine);
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
                Move move = parser.parseMove(san);
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
}
