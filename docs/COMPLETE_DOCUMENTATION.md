# Mo-Lights Chess - Complete Documentation

**Version 1.0**  
**Last Updated: January 2025**

---

# Table of Contents

1. [Introduction](#1-introduction)
2. [Quick Start](#2-quick-start)
3. [Installation Guide](#3-installation-guide)
4. [User Guide](#4-user-guide)
5. [API Documentation](#5-api-documentation)
6. [Developer Guide](#6-developer-guide)
7. [Quick Reference](#7-quick-reference)

---

# 1. Introduction

## About Mo-Lights Chess

Mo-Lights Chess is a fully-featured chess engine implementation in Java with complete rule enforcement, algebraic notation support, and PGN file compatibility.

### Features

**Core Chess Functionality:**
- ✅ Complete Chess Rules: All standard chess rules properly implemented
- ✅ Special Moves: Castling (kingside & queenside), En passant, Pawn promotion
- ✅ Game State Detection: Check, Checkmate, and Stalemate recognition
- ✅ Move Validation: Legal move generation with check prevention
- ✅ Undo System: Full move history with undo capability

**Input/Output:**
- 🎯 Dual Notation Support: Algebraic notation (e.g., `e4`, `Nf3`, `O-O`, `Bxe5`) and Coordinate notation (e.g., `e2 e4`, `g1 f3`)
- 📄 PGN Support: Read and write PGN (Portable Game Notation) files
- 🎨 Visual Board Display: Color-coded chess board in terminal

**User Experience:**
- 💬 Interactive CLI: Menu-driven interface with clear commands
- 💾 Save/Load Games: Persistent game state through PGN format
- 🔄 Move History: Track all moves with full algebraic notation
- 🎮 User-Friendly: Shows all legal moves, validates input, provides helpful feedback

### System Requirements

**Minimum Requirements:**
- Operating System: Windows 7+, macOS 10.10+, or Linux (any modern distribution)
- Java: JDK 11 or higher
- RAM: 50 MB
- Disk Space: 5 MB
- Display: Terminal/Command Prompt with UTF-8 support

**Recommended:**
- Java: JDK 17 or higher
- Terminal: Modern terminal with Unicode support

---

# 2. Quick Start

## For Impatient Users

**IMPORTANT:** Make sure you're in the project root directory (where `Main.java` is located), NOT in subdirectories like `pieces` or `exceptions`.

```bash
# 1. Verify you're in the right directory
ls Main.java    # Should display: Main.java

# 2. Compile all source files
javac *.java pieces/*.java exceptions/*.java

# 3. Run the game
java Main
```

That's it! The game will start.

## Quick Command Reference

| Command | Action |
|---------|--------|
| `e4` | Move pawn to e4 |
| `Nf3` | Move knight to f3 |
| `O-O` | Castle kingside |
| `moves` | Show all legal moves |
| `undo` | Take back last move |
| `save mygame` | Save game |
| `help` | Show help |

---

# 3. Installation Guide

## 3.1 Windows Installation

### Step 1: Install Java

1. **Check if Java is installed:**
   - Press `Win + R`, type `cmd`, press Enter
   - Type `java -version` and press Enter
   - If you see version information, Java is installed. Skip to Step 2.

2. **Download Java:**
   - Visit https://www.oracle.com/java/technologies/downloads/
   - Or use OpenJDK: https://adoptium.net/
   - Download the Windows x64 Installer

3. **Install Java:**
   - Run the downloaded installer
   - Follow the installation wizard
   - Accept default options
   - Wait for installation to complete

4. **Verify installation:**
   - Open a new Command Prompt
   - Type `java -version`
   - You should see the Java version

### Step 2: Download Mo-Lights Chess

**Option A: Using Git**
```cmd
cd C:\Users\YourName\Documents
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess
```

**Option B: Download ZIP**
1. Download from GitHub (click Code → Download ZIP)
2. Extract to your desired location
3. Open Command Prompt and navigate to the folder

### Step 3: Compile the Game

```cmd
javac *.java pieces/*.java exceptions/*.java
```

### Step 4: Run the Game

```cmd
java Main
```

### Optional: Enable Unicode Chess Pieces

**PowerShell:**
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
java Main
```

**Command Prompt:**
```cmd
chcp 65001
java Main
```

## 3.2 macOS Installation

### Step 1: Install Java

1. **Check if Java is installed:**
   ```bash
   java -version
   ```

2. **Install Java using Homebrew (Recommended):**
   ```bash
   # Install Homebrew if needed
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   
   # Install Java
   brew install openjdk@17
   
   # Link it
   sudo ln -sfn $(brew --prefix)/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
   ```

3. **Verify installation:**
   ```bash
   java -version
   ```

### Step 2: Download Mo-Lights Chess

```bash
cd ~/Documents
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess
```

### Step 3: Compile and Run

```bash
# Compile
javac *.java pieces/*.java exceptions/*.java

# Run
java Main
```

## 3.3 Linux Installation

### Step 1: Install Java

**Debian/Ubuntu:**
```bash
sudo apt update
sudo apt install openjdk-17-jdk
```

**Fedora:**
```bash
sudo dnf install java-17-openjdk-devel
```

**Arch Linux:**
```bash
sudo pacman -S jdk-openjdk
```

**Verify installation:**
```bash
java -version
javac -version
```

### Step 2: Download, Compile, and Run

```bash
# Download
cd ~
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess

# Compile
javac *.java pieces/*.java exceptions/*.java

# Run
java Main
```

## 3.4 Troubleshooting

### "javac: command not found" or "java: command not found"

**Problem:** Java is not in your system PATH.

**Windows Solution:**
1. Find your Java installation (usually `C:\Program Files\Java\jdk-17`)
2. Right-click "This PC" → Properties → Advanced System Settings
3. Click "Environment Variables"
4. Under "System variables," find "Path" and click "Edit"
5. Click "New" and add: `C:\Program Files\Java\jdk-17\bin`
6. Click OK, OK, OK
7. Close and reopen Command Prompt

**macOS/Linux Solution:**
Add to your `~/.bashrc` or `~/.zshrc`:
```bash
export JAVA_HOME=$(/usr/libexec/java_home)  # macOS
export PATH=$JAVA_HOME/bin:$PATH
```

Then: `source ~/.bashrc`

### Compilation Errors

Make sure you're in the correct directory:
```bash
ls Main.java  # Should show: Main.java
```

### Chess Pieces Display as Boxes

**Windows:** Use Windows Terminal or PowerShell with UTF-8:
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
```

**macOS/Linux:** Ensure terminal locale is UTF-8:
```bash
export LANG=en_US.UTF-8
```

---

# 4. User Guide

## 4.1 Playing Your First Game

### Starting a New Game

When you run `java Main`, you'll see:

```
=================================
   Welcome to Mo-Lights Chess!
=================================

--- MAIN MENU ---
1. New Game
2. Load Game
3. Quit

Enter your choice (1-3):
```

Type `1` and press Enter to start a new game.

### The Opening Position

```
  ┌────────────────┐
8 │♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜ │
7 │♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ │
6 │· · · · · · · · │
5 │· · · · · · · · │
4 │· · · · · · · · │
3 │· · · · · · · · │
2 │♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙ │
1 │♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ │
  └────────────────┘
   a b c d e f g h
```

**Legend:**
- Lighter pieces (♙♖♘♗♕♔) = White
- Darker pieces (♟♜♞♝♛♚) = Black
- · = Empty square

### Making Your First Move

White moves first. Move the king's pawn:

```
Enter command: e4
Move made: e4
```

Black responds:
```
Enter command: e5
Move made: e5
```

Continue playing by alternating turns!

## 4.2 Understanding the Board

### Board Coordinates

- **Files** (columns): a-h (left to right)
- **Ranks** (rows): 1-8 (bottom to top)
- Each square: `file + rank` (e.g., `e4`, `a1`, `h8`)

```
    a b c d e f g h  ← Files
  ┌────────────────┐
8 │♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜ │ ← Rank 8
7 │♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ │
6 │· · · · · · · · │
5 │· · · · · · · · │
4 │· · · · · · · · │
3 │· · · · · · · · │
2 │♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙ │
1 │♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ │ ← Rank 1
  └────────────────┘
```

## 4.3 Making Moves

### Algebraic Notation (Recommended)

**Pawn Moves:**
- `e4` - Move pawn to e4
- `d5` - Move pawn to d5

**Piece Moves:**
- `Nf3` - Move knight to f3
- `Bc4` - Move bishop to c4
- `Qh5` - Move queen to h5

**Piece Letters:**
- K = King
- Q = Queen
- R = Rook
- B = Bishop
- N = Knight
- (No letter) = Pawn

**Captures:**
- `Bxe5` - Bishop captures on e5
- `Nxd4` - Knight captures on d4
- `exd5` - Pawn captures on d5

### Coordinate Notation (Alternative)

Use "from-to" notation:
- `e2 e4` - Move piece from e2 to e4
- `g1 f3` - Move piece from g1 to f3

## 4.4 Special Moves

### Castling

**Kingside Castling:**
```
Enter command: O-O
```

The king moves two squares toward the kingside rook, and the rook jumps over.

Before: `♔ · · · · · · ♖`  
After: `· · · · · ♖ ♔ ·`

**Queenside Castling:**
```
Enter command: O-O-O
```

**Requirements:**
- King and rook haven't moved
- No pieces between them
- King not in check
- King doesn't move through check
- King doesn't end in check

### En Passant

Special pawn capture when an enemy pawn moves two squares and lands beside your pawn.

Example:
```
5 │· · · ♟ ♙ · · · │  Your white pawn on e5
```

Black plays `d5`:
```
5 │· · · ♟ ♙ · · · │
4 │· · · ♟ · · · · │
```

You can capture: `exd6`

**Must be done immediately on your next turn!**

### Pawn Promotion

When a pawn reaches the opposite end:

```
Enter command: e8=Q
```

Promotes to Queen. Options:
- Q = Queen (most common)
- R = Rook
- B = Bishop
- N = Knight

## 4.5 Game Commands

| Command | Description |
|---------|-------------|
| `<move>` | Make a move (e.g., `e4`, `Nf3`) |
| `moves` | Show all legal moves |
| `undo` | Undo the last move |
| `save <filename>` | Save game to file |
| `resign` | Resign the game |
| `draw` | Offer a draw |
| `accept` | Accept draw offer |
| `decline` | Decline draw offer |
| `help` | Show command list |
| `menu` | Return to main menu |

### Showing Legal Moves

```
Enter command: moves

Legal moves for white:
a2->a3 a2->a4 b2->b3 b2->b4 c2->c3 c2->c4 
(Total: 20 moves)
```

### Undoing Moves

```
Enter command: undo
Move undone!
```

Takes back the last move.

## 4.6 Saving and Loading Games

### Saving a Game

```
Enter command: save mygame
Game saved to mygame.pgn
```

### Loading a Game

From the main menu:
```
Enter your choice (1-3): 2
Enter filename to load: mygame
Game loaded successfully!
```

## 4.7 Chess Rules Reference

### Piece Movement

**Pawn (♙ ♟)**
- Moves forward one square
- Can move two squares on first move
- Captures diagonally
- Can capture en passant
- Promotes at opposite end

**Rook (♖ ♜)**
- Moves horizontally or vertically any distance
- Cannot jump over pieces

**Knight (♘ ♞)**
- Moves in "L" shape: 2 squares + 1 square perpendicular
- Can jump over pieces

**Bishop (♗ ♝)**
- Moves diagonally any distance
- Cannot jump over pieces

**Queen (♕ ♛)**
- Moves horizontally, vertically, or diagonally
- Most powerful piece

**King (♔ ♚)**
- Moves one square in any direction
- Cannot move into check
- Most important piece

### Check, Checkmate, and Stalemate

**Check:** King is under attack. Must escape check.

**Checkmate:** King is in check with no legal moves. Game over (you lose).

**Stalemate:** No legal moves but not in check. Game is a draw.

### Piece Values

| Piece | Value |
|-------|-------|
| Pawn | 1 |
| Knight | 3 |
| Bishop | 3 |
| Rook | 5 |
| Queen | 9 |
| King | ∞ |

## 4.8 Tips for Beginners

1. **Control the Center** - Try to control e4, d4, e5, d5
2. **Develop Your Pieces** - Get knights and bishops out early
3. **Castle Early** - Protect your king (usually within first 10 moves)
4. **Think Before You Move** - Use the `moves` command to see options
5. **Protect Your King** - Keep pawns in front, don't expose to attacks

---

# 5. API Documentation

## 5.1 Core Classes

### ChessEngine

The main game logic controller.

**Constructor:**
```java
public ChessEngine()
```

**Key Methods:**

```java
// Game State
public Board getBoard()
public List<Move> getMoveLog()
public String getCurrentTurn()  // "white" or "black"
public GameResult getGameResult()

// Move Execution
public void makeMove(Move move) throws InvalidMoveException, InvalidSquareException
public void undoMove() throws GameStateException, InvalidSquareException

// Move Generation
public List<Move> getAllLegalMoves() throws InvalidSquareException, GameStateException
public List<Move> getAllPossibleMoves() throws InvalidSquareException

// Game State Checks
public boolean isInCheck(String color) throws InvalidSquareException, GameStateException
public boolean isCheckmate() throws InvalidSquareException, GameStateException
public boolean isStalemate() throws InvalidSquareException, GameStateException
public Square findKing(String color) throws InvalidSquareException, GameStateException

// Game Termination
public void resign()
public boolean requestDraw()
public void acceptDraw() throws GameStateException
public void declineDraw() throws GameStateException
```

### Board

Represents the 8x8 chess board.

**Constructor:**
```java
public Board()
```

**Methods:**
```java
public Square getSquare(int row, int col) throws InvalidSquareException
public Square getSquare(String algebraic)  // e.g., "e4"
public Square[][] getSquares()
public void printBoard()
```

**Coordinate System:**
- Rows: 0-7, where row 0 = rank 8, row 7 = rank 1
- Columns: 0-7, where col 0 = file a, col 7 = file h

### Square

Represents a single board square.

**Constructor:**
```java
public Square(int row, int col)
```

**Methods:**
```java
public int getRow()
public int getCol()
public String getAlgebraicNotation()  // Returns "e4", "a1", etc.
public Piece getPiece()
public void setPiece(Piece piece)
public void removePiece()
public boolean hasPiece()
```

### Move

Represents a chess move.

**Constructors:**
```java
public Move(Square startSquare, Square endSquare)
public Move(Square startSquare, Square endSquare, boolean isEnpassant, 
            boolean isPawnPromotion, Piece pawnPromotionPiece, 
            boolean kingSideCastle, boolean queenSideCastle)
```

**Methods:**
```java
// Basic Info
public Square getStartSquare()
public Square getEndSquare()
public Piece getPieceMoved()
public Piece getPieceCaptured()

// Special Moves
public boolean getIsEnpassant()
public boolean getIsPawnPromotion()
public Piece getPawnPromotionPiece()
public boolean getKingSideCastle()
public boolean getQueenSideCastle()
```

### GameResult

Tracks game outcome.

**Enum:**
```java
public enum ResultType {
    ONGOING, WHITE_WIN, BLACK_WIN, DRAW, 
    WHITE_RESIGNED, BLACK_RESIGNED
}
```

**Methods:**
```java
public ResultType getResultType()
public String getReason()
public void setResult(ResultType resultType, String reason)
public boolean isGameOver()
public String getResultMessage()
```

## 5.2 Piece Classes

### Piece (Abstract Base Class)

**Constructor:**
```java
protected Piece(String color)  // "white" or "black"
```

**Abstract Methods:**
```java
public abstract List<int[]> getPossibleMoves(int fromRow, int fromCol)
public abstract String getSymbol()
```

**Common Methods:**
```java
public String getColor()
public boolean hasMoved()
public void setMoved(boolean moved)
public String getType()  // "Pawn", "Rook", etc.
public String getPieceLetter()  // K, Q, R, B, N, or "" for pawns
```

### Individual Pieces

**Pawn, Rook, Knight, Bishop, Queen, King** all extend `Piece` and implement:
- `getPossibleMoves(int fromRow, int fromCol)` - Returns possible move positions
- `getSymbol()` - Returns Unicode symbol (♙♖♘♗♕♔ or ♟♜♞♝♛♚)

## 5.3 Utility Classes

### AlgebraicNotationParser

Parses and generates Standard Algebraic Notation.

**Constructor:**
```java
public AlgebraicNotationParser(ChessEngine engine)
```

**Methods:**
```java
public Move parseMove(String san) throws PGNParseException
public String toAlgebraicNotation(Move move) throws InvalidSquareException, GameStateException
```

**Supported Notation:**
- Pawn moves: `e4`, `d5`
- Piece moves: `Nf3`, `Bb5`
- Captures: `Bxe5`, `exd5`
- Castling: `O-O`, `O-O-O`
- Promotion: `e8=Q`
- Disambiguation: `Nbd7`, `R1e8`

### PGNReader

Reads PGN files.

**Constructor:**
```java
public PGNReader(ChessEngine engine)
```

**Methods:**
```java
public void readPGN(String filename) throws ChessFileException, PGNParseException
```

### PGNWriter

Writes PGN files.

**Constructor:**
```java
public PGNWriter(ChessEngine engine)
```

**Methods:**
```java
public void writePGN(String filename, String event, String white, 
                     String black, String result) throws ChessFileException
```

## 5.4 Exceptions

- `ChessFileException` - File I/O errors
- `GameStateException` - Invalid game state operations
- `InvalidColorException` - Invalid color string
- `InvalidMoveException` - Illegal move
- `InvalidSquareException` - Square out of bounds
- `PGNParseException` - PGN parsing error
- `WrongTurnException` - Wrong player trying to move

## 5.5 Usage Examples

### Basic Game Flow

```java
// Create a new game
ChessEngine engine = new ChessEngine();

// Get legal moves
List<Move> legalMoves = engine.getAllLegalMoves();

// Make a move
Move selectedMove = legalMoves.get(0);
engine.makeMove(selectedMove);

// Check game state
if (engine.isCheckmate()) {
    System.out.println("Checkmate!");
} else if (engine.isStalemate()) {
    System.out.println("Stalemate!");
}

// Undo
engine.undoMove();
```

### Using Algebraic Notation

```java
ChessEngine engine = new ChessEngine();
AlgebraicNotationParser parser = new AlgebraicNotationParser(engine);

// Parse move
Move move = parser.parseMove("e4");
if (move != null) {
    engine.makeMove(move);
    
    // Convert back to notation
    String notation = parser.toAlgebraicNotation(move);
    System.out.println("Move: " + notation);
}
```

### Saving and Loading

```java
// Save
PGNWriter writer = new PGNWriter(engine);
writer.writePGN("game.pgn", "Casual Game", "Alice", "Bob", "*");

// Load
ChessEngine newEngine = new ChessEngine();
PGNReader reader = new PGNReader(newEngine);
reader.readPGN("game.pgn");
```

---

# 6. Developer Guide

## 6.1 Project Structure

```
Mo-Lights-Chess/
├── Main.java                      # CLI interface
├── ChessEngine.java               # Game logic
├── Board.java                     # Board state
├── Square.java                    # Individual square
├── Move.java                      # Move representation
├── GameResult.java                # Game outcome
├── AlgebraicNotationParser.java   # SAN parser
├── PGNReader.java                 # PGN reader
├── PGNWriter.java                 # PGN writer
├── pieces/
│   ├── Piece.java                 # Abstract base
│   ├── Pawn.java
│   ├── Rook.java
│   ├── Knight.java
│   ├── Bishop.java
│   ├── Queen.java
│   └── King.java
└── exceptions/
    ├── ChessFileException.java
    ├── GameStateException.java
    ├── InvalidColorException.java
    ├── InvalidMoveException.java
    ├── InvalidSquareException.java
    ├── PGNParseException.java
    └── WrongTurnException.java
```

## 6.2 Architecture Overview

### Data Flow

```
User Input → Main → AlgebraicNotationParser → ChessEngine → Board → Pieces
```

### Move Validation Pipeline

1. **getAllPossibleMoves()** - Generate moves based on piece movement rules
2. **getAllLegalMoves()** - Filter moves by testing each:
   - Temporarily make the move
   - Check if own king is in check
   - Undo the move
   - Keep only safe moves

## 6.3 Design Patterns

### Template Method Pattern (Pieces)

```java
public abstract class Piece {
    public abstract List<int[]> getPossibleMoves(int fromRow, int fromCol);
}

public class Knight extends Piece {
    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        // L-shaped moves
    }
}
```

### Command Pattern (Moves)

Each `Move` object encapsulates:
- What piece moved
- Where it moved
- What was captured
- Special move flags

Enables undo/redo functionality.

### Facade Pattern (ChessEngine)

`ChessEngine` provides simplified interface to complex chess rules:
```java
engine.makeMove(move);
engine.isCheckmate();
engine.getAllLegalMoves();
```

## 6.4 Adding New Features

### Adding a New Piece Type

1. Create the piece class in `pieces/`:

```java
package pieces;

public class Archbishop extends Piece {
    public Archbishop(String color) {
        super(color);
    }

    @Override
    public List<int[]> getPossibleMoves(int fromRow, int fromCol) {
        // Implement movement (Bishop + Knight)
        return moves;
    }

    @Override
    public String getSymbol() {
        return color.equals("white") ? "♗♘" : "♝♞";
    }
}
```

2. Add to `ChessEngine.getAllPossibleMoves()`:

```java
if(currentPiece.getType().equals("Archbishop")) {
    this.getArchbishopMoves(currentSquare, this.board, possibleMoves);
}
```

3. Implement move generation method:

```java
public void getArchbishopMoves(Square startSquare, Board board, 
                                List<Move> possibleMoves) {
    // Combine bishop and knight move logic
}
```

### Adding a New Command

In `Main.java`, add to the switch statement:

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

## 6.5 Code Style Guidelines

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
```

### Method Structure

```java
/**
 * Javadoc comment for public methods
 * 
 * @param row The row index (0-7)
 * @return true if successful
 */
public boolean methodName(int row) {
    // Keep methods short (< 50 lines)
    // Single responsibility
    return true;
}
```

## 6.6 Testing

### Manual Test Checklist

- [ ] Basic moves for all pieces
- [ ] Castling (both sides, blocked scenarios)
- [ ] En passant
- [ ] Pawn promotion
- [ ] Check detection
- [ ] Checkmate detection
- [ ] Stalemate detection
- [ ] Undo functionality
- [ ] Save/load PGN files

### Test PGN File

```pgn
[Event "Test"]
[Site "Local"]
[Date "2025.01.01"]
[Round "1"]
[White "Player1"]
[Black "Player2"]
[Result "*"]

1. e4 e5 2. Nf3 Nc6 3. Bb5 *
```

## 6.7 Performance Considerations

| Operation | Complexity |
|-----------|------------|
| getPossibleMoves() | O(n) where n = pieces |
| getAllLegalMoves() | O(m × k) where m = possible moves, k = opponent pieces |
| makeMove() | O(1) |
| undoMove() | O(1) |

## 6.8 Debugging Tips

### Common Issues

**"King not found" exception:**
- Check makeMove/undoMove logic
- Verify board state integrity

**Empty legal moves when shouldn't be:**
- Debug isInCheck() and isSquareUnderAttack()
- Add logging in getAllLegalMoves()

**Castling not allowed:**
- Check king/rook hasMoved flags
- Verify path is clear
- Ensure not castling through check

### Debug Output Example

```java
// Add to getAllLegalMoves() for debugging
System.out.println("Possible moves: " + possibleMoves.size());
for (Move m : possibleMoves) {
    System.out.println("  " + m);
}
```

---

# 7. Quick Reference

## 7.1 Commands

| Command | Action |
|---------|--------|
| `e4` | Pawn to e4 |
| `Nf3` | Knight to f3 |
| `Bxe5` | Bishop captures e5 |
| `O-O` | Castle kingside |
| `O-O-O` | Castle queenside |
| `e8=Q` | Promote to queen |
| `e2 e4` | Coordinate notation |
| `moves` | Show legal moves |
| `undo` | Take back move |
| `save game` | Save to game.pgn |
| `resign` | Give up |
| `draw` | Offer draw |
| `accept` | Accept draw |
| `help` | Show help |
| `menu` | Main menu |

## 7.2 Piece Symbols

| Piece | White | Black | Letter | Value |
|-------|-------|-------|--------|-------|
| King | ♔ | ♚ | K | ∞ |
| Queen | ♕ | ♛ | Q | 9 |
| Rook | ♖ | ♜ | R | 5 |
| Bishop | ♗ | ♝ | B | 3 |
| Knight | ♘ | ♞ | N | 3 |
| Pawn | ♙ | ♟ | - | 1 |

## 7.3 Board Diagram

```
  a b c d e f g h
8 ♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜
7 ♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟
6 · · · · · · · ·
5 · · · · · · · ·
4 · · · · · · · ·
3 · · · · · · · ·
2 ♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙
1 ♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖
```

## 7.4 Notation Examples

| Algebraic | Coordinate | Meaning |
|-----------|------------|---------|
| `e4` | `e2 e4` | Pawn to e4 |
| `Nf3` | `g1 f3` | Knight to f3 |
| `Bxc6` | `b5 c6` | Bishop captures c6 |
| `O-O` | - | Kingside castle |
| `O-O-O` | - | Queenside castle |
| `e8=Q` | `e7 e8` | Promote to queen |
| `exd5` | `e4 d5` | Pawn captures d5 |
| `Nbd7` | `b8 d7` | b-Knight to d7 |

## 7.5 Special Moves

### Castling Requirements
- ✓ King hasn't moved
- ✓ Rook hasn't moved
- ✓ No pieces between
- ✓ King not in check
- ✓ King doesn't pass through check
- ✓ King doesn't land in check

### En Passant Requirements
- ✓ Your pawn on 5th rank (White) or 4th rank (Black)
- ✓ Enemy pawn just moved 2 squares
- ✓ Enemy pawn beside your pawn
- ✓ Must capture immediately

### Pawn Promotion Options
- `e8=Q` → Queen (recommended)
- `e8=R` → Rook
- `e8=B` → Bishop
- `e8=N` → Knight

## 7.6 Game States

| State | Description |
|-------|-------------|
| Check | King is attacked |
| Checkmate | King in check, no escape (lose) |
| Stalemate | No legal moves, not in check (draw) |
| Resignation | Player gives up |
| Draw | Both players agree |

## 7.7 Opening Principles

1. Control the center (e4, d4, e5, d5)
2. Develop pieces (knights & bishops)
3. Castle early (protect king)
4. Don't move same piece twice
5. Connect rooks

## 7.8 Troubleshooting Quick Fixes

| Problem | Solution |
|---------|----------|
| Can't compile | Check you're in project root |
| Can't run | Verify Main.class exists |
| Java not found | Add Java to PATH |
| Pieces show as boxes | Enable UTF-8: `chcp 65001` (Windows) |
| Illegal move error | Use `moves` to see legal options |
| Can't castle | Check requirements above |

## 7.9 File Locations

```
Mo-Lights-Chess/
├── *.java       # Source files
├── *.class      # Compiled files
├── *.pgn        # Saved games
└── docs/        # Documentation
```

---

# Appendix

## A. Project Information

**Project:** Mo-Lights Chess  
**Language:** Java  
**Minimum Java Version:** JDK 11  
**License:** Open Source  
**Repository:** https://github.com/yourusername/Mo-Lights-Chess

## B. Credits

**Chess Rules:** FIDE (World Chess Federation)  
**PGN Format:** Standard Portable Game Notation  
**Unicode Symbols:** Chess piece Unicode characters

## C. Version History

**Version 1.0 (January 2025)**
- Initial release
- Complete chess rules implementation
- PGN support
- Algebraic notation
- Save/load functionality
- Draw and resignation features

---

**End of Documentation**

For the latest updates and support, visit the GitHub repository.

**Enjoy playing Mo-Lights Chess!** ♟️
