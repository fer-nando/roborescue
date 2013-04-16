#!/bin/bash

sair()
{
	if [ "$1" -gt 0 ]; then kill $1; fi
	if [ "$2" -gt 0 ]; then kill $2; fi
}


clear
echo "Inicializando Robo Rescue - Server side ..."
echo ""

trap 'sair $SERVER $ROBOCODE; exit 0' 2

java 	-cp "dist/roborescue.jar" \
	-Djava.rmi.server.hostname=$1 \
	conexao.StartServer &
SERVER=$!
java 	-cp "dist/roborescue.jar:dist/lib/*" \
	-Xmx512M \
	-DNOSECURITY=true \
	robocode.rescue.StartRobocode &
ROBOCODE=$!

read -n 1

sair $SERVER $ROBOCODE

