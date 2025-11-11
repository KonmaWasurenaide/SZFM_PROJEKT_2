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
    usecase "Chase by police cars" as UC5
    usecase "Scoring" as UC6
    usecase "Set high score" as UC7
    usecase "Pausing the game" as UC8
    }

    package UI {
    
    
        package MainMenuUI{
        usecase "Start the game" as UC9
        usecase "Quit from the game" as UC10
        }
        
        package InGameUI{
        usecase "Check the gametime and score" as UC11
        usecase "Check the wanted level" as UC12
        usecase "Check the 'busted in' timer" as UC16
        }
        
        package PauseMenuUI{
        usecase "Quit to main menu" as UC13
        usecase "Restart the game" as UC14
        usecase "Resume the game" as UC15
        
        }
        
        package BustedMenuUI{
        usecase "Quit to main menu" as UC17
        usecase "Restart the game" as UC18
        usecase "Check the high score" as UC19
        
        }
    }

} 
u --> UC1
u --> UC2
u --> UC3
u --> UC4
u --> UC5
u --> UC6
u --> UC7
u --> UC8
u --> UC9
u --> UC10
u --> UC11
u --> UC12
u --> UC13
u --> UC14
u --> UC15
u --> UC16
u --> UC17
u --> UC18
u --> UC19
@enduml



```