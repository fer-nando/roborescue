package conexao.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

import robocode.rescue.RoboInfo;


public interface InterfaceServidor extends Remote {

	public boolean isStarted()throws RemoteException;
	public RoboInfo getRoboInfo(String TeamName, int robo)throws RemoteException;
	public RoboInfo[] getTeamInfo(String TeamName)throws RemoteException;
	
}
