# Weatherly App

**Weatherly** is a modular weather forecasting Android application built with **Jetpack Compose** and **Kotlin**. It provides real-time weather updates, leveraging clean architecture and modern Android development practices.

---
## App Demo 📸

Explore the app's interface through this demo video:

<img src="https://github.com/abhishekdubey331/Weatherly/blob/main/demo/weatherly_demo.gif" width="500"/>

---

## Table of Contents
- [Architecture](#architecture)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [Code Structure](#code-structure)
- [License](#license)

---

## Architecture
Weatherly follows **MVVM architecture** with modularization:
- **View**: Jetpack Compose UI.
- **ViewModel**: State management and business logic.
- **Domain**: UseCases and models.
- **Data**: Handles API calls and caching.
- **Core**: Shared resources like Network modules and utilities.

---

## Technologies Used
- **Jetpack Compose**: Declarative UI.
- **Kotlin Coroutines**: Asynchronous programming.
- **Hilt**: Dependency injection.
- **Retrofit & OkHttp**: API integration.
- **DataStore**: Local storage.

---

## Setup Instructions
1. Clone the repository:
   ```bash
   git clone https://github.com/abhishekdubey331/Weatherly
   cd weatherly
   ```
2. Add your API key in `local.properties`:
   ```properties
   WEATHER_API_KEY=your_api_key_here
   ```
3. Build and run the project:
   ```bash
   ./gradlew assembleDebug
   ```

---

## Code Structure
```
Weatherly/
├── app/             # Main app module
├── domain/          # Business logic and models
├── data/            # API services and repositories
├── core/            # Shared resources (network, utilities)
└── build.gradle     # Project configuration
```

---

## License
This project is licensed under the **MIT License**.
