package br.com.atps.jogodavelha.client;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.com.atps.jogodavelha.util.Tabuleiro;


public class TestaDoisClientes {
	
    static Tabuleiro tabuleiro;
    static jpMesa mesaDeJogo;	

	public static void main(String [] args){	
		
        //player 1
        tabuleiro = new Tabuleiro();
        mesaDeJogo = new jpMesa();
        mesaDeJogo.setTabuleiro(tabuleiro);
        
        JFrame frame = new JFrame("Velha Online");
        frame.add(mesaDeJogo);        
        frame.setSize(184,236);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible(true); 
        
        
        
        //Player 2
        tabuleiro = new Tabuleiro();
        mesaDeJogo = new jpMesa();
        mesaDeJogo.setTabuleiro(tabuleiro);
        
        JFrame frame2 = new JFrame("Velha Online");
        frame2.add(mesaDeJogo);        
        frame2.setSize(184,236);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame2.setVisible(true); 	        
	}

}
