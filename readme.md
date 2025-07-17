# ClipCop: Your Smart Clipboard Manager

[![JavaFX](https://img.shields.io/badge/JavaFX-24.0.1-blue?style=for-the-badge&logo=javafx&logoColor=white)](https://openjfx.io/)
[![Java](https://img.shields.io/badge/Java-24-red?style=for-the-badge&logo=openjdk&logoColor=white)](https://openjdk.org/projects/jdk/24/)
[![Jackson](https://img.shields.io/badge/Jackson-2.18.0-orange?style=for-the-badge&logo=jackson&logoColor=white)](https://github.com/FasterXML/jackson)
[![Maven](https://img.shields.io/badge/Build%20with-Maven-red?style=for-the-badge&logo=apache-maven&logoColor=white)](https://maven.apache.org/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=for-the-badge)](https://opensource.org/licenses/MIT)
[![Platform: Windows | Mac | Linux](https://img.shields.io/badge/Platform-Windows%20%7C%20Mac%20%7C%20Linux-brightgreen?style=for-the-badge&logo=windows&logoColor=white&logo=apple&logoColor=white&logo=linux&logoColor=white)](https://www.google.com)

---

👋 Meet **ClipCop**: Your Smart Clipboard for Windows, Mac, & Linux! 🚀

Ever wish your clipboard could do more? Tired of losing that one important thing you copied? 🤔 **ClipCop** is here to make your digital life incredibly smoother, no matter if you're on **Windows, Mac, or Linux!**

## Why ClipCop?

Here's why ClipCop makes life so much easier than those basic, built-in options:

* 💾 **Keeps History Alive:** Forget about losing track! ClipCop stores up to **100 clipboard items for 30 days**, so you can always go back and grab what you need.
* ☁️ **Feather-Light:** We've made it super efficient! ClipCop **uses minimal memory** with intelligent text compression, keeping your system quick and responsive.
* 🔍 **Instant Search:** Looking for something specific? Just type! Our **powerful search feature** lets you quickly find any copied text.
* 💡 **Open Source:** Transparency matters! ClipCop is **freely available under the MIT License**, meaning it's built with community trust and is always improving.

---

## Features

* **Clipboard History:** Stores up to 100 recent clipboard entries for easy retrieval.
* **Quick Paste Popup:** Access your top 10 most recent items with a single click or keypress (Enter to paste, Esc to close).
* **Searchable History:** Instantly filter your entire clipboard history via a convenient search bar.
* **Dark/Light Mode:** Seamlessly switch between elegant glassmorphic dark and light themes to suit your preference.
* **Cross-Platform:** Designed to run smoothly on Windows, Mac, and Linux operating systems.
* **Draggable UI:** Easily reposition windows by dragging the title bar.
* **Persistent Storage:** Your clipboard history is automatically saved to `data/clipboard_history.json`, so it's always there when you reopen ClipCop.

---

## Prerequisites

To get ClipCop up and running, you'll need the following:

* **Java 24 (JDK 24):** Download the latest JDK from [Oracle](https://www.oracle.com/java/technologies/downloads/) or [OpenJDK](https://openjdk.org/install/).
* **Apache Maven:** A powerful build automation tool. Download it from the [official Maven website](https://maven.apache.org/download.cgi).

---

## Setup (with Maven)

Follow these steps to set up and run ClipCop using Maven:

1.  **Clone the Repository:**
    ```bash
    git clone [https://github.com/your-username/ClipCop.git](https://github.com/your-username/ClipCop.git) # Replace with your actual repo URL
    cd ClipCop
    ```
    *(If you're starting from scratch without Git, manually create the `clipcop` directory and skip this step, but consider using Git!)*

2.  **Project Structure:**
    Ensure your project structure includes `src/main/java`, `src/main/resources`, `lib` (if you have specific non-Maven JARs), and `data`.
    *(You can still use `mkdir -p src/main/java/com/clipboard/controller ... lib data .vscode` if you're not cloning.)*

3.  **Initialize History:**
    Create an empty JSON file for your clipboard history:
    ```bash
    echo "[]" > data/clipboard_history.json
    ```

4.  **Maven `pom.xml`:**
    Create a `pom.xml` file in the root of your `ClipCop` project directory with the following content. This will manage your JavaFX and Jackson dependencies.

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0)"
             xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)"
             xsi:schemaLocation="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0) [http://maven.apache.org/xsd/maven-4.0.0.xsd](http://maven.apache.org/xsd/maven-4.0.0.xsd)">
        <modelVersion>4.0.0</modelVersion>

        <groupId>com.clipboard</groupId>
        <artifactId>clipcop</artifactId>
        <version>1.0-SNAPSHOT</version>

        <properties>
            <maven.compiler.source>24</maven.compiler.source>
            <maven.compiler.target>24</maven.compiler.target>
            <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
            <javafx.version>24.0.1</javafx.version>
            <jackson.version>2.18.0</jackson.version>
            <main.class>com.clipboard.MainApp</main.class> </properties>

        <dependencies>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-controls</artifactId>
                <version>${javafx.version}</version>
            </dependency>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-fxml</artifactId>
                <version>${javafx.version}</version>
            </dependency>
            <dependency>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-graphics</artifactId>
                <version>${javafx.version}</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-core</artifactId>
                <version>${jackson.version}</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-databind</artifactId>
                <version>${jackson.version}</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.core</groupId>
                <artifactId>jackson-annotations</artifactId>
                <version>${jackson.version}</version>
            </dependency>
            <dependency>
                <groupId>com.fasterxml.jackson.datatype</groupId>
                <artifactId>jackson-datatype-jsr310</artifactId>
                <version>${jackson.version}</version>
            </dependency>
        </dependencies>

        <build>
            <plugins>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.11.0</version>
                    <configuration>
                        <source>${maven.compiler.source}</source>
                        <target>${maven.compiler.target}</target>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.openjfx</groupId>
                    <artifactId>javafx-maven-plugin</artifactId>
                    <version>0.0.8</version> <configuration>
                        <mainClass>${main.class}</mainClass>
                    </configuration>
                    <executions>
                        <execution>
                            <id>default-cli</id>
                        </execution>
                        <execution>
                            <id>jar</id>
                            <goals>
                                <goal>jlink</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>
    </project>
    ```

5.  **Add Project Files:**
    Copy all your source files (Java classes, FXML files, `styles.css`) into the standard Maven directory structure.
    * Java files go in `src/main/java/com/clipboard/` (e.g., `MainApp.java`, `ClipboardController.java`).
    * FXML and CSS files go in `src/main/resources/com/clipboard/`.
    Ensure your `.vscode/launch.json` and `.vscode/settings.json` are configured for a Maven project.

6.  **Run App (using Maven):**
    Open your terminal in the `ClipCop` project root directory and run:
    ```bash
    mvn clean javafx:run
    ```
    This command will download dependencies, compile your code, and run the application.

---

## Usage

ClipCop is designed to be intuitive and easy to use:

* **Main Window:**
    * View and search your complete clipboard history.
    * Click the "Show Quick Paste" button to bring up the handy popup.
    * Toggle between sleek dark and light modes with the "🌙" / "☀" button.
    * Drag the window by its title bar to reposition it.
    * Use the "`-`" button to minimize and " `X` " to close the application.

* **Quick Paste Popup:**
    * Displays your top 10 most recent clipboard items.
    * Simply click an item or press `Enter` to paste it into your active application.
    * Press `Esc` to quickly close the popup.
    * Toggle dark/light mode with its dedicated button.
    * Drag the popup by its title bar to move it around.

* **Copy Text:**
    * Just copy text as you normally would! ClipCop automatically captures it and adds it to your history (up to 100 items).

---

## Future Advancements

We're constantly dreaming up ways to make ClipCop even more powerful and indispensable! Here are some features we're considering for future releases:

* **Image & File Support:** Expand clipboard history to include copied images and files, not just text.
* **Cloud Synchronization:** Securely sync your clipboard history across multiple devices.
* **Customizable Hotkeys:** Allow users to define their own key combinations for quick paste and other actions.
* **Clipboard Snippets/Templates:** Store frequently used text snippets or code templates for quick insertion.
* **Enhanced UI/UX:** Continuous improvements to the user interface for an even more intuitive and beautiful experience.
* **OCR Integration:** (Optical Character Recognition) Potentially allow copying text directly from images.

---

## License

ClipCop is open source and distributed under the **MIT License**. Feel free to use, modify, and distribute it as you wish!

## Contributing

Contributions are absolutely welcome! If you have ideas for improvements or find a bug, please fork the repository, make your changes, and submit a pull request.

## Troubleshooting

Encountering issues? Here are some common fixes:

* **`Maven Not Found`:** Ensure Maven is installed and added to your system's PATH.
* **`JavaFX not found/module-related errors`:** Verify your `pom.xml` is correct and Maven is building/running correctly. The `javafx-maven-plugin` usually handles module path issues.
* **`Class Version Error`:** Make sure you are definitely using **JDK 24** (you can check with `java -version` in your terminal).
* **`JSON Errors`:** Verify that `data/clipboard_history.json` exists and starts with `[]`.
* **`Dark Mode Issues`:** Ensure your `styles.css` file correctly defines the `.root:dark` and `.component` selectors.

If you're still stuck, please report issues or errors in the repository, and we'll do our best to help!

---

Built by **SMD-Thousif**. Because copying and pasting shouldn't feel like a memory test. 😉