import java.util.ArrayList;
import java.util.List;


public class King extends Piece {
    
    public King(String color) {
        super(color);
    }
    
    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        List<int[]> moves = new ArrayList<>();
        
        // All 8 directions, but only one square
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        
        for (int[] dir : directions) {
            int newRow = fromRow + dir[0];
            int newCol = fromCol + dir[1];
            
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                moves.add(new int[]{newRow, newCol});
            }
        }
        
        return moves;
    }
    
    @Override
    public String getSymbol() {
        return color.equals("white") ? "♔" : "♚";
    }
}

