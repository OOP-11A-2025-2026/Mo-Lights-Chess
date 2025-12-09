package pieces;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    public Knight(String color) {
        super(color);
    }
    
    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        List<int[]> moves = new ArrayList<>();
        
        // All possible L-shaped moves
        int[][] knightMoves = {
            {-2, -1}, {-2, 1}, {-1, -2}, {-1, 2},
            {1, -2}, {1, 2}, {2, -1}, {2, 1}
        };
        
        for (int[] move : knightMoves) {
            int newRow = fromRow + move[0];
            int newCol = fromCol + move[1];
            
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                moves.add(new int[]{newRow, newCol});
            }
        }
        
        return moves;
    }
    
    @Override
    public String getSymbol() {
        return color.equals("white") ? "♘" : "♞";
    }
}

