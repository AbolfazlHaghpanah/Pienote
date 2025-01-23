# Pienote

Pienote is a beautifully designed note-taking application powered by Jetpack Compose and Kotlin Multiplatform. With rich text editing, Markdown rendering, stunning animations, and seamless cross-platform support, Pienote is built for productivity and creativity.

⚠️ **Status**: In Development
Pienote is currently in active development and is not stable for production use yet. Expect frequent changes and improvements as new features are being implemented.

## ✨ Features
-	🌟 Rich Text Editing: Customize your notes with bold, italic, underline, and code block formatting.
-	🎨 Cool Animations: Enjoy fluid, polished animations for transitions, dialogs, and interactions, enhancing the user experience.
-	🚀 Cross-Platform Support: Built using Kotlin Multiplatform, making it easy to run on Android, Desktop, and (soon) macOS.
-	📋 Keyboard Shortcuts: Speed up your workflow with intuitive keyboard shortcuts managed by a KeyboardShortcutsManager.
-	🖌️ Customizable Themes: Built with Material 3 for light and dark modes.
-	📖 Persistent Storage: SQLDelight ensures your notes are always stored securely and efficiently.
-	🔗 Modular Architecture: Clean code organization for scalability and maintainability.

## 🛠️ Tech Stack

Pienote is powered by the latest tools and technologies:

**Core Technologies**
-	Compose Multiplatform: Share code across Android, Desktop, and macOS.
-	Jetpack Compose: Modern UI toolkit for building declarative, reactive user interfaces.
-	SQLDelight: Database management with type-safe queries and seamless multiplatform support.
-	Detekt: Setted up detekt for code anlysis.
-	Koin: Dependency injection for shared multi platforms.
-	Coil: For image loading.
-	Build-logic: To Implement custom gradle plugins for better modularization.

**Platform-Specific**
-	Android: Uses Jetpack libraries, Material 3, and Kotlin Coroutines.
-	Desktop: Compose Desktop for native-like desktop applications.
-	macOS: Compose Multiplatform for macOS support (work in progress).

## 📸 Screenshots


| Home | Category | Note | Edit Note |
|------|----------|------|-----|
|  ![image](https://github.com/user-attachments/assets/15e22e92-12c0-4658-b121-2d88d8668ceb)    |     ![image](https://github.com/user-attachments/assets/68ee2b75-e11e-4675-945f-d887596fcded)     |   ![image](https://github.com/user-attachments/assets/d9725091-018b-4fcb-84b9-b024975af996) | ![image](https://github.com/user-attachments/assets/d6bd9788-10f9-46bd-8f2f-fd51ec9ee748) |
|  ![image](https://github.com/user-attachments/assets/4d238840-0c12-4bc0-8393-60a91d343ec5) | ![image](https://github.com/user-attachments/assets/00101731-5f31-4250-b23f-b2d06f2c37fd) | ![image](https://github.com/user-attachments/assets/695ce575-3593-49d1-a3e6-259cceb21b58) | ![image](https://github.com/user-attachments/assets/01e0de77-1535-4b39-9147-8c2d2843ac11) |


## 🎯 How It Works

**Architecture**

Pienote follows a clean architecture approach with three core layers:
1. Domain: Handles business logic, built using pure Kotlin.
2. Data: Manages repositories and the database (SQLDelight).
3. Presentation: Jetpack Compose-based UI for Android and Desktop.

**Highlighted Components**
-	Animations: Custom AnimatedContent and transitions make interactions smooth and engaging.
-	Navigation: Type-safe navigation powered by Kotlin sealed interfaces and classes.
-	Custom Gradle Plugin: Simplifies configuration and maintains consistency across modules.
-	Markdown Support: Markdown syntax transforms raw text into structured, formatted notes.



