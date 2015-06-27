/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atps.jogodavelha.client;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import br.com.atps.jogodavelha.util.Tabuleiro;

/**
 *
 * @author adaltonreis
 */
public class VelhaOnlineCliente implements Runnable {
    
    static Tabuleiro tabuleiro;
    static jpMesa mesaDeJogo;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //player 1
        tabuleiro = new Tabuleiro();
        mesaDeJogo = new jpMesa();
      //  mesaDeJogo.setSize(150,200);
        mesaDeJogo.setTabuleiro(tabuleiro);
        
        JFrame frame = new JFrame("Velha Online");
        frame.add(mesaDeJogo);        
        frame.setSize(184,236);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE );
        frame.setVisible(true);   
        
    }
	//@Override
	public void run() {
		VelhaOnlineCliente.main(new String[2]);		
	}
    
}
