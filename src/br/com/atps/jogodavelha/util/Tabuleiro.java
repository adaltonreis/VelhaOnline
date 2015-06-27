/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atps.jogodavelha.util;


/**
 *
 * @author adaltonreis
 */
public class Tabuleiro {	
    private int [] tabuleiro;

    public Tabuleiro() {
        
        tabuleiro = new int[9];
                
        for(int i=0; i<9; i++){
            tabuleiro[i]= -100;
        }
    }

    //Reinicia os valores do tabuleiro sem perber a referência para o mesmo
    public void reinicia(){
    	
        for(int i=0; i<9; i++){
            tabuleiro[i]= -100;
        }    	
    }

    /**
     * @return the jogo
     */
    public int [] getTabuleiro() {
        return this.tabuleiro;
    }
       
   public void setTabuleiro(int [] tabuleiro) {
       this.tabuleiro = tabuleiro;
   }
   
   public void marcaPosicao(int posicao, int simbolo) {
	   if(posicao < 0 || posicao > 9 || 
			   (simbolo != 0 && simbolo != 1 && simbolo != -100)){
		   System.out.println("Erro ao tentar atualizar tabuleiro. Dados incorretos.");
	   }else{
		   tabuleiro[posicao] = simbolo;
	   }
	   
   }
    
}
