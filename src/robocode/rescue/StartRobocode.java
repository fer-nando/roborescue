package robocode.rescue;

import util.ConstantesExecucao;
import util.Transferencia;

public class StartRobocode {

    public static void main(String[] args) {
        ConstantesExecucao.start("server");
        Transferencia.transfere();
        BattleManager battle;
        if(args.length > 0) {
            battle = new BattleManager(1, Long.parseLong(args[0]));
        } else  {
            battle = new BattleManager(1);
        }
        battle.start();
    }
}
