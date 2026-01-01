# Mo-Lights Chess - API Documentation

## Table of Contents
- [Core Classes](#core-classes)
  - [ChessEngine](#chessengine)
  - [Board](#board)
  - [Square](#square)
  - [Move](#move)
  - [GameResult](#gameresult)
- [Piece Classes](#piece-classes)
  - [Piece (Abstract)](#piece-abstract)
  - [Pawn](#pawn)
  - [Rook](#rook)
  - [Knight](#knight)
  - [Bishop](#bishop)
  - [Queen](#queen)
  - [King](#king)
- [Utility Classes](#utility-classes)
  - [AlgebraicNotationParser](#algebraicnotationparser)
  - [PGNReader](#pgnreader)
  - [PGNWriter](#pgnwriter)
- [Exceptions](#exceptions)

---

## Core Classes

### ChessEngine

The main game logic controller that manages turns, move validation, and game state.

#### Constructor

```java
public ChessEngine()
```
Creates a new chess engine with a fresh board in starting position.

#### Public Methods

##### Game State

```java
public Board getBoard()
```
Returns the current board state.

```java
public List<Move> getMoveLog()
```
Returns the list of all moves made in the game.

```java
public String getCurrentTurn()
```
Returns the current player's turn ("white" or "black").

```java
public GameResult getGameResult()
```
Returns the current game result status.

```java
public void setCurrentTurn(String currentTurn) throws InvalidColorException
```
Sets the current turn. Used primarily when loading games from PGN.
- **Parameters**: `currentTurn` - "white" or "black"
- **Throws**: `InvalidColorException` if color is not valid

##### Move Execution

```java
public void makeMove(Move move) throws InvalidMoveException, InvalidSquareException
```
Executes a move on the board, updating all game state.
- **Parameters**: `move` - The move to execute
- **Throws**: 
  - `InvalidMoveException` if move is illegal
  - `WrongTurnException` if wrong player is trying to move
  - `InvalidSquareException` if square coordinates are invalid

```java
public void undoMove() throws GameStateException, InvalidSquareException
```
Undoes the last move, restoring previous board state.
- **Throws**: 
  - `GameStateException` if no moves to undo
  - `InvalidSquareException` if square coordinates are invalid

##### Move Generation

```java
public List<Move> getAllLegalMoves() throws InvalidSquareException, GameStateException
```
Returns all legal moves for the current player. This filters out moves that would leave the king in check.
- **Returns**: List of all legal Move objects
- **Throws**: `InvalidSquareException`, `GameStateException`

```java
public List<Move> getAllPossibleMoves() throws InvalidSquareException
```
Returns all theoretically possible moves based on piece movement rules, without checking for check/checkmate.
- **Returns**: List of all possible Move objects
- **Throws**: `InvalidSquareException`

##### Game State Checks

```java
public boolean isInCheck(String color) throws InvalidSquareException, GameStateException
```
Checks if the specified color's king is in check.
- **Parameters**: `color` - "white" or "black"
- **Returns**: true if in check, false otherwise

```java
public boolean isCheckmate() throws InvalidSquareException, GameStateException
```
Checks if current player is in checkmate.
- **Returns**: true if checkmate, false otherwise

```java
public boolean isStalemate() throws InvalidSquareException, GameStateException
```
Checks if the game is in stalemate (no legal moves but not in check).
- **Returns**: true if stalemate, false otherwise

```java
public Square findKing(String color) throws InvalidSquareException, GameStateException
```
Finds and returns the square containing the king of the specified color.
- **Parameters**: `color` - "white" or "black"
- **Returns**: Square containing the king
- **Throws**: `GameStateException` if king not found

```java
public boolean isSquareUnderAttack(int row, int col, String byColor) 
    throws InvalidSquareException, GameStateException
```
Checks if a square is under attack by the specified color.
- **Parameters**: 
  - `row` - Row index (0-7)
  - `col` - Column index (0-7)
  - `byColor` - Attacking color ("white" or "black")
- **Returns**: true if square is under attack

##### Game Termination

```java
public void resign()
```
Current player resigns from the game. Sets game result appropriately.

```java
public boolean requestDraw()
```
Requests a draw. If opponent already requested, accepts immediately.
- **Returns**: true if draw accepted, false if pending opponent response

```java
public void acceptDraw() throws GameStateException
```
Accepts a pending draw request.
- **Throws**: `GameStateException` if no draw request pending

```java
public void declineDraw() throws GameStateException
```
Declines a pending draw request.
- **Throws**: `GameStateException` if no draw request pending

```java
public String getDrawRequestedBy()
```
Returns which player requested a draw, or null if none.

```java
public void clearDrawRequest()
```
Clears any pending draw request.

---

### Board

Represents the 8x8 chess board and manages piece positions.

#### Constructor

```java
public Board()
```
Creates a new board with pieces in standard starting position.

#### Public Methods

```java
public Square getSquare(int row, int col) throws InvalidSquareException
```
Gets a square by row and column indices.
- **Parameters**: 
  - `row` - Row index (0-7, where 0 is rank 8, 7 is rank 1)
  - `col` - Column index (0-7, where 0 is file a, 7 is file h)
- **Returns**: The Square at the specified position
- **Throws**: `InvalidSquareException` if coordinates out of bounds

```java
public Square getSquare(String algebraic)
```
Gets a square by algebraic notation (e.g., "e4", "a8").
- **Parameters**: `algebraic` - Algebraic notation (file + rank)
- **Returns**: The Square at the specified position

```java
public Square[][] getSquares()
```
Returns the entire 2D array of squares.
- **Returns**: 8x8 array of Square objects

```java
public void printBoard()
```
Prints a visual representation of the board to console with Unicode pieces and colored text.

---

### Square

Represents a single square on the chess board.

#### Constructor

```java
public Square(int row, int col)
```
Creates a new empty square.
- **Parameters**: 
  - `row` - Row index (0-7)
  - `col` - Column index (0-7)

#### Public Methods

```java
public int getRow()
```
Returns the row index of this square.

```java
public int getCol()
```
Returns the column index of this square.

```java
public String getAlgebraicNotation()
```
Returns the algebraic notation for this square (e.g., "e4").
- **Returns**: String in format [file][rank] (e.g., "a1", "h8")

```java
public Piece getPiece()
```
Returns the piece on this square, or null if empty.

```java
public void setPiece(Piece piece)
```
Places a piece on this square.
- **Parameters**: `piece` - The piece to place

```java
public void removePiece()
```
Removes the piece from this square (sets to null).

```java
public boolean hasPiece()
```
Checks if this square has a piece.
- **Returns**: true if square contains a piece

```java
public boolean equals(Object obj)
```
Compares this square with another object for equality (based on position).

---

### Move

Represents a single chess move with all associated metadata.

#### Constructors

```java
public Move(Square startSquare, Square endSquare)
```
Creates a standard move from one square to another.

```java
public Move(Square startSquare, Square endSquare, boolean isEnpassant, 
            boolean isPawnPromotion, Piece pawnPromotionPiece, 
            boolean kingSideCastle, boolean queenSideCastle)
```
Creates a move with all special move flags.

```java
public Move(Move move)
```
Copy constructor for creating a duplicate move.

#### Public Methods

##### Basic Getters

```java
public Square getStartSquare()
public Square getEndSquare()
public Piece getPieceMoved()
public Piece getPieceCaptured()
```

##### Special Move Getters

```java
public boolean getIsEnpassant()
public boolean getIsPawnPromotion()
public Piece getPawnPromotionPiece()
public boolean getKingSideCastle()
public boolean getQueenSideCastle()
public boolean getHadPieceBeenMoved()
public Square getEnPassantCapturingSquare()
```

##### Setters for Special Moves

```java
public void setEnpassant()
public void setPieceCaptured(Piece piece)
public void setPawnPromotion()
public void setPawnPromotionPiece(Piece piece)
public void setEnPassantCapturingSquare(Square square)
public void setHadPieceBeenMoved(boolean hadMoved)
```

##### Utility Methods

```java
public String toString()
```
Returns a string representation of the move in format "startSquare->endSquare".

```java
public boolean equals(Object obj)
```
Compares moves for equality based on start and end squares.

---

### GameResult

Tracks the outcome of a chess game.

#### Enum: ResultType

```java
public enum ResultType {
    ONGOING,         // Game is still in progress
    WHITE_WIN,       // White won (by checkmate or resignation)
    BLACK_WIN,       // Black won (by checkmate or resignation)
    DRAW,            // Game ended in a draw
    WHITE_RESIGNED,  // White resigned
    BLACK_RESIGNED   // Black resigned
}
```

#### Constructors

```java
public GameResult()
```
Creates a new game result in ONGOING state.

```java
public GameResult(ResultType resultType, String reason)
```
Creates a game result with specified type and reason.

#### Public Methods

```java
public ResultType getResultType()
```
Returns the current result type.

```java
public String getReason()
```
Returns the reason for the result (e.g., "checkmate", "resignation").

```java
public void setResult(ResultType resultType, String reason)
```
Sets the game result and reason.

```java
public boolean isGameOver()
```
Checks if the game has ended.
- **Returns**: true if game is over, false if ongoing

```java
public String getResultMessage()
```
Returns a formatted message describing the game result.
- **Returns**: User-friendly result message

---

## Piece Classes

### Piece (Abstract)

Base class for all chess pieces.

#### Constructor

```java
protected Piece(String color)
```
- **Parameters**: `color` - "white" or "black"
- **Throws**: `IllegalArgumentException` if color is invalid

#### Abstract Methods

```java
public abstract List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns all theoretical move positions for this piece type.
- **Returns**: List of int arrays [row, col] or [row, col, dRow, dCol] for sliding pieces

```java
public abstract String getSymbol()
```
Returns the Unicode symbol for this piece.

#### Public Methods

```java
public String getColor()
```
Returns "white" or "black".

```java
public boolean hasMoved()
```
Returns true if piece has moved from starting position.

```java
public void setMoved(boolean moved)
```
Sets the moved status of the piece.

```java
public String getType()
```
Returns the piece type name ("Pawn", "Rook", etc.).

```java
public String getPieceLetter()
```
Returns the algebraic notation letter (K, Q, R, B, N, or "" for pawns).

```java
public String getPieceStr()
```
Returns a string representation like "wK" (white King) or "bP" (black Pawn).

---

### Pawn

#### Methods

```java
public List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns possible pawn moves:
- Forward 1 square (2 on first move)
- Diagonal captures (included even if no piece there, for en passant detection)

```java
public String getSymbol()
```
Returns "♙" for white or "♟" for black.

---

### Rook

#### Methods

```java
public List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns all squares in horizontal and vertical lines from current position.
Each move includes direction info: [row, col, dRow, dCol]

```java
public String getSymbol()
```
Returns "♖" for white or "♜" for black.

---

### Knight

#### Methods

```java
public List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns all L-shaped moves (2 squares in one direction, 1 in perpendicular).

```java
public String getSymbol()
```
Returns "♘" for white or "♞" for black.

---

### Bishop

#### Methods

```java
public List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns all squares on diagonal lines from current position.
Each move includes direction info: [row, col, dRow, dCol]

```java
public String getSymbol()
```
Returns "♗" for white or "♝" for black.

---

### Queen

#### Methods

```java
public List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns all squares in horizontal, vertical, and diagonal lines (combination of Rook and Bishop moves).
Each move includes direction info: [row, col, dRow, dCol]

```java
public String getSymbol()
```
Returns "♕" for white or "♛" for black.

---

### King

#### Methods

```java
public List<int[]> getPossibleMoves(int fromRow, int fromCol)
```
Returns all squares one step away in any direction (8 squares max).

```java
public String getSymbol()
```
Returns "♔" for white or "♚" for black.

---

## Utility Classes

### AlgebraicNotationParser

Parses and generates Standard Algebraic Notation (SAN).

#### Constructor

```java
public AlgebraicNotationParser(ChessEngine engine)
```
Creates a parser linked to a chess engine.

#### Public Methods

```java
public Move parseMove(String san) throws PGNParseException
```
Converts algebraic notation to a Move object.
- **Parameters**: `san` - Algebraic notation (e.g., "e4", "Nf3", "O-O", "exd5")
- **Returns**: Matching Move object, or null if no legal move matches
- **Throws**: `PGNParseException` if parsing fails

**Supported notation formats:**
- Pawn moves: `e4`, `d5`
- Piece moves: `Nf3`, `Bb5`, `Qd4`
- Captures: `exd5`, `Bxc6`, `Nxe4`
- Castling: `O-O` (kingside), `O-O-O` (queenside)
- Promotion: `e8=Q`, `axb8=N`
- Disambiguation: `Nbd7`, `R1e8`, `Qh4e1`
- Check/Checkmate: Symbols `+` and `#` are stripped automatically

```java
public String toAlgebraicNotation(Move move) 
    throws InvalidSquareException, GameStateException
```
Converts a Move to algebraic notation with check/checkmate symbols.
- **Parameters**: `move` - The move to convert (must already be made on board)
- **Returns**: SAN string with check (+) or checkmate (#) symbols
- **Throws**: `InvalidSquareException`, `GameStateException`

---

### PGNReader

Reads chess games from PGN (Portable Game Notation) files.

#### Constructor

```java
public PGNReader(ChessEngine engine)
```
Creates a PGN reader linked to a chess engine.

#### Public Methods

```java
public void readPGN(String filename) 
    throws ChessFileException, PGNParseException
```
Reads a PGN file and replays all moves in the engine.
- **Parameters**: `filename` - Path to PGN file
- **Throws**: 
  - `ChessFileException` if file cannot be read
  - `PGNParseException` if PGN format is invalid or moves are illegal

**Supported PGN features:**
- Standard movetext with move numbers
- Comments in braces `{comment}`
- Alternative notation forms
- Result indicators (`1-0`, `0-1`, `1/2-1/2`, `*`)

---

### PGNWriter

Writes chess games to PGN (Portable Game Notation) files.

#### Constructor

```java
public PGNWriter(ChessEngine engine)
```
Creates a PGN writer linked to a chess engine.

#### Public Methods

```java
public void writePGN(String filename, String event, String white, 
                     String black, String result) throws ChessFileException
```
Writes the current game to a PGN file.
- **Parameters**: 
  - `filename` - Output file path
  - `event` - Event name (e.g., "Casual Game")
  - `white` - White player name
  - `black` - Black player name
  - `result` - Game result ("1-0", "0-1", "1/2-1/2", or "*")
- **Throws**: `ChessFileException` if file cannot be written

**Output format:**
- Standard PGN headers (Event, Site, Date, Round, White, Black, Result)
- Movetext with move numbers
- Proper algebraic notation with check/checkmate symbols
- 80-character line wrapping

---

## Exceptions

All custom exceptions extend from their respective base classes.

### ChessFileException
```java
extends IOException
```
Thrown when file I/O operations fail (reading/writing PGN files).

### GameStateException
```java
extends Exception
```
Thrown when an invalid game state operation is attempted (e.g., undo from start position, accepting non-existent draw).

### InvalidColorException
```java
extends InvalidMoveException
```
Thrown when an invalid color string is provided (not "white" or "black").

### InvalidMoveException
```java
extends Exception
```
Base exception for illegal moves.

### InvalidSquareException
```java
extends Exception
```
Thrown when attempting to access a square outside the board (row/col not in 0-7).

### PGNParseException
```java
extends Exception
```
Thrown when PGN parsing fails due to invalid notation or illegal moves.

### WrongTurnException
```java
extends InvalidMoveException
```
Thrown when a player attempts to move out of turn.

---

## Usage Examples

### Basic Game Flow

```java
// Create a new game
ChessEngine engine = new ChessEngine();

// Get legal moves for current player
List<Move> legalMoves = engine.getAllLegalMoves();

// Make a move
Move selectedMove = legalMoves.get(0);
engine.makeMove(selectedMove);

// Check game state
if (engine.isCheckmate()) {
    System.out.println("Checkmate!");
} else if (engine.isStalemate()) {
    System.out.println("Stalemate!");
} else if (engine.isInCheck(engine.getCurrentTurn())) {
    System.out.println("Check!");
}

// Undo the last move
engine.undoMove();
```

### Using Algebraic Notation

```java
ChessEngine engine = new ChessEngine();
AlgebraicNotationParser parser = new AlgebraicNotationParser(engine);

// Parse and make a move
Move move = parser.parseMove("e4");
if (move != null) {
    engine.makeMove(move);
    
    // Convert back to notation
    String notation = parser.toAlgebraicNotation(move);
    System.out.println("Move: " + notation); // "e4"
}
```

### Loading and Saving Games

```java
// Save a game
ChessEngine engine = new ChessEngine();
// ... play some moves ...
PGNWriter writer = new PGNWriter(engine);
writer.writePGN("mygame.pgn", "Casual Game", "Alice", "Bob", "*");

// Load a game
ChessEngine newEngine = new ChessEngine();
PGNReader reader = new PGNReader(newEngine);
reader.readPGN("mygame.pgn");
```

### Handling Pawn Promotion

```java
// Get legal moves
List<Move> legalMoves = engine.getAllLegalMoves();

// Filter for promotion moves to a specific piece
for (Move move : legalMoves) {
    if (move.getIsPawnPromotion()) {
        if (move.getPawnPromotionPiece().getType().equals("Queen")) {
            engine.makeMove(move); // Promote to queen
            break;
        }
    }
}
```

---

## Architecture Notes

### Move Validation Strategy

The engine uses a two-phase move validation approach:

1. **`getAllPossibleMoves()`**: Generates all moves based on piece movement rules
2. **`getAllLegalMoves()`**: Filters possible moves by testing each one:
   - Temporarily makes the move
   - Checks if own king is in check
   - Undoes the move
   - Keeps only moves that don't leave king in check

This ensures perfect legal move generation including complex cases like pinned pieces and discovered checks.

### Board Coordinate System

- **Rows**: 0-7, where row 0 = rank 8 (black's back rank), row 7 = rank 1 (white's back rank)
- **Columns**: 0-7, where col 0 = file 'a', col 7 = file 'h'
- **Algebraic notation**: Standard chess notation (e.g., "e4" = row 4, col 4)

### Threading Considerations

This implementation is **not thread-safe**. All operations on a `ChessEngine` instance should be performed from a single thread.

---

## Version

API Version: 1.0  
Last Updated: 2025
