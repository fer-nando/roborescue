package conexao;

import java.rmi.AlreadyBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import conexao.serventes.ServidorServente;

public class Servidor {

    int porta;

    public Servidor(int porta) {

        this.porta = porta;
    }

    public void iniciaServidor() throws RemoteException, AlreadyBoundException {
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }*/

        ServidorServente ss = new ServidorServente();
        Registry registry = LocateRegistry.createRegistry(porta);
        registry.bind("Servidor", ss);

        System.out.println("[Server] Server started in port " + porta);
    }
}
