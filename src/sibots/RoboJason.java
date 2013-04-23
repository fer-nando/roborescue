package sibots;

import java.io.IOException;
import robocode.RobotDeathEvent;
import robocode.rescue.RoboAction;
import robocode.rescue.RoboClient;

public class RoboJason extends RoboClient {

    public RoboJason() {
        //Nao Utiliza nenhum metodo extendido da classe TeamRobot aqui se Nao o robo explode
        //Se precisar, utilize no mainLoop() ou depois
    }

    //Ja existe um while(true) chamando esse metodo;
    public void mainLoop() {
        
        //System.out.println(info.getAction().acao + "(" + info.getAction().state + ")");

        if (info.getAction().state != RoboAction.DONE) {
            
            setMaxTurnRate(info.getAction().maxTurnRate);
            setMaxVelocity(info.getAction().maxVelocity);
            
            if (info.getAction().state == RoboAction.FETCH) {
                        
                String action = info.getAction().acao;

                if (action.equals(RoboAction.DO_NOTHING)) {
                    doNothing();
                } else {
                    double newDist = info.getAction().distance;
                    double newAng = info.getAction().turnAng;

                    if (action.equals(RoboAction.DO_BOTH)) {
                        if (info.getAction().asynchronous) {
                            setTurnRight(newAng);
                            setAhead(newDist);
                        } else {
                            turnRight(newAng);
                            ahead(newDist);
                        }
                        System.out.println("Move " + newDist + "L" + newAng);
                    } else if (action.equals(RoboAction.TURN_LEFT)) {
                         setAhead(0);
                        if (info.getAction().asynchronous) {
                            setTurnLeft(newAng);
                        } else {
                            turnLeft(newAng);
                        }
                        System.out.println("TurnLeft " + newAng);
                    } else if (action.equals(RoboAction.TURN_RIGHT)) {
                        setAhead(0);
                        if (info.getAction().asynchronous) {
                            setTurnRight(newAng);
                        } else {
                            turnRight(newAng);
                        }
                        System.out.println("TurnRigh " + newAng);
                    } else if (action.equals(RoboAction.MOVE_AHEAD)) {
                        setTurnLeft(0);
                        if (info.getAction().asynchronous) {
                            setAhead(newDist);
                        } else {
                            ahead(newDist);
                        }
                        System.out.println("Ahead " + newDist);
                    } else if (action.equals(RoboAction.MOVE_BACK)) {
                        setTurnLeft(0);
                        if (info.getAction().asynchronous) {
                            setBack(newDist);
                        } else {
                            back(newDist);
                        }
                        System.out.println("Back " + newDist);
                    }
                }
                
                info.getAction().state = RoboAction.RUN;
            }
            
            if(getDistanceRemaining() == 0) {
                info.getAction().state = RoboAction.DONE;
                System.out.println(info.getAction().acao + "<- done");
            }
        }

        execute(); //necessario para que nao gere um erro xD

    }
    
    @Override
    public void onRobotDeath(RobotDeathEvent event) {
      try {
        if (getEnergy() == 0)
          broadcastMessage(getName()+",Morri!");
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    

    //O setup e rodado antes do mainLoop(), caso precise fazer alguma configuracao fora do loop principal
    @Override
    public void setup() {
        // TODO Auto-generated method stub
    }
}
