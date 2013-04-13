package robocode.rescue;

import java.io.Serializable;

public class RoboInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    public static final int Refem = 0;
    public static final int Robo1 = 1;
    public static final int Robo2 = 2;
    public static final int Robo3 = 3;
    public static final int Robo4 = 4;
    
    private double height;
    private double width;
    private double x;
    private double y;
    private double velocity;
    private double heading;
    private String name;
    private int numRobo;
    private RoboAction action;

    public RoboInfo(String name) {
        this.name = name;
        init();
    }

    private void init() {
        action = new RoboAction();
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHeading() {
        return heading;
    }

    public void setHeading(double heading) {
        this.heading = heading;
    }

    public double getVelocity() {
        return velocity;
    }

    public void setVelocity(double velocity) {
        this.velocity = velocity;
    }

    public RoboAction getAction() {
        return action;
    }

    public void setAction(RoboAction action) {
        this.action.acao = action.acao;
        this.action.maxTurnRate = action.maxTurnRate;
        this.action.maxVelocity = action.maxVelocity;
        if (!action.acao.equals(RoboAction.RESUME)) {
            this.action.asynchronous = action.asynchronous;
            this.action.distance = action.distance;
            this.action.turnAng = action.turnAng;
            this.action.state = action.state;
        }
    }

    public String getName() {
        return name;
    }

    public int getNumRobo() {
        return numRobo;
    }

    public void setNumRobo(int numRobo) {
        this.numRobo = numRobo;
    }
}