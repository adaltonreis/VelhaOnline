package br.com.atps.jogodavelha.util;

/*
*      Tipos de Mensagem
*  0:  Enviado pelo servidor, apenas um campo (0/1), simbolo a ser usado no cliente
*  1:  Enviado pelo servidor, dois campos(0..8:0/1, posição a ser marcada e símbolo respectivamente.
*  2:  Enviada pelo servidor, dois campos dois campos(0/1:0/1), fim do jogo, simbolo vencedor. valor negativo indica velha 
*  3:  Enviada pelo cliente, apenas um campo (0/1), posição a ser marca.
*  ////////4:  Enviada pelo servidor, apenas um campo (0/1), quem joga; valores( -1>niguém, 0>O, 1>X )
* -1:  Enviada pelo servidor, Erro. ( 0 auguardar, 1 abortar jogo, 2 )
* -2:
*/
public enum MensagemTipo {
	
	
	S_SIMBOLO (0),//Simbolo a ser usado 
	S_MARCAR_TABULEIRO (1), // posição a ser maracada no tabuleiro e qual símbolo
	S_FIM_DO_JOGO (2), // jogo teminou, quem ganhou
	C_JOGADA (3),
	ERRO (-1),// erro
	SAIR (-2),
	RESTART(-3), //Recomeçar o jogo
	
	MSGM_INICIO (50),
	MSGM_FIM (-50 ),
	MSGM_VAZIA( -100),
	MSGM_NOVA (-1000);
	
	
	private int msgm;
	
	MensagemTipo(int msgm){
		this.msgm = msgm;
	}
	
	public int getValor(){
		return this.msgm;
	}

	public MensagemTipo fromInt(int valor){
		switch( valor){
			case 0 : { return  S_SIMBOLO;}
			case 1 : { return  S_MARCAR_TABULEIRO; }
			case 2 : { return  S_FIM_DO_JOGO; }
			case 3 : { return  C_JOGADA;  }
			case -1 : { return ERRO;  }
			case -2 : { return SAIR; }
			case -3: { return RESTART;}
			case 50 : { return  MSGM_INICIO; }
			case -50 : { return  MSGM_FIM;  }
			case -100 : { return  MSGM_VAZIA;  }
			default :    return  MSGM_VAZIA; 
		}
	}
}
