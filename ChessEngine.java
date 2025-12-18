    import pieces.*;
    import exceptions.*;

    import java.util.ArrayList;
    import java.util.List;

    public class ChessEngine {
        Board board;
        List<Move> moveLog;
        String currentTurn;
        GameResult gameResult;
        private String drawRequestedBy; // Track who requested a draw


        public ChessEngine() {
            this.board = new Board();
            this.moveLog = new ArrayList<>();
            this.currentTurn = "white";
            this.gameResult = new GameResult();
            this.drawRequestedBy = null;
        }

        public Board getBoard()
        {
            return this.board;
        }

        public List<Move> getMoveLog()
        {
            return this.moveLog;
        }
        public String getCurrentTurn()
        {
            return this.currentTurn;
        }

        public GameResult getGameResult()
        {
            return this.gameResult;
        }

        public String getDrawRequestedBy()
        {
            return this.drawRequestedBy;
        }

        public void clearDrawRequest()
        {
            this.drawRequestedBy = null;
        }
        
        public void setCurrentTurn(String currentTurn) throws InvalidColorException
        {   
            if(!currentTurn.equals("white") && !currentTurn.equals("black"))
            {
                throw new InvalidColorException(currentTurn);
            }
            this.currentTurn = currentTurn;
        }
        //execute a move by changing the board state and saving the move in movelog
        public void makeMove(Move move) throws InvalidMoveException, InvalidSquareException
        {
            if(!this.currentTurn.equals(move.getPieceMoved().getColor()))
            {
                throw new WrongTurnException(this.currentTurn, move.getPieceMoved().getColor());
            }
            Piece pieceMoved = move.getPieceMoved();
            Square start = move.getStartSquare();
            Square end = move.getEndSquare();
            end.setPiece(pieceMoved);
            start.removePiece();
            pieceMoved.setMoved(true);
            if(move.getIsEnpassant()) // en passant move
            {
                move.getEnPassantCapturingSquare().removePiece();
            }
            if(move.getIsPawnPromotion())
            {
                end.setPiece(move.getPawnPromotionPiece());
            }
            // Handle castling - move the rook as well
            if(move.getKingSideCastle()) {
                int row = start.getRow();
                Square rookStart = this.board.getSquare(row, 7);
                Square rookEnd = this.board.getSquare(row, 5);
                Piece rook = rookStart.getPiece();
                rookEnd.setPiece(rook);
                rookStart.removePiece();
                rook.setMoved(true);
            }
            if(move.getQueenSideCastle()) {
                int row = start.getRow();
                Square rookStart = this.board.getSquare(row, 0);
                Square rookEnd = this.board.getSquare(row, 3);
                Piece rook = rookStart.getPiece();
                rookEnd.setPiece(rook);
                rookStart.removePiece();
                rook.setMoved(true);
            }
            this.currentTurn = this.currentTurn.equals("white") ? "black" : "white";
            this.moveLog.add(move);
        }
        //undo move by returning to the board last state and removing last move from movelog
        public void undoMove() throws GameStateException, InvalidSquareException
        {
            if(this.moveLog.isEmpty())
            {
                throw new GameStateException("Cannot undo move from starting position");
            }
            Move lastMove = this.moveLog.remove(this.moveLog.size() - 1);
            Piece pieceMoved = lastMove.getPieceMoved();
            Piece pieceCaptured = lastMove.getPieceCaptured();
            Square start = lastMove.getStartSquare();
            Square end = lastMove.getEndSquare();
            if(lastMove.getIsEnpassant()) // undo en passant move
            {
                start.setPiece(pieceMoved);
                end.removePiece();     
                lastMove.getEnPassantCapturingSquare().setPiece(pieceCaptured);
                pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
                this.currentTurn = this.currentTurn.equals("white") ? "black" : "white";
                return;
            }
            // Handle undoing castling
            if(lastMove.getKingSideCastle() || lastMove.getQueenSideCastle()) {
                int row = start.getRow();
                start.setPiece(pieceMoved);
                end.removePiece();
                pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
                
                if(lastMove.getKingSideCastle()) {
                    Square rookEnd = this.board.getSquare(row, 5);
                    Square rookStart = this.board.getSquare(row, 7);
                    Piece rook = rookEnd.getPiece();
                    rookStart.setPiece(rook);
                    rookEnd.removePiece();
                    rook.setMoved(false);
                } else { // queenside castle
                    Square rookEnd = this.board.getSquare(row, 3);
                    Square rookStart = this.board.getSquare(row, 0);
                    Piece rook = rookEnd.getPiece();
                    rookStart.setPiece(rook);
                    rookEnd.removePiece();
                    rook.setMoved(false);
                }
                
                this.currentTurn = this.currentTurn.equals("white") ? "black" : "white";
                return;
            }
            // pawn promotion is the same undo move as normal move
            start.setPiece(pieceMoved);
            end.setPiece(pieceCaptured);
            pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
            this.currentTurn = this.currentTurn.equals("white") ? "black" : "white";
        
        }


    // Find the king of the specified color
    public Square findKing(String color) throws InvalidSquareException, GameStateException {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Square square = this.board.getSquare(row, col);
                if (square.hasPiece()) {
                    Piece piece = square.getPiece();
                    if (piece.getType().equals("King") && piece.getColor().equals(color)) {
                        return square;
                    }
                }
            }
        }
        throw new GameStateException("King not found for color: " + color);
    }
    
    // Check if a square is under attack by the specified color
    public boolean isSquareUnderAttack(int row, int col, String byColor) throws InvalidSquareException, GameStateException {
        // Save current turn
        String originalTurn = this.currentTurn;
        
        // Temporarily switch turn to get opponent's moves
        this.currentTurn = byColor;
        
        // Get all possible moves for the attacking color (not legal moves to avoid recursion)
        List<Move> opponentMoves = this.getAllPossibleMoves();
        
        // Restore original turn
        this.currentTurn = originalTurn;
        
        // Check if any opponent move targets this square
        for (Move move : opponentMoves) {
            if (move.getEndSquare().getRow() == row && move.getEndSquare().getCol() == col) {
                return true;
            }
        }
        
        return false;
    }
    
    // Check if the king of the specified color is in check
    public boolean isInCheck(String color) throws InvalidSquareException, GameStateException {
        Square kingSquare = findKing(color);
        String opponentColor = color.equals("white") ? "black" : "white";
        return isSquareUnderAttack(kingSquare.getRow(), kingSquare.getCol(), opponentColor);
    }
    
    //filter those move if the put the king in check or not
    public List<Move> getAllLegalMoves() throws InvalidSquareException, GameStateException
    {
        List<Move> possibleMoves = this.getAllPossibleMoves();
        List<Move> legalMoves = new ArrayList<>();
        
        // Test each move to see if it leaves the king in check
        for (Move move : possibleMoves) {
            // Special handling for castling - check that king doesn't castle through check
            if (move.getKingSideCastle() || move.getQueenSideCastle()) {
                String kingColor = move.getPieceMoved().getColor();
                String opponentColor = kingColor.equals("white") ? "black" : "white";
                int row = move.getStartSquare().getRow();
                int startCol = move.getStartSquare().getCol();
                
                // King cannot castle out of check
                if (isInCheck(kingColor)) {
                    continue;
                }
                
                // Check intermediate square(s)
                boolean passesThroughCheck = false;
                if (move.getKingSideCastle()) {
                    // King moves from col 4 to col 6, passing through col 5
                    if (isSquareUnderAttack(row, startCol + 1, opponentColor)) {
                        passesThroughCheck = true;
                    }
                } else { // Queenside castle
                    // King moves from col 4 to col 2, passing through col 3
                    if (isSquareUnderAttack(row, startCol - 1, opponentColor)) {
                        passesThroughCheck = true;
                    }
                }
                
                if (passesThroughCheck) {
                    continue;
                }
            }
            
            // Make the move temporarily
            try {
                makeMoveTesting(move);
                
                // Check if this leaves our king in check
                if (!isInCheck(move.getPieceMoved().getColor())) {
                    legalMoves.add(move);
                }
                
                // Undo the move
                undoMoveTesting();
            } catch (Exception e) {
                // If there's an error, skip this move
                try {
                    // Try to undo anyway
                    if (!this.moveLog.isEmpty()) {
                        undoMoveTesting();
                    }
                } catch (Exception undoError) {
                    // Ignore undo errors
                }
            }
        }
        
        return legalMoves;
    }
    
    // Make a move for testing purposes (doesn't switch turns)
    private void makeMoveTesting(Move move) throws InvalidSquareException {
        Piece pieceMoved = move.getPieceMoved();
        Square start = move.getStartSquare();
        Square end = move.getEndSquare();
        end.setPiece(pieceMoved);
        start.removePiece();
        boolean hadMoved = pieceMoved.hasMoved();
        pieceMoved.setMoved(true);
        move.setHadPieceBeenMoved(hadMoved);
        
        if(move.getIsEnpassant()) {
            move.getEnPassantCapturingSquare().removePiece();
        }
        if(move.getIsPawnPromotion()) {
            end.setPiece(move.getPawnPromotionPiece());
        }
        if(move.getKingSideCastle()) {
            int row = start.getRow();
            Square rookStart = this.board.getSquare(row, 7);
            Square rookEnd = this.board.getSquare(row, 5);
            Piece rook = rookStart.getPiece();
            rookEnd.setPiece(rook);
            rookStart.removePiece();
            rook.setMoved(true);
        }
        if(move.getQueenSideCastle()) {
            int row = start.getRow();
            Square rookStart = this.board.getSquare(row, 0);
            Square rookEnd = this.board.getSquare(row, 3);
            Piece rook = rookStart.getPiece();
            rookEnd.setPiece(rook);
            rookStart.removePiece();
            rook.setMoved(true);
        }
        
        this.moveLog.add(move);
    }
    
    // Undo a move for testing purposes (doesn't switch turns)
    private void undoMoveTesting() throws InvalidSquareException {
        if(this.moveLog.isEmpty()) {
            return;
        }
        
        Move lastMove = this.moveLog.remove(this.moveLog.size() - 1);
        Piece pieceMoved = lastMove.getPieceMoved();
        Piece pieceCaptured = lastMove.getPieceCaptured();
        Square start = lastMove.getStartSquare();
        Square end = lastMove.getEndSquare();
        
        if(lastMove.getIsEnpassant()) {
            start.setPiece(pieceMoved);
            end.removePiece();
            lastMove.getEnPassantCapturingSquare().setPiece(pieceCaptured);
            pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
            return;
        }
        
        if(lastMove.getKingSideCastle() || lastMove.getQueenSideCastle()) {
            int row = start.getRow();
            start.setPiece(pieceMoved);
            end.removePiece();
            pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
            
            if(lastMove.getKingSideCastle()) {
                Square rookEnd = this.board.getSquare(row, 5);
                Square rookStart = this.board.getSquare(row, 7);
                Piece rook = rookEnd.getPiece();
                rookStart.setPiece(rook);
                rookEnd.removePiece();
                rook.setMoved(false);
            } else {
                Square rookEnd = this.board.getSquare(row, 3);
                Square rookStart = this.board.getSquare(row, 0);
                Piece rook = rookEnd.getPiece();
                rookStart.setPiece(rook);
                rookEnd.removePiece();
                rook.setMoved(false);
            }
            return;
        }
        
        start.setPiece(pieceMoved);
        end.setPiece(pieceCaptured);
        pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
    }
    
    // Check if the current player is in checkmate
    public boolean isCheckmate() throws InvalidSquareException, GameStateException {
        return getAllLegalMoves().isEmpty() && isInCheck(this.currentTurn);
    }
    
    // Check if the game is a stalemate
    public boolean isStalemate() throws InvalidSquareException, GameStateException {
        return getAllLegalMoves().isEmpty() && !isInCheck(this.currentTurn);
    }

        //getting all moves that follow the basic rules of chess (how every piece move) + pawn promotion + en passant rules

        public List<Move> getAllPossibleMoves() throws InvalidSquareException
        {
            List<Move> possibleMoves = new ArrayList<>();
            String cols = "abcdefgh";
            String rows = "87654321";
            for(int r = 0; r < rows.length(); r++) 
            {
                for(int c = 0; c < cols.length(); c++) 
                {
                    String squareName = String.valueOf(cols.charAt(c)) + String.valueOf(rows.charAt(r));
                    Square currentSquare = this.board.getSquare(squareName);
                    Piece currentPiece = currentSquare.getPiece();
                    if(currentPiece != null)
                    {
                        if(currentPiece.getColor().equals(this.currentTurn))
                        {
                            if(currentPiece.getType().equals("Pawn"))
                            {
                                this.getPawnMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Rook"))
                            {
                                this.getRookMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Bishop"))
                            {
                                this.getBishopMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Queen"))
                            {
                                this.getQueenMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Knight"))
                            {
                                this.getKnightMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("King"))
                            {
                                this.getKingMoves(currentSquare, this.board, possibleMoves);
                            }
                        }
                    }
                }
            }

            return possibleMoves;
        } 

        public void getPawnMoves(Square startSquare, Board board, List<Move> possibleMoves) throws InvalidSquareException
        {
            Pawn pawn = (Pawn) startSquare.getPiece();
            List<int[]> possiblePawnMoves = pawn.getPossibleMoves(startSquare.getRow(), startSquare.getCol());

            for(int[] move : possiblePawnMoves)
            {
                int newRow = move[0];
                int newCol = move[1];
                
                Square targetSquare = board.getSquare(newRow, newCol);
                Piece targetPiece = targetSquare.getPiece();

                boolean sameColumn = (newCol == startSquare.getCol());
                boolean diagonalMove = Math.abs(newCol - startSquare.getCol()) == 1;
                boolean moveAdded = false;
                if(sameColumn)
                {
                    if(targetPiece != null) continue;
                    if(Math.abs(newRow - startSquare.getRow()) == 2)
                    {
                        int midRow = (newRow + startSquare.getRow()) / 2;
                        if(board.getSquare(midRow, newCol).getPiece() != null) continue;
                    }
                    possibleMoves.add(new Move(startSquare, targetSquare));
                    moveAdded = true;
                }

                if(diagonalMove)
                {
                    if(targetPiece == null)
                    {
                        //en passant 
                        if(!this.moveLog.isEmpty()) 
                        {
                            Move lastMove = this.moveLog.get(this.moveLog.size() - 1);
                            if(lastMove.getPieceMoved().getType().equals("Pawn"))
                            {
                                int pawnRow = startSquare.getRow();
                                int pawnCol = startSquare.getCol();
                                int lastStartRow = lastMove.getStartSquare().getRow();
                                int lastEndRow = lastMove.getEndSquare().getRow();
                                int lastEndCol = lastMove.getEndSquare().getCol();

                                if(Math.abs(lastStartRow - lastEndRow) == 2) 
                                {
                                    if(lastEndRow == pawnRow) 
                                    {
                                        if(Math.abs(lastEndCol - pawnCol) == 1) 
                                        {
                                            int direction = pawn.getColor().equals("white") ? -1 : 1;
                                            int epRow = pawnRow + direction;
                                            int epCol = lastEndCol; 
                                            Square epSquare = board.getSquare(epRow, epCol);
                                            if(epSquare.getPiece() == null) 
                                            {
                                                Move enPassantMove = new Move(startSquare, epSquare);
                                                enPassantMove.setEnpassant();
                                                enPassantMove.setPieceCaptured(lastMove.getPieceMoved());
                                                enPassantMove.setEnPassantCapturingSquare(lastMove.getEndSquare());
                                                possibleMoves.add(enPassantMove);

                                            }
                                        }
                                    }
                                }
                            }
                        }
                        continue;
                    }

                    if(!(targetPiece.getColor().equals(pawn.getColor())))
                    {
                        possibleMoves.add(new Move(startSquare, targetSquare));
                        moveAdded = true;
                    }
                }
                if(moveAdded) // pawn promotion
                {
                    Move lastMove = possibleMoves.get(possibleMoves.size() - 1);
                    int moveRow = lastMove.getEndSquare().getRow();
                    if(moveRow == 0 || moveRow == 7)
                    {
                        lastMove.setPawnPromotion();
                        lastMove.setPawnPromotionPiece(new Queen(lastMove.getPieceMoved().getColor()));
                        Move pawnToRook = new Move(lastMove);
                        pawnToRook.setPawnPromotionPiece(new Rook(lastMove.getPieceMoved().getColor()));
                        Move pawnToBishop = new Move(lastMove);
                        pawnToBishop.setPawnPromotionPiece(new Bishop(lastMove.getPieceMoved().getColor()));
                        Move pawnToKnight = new Move(lastMove);
                        pawnToKnight.setPawnPromotionPiece(new Knight(lastMove.getPieceMoved().getColor()));
                        possibleMoves.add(pawnToRook);
                        possibleMoves.add(pawnToBishop);
                        possibleMoves.add(pawnToKnight);
                        
                    }
                }

            }
            

        }

        public void getRookMoves(Square startSquare, Board board, List<Move> possibleMoves) throws InvalidSquareException
        {
            Rook rook = (Rook) startSquare.getPiece();
            List<int[]> possibleRookMoves = rook.getPossibleMoves(startSquare.getRow(), startSquare.getCol());
            boolean directionBlocked = false;
            int[] lastDirection =  {1000, 1000};
            for(int[] move: possibleRookMoves)
            {
            
                int newRow = move[0];
                int newCol = move[1];
                int dRow = move[2];
                int dCol = move[3];
                if(lastDirection[0] != dRow || lastDirection[1] != dCol)
                {
                    directionBlocked = false;
                    lastDirection[0] = dRow;
                    lastDirection[1] = dCol;
                }
                if(directionBlocked) continue;
                Square targetSquare = board.getSquare(newRow, newCol);
                Piece targetPiece = targetSquare.getPiece();
                if(targetPiece == null)
                {
                    possibleMoves.add(new Move(startSquare, targetSquare));
                }
                else
                {
                    if(!targetPiece.getColor().equals(rook.getColor()))
                    {
                        possibleMoves.add(new Move(startSquare, targetSquare));
                    }
                    directionBlocked = true;
                }

            }
        }

        public void getQueenMoves(Square startSquare, Board board, List<Move> possibleMoves) throws InvalidSquareException
        {
            Queen queen = (Queen) startSquare.getPiece();
            List<int[]> possibleQueenMoves = queen.getPossibleMoves(startSquare.getRow(), startSquare.getCol());
            boolean directionBlocked = false;
            int[] lastDirection =  {1000, 1000};
            for(int[] move: possibleQueenMoves)
            {
            
                int newRow = move[0];
                int newCol = move[1];
                int dRow = move[2];
                int dCol = move[3];
                if(lastDirection[0] != dRow || lastDirection[1] != dCol)
                {
                    directionBlocked = false;
                    lastDirection[0] = dRow;
                    lastDirection[1] = dCol;
                }
                if(directionBlocked) continue;
                Square targetSquare = board.getSquare(newRow, newCol);
                Piece targetPiece = targetSquare.getPiece();
                if(targetPiece == null)
                {
                    possibleMoves.add(new Move(startSquare, targetSquare));
                }
                else
                {
                    if(!targetPiece.getColor().equals(queen.getColor()))
                    {
                        possibleMoves.add(new Move(startSquare, targetSquare));
                    }
                    directionBlocked = true;
                }

            }
        }

        public void getBishopMoves(Square startSquare, Board board, List<Move> possibleMoves) throws InvalidSquareException
        {
            Bishop bishop = (Bishop) startSquare.getPiece();
            List<int[]> possibleBishopMoves = bishop.getPossibleMoves(startSquare.getRow(), startSquare.getCol());
            boolean directionBlocked = false;
            int[] lastDirection =  {1000, 1000};
            for(int[] move: possibleBishopMoves)
            {
            
                int newRow = move[0];
                int newCol = move[1];
                int dRow = move[2];
                int dCol = move[3];
                if(lastDirection[0] != dRow || lastDirection[1] != dCol)
                {
                    directionBlocked = false;
                    lastDirection[0] = dRow;
                    lastDirection[1] = dCol;
                }
                if(directionBlocked) continue;
                Square targetSquare = board.getSquare(newRow, newCol);
                Piece targetPiece = targetSquare.getPiece();
                if(targetPiece == null)
                {
                    possibleMoves.add(new Move(startSquare, targetSquare));
                }
                else
                {
                    if(!targetPiece.getColor().equals(bishop.getColor()))
                    {
                        possibleMoves.add(new Move(startSquare, targetSquare));
                    }
                    directionBlocked = true;
                }

            }
        }

        public void getKnightMoves(Square startSquare, Board board, List<Move> possibleMoves) throws InvalidSquareException
        {
            Knight knight = (Knight) startSquare.getPiece();
            List<int[]> possibleKnightMoves = knight.getPossibleMoves(startSquare.getRow(), startSquare.getCol());  
            for(int[] move: possibleKnightMoves)
            {
                int newRow = move[0];
                int newCol = move[1];
                Square targetSquare = board.getSquare(newRow, newCol);
                Piece targetPiece = targetSquare.getPiece();
                if(targetPiece == null)
                {
                    possibleMoves.add(new Move(startSquare, targetSquare));
                }
                else
                {
                    if(!targetPiece.getColor().equals(knight.getColor()))
                    {
                        possibleMoves.add(new Move(startSquare, targetSquare));
                    }
                }
            }
        }
    
        public void getKingMoves(Square startSquare, Board board, List<Move> possibleMoves) throws InvalidSquareException
        {
            King king = (King) startSquare.getPiece();
            List<int[]> possibleKingMoves = king.getPossibleMoves(startSquare.getRow(), startSquare.getCol());  
            for(int[] move: possibleKingMoves)
            {
                int newRow = move[0];
                int newCol = move[1];
                Square targetSquare = board.getSquare(newRow, newCol);
                Piece targetPiece = targetSquare.getPiece();
                if(targetPiece == null)
                {
                    possibleMoves.add(new Move(startSquare, targetSquare));
                }
                else
                {
                    if(!targetPiece.getColor().equals(king.getColor()))
                    {
                        possibleMoves.add(new Move(startSquare, targetSquare));
                    }
                }
            }
            
            // Castling
            // Note: We only check if path is clear here. Check detection (can't castle through check)
            // will be handled by getAllLegalMoves() which filters out moves that leave king in check
            if (!king.hasMoved()) {
                int kingRow = startSquare.getRow();
                int kingCol = startSquare.getCol();
                
                // Kingside castling
                Square kingsideRookSquare = board.getSquare(kingRow, 7);
                if (kingsideRookSquare.hasPiece() && 
                    kingsideRookSquare.getPiece().getType().equals("Rook") &&
                    kingsideRookSquare.getPiece().getColor().equals(king.getColor()) &&
                    !kingsideRookSquare.getPiece().hasMoved()) {
                    
                    // Check if squares between king and rook are empty
                    boolean pathClear = true;
                    for (int col = kingCol + 1; col < 7; col++) {
                        if (board.getSquare(kingRow, col).hasPiece()) {
                            pathClear = false;
                            break;
                        }
                    }
                    
                    if (pathClear) {
                        Square targetSquare = board.getSquare(kingRow, kingCol + 2);
                        Move castleMove = new Move(startSquare, targetSquare, false, false, null, true, false);
                        possibleMoves.add(castleMove);
                    }
                }
                
                // Queenside castling
                Square queensideRookSquare = board.getSquare(kingRow, 0);
                if (queensideRookSquare.hasPiece() && 
                    queensideRookSquare.getPiece().getType().equals("Rook") &&
                    queensideRookSquare.getPiece().getColor().equals(king.getColor()) &&
                    !queensideRookSquare.getPiece().hasMoved()) {
                    
                    // Check if squares between king and rook are empty
                    boolean pathClear = true;
                    for (int col = 1; col < kingCol; col++) {
                        if (board.getSquare(kingRow, col).hasPiece()) {
                            pathClear = false;
                            break;
                        }
                    }
                    
                    if (pathClear) {
                        Square targetSquare = board.getSquare(kingRow, kingCol - 2);
                        Move castleMove = new Move(startSquare, targetSquare, false, false, null, false, true);
                        possibleMoves.add(castleMove);
                    }
                }
            }
        }

    /**
     * Current player resigns from the game
     */
    public void resign()
    {
        String winner = this.currentTurn.equals("white") ? "BLACK" : "WHITE";
        GameResult.ResultType resultType = this.currentTurn.equals("white") ? 
            GameResult.ResultType.BLACK_RESIGNED : GameResult.ResultType.WHITE_RESIGNED;
        
        this.gameResult.setResult(resultType, this.currentTurn + " resigned");
    }

    /**
     * Request a draw - returns true if accepted (opponent already requested), false if pending opponent response
     */
    public boolean requestDraw()
    {
        if (this.drawRequestedBy == null) {
            // No draw request yet, set one
            this.drawRequestedBy = this.currentTurn;
            return false;
        } else if (this.drawRequestedBy.equals(this.currentTurn)) {
            // Same player requesting again - ignore
            return false;
        } else {
            // Other player already requested draw - accept it
            this.gameResult.setResult(GameResult.ResultType.DRAW, "Draw agreed by both players");
            this.drawRequestedBy = null;
            return true;
        }
    }

    /**
     * Accept a pending draw request
     */
    public void acceptDraw() throws GameStateException
    {
        if (this.drawRequestedBy == null) {
            throw new GameStateException("No draw request to accept");
        }
        if (this.drawRequestedBy.equals(this.currentTurn)) {
            throw new GameStateException("You cannot accept your own draw request");
        }
        this.gameResult.setResult(GameResult.ResultType.DRAW, "Draw agreed by both players");
        this.drawRequestedBy = null;
    }

    /**
     * Decline a pending draw request
     */
    public void declineDraw() throws GameStateException
    {
        if (this.drawRequestedBy == null) {
            throw new GameStateException("No draw request to decline");
        }
        this.drawRequestedBy = null;
    }

    }
