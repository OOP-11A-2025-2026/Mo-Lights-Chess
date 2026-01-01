# Mo-Lights Chess - User Guide

## Table of Contents
- [Introduction](#introduction)
- [Getting Started](#getting-started)
- [Playing Your First Game](#playing-your-first-game)
- [Understanding the Interface](#understanding-the-interface)
- [Making Moves](#making-moves)
- [Special Moves](#special-moves)
- [Game Commands](#game-commands)
- [Saving and Loading Games](#saving-and-loading-games)
- [Chess Rules Reference](#chess-rules-reference)
- [Tips and Strategies](#tips-and-strategies)
- [Troubleshooting](#troubleshooting)
- [Frequently Asked Questions](#frequently-asked-questions)

---

## Introduction

Welcome to Mo-Lights Chess! This is a fully-featured chess game that runs in your terminal/command prompt. It supports all standard chess rules, special moves like castling and en passant, and can save/load games in standard PGN format.

### Features

- ✅ Complete chess rules implementation
- ✅ Beautiful Unicode piece display
- ✅ Algebraic notation support
- ✅ Save and load games (PGN format)
- ✅ Undo moves
- ✅ Show all legal moves
- ✅ Check, checkmate, and stalemate detection
- ✅ Draw offers and resignations
- ✅ Move history tracking

---

## Getting Started

### Installation

1. **Ensure Java is installed**
```bash
java -version
```
If not installed, download from [java.com](https://www.java.com/) or [OpenJDK](https://openjdk.org/).

2. **Download Mo-Lights Chess**
   - Clone from GitHub: `git clone <repository-url>`
   - Or download and extract the ZIP file

3. **Navigate to the folder**
```bash
cd Mo-Lights-Chess
```

4. **Compile the program** (first time only)
```bash
javac *.java pieces/*.java exceptions/*.java
```

5. **Run the game**
```bash
java Main
```

---

## Playing Your First Game

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

Enter your choice (1-3):
```

Type `1` and press Enter to start a new game.

### The Opening Position

You'll see the chess board displayed with Unicode pieces:

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

White moves first. Let's move the king's pawn forward two squares:

```
Enter command: e4
```

Press Enter. The board updates:

```
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
```

Now it's Black's turn. Black responds with the same move:

```
Enter command: e5
```

Congratulations! You've made your first moves. Continue playing by entering moves for White and Black alternately.

---

## Understanding the Interface

### Board Coordinates

The chess board uses standard algebraic notation:

- **Files** (columns): a-h (left to right)
- **Ranks** (rows): 1-8 (bottom to top)
- Each square has a unique name: `file + rank`

Examples:
- `e4` = 5th file (e), 4th rank
- `a1` = White's left rook starting position
- `h8` = Black's right rook starting position

### Reading the Board

```
    a b c d e f g h  ← Files (columns)
  ┌────────────────┐
8 │♜ ♞ ♝ ♛ ♚ ♝ ♞ ♜ │ ← Rank 8 (Black's back rank)
7 │♟ ♟ ♟ ♟ ♟ ♟ ♟ ♟ │
6 │· · · · · · · · │
5 │· · · · · · · · │
4 │· · · · · · · · │
3 │· · · · · · · · │
2 │♙ ♙ ♙ ♙ ♙ ♙ ♙ ♙ │
1 │♖ ♘ ♗ ♕ ♔ ♗ ♘ ♖ │ ← Rank 1 (White's back rank)
  └────────────────┘
   a b c d e f g h
```

### Game State Indicators

The game shows important information:

```
WHITE's turn:        ← Current player
*** CHECK! ***       ← King is in check
*** BLACK has offered a draw ***  ← Draw offer pending
```

---

## Making Moves

### Algebraic Notation (Recommended)

Algebraic notation is the standard way to record chess moves.

#### Pawn Moves
Simply write the destination square:
- `e4` - Move pawn to e4
- `d5` - Move pawn to d5

#### Piece Moves
Write the piece letter + destination:
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

#### Captures
Add `x` before the destination:
- `Bxe5` - Bishop captures on e5
- `Nxd4` - Knight captures on d4
- `exd5` - Pawn on e-file captures on d5

#### Disambiguation
When two pieces of the same type can move to the same square, specify which one:
- `Nbd7` - Knight from b-file to d7
- `R1e8` - Rook from rank 1 to e8
- `Qh4e1` - Queen from h4 to e1

### Coordinate Notation (Alternative)

You can also use "from-to" notation:

```
Enter command: e2 e4
```

This moves the piece from e2 to e4. This is useful if you're not familiar with algebraic notation.

### Examples

| Algebraic | Coordinate | Description |
|-----------|------------|-------------|
| `e4` | `e2 e4` | Pawn to e4 |
| `Nf3` | `g1 f3` | Knight to f3 |
| `Bxc6` | `b5 c6` | Bishop captures on c6 |
| `O-O` | (special) | Kingside castling |
| `e8=Q` | `e7 e8` (then choose Q) | Pawn promotion to queen |

---

## Special Moves

### Castling

Castling is a special move involving the king and rook.

**Kingside Castling (O-O):**
```
Enter command: O-O
```
or
```
Enter command: 0-0
```

The king moves two squares toward the kingside rook, and the rook jumps over to the other side of the king.

Before:
```
♔ · · · · · · ♖
```

After:
```
· · · · · ♖ ♔ ·
```

**Queenside Castling (O-O-O):**
```
Enter command: O-O-O
```
or
```
Enter command: 0-0-0
```

The king moves two squares toward the queenside rook, and the rook jumps over.

Before:
```
♖ · · · ♔ · · ·
```

After:
```
· · ♔ ♖ · · · ·
```

**Requirements for Castling:**
- Neither the king nor the rook has moved before
- No pieces between the king and rook
- King is not in check
- King doesn't move through a square that's under attack
- King doesn't end up in check

### En Passant

En passant is a special pawn capture.

**Situation:** Your pawn is on the 5th rank (for White) or 4th rank (for Black). An enemy pawn on an adjacent file moves forward two squares, landing beside your pawn.

**You can capture it** as if it had only moved one square!

Example:
```
5 │· · · ♟ ♙ · · · │  ← Your white pawn on e5
4 │· · · · · · · · │  ← Black pawn will move to d5
```

Black plays `d5` (moving from d7):
```
5 │· · · ♟ ♙ · · · │
4 │· · · ♟ · · · · │
```

You can now play `exd6` (en passant capture):
```
Enter command: exd6
```

Result:
```
6 │· · · ♙ · · · · │  ← Your pawn moves diagonally
5 │· · · · · · · · │
4 │· · · · · · · · │  ← Black pawn is captured
```

**Important:** En passant must be done immediately on your next turn, or the opportunity is lost!

### Pawn Promotion

When a pawn reaches the opposite end of the board, it must be promoted to another piece.

Example: Your white pawn is on e7 and you want to move it to e8:

```
Enter command: e8=Q
```

The `=Q` specifies promotion to a queen. You can promote to:
- Q = Queen (most common)
- R = Rook
- B = Bishop
- N = Knight

If you don't specify, the game will ask:
```
Enter command: e8
Promote to (Q/R/B/N): Q
```

**Tip:** Almost always promote to a queen, as it's the most powerful piece!

---

## Game Commands

### During a Game

| Command | Description | Example |
|---------|-------------|---------|
| `<move>` | Make a move | `e4`, `Nf3`, `Bxe5` |
| `moves` | Show all legal moves | `moves` |
| `undo` | Undo the last move | `undo` |
| `save <filename>` | Save game to file | `save mygame` |
| `resign` | Resign the game | `resign` |
| `draw` | Offer a draw | `draw` |
| `accept` | Accept draw offer | `accept` |
| `decline` | Decline draw offer | `decline` |
| `help` | Show command list | `help` |
| `menu` | Return to main menu | `menu` |

### Showing Legal Moves

Not sure what to do? Ask for legal moves:

```
Enter command: moves

Legal moves for white:
a2->a3 a2->a4 b2->b3 b2->b4 c2->c3 c2->c4 d2->d3 d2->d4 
e2->e3 e2->e4 f2->f3 f2->f4 g2->g3 g2->g4 h2->h3 h2->h4 
b1->a3 b1->c3 g1->f3 g1->h3 
(Total: 20 moves)
```

This shows all legal moves in "from->to" format.

### Undoing Moves

Made a mistake? Undo the last move:

```
Enter command: undo
Move undone!
```

This takes back both your move and your opponent's last move. You can undo multiple times to go back further in the game.

### Resigning

If you want to give up:

```
Enter command: resign
Are you sure you want to resign? (yes/no): yes
BLACK wins by resignation!
```

### Draw Offers

Either player can offer a draw:

```
Enter command: draw
Draw offered to BLACK!
```

On Black's turn, they'll see:
```
*** WHITE has offered a draw ***
Type 'accept' to accept or 'decline' to decline
```

To accept:
```
Enter command: accept
Draw accepted! Game ends in a draw.
```

To decline:
```
Enter command: decline
Draw declined. Game continues!
```

---

## Saving and Loading Games

### Saving a Game

To save your current game:

```
Enter command: save mygame
Game saved to mygame.pgn
```

The `.pgn` extension is added automatically if you don't include it.

### Loading a Game

From the main menu, select option 2:

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

The game will replay all the moves and you can continue from where you left off.

### PGN Format

PGN (Portable Game Notation) is the standard format for chess games. Your saved games can be opened in other chess programs!

Example PGN file:
```pgn
[Event "Casual Game"]
[Site "Local"]
[Date "2025.01.01"]
[Round "1"]
[White "Player1"]
[Black "Player2"]
[Result "*"]

1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O *
```

---

## Chess Rules Reference

### Piece Movement

#### Pawn (♙ ♟)
- Moves forward one square
- On first move, can move forward two squares
- Captures diagonally forward one square
- Can capture en passant
- Must be promoted when reaching the opposite end

#### Rook (♖ ♜)
- Moves any number of squares horizontally or vertically
- Cannot jump over pieces
- Used in castling

#### Knight (♘ ♞)
- Moves in an "L" shape: 2 squares in one direction, then 1 square perpendicular
- Can jump over other pieces
- The only piece that can jump

#### Bishop (♗ ♝)
- Moves any number of squares diagonally
- Cannot jump over pieces
- Each bishop stays on its starting color (light or dark)

#### Queen (♕ ♛)
- Moves any number of squares horizontally, vertically, or diagonally
- Combines rook and bishop movement
- The most powerful piece

#### King (♔ ♚)
- Moves one square in any direction
- Cannot move into check
- Used in castling
- **Most important piece** - if checkmated, you lose!

### Check, Checkmate, and Stalemate

#### Check
Your king is **in check** when an enemy piece is attacking it.

When in check, you must:
1. Move the king to safety, OR
2. Block the attack with another piece, OR
3. Capture the attacking piece

The game shows:
```
*** CHECK! ***
```

#### Checkmate
**Checkmate** means:
- Your king is in check
- There's no legal move to get out of check
- **You lose the game**

```
=================================
         CHECKMATE!
      BLACK WINS!
=================================
```

#### Stalemate
**Stalemate** means:
- Your king is NOT in check
- You have no legal moves
- **The game is a draw** (neither player wins)

```
=================================
         STALEMATE!
        GAME IS A DRAW
=================================
```

### Other Ways the Game Can End

1. **Resignation** - A player gives up
2. **Draw Agreement** - Both players agree to a draw
3. **Insufficient Material** - Not enough pieces left to checkmate (not auto-detected in this version)
4. **Threefold Repetition** - Same position occurs three times (not auto-detected in this version)
5. **Fifty-Move Rule** - 50 moves without captures or pawn moves (not auto-detected in this version)

---

## Tips and Strategies

### For Beginners

1. **Control the Center**
   - Try to control squares e4, d4, e5, d5
   - Open with `e4` or `d4`

2. **Develop Your Pieces**
   - Get your knights and bishops out early
   - Don't move the same piece twice in the opening

3. **Castle Early**
   - Castling protects your king
   - Usually castle within the first 10 moves

4. **Protect Your King**
   - Keep pawns in front of your king
   - Don't expose your king to attacks

5. **Think Before You Move**
   - Use the `moves` command to see your options
   - Look for what your opponent threatens

6. **Value of Pieces**
   - Pawn = 1 point
   - Knight/Bishop = 3 points
   - Rook = 5 points
   - Queen = 9 points
   - King = priceless!

### Using the Program Effectively

1. **Use `moves` to Learn**
   ```
   Enter command: moves
   ```
   This shows all legal moves and helps you understand what's possible.

2. **Practice with `undo`**
   - Try a move
   - See the result
   - Use `undo` if you don't like it
   - Try a different move

3. **Save Important Games**
   - Save games where you learn something new
   - Load them later to review

4. **Play Both Sides**
   - This program lets you play both White and Black
   - Great for solo practice!

---

## Troubleshooting

### Common Issues

#### "Illegal move!" message

**Cause:** The move you entered isn't legal.

**Solutions:**
1. Check if it's your turn (White or Black?)
2. Verify the notation is correct
3. Use `moves` command to see legal moves
4. Remember: you can't leave your king in check!

Example:
```
Enter command: moves
```
Look for the move you want in the legal moves list.

#### Pieces display as boxes or question marks

**Cause:** Your terminal doesn't support Unicode.

**Solutions:**

**Windows PowerShell:**
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
```

**Windows CMD:**
```cmd
chcp 65001
```

**Mac/Linux:**
Usually works by default. Check your terminal settings for UTF-8 encoding.

#### Cannot castle

**Reasons:**
1. King or rook has already moved
2. Pieces are between king and rook
3. King is in check
4. King would move through or into check

**Verify:**
```
Enter command: moves
```
If castling isn't in the legal moves list, it's not currently legal.

#### "King not found" error

**Cause:** Internal error, board state corrupted.

**Solution:** This shouldn't happen in normal play. If it does:
1. Use `undo` to go back
2. If that doesn't work, save the game log and report the bug

### File Loading Issues

#### "File error: File not found"

**Cause:** The PGN file doesn't exist or is in a different folder.

**Solutions:**
1. Make sure the file is in the same folder as the program
2. Include the full path: `/Users/yourname/Documents/game.pgn`
3. Check the filename is spelled correctly

#### "Parse error" when loading PGN

**Cause:** The PGN file has invalid notation or illegal moves.

**Solutions:**
1. Ensure the PGN file is from a valid chess game
2. Check for typos in the move notation
3. Verify the PGN file wasn't corrupted

---

## Frequently Asked Questions

### General Questions

**Q: Can I play against the computer?**  
A: Not in the current version. This is a two-player game (you play both sides) or you can play with a friend taking turns.

**Q: Can I use this with a chess GUI?**  
A: Not currently. The program is text-based only.

**Q: Are all chess rules implemented?**  
A: Yes! All standard rules including:
- All piece movements
- Castling (both sides)
- En passant
- Pawn promotion
- Check, checkmate, stalemate
- Resignation and draws

**Q: What's NOT implemented?**  
A: Automatic draw detection for:
- Threefold repetition
- Fifty-move rule
- Insufficient material

These must be claimed manually using the `draw` command.

### Gameplay Questions

**Q: How do I know if I'm in check?**  
A: The game displays `*** CHECK! ***` when your king is in check.

**Q: Can I take back a move?**  
A: Yes! Use the `undo` command. It undoes the last move (yours and your opponent's).

**Q: What if two pieces can move to the same square?**  
A: Use disambiguation in algebraic notation:
- `Nbd7` - Knight from b-file
- `R1e8` - Rook from 1st rank
- Or use coordinate notation: `b1 d7`

**Q: How do I promote to something other than a queen?**  
A: Use `e8=R` for rook, `e8=B` for bishop, `e8=N` for knight. Or just type `e8` and you'll be prompted to choose.

### Technical Questions

**Q: What Java version do I need?**  
A: Java 11 or higher.

**Q: Can I run this on Mac/Linux/Windows?**  
A: Yes! It works on all platforms with Java installed.

**Q: Where are saved games stored?**  
A: In the same folder as the program, with a `.pgn` extension.

**Q: Can I edit PGN files?**  
A: Yes! They're plain text files. Open with any text editor.

**Q: How do I quit the game?**  
A: Type `menu` to go to the main menu, then select `3. Quit`. Or press Ctrl+C (force quit).

---

## Quick Reference Card

### Common Moves

| Action | Algebraic | Coordinate |
|--------|-----------|------------|
| Pawn forward | `e4` | `e2 e4` |
| Knight move | `Nf3` | `g1 f3` |
| Capture | `Bxe5` | `f1 e5` |
| Castle kingside | `O-O` | (special) |
| Castle queenside | `O-O-O` | (special) |
| Pawn promotion | `e8=Q` | `e7 e8` |
| En passant | `exd6` | `e5 d6` |

### Commands

| Command | Action |
|---------|--------|
| `moves` | Show legal moves |
| `undo` | Take back move |
| `save mygame` | Save game |
| `resign` | Give up |
| `draw` | Offer draw |
| `accept` | Accept draw |
| `decline` | Decline draw |
| `help` | Show commands |
| `menu` | Main menu |

### Piece Letters

| Symbol | Name | Letter |
|--------|------|--------|
| ♔ ♚ | King | K |
| ♕ ♛ | Queen | Q |
| ♖ ♜ | Rook | R |
| ♗ ♝ | Bishop | B |
| ♘ ♞ | Knight | N |
| ♙ ♟ | Pawn | (none) |

---

## Getting Help

If you encounter problems:

1. **Use the `help` command** in-game
2. **Check this guide** for detailed explanations
3. **Report bugs** on the GitHub issues page
4. **Read the FAQ** section above

---

**Have fun playing Mo-Lights Chess!** ♟️

May all your games be decisive victories! (Or honorable draws!)
