package jason;

import jason.JasonException;

import java.rmi.RemoteException;

import util.ConstantesExecucao;
import util.Transferencia;


public class Jason {

	public Jason() throws JasonException {

		String arg[] = new String[1];
		arg[0] = "masMain.mas2j";
		jason.infra.centralised.RunCentralisedMAS.main(arg);
	}

	public static void main(String[] args) throws JasonException, RemoteException {

		ConstantesExecucao.start("client");
		//Transferencia.transfere();
		// Jason
		new Jason();
	}
}
