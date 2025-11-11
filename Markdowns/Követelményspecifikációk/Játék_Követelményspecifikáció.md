# KÖVETELMÉNYSPECIFIKÁCIÓ
## 1. Bevezetés
A jelen dokumentum egy 3D-s, autós üldözésen alapuló játék (továbbiakban: a Rendszer) követelményeit, célját, működését és üzleti folyamatait írja le. A játék alapja, hogy a játékos egy városi környezetben szabadon közlekedhet, miközben NavMesh alapú mesterséges intelligenciával (AI) működő rendőrautók folyamatosan üldözik. A játék célja minél tovább túlélni, elkerülve a rendőrségi elfogást, miközben a játékos pontot szerez az eltelt idő és a túlélés alapján.


## 2. Jelenlegi helyzet leírása
A projekt jelenleg a tervezési fázisban van, a fejlesztés még nem kezdődött el. A játék fő céljai, funkciói, a célközönség és a technológiai platformok meghatározásra kerültek, azonban sem grafikai elemek, sem pálya, sem játékmenetbeli logika nincs még implementálva.
A tervezés során eddig az alábbi elemek kerültek áttekintésre és specifikálásra:
- A játék világának környezete városi és városon kívüli területre oszlik.
- A játékos egy járművet irányít, amelyet üldöző AI-vezérelt rendőrautók kergetnek.
- A játék működését egy wanted-star rendszer, pont- és időszámláló, valamint Game Over logika egészíti ki.
- Hanghatások és zenei háttér a hangulati élmény támogatására kerül bevezetésre.
A következő lépés a részletes követelmények véglegesítése és sprint-alapú fejlesztési terv felállítása.


## 3. Vágyálomrendszer leírása 

- Magával ragadó, újrajátszható, gyors tempójú autós hajsza élményt biztosít.
- A mesterséges intelligencia a játékos pozícióját és viselkedését figyelembe véve képes reagálni, útvonalat választani és csapdázni a játékost.
- A wanted-star rendszer dinamikusan növeli a játék kihívási szintjét.
- A játék díjazza a teljesítményt pontszám és high score formájában.
- A felhasználói felület egyértelmű és könnyen kezelhető (főmenü, szünet menü, játék vége képernyő).
- A játék képes rögzíteni és összehasonlítani a legmagasabb elért pontszámot, ezzel motiválva az újrajátszást.
- AI reakció még gyorsabb és adaptívabb (körbezárás, visszavágás).
- Wanted szint nem csak az idő alapján növekedhet, hanem a játékos agresszivitása alapján is (pl. ütközések).
- High score online megosztható opcionálisan.
- Különböző pályaterületek (parkolóház, sűrű belváros, ipari zóna).
- Az autó átfestése
- Térkép


## 4. Jogszabályi és ajánlási háttér 
A játékfejlesztésre közvetlen állami rendelet nem vonatkozik, azonban az alábbi ajánlások, elvek és keretrendszerek relevánsak:
|Típus|Leírás|
|---|---|
|Szoftverfejlesztési módszertan|Ajánlott Agile / Scrum módszertan használata a sprint struktúra már meghatározott részeit követve.|
|Játékfejlesztési ajánlások|Játékmechanika egyensúly, fokozatos nehézség, felhasználói élmény és hozzáférhetőség figyelembevétele.|
|Technikai szabványok|Unity Engine + NavMesh AI Navigation rendszerének alkalmazása.|
|Adatkezelés|High score adatok tárolása lokálisan, GDPR szerinti személyes adat nem kerül kezelésre.|

	
## 5. Üzleti folyamat modellje
#### 5.1  Játék indítása
- Játékos elindítja a játékot → Főmenü megjelenik.
- Játékos Start → Játéktér betöltése.
- Időszámláló és pontszám nullázódik.
#### 5.2 Játék közbeni működés
- Autó mozgása valós idejű irányítással.
- Kamera folyamatosan követi a játékost.
- AI rendőrautók NavMesh alapú üldözési logikával mozognak.
- Wanted szint idővel növekszik → Több rendőr spawnol.
- Rendőr túl messze kerül → Despawn + újrapozicionálás.
#### 5.3 Játék vége
- Rendőr jármű elkapja a játékost.
- Játékállapot lefagy → Game Over képernyő.
- High score összehasonlítás → Frissítés ha szükséges.
- Játékos választhat: Új játék vagy vissza Főmenübe.

## 6. Követelménylista 

| Azonosító | Név | Követelmény | Típus | Prioritás | Verzió |
|----------|-----|-------------|-------|-----------|--------|
| K-01 | Járműirányítás | A játékos autója W/A/S/D irányítással mozgatható. | Funkcionális | Magas | v0.1 |
| K-02 | Kamera-követés | A kamera folyamatosan kövesse a játékos járművét. | Funkcionális | Magas | v0.2 |
| K-03 | Collider rendszer | A pálya minden objektuma rendelkezzen precízen kialakított colliderrel. | Funkcionális | Magas | v0.3 |
| K-04 | Pályahatárok | A pálya szélei fizikailag határolva legyenek, hogy a játékos ne essen ki a játéktérről. | Funkcionális | Magas | v0.3 |
| K-05 | Városi pálya kialakítás | Nagyméretű, vizuálisan érdekes városi pálya legyen kialakítva több menekülési lehetőséggel. | Funkcionális | Magas | v0.3 |
| K-06 | Külső környezet | A városon kívüli környezet egyszerűbb, nyíltabb legyen, kevés akadállyal. | Funkcionális | Közepes | v0.3 |
| K-07 | AI Üldözés | AI rendőrautók NavMesh alapú útvonaltervezéssel üldözzék a játékost. | Funkcionális | Magas | v0.4 |
| K-08 | AI Akadálykerülés | Az AI képes legyen elkerülni az akadályokat és dinamikusan reagálni a játékos helyzetére. | Funkcionális | Magas | v0.4 |
| K-09 | Wanted alapú spawn | A wanted szint növekedésével több rendőr spawnoljon (max. 5). | Funkcionális | Magas | v0.5 |
| K-10 | Körözési szint jelzése | A wanted szint csillagokkal kerüljön kijelzésre. | Funkcionális | Magas | v0.5 |
| K-11 | Rendőr újrapozicionálás | A rendőrök despawnoljanak, ha túl távol kerülnek, majd újrapozícionálódjanak. | Funkcionális | Közepes | v0.6 |
| K-12 | Pont & Idő kijelzés | A pontszám és idő valós időben jelenjen meg a képernyőn. | Funkcionális | Közepes | v0.5 |
| K-13 | UI Menürendszer | A játék UI tartalmazza a főmenüt, pause menüt és game over képernyőt. | Funkcionális | Magas | v0.7 |
| K-14 | High Score összehasonlítás | A játék vége után a pontszám kerüljön összehasonlításra a high score értékkel. | Funkcionális | Magas | v0.5 |
| K-15 | Üldözési visszaszámláló | Ha a rendőrök a játékos közelébe érnek, induljon el visszaszámláló. | Funkcionális | Közepes | v0.7 |
| K-16 | Game Over logika | A visszaszámláló lejárta után Game Over képernyő jelenjen meg újrakezdés és menü opcióval. | Funkcionális | Magas | v0.7 |
| K-17 | Menü háttérzene | A főmenüben háttérzene szóljon. | Nem funkcionális | Alacsony | v0.8 |
| K-18 | Atmoszférikus sziréna hang | A rendőrautók közeledésekor szirénahang induljon el. | Funkcionális | Közepes | v0.8 |
| K-19 | Hanghatások támogatása | A játék futása során hanghatások legyenek (motor, ütközés, sziréna). | Nem funkcionális | Közepes | v0.8 |
| K-20 | Újrajátszhatóság | A játék legyen újrajátszható, betöltési idő minimális legyen. | Nem funkcionális | Közepes | v0.5 |
| K-21 | Pénz gyűjtés | A játék során a felhasználó tudjon pénz gyűjteni, amiket pályán tud felvenni | Funckiónális | Alacsony | v1.2 |
| K-22 | Mini map | A UI felületen jelenjen meg egy kis méretű térkép amelyen információt nyerhetünk a játékos és rendőrök pozicíójáról | Funkciónális | Alacsony | v1.3 |
| K-23 | Autó átfestés | Ha a játékos a menekülés közben beáll egy bizonyos garázsba festődjön át az autó és a körözési szint csökkenjen | Funkciónális| Alacsony | v1.5|




## 7. Fogalomszótár

NavMesh:	Útvonalháló alapú mozgástervezési rendszer Unity-ben.
Wanted-star rendszer:	Körözöttségi szint, amely meghatározza a rendőrautók számát és agresszivitását.
High Score:	A játék során valaha elért legmagasabb pontszám.
Despawn:	Játékbeli objektum eltávolítása és későbbi visszahelyezése más pozícióba.
UI:	Felhasználói felület (gombok, kijelzők, menük).
Collider(ütközésvizsgáló komponens):  A játékmotorokban egy olyan láthatatlan geometriai forma, amelyet egy objektumhoz rendelünk annak érdekében, hogy a rendszer felismerje az ütközéseket és a fizikai kölcsönhatásokat.

