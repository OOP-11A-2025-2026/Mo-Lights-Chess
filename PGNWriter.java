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

        // Moves
        int moveNumber = 1;
        for (int i = 0; i < moves.size(); i++) {
            if (i % 2 == 0) {
                sb.append(moveNumber++ + ". ");
            }
            String end = moves.get(i).toString().split(" ")[1];
            sb.append(end + " ");
        }

        sb.append(result);

        try {
            Files.writeString(Path.of(filePath), sb.toString());
        } catch (IOException e) {
            throw new ChessFileException(filePath, "write", e);
        }
    }
}
