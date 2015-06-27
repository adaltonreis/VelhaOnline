/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atps.jogodavelha.server;

import java.io.InputStream;
import java.util.Scanner;

import br.com.atps.jogodavelha.util.Mensagem;
import br.com.atps.jogodavelha.util.MensagemTipo;

/**
 *
 * @author adaltonreis
 */
public class TrataJogador implements Runnable {
    private int simbolo;// 0 ou 1
    InputStream jogador; 
    Jogo jogo;
    
    //Construtor já associa um símbolo 
    public TrataJogador(int simbolo, InputStream jogador, Jogo jogo){
        this.simbolo = simbolo;
        this.jogador = jogador;     
        this.jogo = jogo;
    }

   // @Override
    public void run() {
       recebeMensagem();
    }

    private void recebeMensagem() {
        Scanner scan = new Scanner(jogador);        
        /*Estrutura da mensagem:
        *  inicio (String)
        *  tipo   (String)
        *  corpo  (String)
        *  fim    (String)
        */
        Mensagem msgm = null;
        int aux = -100;          
        
        int tipo = -100;
        int valor1 = -100;
        int valor2 = -100;
        int quemJogaProxima = -100;//Cmapo não será usado, mas pe preciso lê-lo para liberar buffer.
        
        int arrayPos = 0;
        
        while(scan.hasNextInt()){
            aux = scan.nextInt();
            //início da mensagem
            if(aux == MensagemTipo.MSGM_INICIO.getValor()){
            	arrayPos = 0; 
            }else
            	arrayPos++;
            
            switch(arrayPos){
            	case 1: {
            		tipo = aux;
            		break;
            	}
            	case 2: {
            		valor1 = aux;
            		
            		break;
            	}
            	case 3: {
            		valor2 = aux;
            		
            		break;
            	}
            	case 4: {
            		quemJogaProxima = aux; 
            		 
            		break;
            	}
            
            }
            if (aux == MensagemTipo.MSGM_FIM.getValor() ){
                msgm = new Mensagem( tipo , valor1, valor2,quemJogaProxima);
                //Imprime a mensagem na tela do console
                System.out.println("Server- Repassando mensagem ao servidor");
                System.out.println("Mensagem: "+msgm.toString());
                //Pede ao servidro para processar a mensagem
                jogo.trataMensagem(msgm, simbolo);  
                                
            }
            
            if (aux == MensagemTipo.SAIR.getValor() ){   
            	jogo.sair();;
            }
        }       
    }
    
}
