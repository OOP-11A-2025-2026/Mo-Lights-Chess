import pieces.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
//primerno chetene i pisane na pgn fail
//    public static void main(String[] args) {
//        // Create chess engine and board
//        ChessEngine engine = new ChessEngine();
//
//        // Make a few moves manually
//        // e2 to e4
//        Move move1 = new Move(engine.getBoard().getSquare("e2"), engine.getBoard().getSquare("e4"));
//        engine.makeMove(move1);
//
//        // e7 to e5
//        Move move2 = new Move(engine.getBoard().getSquare("e7"), engine.getBoard().getSquare("e5"));
//        engine.makeMove(move2);
//
//        // g1 to f3 (Knight)
//        Move move3 = new Move(engine.getBoard().getSquare("g1"), engine.getBoard().getSquare("f3"));
//        engine.makeMove(move3);
//
//        // b8 to c6 (Knight)
//        Move move4 = new Move(engine.getBoard().getSquare("b8"), engine.getBoard().getSquare("c6"));
//        engine.makeMove(move4);
//
//        // Create a PGN file
//        PGNWriter writer = new PGNWriter(engine);
//
//        try
//        {
//            writer.writePGN("example_game.pgn", "championship", "skibdi", "kasparov", "1/0");
//            System.out.println("PGN saved to example_game.pgn");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static void main(String[] args) throws IOException {
        ChessEngine engine = new ChessEngine();

        PGNReader reader = new PGNReader(engine);
        reader.readPGN("example_game.pgn");

        // Print board after reading moves
        engine.getBoard().printBoard();
    }
}
