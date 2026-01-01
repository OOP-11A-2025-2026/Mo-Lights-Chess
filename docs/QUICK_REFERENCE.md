# Quick Reference Card - Mo-Lights Chess

## ⚡ Quick Commands

```bash
# Compile
javac *.java pieces/*.java exceptions/*.java

# Run
java Main

# Clean
rm *.class pieces/*.class exceptions/*.class
```

---

## 🎮 Game Commands

| Command | Action |
|---------|--------|
| `e4` | Move pawn to e4 |
| `Nf3` | Move knight to f3 |
| `Bxe5` | Bishop captures on e5 |
| `O-O` | Castle kingside |
| `O-O-O` | Castle queenside |
| `e8=Q` | Promote pawn to queen |
| `e2 e4` | Move from e2 to e4 (coordinate) |
| `moves` | Show all legal moves |
| `undo` | Take back last move |
| `save mygame` | Save to mygame.pgn |
| `resign` | Resign the game |
| `draw` | Offer draw |
| `accept` | Accept draw offer |
| `decline` | Decline draw offer |
| `help` | Show help |
| `menu` | Return to main menu |

---

## ♟️ Piece Symbols & Letters

| Piece | White | Black | Letter |
|-------|-------|-------|--------|
| King | ♔ | ♚ | K |
| Queen | ♕ | ♛ | Q |
| Rook | ♖ | ♜ | R |
| Bishop | ♗ | ♝ | B |
| Knight | ♘ | ♞ | N |
| Pawn | ♙ | ♟ | (none) |

---

## 🗺️ Board Coordinates

```
  a b c d e f g h  ← Files
8 □ ■ □ ■ □ ■ □ ■  ← Ranks
7 ■ □ ■ □ ■ □ ■ □
6 □ ■ □ ■ □ ■ □ ■
5 ■ □ ■ □ ■ □ ■ □
4 □ ■ □ ■ □ ■ □ ■
3 ■ □ ■ □ ■ □ ■ □
2 □ ■ □ ■ □ ■ □ ■
1 ■ □ ■ □ ■ □ ■ □

Examples:
• e4 = 5th file, 4th rank
• a1 = White's queenside rook starting square
• h8 = Black's kingside rook starting square
```

---

## 📝 Notation Examples

### Algebraic Notation

| Notation | Meaning |
|----------|---------|
| `e4` | Pawn to e4 |
| `d5` | Pawn to d5 |
| `Nf3` | Knight to f3 |
| `Bc4` | Bishop to c4 |
| `Qh5` | Queen to h5 |
| `Bxe5` | Bishop captures on e5 |
| `Nxd4` | Knight captures on d4 |
| `exd5` | e-pawn captures on d5 |
| `O-O` | Kingside castling |
| `O-O-O` | Queenside castling |
| `e8=Q` | Promote to queen |
| `e8=R` | Promote to rook |
| `e8=B` | Promote to bishop |
| `e8=N` | Promote to knight |
| `Nbd7` | Knight from b-file to d7 |
| `R1e8` | Rook from rank 1 to e8 |
| `Qh4e1` | Queen from h4 to e1 |

### Coordinate Notation

| Coordinate | Algebraic Equivalent |
|------------|---------------------|
| `e2 e4` | `e4` |
| `g1 f3` | `Nf3` |
| `f1 c4` | `Bc4` |
| `d1 h5` | `Qh5` |
| `f1 e5` | `Bxe5` (if capture) |

---

## 🎯 Special Moves

### Castling

**Kingside (O-O):**
- King moves from e1→g1 (White) or e8→g8 (Black)
- Rook moves from h1→f1 (White) or h8→f8 (Black)

**Queenside (O-O-O):**
- King moves from e1→c1 (White) or e8→c8 (Black)
- Rook moves from a1→d1 (White) or a8→d8 (Black)

**Requirements:**
- ✓ King hasn't moved
- ✓ Rook hasn't moved
- ✓ No pieces between king and rook
- ✓ King not in check
- ✓ King doesn't pass through check
- ✓ King doesn't end in check

### En Passant

**White's en passant:**
```
5 │· · · ♟ ♙ · · · │  Your pawn on e5
4 │· · · · · · · · │  Black plays d7-d5
```
You can capture: `exd6`

**Requirements:**
- ✓ Your pawn is on 5th rank (White) or 4th rank (Black)
- ✓ Enemy pawn just moved 2 squares
- ✓ Enemy pawn is adjacent to your pawn
- ✓ Must capture immediately (next turn)

### Pawn Promotion

When pawn reaches opposite end:
- `e8=Q` → Queen (recommended)
- `e8=R` → Rook
- `e8=B` → Bishop
- `e8=N` → Knight

---

## 🎲 Game States

| State | Description | Result |
|-------|-------------|--------|
| **Check** | King is attacked | Must escape check |
| **Checkmate** | King in check, no escape | Game over (you lose) |
| **Stalemate** | No legal moves, not in check | Draw |
| **Resignation** | Player gives up | Other player wins |
| **Draw Agreement** | Both players agree | Draw |

---

## 🔧 Troubleshooting

### Common Errors

| Error | Solution |
|-------|----------|
| "javac: command not found" | Install Java JDK and add to PATH |
| "Illegal move!" | Use `moves` to see legal options |
| "No piece on [square]" | Check if you typed the right square |
| "It's [color]'s turn!" | Wrong player is trying to move |
| Pieces show as boxes | Enable UTF-8 in terminal |
| "King not found" | Internal error - use `undo` |

### Quick Fixes

**Can't compile:**
```bash
# Make sure you're in project root
ls Main.java  # Should exist

# Compile from root directory
javac *.java pieces/*.java exceptions/*.java
```

**Can't run:**
```bash
# Check if compiled
ls Main.class  # Should exist

# Run from same directory
java Main
```

**Chess pieces broken (Windows):**
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
java Main
```

---

## 📊 Piece Values

Standard piece values for evaluation:

| Piece | Value | Strategy |
|-------|-------|----------|
| Pawn | 1 | Promote when possible |
| Knight | 3 | Good in closed positions |
| Bishop | 3 | Good in open positions |
| Rook | 5 | Control open files |
| Queen | 9 | Most powerful piece |
| King | ∞ | Protect at all costs |

---

## 🎓 Opening Principles

1. **Control the center** (e4, d4, e5, d5)
2. **Develop pieces** (knights and bishops)
3. **Castle early** (safety)
4. **Don't move same piece twice** (in opening)
5. **Connect rooks** (get them on open files)

---

## 📁 File Locations

```
Mo-Lights-Chess/
├── Main.java           ← Entry point
├── ChessEngine.java    ← Game logic
├── Board.java          ← Board state
├── *.class            ← Compiled files
├── *.pgn              ← Saved games
└── docs/
    ├── INDEX.md
    ├── INSTALLATION.md
    ├── USER_GUIDE.md
    ├── API.md
    └── DEVELOPER_GUIDE.md
```

---

## 🔗 Quick Links

- **Full documentation:** [docs/INDEX.md](INDEX.md)
- **Installation help:** [docs/INSTALLATION.md](INSTALLATION.md)
- **How to play:** [docs/USER_GUIDE.md](USER_GUIDE.md)
- **API reference:** [docs/API.md](API.md)
- **Development:** [docs/DEVELOPER_GUIDE.md](DEVELOPER_GUIDE.md)

---

## 💾 Saving/Loading

**Save:**
```
Enter command: save mygame
Game saved to mygame.pgn
```

**Load (from main menu):**
```
Enter your choice (1-3): 2
Enter filename to load: mygame
```

---

## 🎯 Tips

- **Learning moves?** Use `moves` command frequently
- **Made a mistake?** Use `undo` to take it back
- **Practice both sides** to improve
- **Save interesting positions** for later study
- **Start with pawns and knights** before moving bishops/queen

---

**Print this page for quick reference while playing!** ♟️
