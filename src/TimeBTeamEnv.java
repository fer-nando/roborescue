
import jason.Ambiente;
import jason.asSyntax.Structure;

import java.rmi.RemoteException;

import robocode.rescue.RoboAction;
import robocode.rescue.RoboInfo;

public class TimeBTeamEnv extends Ambiente {

    private final int numRobos = 5;
    private RoboInfo[] robos;

    // Para inicializacoes necessarias
    @Override
    public void setup() {
        robos = new RoboInfo[numRobos];
    }

    @Override
    public boolean executeAction(String ag, Structure action) {

        // System.out.println(ag + " - Acao: " + action);

        try {
            robos = getReferenciaServidor().getTeamInfo(myTeam);
            mainLoop();
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return true;
    }

    public void mainLoop() throws RemoteException {

        int robo;
        RoboInfo refem = getReferenciaServidor().getRoboInfo(myTeam, RoboInfo.Refem);
        double xRefem = refem.getX();
        double yRefem = refem.getY();
        RoboAction action = new RoboAction();

        for (robo = 1; robo < numRobos; robo++) {

            action.acao = RoboAction.DO_BOTH;
            action.asynchronous = false;

            if (xRefem < robos[robo].getX() && yRefem > robos[robo].getY()) {
                action.turnAng = 1.0;
            } else if (xRefem < robos[robo].getX() && yRefem < robos[robo].getY()) {
                action.turnAng = -1.0;

            }
            
            if (robos[robo].getX() - xRefem > 100) {
                action.distance = 40.0;
                if (robos[robo].getHeading() < 180.0) {
                    action.distance *= -1;
                }
            } else {
                if (robos[robo].getHeading() > 180.0) {
                    action.asynchronous = false;
                    action.acao = RoboAction.TURN_LEFT;
                    action.turnAng = 180.0;
                } else {
                    action.distance = 40.0;
                }
            }

            getReferenciaServidor().setAction(myTeam, robo, action);
        }
    }
}
