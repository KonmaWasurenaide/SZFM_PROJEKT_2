### Tesztelési adatok
Tesztelő neve: Gábor Lili Zita
Tesztelés dátuma: 2025.11.16
Tesztelés kezdete: 18:15
Tesztelés befejezte: 18:45
Tesztelés eredménye: 10/9

---
| Mező| Tartalom|
| - | -|
| **TC_ID**| TC-01|
| **Cél**| Ellenőrizni, hogy a játékos autója W/A/S/D billentyűkkel mozgatható.|
| **Előfeltételek**   | A játék elindítva, a játékos járműve aktív a pályán.|
| **Lépések**| 1. Nyomd meg a `W` billentyűt (előre).<br>2. Nyomd meg az `A` billentyűt (balra fordulás).<br>3. Nyomd meg a `D` billentyűt (jobbra fordulás).<br>4. Nyomd meg az `S` billentyűt (tolatás).<br>5. Az autó nem forog egy helyben<br>6. Nyomd meg a `Space` billentyűt (kézifék)  |
| **Elvárt eredmény** | Az autó minden iránybillentyűre megfelelően reagál.|
| **Státusz**| Siker |
| **Megjegyzés**| Az autó csak akkor fog kanyarodni, ha mozgásban van. Az autó mozgás közben `S` billenytű hatására fékez. |

---

| Mező                | Tartalom                                                                                       |
| ------------------- | ---------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-02                                                                                          |
| **Cél**             | Ellenőrizni, hogy a kamera folyamatosan követi a játékos járművét.                             |
| **Előfeltételek**   | A játék fut, a jármű irányítható.                                                              |
| **Lépések**         | 1. Mozgasd az autót előre.<br>2. Kanyarodj balra és jobbra.<br>3. Teszteld gyors mozgásnál is. |
| **Elvárt eredmény** | A kamera mindig az autót követi, nem késik és nem szakad le.                                   |
| **Státusz**         |    Siker                                                                          |
| **Megjegyzés**      | Külön tesztelendő városi és városon kívüli pályán is.                                          |

---

| Mező                | Tartalom                                                                                                         |
| ------------------- | ---------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-03                                                                                                            |
| **Cél**             | Hitbox és collider rendszer helyes működésének ellenőrzése.                                                      |
| **Előfeltételek**   | A játékos mozgásban van, pálya objektumok elérhetőek.                                                            |
| **Lépések**         | 1. Ütközz a pálya szélével.<br>2. Próbálj a falon áthaladni.<br>3. Ellenőrizd, hogy az autó nem zuhan a semmibe.<br>4. Ellenőrizd, hogy az autó tud a rendőrökkel ütközni |
| **Elvárt eredmény** | Az autó ütközik a pályán lévő akadályokkal és rendőrökkel, a collider megakadályozza a kiesést a pályáról.                                 |
| **Státusz**         |  Sikertelen                                                                                               |
| **Megjegyzés**      | Ellenőrizd különböző objektumokra (ház, kerítés, fa,rendőr).                                                            |
---

| Mező                | Tartalom                                                                                                                                                                |
| ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-04                                                                                                                                                                   |
| **Cél**             | Ellenőrizni az  AI működését rendőrautók esetén.                                                                                                                  |
| **Előfeltételek**   | Körözési szint legalább 1 csillag, rendőrautók spawnolva.                                                                                                                 |
| **Lépések**         | 1. Indítsd el a játékot.<br>2. Hagyd, hogy a rendőrök üldözzenek.<br>3. Próbálj meg lerázni őket.<br>4. Figyeld meg, hogy az AI képes-e kikerülni az akadályokat. |
| **Elvárt eredmény** | Az AI rendőrautók kikerülik az akadályokat és követik a játékost.                                                                           |
| **Státusz**         |  Siker                                                                                                                                                      |
| **Megjegyzés**      | Negatív teszt: AI beragadása vagy „teleportálása”.|

---
| Mező                | Tartalom                                                                                                                                          |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-05                                                                                                                                             |
| **Cél**             | Körözési rendszer helyes működésének vizsgálata.                                                                                                    |
| **Előfeltételek**   | A játékos elindította a játékot, rendőrautók aktívak.                                                                                             |
| **Lépések**         | 1. Kezdd 1 csillaggal.<br>2. Menekülj, hogy növekedjen a körözési szint.<br>3. Ellenőrizd, hogy idővel a csillagok száma növekszik.<br>4. Ellenőrizd, hogy a csillagok számával arányosan növekszik a rendőrök száma |
| **Elvárt eredmény** | Körözési szint növekedésekor több rendőr jelenik meg (max. 5), csillagok száma szinkronban van a rendőrökével.                                          |
| **Státusz**         |    Siker                                                                                                          |
| **Megjegyzés**      | Negatív teszt: több mint 5 rendőr vagy hibás csillagszám.                                                                                         |

---
| Mező                | Tartalom                                                                                                                                                |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-06                                                                                                                                                   |
| **Cél**             | Ellenőrizni, hogy a pont és idő kijelzés valós időben frissül.                                                                                          |
| **Előfeltételek**   | A játék folyamatban van.                                                                                                                                |
| **Lépések**         | 1. Játssz legalább 1 percet.<br>2. Ellenőrizd, hogy a pont és idő kijelzés változik.<br>3. Ellenőrizd, hogy a játék vége után, hogy a high score mentésre kerül-e.<br>4. Ellenőrizd, hogy ha megdöntöd a high score-t akkor az frissül-e  |
| **Elvárt eredmény** | Az idő számlál és a pontszám növekszik, Game Over után high score-ként tárolódik.                                                                       |
| **Státusz**         |     Siker                                                                                                                |
| **Megjegyzés**      | Ellenőrizd, hogy újrajtászás és kilépés esetén is teljesül                                                                                                   |
---

| Mező                | Tartalom                                                                                                                                                                              |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-07                                                                                                                                                                                 |
| **Cél**             | Ellenőrizni a Game Over logika helyes működését.                                                                                                                                      |
| **Előfeltételek**   | Játék aktív, legalább 1 rendőr jelen van, rendőr közel van a játékoshoz.                                                                                                                           |
| **Lépések**         | 1. Engedd, hogy a rendőr közel érjen.<br>2. Figyeld, hogy elindul-e a visszaszámláló.<br>3. Ha lejár → Game Over képernyő megjelenik.<br>4. Teszteld az újraindítás és főmenü gombot. |
| **Elvárt eredmény** | Game Over képernyő megjelenik, funkcionális gombok működnek. Elmenekülés esetén a számláló újraindul             .                                                                                                          |
| **Státusz**         |   Siker                                                                                                                                                                   |
| **Megjegyzés**      | Ellenőrizd többféle körözési szint mellett is. Többszöri/sorozatos közelengedés során is teszteld, hogy mindig újraindul-e a számláló                                                                                                                                         |

---

| Mező                | Tartalom                                                                                                                                                               |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-08                                                                                                                                                                  |
| **Cél**             | Ellenőrizni a hanghatásokat (ütközés, sziréna) és a zenéket.                                                                                                                 |
| **Előfeltételek**   | A játékban aktív a hangrendszer.                                                                                                                                         |
| **Lépések**         | 1. Ütközz → ütközési hang.<br>2. Rendőr közelít → szirénahang.<br>3. Ellenőrizd, hogy hangok nem ismétlődnek túl gyakran.<br>4. Ellenőrizd, hogy a főmenüben és a játékban egyaránt hallható egy háttérzene |
| **Elvárt eredmény** | Minden eseményhez megfelelő hanghatás társul, nincs torzulás.                                                                                                          |
| **Státusz**         | Siker                                                                                                                                  |
| **Megjegyzés**      | Teszteld külön platformokon (Web, Asztali alkalmazás).                                                                                                                 |

---

| Mező                | Tartalom                                                                                                                                  |
| ------------------- | ----------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-09                                                                                                                                     |
| **Cél**             | Ellenőrizni a menürendszer működését (főmenü, pause, game over).                                                                          |
| **Előfeltételek**   | A játék  elindítva.                                                                                                           |
| **Lépések**         | 1. Főmenüből:<br> - Válaszd a „Start" opciót<br>- Válaszd a "Quit" opciót.<br>2. Nyomd meg az "Esc" gombot kétszer <br>3.Pause menüből:<br> - Válaszd a „Resume" opciót<br>- Válaszd a "Restart" opciót.<br>- Válaszd a "Main Menu" opciót.<br>4. Game Over után:<br>- Válaszd a "Restart" opciót.<br>- Válaszd a „Main Menu” opciót.|
| **Elvárt eredmény** | Minden menüpont működik, átirányítás hibátlan. Azaz:<br>1. Főmenüből:<br> - Elindul a játék<br>- Kilépsz a játékból <br> 2. Első megnyomáskor a Pause menü megnyílik, újboli megnyomáskor eltűnik<br>3. Pause menüből:<br> - Folytatódik a játék <br>- Újrakezdődik a játék<br>- Visszalépsz a főmenübe<br>4. Game Over után:<br>- Újrakezdődik a játék<br>- Visszalépsz a főmenübe                                                                                           |
| **Státusz**         |   Siker                                                                                                    |
| **Megjegyzés**      | Ellenőrizd, hogy háttérzene is indul a menüben.                                                                                           |

---

| Mező                | Tartalom                                                                                                                                             |
| ------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------- |
| **TC_ID**           | TC-10                                                                                                                                                |
| **Cél**             | Ellenőrizni, hogy a játékos ki tud-e esni a játéktérből                                                                                         |
| **Előfeltételek**   | A játék aktív. Létezik a környezet.                                                                                                                             |
| **Lépések**         | 1. Fedezd fel a pályát<br>2. Próbálj találni olyan pontot, ahol ki lehet esni a pályáról<br> |
| **Elvárt eredmény** | A játéktér teljesen körülzárt. A játékos a teljes játék ideje alatt a pályán marad.                                                                                  |
| **Státusz**         |   Siker                                                                                                                                  |
| **Megjegyzés**      | Teszteld városi és vidéki pályán is.                                                                                                                 |
