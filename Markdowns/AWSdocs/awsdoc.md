### AWS konfiguráció

80-as és 443-as portok meg vannak nyitva egy nginx service-nek, ami továbbítja ezeket a kéréseket a 8080-as portra
A 8080-as portot figyeli egy springboot alkalmazás (Matyi), ami ubuntu service-ként van regisztrálva.
#### Springboot service
- .jar helye: /opt/ggames/app.jar
- Springboot updatelése: `sudo cp target/GGames-0.0.1-SNAPSHOT.jar /opt/ggames/app.jar`
- .service (konfigurációs file) helye : /etc/systemd/system/ggames.service
- sudo systemctl (start stop restart status) ggames
#### Pull and build
- `cd`
- `rm -rf SZFM_PROJEKT_2/`
- `git clone  https://github.com/KonmaWasurenaide/SZFM_PROJEKT_2.git`
- `mvn clean package -DskipTests`
- `sudo cp GGames-0.0.1-SNAPSHOT.jar /opt/ggames/app.jar`
- `sudo systemctl daemon-reload  `
- `sudo systemctl restart ggames  `
- `sudo systemctl status ggames  `
#### nginx
- Fő konfigurációs file: /etc/nginx/nginx.conf
- konfigurációs file editing: sudo nano /etc/nginx/sites-available/kusp.games
- sudo systemctl (start stop restart status) nginx.service

#### troubleshooting
- broken filelink in sites-enabled at nginx -- just remove it

