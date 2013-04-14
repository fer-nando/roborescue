
import jason.Ambiente;
import jason.asSyntax.Structure;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import robocode.rescue.RoboAction;
import robocode.rescue.RoboInfo;

public class TimeATeamEnv extends Ambiente {

    private final int numRobos = 5;
    private RoboInfo[] robos;
    
    //Para inicializacoes necessarias
    @Override
    public void setup() {
        robos = new RoboInfo[numRobos];
    }

    @Override
    public boolean executeAction(String ag, Structure action) {

        //System.out.println(ag + " - Acao: " + action);

        try {
            robos = getReferenciaServidor().getTeamInfo(myTeam);
            mainLoop();
            Thread.sleep(50);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException ex) {
            Logger.getLogger(TimeATeamEnv.class.getName()).log(Level.SEVERE, null, ex);
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
            action.asynchronous = true;
            
            
            if (xRefem > robos[robo].getX() && yRefem > robos[robo].getY()) {
                action.turnAng = 0.0;
            } else if (xRefem > robos[robo].getX() && yRefem < robos[robo].getY()) {
                action.turnAng = 0.0;
            }

            if (xRefem - robos[robo].getX() > 130) {
                action.distance = 100.0;
                if (robos[robo].getHeading() > 180.0) {
                    action.distance *= -1;
                }
            } else {
                if (robos[robo].getHeading() < 180.0) {
                    action.asynchronous = false;
                    action.acao = RoboAction.TURN_LEFT;
                    action.turnAng = 180.0;
                } else {
                    action.distance = 100.0;
                }
            }
            
            getReferenciaServidor().setAction(myTeam, robo, action);

        }

    }
}
