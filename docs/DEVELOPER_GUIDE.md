# Developer Guide - Mo-Lights Chess

## Table of Contents
- [Getting Started](#getting-started)
- [Project Structure](#project-structure)
- [Architecture Overview](#architecture-overview)
- [Key Design Patterns](#key-design-patterns)
- [Adding New Features](#adding-new-features)
- [Testing](#testing)
- [Code Style Guidelines](#code-style-guidelines)
- [Common Development Tasks](#common-development-tasks)
- [Debugging Tips](#debugging-tips)
- [Performance Considerations](#performance-considerations)

---

## Getting Started

### Prerequisites for Development

- **Java JDK 11 or higher**
- **Git** for version control
- **Text Editor or IDE** (recommended: IntelliJ IDEA, Eclipse, or VS Code with Java extensions)
- Basic understanding of:
  - Object-oriented programming
  - Chess rules
  - Data structures (arrays, lists)

### Setting Up Development Environment

1. **Clone the repository**
```bash
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess
```

2. **Compile the project**
```bash
javac *.java pieces/*.java exceptions/*.java
```

3. **Run the game**
```bash
java Main
```

4. **Clean build artifacts**
```bash
# Unix/Linux/Mac
rm *.class pieces/*.class exceptions/*.class

# Windows
del *.class pieces\*.class exceptions\*.class
```

### Recommended IDE Setup

#### IntelliJ IDEA
1. Open the project folder
2. Mark source roots: Right-click on root → Mark Directory as → Sources Root
3. Set JDK: File → Project Structure → Project → SDK → Choose JDK 11+
4. Build automatically: Build → Build Project

#### VS Code
1. Install Java Extension Pack
2. Open project folder
3. VS Code will auto-detect Java source files
4. Use integrated terminal for compilation

---

## Project Structure

```
Mo-Lights-Chess/
├── Main.java                      # CLI interface and game loop
├── ChessEngine.java               # Core game logic and rules
├── Board.java                     # Board representation
├── Square.java                    # Individual square
├── Move.java                      # Move representation
├── GameResult.java                # Game outcome tracking
├── AlgebraicNotationParser.java   # SAN parser/generator
├── PGNReader.java                 # PGN file reader
├── PGNWriter.java                 # PGN file writer
│
├── pieces/
│   ├── Piece.java                 # Abstract base class
│   ├── Pawn.java
│   ├── Rook.java
│   ├── Knight.java
│   ├── Bishop.java
│   ├── Queen.java
│   └── King.java
│
├── exceptions/
│   ├── ChessFileException.java
│   ├── GameStateException.java
│   ├── InvalidColorException.java
│   ├── InvalidMoveException.java
│   ├── InvalidSquareException.java
│   ├── PGNParseException.java
│   └── WrongTurnException.java
│
├── docs/
│   ├── API.md                     # API documentation
│   ├── DEVELOPER_GUIDE.md         # This file
│   └── USER_GUIDE.md              # User documentation
│
├── README.md
└── example.pgn                    # Sample game files
```

### Module Responsibilities

| Module | Responsibility |
|--------|---------------|
| **Main** | User interface, input parsing, game loop |
| **ChessEngine** | Game rules, move validation, state management |
| **Board** | 8x8 grid representation, piece placement |
| **Square** | Individual board position |
| **Move** | Move metadata and special move flags |
| **pieces/** | Piece-specific movement rules |
| **AlgebraicNotationParser** | SAN parsing and generation |
| **PGN I/O** | Save/load games in PGN format |

---

## Architecture Overview

### Data Flow

```
User Input → Main → AlgebraicNotationParser → ChessEngine → Board → Pieces
                          ↓                         ↓
                    Move Object              Legal Move Validation
                                                    ↓
                                            Board State Update
```

### Move Validation Pipeline

```
1. getAllPossibleMoves()
   ├─ For each square with current player's piece
   ├─ Call piece.getPossibleMoves()
   └─ Add moves to list (basic movement rules only)

2. getAllLegalMoves()
   ├─ Get all possible moves
   ├─ For each possible move:
   │  ├─ Temporarily make the move
   │  ├─ Check if own king is in check
   │  ├─ Undo the move
   │  └─ If safe, add to legal moves
   └─ Return filtered list

3. Special move handling
   ├─ En passant: Detected in getPawnMoves()
   ├─ Castling: Validated in getAllLegalMoves()
   └─ Promotion: Generated as 4 separate moves (Q/R/B/N)
```

### State Management

The `ChessEngine` maintains:
- **Current board state** (via `Board` object)
- **Move history** (`List<Move>`)
- **Current turn** (`String`: "white" or "black")
- **Game result** (`GameResult` object)
- **Draw request status** (`String`: who requested)

All state changes go through `ChessEngine` methods to ensure consistency.

---

## Key Design Patterns

### 1. Template Method Pattern (Piece Movement)

```java
// Abstract base class defines the interface
public abstract class Piece {
    public abstract List<int[]> getPossibleMoves(int fromRow, int fromCol);
}

// Each piece implements its own movement logic
public class Knight extends Piece {
    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        // L-shaped moves
    }
}
```

### 2. Command Pattern (Move Objects)

Each `Move` object encapsulates:
- What piece moved
- Where it moved from/to
- What was captured
- Special move flags

This enables easy undo/redo functionality.

### 3. Strategy Pattern (Move Validation)

Different validation strategies for different move types:
- Standard moves: Basic legal move check
- Castling: Path clear + not through check
- En passant: Last move was pawn double-push
- Promotion: Pawn reached end rank

### 4. Facade Pattern (ChessEngine)

`ChessEngine` provides a simplified interface to complex chess rules:
```java
// Complex internal logic hidden behind simple methods
engine.makeMove(move);
engine.isCheckmate();
engine.getAllLegalMoves();
```

---

## Adding New Features

### Adding a New Piece Type

If you want to add a custom piece (e.g., Chancellor = Rook + Knight):

1. **Create the piece class**
```java
// pieces/Chancellor.java
package pieces;

import java.util.ArrayList;
import java.util.List;

public class Chancellor extends Piece {
    public Chancellor(String color) {
        super(color);
    }

    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        List<int[]> moves = new ArrayList<>();
        
        // Add rook moves (horizontal/vertical)
        // ... rook logic ...
        
        // Add knight moves (L-shaped)
        // ... knight logic ...
        
        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♜♘" : "♜♞";
    }

    @Override
    public String getPieceLetter() {
        return "C";
    }
}
```

2. **Add to ChessEngine.getAllPossibleMoves()**
```java
if(currentPiece.getType().equals("Chancellor"))
{
    this.getChancellorMoves(currentSquare, this.board, possibleMoves);
}
```

3. **Implement the move generation method**
```java
public void getChancellorMoves(Square startSquare, Board board, List<Move> possibleMoves) 
    throws InvalidSquareException {
    // Similar to getRookMoves() + getKnightMoves()
}
```

### Adding a New Move Type

To add a new special move (e.g., triple-step pawn on first move):

1. **Add flag to Move class**
```java
private boolean isTripleStep;

public boolean getIsTripleStep() {
    return isTripleStep;
}

public void setTripleStep() {
    this.isTripleStep = true;
}
```

2. **Detect and set flag in piece move generation**
```java
// In ChessEngine.getPawnMoves()
if (/* conditions for triple step */) {
    Move tripleStepMove = new Move(startSquare, targetSquare);
    tripleStepMove.setTripleStep();
    possibleMoves.add(tripleStepMove);
}
```

3. **Handle in makeMove() and undoMove()**
```java
// In makeMove()
if(move.getIsTripleStep()) {
    // Special handling for triple step
}

// In undoMove()
if(lastMove.getIsTripleStep()) {
    // Undo triple step
}
```

### Adding UCI/XBoard Protocol Support

To make the engine compatible with chess GUIs:

1. **Create a new interface class**
```java
// UCIProtocol.java
public class UCIProtocol {
    private ChessEngine engine;
    
    public void handleCommand(String command) {
        if (command.equals("uci")) {
            sendIdentification();
        } else if (command.startsWith("position")) {
            setPosition(command);
        } else if (command.startsWith("go")) {
            calculateBestMove();
        }
        // ... more commands
    }
}
```

2. **Integrate with Main.java**
```java
if (args.length > 0 && args[0].equals("--uci")) {
    UCIProtocol uci = new UCIProtocol();
    uci.startProtocol();
} else {
    // Normal CLI interface
}
```

---

## Testing

### Manual Testing Checklist

Test these scenarios after making changes:

#### Basic Moves
- [ ] Pawn single/double step
- [ ] All piece types can move correctly
- [ ] Captures work for all pieces
- [ ] Cannot move off board
- [ ] Cannot capture own pieces

#### Special Moves
- [ ] Castling kingside/queenside
- [ ] Castling blocked by pieces
- [ ] Cannot castle out of/through/into check
- [ ] En passant capture
- [ ] En passant only works immediately
- [ ] Pawn promotion to all piece types

#### Game Rules
- [ ] Check detection works
- [ ] Cannot move into check
- [ ] Cannot leave king in check
- [ ] Pinned pieces cannot move
- [ ] Checkmate detection
- [ ] Stalemate detection

#### Game Flow
- [ ] Turn switching
- [ ] Undo move
- [ ] Resign
- [ ] Draw offers/accept/decline

#### File I/O
- [ ] Save game to PGN
- [ ] Load game from PGN
- [ ] Algebraic notation parsing

### Creating Test PGN Files

```pgn
[Event "Test Game"]
[Site "Local"]
[Date "2025.01.01"]
[Round "1"]
[White "Player1"]
[Black "Player2"]
[Result "*"]

1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O *
```

Test loading this file:
```bash
java Main
# Choose 2 (Load Game)
# Enter filename: test.pgn
```

### Debugging Test Cases

Add debug output to trace move generation:

```java
// In getAllLegalMoves()
System.out.println("Possible moves: " + possibleMoves.size());
for (Move m : possibleMoves) {
    System.out.println("  " + m);
}
System.out.println("Legal moves: " + legalMoves.size());
```

---

## Code Style Guidelines

### Naming Conventions

```java
// Classes: PascalCase
public class ChessEngine { }

// Methods: camelCase
public void makeMove(Move move) { }

// Variables: camelCase
private String currentTurn;

// Constants: UPPER_SNAKE_CASE
private static final int BOARD_SIZE = 8;

// Packages: lowercase
package pieces;
```

### Code Organization

1. **Class member order:**
   - Static fields
   - Instance fields
   - Constructors
   - Public methods
   - Private/protected methods

2. **Method structure:**
   - Keep methods short (< 50 lines ideally)
   - Single responsibility per method
   - Extract complex logic into helper methods

3. **Comments:**
   - Javadoc for public methods
   - Inline comments for complex logic
   - No obvious comments (`// increment i`)

### Example: Well-Formatted Method

```java
/**
 * Checks if a square is under attack by the specified color.
 * 
 * @param row The row index (0-7)
 * @param col The column index (0-7)
 * @param byColor The attacking color ("white" or "black")
 * @return true if the square is under attack, false otherwise
 * @throws InvalidSquareException if row/col out of bounds
 * @throws GameStateException if king not found
 */
public boolean isSquareUnderAttack(int row, int col, String byColor) 
    throws InvalidSquareException, GameStateException {
    
    // Save current turn to restore later
    String originalTurn = this.currentTurn;
    
    // Temporarily switch to attacking color
    this.currentTurn = byColor;
    
    // Get all possible moves for attacking color
    List<Move> opponentMoves = this.getAllPossibleMoves();
    
    // Restore original turn
    this.currentTurn = originalTurn;
    
    // Check if any move targets this square
    for (Move move : opponentMoves) {
        if (move.getEndSquare().getRow() == row && 
            move.getEndSquare().getCol() == col) {
            return true;
        }
    }
    
    return false;
}
```

---

## Common Development Tasks

### Task 1: Fix a Bug in Move Validation

1. **Reproduce the bug**
   - Create a test case (PGN file or sequence of moves)
   - Document expected vs actual behavior

2. **Locate the issue**
   - Add debug output in `getAllLegalMoves()`
   - Check if move is in possible moves
   - Check if move is filtered out incorrectly

3. **Fix and verify**
   - Modify the logic
   - Test with original bug case
   - Test with related scenarios

### Task 2: Add a New Command

To add a new command like `/hint`:

1. **Add to help text** (Main.java)
```java
System.out.println("  - Hint: hint");
```

2. **Add case in switch statement**
```java
case "hint":
    List<Move> legalMoves = engine.getAllLegalMoves();
    if (!legalMoves.isEmpty()) {
        Move randomMove = legalMoves.get(
            new Random().nextInt(legalMoves.size())
        );
        System.out.println("Try: " + randomMove);
    }
    break;
```

### Task 3: Improve Board Display

To add coordinates to board display:

```java
// In Board.printBoard()
System.out.println("    a  b  c  d  e  f  g  h");
System.out.println("  ┌────────────────────────┐");

for(int row = 0; row < 8; row++) {
    System.out.print((8 - row) + " │");
    // ... piece printing ...
    System.out.println("│ " + (8 - row));
}

System.out.println("  └────────────────────────┘");
System.out.println("    a  b  c  d  e  f  g  h");
```

---

## Debugging Tips

### Common Issues and Solutions

#### Issue: "King not found" exception
**Cause**: Board state corrupted, king was captured or removed  
**Solution**: Check makeMove/undoMove logic for piece removal bugs

#### Issue: Legal moves list is empty when it shouldn't be
**Cause**: All moves filtered out by check detection  
**Solution**: Debug isInCheck() and isSquareUnderAttack()

#### Issue: Castling not allowed when it should be
**Causes**:
- King or rook `hasMoved` flag incorrectly set
- Path not actually clear (piece in between)
- King in check or moves through check

**Solution**:
```java
// Add debug output in getKingMoves()
System.out.println("King moved: " + king.hasMoved());
System.out.println("Rook moved: " + rook.hasMoved());
System.out.println("Path clear: " + pathClear);
```

#### Issue: En passant not working
**Causes**:
- Last move wasn't a pawn double-push
- Wrong turn order

**Solution**:
```java
// Debug in getPawnMoves()
if (!this.moveLog.isEmpty()) {
    Move lastMove = this.moveLog.get(this.moveLog.size() - 1);
    System.out.println("Last move piece: " + lastMove.getPieceMoved().getType());
    System.out.println("Last move distance: " + 
        Math.abs(lastMove.getStartSquare().getRow() - 
                 lastMove.getEndSquare().getRow()));
}
```

### Using a Debugger

Set breakpoints at these key locations:

1. **makeMove()** - Before board state changes
2. **getAllLegalMoves()** - Before/after filtering
3. **isInCheck()** - To see check detection logic
4. **Piece.getPossibleMoves()** - To see move generation

### Logging Game State

Add this method to ChessEngine for debugging:

```java
public void printGameState() {
    System.out.println("=== GAME STATE ===");
    System.out.println("Turn: " + currentTurn);
    System.out.println("Moves made: " + moveLog.size());
    System.out.println("Last move: " + 
        (moveLog.isEmpty() ? "none" : moveLog.get(moveLog.size()-1)));
    try {
        System.out.println("In check: " + isInCheck(currentTurn));
        System.out.println("Legal moves: " + getAllLegalMoves().size());
    } catch (Exception e) {
        System.out.println("Error checking state: " + e.getMessage());
    }
    System.out.println("==================");
}
```

---

## Performance Considerations

### Current Performance Characteristics

| Operation | Time Complexity | Notes |
|-----------|----------------|-------|
| getPossibleMoves() | O(n) | n = number of pieces on board |
| getAllLegalMoves() | O(m × k) | m = possible moves, k = opponent pieces |
| isInCheck() | O(n) | n = opponent pieces |
| makeMove() | O(1) | Constant time |
| undoMove() | O(1) | Constant time |

### Optimization Opportunities

#### 1. Move Generation Caching
```java
// Cache legal moves until board state changes
private List<Move> cachedLegalMoves;
private int cachedMoveNumber;

public List<Move> getAllLegalMoves() {
    if (cachedMoveNumber == moveLog.size() && cachedLegalMoves != null) {
        return cachedLegalMoves;
    }
    cachedLegalMoves = computeLegalMoves();
    cachedMoveNumber = moveLog.size();
    return cachedLegalMoves;
}
```

#### 2. Bitboard Representation
For high-performance applications, consider using bitboards:
```java
// Instead of 2D array of Squares
private long whitePawns;   // 64-bit integer, one bit per square
private long blackPawns;
// ... other pieces
```

Benefits:
- Faster move generation
- Efficient attack detection
- Less memory usage

Drawbacks:
- More complex code
- Harder to understand/maintain

#### 3. Incremental Update
Update game state incrementally instead of recalculating:
```java
// Maintain attacked squares
private Set<Square> whiteAttackedSquares;
private Set<Square> blackAttackedSquares;

// Update on each move instead of recalculating
public void makeMove(Move move) {
    // ... existing logic ...
    updateAttackedSquares(move);
}
```

### Memory Usage

Current memory footprint (approximate):
- Board: ~2KB (64 squares × 32 bytes)
- Move log: ~100 bytes per move
- Total for typical game: ~10-20 KB

This is negligible for modern systems, so memory optimization is not critical.

---

## Contributing

### Pull Request Process

1. **Fork and create a branch**
```bash
git checkout -b feature/your-feature-name
```

2. **Make changes**
   - Follow code style guidelines
   - Add tests if applicable
   - Update documentation

3. **Test thoroughly**
   - Run through manual test checklist
   - Test edge cases
   - Ensure no regressions

4. **Commit with clear messages**
```bash
git commit -m "Add feature: X"
git commit -m "Fix bug: Y in Z"
```

5. **Push and create PR**
```bash
git push origin feature/your-feature-name
# Create pull request on GitHub
```

### Code Review Checklist

Reviewers should check:
- [ ] Code follows style guidelines
- [ ] No new compiler warnings
- [ ] Changes are well-documented
- [ ] Edge cases are handled
- [ ] No obvious bugs
- [ ] Performance impact is acceptable

---

## Additional Resources

### Chess Programming Resources
- [Chess Programming Wiki](https://www.chessprogramming.org/)
- [PGN Format Specification](https://www.chessclub.com/help/PGN-spec)
- [Standard Algebraic Notation](https://en.wikipedia.org/wiki/Algebraic_notation_(chess))

### Java Resources
- [Java Documentation](https://docs.oracle.com/en/java/)
- [Effective Java](https://www.oreilly.com/library/view/effective-java/9780134686097/)

### Testing Tools
- [JUnit](https://junit.org/) - Unit testing framework
- [Apache Commons](https://commons.apache.org/) - Utility libraries

---

## Contact

For questions or discussions about development:
- Open an issue on GitHub
- Check existing documentation
- Review code comments

Happy coding! ♟️
