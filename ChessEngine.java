    import pieces.*;

    import java.util.ArrayList;
    import java.util.List;

    public class ChessEngine {
        Board board;
        List<Move> moveLog;
        String currentTurn;


        public ChessEngine() {
            this.board = new Board();
            this.moveLog = new ArrayList<>();
            this.currentTurn = "white";
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
        
        public void setCurrentTurn(String currentTurn) 
        {   
            if(!currentTurn.equals("white") && !currentTurn.equals("black"))
            {
                throw new IllegalArgumentException("Color must be 'white' or 'black'");
            }
            this.currentTurn = currentTurn;
        }
        //execute a move by changing the board state and saving the move in movelog
        public void makeMove(Move move)    
        {
            if(!this.currentTurn.equals(move.getPieceMoved().getColor()))
            {
                throw new IllegalArgumentException("you can't move your pieces on opponent turn");
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
            this.currentTurn = this.currentTurn.equals("white") ? "black" : "white";
            this.moveLog.add(move);
        }
        //undo move by returning to the board last state and removing last move from movelog
        public void undoMove()
        {
            if(this.moveLog.isEmpty())
            {
                throw new IllegalArgumentException("You cant undo move from starting position");
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
            // pawn promotion is the same undo move as normal move
            start.setPiece(pieceMoved);
            end.setPiece(pieceCaptured);
            pieceMoved.setMoved(lastMove.getHadPieceBeenMoved());
            this.currentTurn = this.currentTurn.equals("white") ? "black" : "white";
        
        }


        //filter those move if the put the king in check or not
        public List<Move> getAllLegalMoves()
        {
            return this.getAllPossibleMoves();
        }

        //getting all moves that follow the basic rules of chess (how every piece move) + pawn promotion + en passant rules

        public List<Move> getAllPossibleMoves()
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
                            if(currentPiece.getType().equals("Pieces.Pawn"))
                            {
                                this.getPawnMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Pieces.Rook"))
                            {
                                this.getRookMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Pieces.Bishop"))
                            {
                                this.getBishopMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Pieces.Queen"))
                            {
                                this.getQueenMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Pieces.Knight"))
                            {
                                this.getKnightMoves(currentSquare, this.board, possibleMoves);
                            }
                            if(currentPiece.getType().equals("Pieces.King"))
                            {
                                this.getKingMoves(currentSquare, this.board, possibleMoves);
                            }
                        }
                    }
                }
            }

            return possibleMoves;
        } 

        public void getPawnMoves(Square startSquare, Board board, List<Move> possibleMoves)
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
                            if(lastMove.getPieceMoved().getType().equals("Pieces.Pawn"))
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

        public void getRookMoves(Square startSquare, Board board, List<Move> possibleMoves)
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

        public void getQueenMoves(Square startSquare, Board board, List<Move> possibleMoves)
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

        public void getBishopMoves(Square startSquare, Board board, List<Move> possibleMoves)
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

        public void getKnightMoves(Square startSquare, Board board, List<Move> possibleMoves)
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
    
        public void getKingMoves(Square startSquare, Board board, List<Move> possibleMoves)
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
        }




    }
