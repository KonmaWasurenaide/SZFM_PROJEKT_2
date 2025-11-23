    
server {
    listen 80;
    server_name kusp.games www.kusp.games;

    # HTTP → HTTPS, keep your "/" → "/login" behavior
    location = / {
        return 301 https://$host/login;
    }
    location / {
        return 301 https://$host$request_uri;
    }
}

server {
    listen 443 ssl http2;
    server_name kusp.games www.kusp.games;

    ssl_certificate           /etc/ssl/kusp.games/fullchain.pem;
    ssl_certificate_key       /etc/ssl/kusp.games/kusp.games.key;

    ssl_trusted_certificate   /etc/ssl/kusp.games/kusp_games.ca-bundle;

    ssl_session_timeout 1d;
    ssl_session_cache shared:SSL:10m;
    ssl_session_tickets off;
    ssl_protocols TLSv1.2 TLSv1.3;
    ssl_ciphers HIGH:!aNULL:!MD5;
    ssl_prefer_server_ciphers off;
    ssl_stapling on;
    ssl_stapling_verify on;

    # Keep "/" → "/login" on HTTPS too
    location = / {
        return 302 /login;
    }

    # Proxy everything else to Spring Boot on 8080
    location / {
        proxy_pass http://localhost:8080;
        proxy_http_version 1.1;
        proxy_set_header Host              $host;
        proxy_set_header X-Real-IP         $remote_addr;
        proxy_set_header X-Forwarded-For   $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
