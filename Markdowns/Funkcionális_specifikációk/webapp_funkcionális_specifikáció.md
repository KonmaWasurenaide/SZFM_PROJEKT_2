# FUNKCIONÁLIS SPECIFIKÁCIÓ

## 1. Bevezetés
A funkcionális specifikáció a webalapú játékáruház rendszert a felhasználó szemszögéből írja le. A követelményelemzésben azonosított üzleti folyamatok (böngészés, letöltés/indítás, barát- és chatfunkciók, adminisztráció) ezen dokumentumban konkrét funkciókká (menük, gombok, űrlapok, nézetek) és használati esetekké (use case) alakulnak.

## 2. Jelenlegi helyzet leírása
(lásd követelményspecifikáció 2. pontját)

## 3. Vágyálomrendszer leírása
(lásd követelményspecifikáció 3. pontját)

## 4. Vonatkozó pályázatok, törvények, rendeletek, szabványok, ajánlások
(lásd követelményspecifikáció 4. pontját)

## 5. Üzleti folyamatok modellje
(lásd követelményspecifikáció 5. pontját)

## 6. Követelménylista 
(lásd követelményspecifikáció 6. pontját)

## 7. Használati esetek

| Azonosító | Név | Szereplő | Esemény menete | Eredmény|
|-----------|-----|----------|----------------|---------|
| UC‑01 | Regisztráció | User | A felhasználó megadja a felhasználónevét, email-jét és jelszavát, majd rákattint a regisztrálásra. | A felhasználó regisztrálva van a rendszerben, át lett irányítva a bejelentkezés menübe és a jövőben be tud majd jelentkzeni. | 
| UC‑02 | Bejelentkezés | User | A felhasználó megadja a felhasználónevét és jelszavát, majd rákattint a bejelentkezés gombra. | Sikeres bejelentkezés esetén a felhasználó tovább lesz irányítva a játékáruházba, sikertelen esetén pedig el lesz utasítva a felhasználónév és jelszó. |
| UC‑03 | Böngészés/keresés | User | A felhasználó a keresőmezőben rákeres kulcsszavakra/játék nevére. | NLP modellel intregrált játék ajánló keresés ajánl játékot kulcsszavak alapján.|
| UC‑04 | Barátkérelem és kezelés | User | A felhasználó tud barátokat bejelölni és elfogadni/elutasítani bejelöléseket. | Megtörténnek a barátság-akciók. | 
| UC‑05 | Chat | User | A felhasználó kiválasztja az egyik barátját és chatel vele. | A beszélgetés megtörténik, és el lesz mentve. |
| UC‑06 | Admin játék CRUD | Admin | Az admin játékot létrehoz/szerkeszt/töröl; képek/fájlok feltöltése; publikálás. | Az admin általi akciók végremennek. |

## 8. Képernyőtervek
Írásos forma
- Store: felső menü(profil, értesítések), rácsos játéklista kártyákkal (kép, név, kategória, akciók).  
- Bejelentkezés: felhasználónév, jelszó, „Bejelentkezés”, „Regisztráció” link; hibaüzenet blokk.  
- Regisztráció: felhasználónév, email, jelszó, „Létrehozás”. "Bejelentkezés" link; hibaüzenet blokk. 
- Játék részletek: leírás, „Indítás”, „Letöltés”.  
- Barátok: kereső, kérelmek listája (Elfogad/Elutasít), barátlista.  
- Chat: üzenetpanel, olvasottság, küldés.  
- Admin – játéklista: táblázat (név, kategória, státusz, műveletek).  
- Admin – szerkesztő: űrlap (név, leírás, kategória), képfeltöltő, mentés/közzététel.

## 9. Forgatókönyvek
S1 – Regisztráció és belépés: Vendég regisztrál, majd belép; rendszer a főoldalra navigál.  
S2 – Játék indítás: Felhasználó megnyit játékot, "Letöltés"; stat rögzül.  
S3 – Barát + chat: Kérelem küldés → Elfogadás → üzenetváltás; jelzés új üzenetre.  
S4 – Admin új játék: Admin létrehoz, képet tölt fel, publikál; a játék megjelenik a listában.  
S5 – Hibás bejelentkezés: Rossz jelszó → generikus hiba; brute force védelem aktiv.

## 10. Funkció – követelmény megfeleltetés
- Regisztráció: K‑01, K‑14  
- Bejelentkezés: K‑02, K‑14  
- Katalógus/böngészés/keresés: K‑04, K‑15, K‑17  
- Részletek + indítás/letöltés: K‑05, K‑06, K‑19  
- Barátkezelés: K‑07, K‑08, K‑09  
- Chat/értesítés: K‑10, K‑11  
- Profil: K‑12, K‑17, K‑18  
- Admin CRUD + médiakezelés: K‑03, K‑16  
- Biztonság: K‑14  
- Statisztika: K‑19  
- (K‑20 Achievements – későbbi bővítés)

## 11. Fogalomszótár
- Felhasználó: bejelentkezett látogató, aki játékokat indít/letölt, kapcsolatokat kezel.
- Admin: tartalomért felelős szerepkör, játékok és médiák CRUD.
- Játék: metaadatokkal (név, leírás, kategória, képek, indítás/letöltés link) rendelkező tartalom.
- Kategória: játékok csoportosítására szolgáló címke.
- Barátkérelem: kétirányú kapcsolat kezdeményezése; státusz: folyamatban/elfogadva/elutasítva.
- Chat: valós idejű üzenetváltás barátok között (WebSocket).
- Profil: felhasználói megjelenítés (avatar, név, státusz, téma).
- Statisztika: indítás/letöltés események összegzése.
