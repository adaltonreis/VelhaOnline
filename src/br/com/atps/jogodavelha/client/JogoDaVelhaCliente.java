package br.com.atps.jogodavelha.client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

import br.com.atps.jogodavelha.util.ControleDaRodada;
import br.com.atps.jogodavelha.util.Mensagem;
import br.com.atps.jogodavelha.util.MensagemTipo;
import br.com.atps.jogodavelha.util.Tabuleiro;

public class JogoDaVelhaCliente {
	
    private Socket cliente;
    private String server_ip;
    private int server_port;
    
    jpMesa mesa;
    
    private ControleDaRodada rodada;
       
    
    public JogoDaVelhaCliente(String host, int port, jpMesa mesa, Tabuleiro tabuleiro){
        this.setHost(host);
        this.setPort(port);  
        this.mesa = mesa;
        this.rodada = new ControleDaRodada();
    }

    public void executa() {
        try {
            cliente = new Socket(server_ip,server_port);
            System.out.println("Conectado !");
            
            //Receber mensagens
            new Thread(new RecebedorCliente(cliente.getInputStream(),
                    rodada.getTabuleiro(), this )).start();
                    
        } catch (IOException ex) {
            Logger.getLogger(JogoDaVelhaCliente.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Não foi possível conectar !");
        }   	
    }

    public void enviaMensagem(Mensagem msgm){
        PrintStream enviar = null;
        try {        
            enviar = new PrintStream(cliente.getOutputStream());
            
            for(int i=0; i< msgm.getMsgm().length; i++ )
            	enviar.println(msgm.getMsgm()[i]);
            
        } catch (IOException ex) {
            Logger.getLogger(JogoDaVelhaCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void setHost(String host) {
        this.server_ip = host;
    }
	
    private void setPort(int port) {
        this.server_port = port;
    }

   public void trataMensagem(Mensagem msgm){
	   
	   System.out.println("Cliente tratando mensagem tipo " + msgm.getMsgm()[1]);
	   
        MensagemTipo enumMsgm = MensagemTipo.MSGM_NOVA;
        enumMsgm = enumMsgm.fromInt(msgm.getMsgm()[1]);         
         
        //Define o que fazer de acordo com o tipo de mensagem        
       // switch (msgm.getMsgm()[1]){
        switch ( enumMsgm ){
            //
        	case SAIR : { //Encerrar o jogo
        		
        		Thread.currentThread().interrupt();
        		break;
        	} 
        	case C_JOGADA : //retornar informação de erro 
        	case ERRO : {//retornar informação de erro 
        		
        		System.out.println("Cliente> Erro: "+ msgm.toString());
        	 	rodada.setQuemJogaProxima(msgm.getMsgm()[4]);
        		
            	break;
            }
            case S_SIMBOLO:  { //receber o simbolo a ser usado
            	
            	rodada.setQuemJogaProxima(msgm.getMsgm()[4]);
            	rodada.setSimbolo(msgm.getMsgm()[2]);
                mesa.getjButton10().setText("Você é o "+numTochar(rodada.getSimbolo()));
            	System.out.println("Cliente>  simbolo: " + rodada.getSimbolo()
            			+". Quem joga: "+ rodada.getQuemJogaProxima() );            	
            	break;
            }
            
            case S_MARCAR_TABULEIRO :{//Atualizar o formulário
            	
            	System.out.println("Cliente> Atualizar o tabuleiro, posição "+
            			msgm.getMsgm()[2] + " simbolo "+ rodada.getSimbolo());
            	/*teste*/System.out.println("Cliente> Mensagem: "+msgm.toString());
            	
            	
            	rodada.setQuemJogaProxima(msgm.getMsgm()[4]);
            	this.rodada.getTabuleiro().marcaPosicao(msgm.getMsgm()[2],
            			msgm.getMsgm()[3]);
            	atualizaFormulário();
            	break;
            }
            	
            case S_FIM_DO_JOGO : {//Exibir mensagem para o Jogador
            	rodada.setQuemJogaProxima(msgm.getMsgm()[4]);
            	rodada.setTerminou(true);//Niguém pode jogar mais
            	
            	String dialogo = "";
            	
            	switch(msgm.getMsgm()[2]){
            		case 0: {
            			if(rodada.getSimbolo() == 0){ //Você ganhou
                			dialogo = "Parabéns, você ganhou ( "+ numTochar(rodada.getSimbolo()) +" )!";
            			}else{//VOcê perdeu
            				dialogo =  "Lanterninha, treine mais! Você quer jogar denovo ?";            			
            			}
            			break;            			
            		}
            		case 1 : {
            			if(rodada.getSimbolo()== 1){ // Você ganhou
                			dialogo = "Parabéns, você ganhou ( "+ numTochar(rodada.getSimbolo()) +" )!";
            			}else{ //Você perdeu
            				dialogo =  "Lanterninha, treine mais !";            			
            			}
            			break; 
            		}
            		case -1 :{//Deu velha
            			dialogo = "Xiii deu velha!";// deu velha ( Niguém ganhou )
            		}
            	}
            	            	
            	JOptionPane.showMessageDialog(null, dialogo,"Jogo Da Velha",JOptionPane.YES_NO_OPTION);
            			            		
            	System.out.println("Cliente> "+dialogo);

            	break;
            }
            case RESTART: {//retornar informação de erro
            	restart(msgm.getMsgm()[4]);
            }
        }                      
    }
 
   //Reiniciando variaveis para o próximo jogo 
   private void restart(int quemJogaProxima) {
	   
	    System.out.println("Cliente> Reiniciando variáveis . . .");

	    //Reiniciliza todas as variáveis
	    rodada = new ControleDaRodada();
	    rodada.setQuemJogaProxima(quemJogaProxima);

	    atualizaFormulário();
   }

//Coloca os valores no formulário.
   private void atualizaFormulário() {
	
		mesa.getBtn0().setText((rodada.getTabuleiro().getTabuleiro()[0] == -100 ? " ":  
					String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[0]))));
		
		mesa.getBtn1().setText((rodada.getTabuleiro().getTabuleiro()[1] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[1]))));
		
		mesa.getBtn2().setText((rodada.getTabuleiro().getTabuleiro()[2] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[2]))));
		
		mesa.getBtn3().setText((rodada.getTabuleiro().getTabuleiro()[3] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[3]))));
		
		mesa.getBtn4().setText((rodada.getTabuleiro().getTabuleiro()[4] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[4]))));
		
		mesa.getBtn5().setText((rodada.getTabuleiro().getTabuleiro()[5] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[5]))));
		
		mesa.getBtn6().setText((rodada.getTabuleiro().getTabuleiro()[6] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[6]))));
		
		mesa.getBtn7().setText((rodada.getTabuleiro().getTabuleiro()[7] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[7]))));
		
		mesa.getBtn8().setText((rodada.getTabuleiro().getTabuleiro()[8] == -100 ? " ":
				String.valueOf(numTochar(rodada.getTabuleiro().getTabuleiro()[8]))));	
}
   
   public ControleDaRodada getRodada(){
	   return this.rodada;
   }

//converte de inteiro para char
   private char numTochar(int num){
       
       char ret = ' ';
       if( num == 0)
           ret = 'O';
       else if( num == 1 )
           ret = 'X';
       
       return ret;
   }
   
   //converte char para inteiro
   private int charToNum(char c){
       
       int ret = ' ';
       
       if( c == 'O' || c== 'o')
           ret = 0;
       else if( c == 'X' || c== 'x')
           ret = 1;
       
       return ret;
   }

public void sair() {
	try {
		this.finalize();
		cliente.close();
	} catch (IOException e) {
		e.printStackTrace();
	} catch (Throwable e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
}

public Socket getCliente() {
	return cliente;
}

public void setCliente(Socket cliente) {
	this.cliente = cliente;
}

public String getServer_ip() {
	return server_ip;
}

public void setServer_ip(String server_ip) {
	this.server_ip = server_ip;
}

public int getServer_port() {
	return server_port;
}

public void setServer_port(int server_port) {
	this.server_port = server_port;
}

public jpMesa getMesa() {
	return mesa;
}

public void setMesa(jpMesa mesa) {
	this.mesa = mesa;
}
  
}
