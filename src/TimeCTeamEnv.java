
import jason.Ambiente;
import jason.asSyntax.Structure;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import robocode.rescue.RoboAction;
import robocode.rescue.RoboInfo;


public class TimeCTeamEnv extends Ambiente {
    
    private final int numRobos = 5;
    private RoboInfo[] robos;
    
    double radius = 200;
    double angle = 180;
    double distance = radius * angle * Math.PI / 180;
    boolean [] direction;
    
    //Para inicializacoes necessarias
    @Override
    public void setup() {
        robos = new RoboInfo[numRobos];
        direction = new boolean[numRobos];
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

            action.asynchronous = true;
            
            action.acao = RoboAction.RESUME;
            action.maxTurnRate = angle / (distance / robos[robo].getVelocity());
            
            if(robos[robo].getAction().state == RoboAction.DONE) {
                action.acao = RoboAction.DO_BOTH;
                if(!direction[robo]) {
                    action.turnAng = angle;
                    action.distance = distance;
                } else {
                    action.turnAng = -angle;
                    action.distance = -distance;
                }
                direction[robo] = !direction[robo];
            }
            
            getReferenciaServidor().setAction(myTeam, robo, action);

        }

    }
}
