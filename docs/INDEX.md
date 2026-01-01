# Mo-Lights Chess - Documentation Index

Welcome to the complete documentation for Mo-Lights Chess!

## 📖 Available Documentation

### For Users

#### [Installation Guide](INSTALLATION.md)
**Start here if you're setting up the game for the first time.**

- System requirements
- Step-by-step installation for Windows, macOS, and Linux
- Java installation instructions
- Troubleshooting common installation issues
- Creating shortcuts and aliases

#### [User Guide](USER_GUIDE.md)
**Learn how to play and use all features.**

- Getting started tutorial
- Understanding the interface
- Making moves (algebraic and coordinate notation)
- Special moves (castling, en passant, promotion)
- Game commands (undo, save, load, resign, draw)
- Chess rules reference
- Tips and strategies
- FAQ

### For Developers

#### [API Documentation](API.md)
**Complete reference for all classes, methods, and data structures.**

- Core classes (ChessEngine, Board, Square, Move, GameResult)
- Piece classes (Pawn, Rook, Knight, Bishop, Queen, King)
- Utility classes (AlgebraicNotationParser, PGNReader, PGNWriter)
- Exception hierarchy
- Usage examples
- Architecture notes

#### [Developer Guide](DEVELOPER_GUIDE.md)
**Learn the codebase and contribute to the project.**

- Project structure
- Architecture overview
- Design patterns used
- How to add new features
- Code style guidelines
- Testing procedures
- Debugging tips
- Performance considerations

---

## 🎯 Quick Navigation

### I want to...

| Goal | Document | Section |
|------|----------|---------|
| **Install the game** | [Installation Guide](INSTALLATION.md) | [Quick Start](INSTALLATION.md#quick-start) |
| **Learn to play** | [User Guide](USER_GUIDE.md) | [Playing Your First Game](USER_GUIDE.md#playing-your-first-game) |
| **Fix installation errors** | [Installation Guide](INSTALLATION.md) | [Troubleshooting](INSTALLATION.md#troubleshooting) |
| **Understand algebraic notation** | [User Guide](USER_GUIDE.md) | [Making Moves](USER_GUIDE.md#making-moves) |
| **Save/load games** | [User Guide](USER_GUIDE.md) | [Saving and Loading Games](USER_GUIDE.md#saving-and-loading-games) |
| **Understand the code** | [Developer Guide](DEVELOPER_GUIDE.md) | [Architecture Overview](DEVELOPER_GUIDE.md#architecture-overview) |
| **Add a new feature** | [Developer Guide](DEVELOPER_GUIDE.md) | [Adding New Features](DEVELOPER_GUIDE.md#adding-new-features) |
| **Use the API** | [API Documentation](API.md) | [Core Classes](API.md#core-classes) |
| **Contribute code** | [Developer Guide](DEVELOPER_GUIDE.md) | [Contributing](DEVELOPER_GUIDE.md#contributing) |

---

## 📚 Documentation Structure

```
docs/
├── INDEX.md              ← You are here
├── INSTALLATION.md       ← Setup and installation
├── USER_GUIDE.md         ← How to play
├── API.md                ← Technical reference
└── DEVELOPER_GUIDE.md    ← Development guide
```

---

## 🎓 Learning Paths

### Path 1: New Player
**Goal:** Learn to play chess with Mo-Lights Chess

1. Start with [Installation Guide](INSTALLATION.md) - Get the game running
2. Read [User Guide - Getting Started](USER_GUIDE.md#getting-started)
3. Play through [Your First Game](USER_GUIDE.md#playing-your-first-game)
4. Learn [Making Moves](USER_GUIDE.md#making-moves)
5. Explore [Special Moves](USER_GUIDE.md#special-moves)
6. Reference [Chess Rules](USER_GUIDE.md#chess-rules-reference) as needed

### Path 2: Experienced Chess Player
**Goal:** Master the interface quickly

1. [Quick Start](../README.md#quick-start) - Install and run
2. [Making Moves](USER_GUIDE.md#making-moves) - Learn notation formats
3. [Game Commands](USER_GUIDE.md#game-commands) - Available commands
4. [Saving and Loading Games](USER_GUIDE.md#saving-and-loading-games) - PGN support

### Path 3: Code Contributor
**Goal:** Understand and modify the codebase

1. [Installation Guide](INSTALLATION.md) - Set up development environment
2. [Developer Guide - Project Structure](DEVELOPER_GUIDE.md#project-structure)
3. [Architecture Overview](DEVELOPER_GUIDE.md#architecture-overview)
4. [Key Design Patterns](DEVELOPER_GUIDE.md#key-design-patterns)
5. [API Documentation](API.md) - Understand the classes
6. [Adding New Features](DEVELOPER_GUIDE.md#adding-new-features)
7. [Contributing](DEVELOPER_GUIDE.md#contributing) - PR process

### Path 4: API User
**Goal:** Build extensions or integrations

1. [API Documentation](API.md) - Complete reference
2. [ChessEngine class](API.md#chessengine) - Main interface
3. [Move Validation](API.md#move-generation) - How moves work
4. [Examples](API.md#usage-examples) - Code samples
5. [Developer Guide - Architecture](DEVELOPER_GUIDE.md#architecture-overview) - Design context

---

## 🔍 Common Topics

### Installation Issues

- [Java not found](INSTALLATION.md#javac-command-not-found-or-java-command-not-found)
- [Compilation errors](INSTALLATION.md#compilation-errors)
- [Chess pieces display as boxes](INSTALLATION.md#chess-pieces-display-as-boxes)
- [Class not found errors](INSTALLATION.md#class-not-found-main)

### Gameplay Questions

- [How to make moves](USER_GUIDE.md#making-moves)
- [How to castle](USER_GUIDE.md#castling)
- [How to capture en passant](USER_GUIDE.md#en-passant)
- [How to promote pawns](USER_GUIDE.md#pawn-promotion)
- [How to offer draws](USER_GUIDE.md#draw-offers)

### Technical Details

- [Move validation algorithm](DEVELOPER_GUIDE.md#move-validation-pipeline)
- [Board representation](API.md#board-coordinate-system)
- [Piece movement implementation](DEVELOPER_GUIDE.md#template-method-pattern-piece-movement)
- [PGN file format](API.md#pgnreader)

### Development Tasks

- [Adding a new piece type](DEVELOPER_GUIDE.md#adding-a-new-piece-type)
- [Adding a new command](DEVELOPER_GUIDE.md#task-2-add-a-new-command)
- [Debugging move validation](DEVELOPER_GUIDE.md#debugging-tips)
- [Testing changes](DEVELOPER_GUIDE.md#testing)

---

## 📊 Documentation Coverage

| Topic | Coverage | Documents |
|-------|----------|-----------|
| **Installation** | ✅ Complete | Installation Guide |
| **Basic Usage** | ✅ Complete | User Guide |
| **Advanced Usage** | ✅ Complete | User Guide |
| **API Reference** | ✅ Complete | API Documentation |
| **Architecture** | ✅ Complete | Developer Guide, API Docs |
| **Development** | ✅ Complete | Developer Guide |
| **Testing** | ✅ Complete | Developer Guide |
| **Troubleshooting** | ✅ Complete | Installation Guide, User Guide |
| **Examples** | ✅ Complete | All guides |

---

## 💡 Tips for Using This Documentation

### Search Tips

Most documentation readers (including GitHub) support searching:
- Press `Ctrl+F` (Windows/Linux) or `Cmd+F` (Mac)
- Search for keywords like "castling", "checkmate", "API", etc.

### Reading Order

**For users:** Installation Guide → User Guide → FAQ  
**For developers:** User Guide → API Docs → Developer Guide  
**For contributors:** Developer Guide → API Docs → Code

### Getting Help

If you can't find what you need:
1. Check the [FAQ](USER_GUIDE.md#frequently-asked-questions)
2. Search the documentation (Ctrl+F)
3. Check [Troubleshooting](INSTALLATION.md#troubleshooting) sections
4. Open a GitHub issue with your question

---

## 🔄 Documentation Updates

This documentation is maintained alongside the code. When features are added or changed, the documentation is updated.

**Last Updated:** January 2025  
**Version:** 1.0  
**Covers:** Mo-Lights Chess v1.0

---

## 🤝 Contributing to Documentation

Found an error or want to improve the docs?

1. Fork the repository
2. Edit the markdown files in `docs/`
3. Submit a pull request
4. Follow the [Developer Guide](DEVELOPER_GUIDE.md#contributing) for PR process

**Good documentation contributions:**
- Fixing typos or unclear explanations
- Adding examples
- Improving organization
- Adding missing information
- Updating outdated content

---

## 📝 Document Summaries

### Installation Guide (INSTALLATION.md)
- **Length:** ~500 lines
- **Reading Time:** 15-20 minutes
- **Key Topics:** Java installation, compilation, running the game, troubleshooting
- **Audience:** All users (new and experienced)

### User Guide (USER_GUIDE.md)
- **Length:** ~800 lines
- **Reading Time:** 30-40 minutes
- **Key Topics:** Playing games, notation, commands, rules, strategies
- **Audience:** Players (beginners to advanced)

### API Documentation (API.md)
- **Length:** ~700 lines
- **Reading Time:** 45-60 minutes
- **Key Topics:** Classes, methods, data structures, usage examples
- **Audience:** Developers and API users

### Developer Guide (DEVELOPER_GUIDE.md)
- **Length:** ~650 lines
- **Reading Time:** 45-60 minutes
- **Key Topics:** Architecture, design, development, testing, contributing
- **Audience:** Contributors and maintainers

---

## 🎯 Documentation Goals

This documentation aims to:

✅ Help users install and play the game quickly  
✅ Explain all features thoroughly  
✅ Provide technical reference for developers  
✅ Enable easy contribution to the project  
✅ Minimize support requests through comprehensive FAQs  
✅ Serve as a learning resource for chess and Java programming  

---

**Happy reading, and enjoy Mo-Lights Chess!** ♟️
