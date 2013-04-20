package robocode.rescue;

import java.awt.Color;
import java.rmi.RemoteException;

import robocode.TeamRobot;
import util.ConstantesExecucao;
import conexao.Cliente;
import conexao.interfaces.InterfaceServidorRobocode;


public abstract class RoboClient extends TeamRobot {

    protected RoboInfo info;
    protected Cliente cliente;
    protected InterfaceServidorRobocode referenciaServidor;
    protected String team;
    protected String enemyTeam;
    private static int numTeamA = 0;
    private static int numTeamB = 0;

    public RoboClient() {
        System.out.println("Iniciando servidor RMI no robo.");

        cliente = new Cliente(1313, "localhost");
        cliente.iniciaCliente();
        referenciaServidor = (InterfaceServidorRobocode) cliente.getReferenciaServidor();
    }

    public void run() {
        try {
            setupRobo();
        } catch (RemoteException e1) {
            e1.printStackTrace();
            return;
        }

        while (true) {
            try {
                if (referenciaServidor.isStarted()) {
                    mainLoop();
                } else {
                    doNothing();
                    execute();
                }
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private void setupRobo() throws RemoteException {

        /**
         * Cria Thread para o robo ficar atualizando as informacoes dele no
         * servidor*
         */
        Runnable update = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        setRoboInfo();
                        if (info.getNumRobo() != 0) {
                            getRoboAction();
                        }
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

            }
        };
        Thread t = new Thread(update);

        String name = getName();
        info = new RoboInfo(name);
        //int numRobo = Integer.parseInt(name.substring(name.indexOf('(')+1, name.indexOf(')')));
        //info.setNumRobo(numRobo);
        info.setHeight(getHeight());
        info.setWidth(getWidth());
        info.setX(getX());
        info.setY(getY());
        info.setVelocity(getVelocity());

        if (name.contains("Refem")) {
            info.setNumRobo(RoboInfo.Refem);
            if (getTeammates()[1].contains(ConstantesExecucao.nomeTeamA)) {
                team = ConstantesExecucao.nomeTeamA;
                enemyTeam = ConstantesExecucao.nomeTeamB;
            } else if (getTeammates()[1].contains(ConstantesExecucao.nomeTeamB)) {
                team = ConstantesExecucao.nomeTeamB;
                enemyTeam = ConstantesExecucao.nomeTeamA;
            } else {
                System.out.println("Nao pertence a nenhum time");
            }
        } else {
            if (name.contains(ConstantesExecucao.nomeTeamA)) {
                team = ConstantesExecucao.nomeTeamA;
                enemyTeam = ConstantesExecucao.nomeTeamB;
                info.setNumRobo(++numTeamA);
            } else if (name.contains(ConstantesExecucao.nomeTeamB)) {
                team = ConstantesExecucao.nomeTeamB;
                enemyTeam = ConstantesExecucao.nomeTeamA;
                info.setNumRobo(++numTeamB);
            }
        }
        System.out.println(team);
        System.out.println(info.getNumRobo());
        referenciaServidor.setRoboInfo(team, info.getNumRobo(), info);

        setup();
        setRobotColor();

        t.start();
    }

    public abstract void mainLoop();

    public RoboInfo[] getTeamInfo() {

        try {
            RoboInfo[] teamInfo = referenciaServidor.getTeamInfo(team);
            return teamInfo;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public RoboInfo getTeammate(int robo) {
        try {
            RoboInfo teammate = referenciaServidor.getRoboInfo(team, robo);
            return teammate;
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public void getRoboAction() {
        try {
            RoboAction action = referenciaServidor.getAction(team, info.getNumRobo());
            info.setAction(action);

        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setRoboInfo() {

        //System.out.println("dist rem: " + getDistanceRemaining() +
        //        "\nturn rem: " + getTurnRemaining());
        info.setHeading(getHeading());
        info.setX(getX());
        info.setY(getY());
        info.setVelocity(getVelocity());
        try {
            referenciaServidor.setRoboInfo(team, info.getNumRobo(), info.getX(),
                    info.getY(), info.getHeading(), info.getVelocity(), info.getAction().state);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void setRobotColor() {
        if (team.contains(ConstantesExecucao.nomeTeamA)) {
            setColors(Color.BLUE, Color.BLUE, Color.BLACK);
        } else if (team.contains(ConstantesExecucao.nomeTeamB)) {
            setColors(Color.GREEN, Color.GREEN, Color.BLACK);
        }
    }

    public abstract void setup();
}
