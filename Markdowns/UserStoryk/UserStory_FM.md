# Userstory: 

## 1. Felhasználói regisztráció

Mint új felhasználó, szeretném, ha lenne lehetőségem regisztrálni az oldalon, és az adataim biztonságosan tárolódjanak az adatbázisban, azért, hogy a jövőben be tudjak jelentkezni és elérjem a személyes könyvtáramat.

### Elfogadási kritérium:
- H2 adatbázis beállítva és működik a webappal.
- User tábla létrehozva pl. id, username, email, password.
- /register végpont működik és új felhasználót tud menteni.
- Érvénytelen adatok (pl. üres mezők, rövid jelszó, duplikált email) esetén hibaüzenet jelenik meg.

## 2. Felhasználói bejelentkezés

### Prioritás:
Második sprint

### User story:
Mint regisztrált felhasználó, szeretnék be tudni jelentkezni az oldalon a korábban megadott adataimmal, hogy hozzáférjek a saját profilomhoz és játékkönyvtáramhoz.

### Elfogadási Kritérium:
- A rendszer képes az adatbázisban tárolt felhasználókat azonosítani (username/email és jelszó alapján).
A bejelentkezéshez szükséges POST /api/login végpont működik.

- Helyes adatok esetén a rendszer visszaad egy sikeres bejelentkezési választ (pl. token vagy “login success” üzenet).

- Hibás jelszó vagy nem létező felhasználónév esetén megfelelő hibaüzenetet ad vissza.

- A bejelentkezett felhasználó adatai lekérhetők egy védett végponton (pl. /api/profile).

### Prioritás:
Második sprint