
start java -cp roborescue.jar -Djava.rmi.server.hostname=%1 conexao.StartServer
start java -cp roborescue.jar;lib//* -Xmx512M -DNOSECURITY=true robocode.rescue.StartRobocode
