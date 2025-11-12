# WEBAPP – FUNKCIONÁLIS SPECIFIKÁCIÓ

Verzió: v1.0

## 1. Bevezetés
A dokumentum a webalapú játékáruház rendszer funkcionális működését írja le a követelmények (K-01 … K-20) alapján. Cél: felhasználóbarát, biztonságos, reszponzív webapp, amely böngészést, játékindítást/letöltést, közösségi funkciókat (barát, chat) és admin kezelőfelületet biztosít.

## 2. Rendszeráttekintés és hatókör
- Hitelesítés és jogosultság: regisztráció, bejelentkezés, kijelentkezés (K-01, K-02, K-14).
- Játékböngészés és keresés: listázás, kategória/név szerinti szűrés (K-04, K-15).
- Játék megtekintése: részletek, képek, indítás böngészőben (ha elérhető), letöltés (K-05, K-06, K-16).
- Barátkezelés: kérelmek küldése/fogadása, barátlista (K-07, K-08, K-09).
- Chat: valós idejű, barátok között (K-10, K-11).
- Profil: avatar, név, státusz módosítása (K-12).
- Admin modul: játékok CRUD (név, leírás, kategória, képek/fájlok) (K-03, K-16).
- UI és reszponzivitás: mobil/asztali támogatás, világos/sötét mód (K-17, K-18).
- Statisztika/ajánlás (alap): megtekintés, letöltés/indítás számok (K-19).


## 3. Szerepkörök és jogosultságok
- Felhasználó: teljes böngészés, letöltés/indítás, barátfunkciók, chat.
- Admin: felhasználói jogosultságok felett, játékok CRUD, médiakezelés.

## 4. Használati esetek (Use Case-ek)

### 4.1 Regisztráció (K-01, K-14)
- Szereplő: Vendég
- Előfeltétel: érvényes űrlapadatok.
- Fő folyamat: mezők kitöltése → szerver oldali validáció → fiók létrehozása → visszajelzés.
- Validáció: felhasználónév egyedi; jelszó min. hossz; tiltott karakterek kiszűrése.
- Sikerkritérium: visszaigazolás, bejelentkezési oldalra terelés.

### 4.2 Bejelentkezés (K-02, K-14)
- Szereplő: Felhasználó
- Fő folyamat: felhasználónév + jelszó → hitelesítés → munkamenet/jogosultság beállítása.
- Hibák: általános „Érvénytelen adatok” üzenet (információszivárgás elkerülése).
- Siker: főoldalra vagy korábban kért oldalra visszairányítás.

### 4.3 Játékok böngészése/keresése (K-04, K-15, K-17)
- Szereplő: Vendég/Felhasználó
- Folyamat: lista betöltése → szűrés (kategória/név) → lapozás/rendezés.
- Eredmény: találati lista kártyákkal (kép, név, kategória, rövid leírás).

### 4.4 Játék részletek és lejátszás/letöltés (K-05, K-06, K-16)
- Szereplő: Felhasználó
- Folyamat: részletoldal → „Indítás” (beágyazott böngészős játék) vagy „Letöltés” (fájl).
- Követelmény: arányos képek, előnézeti kép, fájlméret-korlát, típus-szűrés (K-16).
- Naplózás: indítás/letöltés számlálása (K-19).

### 4.5 Barátkérelem küldése/fogadása (K-07, K-08, K-09)
- Szereplő: Felhasználó
- Folyamat: felhasználó keresése → kérelem küldés; címzett: elfogadás/elutasítás.
- Eredmény: baráti kapcsolat létrejön; barátlista frissül.

### 4.6 Chat (K-10, K-11)
- Szereplő: Felhasználó
- Folyamat: barát kiválasztása → WebSocket csatorna → üzenetküldés/fogadás valós időben.
- Értesítés: badge/hang új üzenetnél (K-11).

### 4.7 Profilkezelés (K-12, K-17, K-18)
- Szereplő: Felhasználó
- Folyamat: avatar feltöltés (típus/méret limit), név/státusz módosítása, téma (light/dark).
- Biztonság: szerver oldali fájlszűrés (K-14, K-16).

### 4.8 Admin játék CRUD (K-03, K-16)
- Szereplő: Admin
- Folyamat: új játék felvétele/módosítás/törlés; képfeltöltés, kategóriák kezelése.
- Validáció: kötelező mezők, fájl típus/limit, egyedi név.

## 5. Navigáció és képernyők
- Belépési pontok: Landing / Bejelentkezés / Regisztráció.
- Főoldal: játéklista, kereső és szűrők.
- Játék részletek: kép, leírás, Indítás/Letöltés gombok.
- Barátok: keresés, kérelmek, barátlista.
- Chat: beszélgetés lista + aktív beszélgetés panel.
- Profil: adatok, avatar, téma.
- Admin: játéklista táblázat, szerkesztő űrlap, képfeltöltő.
- Navigáció: felső menüsor + hamburger mobilon.

## 6. Adatmodell (magas szint)
- User(id, username, passwordHash, displayName, avatarUrl, createdAt, status)
- Game(id, name, description, category, imageUrl, downloadUrl|launchUrl, createdAt)
- Friendship(id, requesterId, addresseeId, status[pending|accepted|rejected], createdAt)
- Message(id, senderId, receiverId, body, sentAt, readAt)
- Category(id, name)
- Stats(id, userId, gameId, launches, downloads, lastPlayedAt)

Megjegyzés: személyes adat minimalizálás (GDPR), jelszó hash (BCrypt) (K-14).

## 7. API vázlat (REST + WebSocket)
- Auth:
  - POST /api/auth/register {username, password} → 201 | 409
  - POST /api/auth/login {username, password} → 200 (token/session) | 401
  - GET /api/auth/me → 200 (aktuális profil)
- Games:
  - GET /api/games?search=&category=&page=&size=
  - GET /api/games/{id}
  - POST /api/admin/games (Admin)
  - PUT /api/admin/games/{id} (Admin)
  - DELETE /api/admin/games/{id} (Admin)
- Friends:
  - POST /api/friends/requests {userId}
  - GET /api/friends/requests (bejövő/kimenő)
  - POST /api/friends/requests/{id}/accept | /reject
  - GET /api/friends (barátlista)
- Chat:
  - WS /ws/chat (azonosítás: token)
  - Rest: GET /api/chat/history?userId=...
- Media:
  - POST /api/admin/uploads/image (Admin; típus/méret validáció)
- Stats:
  - POST /api/stats/launch {gameId}
  - POST /api/stats/download {gameId}

Válaszok: JSON; hibák: standard HTTP kódok + generikus üzenetek (K-14).

## 8. Validáció, hibakezelés, használhatóság
- Frontend + backend validáció; mezőszintű hibaüzenetek.
- Biztonság miatti generikus auth/exists üzenetek (nincs információszivárgás).
- Üres állapotok, betöltő állapotok, hibaállapotok konzisztens UI-val.
- Feltöltés: méretlimit, MIME ellenőrzés, vírusgyanús fájlok tiltása (K-16, K-14).

## 9. Biztonság (K-14)
- HTTPS (Nginx), HSTS.
- Jelszó: BCrypt; throttling/rate limit auth végpontokra.
- Session/JWT: HttpOnly, Secure cookie; CSRF védett űrlapok.
- Beviteli adatok sanitizálása; XSS/CSRF/SQLi elleni védelem.
- Naplózás: auth események, admin műveletek.

## 10. Nem funkcionális követelmények
- Reszponzivitás (K-17), sötét mód (K-18).
- Teljesítmény: lista lekérések < 300 ms átlag; képek optimalizálása.
- Elérhetőség: 99%+ (üzemeltetési cél), Nginx proxy.
- Megfigyelhetőség: szerver log, front hiba tracking (opcionális).

## 11. Követelmények leképezése
- K-01 Regisztráció → 4.1, 7(Auth)
- K-02 Bejelentkezés → 4.2, 7(Auth)
- K-03 Admin játék CRUD → 4.8, 7(Games Admin)
- K-04 Játékböngészés → 4.3, 5
- K-05 Letöltés → 4.4, 7(Stats)
- K-06 Online indítás → 4.4
- K-07–K-09 Barát funkciók → 4.5, 7(Friends)
- K-10–K-11 Chat/értesítés → 4.6, 5(Chat UI), 7(WS)
- K-12 Profil → 4.7
- K-13 Admin felület → 4.8, 5(Admin UI)
- K-14 Biztonság → 8, 9
- K-15 Keresés → 4.3, 7(Games list)
- K-16 Képkezelés → 4.4, 4.8, 7(Media)
- K-17 Reszponzív UI → 5
- K-18 Sötét mód → 4.7, 5
- K-19 Statisztika → 4.4, 7(Stats)
- K-20 Achievements (későbbi) → out of scope v1.0

## 12. Elfogadási kritériumok (példák)
- Regisztráció: új, egyedi felhasználónévvel 201 Created; duplikált eset 409 Conflict.
- Bejelentkezés: helyes adatokkal 200 és session/token; hibás adatokkal 401 és generikus üzenet.
- Játéklista: üres keresésre minimum 1 oldalnyi adatsor, szűrés működik kategória szerint.
- Admin CRUD: létrehozás után azonnal listában; fájlfeltöltés méretlimit felett 413 Payload Too Large.

## 13. Technológiai környezet
- Frontend: HTML/CSS/JS (reszponzív, light/dark).
- Backend: Spring Boot (REST + WS), H2 fejlesztéshez.
- Reverse proxy: Nginx (HTTP→HTTPS, /api proxy).
- Tárolás: fájlok (képek) kiszolgálása szerverről vagy object storage (később).

## 14. Függőségek és kockázatok
- Böngészős játékok beágyazása (Unity canvas/iframe) böngésző-kompatibilitása.
- Chat stabilitás (WS kapcsolatkezelés, újracsatlakozás).
- Fájlfeltöltés biztonság (MIME spoofing, limit).

## 15. Mérföldkövek (magas szint)
1. Auth + Alap játéklista (K-01–K-04)
2. Részletek + Letöltés/Indítás + Képkezelés (K-05–K-06, K-16)
3. Barát + Chat (K-07–K-11)
4. Profil + Reszponzív UI + Sötét mód (K-12, K-17–K-18)
5. Admin CRUD + Statisztika (K-03, K-19)
