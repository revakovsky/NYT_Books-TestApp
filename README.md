# NYT Books - Test App

---

### Application Description

This project is a test assignment for the Android Developer position.
The goal of the application is to display bestseller book categories and book details retrieved from
the [New York Times Developer Network Books API](https://developer.nytimes.com/)

**Upon launching the application, users can:**

- See a Splash Screen that checks for an active session
- If no session is found, be prompted to sign in with Google via Firebase Authentication
- Upon successful login, be redirected to the main screen

Then users can:

- View a list of bestseller book categories on the main screen
- Tap a category to see a list of books within that category
- View book details such as title, description, author, publisher, image, and rank
- Click the “Buy” button to open a dialog with a list of online bookstores
- Select a store to open its website directly in an in-app browser (WebView)
- Swipe down to refresh the book list from the network

If the network is unavailable, the app automatically falls back to data from the local Room
database.
Users are notified of their connection status accordingly.

### ⚠️ Firebase Configuration Required

> **Important**  
> To run the application successfully, you must provide a `google-services.json` file at the
> following path:
> ```
> app/google-services.json
> ```
> This file is required for Firebase Authentication (Google Sign-In) to work.  
> It is not included in the repository and must be added manually.

### Minimum Android Version:

- Mobile app: Android 8.0 (API level 26)

### Architecture & Principles

The project follows the Clean Architecture approach and the MVI architectural pattern.
It is structured as a multi-module, offline-first application, designed with scalability and
maintainability in mind.  
The codebase adheres to key software engineering principles: DRY, KISS, SOLID, and Separation of
Concerns.

### Tech Stack & Features

- [Kotlin](https://kotlinlang.org/docs/android-overview.html#)
- [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html#)
- [Kotlin Flows](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/#)
- [Multi-module project structure](https://developer.android.com/topic/modularization)
- [Gradle Convention Plugin](https://docs.gradle.org/current/userguide/plugins.html)
- [MVI Architecture](https://developer.android.com/topic/architecture)
- [Jetpack Compose](https://developer.android.com/develop/ui/compose/documentation)
- [Material 3](https://developer.android.com/develop/ui/compose/designsystems/material3)
- [Nested Type-safe Jetpack Compose Navigation](https://developer.android.com/develop/ui/compose/navigation)
- [Koin](https://insert-koin.io/docs/quickstart/android/)
- [Ktor Client](https://ktor.io/docs/welcome.html)
- [Room](https://developer.android.com/jetpack/androidx/releases/room)
- [Coil](https://coil-kt.github.io/coil/)
- [Android Splash Screen API](https://developer.android.com/develop/ui/views/launch/splash-screen)
- [Connectivity Manager](https://developer.android.com/training/monitoring-device-state/connectivity-status-type)
- [Firebase Authentication (Google Sign-In)](https://firebase.google.com/docs/auth/android/google-signin)
- [WebView](https://developer.android.com/develop/ui/views/layout/webapps/webview)
- [PullRefresh](https://developer.android.com/develop/ui/compose/components/pull-to-refresh)

### Features and Functionality

- Portrait / Landscape screen orientation support
- Animated screen transitions
- Light / Dark modes
