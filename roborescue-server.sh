#!/bin/bash

sair()
{
	if [ "$1" -gt 0 ]; then kill $1; fi
}


clear
echo "Inicializando Robo Rescue - Server side ..."
echo ""

trap 'sair $SERVER; exit 0' 2

java 	-cp "dist/roborescue.jar:dist/lib/*" \
	-Xmx512M \
	-DNOSECURITY=true \
        -Djava.rmi.server=$1 \
	conection.ServerWindow &
SERVER=$!

read -n 1

sair $SERVER

