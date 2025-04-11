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

-------

<img src="https://github.com/user-attachments/assets/a152d5ed-00eb-48ff-b755-7bb46713ac91" height="400" alt="Screenshot"/>
<img src="https://github.com/user-attachments/assets/d5894f3e-d9ef-48bb-904c-38d0e7159222" height="400" alt="Screenshot"/>
<img src="https://github.com/user-attachments/assets/43e98918-d77d-4f60-b371-9df757a00163" height="400" alt="Screenshot"/>

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

1. Clone this repository

2. Open the project in Android Studio

3. Make sure to add your own google service json key

4. Sync the project with Gradle files

5. Run the app on an emulator or physical device
