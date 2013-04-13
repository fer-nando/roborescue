package conexao;

import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import conexao.interfaces.InterfaceServidor;

public class Cliente {

    private int porta;
    private String host;
    private InterfaceServidor referenciaServidor;

    public Cliente(int porta, String host) {
        this.porta = porta;
        this.host = host;
    }

    public void iniciaCliente() {

        ConnectServer cv = new ConnectServer(this, this.porta, this.host);
        cv.start();

        try {
            cv.join();
        } catch (InterruptedException ex) {
            System.err.println("Err: Join in Client.initClient()");
        }
    }

    public InterfaceServidor getReferenciaServidor() {
        return referenciaServidor;
    }

    public void setReferenciaServidor(InterfaceServidor referenciaServidor) {
        this.referenciaServidor = referenciaServidor;
    }
}

class ConnectServer extends Thread {

    private int maximoTentativas = 20;
    private InterfaceServidor referenciaServidor;
    private int porta;
    private String host;
    Cliente cliente;

    public ConnectServer(Cliente cliente, int porta, String host) {
        this.cliente = cliente;
        this.porta = porta;
        this.host = host;
    }

    @SuppressWarnings("static-access")
    public void run() {

        int tentativa = 0;
        boolean finished = false;

        System.out.println("[Client] Iniciando conexao");

        while (!finished && tentativa < maximoTentativas) {

            try {
                tentativa++;
                this.connect();
                finished = true;
                cliente.setReferenciaServidor(this.referenciaServidor);
                System.out.println("[Client] Fim da conexao");
            } catch (RemoteException ex) {
                System.err.println("[Client] Erro na conexao com os servers. Tentativa:" + tentativa + ". host:" + host + ". Port:" 
                        + porta + "\nTentativa restante:" + (this.maximoTentativas - tentativa));
            } catch (NotBoundException ex) {
                System.err.println("[Client] Erro na conexao com os servers. Tentativa:" + tentativa + ". host:" + host + ". Port:" 
                        + porta);
            }

            try {
                this.sleep(300);
            } catch (InterruptedException ex) {
                System.err.println("[Client] Erro na instrucao wait da Thread: ConnectServer");
            }
        }
    }

    public void connect() throws RemoteException, NotBoundException {
        /*if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }*/
        Registry referenciaRegistry = LocateRegistry.getRegistry(host, porta);
        this.referenciaServidor = (InterfaceServidor) referenciaRegistry.lookup("Servidor");
    }
}