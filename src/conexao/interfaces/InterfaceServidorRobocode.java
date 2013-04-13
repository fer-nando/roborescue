package conexao.interfaces;

import java.rmi.RemoteException;

import robocode.rescue.RoboAction;
import robocode.rescue.RoboInfo;

public interface InterfaceServidorRobocode extends InterfaceServidor {

	//metodos que devem ser chamdos pelo robo
	public RoboAction getAction(String TeamName, int robo) 
                throws RemoteException;
	public void setRoboInfo(String TeamName, int robo, RoboInfo info) 
                throws RemoteException;
	public void setRoboInfo(String TeamName, int robo, double x, double y, 
                double heading, double velocity, int state) 
                throws RemoteException;
	public void setLimits(double height, double width) 
                throws RemoteException;
	
	//metodos que sao chamados pelo refem
	public void setFollowing(String TeamName, int robo) 
                throws RemoteException;
}
