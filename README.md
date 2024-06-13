Android Weather and Task Manager App

Overview
This Android application is designed to provide users with two main features: weather search by city and task management. The app utilizes the Clean Architecture pattern along with the State Observer pattern for better code organization and maintainability. It includes features like dependency injection, local data storage with Room database implementation, and follows best practices for UI development.

Features
Weather Search by City:

Users can search for weather information by entering the name of a city.
Weather data is fetched from a remote API and displayed in the app's UI.
The UI provides information such as temperature, weather conditions, humidity, etc.
Task Manager:

Users can manage their tasks within the app.
Tasks can be added and deleted.
Tasks can be deleted by swiping right or left.
Tasks are stored locally using Room database for offline access.
Architecture
The app follows the Clean Architecture pattern, which separates the application into three layers: presentation, domain, and data. Each layer has its own responsibility and interacts with the others through well-defined interfaces.

Presentation Layer: This layer contains UI-related components such as activities, fragments, and view models. It communicates with the domain layer through use cases and observes changes in data using the State Observer pattern.

Domain Layer: Here lies the business logic of the application. It defines use cases that represent the actions a user can perform within the app. The domain layer is independent of any specific implementation details and interacts with the data layer through repository interfaces.

Data Layer: This layer handles data operations such as fetching data from remote APIs or local databases. It includes repository implementations that define how data is accessed and manipulated. Room database is used for local data storage.

Project Structure
The project is structured into several packages for better organization and separation of concerns:

di: Contains classes related to Dependency Injection using Dagger hilt.
db: Includes Room database implementation for local data storage.
presentation: Contains UI-related components like activities, fragments, and view models.
data: Holds models and repository implementations for data operations.
domain: Defines use cases and repository interfaces.
utils: Includes utility classes and extension functions used throughout the app.
Tech Stack
Kotlin
Android Jetpack (ViewModel, Flow, Room)
Retrofit for network requests
Dagger hilt for Dependency Injection
Setup
To run the app locally, follow these steps:

Github repository : https://github.com/subhraji/vantage-assignment

Clone this repository.
Open the project in Android Studio.
Build and run the project on an Android device or emulator.
