![Class UML for the Web](/Markdowns/Images/WebClassUML.png)
---
#### PlantUML Script

```
@startuml Backend_Architektúra

!define Entity(name) class name [[name.java]]
!define Service(name) interface name [[name.java]]
!define ServiceImpl(name) class name [[name.java]]
!define Controller(name) class name [[name.java]]
!define DTO(name) class name [[name.java]]
!define Repository(name) interface name [[name.java]]

skinparam classAttributeIconSize 0
skinparam monochrome true
skinparam style strict

title GGames Backend Architektúra - Finomított nézet

' =================== ENTITY RÉTEG ===================
package "com.ggames.GGames.Data.Entity" as entity {
    Entity(UserEntity)
    Entity(GameEntity)
    Entity(ChatMessageEntity)
    Entity(FriendshipEntity)
    Entity(DownloadEntity)
    enum FriendshipStatus
    
    ' Entitás Kapcsolatok és elrendezés
    UserEntity -[hidden]right- GameEntity
    ChatMessageEntity -[hidden]right- FriendshipEntity
    FriendshipEntity -[hidden]right- DownloadEntity
    
    UserEntity "1" *-- "*" DownloadEntity : birtokolja
    GameEntity "1" *-- "*" DownloadEntity : játék
    UserEntity "1" *-- "*" ChatMessageEntity : küldő/címzett
    UserEntity "1" *-- "*" FriendshipEntity : küldő/címzett
    FriendshipEntity "1" *-- "1" FriendshipStatus : állapot
}

' =================== REPOSITORY RÉTEG ===================
package "com.ggames.GGames.Data.Repository" as repo {
    Repository(UserRepository)
    Repository(GameRepository)
    Repository(ChatMessageRepository)
    Repository(DownloadRepository)
    Repository(FriendshipRepository)

    interface JpaRepository
    UserRepository --|> JpaRepository
    GameRepository --|> JpaRepository
    ChatMessageRepository --|> JpaRepository
    DownloadRepository --|> JpaRepository
    FriendshipRepository --|> JpaRepository

    UserRepository ..> UserEntity
    GameRepository ..> GameEntity
    ChatMessageRepository ..> ChatMessageEntity
    DownloadRepository ..> DownloadEntity
    FriendshipRepository ..> FriendshipEntity
}

' =================== DTO RÉTEG ===================
package "com.ggames.GGames.Service.Dto" as dto {
    DTO(UserDto)
    DTO(GameDto)
    DTO(ChatMessageDto)
    DTO(FriendDto)
    DTO(FriendRequestDto)
    DTO(UserSuggestDto)
    
    ' DTO Alignment
    UserDto -[hidden]right- GameDto
    ChatMessageDto -[hidden]right- FriendDto
    FriendRequestDto -[hidden]right- UserSuggestDto
}

' =================== SERVICE RÉTEG ===================
package "com.ggames.GGames.Service" as service {
    Service(UserService)
    Service(GameService)
    Service(ChatService)
    Service(FriendshipService)
    class FriendshipException extends RuntimeException
    
    UserService -[hidden]right- GameService
    ChatService -[hidden]right- FriendshipService
}

package "com.ggames.GGames.Service.Impl" as serviceImpl {
    ServiceImpl(UserServiceImpl)
    ServiceImpl(ChatServiceImpl)
    ServiceImpl(FriendshipServiceImpl)

    UserServiceImpl --|> UserService
    ChatServiceImpl --|> ChatService
    FriendshipServiceImpl --|> FriendshipService

    ' UserService függőségek
    UserServiceImpl --> UserRepository
    UserServiceImpl --> DownloadRepository
    UserServiceImpl ..> UserDto
    UserServiceImpl ..> GameEntity
    UserServiceImpl -[hidden]up- UserService

    ' ChatService függőségek
    ChatServiceImpl --> ChatMessageRepository
    ChatServiceImpl --> UserService
    ChatServiceImpl --> FriendshipService
    ChatServiceImpl ..> ChatMessageDto
    ChatServiceImpl -[hidden]up- ChatService
    
    ' FriendshipService függőségek
    FriendshipServiceImpl --> FriendshipRepository
    FriendshipServiceImpl --> UserRepository
    FriendshipServiceImpl ..> FriendshipException
    FriendshipServiceImpl ..> FriendRequestDto
    FriendshipServiceImpl ..> UserSuggestDto
    FriendshipServiceImpl -[hidden]up- FriendshipService
}

' =================== CONTROLLER RÉTEG ===================
package "com.ggames.GGames.Controller" as controller {
    
    ' Játék/Admin Kontrollerek
    Controller(AdminController)
    Controller(StoreController)
    Controller(LibraryController)
    
    ' Regisztráció/Hitelesítés Kontrollerek
    Controller(RegisterController)
    Controller(LoginController)
    Controller(ResourceController)

    ' Barátság/Chat Kontrollerek (Logikai csoportosítás)
    Controller(FriendshipPageController)
    Controller(UserSearchController)
    Controller(FriendshipNotificationController)
    Controller(ChatHistoryController)
    Controller(ChatController)
    
    ' --- Elrendezés segítő (Tiszta oszlopok és sorok) ---
    
    ' Sor 1: Fő funkciók
    AdminController -[hidden]right- StoreController
    StoreController -[hidden]right- LibraryController
    LibraryController -[hidden]right- RegisterController
    
    ' Sor 2: Kiegészítő funkciók
    RegisterController -[hidden]down- FriendshipPageController
    LoginController -[hidden]right- ResourceController
    
    ' Sor 3: Social/Chat
    FriendshipPageController -[hidden]down- UserSearchController
    ChatHistoryController -[hidden]right- ChatController
    
    ' Vizuális igazítás
    AdminController -[hidden]down- LoginController
    StoreController -[hidden]down- LoginController
    
    LoginController -[hidden]down- FriendshipNotificationController
    FriendshipNotificationController -[hidden]right- ChatHistoryController

    ' Kontroller - Service kapcsolatok
    AdminController --> GameService
    AdminController --> UserService
    AdminController --> FriendshipService
    StoreController --> GameService
    StoreController --> UserService
    StoreController --> FriendshipService
    LibraryController --> GameService
    LibraryController --> UserService
    LibraryController --> FriendshipService
    RegisterController --> UserService
    
    FriendshipPageController --> FriendshipService
    FriendshipPageController --> UserService
    UserSearchController --> FriendshipService
    UserSearchController --> UserService
    FriendshipNotificationController --> FriendshipService
    FriendshipNotificationController --> UserService
    ChatHistoryController --> ChatService
    ChatHistoryController --> UserService
    ChatHistoryController --> FriendshipService
    ChatController --> ChatService

    ' Kontroller - DTO kapcsolatok
    ChatController ..> ChatMessageDto
    ChatHistoryController ..> ChatMessageDto
    FriendshipNotificationController ..> FriendRequestDto
    UserSearchController ..> UserSuggestDto
}

@enduml