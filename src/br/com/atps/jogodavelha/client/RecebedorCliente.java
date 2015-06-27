/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atps.jogodavelha.client;

import java.io.InputStream;
import java.util.Scanner;

import br.com.atps.jogodavelha.util.Mensagem;
import br.com.atps.jogodavelha.util.MensagemTipo;
import br.com.atps.jogodavelha.util.Tabuleiro;

/**
 *
 * @author adaltonreis
 */
public class RecebedorCliente implements Runnable{

	JogoDaVelhaCliente cliente;
    private InputStream input;
    private Tabuleiro tabuleiro;
    
    
    public RecebedorCliente(InputStream input,Tabuleiro tabuleiro, JogoDaVelhaCliente cliente){
        this.tabuleiro = tabuleiro;
        this.cliente = cliente;
        this.input =  input;
    }
    
    @Override
    public void run() {
    	recebeMensagem();
    }
    
    private void setTabuleiro(Tabuleiro tabuleiro) {
       this.tabuleiro = tabuleiro;
    }
    private Tabuleiro getTabuleiro(){
        return this.tabuleiro;
    }    
        
    private void recebeMensagem() {
    	
    	/*Teste*/
    	System.out.println("Cliente: Aguardando mensagem do servidor");
    	
        Scanner scan = new Scanner(input);        
        Mensagem msgm = null;
        int aux = -100;          
        
        int tipo = -100;
        int valor1 = -100;
        int valor2 = -100;
        int quemJogaProxima = -100;
        
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
                System.out.println("Cliente- Repassando mensagem ao analisador");
                System.out.println("Mensagem: "+msgm.toString());
                
                //Pede ao servidro para processar a mensagem
                cliente.trataMensagem(msgm);  
                                
            }
            
            if (aux == MensagemTipo.SAIR.getValor() ){   
            	cliente.sair();;
            }
        }       
    }
        
}
