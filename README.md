# Flixt
![image](https://github.com/user-attachments/assets/4290a6c5-2658-4cc0-bd91-67fa997ddc4d)

Flixt is a mobile application for online movie ticket booking with a convenient, fast, and intuitive experience.
## Demo
//video

## Table of Contents
- [Flixt](#flixt)
  - [Demo](#demo)
  - [Table of Contents](#table-of-contents)
  - [Tech Stack](#tech-stack)
  - [Features](#features)
  - [Project Structure](#project-structure)
  - [Architecture Pattern](#architecture-pattern)
  - [Dependency Injection](#dependency-injection)
  - [Prerequisites to Run This Project](#prerequisites-to-run-this-project)
  - [Installation](#installation)

## Tech Stack
![Tech-Stack-Flixt](https://github.com/user-attachments/assets/7fcc46ff-4049-4a64-930e-829174d2d4d6)


## Features
- *Firebase Google Authentication* for secure and easy login
- *Firebase Crashlytics* for real-time crash reporting and app stability monitoring
- *Film Search*  Quickly find films with a responsive and precise search functionality.
- *Now Playing Movie* List of movies currently in theaters.
- *Coming Soon Playing Movie* List of movies that will be in theaters.
- *Detail Movie* Details of the movie you want to see.
- *Watch Trailer* feature to view movie trailers using webview.
- *Select Schedule Watching Movie* choose a place and schedule to see the movie.
- *Buy Ticket* buy tickets of the desired movie.
- *Detail Ticket* details of the tickets that have been purchased.
- *Watchlist Movie* list for the movie you want to see.

   
## Project Structure
- *Data Module*: Handles data sources such as APIs, databases, and network responses. It manages data fetching and persistence.
- *Domain Module*: Contains the core business logic of the application, including use cases and entities. This layer acts as a bridge between the data and presentation layers.
- *App Module*: Manages the UI components, user interactions, and ViewModels that provide data to the views.
- *DI Module*: Used to inject dependencies needed by other modules.

![Project Structure](https://github.com/user-attachments/assets/b74660f6-da88-4418-b270-fb7b50861dd0)

## Architecture Pattern
Flixt is built using the MVVM (Model-View-ViewModel) architectural pattern. This pattern separates the user interface (UI) from the business logic, which offers several benefits:

- *Model*: Represents the data layer, including data sources, APIs, and repositories. It handles data manipulation and provides the required data to the ViewModel.
- *View*: Refers to the user interface components such as Activity, Fragment, or XML layout files. The View is responsible for displaying data to the user and sending user actions to the ViewModel.
- *ViewModel*: Acts as a mediator between the Model and View. It fetches data from the Model and provides it to the View in a format that can be easily displayed. It also manages UI-related logic and survives configuration changes, such as screen rotations, ensuring data persistence.
By adopting MVVM, Flixt promotes a clean separation of concerns, making the codebase more maintainable, testable, and scalable.

![image](https://github.com/user-attachments/assets/1badc8cb-f936-4e78-a473-6cd35b362e83)

## Dependency Injection
Flixt utilizes Hilt, a dependency injection framework built on top of Dagger. Dependency injection (DI) is a design pattern that allows objects to receive their dependencies from an external source rather than creating them internally.

## Prerequisites to Run This Project
Sebelum menjalankan aplikasi ini, pastikan Anda telah menginstal:
- *Android Studio*  (latest version recommended)
- *Java Development Kit (JDK)*
- An active internet connection to download project dependencies

## Installation
Follow these steps to run the project on your local machine:
1. Clone this repository:
   ```bash
   git clone https://github.com/miftahulacil0608/Phincon_Academy-Final_Project-Movieku.git
   
- Open the project in Android Studio.
- Sync the project with Gradle files.
- Run the project on an emulator or a physical device.
