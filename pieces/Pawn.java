package pieces;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    
    public Pawn(String color) {
        super(color);
    }
    
    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        List<int[]> moves = new ArrayList<>();
        int direction = color.equals("white") ? -1 : 1; // White moves up, black moves down
        
        // Move forward one square
        int newRow = fromRow + direction;
        if (newRow >= 0 && newRow < 8) {
            moves.add(new int[]{newRow, fromCol});
            
            // Move forward two squares from starting position
            if (!hasMoved) {
                int newRow2 = fromRow + (2 * direction);
                if (newRow2 >= 0 && newRow2 < 8) {
                    moves.add(new int[]{newRow2, fromCol});
                }
            }
        }
        
        // Diagonal capture moves (potential captures)
        for (int colOffset : new int[]{-1, 1}) {
            int newCol = fromCol + colOffset;
            newRow = fromRow + direction;
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8) {
                moves.add(new int[]{newRow, newCol});
            }
        }
        
        return moves;
    }
    
    @Override
    public String getSymbol() {
        return color.equals("white") ? "♙" : "♟";
    }
}

