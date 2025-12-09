package pieces;

import java.util.ArrayList;
import java.util.List;

/**
 * Pieces.Bishop piece - moves diagonally.
 */
public class Bishop extends Piece {
    
    public Bishop(String color) {
        super(color);
    }
    
    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        List<int[]> moves = new ArrayList<>();
        
        // Diagonal directions
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        
        for (int[] dir : directions) {
            int dRow = dir[0];
            int dCol = dir[1];
            
            for (int i = 1; i < 8; i++) {
                int newRow = fromRow + (dRow * i);
                int newCol = fromCol + (dCol * i);
                
                if (newRow < 0 || newRow >= 8 || newCol < 0 || newCol >= 8) {
                    break;
                }
                
                moves.add(new int[]{newRow, newCol, dRow, dCol});
            }
        }
        
        return moves;
    }
    
    @Override
    public String getSymbol() {
        return color.equals("white") ? "♗" : "♝";
    }
}

