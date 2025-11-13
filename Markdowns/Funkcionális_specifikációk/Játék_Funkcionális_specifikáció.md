# FUNKCIONÁLIS SPECIFIKÁCIÓ

## 1. Bevezetés
A következő dokumentum célja, hogy bemutassa egy 3D-s autós üldözős játék részletesebb működését a felhasználói szemszögből. A játék célja, hogy a játékos egy városi környezetben minél tovább elkerülje az őt üldöző rendőröket, miközben pontokat gyűjt. A játék során különböző mechanikák, mint például körözési rendszer, AI és háttérzene támogatják az élményt.

A dokumentum az elkészült **Követelményspecifikációra** épül, annak pontjaira hivatkozva tartalmazza a funkcionális leírást, képernyőterveket, használati eseteket és a követelmények funkciókká való leképezését.


## 2. Jelenlegi helyzet leírása
A projekt tervezési szakaszban van. A fejlesztés még nem kezdődött el, de a fő koncepció és a felhasználói célok már meghatározásra kerültek.

*(lásd követelményspecifikáció 2. pontját)*


## 3. Vágyálomrendszer leírása
*(lásd követelményspecifikáció 3. pontját)*  


## 4. Jogszabályi, szabványi és módszertani háttér

*(lásd követelményspecifikáció 4. pontját)*  

## 5. Üzleti folyamat modellje
*(lásd követelményspecifikáció 5. pontját)*  

## 6. Követelménylista
*(lásd követelményspecifikáció 6. pontját)* 

## 7. Használati esetek 

#### Játék indítása
- **Szereplő:** Játékos  
- **Leírás:** A játékos elindítja a programot, és a főmenüből új játékot kezd.  
- **Események menete:**  
  - Főmenü megjelenik.  
  - Start gomb aktiválja a játékmenetet.  
  - Pálya és objektumok betöltése.  
- **Eredmény:** A játékos autója megjelenik a városi környezetben.


#### Autó irányítása
- **Szereplő:** Játékos  
- **Leírás:** A játékos a W/A/S/D billentyűkkel irányítja az autót.  
- **Feltételek:** A pálya betöltődött.  
- **Események:**  
  - W – előre, A – balra, D – jobbra, S – hátra.  
  - Az autó fizikai modell szerint viselkedik.  
- **Eredmény:** A jármű mozgása reagál a beviteli eseményekre.



#### AI Üldözés
- **Szereplő:** AI rendőrautó  
- **Leírás:** Az AI a játékos helyzete alapján tervezi meg az útvonalát és üldözi őt.  
- **Események:**  
  - AI megkapja a játékos aktuális pozícióját.  
  - NavMesh algoritmus útvonalat keres.  
  - AI mozgás végrehajtása.  
- **Kivétel:** Ha a rendőr túl messze kerül, akkor despawn + újrapozicionálás.



#### Pontszerzés és körözési rendszer
- **Szereplő:** Játékos, AI  
- **Leírás:** A játékos idővel pontot szerez, és nő a körözési szintje.  
- **Események:**  
  - Pontnövekedés az idő múlásával.  
  - Körözési szint csillaggal kijelezve.  
  - Több rendőr spawnol, ha a körözési szint nő.  
- **Eredmény:** Fokozatosan növekvő kihívás.



#### Game Over esemény
- **Szereplő:** Játékos, AI  
- **Leírás:** Ha a rendőrök huzamosabb ideig a közelben maradnak, a játék véget ér.  
- **Események:**  
  - AI a közelben → visszaszámláló indul.  
  - Lejár a visszaszámláló → Game Over képernyő.  
- **Eredmény:** A játék vége, pontszám mentése, új játék indítható.



#### Hanghatások és zene
- **Szereplő:** Rendszer  
- **Leírás:** A játék során különböző hanghatások jelennek meg.  
- **Események:**  
  - Menüben háttérzene.  
  - Sziréna a rendőr közeledésekor.  
  - Ütközési hang a jármű ütközésekor.  
- **Eredmény:** Hangulat- és visszajelzés támogatása.


#### Pénzgyűjtés és autó átfestés
- **Szereplő:** Játékos  
- **Leírás:** A játékos pénzt gyűjt, amit garázsban „lefestésre” használhat.  
- **Események:**  
  - Pénz pickup tárgyak felvétele.  
  - Bizonyos garázsban autó színe megváltozik, így a körözési szint csökken.  
- **Eredmény:** A játékos taktikailag kezelheti körözöttségét.



#### Mini térkép használata
- **Szereplő:** Játékos  
- **Leírás:** A képernyő sarkában kis térkép mutatja a pozíciókat.  
- **Eredmény:** Játékos könnyebben tájékozódik a környezetben.



## 8. Képernyőtervek
Az alábbi táblázat leíró szinten mutatja meg a képrenyőterveket.
*(További grafikus tervekért lásd GameUIPlan dokumentumát)*  

| Felület | Leírás |
|----------|--------|
| **Főmenü** | Tartalmazza a Start és Quit gombokat, továbbá egy animált hátteret. Mindemellett pedig egy kellemes háttérzene szól.|
| **Játékképernyő (HUD)** | Tartalmazza a pontszámot, időt, körözési csillagokat,   mini térképet és egy pénz számlálót. |
| **Pause menü** | Tartalmaz egy  Resume, Restart és egy Main Menu gombot. |
| **Game Over képernyő** | Megjeleníti a végső pontszámot, high score-t, valamint a Restart és egy Main Menu gombot.  |



## 9. Forgatókönyv 

**Cím:** Menekülés a városban  
- A játékos elindítja a játékot.  
- A főmenüből kiválasztja a Start-ot.  
- A város betöltődik, az autó mozgatható.  
- Az AI megkezdi az üldözést.  
- A játékos szűk utcákon manőverezik, miközben pontot és pénzt gyűjt.  
- Körözési szint nő, ezáltal egyre több rendőr jelenik meg.  
- A játékos garázsba hajt, ahol átfestik az autót, így körözési szint csökken.  
- Egy szerencsétlen ütközés után szirénák erősődő hangja hallható, visszaszámláló indul.  
- A játék véget ér, Game Over képernyő megjelenik.  
- Pontszám elmentődik, high score frissül.



## 10. Funkció – Követelmény megfeleltetés 

| Funkció (Use Case) | Kapcsolódó követelmény(ek) |
|--------------------|----------------------------|
| Játék indítása | K-03, K-04, K-05, K-06, K-13, K-17 |
| Autó irányítása | K-01, K-02 |
| AI Üldözés | K-07, K-08, K-11 |
| Pontszerzés és körözési rendszer | K-12, K-09, K-10, K-14 |
| Game Over esemény | K-15, K-16, K-20 |
| Hanghatások és zene | K-17, K-18, K-19 |
| Pénzgyűjtés és autó átfestés | K-21, K-23 |
| Mini térkép | K-22 |



## 11. Fogalomszótár

| Fogalom | Jelentés |
|----------|----------|
| NavMesh | Útvonalháló alapú mozgástervezési rendszer Unity-ben. |
| High Score | A játék során valaha elért legmagasabb pontszám. |
| Despawn | Játékbeli objektum eltávolítása és későbbi visszahelyezése más pozícióba. |
| UI | Felhasználói felület (gombok, kijelzők, menük). |
| Collider (ütközésvizsgáló komponens) | Láthatatlan geometriai forma, amely az objektumok fizikai ütközéseit és kölcsönhatásait érzékeli a játékmotorban. |
| AI (Artificial Intelligence) | Olyan logikai egység, amely az ellenfelek mozgását, döntéseit irányítja. |
