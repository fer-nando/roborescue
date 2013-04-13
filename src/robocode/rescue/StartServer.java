package robocode.rescue;

import jason.Jason;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.ConstantesExecucao;
import util.Transferencia;

import conexao.Servidor;

public class StartServer {

    /**
     * @param args
     */
    public static void main(String[] args) {
        ConstantesExecucao.start("client");
        //Transferencia.transfere();

        Servidor servidor = new Servidor(1313);

        try {
            servidor.iniciaServidor();
        } catch (java.rmi.AlreadyBoundException ex) {
            Logger.getLogger(Jason.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RemoteException ex) {
            Logger.getLogger(Jason.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
