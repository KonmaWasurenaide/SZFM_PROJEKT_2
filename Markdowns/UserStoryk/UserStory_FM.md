# Webalkalmazás Sprint Tervezete (Felhasználó- és Adminisztrációkezelés)

Ez a dokumentum a webalkalmazás (regisztráció, bejelentkezés, admin, könyvtár, barátok, chat, tesztek) User Story-jait és Elfogadási Kritériumait tartalmazza, sprintekre bontva.

## 1. Felhasználói Regisztráció (Sprint #1)

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Regisztráció** | **Mint új felhasználó**, szeretném, ha lenne lehetőségem regisztrálni az oldalon, és az adataim biztonságosan tárolódjanak az adatbázisban, **azért, hogy a jövőben be tudjak jelentkezni és elérjem a személyes könyvtáramat**. | **H2 adatbázis** beállítva és működik a webappal. * **User tábla** létrehozva pl. id, username, email, password. **/register** végpont működik és új felhasználót tud menteni. A jelszó **hashelve** és sózva tárolódik az adatbázisban (pl. bcrypt használatával). Érvénytelen adatok (pl. üres mezők, rövid jelszó, duplikált email/username) esetén **hibaüzenet** jelenik meg. | **Sprint #1** |

## 2. Felhasználói Bejelentkezés (Sprint #2)

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Bejelentkezés** | **Mint regisztrált felhasználó**, szeretnék be tudni jelentkezni az oldalon a korábban megadott adataimmal, **hogy hozzáférjek a saját profilomhoz és játékkönyvtáramhoz** (Library). | A rendszer képes az adatbázisban tárolt felhasználókat azonosítani (**username/email és jelszó** alapján). A bejelentkezéshez szükséges **POST /api/login** végpont működik. Helyes adatok esetén a rendszer visszaad egy **sikeres bejelentkezési választ** (pl. **JWT token** a későbbi védett végpontok eléréséhez). Hibás jelszó vagy nem létező felhasználónév esetén **megfelelő hibaüzenetet** (pl. "Érvénytelen hitelesítő adatok") ad vissza. A jelszó hashelése és ellenőrzése **biztonságosan** történik. A bejelentkezett felhasználó adatai lekérhetők egy **védett végponton** (pl. **/api/profile**) az érvényes token birtokában. | **Sprint #2** |

## 3. Admin CRUD Műveletek (Sprint #3) 

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Admin CRUD** | **Mint adminisztrátor**, szeretném, ha a rendszerben lehetőségem lenne **felhasználók, játékok és egyéb entitások kezelésére** (létrehozás, olvasás, frissítés, törlés - CRUD), **azért, hogy fenn tudjam tartani a platform tartalmát és felhasználói bázisát**. | Csak a **megfelelő jogosultsággal** (pl. ROLE_ADMIN) rendelkező felhasználók férnek hozzá az adminisztrációs végpontokhoz. **Játék (Game) entitás** létrehozása, lekérdezése, frissítése és törlése (pl. **POST/GET/PUT/DELETE /api/admin/games**). A **felhasználók listájának** lekérdezése és egyedi felhasználók adatainak módosítása (pl. szerepkör [Role] beállítása) (pl. **GET/PUT /api/admin/users/{id}**). Az adminisztrációs felületen a műveletekhez **megfelelő visszajelzés** jelenik meg (pl. sikeres mentés/törlés üzenet). | **Sprint #3** |

## 4. Játékkönyvtár (Library) Rendszer (Sprint #4)

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Játékkönyvtár** | **Mint bejelentkezett felhasználó**, szeretném, ha lenne egy **személyes játékkönyvtáram (Library)**, **ahová hozzáadhatom a birtokolt játékaimat**, **hogy átláthassam és kezelhessem a gyűjteményemet**. | Létrejön egy **kapcsolat (entitás)** a User és a Game entitások között (LibraryItem). **GET /api/library** végpont lekéri a bejelentkezett felhasználó összes játékát. **POST /api/library/add/{gameId}** végpont hozzáad egy játékot a könyvtárhoz. **DELETE /api/library/remove/{gameId}** végpont eltávolít egy játékot a könyvtárból. A könyvtár adatai csak a **bejelentkezett felhasználó** számára érhetők el (JWT token alapján). | **Sprint #4** |

## 5. Barátok (Friends) Rendszer (Sprint #5)

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Barátok** | **Mint felhasználó**, szeretnék **barátokat hozzáadni**, **elfogadni/elutasítani a baráti felkéréseket**, és **áttekinteni a barátlistámat**, **hogy tudjak más felhasználókkal kapcsolatot tartani és együtt játszani**. | Létrehozásra kerül egy **FriendRequest** entitás a küldő/fogadó felhasználóval és a státusszal (függőben, elfogadva, elutasítva). **POST /api/friends/send/{userId}** végpont baráti felkérést küld. **PUT /api/friends/accept/{requestId}** végpont elfogadja a felkérést. **GET /api/friends** végpont lekéri az **elfogadott barátok** listáját. **GET /api/friends/requests** végpont lekéri a **beérkezett/elküldött felkéréseket**. | **Sprint #5** |

## 6. Chat (Üzenetküldés) Rendszer (Sprint #6)

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Chatelés** | **Mint felhasználó**, szeretnék **valós időben üzeneteket küldeni és fogadni a barátaimmal**, **hogy tudjunk kommunikálni a játékkal kapcsolatosan vagy azon kívül**. | A rendszer képes **WebSocket** kapcsolatot létrehozni a kliens és a szerver között (pl. STOMP). Csak a **barátok** tudnak egymásnak üzenetet küldeni. **Üzenet (Message) entitás** jön létre az adatbázisban a chat előzmények tárolására. **Üzenet küldése** valós időben megtörténik és megjelenik a fogadó kliensen. A felhasználó le tudja kérdezni az **előző beszélgetések** tartalmát (pl. **GET /api/chat/history/{friendId}**). | **Sprint #6** |

## 7. Tesztelés és Refactoring (Tisztítás) (Sprint #7)

| Cím | UserStory | Elfogadási kritérium | Sprint |
| :--- | :--- | :--- | :--- |
| **Tesztelés és Refactoring** | **Mint fejlesztő**, szeretnék **automatikus teszteket** írni és a meglévő kódot **átstrukturálni és tisztítani**, **hogy biztosítsam a kód minőségét, megbízhatóságát és könnyebb karbantarthatóságát a jövőre nézve**. | A **kritikus üzleti logikát** (pl. regisztráció, bejelentkezés, CRUD, barát kérés) **Unit és Integrációs tesztek** fedik le. * A **tesztfedettség** (code coverage) eléri a **minimális elfogadható szintet** (pl. 70%). A kód átnézésre és **Code Smells**-ektől megtisztításra kerül (Refactoring). A kód megfelel az elfogadott **kódolási stílusnak és konvencióknak**. A **dependency injection** konzisztensen és helyesen van használva. | **Sprint #7** |