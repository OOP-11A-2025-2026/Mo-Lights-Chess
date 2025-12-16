import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import exceptions.*;

public class PGNWriter {

    private ChessEngine engine;

    public PGNWriter(ChessEngine engine) {
        this.engine = engine;
    }

    public void writePGN(String filePath, String event, String whitePlayer, String blackPlayer, String result) throws ChessFileException {
        List<Move> moves = engine.getMoveLog();
        StringBuilder sb = new StringBuilder();

        // Headers
        sb.append("[Event \"" + event + "\"]\n");
        sb.append("[Site \"?\"]\n");
        sb.append("[Date \"" + LocalDate.now() + "\"]\n");
        sb.append("[Round \"1\"]\n");
        sb.append("[White \"" + whitePlayer + "\"]\n");
        sb.append("[Black \"" + blackPlayer + "\"]\n");
        sb.append("[Result \"" + result + "\"]\n\n");

        // Moves - replay game to generate proper notation with check/checkmate symbols
        try {
            ChessEngine tempEngine = new ChessEngine();
            AlgebraicNotationParser parser = new AlgebraicNotationParser(tempEngine);
            
            int moveNumber = 1;
            for (int i = 0; i < moves.size(); i++) {
                if (i % 2 == 0) {
                    sb.append(moveNumber++ + ". ");
                }
                
                Move move = moves.get(i);
                tempEngine.makeMove(new Move(move)); // Make the move on temp engine
                
                try {
                    String notation = parser.toAlgebraicNotation(move);
                    sb.append(notation + " ");
                } catch (Exception e) {
                    // Fallback to basic notation if something goes wrong
                    sb.append(move.toString() + " ");
                }
            }
        } catch (Exception e) {
            // If replay fails, use basic notation
            int moveNumber = 1;
            for (int i = 0; i < moves.size(); i++) {
                if (i % 2 == 0) {
                    sb.append(moveNumber++ + ". ");
                }
                sb.append(moves.get(i).toString() + " ");
            }
        }

        sb.append(result);

        try {
            Files.writeString(Path.of(filePath), sb.toString());
        } catch (IOException e) {
            throw new ChessFileException(filePath, "write", e);
        }
    }
}
