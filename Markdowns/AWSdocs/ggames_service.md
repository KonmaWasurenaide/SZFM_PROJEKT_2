[Unit]
Description=GGames Spring Boot service
After=network.target

[Service]
User=ggames
WorkingDirectory=/opt/ggames
ExecStart=/usr/bin/java -jar /opt/ggames/app.jar --server.port=8080
Restart=always
RestartSec=3
# Low port bind jog:
AmbientCapabilities=CAP_NET_BIND_SERVICE
CapabilityBoundingSet=CAP_NET_BIND_SERVICE
NoNewPrivileges=true

[Install]
WantedBy=multi-user.target
