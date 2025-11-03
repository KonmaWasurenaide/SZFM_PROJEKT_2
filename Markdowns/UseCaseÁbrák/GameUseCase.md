![UseCase figure for the Unity Game](/Markdowns/Images/GameUseCase.png)

---
#### PlantUML Script

```
@startuml 
top to bottom direction 
actor Player as u

package Game {

package Gameplay { 
usecase "Moving the Car" as UC1
usecase "Stopping the Car" as UC2
usecase "Avoid obstacles" as UC3
usecase "Avoid police cars" as UC4
usecase "Scoring" as UC5
usecase "Set high score" as UC6
}

package UI { 
usecase "Start the game" as UC7
usecase "Pausing the game" as UC8
usecase "Quit to main menu" as UC9
usecase "Restart the game" as UC10
usecase "Check the gametime and score" as UC11
usecase "Quit from the game" as UC12
}

} 
u --> UC1
u --> UC2
u --> UC4
u --> UC5
u --> UC6
u --> UC7
u --> UC8
u --> UC9
u --> UC10
u --> UC11
u --> UC12
@enduml

```