package conection.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import robocode.rescue.RobotInfo;


public interface ServerInterface extends Remote {

	public boolean isStarted()throws RemoteException;
    /*
	public RobotInfo getRoboInfo(String TeamName, int robo)throws RemoteException;
	public RobotInfo[] getTeamInfo(String TeamName)throws RemoteException;
    */
	
}
