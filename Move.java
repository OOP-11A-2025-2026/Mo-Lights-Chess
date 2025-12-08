public class Move {

    private Square startSquare;
    private Square endSquare;
    private Piece pieceMoved;
    private Piece pieceCaptured;

    //has pieceMoved been moved before this move
    private boolean hadPieceBeenMoved;

    //en passant 
    private boolean isEnPassant;
    private Square enPassantCapturingSquare;

    //pawn promotion 
    private boolean isPawnPromotion;
    private Piece pawnPromotionPiece;

    //castle
    private boolean kingSideCastle;
    private boolean queenSideCastle;
    
    
    //normal move 
    public Move(Square startSquare, Square endSquare)
    {
        if(startSquare.getPiece() == null)
        {
            throw new IllegalArgumentException("You can't move from empty square");
        }
        if(startSquare.getAlgebraicNotation().equals(endSquare.getAlgebraicNotation()))
        {
            throw new IllegalArgumentException("Your move end square should be different from you move start square");
        }
        this.startSquare = startSquare;
        this.endSquare = endSquare;        
        this.pieceMoved = this.startSquare.getPiece();
        this.pieceCaptured = this.endSquare.getPiece();
        this.isEnPassant = false;
        this.isPawnPromotion = false;
        this.pawnPromotionPiece = null;
        this.kingSideCastle = false;
        this.queenSideCastle = false;
        this.hadPieceBeenMoved = this.pieceMoved.hasMoved();
        this.enPassantCapturingSquare = null;
    }
    //special moves (en passant, pawn promotion, castle)
    public Move(Square startSquare, Square endSquare, boolean isEnPassant, boolean isPawnPromotion, Piece pawnPromotionPiece, boolean kingSideCastle, boolean queenSideCastle)
    {
        if(startSquare.getPiece() == null)
        {
            throw new IllegalArgumentException("You can't move from empty square");
        }
        this.startSquare = startSquare;
        this.endSquare = endSquare;        
        this.pieceMoved = this.startSquare.getPiece();
        this.pieceCaptured = this.endSquare.getPiece();
        this.isEnPassant = isEnPassant;
        this.isPawnPromotion = isPawnPromotion;
        this.pawnPromotionPiece = pawnPromotionPiece;
        this.kingSideCastle = kingSideCastle;
        this.queenSideCastle = queenSideCastle;
        this.hadPieceBeenMoved = this.pieceMoved.hasMoved();
        this.enPassantCapturingSquare = null;
    }
    //copy constructor
    public Move(Move other)
    {
        this.startSquare = other.startSquare;
        this.endSquare = other.endSquare;        
        this.pieceMoved = other.pieceMoved;
        this.pieceCaptured = other.pieceCaptured;
        this.isEnPassant = other.isEnPassant;
        this.isPawnPromotion = other.isPawnPromotion;
        this.pawnPromotionPiece = other.pawnPromotionPiece;
        this.kingSideCastle = other.kingSideCastle;
        this.queenSideCastle = other.queenSideCastle;
        this.hadPieceBeenMoved = other.hadPieceBeenMoved;
        this.enPassantCapturingSquare = other.enPassantCapturingSquare;
    }


    //getters

    public Square getStartSquare()
    {
        return this.startSquare;
    }
    public Square getEndSquare()
    {
        return this.endSquare;
    }
    public boolean getIsEnpassant()
    {
        return this.isEnPassant;
    }
    public boolean getIsPawnPromotion()
    {
        return this.isPawnPromotion;
    }
    public Piece getPawnPromotionPiece()
    {
        return this.pawnPromotionPiece;
    }
    public Piece getPieceMoved()
    {
        return this.pieceMoved;
    }
    public Piece getPieceCaptured()
    {
        return this.pieceCaptured;
    }
    public boolean getKingSideCastle()
    {
        return this.kingSideCastle;
    }
    public boolean getQueenSideCastle()
    {
        return this.queenSideCastle;
    }
    public boolean getHadPieceBeenMoved()
    {
        return this.hadPieceBeenMoved;
    }
    public Square getEnPassantCapturingSquare()
    {
        return this.enPassantCapturingSquare;
    }

    //setters
    public void setEnpassant()
    {
        this.isEnPassant = true;
    }
    public void setPawnPromotion()
    {
        this.isPawnPromotion = true;
    }
    public void setPawnPromotionPiece(Piece piece)
    {
        this.pawnPromotionPiece = piece;
    }
    public void setPieceMoved(Piece piece)
    {
        this.pieceMoved = piece;
    }
    public void setPieceCaptured(Piece piece)
    {
        this.pieceCaptured = piece;
    }
    public void setHadPieceBeenMoved(boolean hadPieceBeenMoved)
    {
        this.hadPieceBeenMoved = hadPieceBeenMoved;
    }
    public void setEnPassantCapturingSquare(Square square)
    {
        this.enPassantCapturingSquare = square;
    }

    public boolean areTwoMovesEqual(Move other)
    {
        if(startSquare == null) 
        {
            if(other.startSquare != null) return false;
        } 
        else if(!startSquare.equals(other.startSquare)) 
        {
            return false;
        }

        if(endSquare == null) 
        {
            if(other.endSquare != null) return false;
        } 
        else if(!endSquare.equals(other.endSquare)) 
        {
            return false;
        }

        if (pieceMoved == null)
        {
            if(other.pieceMoved != null) return false;
        } 
        else if(!pieceMoved.equals(other.pieceMoved)) 
        {
            return false;
        }

        
        return true;
    }


    @Override
    public String toString() {
        if(this.kingSideCastle)
        {
            return "0-0";
        }
        else if(this.queenSideCastle)
        {
            return "0-0-0";
        }
        StringBuilder sb = new StringBuilder();
        String pieceLetter = this.pieceMoved.getPieceLetter();
        
        if(pieceLetter.isEmpty())
        {
            pieceLetter = this.startSquare.getAlgebraicNotation() + " ";
        }
        sb.append(pieceLetter);
        String capturingText = (this.pieceCaptured != null) ? "x" : "";
        sb.append(capturingText);
        sb.append(endSquare.getAlgebraicNotation());
        if(isPawnPromotion) 
        {
            sb.append("=").append(this.pawnPromotionPiece.getPieceLetter());
        }
        return sb.toString();
    }

}

