# Contactsapi

## Feladat

Az alábbi végpontok megvalósítása:
- Kapcsolattartók listázása
- Kapcsolattartók listázása oldalszám szerint
- Kapcsolatartó részletezése
- Kapcsolattartó létrehozása
- Kapcsolattartó módosítása
- Kapcsolattartó törlése

## API dokumentáció:
https://documenter.getpostman.com/view/16745716/UVC9hR8V#0f7a49ad-492d-4f3f-90ac-6b3c07912983



# Használati útmutató

A projekt tartalmaz egy `docker-compose.yml` fájlt, ami adatbázist biztosít a fejlesztéshez.

## Konténerek indítása

Az indításhoz előfeltétel, hogy Docker (pl. Docker for Desktop) telepítve legyen a számítógépen.

Ezt követően parancssorból a konténerek egyszerűen elindíthatóak az alábbi parancs kiadásával:  
`docker-compose up`

Leállítani megszakítás küldésével (`CTRL+C`) lehetséges.

### Daemon mód

Ebben az esetben a konténerek a háttérben fognak futni.

Indítás: `docker-compose up -d`  
Leállítás: `docker-compose down`

Logok olvasása:  
`docker-compose logs -f database`  
`docker-compose logs -f mailhog`

## Adatbázis

Adatbázisként PostgreSQL került beállításra. A konténer nem került beállításra perzisztens volume, így a konténer
törlésekor elveszhet a benne tárolt adat.

Inicializáláskor az adatbázisba betöltésre kerül egy `company` tábla néhány példa adattal, ami a cég kapcsolat
kialakításához használható a feladatban.

### Csatlakozáshoz szükséges adatok

jDBC connection string: `jdbc:postgresql://localhost:15432/contactsapi`

Felhasználónév: `contactsapi`  
Jelszó: `contactsapi`  
Adatbázis: `contactsapi`
