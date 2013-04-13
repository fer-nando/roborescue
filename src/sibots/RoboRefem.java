package sibots;

import static robocode.util.Utils.normalAbsoluteAngle;
import static robocode.util.Utils.normalRelativeAngle;

import java.awt.Color;
import java.awt.Point;
import java.rmi.RemoteException;

import robocode.HitRobotEvent;
import robocode.HitWallEvent;
import robocode.rescue.RoboInfo;
import robocode.rescue.RoboClient;
import util.ConstantesExecucao;

public class RoboRefem extends RoboClient {

    private RoboInfo Target;

    public RoboRefem() {
    }

    @Override
    public void mainLoop() {
        procuraTarget();
    }

    private void procuraTarget() {
        try {
            if (Target != null) {
                Target = getTeammate(Target.getNumRobo());
                double distancia = Point.distance(Target.getX(), Target.getY(), info.getX(), info.getY());
                if (distancia > 150) {
                    Target = null;
                    setRobotColor();
                    referenciaServidor.setFollowing(team, -1);
                }
            }
            if (Target == null) {
                RoboInfo[] robots = getTeamInfo();
                for (RoboInfo ri : robots) {
                    if (ri.getNumRobo() != info.getNumRobo()) {
                        double distance = Point.distance(ri.getX(), ri.getY(), info.getX(), info.getY());
                        System.out.println(ri.getNumRobo() + " ; " + info.getNumRobo() + ":   " + distance);
                        if (distance <= 150) {
                            Target = ri;
                            System.out.println("Target:" + Target.getName());
                            referenciaServidor.setFollowing(team, ri.getNumRobo());
                            break;
                        }
                    }
                }
            }
            if (Target != null) {
                segueTarget();
            } else {
                System.out.println("doNothing");
                setVelocity(0);
                doNothing();
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        execute();
    }

    private void segueTarget() {
        System.out.println("Target:" + Target.getName());
        double angle = normalAbsoluteAngle(Math.atan2(Target.getX() - info.getX(), Target.getY() - info.getY()));
        double turn = normalRelativeAngle(angle - getGunHeadingRadians()) * 180.0 / Math.PI;
        System.out.println("Angulo:" + turn);
        if (turn >= 0 && turn <= 180) {
            setTurnRight(10);
        }
        if (turn < 0 && turn >= -180) {
            setTurnLeft(10);
        }

        //waitFor(new TurnCompleteCondition(this));
        setVelocity(Math.abs(Target.getVelocity()));
        if (Point.distance(Target.getX(), Target.getY(), info.getX(), info.getY()) < 60) {
            setVelocity(0);
            System.out.println("Velocidade: 0.0 (dist < 60)");
        } else {
            System.out.println("Velocidade: " + Target.getVelocity());
        }
    }

    @Override
    public void setRobotColor() {
        if (team.startsWith(ConstantesExecucao.nomeTeamA)) {
            setColors(Color.RED, Color.BLUE, Color.BLACK);
        } else if (team.startsWith(ConstantesExecucao.nomeTeamB)) {
            setColors(Color.RED, Color.GREEN, Color.BLACK);
        }
    }
    
    private void setVelocity(double velocityRate) {
        setMaxVelocity(velocityRate);
        if (velocityRate > 0) {
            setAhead(Double.POSITIVE_INFINITY);
        } else if (velocityRate < 0) {
            setBack(Double.POSITIVE_INFINITY);
        } else {
            setAhead(0);
        }
    }

    @Override
    public void onHitWall(HitWallEvent e) {
        setMaxVelocity(8);
        double angulo = e.getBearing();
        if (angulo > -90 && angulo <= 90) {
            setBack(10);
        } else {
            setAhead(10);
        }
        execute();
    }
    
    @Override
    public void onHitRobot(HitRobotEvent e) {
        double angulo = e.getBearing();
        if (angulo > -90 && angulo <= 90) {
            setBack(10);
        } else {
            setAhead(10);
        }
        execute();
    }
    
    @Override
    public void setup() {
        ConstantesExecucao.start("client");
    }
}
