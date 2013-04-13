#!/bin/bash

sair()
{
	if [ "$1" -gt 0 ]; then kill $1; fi
}


clear
echo "Inicializando Robo Rescue - Client side ..."
echo ""

trap 'sair $JASON; exit 0' 2

java -cp ".:dist/roborescue.jar:dist/lib/*" \
	jason.Jason &
JASON=$!

read -n 1

sair $JASON

