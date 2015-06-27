/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.atps.jogodavelha.util;


/**
 *Estrutura da mensagem:
 *  tipo   (int)
 *  corpo  (String)
 * @author adaltonreis
 *      Tipos de Mensagem
 *  0:  Enviado pelo servidor, apenas um campo (0/1), simbolo a ser usado no cliente
 *  1:  Enviado pelo servidor, dois campos(0..8:0/1, posição a ser marcada e símbolo respectivamente.
 *  2:  Enviada pelo servidor, dois campos dois campos(0/1:0/1), fim do jogo, simbolo vencedor. valor negativo indica velha 
 *  3:  Enviada pelo cliente, apenas um campo (0/1), posição a ser marca.
 *  ////4:  Enviada pelo servidor, apenas um campo (0/1), quem joga; valores( -1>niguém, 0>O, 1>X )
 * -1:  Enviada pelo servidor, Erro. ( 0 auguardar, 1 abortar jogo, 2 )
 * -2:
 */
public class Mensagem {
    
    // inicio = -1 ou -3 (Mensagem vazia /erro)
    //private final  int tipo;
	private int tipo;
    private final  int valor1;//0..8
    private final  int valor2;//0..8
    private final  int quemJogaProxima;//0..8
    //fim = -2

    
    public Mensagem(int tipo, int v1, int v2, int quemJogaProxima ) {        
        this.tipo = tipo;
        this.valor1 = v1;
        this.valor2 = v2;
        this.quemJogaProxima = quemJogaProxima;
    }

    public int [] getMsgm(){  
        
        int [] msgm  = new int[6];
        
        msgm[0]= MensagemTipo.MSGM_INICIO.getValor();
        msgm[1]= this.tipo ;  //tipo   
        msgm[2]= this.valor1 ;  //valor1
        msgm[3]= this.valor2 ;  //valor2
        msgm[4]= this.quemJogaProxima;
        msgm[5]= MensagemTipo.MSGM_FIM.getValor();
        
        return msgm;
    }
    
    public String toString(){
        String ret = 
        String.valueOf(getMsgm()[0])+ " " 
        + String.valueOf(getMsgm()[1]) +" " +
        String.valueOf(getMsgm()[2])+ " "
        +String.valueOf(getMsgm()[3]) +" " +
        String.valueOf(getMsgm()[4])+ " "
        +String.valueOf(getMsgm()[5]);
    	return ret;
    }
}
