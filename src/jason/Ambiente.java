package jason;

import jason.asSyntax.Structure;
import jason.environment.Environment;

import java.rmi.RemoteException;

import util.ConstantesExecucao;
import conexao.Cliente;
import conexao.interfaces.InterfaceServidorJason;

public class Ambiente extends Environment {

    private Cliente cliente;
    private InterfaceServidorJason referenciaServidor;
    protected String myTeam;
    protected String enemyTeam;

    @Override
    public void init(String[] args) {

        cliente = new Cliente(1313, args[1]);
        cliente.iniciaCliente();
        System.out.println("[Client] Connection established with " + args[1] + ":1313");
        referenciaServidor = (InterfaceServidorJason) cliente.getReferenciaServidor();

        ConstantesExecucao.start("client");
        if (args[0].equals("TimeA")) {
            myTeam = ConstantesExecucao.nomeTeamA;
            enemyTeam = ConstantesExecucao.nomeTeamB;
        } else {
            myTeam = ConstantesExecucao.nomeTeamB;
            enemyTeam = ConstantesExecucao.nomeTeamA;
        }

        setup();

        try {
            System.out.println("[Client] Waiting for the game start");
            referenciaServidor.setStart(myTeam);
            while (!referenciaServidor.isStarted()) {
                Thread.sleep(50);
            }
            System.out.println("[Client] START!");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public void setup() {
    }

    @Override
    public boolean executeAction(String ag, Structure action) {
        return true;
    }

    public InterfaceServidorJason getReferenciaServidor() {
        return referenciaServidor;
    }
}