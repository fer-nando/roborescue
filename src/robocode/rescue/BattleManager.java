package robocode.rescue;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;
import robocode.control.events.BattleAdaptor;
import robocode.control.events.TurnEndedEvent;
import robocode.control.snapshot.IRobotSnapshot;
import util.ConstantesExecucao;

public class BattleManager {

    RobocodeEngine engine;
    IRobotSnapshot refemA;
    IRobotSnapshot refemB;
    private int numRounds;
    private long timeout;
    boolean battleComplete = false;
    int bFLargura = 2427; //2427 proporcao aurea =P
    int bFAltura = 1500;

    public BattleManager(int numRounds) {
        this.numRounds = numRounds;
        this.timeout = 0;
    }

    public BattleManager(int numRounds, long timeout) {
        this.numRounds = numRounds;
        this.timeout = timeout;
    }

    public void start() {
        File location = new File(ConstantesExecucao.caminhoRobocode);
        engine = new RobocodeEngine(location);
        BattleObserver listener = new BattleObserver();
        engine.addBattleListener(listener);

        BattlefieldSpecification bfSpec = new BattlefieldSpecification(bFLargura, bFAltura);
        //rodos que seram adicionados
        //String pack = JOptionPane.showInputDialog("Digite o nome do packege que o Robo ou time se encontra");
        String pack = "sibots";

        //String teamA = JOptionPane.showInputDialog("Digite o nome do time A ou Robo1");
        //String teamB = JOptionPane.showInputDialog("Digite o nome do time B ou Robo2");

        String teamA = pack + "." + ConstantesExecucao.nomeTeamA + "Team*";
        String teamB = pack + "." + ConstantesExecucao.nomeTeamB + "Team*";

        //System.out.println(teamA + "\n" + teamB);

        RobotSpecification[] robots = engine.getLocalRepository(teamA + ", " + teamB);

        //especificação da batalha
        BattleSpecification battle = new BattleSpecification(numRounds, Long.MAX_VALUE, 0, false, bfSpec, robots);

        RoboPos rAR = new RoboPos(bFLargura, 750, 270);
        RoboPos rA1 = new RoboPos(200, 600, 90);
        RoboPos rA2 = new RoboPos(200, 700, 90);
        RoboPos rA3 = new RoboPos(200, 800, 90);
        RoboPos rA4 = new RoboPos(200, 900, 90);
        RoboPos rBR = new RoboPos(0, 750, 90);
        RoboPos rB1 = new RoboPos(bFLargura - 200, 600, 270);
        RoboPos rB2 = new RoboPos(bFLargura - 200, 700, 270);
        RoboPos rB3 = new RoboPos(bFLargura - 200, 800, 270);
        RoboPos rB4 = new RoboPos(bFLargura - 200, 900, 270);

        String initialPos = rAR + "," + rA1 + "," + rA2 + "," + rA3 + "," + rA4 + ","
                + rBR + "," + rB1 + "," + rB2 + "," + rB3 + "," + rB4;

        //System.out.println(initialPos);

        System.out.println("[Robocode] " + ConstantesExecucao.nomeTeamA
                + " vs " + ConstantesExecucao.nomeTeamB);
        System.out.println("[Robocode] Batalha iniciada!");
        engine.setVisible(true);
        engine.runBattle(battle, initialPos, false);

        if(timeout > 0) {
            try {
                Thread.sleep(timeout);
            } catch (InterruptedException ex) {
                System.out.println("InterruptedException: " + ex);
            }
            if (!battleComplete) {
                // timeout
                System.out.println("\n[Robocode] TIMEOUT!");
                System.out.println("[Robocode] Distancia do " + ConstantesExecucao.nomeTeamA + "  = "
                        + (refemA.getX() - 200));
                System.out.println("[Robocode] Distancia do " + ConstantesExecucao.nomeTeamB + "  = "
                        + (bFLargura - 200 - refemB.getX()));
                engine.abortCurrentBattle();
            }
        } else {
            engine.waitTillBattleOver();
        }

        System.out.println("[Robocode] Batalha terminada!");
        engine.close();
        System.exit(0);
    }

    private class BattleObserver extends BattleAdaptor {

        @Override
        public void onTurnEnded(TurnEndedEvent event) {
            IRobotSnapshot[] robots = event.getTurnSnapshot().getRobots();
            refemA = robots[0];
            refemB = robots[5];

            if (refemA.getX() <= 200 && refemB.getX() >= bFLargura - 200) {
                // empate
                battleComplete = true;
                System.out.println("\n[Robocode] EMPATE!");
                engine.abortCurrentBattle();
            } else if (refemA.getX() <= 200) {
                // vencedor = A
                battleComplete = true;
                System.out.println("\n[Robocode] VENCEDOR: " + ConstantesExecucao.nomeTeamA);
                engine.abortCurrentBattle();
            } else if (refemB.getX() >= bFLargura - 200) {
                // vencedor = B
                battleComplete = true;
                System.out.println("\n[Robocode] VENCEDOR: " + ConstantesExecucao.nomeTeamB);
                engine.abortCurrentBattle();
            }
        }
    }

    private class RoboPos {

        int x, y, ang;

        public RoboPos(int x, int y, int ang) {
            this.x = x;
            this.y = y;
            this.ang = ang;
        }

        public String toString() {
            return "(" + x + "," + y + "," + ang + ")";
        }
    }
}
