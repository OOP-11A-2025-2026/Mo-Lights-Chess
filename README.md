# ♟️ Mo-Lights Chess

A fully-featured chess engine implementation in Java with complete rule enforcement, algebraic notation support, and PGN file compatibility.

## 📋 Table of Contents
- [Features](#-features)
- [Quick Start](#-quick-start)
- [Documentation](#-documentation)
- [Architecture](#-architecture)
- [How It Works](#-how-it-works)
- [Installation](#-installation)
- [Usage](#-usage)
- [Game Rules Implemented](#-game-rules-implemented)
- [Project Structure](#-project-structure)
- [Technical Details](#-technical-details)
- [Examples](#-examples)

## ✨ Features

### Core Chess Functionality
- ✅ **Complete Chess Rules**: All standard chess rules properly implemented
- ✅ **Special Moves**: Castling (kingside & queenside), En passant, Pawn promotion
- ✅ **Game State Detection**: Check, Checkmate, and Stalemate recognition
- ✅ **Move Validation**: Legal move generation with check prevention
- ✅ **Undo System**: Full move history with undo capability

### Input/Output
- 🎯 **Dual Notation Support**: 
  - Algebraic notation (e.g., `e4`, `Nf3`, `O-O`, `Bxe5`)
  - Coordinate notation (e.g., `e2 e4`, `g1 f3`)
- 📄 **PGN Support**: Read and write PGN (Portable Game Notation) files
- 🎨 **Visual Board Display**: Color-coded chess board in terminal

### User Experience
- 💬 **Interactive CLI**: Menu-driven interface with clear commands
- 💾 **Save/Load Games**: Persistent game state through PGN format
- 🔄 **Move History**: Track all moves with full algebraic notation
- 🎮 **User-Friendly**: Shows all legal moves, validates input, provides helpful feedback

## 🚀 Quick Start

**IMPORTANT:** Make sure you're in the project root directory (where `Main.java` is located), NOT in subdirectories like `pieces` or `exceptions`.

```bash
# Verify you're in the right directory
ls Main.java    # Should display: Main.java

# Compile all source files
javac *.java pieces/*.java exceptions/*.java

# Run the game
java Main
```

## 📚 Documentation

Comprehensive documentation is available in the `docs/` folder:

- **[Installation Guide](docs/INSTALLATION.md)** - Detailed setup instructions for Windows, macOS, and Linux
- **[User Guide](docs/USER_GUIDE.md)** - How to play, commands, special moves, and tips
- **[API Documentation](docs/API.md)** - Complete API reference for all classes and methods
- **[Developer Guide](docs/DEVELOPER_GUIDE.md)** - Architecture, design patterns, and contributing guidelines

### Quick Links

- **New to chess?** Start with the [User Guide](docs/USER_GUIDE.md)
- **Installation problems?** Check the [Installation Guide](docs/INSTALLATION.md)
- **Want to contribute?** Read the [Developer Guide](docs/DEVELOPER_GUIDE.md)
- **Building an extension?** See the [API Documentation](docs/API.md)

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────────┐
│                              Main.java                               │
│                        (User Interface Layer)                        │
│  • Menu system                                                       │
│  • Game loop                                                         │
│  • Input parsing                                                     │
│  • Display formatting                                                │
└───────────────────────────────┬─────────────────────────────────────┘
                                │
                    ┌───────────▼───────────┐
                    │   ChessEngine.java    │
                    │   (Game Logic Core)   │
                    │                       │
                    │  • Turn management    │
                    │  • Move execution     │
                    │  • Legal move gen     │
                    │  • Check detection    │
                    │  • Game state eval    │
                    └───┬────────────────┬──┘
                        │                │
        ┌───────────────▼──┐      ┌─────▼──────────────┐
        │   Board.java     │      │   Move.java        │
        │                  │      │                    │
        │  • 8x8 grid      │      │  • Move metadata   │
        │  • Piece setup   │      │  • Special flags   │
        │  • Board display │      │  • Captured pieces │
        └─────┬────────────┘      └────────────────────┘
              │
      ┌───────▼────────┐
      │  Square.java   │
      │                │
      │  • Position    │
      │  • Piece ref   │
      └───┬────────────┘
          │
    ┌─────▼──────────────────────────────────────────────┐
    │                Pieces Package                      │
    ├────────────────────────────────────────────────────┤
    │  Piece.java (Abstract)                             │
    │    │                                               │
    │    ├── Pawn.java      • Forward movement          │
    │    │                  • Captures diagonally        │
    │    │                  • En passant                 │
    │    │                  • Promotion                  │
    │    │                                               │
    │    ├── Rook.java      • Horizontal/vertical       │
    │    │                                               │
    │    ├── Knight.java    • L-shaped moves            │
    │    │                                               │
    │    ├── Bishop.java    • Diagonal moves            │
    │    │                                               │
    │    ├── Queen.java     • All directions            │
    │    │                                               │
    │    └── King.java      • One square any direction  │
    │                       • Castling                   │
    └────────────────────────────────────────────────────┘

┌──────────────────────┐          ┌──────────────────────┐
│  PGN Input/Output    │          │  Exceptions Package  │
├──────────────────────┤          ├──────────────────────┤
│ PGNReader.java       │          │ ChessFileException   │
│  • Parse PGN files   │          │ GameStateException   │
│                      │          │ InvalidColorException│
│ PGNWriter.java       │          │ InvalidMoveException │
│  • Generate PGN      │          │ InvalidSquareException│
│                      │          │ PGNParseException    │
│ AlgebraicNotation    │          │ WrongTurnException   │
│ Parser.java          │          └──────────────────────┘
│  • Parse moves       │
│  • Generate notation │
└──────────────────────┘
```

## 🔄 How It Works

### Move Validation Pipeline

```
User Input → Parse Notation → Generate Possible Moves → Filter Legal Moves → Execute Move
                ↓                      ↓                       ↓                 ↓
           "Nf3" or "g1 f3"    Knight from g1        Remove moves that     Update board
                                  can move to:        leave king in         Switch turn
                                  • f3                check:                Add to log
                                  • h3                • Test each move
                                  • e2                • Check for check
                                                      • Keep only safe
```

### Legal Move Generation Process

1. **Possible Moves Generation** (`getAllPossibleMoves`)
   - Iterate through all squares
   - For each piece of current color, get all moves following basic movement rules
   - Include special moves (castling, en passant, promotion)

2. **Legal Move Filtering** (`getAllLegalMoves`)
   - Test each possible move
   - Temporarily execute move
   - Check if own king is in check
   - Undo move
   - Keep only moves that don't leave king in check
   - Special handling for castling (can't castle through check)

3. **Game State Evaluation**
   - **Check**: King is under attack
   - **Checkmate**: In check + no legal moves
   - **Stalemate**: Not in check + no legal moves

### Data Flow During a Move

```
┌──────────────┐
│ User Input:  │
│    "e4"      │
└──────┬───────┘
       │
       ▼
┌─────────────────────────────┐
│ AlgebraicNotationParser     │
│ • Identifies piece: Pawn    │
│ • Identifies target: e4     │
│ • Resolves ambiguity        │
└──────┬──────────────────────┘
       │
       ▼
┌─────────────────────────────┐
│ ChessEngine.getAllLegalMoves│
│ • Gets all possible moves   │
│ • Filters by safety         │
│ • Returns: e2→e4 (legal)    │
└──────┬──────────────────────┘
       │
       ▼
┌─────────────────────────────┐
│ ChessEngine.makeMove        │
│ • Validates turn            │
│ • Updates board state       │
│ • Handles special moves     │
│ • Switches turn             │
│ • Logs move                 │
└──────┬──────────────────────┘
       │
       ▼
┌─────────────────────────────┐
│ Board.printBoard            │
│ • Displays updated position │
│ • Shows piece colors        │
└─────────────────────────────┘
```

## 💻 Installation

### Prerequisites
- **Java Development Kit (JDK) 11 or higher**
  - Check if you have Java installed: `java -version`
  - Download from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/)
- **Terminal/Command Prompt**

### Quick Start

1. **Clone or download the repository**
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

That's it! The game should start and display the main menu.

### Optional: Enable Better Chess Piece Display

For proper Unicode chess piece rendering (♟️♜♞♝♛♚):

**Windows PowerShell:**
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
```

**Windows Command Prompt (CMD):**
```cmd
chcp 65001
```

**macOS/Linux:**
Usually works by default. If you see strange characters, ensure your terminal uses UTF-8 encoding.

### Troubleshooting

**"javac: command not found"**
- Java JDK is not installed or not in your PATH
- Install JDK and ensure `JAVA_HOME` is set correctly

**Compilation errors**
- Make sure you're in the project root directory (where Main.java is located)
- Check that you have JDK 11 or higher: `javac -version`

**Chess pieces display as boxes or question marks**
- Your terminal doesn't support UTF-8 or Unicode characters
- Try the UTF-8 commands above for your platform
- The game will still work, just with less pretty display

**To clean up compiled files:**
```bash
rm *.class pieces/*.class exceptions/*.class
```

Or on Windows:
```cmd
del *.class pieces\*.class exceptions\*.class
```

## 🎮 Usage

### Starting a New Game

When you run the program, you'll see the main menu:

```
=================================
   Welcome to Mo-Lights Chess!
=================================

--- MAIN MENU ---
1. New Game
2. Load Game
3. Quit
```

### Making Moves

#### Algebraic Notation (Recommended)
```
e4          - Move pawn to e4
Nf3         - Move knight to f3
Bxe5        - Bishop captures on e5
O-O         - Kingside castling
O-O-O       - Queenside castling
e8=Q        - Pawn promotion to Queen
```

#### Coordinate Notation
```
e2 e4       - Move piece from e2 to e4
g1 f3       - Move piece from g1 to f3
```

### Commands

| Command | Description |
|---------|-------------|
| `<move>` | Make a move using algebraic or coordinate notation |
| `undo` | Undo the last move |
| `moves` | Display all legal moves for the current position |
| `save <filename>` | Save the current game to a PGN file |
| `menu` | Return to main menu |
| `help` | Display help information |

### Loading a Game

1. Select "Load Game" from main menu
2. Enter the filename (with or without .pgn extension)
3. The game will load all moves from the file
4. Continue playing from that position

## 🎯 Game Rules Implemented

### Standard Pieces

| Piece | Movement | Special Rules |
|-------|----------|---------------|
| ♟️ Pawn | Forward 1 square (2 on first move) | Can capture diagonally, En passant, Promotion |
| ♜ Rook | Any distance horizontal/vertical | Used in castling |
| ♞ Knight | L-shape (2+1 squares) | Can jump over pieces |
| ♝ Bishop | Any distance diagonal | Stays on same color |
| ♛ Queen | Any distance any direction | Most powerful piece |
| ♚ King | One square any direction | Castling, cannot move into check |

### Special Moves

#### Castling
- King and rook haven't moved
- No pieces between them
- King not in check
- King doesn't move through check
- King doesn't end in check

#### En Passant
- Opponent pawn moves two squares forward
- Lands beside your pawn
- You can capture it as if it moved only one square
- Must be done immediately on next turn

#### Pawn Promotion
- Pawn reaches the opposite end of the board
- Promotes to Queen, Rook, Bishop, or Knight
- Player chooses promotion piece

### Win Conditions

- **Checkmate**: King is in check and has no legal moves
- **Stalemate**: Not in check but has no legal moves (draw)
- **Resignation**: Player gives up (manual)

## 📁 Project Structure

```
Mo-Lights-Chess/
├── Main.java                      # Entry point and user interface
├── ChessEngine.java               # Core game logic and rules
├── Board.java                     # Chess board representation
├── Square.java                    # Individual board square
├── Move.java                      # Move representation
├── AlgebraicNotationParser.java   # Parse and generate algebraic notation
├── PGNReader.java                 # Load games from PGN files
├── PGNWriter.java                 # Save games to PGN files
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
└── test_*.pgn                     # Sample game files
```

## 🔧 Technical Details

### Key Design Decisions

#### 1. Move Validation Strategy
- **Two-phase approach**: First generate all possible moves, then filter by legality
- Ensures accurate check detection by testing each move
- Prevents illegal moves that would leave king in check

#### 2. Board Representation
- **8x8 2D array** of Square objects
- Row 0 = Rank 8 (black's back rank)
- Row 7 = Rank 1 (white's back rank)
- Efficient for move generation and validation

#### 3. Piece Movement
- Each piece class implements `getPossibleMoves()`
- Returns list of coordinates the piece can potentially move to
- ChessEngine filters these by board state and legality

#### 4. Special Move Handling
- **Castling**: Validated in `getKingMoves()`, filtered in `getAllLegalMoves()`
- **En Passant**: Detected in `getPawnMoves()` by checking last move
- **Promotion**: Generated as 4 separate moves (Q, R, B, N) in `getPawnMoves()`

#### 5. Check Detection
- `isSquareUnderAttack()`: Temporarily switches turn, generates opponent moves
- `isInCheck()`: Checks if king's square is under attack
- Used in move validation and game state evaluation

### Algorithm Complexity

| Operation | Time Complexity | Notes |
|-----------|----------------|-------|
| Generate Possible Moves | O(n) | n = number of pieces |
| Legal Move Validation | O(m × k) | m = possible moves, k = opponent pieces |
| Check Detection | O(n) | n = opponent pieces |
| Make/Undo Move | O(1) | Constant time operation |

### Memory Management
- Move history stored as list
- Board state updated in-place
- Undo recreates previous state from move metadata
- PGN files provide persistent storage

## 📝 Examples

### Example Game Session

```
=================================
   Welcome to Mo-Lights Chess!
=================================

--- MAIN MENU ---
1. New Game
2. Load Game
3. Quit

Enter your choice (1-3): 1

=================================
         GAME STARTED
=================================

WHITE's turn:
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

Enter command: e4
Move made: e4

BLACK's turn:
  ┌────────────────┐
8 │♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜ │
7 │♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ │
6 │· · · · · · · · │
5 │· · · · · · · · │
4 │· · · · ♙ · · · │
3 │· · · · · · · · │
2 │♙ ♙ ♙ ♙ · ♙ ♙ ♙ │
1 │♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ │
  └────────────────┘
   a b c d e f g h

Enter command: e5
Move made: e5

WHITE's turn:
  ┌────────────────┐
8 │♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜ │
7 │♟ ♟ ♟ ♟ · ♟ ♟ ♟ │
6 │· · · · · · · · │
5 │· · · · ♟ · · · │
4 │· · · · ♙ · · · │
3 │· · · · · · · · │
2 │♙ ♙ ♙ ♙ · ♙ ♙ ♙ │
1 │♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ │
  └────────────────┘
   a b c d e f g h

Enter command: Nf3
Move made: Nf3
```

### Saving a Game

```
Enter command: save mygame
Game saved to mygame.pgn
```

### Loading a Game

```
--- MAIN MENU ---
1. New Game
2. Load Game
3. Quit

Enter your choice (1-3): 2
Enter filename to load: mygame
Game loaded successfully!
Moves loaded: 15
```

### Viewing Legal Moves

```
Enter command: moves

Legal moves for white:
a2->a3 a2->a4 b2->b3 b2->b4 c2->c3 c2->c4 d2->d3 d2->d4 
f2->f3 f2->f4 g2->g3 g2->g4 h2->h3 h2->h4 b1->a3 b1->c3 
g1->f3 g1->h3 
(Total: 18 moves)
```

### Special Moves Example

#### Castling
```
Enter command: O-O
Move made: O-O
Kingside castling!
```

#### En Passant
```
Enter command: exd6
Move made: exd6
En passant!
Captured Pawn!
```

#### Pawn Promotion
```
Enter command: e8=Q
Promote to (Q/R/B/N): Q
Move made: e8=Q
Pawn promoted to Queen!
```

## 🎓 Learning Resources

This project implements:
- **Object-Oriented Design**: Inheritance, polymorphism, encapsulation
- **Algorithms**: Move generation, game tree search (implicit), state validation
- **Data Structures**: 2D arrays, lists, move history
- **File I/O**: PGN parsing and generation
- **Exception Handling**: Custom exceptions for game states
- **Testing**: PGN test files for validation

## 🤝 Contributing

Feel free to fork this project and submit pull requests for:
- Bug fixes
- Performance improvements
- Additional features (AI opponent, GUI, time controls, etc.)
- Documentation improvements

## 📄 License

This project is open source and available for educational purposes.

## 🎉 Acknowledgments

- Chess rules from FIDE (World Chess Federation)
- PGN format specification
- Unicode chess symbols for terminal display

---

**Enjoy playing Mo-Lights Chess! ♟️**
