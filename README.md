# YouAssessment

YouAssessment is an application that contains important implementations around e-commerce and ui implementation using both XML and Jetpack Compose same time.

## Features

- **Modern UI**: Built with Jetpack Compose for faster UI implementations
- **Navigations**: An entry screen and a Bottom navigation with Home, Cart, and Auth sections
- **Product Management**: Browse and view products in an organized list
- **Shopping Cart**: Manage your shopping cart with ease
- **User Authentication**: Using the firebase Auth implementation to handle user sign ups and logins

## Tech Stack

- **Language**: Kotlin
- **UI Framework**: XML and Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Navigation**: Jetpack Navigation Component
- **Material Design 3**: Modern Android UI components

## Project Structure

```
app/
├── src/
│   └── main/
│       ├── java/com/cosmic/youassessment/
│       │   ├── ui/
│       │   │   ├── entry/        # Entry point and welcome screen
│       │   │   └── onboarding/   # User onboarding flow
│       │   └── MainActivity.kt   # Main activity with bottom navigation
│       └── res/                  # Resources (layouts, drawables, etc.)
```

### Prerequisites

- Android Studio Arctic Fox or newer
- JDK 11 or newer
- Android SDK with minimum API level 21

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/youassessment.git
   ```

2. Open the project in Android Studio

3. Make sure to add your own google service json key

4. Sync the project with Gradle files

5. Run the app on an emulator or physical device