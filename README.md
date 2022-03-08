# float 🛥️
Float is an open android app built to improve the life of people living on the canals. 

### Status: 🚧 In progress 🚧
Float is still in development, there is just one screen so far with a list of items and a button, no much UI, but there are good fundations where most of the base architecture has been implemented. (See the section below)

## Features

The code contains 1 screen so far. The user can see a list of moorings and add a new one clicking on the button.

## Architecture
The app is built in a Redux-style (MVI), where each UI 'screen' has its own ViewModel, which exposes a single [StateFlow][stateflow] containing the entire view state. Each ViewModel is responsible for subscribing to any data streams required for the view, as well as exposing functions which allow the UI to send events.
- The ViewModel is implemented as [`MainViewModel`][mainvm] , which exposes a `StateFlow<MainUiState>` for the UI to observe.
- [`MainUiState`] contains the complete view state for the screen as data class
- The Main Compose UI in MainScreen.kt uses MainViewModel, and observes it's MainUiState as Compose State, using collectAsState().

## Data
The Data layer has repositories that communicate with the data source using Kotlin coroutines. `kotlinx-coroutines-play-services` provides coroutine support to Firebase and Firestore operations by using `await` on `Task` instead of adding a listener to it.

### Firebase firestore
The Data layer has repositories that communicates with the data source (Firebase firestore). The data on firebase follows the common collection structure with documents inside.
There tree collections: owners, boats and moorings (sub-collection of boats).

### Terms and Concepts
- a **Owner** represents a person that has signed up for the application. Uniquely identified by username.
- a **Boat** represents a boat owned by a particular owner or co-owned by different owners. The registration id is unique but the name is not. 
- a **Mooring** represents a place occupied by a boat currently or in the past. There is no limits to the number of mooring that a boat can have.


## License
```
Copyright 2022 Alessandro Borelli

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```






 [mainvm]: app/src/main/java/com/alessandroborelli/floatapp/presentation/MainViewModel.kt
