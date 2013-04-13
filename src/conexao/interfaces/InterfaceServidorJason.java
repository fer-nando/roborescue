package conexao.interfaces;

import java.rmi.RemoteException;

import robocode.rescue.RoboAction;
import robocode.rescue.RoboInfo;

public interface InterfaceServidorJason extends InterfaceServidor  {

	//metodos que sao chamados pelo ambiente
	public double[] getLimits()throws RemoteException;
	public RoboInfo getRefemFollowing(String TeamName)throws RemoteException;
	public boolean isRefemFollowing(String TeamName)throws RemoteException;
	public void setAction(String TeamName, int robo, RoboAction action)throws RemoteException;
	public void setStart(String TeamName)throws RemoteException;
}
