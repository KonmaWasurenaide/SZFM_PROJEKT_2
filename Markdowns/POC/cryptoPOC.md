# Proof of Concept Jelentés  
**Téma:** Kriptovaluta-alapú fizetési rendszer integrálása a KuspGames platformra    

---

## 1. Célkitűzés
A PoC célja annak vizsgálata volt, hogy kriptovalutával (pl. Bitcoin, Ethereum) lehessen fizetni a KuspGames digitális termékeiért, biztonságos és automatizált módon.

## 2. Elvárt eredmény
Sikeresnek tekintettük volna a PoC-ot, ha:
- a kriptós fizetések technikailag hibamentesen végbemennek;  
- a tranzakciók automatikusan rögzülnek és megerősítést kapnak a backendben;  
- a megoldás megfelel a vonatkozó jogi és pénzügyi előírásoknak.

## 3. Tervezés
- Megtekintett szolgáltatók: **Coinbase Commerce**, **Binance Pay**.
- Backend: tranzakció-verifikáció és rendeléskezelés tesztelése, visszatérítési folyamatok vizsgálata.
- Jogi vonzat vizsgálata

## 4. Eredmények és tapasztalatok
- **Jogilag nem kivitelezhető** a publikus bevezetés: a magyar szabályozás korlátai és a szolgáltatók AML/KYC követelményei (VASP-engedély) akadályozták az éles üzemet.

## 5. POC nembeteljesülésének okai
- Szabályozási korlátok (Magyar Nemzeti Bank és hazai jogi státusz).  
- Jogbizonytalanság: kriptó fizetés jogi megítélése (barter vs. pénzügyi szolgáltatás).  

## 6. Következtetések
A PoC **nem vezetett éles bevezetéshez**, de feltérképezte a technikai megvalósíthatóságot és a jogi/működési akadályokat. A technológia készen áll, a szabályozási környezet viszont nem tette lehetővé a bevezetést.

## 7. Tanulságok és javaslatok
- Technika OK, **jogi előkészítés szükséges**.  
- Érdemes figyelni az EU MiCA és annak hazai implementációját.  
- Alternatíva: engedéllyel rendelkező közvetítő vagy automatikus fiat-konverziót végző szolgáltató bevonása. 
