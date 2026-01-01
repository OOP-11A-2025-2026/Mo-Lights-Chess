# Installation Guide - Mo-Lights Chess

## Table of Contents
- [System Requirements](#system-requirements)
- [Quick Start](#quick-start)
- [Detailed Installation](#detailed-installation)
  - [Windows](#windows)
  - [macOS](#macos)
  - [Linux](#linux)
- [Running the Game](#running-the-game)
- [Building from Source](#building-from-source)
- [Troubleshooting](#troubleshooting)
- [Uninstallation](#uninstallation)

---

## System Requirements

### Minimum Requirements
- **Operating System:** Windows 7+, macOS 10.10+, or Linux (any modern distribution)
- **Java:** JDK 11 or higher
- **RAM:** 50 MB
- **Disk Space:** 5 MB
- **Display:** Terminal/Command Prompt with UTF-8 support (recommended for chess piece symbols)

### Recommended
- **Java:** JDK 17 or higher
- **Terminal:** Modern terminal with Unicode support
  - Windows: Windows Terminal or PowerShell
  - macOS: Terminal.app or iTerm2
  - Linux: GNOME Terminal, Konsole, or similar

---

## Quick Start

For experienced users who already have Java installed:

```bash
# 1. Clone the repository
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess

# 2. Compile
javac *.java pieces/*.java exceptions/*.java

# 3. Run
java Main
```

That's it! Skip to [Running the Game](#running-the-game) for gameplay instructions.

---

## Detailed Installation

### Windows

#### Step 1: Install Java

1. **Check if Java is installed:**
   - Press `Win + R`, type `cmd`, press Enter
   - Type `java -version` and press Enter

   If you see version information (e.g., "openjdk version 17"), Java is installed. **Skip to Step 2.**

2. **Download Java:**
   - Visit [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)
   - Or use OpenJDK: [https://adoptium.net/](https://adoptium.net/)
   - Download the **Windows x64 Installer**

3. **Install Java:**
   - Run the downloaded installer
   - Follow the installation wizard
   - Accept default options
   - Click "Install"
   - Wait for installation to complete

4. **Verify installation:**
   - Open a new Command Prompt (close any old ones)
   - Type `java -version`
   - You should see the Java version

   If it says "command not found," you may need to add Java to your PATH (see Troubleshooting).

#### Step 2: Download Mo-Lights Chess

**Option A: Using Git**
1. Install Git from [https://git-scm.com/download/win](https://git-scm.com/download/win)
2. Open Command Prompt
3. Navigate to where you want to install:
   ```cmd
   cd C:\Users\YourName\Documents
   ```
4. Clone the repository:
   ```cmd
   git clone https://github.com/yourusername/Mo-Lights-Chess.git
   cd Mo-Lights-Chess
   ```

**Option B: Download ZIP**
1. Go to the GitHub repository page
2. Click the green "Code" button
3. Click "Download ZIP"
4. Extract the ZIP file to your desired location
5. Open Command Prompt and navigate to the extracted folder:
   ```cmd
   cd C:\Users\YourName\Documents\Mo-Lights-Chess
   ```

#### Step 3: Compile the Game

In the Command Prompt (inside the Mo-Lights-Chess folder):

```cmd
javac *.java pieces/*.java exceptions/*.java
```

You should see no errors. If compilation succeeds, you'll see no output (which is good!).

#### Step 4: Run the Game

```cmd
java Main
```

The game should start!

#### Optional: Enable Unicode Chess Pieces

For proper chess piece display:

**PowerShell (Recommended):**
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
java Main
```

**Command Prompt:**
```cmd
chcp 65001
java Main
```

---

### macOS

#### Step 1: Install Java

1. **Check if Java is installed:**
   - Open Terminal (Applications → Utilities → Terminal)
   - Type `java -version` and press Enter

   If you see version information, Java is installed. **Skip to Step 2.**

2. **Install Java using Homebrew (Recommended):**

   First, install Homebrew if you don't have it:
   ```bash
   /bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
   ```

   Then install Java:
   ```bash
   brew install openjdk@17
   ```

   Link it:
   ```bash
   sudo ln -sfn $(brew --prefix)/opt/openjdk@17/libexec/openjdk.jdk /Library/Java/JavaVirtualMachines/openjdk-17.jdk
   ```

3. **Alternative: Manual Installation**
   - Visit [https://www.oracle.com/java/technologies/downloads/](https://www.oracle.com/java/technologies/downloads/)
   - Download the macOS installer (DMG file)
   - Double-click to mount the DMG
   - Run the installer package
   - Follow installation instructions

4. **Verify installation:**
   ```bash
   java -version
   ```

#### Step 2: Download Mo-Lights Chess

**Option A: Using Git (Recommended)**

Git is pre-installed on macOS. Open Terminal and run:

```bash
cd ~/Documents
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess
```

**Option B: Download ZIP**
1. Download from GitHub (click Code → Download ZIP)
2. Double-click the ZIP to extract
3. Open Terminal and navigate to the folder:
   ```bash
   cd ~/Downloads/Mo-Lights-Chess
   ```

#### Step 3: Compile the Game

```bash
javac *.java pieces/*.java exceptions/*.java
```

No errors = success!

#### Step 4: Run the Game

```bash
java Main
```

The game should start with Unicode chess pieces displayed correctly.

**Note:** macOS Terminal supports UTF-8 by default, so chess pieces should display properly.

---

### Linux

#### Step 1: Install Java

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

**Other distributions:**
Use your package manager to install `openjdk-17-jdk` or similar.

**Verify installation:**
```bash
java -version
javac -version
```

Both should show version 11 or higher.

#### Step 2: Download Mo-Lights Chess

**Using Git (Recommended):**

```bash
cd ~
git clone https://github.com/yourusername/Mo-Lights-Chess.git
cd Mo-Lights-Chess
```

**Download ZIP:**
```bash
cd ~/Downloads
wget https://github.com/yourusername/Mo-Lights-Chess/archive/refs/heads/main.zip
unzip main.zip
cd Mo-Lights-Chess-main
```

#### Step 3: Compile the Game

```bash
javac *.java pieces/*.java exceptions/*.java
```

#### Step 4: Run the Game

```bash
java Main
```

**Note:** Most Linux terminals support UTF-8 by default. If chess pieces don't display correctly, ensure your terminal locale is set to UTF-8:

```bash
echo $LANG
```

Should output something like `en_US.UTF-8`. If not, set it:

```bash
export LANG=en_US.UTF-8
```

---

## Running the Game

### First Run

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

Type `1` and press Enter to start your first game!

### Creating a Shortcut

#### Windows

Create a batch file (`chess.bat`) in the Mo-Lights-Chess folder:

```batch
@echo off
cd /d "%~dp0"
chcp 65001 > nul
java Main
pause
```

Double-click this file to run the game.

#### macOS/Linux

Create a shell script (`chess.sh`) in the Mo-Lights-Chess folder:

```bash
#!/bin/bash
cd "$(dirname "$0")"
java Main
```

Make it executable:
```bash
chmod +x chess.sh
```

Run it:
```bash
./chess.sh
```

Or add an alias to your `~/.bashrc` or `~/.zshrc`:
```bash
alias chess='cd ~/path/to/Mo-Lights-Chess && java Main'
```

Then just type `chess` in any terminal!

---

## Building from Source

### Standard Compilation

```bash
javac *.java pieces/*.java exceptions/*.java
```

This creates `.class` files in the same directories as the source files.

### Compiling with Output Directory

To organize compiled files separately:

```bash
# Create output directory
mkdir -p bin

# Compile with output directory
javac -d bin *.java pieces/*.java exceptions/*.java

# Run from bin directory
cd bin
java Main
cd ..
```

### Creating a JAR File

To package the game into a single executable JAR:

1. **Compile the code:**
   ```bash
   javac *.java pieces/*.java exceptions/*.java
   ```

2. **Create a manifest file** (`MANIFEST.MF`):
   ```
   Manifest-Version: 1.0
   Main-Class: Main
   ```

3. **Create the JAR:**
   ```bash
   jar cvfm Chess.jar MANIFEST.MF *.class pieces/*.class exceptions/*.class
   ```

4. **Run the JAR:**
   ```bash
   java -jar Chess.jar
   ```

### Building with Maven (Advanced)

Create a `pom.xml` file:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.molights</groupId>
    <artifactId>chess</artifactId>
    <version>1.0.0</version>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>Main</mainClass>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

Build with Maven:
```bash
mvn clean package
java -jar target/chess-1.0.0.jar
```

---

## Troubleshooting

### "javac: command not found" or "java: command not found"

**Problem:** Java is not in your system PATH.

**Windows Solution:**
1. Find your Java installation (usually `C:\Program Files\Java\jdk-17`)
2. Right-click "This PC" → Properties → Advanced System Settings
3. Click "Environment Variables"
4. Under "System variables," find "Path" and click "Edit"
5. Click "New" and add: `C:\Program Files\Java\jdk-17\bin` (adjust path as needed)
6. Click OK, OK, OK
7. Close and reopen Command Prompt
8. Test: `java -version`

**macOS/Linux Solution:**

Add to your `~/.bashrc` or `~/.zshrc`:
```bash
export JAVA_HOME=$(/usr/libexec/java_home)  # macOS
export PATH=$JAVA_HOME/bin:$PATH
```

Then:
```bash
source ~/.bashrc  # or ~/.zshrc
```

### Compilation Errors

**"cannot find symbol" errors:**

Make sure you're in the correct directory (where `Main.java` is located):
```bash
ls Main.java  # Unix/Mac
dir Main.java  # Windows
```

If not found, navigate to the correct directory.

**"class is public, should be declared in a file named..." error:**

This means you might have files with incorrect names. Each public class must be in a file with the same name. Our project structure is correct, so this usually means files are missing.

Verify all files are present:
```bash
ls *.java pieces/*.java exceptions/*.java
```

### Chess Pieces Display as Boxes

**Problem:** Terminal doesn't support Unicode.

**Solutions:**

**Windows:**
Use Windows Terminal (recommended) or PowerShell with UTF-8:
```powershell
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
```

**macOS/Linux:**
Check terminal encoding:
```bash
echo $LANG
```
Should be UTF-8. If not:
```bash
export LANG=en_US.UTF-8
```

**Alternative:** Use a different terminal emulator that supports Unicode.

### "Class not found: Main"

**Problem:** Running from the wrong directory.

**Solution:**

You must be in the directory where the `.class` files are:
```bash
cd /path/to/Mo-Lights-Chess
ls Main.class  # Should exist
java Main
```

### Permission Denied (macOS/Linux)

**Problem:** Don't have permission to compile/run.

**Solution:**
```bash
chmod +x *.java
```

Or run with `sudo` (not recommended for compilation, only if absolutely necessary).

### Out of Memory Errors

**Problem:** Java runs out of memory (very unlikely for this program).

**Solution:**
Increase heap size:
```bash
java -Xmx512m Main
```

---

## Uninstallation

### Remove the Program

Simply delete the Mo-Lights-Chess folder:

**Windows:**
```cmd
rmdir /s Mo-Lights-Chess
```

**macOS/Linux:**
```bash
rm -rf Mo-Lights-Chess
```

### Remove Java (Optional)

Only do this if you don't need Java for other programs.

**Windows:**
1. Go to Control Panel → Programs → Uninstall a program
2. Find "Java" or "OpenJDK"
3. Click Uninstall

**macOS:**
```bash
sudo rm -rf /Library/Java/JavaVirtualMachines/*
```

**Linux:**
```bash
# Debian/Ubuntu
sudo apt remove openjdk-17-jdk

# Fedora
sudo dnf remove java-17-openjdk-devel

# Arch
sudo pacman -R jdk-openjdk
```

---

## Advanced Configuration

### Custom Java Options

Run with specific Java options:

```bash
# Increase memory
java -Xmx1g Main

# Enable assertions (for development)
java -ea Main

# Set encoding explicitly
java -Dfile.encoding=UTF-8 Main
```

### Running in Background (Linux/macOS)

```bash
nohup java Main > chess.log 2>&1 &
```

### Windows Service

To run as a Windows service, you'd need a service wrapper like [Apache Commons Daemon](https://commons.apache.org/proper/commons-daemon/). This is advanced and not necessary for typical usage.

---

## Next Steps

After installation:

1. **Read the [User Guide](USER_GUIDE.md)** to learn how to play
2. **Check out [API Documentation](API.md)** if you want to modify the code
3. **See the [Developer Guide](DEVELOPER_GUIDE.md)** if you want to contribute

---

## Getting Help

If you encounter issues not covered here:

1. Check the [Troubleshooting](#troubleshooting) section above
2. Search existing GitHub issues
3. Create a new issue with:
   - Your operating system
   - Java version (`java -version`)
   - Error message (full text)
   - Steps to reproduce

---

**Enjoy playing Mo-Lights Chess!** ♟️
