# User Story #1

| Cím: Környezet | Prioritás: Az első sprint |
|----------|----------|
| **User Story:** Mint játékos, azt szeretném, hogy legyen egy jól kidolgozott érdekes városi ökosszisztéma, ahol a leendő zsernyákok elől menekülni lehessen, megzavarva minden NPC életét. Valamint legyen egy városon kivüli környezet is, a könnyebb menekülés érdekében. |
---
**Elfogadási Kritérium:**  
- Egy jól megtervezett város 
- Több lehetséges menekülési útvonal
- Élvezhető Design
- Egyszerű kevés akadállyal rendelkező városon kivüli környezet 
---

# User Story #2

| Cím: Hitbox & Colliders | Prioritás: A második sprint |
|----------|----------|
| **User Story:** Mint játékos, azt szeretném, hogy legyen egy müködő, precíz Hitboxxal rendelkező környezet és ellenfél, ne legyenek benne könnyen észrevehető hibák. Fontos hiszen nem a legjobb érzés, ha játék közbe bele buggol az autó a falba, vagy esetleg kiesne a pályáról. |
---
**Elfogadási Kritérium:**  
- Precízen megrajzolt Collider rendszer 
- Pálya szélén elég akadály vagy fal, ami megakadályozza a játékost a semmibe zuhanástól
---

# User Story #3

| Cím: NavMesh AI | Prioritás: A harmadik sprint |
|----------|----------|
| **User Story:** Mint játékos, azt szeretném, hogy ahogy telik az idő a játékban, egyre több zsaru kergessen, szeretnék egy bünöző bőrébe bújni, és átélni egy igazi autós üldözést. Egy jól betanított, AI vezesse a rendörőket, amik megpróbálnak engem megállitani. |
---
**Elfogadási Kritérium:**  
- Jól betanult, Pathfinder algoritmus használó AI ellenfelek
- Kerülje ki az összes akadályt
- Dinamikusan, tudja kergetni a Playert
- Legyen újrafelhasználható
---

# User Story #4

| Cím | UserStory | Elfogadási kritérium|Sprint |
|-----|-----------|---------------------|---------|
|Game Over|Játékosként azt szeretném, hogyha a rendőrök sokáig a közelemben vannak és nincs menekvés a kutyaszorítóból, akkor a vége legyen a játéknak. A segítségemre szolgáljon továbbá egy visszaszámlákó, mielőtt elkapnának, hogy tudjam, hogy mennyi időm van még elhúznom onnan. | Ahogy egy bizonyos közelségbe ér az ágens, akkor induljon el egy visszaszámláló (coroutine) jelezve az elmenekülsére fennmaradt időt. Továbbá ha a visszaszámláló lejár, akkor jelenjen meg a game over felület, ahol újra tudom indítani a játékot vagy ki tudok lépni a főmenübe. |Sprint #4 |


# User Story #5

| Cím | UserStory | Elfogadási kritérium|Sprint |
|-----|-----------|---------------------|---------|
|SoundFX & Music| **Given:** Játékosként azt szeretném, hogy a hangulatot egy jó főmenű zene adja meg. Tudjam, hogy mikor vannak a közelemben a zsernyákok, ezért csengjen a fülemben az irritáló szirénájuk. Ez által tudjam, hogy mikor kell a gázra taposni. Valamint fontos, hogyha ütközés történik, akkor az hangilag is érezhető legyen. | **When:** Amig a főmenűben vagyunk addig valamilyen aláfestő zene legyen, mindaddig mig a játék el nem kezdődik. **Then:** Ha közel kerűl a rendőr a playerhez, akkor lehessen hallani a sziréna hangját. Bármilyen CollisionEnter-nél egy ütközés hang játszodjon le de fontos, hogy esetleges bugok esetén, többszörösen ne tudjon elindulni a Sound Effect. |Sprint #5 |

# User Story #6 (WEB)

| Cím | UserStory | Elfogadási kritérium|Sprint |
|-----|-----------|---------------------|---------|
| NLP Search Motor | Mint felhasználó azt szeretném, a játékkönyvtárban lehetőség legyen valami AI-al megspékelt keresésre, hogy könnyeben megtalálhassam a számomra alkalmas játékokat. Egy rövid leirás alapján, adja ki azokat a játékot/játékokat ami leginkább illeszkednek a leirásomra. | Kell egy adatállomány, ami minden játékról, ad egy közel 50 soros leirást. Erre az adatállományra betanitunk egy meglévő NLP transformer modellt, ami több nyelvű működésre képes. A hosszó leirást Sentence-Bert api-val mondatokra szedjűk, ezt tokenizáljuk, valamint létrehozzuk a mondatok beágyazott(embedding) vektorját. Igy a modellünk a rövid leirás alapján, megkeresi, hogy melyik mondatra illeszkedik a legjobban egy softmax aktivációs függvény segitségével. Ezt a modellt exportálni kell és integrálni a Java Backendbe, úgy hogy tudjon hozzá kéréseket intézni, és tudjanak együtt kommunikálni. | Sprint #6-7 |