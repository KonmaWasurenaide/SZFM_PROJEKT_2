# **Útmutató a játék (Unity projekt) továbbfejlesztéséhez**

Ha a fejlesztő csak a játékot szeretné folytatni a projektből, az alábbi lépések és mappák szükségesek.

---

## **1. Szükséges mappák a repositoryból**

A Unity-projekt teljes egészében a következő könyvtárakban található:

* **`Assets/`** – a játék összes forráskódja, prefabjai, scene-jei, modellei, scriptjei. *(Kötelező)*
* **`ProjectSettings/`** – Unity projektbeállítások, Input, Physics, Layers stb. *(Kötelező)*
* **`Packages/`** – Unity csomagdefiníciók (manifest.json). *(Kötelező)*

Opcionális, de hasznos:

* **`UserSettings/`** – személyes editorbeállítások (nem szükséges továbbfejlesztéshez).
* **`Library/`** – **NE töltsd fel**, Unity újragenerálja.

---

## **2. Ajánlott fejlesztési folyamat**

### **A) Fork készítése**

1. Lépj a repo oldalára:
   `github.com/KonmaWasurenaide/SZFM_PROJEKT_2`
2. Kattints a **Fork** gombra.
3. Hozd létre a saját példányodat → itt tudsz önállóan dolgozni.

### **B) Klónozás**

Miután elkészült a fork, klónozd:

```bash
git clone https://github.com/<felhasznalo>/<forkolt_repo>.git
```

---

## **3. Unity projekt megnyitása**

1. Telepíts egy kompatibilis Unity verziót (amit a projekt használ — ha nem tudod, a `ProjectSettings/ProjectVersion.txt` megmondja).
2. Nyisd meg a **Unity Hub**-ot.
3. Kattints **Add project** → válaszd ki a klónozott mappa gyökérkönyvtárát.
4. Unity automatikusan importálja az Assets-et és létrehozza a `Library/` mappát.

Ezután a játék azonnal futtatható a Play gombbal.

---

## **4. Új fejlesztések hozzáadása**

A fejlesztő innen szabadon tud:

* új scene-eket létrehozni,
* scriptet írni az `Assets/Scripts` alá,
* modelleket, UI-elemeket vagy audio-fájlokat importálni,
* meglévő rendszereket bővíteni (mozgás, AI, UI, gameplay stb.).

---

## **5. Változások visszaküldése (Pull Request)**

Ha szeretné visszaküldeni a fejlesztést az eredeti projektbe:

1. Push a saját forkjába.
2. Menjen vissza GitHubra → **Pull Request**.
3. Írja le röviden, mit módosított vagy javított.

A maintainer ezután el tudja bírálni és be tudja olvasztani.

---

## **6. Ajánlott mappastruktúra új fejlesztésekhez**

* Új scriptek → `Assets/Scripts/`
* Új UI → `Assets/UI/` vagy `Assets/Prefabs/UI/`
* Új modellek → `Assets/Models/`
* Új scene → `Assets/Scenes/`


