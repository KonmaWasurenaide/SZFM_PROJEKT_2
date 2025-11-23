# Helyi Futtatási Útmutató (Localhost)

Ez a dokumentum lépésről lépésre bemutatja, hogyan tudod a projektet (Backend + Frontend) a saját számítógépeden futtatni.

## 1. Előfeltételek

Mielőtt elkezdenéd, győződj meg róla, hogy a következők telepítve vannak:

*   **Java Development Kit (JDK) 21** vagy újabb: A backend futtatásához szükséges.
    *   Ellenőrzés terminálban: `java -version`
*   **Visual Studio Code** (ajánlott): A kód szerkesztéséhez és futtatásához.
*   **Live Server kiegészítő VS Code-hoz** (ajánlott): A weboldalak egyszerű futtatásához.

## 2. Backend (Szerver) Indítása

A backend egy Java Spring Boot alkalmazás, amely a `WebDatabase` mappában található.

1.  Nyiss egy terminált a projekt gyökérmappájában.
2.  Lépj be a `WebDatabase` mappába:
    ```powershell
    cd WebDatabase
    ```
3.  Indítsd el az alkalmazást a Maven wrapper segítségével:
    *   **Windows:**
        ```CMD
        .\mvnw.cmd spring-boot:run
        ```
    *   **Mac/Linux:**
        ```bash
        ./mvnw spring-boot:run
        ```
4.  Várd meg, amíg a terminálban megjelenik a következő üzenet (vagy hasonló):
    `Started GGamesApplication in ... seconds`
5.  A szerver mostantól fut a **9090**-es porton.
    `http://localhost:9090/login`

