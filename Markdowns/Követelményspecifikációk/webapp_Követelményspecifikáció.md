# KÖVETELMÉNYSPECIFIKÁCIÓ

## 1. Bevezetés
A jelen dokumentum egy **webalapú játékáruház rendszer**  követelményeit, célját, működését és üzleti folyamatait írja le.  
A Rendszer célja, hogy egy modern, biztonságos és felhasználóbarát online platformot biztosítson
A rendszer célcsoportja minden olyan felhasználó, aki szeret digitális játékokat játszani vagy letölteni egy központi platformról, és közben közösségi interakciókat is folytatni.


## 2. Jelenlegi helyzet leírása
A projekt jelenleg a **tervezési fázisban** tart, fejlesztés még nem kezdődött el.  
A cél egy egységes platform, ahol a felhasználók:
- böngészhetnek játékokat kategóriák szerint,
- játszhatnak a böngészőben elérhető játékokkal,
- letölthetik a játékokat saját gépükre,
- és kapcsolatba léphetnek más játékosokkal.

A tervezés során meghatározásra kerültek:
- a fő funkciók (játékkezelés, barát funkció, üzenetküldés),
- a technológiai alapok (webes architektúra, REST API, valós idejű kommunikáció),
- a biztonsági és adatvédelmi elvek (jelszókezelés, HTTPS, adatvédelem).

A következő lépés a részletes követelmények véglegesítése és a fejlesztési sprintterv kialakítása.

## 3. Vágyálomrendszer leírása
- Egyszerűen használható, reszponzív és modern felhasználói felület.
- Böngészhető és kereshető játéklista, kategóriákkal és leírásokkal.
- Minden regisztrált felhasználó játszhat vagy letöltheti a játékokat.
- Barátkérelmek küldése és fogadása, külön „Barátkérelmek” fülön.
- Barátok közötti **valós idejű chat** WebSocket alapon.
- Adminisztrátori felület játékok kezelésére (CRUD funkciók).
- Pontozási vagy statisztikai modul (később bővíthető high score rendszerrel).
- Saját profil testreszabása, avatar, név, státusz megadása.
- Opcionálisan beépíthető achievement rendszer vagy játékon belüli jutalom.
- Közösségi élmény: barátlista, online státusz, játékajánlók.


## 4. Jogszabályi és ajánlási háttér
A rendszer működésére vonatkozóan nincs külön szabályozás, azonban az alábbi elvek és szabványok relevánsak:

| Típus | Leírás |
|-------|--------|
| **Szoftverfejlesztési módszertan** | Ajánlott **Agile / Scrum** módszertan, sprint alapú fejlesztéssel. |
| **Adatbiztonság** | A felhasználói adatok (jelszó, e-mail) védelme jelszóhash-eléssel, HTTPS protokollal. |
| **Technológiai szabványok** | REST API, JSON kommunikáció, WebSocket alapú chat. |
| **Adatkezelés** | GDPR-kompatibilis adatkezelés, személyes adatok minimalizálása. |
| **Tartalomkezelés** | Feltöltött fájlok és képek ellenőrzése, méretkorlátozás, fájltípus-szűrés. |


## 5. Üzleti folyamat modellje

### 5.1. Regisztráció és bejelentkezés
- Felhasználó megnyitja az oldalt → „Regisztráció” gomb.  
- Megadja e-mail címét, felhasználónevét, jelszavát.  
- Rendszer validálja és létrehozza a fiókot → Bejelentkezés.  

### 5.2. Játékok kezelése
- Admin bejelentkezik az adminfelületre.  
- Új játék feltöltése: név, leírás, kategória, fájl, kép.  
- Játék módosítása vagy törlése bármikor lehetséges.  
- Felhasználók böngészik a játéklistát, játszanak vagy letöltenek.

### 5.3. Barát funkció
- Felhasználó másik felhasználónak barátkérelmet küld.  
- A címzett kérelmet lát a „Barátkérelmek” fülön.  
- Elfogadás után kapcsolat létrejön, megjelenik a barátlistában.  

### 5.4. Valós idejű chat
- Barátok közti chat WebSocket alapon működik.  
- Üzenetek azonnal megjelennek, olvasottsági státusszal.  
- Új üzenet esetén vizuális értesítés (badge, hangjelzés).  

### 5.5. Profilkezelés
- Felhasználó módosíthatja a profilképét, jelszavát, leírását.  
- Profiloldalon látható: barátlista, játékok, aktivitások.  


## 6. Követelménylista

| Azonosító | Név | Követelmény | Típus | Prioritás | Verzió |
|------------|-----|--------------|--------|------------|--------|
| K-01 | Regisztráció | Felhasználók regisztrálhatnak e-mail és jelszó segítségével. | Funkcionális | Magas | v0.1 |
| K-02 | Bejelentkezés | Bejelentkezés azonosítással. | Funkcionális | Magas | v0.1 |
| K-03 | Admin játék CRUD | Admin létrehozhat, törölhet és módosíthat játékokat. | Funkcionális | Magas | v0.2 |
| K-04 | Játékböngészés | Felhasználók listázhatják a játékokat név, kategória alapján. | Funkcionális | Magas | v0.3 |
| K-05 | Játék letöltése | Felhasználó letöltheti a játékot a helyi gépére. | Funkcionális | Magas | v0.4 |
| K-06 | Online játékindítás | A játékok böngészőben futtathatók. | Funkcionális | Közepes | v0.5 |
| K-07 | Barátkérelem küldés | Felhasználó barátkérelmet küldhet másnak. | Funkcionális | Magas | v0.5 |
| K-08 | Barátkérelem fogadása | Címzett elfogadhatja vagy elutasíthatja a kérelmet. | Funkcionális | Magas | v0.5 |
| K-09 | Barátlista | A barátok megjelennek külön listában. | Funkcionális | Magas | v0.6 |
| K-10 | Chat funkció | Barátok valós idejű üzeneteket küldhetnek egymásnak. | Funkcionális | Magas | v0.6 |
| K-11 | Üzenet értesítés | Új üzenet esetén értesítés jelenik meg. | Funkcionális | Közepes | v0.7 |
| K-12 | Profil szerkesztése | Felhasználók módosíthatják a profiljukat. | Funkcionális | Közepes | v0.7 |
| K-13 | Admin felület | Admin külön panelen kezeli a játékokat. | Funkcionális | Magas | v0.7 |
| K-14 | Biztonság | HTTPS, jelszó hash-elés, input validálás. | Nem funkcionális | Magas | v0.8 |
| K-15 | Keresés | Keresés játékcím vagy kategória alapján. | Funkcionális | Közepes | v0.8.2 |
| K-16 | Képkezelés | Játékhoz előnézeti kép feltöltése. | Funkcionális | Közepes | v0.9 |
| K-17 | Reszponzív UI | Az oldal működjön mobilon és asztali gépen is. | Nem funkcionális | Magas | v1.0 |
| K-18 | Sötét mód | A felhasználó válthat világos/sötét témát. | Nem funkcionális | Alacsony | v1.1 |
| K-19 | Játékstatisztika | Megjeleníthető letöltési és játékindítási szám. | Funkcionális | Alacsony | v1.2 |
| K-20 | Achievements | Későbbi bővítésként teljesítmény-jutalmak. | Funkcionális | Alacsony | v1.3 |


## 7. Fogalomszótár

**CRUD:** Create, Read, Update, Delete – alap adatkezelési műveletek.  
**REST API:** Olyan architektúra, amely HTTP kérések segítségével kommunikál kliens és szerver között.  
**WebSocket:** Valós idejű, kétirányú kommunikációra szolgáló protokoll.  
**Barátkérelem:** Egy felhasználó által küldött kapcsolatfelvételi kérelem egy másik felhasználónak.  
**Admin panel:** Jogosultsággal védett felület, ahol az adminisztrátor a játékokat és felhasználókat kezeli.  
**UI (User Interface):** A felhasználó által látható és vezérelhető grafikus felület.  
**GDPR:** Az Európai Unió adatvédelmi rendelete, amely meghatározza a személyes adatok kezelésének szabályait.  
**Reszponzív dizájn:** A weboldal automatikusan alkalmazkodik különböző képernyőméretekhez.  
