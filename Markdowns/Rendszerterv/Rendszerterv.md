# Rendszerterv 

## 1. A rendszer célja 

A rendszer célja egy olyan webes felület kialakítása, ahol a felhasználók különböző játékokat érhetnek el, futtathatnak vagy letölthetnek, valamint ahol az adminisztrátorok egyszerűen tölthetnek fel új játékokat. A webalkalmazás bemutatására és demonstrálására a projekt részeként elkészül egy 3D-s autós üldözős játék, amely példaként szolgál arra, hogyan használhatók és jeleníthetők meg a webes felületen keresztül elérhető játékok.

A rendszer tehát kettős célt szolgál:

- Webes játékplatform létrehozása, amely támogatja a felhasználói fiókokat, az online játékfuttatást iframe-en keresztül, a letöltést, valamint a dinamikus játékfeltöltést admin felületen.

- Egy játék fejlesztése (autós üldözős játék), amely demonstrálja a platform működését, és valós példát ad a rendszer képességeire.



## 2. Projekt terv

### 2.1 Szerepkörök
- Projektvezető  
- Unity fejlesztő  
- Backend fejlesztő  
- Frontend/Thymeleaf UI fejlesztő  

### 2.2 Sprint ütemterv

| Sprint | Játék                    | Web                           |
| -----: | ------------------------ | ----------------------------- |
|      1 | PlayerController, kamera | Authentikáció, layout         |
|      2 | Pálya + colliderek       | Játék lista, letöltés funckió |
|      3 | NavMesh AI               | Admin feltöltés               |
|      4 | Wanted rendszer          | Play oldal                    |
|      5 | UI                       | WebSocket chat                |
|      6 | Pickup & festés          | Admin finomítás               |
|      7 | Tesztek                  | Deploy                        |

### 2.3 Mérföldkövek
M1 – Mozgás kész  
M2 – Pálya kész  
M3 – AI kész  
M4 – Online felület a játékhoz kész
M5 – WebAuth + Upload kész  
M6 – Chat  



## 3. Üzleti folyamatok modellje

### 3.1 Felhasználó folyamata
1. Regisztrál / bejelentkezik a weboldalon  
2. Játéklista böngészése  
3. Letöltés vagy Online játszás 

### 3.2 Admin folyamata
1. Bejelentkezés  
2. Játék feltöltés  
3. Metaadatok rögzítése  
4. Játéklista karbantartása  

### 3.3 Chat (WebSocket)
- felhasználók valós időben küldenek üzeneteket  



## 4. Követelmények 

### Web 
- W-K1: Regisztráció, bejelentkezés  
- W-K2: Játéklista  
- W-K3: Admin feltöltés  
- W-K4: Letöltés  
- W-K5: Játszás  
- W-K6: WebSocket chat  
- W-K7: Validáció  
- W-K8: HTTPS + security  

### Játék 
- G-K1: Mozgás  
- G-K2: Kamera  
- G-K3: Colliderek  
- G-K4: NavMesh AI  
- G-K5: Despawn  
- G-K6: UI
- G-K7: Pickup & festés  
- G-K8: Game Over  



## 5. Funkcionális terv 

- UC-W1: Regisztráció / Login  
- UC-W2: Játék feltöltés (Admin)  
- UC-W3: Játéklista + letöltés  
- UC-W4: Iframe alapú játék  
- UC-W5: Chat  
- UC-G1: Játék indítása  
- UC-G2: Játék játszása  



## 6. Fizikai környezet és technológiák

### Játék
- Unity 2021/2022 LTS  
- C#  
- WebGL build (az online játszhatóság miatt) 
- Windows build (az offline/letölthető játszhatóság miatt) 

### Web
- Spring Boot  
- Thymeleaf  
- WebSocket (chat)  
- JavaDoc, JUnit5  
- DB: H2/Postgres  



## 7. Absztrakt domain modell

### Web entitások
- User  
- Game  
- Download  
- ChatMessage  

### Játék entitások
- Player  
- PoliceAgent  



## 8. Architekturális terv

### Web 
- **Presentation:** Thymeleaf, JS (chat)  
- **Service:** UserService, GameService, UploadService  
- **Data:** JPA Repositoryk  

### Játék
- View: Scene + UI  
- Controller: Scripts (PlayerController stb.)  
- Model: ScriptableObjects  

### Kapcsolat
- Nincs API kapcsolat  
- Iframemen keresztüli WebGL URL elérés  



## 9. Adatbázis terv 

### Táblák
- USER  
- GAME  
- CHAT_MESSAGES
- DOWNLOAD  
- FRIENDSHIPS


## 10. Implementációs terv

### Web
- AdminController
- ChatController
- ChatHistoryController
- FriendShipNotificationController
- FriendShipPageController
- LibraryController
- LoginController
- RegisterController
- ResourceController
- StoreController
- UserSearchController
- Service réteg  
- Repository réteg  
- JavaDoc + JUnit5  

### Játék
- AgentScript.cs
- CrashManager.cs
- EnemyDespawnManager.cs
- EnemySpawnManager.cs
- GameOverManager.cs
- MenuCameraManager.cs
- MenuManager.cs
- PlayerController.cs  
- CameraManager.cs  
- GameManager.cs    
 


## 11. Tesztterv

### Web
- Unit tesztek (JUnit5)  
- Funkcionális: upload, download, chat  
- Iframe játszhatóság ellenőrzése  

### Játék
- Test terv (TestPlan)
- Test futamok (TestRuns)
- Test esetek (TestCases) 


## 12. Telepítési terv

### Webapp
- Spring Boot jar  
- HTTPS konfiguráció  
- DB migrációk  
- WebGL build hosztolása  

### Játék
- WebGL build feltöltve hosztra  
- Windows build ZIP-ben letölthető  


## 13. Karbantartási terv
- JavaDoc és README frissítése  
- Verziókezelés: Git branching  
- Hibajavító patchek  
- Adatbázis és file backup  




