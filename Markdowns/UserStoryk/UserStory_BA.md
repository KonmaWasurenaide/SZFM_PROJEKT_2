### Sprint 1
| Cím | UserStory | Elfogadási kritérium|Prioritás|
|-----|-----------|---------------------|---------|
|UI tervezés| Felhasználóként szeretném ha a weboldal átlátható és esztétikus legyen.| Kész UI tervek screenshotokkal, tervezési eszköz: Figma.|Sprint 1|

#### Elfogadási kritérium melléklete:
- Screenshot 1: regisztrációs (sign up) oldal
- Screenshot 2: belépési (login) oldal
- Screenshot 3: Főoldal
- Screenshot 4: Játék oldal

### Sprint 2
| Cím | UserStory | Elfogadási kritérium|Prioritás|
|-----|-----------|---------------------|---------|
|UI tervek implementálása és adatbázishoz kapcsolódás| Felhasználóként szeretném hogy a weboldal jól nézzen ki és tudjak regisztrálni.| Kész weboldal framework és database connection.|Sprint 2|

### Sprint 3
| Cím | UserStory | Elfogadási kritérium|Prioritás|
|-----|-----------|---------------------|---------|
|UI tervek implementálása és adatbázishoz kapcsolódás| Felhasználóként szeretném hogy a weboldal jól nézzen ki és tudjak regisztrálni.| Kész weboldal framework és database connection. Befejezni azt amit az Sprint 2-ben nem tudtam|Sprint 3|

### Sprint 4
| Cím | UserStory | Elfogadási kritérium|Prioritás|
|-----|-----------|---------------------|---------|
|Finomítgatás| Felhasználóként szeretném hogy a weboldal jól nézzen ki és tudjak regisztrálni. A password legyen elrejtve, a database ne legyen kitörölve újraindításkor| Password hiding, database persistance, etc|Sprint 4|

### Sprint 5
| Cím | UserStory | Elfogadási kritérium|Prioritás|
|-----|-----------|---------------------|---------|
|AWS struktúra helyreállítása és dokumentálása| Ez fejlesztőknek szól, nincs felhasználói request| AWS, weboldal online és működik. Részletes dokumentáció az AWS struktúráról.|Sprint 5|

notes:
AWS server structure:
- springboot database
- nginx redirection from port80 to port8080 for safety
- app.jar contains the springboot database
