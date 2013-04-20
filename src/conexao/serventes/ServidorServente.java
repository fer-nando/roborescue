package conexao.serventes;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import robocode.rescue.RoboAction;
import robocode.rescue.RoboInfo;
import util.ConstantesExecucao;

import conexao.interfaces.InterfaceServidorJason;
import conexao.interfaces.InterfaceServidorRobocode;

@SuppressWarnings("serial")
public class ServidorServente extends UnicastRemoteObject
        implements InterfaceServidorJason, InterfaceServidorRobocode {

    private final int numRobos = 5;
    private int counterA = 0;
    private int counterB = 0;
    private RoboInfo[] robosTeamA;
    private RoboInfo[] robosTeamB;
    private int refemFollowingTeamA;
    private int refemFollowingTeamB;
    private double battleFieldHeight = 1500;
    private double battleFieldWidth = 2427;
    private boolean robotsStart = false;
    private boolean teamAStart = false;
    private boolean teamBStart = false;

    public ServidorServente() throws RemoteException {
        super();
        robosTeamA = new RoboInfo[numRobos];
        robosTeamB = new RoboInfo[numRobos];

        refemFollowingTeamA = -1;
        refemFollowingTeamB = -1;
    }

    //Metodos que serao utilizados pelos robos para informar suas posicoes
    @Override
    public void setRoboInfo(String TeamName, int robo, RoboInfo info) throws RemoteException {
        if (TeamName.startsWith(ConstantesExecucao.nomeTeamA)) {
            System.out.print("[Server] Add " + ConstantesExecucao.nomeTeamA);
            if(robo == 0) {
                System.out.println("Refem");
            } else {
                System.out.println("*(" + robo + ")");
            }
            if (robosTeamA[robo] == null) {
                robosTeamA[robo] = info;
                counterA++;
            }
        } else if (TeamName.startsWith(ConstantesExecucao.nomeTeamB)) {
            System.out.print("[Server] Add " + ConstantesExecucao.nomeTeamB);
            if(robo == 0) {
                System.out.println("Refem");
            } else {
                System.out.println("*(" + robo + ")");
            }
            if (robosTeamB[robo] == null) {
                robosTeamB[robo] = info;
                counterB++;
            }
        } else {
            System.err.println("[Server] Time " + TeamName + " nao encontrado");
        }

        if (counterA == numRobos && counterB == numRobos) {
            robotsStart = true;
            System.out.println("[Server] Robots ready");
            System.out.println("[Server] Waiting for players");
        }
    }

    @Override
    public void setRoboInfo(String TeamName, int robo, double x, double y, 
            double heading, double velocity, int state) throws RemoteException {
        RoboInfo roboI = getTeam(TeamName)[robo];
        roboI.setX(x);
        roboI.setY(y);
        roboI.setHeading(heading);
        roboI.setVelocity(velocity);
        synchronized(roboI.getAction()) {
            if (roboI.getAction().state != RoboAction.NEW)
                roboI.getAction().state = state;
        }
    }

    @Override
    public RoboInfo getRoboInfo(String TeamName, int robo) throws RemoteException {
        RoboInfo info = getTeam(TeamName)[robo];
        return info;
    }

    private RoboInfo[] getTeam(String TeamName) {
        if (TeamName.startsWith(ConstantesExecucao.nomeTeamA)) {
            return robosTeamA;
        } else if (TeamName.startsWith(ConstantesExecucao.nomeTeamB)) {
            return robosTeamB;
        } else {
            System.err.println("[Server] Time " + TeamName + " nao encontrado");
            return null;
        }
    }

    @Override
    public RoboInfo[] getTeamInfo(String TeamName) throws RemoteException {

        return getTeam(TeamName);
    }

    @Override
    public boolean isRefemFollowing(String TeamName) throws RemoteException {
        if (TeamName.startsWith(ConstantesExecucao.nomeTeamA)) {
            if (refemFollowingTeamA == -1) {
                return false;
            } else {
                return true;
            }
        } else if (TeamName.startsWith(ConstantesExecucao.nomeTeamB)) {
            if (refemFollowingTeamB == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            System.err.println("[Server] Time " + TeamName + " nao encontrado");
            return false;
        }
    }

    @Override
    public RoboInfo getRefemFollowing(String TeamName) throws RemoteException {
        if (TeamName.startsWith(ConstantesExecucao.nomeTeamA)) {
            if (isRefemFollowing(TeamName)) {
                return robosTeamA[refemFollowingTeamA];
            } else {
                return null;
            }
        } else if (TeamName.startsWith(ConstantesExecucao.nomeTeamB)) {
            if (isRefemFollowing(TeamName)) {
                return robosTeamB[refemFollowingTeamB];
            } else {
                return null;
            }
        } else {
            System.err.println("[Server] Time " + TeamName + " nao encontrado");
            return null;
        }
    }

    @Override
    public void setFollowing(String TeamName, int robo) throws RemoteException {
        if (TeamName.startsWith(ConstantesExecucao.nomeTeamA)) {
            refemFollowingTeamA = robo;
        } else if (TeamName.startsWith(ConstantesExecucao.nomeTeamB)) {
            refemFollowingTeamB = robo;
        } else {
            System.err.println("[Server] Time" + TeamName + "nao encontrado");
        }
    }

    @Override
    public boolean isStarted() throws RemoteException {
        return (robotsStart && teamAStart && teamBStart);
    }

    @Override
    public void setLimits(double height, double width) throws RemoteException {
        battleFieldHeight = height;
        battleFieldWidth = width;
    }

    @Override
    public double[] getLimits() throws RemoteException {
        return (new double[]{battleFieldHeight, battleFieldWidth});
    }

    @Override
    public RoboAction getAction(String TeamName, int robo) throws RemoteException {
        RoboInfo roboI = getTeam(TeamName)[robo];
        synchronized(roboI.getAction()) {
            if (roboI.getAction().state == RoboAction.NEW) {
                roboI.getAction().state = RoboAction.FETCH;
            }
        }
        return roboI.getAction();
    }

    @Override
    public void setAction(String TeamName, int robo, RoboAction action) throws RemoteException {
        RoboInfo roboI = getTeam(TeamName)[robo];
        synchronized(roboI.getAction()) {
            roboI.setAction(action);
        }
    }

    @Override
    public void setStart(String TeamName) throws RemoteException {
        if (TeamName.startsWith(ConstantesExecucao.nomeTeamA)) {
            teamAStart = true;
            System.out.println("[Server] " + ConstantesExecucao.nomeTeamA + " joined");
        } else if (TeamName.startsWith(ConstantesExecucao.nomeTeamB)) {
            teamBStart = true;
            System.out.println("[Server] " + ConstantesExecucao.nomeTeamB + " joined");
        }
        if (robotsStart && teamAStart && teamBStart) {
            System.out.println("[Server] START!");
        }
    }
}
