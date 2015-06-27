package br.com.atps.jogodavelha.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

import br.com.atps.jogodavelha.util.ControleDaRodada;
import br.com.atps.jogodavelha.util.Mensagem;
import br.com.atps.jogodavelha.util.MensagemTipo;
import br.com.atps.jogodavelha.util.Tabuleiro;

public class Jogo {

	static ServerSocket server;
	private Socket jogador1;
	private Socket jogador2;
	private int port = 2661;
	
	//private Tabuleiro tabuleiro;
	//private int numJogadas = 0;

	//private int quemJogaProxima =1;
	
	//private int ganhou = -100;
	
	//Referência para a thread criada para tratar o jogador
	private Thread trataJogador;
	private ControleDaRodada rodada;

	/**
	 *  0 : Símbolo O ganhou
	 *  1 : Símbolo X ganhou
	 * -1 : de velha
	 * -100: jogo em andamento;
	 */

	//Construtor
	public Jogo(){
	}


	public void ganhou(){
		//Símbolo O ganha
		Tabuleiro tabuleiro =rodada.getTabuleiro();
		
		if( tabuleiro.getTabuleiro()[0] + 
				tabuleiro.getTabuleiro()[1] + tabuleiro.getTabuleiro()[2] == 0 ||
				tabuleiro.getTabuleiro()[3] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[5] == 0 ||
				tabuleiro.getTabuleiro()[6] + tabuleiro.getTabuleiro()[7] + tabuleiro.getTabuleiro()[8] == 0 ||

				tabuleiro.getTabuleiro()[0] + tabuleiro.getTabuleiro()[3] + tabuleiro.getTabuleiro()[6] == 0 ||
				tabuleiro.getTabuleiro()[1] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[7] == 0 ||
				tabuleiro.getTabuleiro()[2] + tabuleiro.getTabuleiro()[5] + tabuleiro.getTabuleiro()[8] == 0 ||
				
				
				tabuleiro.getTabuleiro()[0] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[8] == 0 ||
				tabuleiro.getTabuleiro()[2] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[6] == 0 ){
				rodada.setGanhador(0);

			//Símbolo X ganha
		}else if( tabuleiro.getTabuleiro()[0] + tabuleiro.getTabuleiro()[1] + tabuleiro.getTabuleiro()[2] == 3 ||
				tabuleiro.getTabuleiro()[3] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[5] == 3 ||
				tabuleiro.getTabuleiro()[6] + tabuleiro.getTabuleiro()[7] + tabuleiro.getTabuleiro()[8] == 3 ||
				
				tabuleiro.getTabuleiro()[0] + tabuleiro.getTabuleiro()[3] + tabuleiro.getTabuleiro()[6] == 3 ||
				tabuleiro.getTabuleiro()[1] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[7] == 3 ||
				tabuleiro.getTabuleiro()[2] + tabuleiro.getTabuleiro()[5] + tabuleiro.getTabuleiro()[8] == 3 ||
										
				
				tabuleiro.getTabuleiro()[0] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[8] == 3 ||
				tabuleiro.getTabuleiro()[2] + tabuleiro.getTabuleiro()[4] + tabuleiro.getTabuleiro()[6] == 3 ){
			rodada.setGanhador(1);
		}

		//se já houve 9 jogadas
		if(rodada.getGanhador() == -100 && rodada.getNumJogadas() == 9 ){
			rodada.setGanhador(-1);
		} 
		
		/* teste */System.out.println("Server -Variável GANHADOR = "+ rodada.getGanhador() );
		/* teste */System.out.print("Tabuleiro>> ");
			for(int j=0; j<9; j++)
		     System.out.print(tabuleiro.getTabuleiro()[j]+" "); 
		/* teste */System.out.println(" <<");
	}

	public void executa(){

		try {
			server = new ServerSocket(port);
			System.out.println("Server - Conectado, aguardando jogadores ");
			
			this.rodada = new ControleDaRodada();
			this.rodada.setQuemJogaProxima(1);
			
			jogador1 = server.accept();//Aceita a conexão com o primeiro jogador
			System.out.println("Jogador 1 se conectou de "
					+ jogador1.getInetAddress().getHostAddress() +
					". Aguardando jogador 2");

			enviar(jogador1, new Mensagem(
					MensagemTipo.S_SIMBOLO.getValor(),
					0,-100, rodada.getQuemJogaProxima()) ); //simbolo a ser usado pelo jogador1 e pede que aguarde              

			trataJogador = new Thread(new TrataJogador(0,jogador1.getInputStream(),//Starta uma thread
					this));
			trataJogador.start();

			jogador2 = server.accept();//Aceita a conexão com o seundo jogador
			System.out.println("Jogador 2 se conectou de "
					+ jogador2.getInetAddress().getHostAddress() +
					". Jogador dois é quem iniciará a partida.");

			enviar(jogador2, new Mensagem(MensagemTipo.S_SIMBOLO.getValor(),
					1,-100, rodada.getQuemJogaProxima()));//simbolo a ser usado pelo jogador2 e autoriza que o mesmo inicie o jogo                         

			new Thread(new TrataJogador(1,jogador2.getInputStream(), //Starta uma thread
					this)).start();
			
			

		} catch (IOException e) {
			e.printStackTrace();
		}


	}

	public void trataMensagem(Mensagem msgm, int origem ){
		MensagemTipo enumMsgm = MensagemTipo.MSGM_NOVA;
		enumMsgm = enumMsgm.fromInt(msgm.getMsgm()[1]);   

		System.out.println("Server - msgm recebida do tipo: "+msgm.getMsgm()[1]);

		//Define o que fazer de acordo com o tipo de mensagem        
		// switch (msgm.getMsgm()[1]){
		switch ( enumMsgm ){
		//
		case SAIR : { //Alguém pediu pra encerrar o jogo
			
			rodada.setQuemJogaProxima( -100 );
			
			System.out.println("Server - Cliente "+origem+" quer encerrar o jogo.");
			System.out.println("Server - Terminado . . .");
			
			//Dá ordem aos dois jogadores para que reiniciem
			enviar(jogador1, new Mensagem(MensagemTipo.SAIR.getValor(),
					-100, -100, rodada.getQuemJogaProxima()));   
			
			enviar(jogador2, new Mensagem(MensagemTipo.SAIR.getValor(),
					-100, -100, rodada.getQuemJogaProxima()));
			trataJogador.interrupt(); // Para a thread responsável pelos clientes
			Thread.currentThread().interrupt();//Para thread principal 			
			break;
		} 
		case ERRO : //retornar informação de erro ( -1 )
		case S_SIMBOLO:  //retornar informação de erro ( -1 )
		case S_MARCAR_TABULEIRO : //retornar informação de erro ( -1 )
		case S_FIM_DO_JOGO :{ //retornar informação de erro ( -1 )
			//Avisa ao cliente que cometeu um erro e aguarda outra mensagem
			enviar(jogador2, new Mensagem(MensagemTipo.ERRO.getValor(), msgm.getMsgm()[2], -100, rodada.getQuemJogaProxima()));
			break;
		}

		case C_JOGADA : { //Tentar fazer jogada
			if( origem == rodada.getQuemJogaProxima() ){

				System.out.println("Server - Jogada número "+ rodada.getNumJogadas() );
				//Marca a jogada
				rodada.getTabuleiro().marcaPosicao( msgm.getMsgm()[2], origem );               

				//Passa a vez ao outro jogador
				rodada.setQuemJogaProxima( (origem == 0 ? 1 :0 ));
				
				rodada.setNumJogadas(rodada.getNumJogadas()+ 1);

				//Verifica se o jogo acabou ou se alguém ganhou
				ganhou();

				switch (rodada.getGanhador()){
				case -1: { // Deu velha
					System.out.println("Server - Fim do jogo, mas deu velha.");
					rodada.setQuemJogaProxima( -100); //Ninguém jogará na próxima
					
					//Manda atualisar os formulários
					enviar(jogador1, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(), 
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(), 
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));                                            	
				
					//Avisa que o jogo terminou
					enviar(jogador1, new Mensagem(MensagemTipo.S_FIM_DO_JOGO.getValor(),
							-1, -100, rodada.getQuemJogaProxima()));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_FIM_DO_JOGO.getValor(), 
							-1, -100, rodada.getQuemJogaProxima())); 

					//restarta o jogo
					restart(1);
					
					break;
				}
				case 0: { //Simbolo 0 � o ganhador
					System.out.println("Server - Fim do jogo, O ganhou");
					rodada.setQuemJogaProxima( -100); //Ninguém jogará na próxima
					//Manda atualisar os formulários
					enviar(jogador1, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(),
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(),
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));       
					
					//Avisa que o jogo terminou                        
					enviar(jogador1, new Mensagem(MensagemTipo.S_FIM_DO_JOGO.getValor(),
							0, -100, rodada.getQuemJogaProxima( )));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_FIM_DO_JOGO.getValor(), 
							0, -100, rodada.getQuemJogaProxima() )); 

					//restarta o jogo
					restart(rodada.getGanhador());
					
					break;
				}
				case 1: { //Símbolo X é o ganhador
					System.out.println("Server - Fim do jogo, X ganhou");
					rodada.setQuemJogaProxima( -100 ); //Ninguém jogará na próxima

					//Manda atualisar os formulários
					enviar(jogador1, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(), 
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(), 
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));   
					
					//Avisa que o jogo terminou
					enviar(jogador1, new Mensagem(MensagemTipo.S_FIM_DO_JOGO.getValor(), 
							1, -100, rodada.getQuemJogaProxima()));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_FIM_DO_JOGO.getValor()
							, 1, -100, rodada.getQuemJogaProxima())); 
					//restarta o jogo
					restart(rodada.getGanhador());				
					
					break;
				}
				default :{
					//responde aos dois jogadores para que atualizem seus tabuleiros
					System.out.println("Server - Atualizar o tabuleiro " + msgm.toString());
					
					rodada.setQuemJogaProxima( origem == 0 ? 1 : 0 );
					
					enviar(jogador1, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(), 
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));   
					enviar(jogador2, new Mensagem(MensagemTipo.S_MARCAR_TABULEIRO.getValor(), 
							msgm.getMsgm()[2], origem, rodada.getQuemJogaProxima()));
					break;
				}
				}				
			}else{//Envia resposta pedindo que o jogador aguarde sua vez.

				System.out.println("Server - jogador "+origem+" deve aguardar.");

				if(origem ==0 ){
					enviar(jogador1, new Mensagem(
							MensagemTipo.ERRO.getValor(), msgm.getMsgm()[2], -100, rodada.getQuemJogaProxima()));
				}else {
					enviar(jogador2, new Mensagem(MensagemTipo.ERRO.getValor(), msgm.getMsgm()[2], -100, rodada.getQuemJogaProxima()));            		
				}
			}
			break;
		}
		case RESTART :{
			//responde aos dois jogadores para que reiniciem 
			System.out.println("Server - Restart");	
			//Reinicia as variáveis de controle
			restart(origem);			
			break;
		}		
		}              
	}


	private void enviar(Socket cliente, Mensagem msgm){
		System.out.println("Server - Enviando mensagem " + 
					MensagemTipo.MSGM_VAZIA.fromInt(msgm.getMsgm()[1]));
		try {
			PrintStream envia = new PrintStream(cliente.getOutputStream());

			for(int i= 0; i < msgm.getMsgm().length; i++){
				envia.println(msgm.getMsgm()[i]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//Zerar todas as variáveis pra recomeçar o jogo
	public void restart(int quemJogaProxima){
		
		System.out.println("Server - Reiniciando . . .");
		
		rodada = new ControleDaRodada();
		rodada.setQuemJogaProxima(quemJogaProxima);
		
		//Dá ordem aos dois jogadores para que reiniciem
		enviar(jogador1, new Mensagem(MensagemTipo.RESTART.getValor(),
				-100, -100,-100 ));   
		
		enviar(jogador2, new Mensagem(MensagemTipo.RESTART.getValor(),
				-100, -100, -100));		
		
		//Envia qual será o símbolo de cada um !
		enviar(jogador1, new Mensagem(MensagemTipo.S_SIMBOLO.getValor(),
			0, -100, rodada.getQuemJogaProxima() ));
		
		enviar(jogador2, new Mensagem(MensagemTipo.S_SIMBOLO.getValor(),
			1, -100, rodada.getQuemJogaProxima() ));			
		
	}
	
   public ControleDaRodada getRodada(){
	   return this.rodada;
   }
	
	public void sair(){

		try {
			server.close();
			jogador1.close();
			jogador2.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
