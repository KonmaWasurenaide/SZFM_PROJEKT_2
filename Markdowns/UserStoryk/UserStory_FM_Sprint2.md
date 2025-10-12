## 1. Felhasználói bejelentkezés

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