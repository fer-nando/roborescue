## Versão 2.0.1 (13-Apr-2013)

* Adicionados à classe RoboAction: atributos maxTurnRate e maxVelocity; nova ação RESUME; novo estado FETCH.
* Estado DONE agora indica que a ação terminou de ser executada.
* Adicionado TimeCTeamEnv para exemplificar o uso das modificações.
* Renomeada a classe Main para StartRobocode.
* A classe StartServer foi movida para o pacote ``conexao``.

## Versão 2.0.0 (10-Apr-2013)

Modificação do projeto desenvolvido por Vinicius Zaramella (https://code.google.com/p/si-projeto-integrado/).

### Resumo

O programa foi dividio em três módulos: Servidor, Robocode e Times.
* O Servidor é o responsável pela comunicação entre o Robocode e os Times.
* O Robocode code cria os robos e executa o ambiente simulado. Cada robo é um cliente do servidor e fica periodicamente enviando suas informações atuais e requisitando ações.
* Os Times são desenvolvidos no framework Jason, composto basicamente por um Ambiente (Java) e um ou mais agentes (AgentSpeak). O Ambiente também é um cliente do servidor. Ele requisita as informações do seu time, gera percepções e executa as ações dos seus agentes, e envia ações para os robos. 

Os alunos só precisão modificar os arquivos do Jason (ambiente + agentes) e cada time roda independente do outro.

### Modificações
* Os pacotes foram reestruturados para melhor divisão dos módulos do programa. Foram dividios em: ``conexao``, ``jason``, ``robocode`` e ``util``.
* Cada módulo (o servidor, o robocode e os times)  pode ser executado separadamente na rede local.
	* O servidor deve ter o seu IP como argumento de execução, e.g. ``java -cp "robocode.jar" robocode.rescue.StartServer 192.168.109.77``.
	* Os ambientes dos times devem indicar esse mesmo ip no segundo parâmetro da contrutora, e.g. ``TimeATimeEnv("TimeA", "192.168.109.77")``.


