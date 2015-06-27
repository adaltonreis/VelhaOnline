package br.com.atps.jogodavelha.util;


public class ControleDaRodada{
	
	private Tabuleiro tabuleiro;
	
    //informa se você ganhou
    private boolean cliente_ganhou = false;
    
    //Controla para que não sejam aceitas jogadas depois de alguém ter vencido
    private boolean terminou = false; 
    
    //Informa se é sua vez de jogar
    private int quemJogaProxima = 0;
    
    //Símbolo a usar
    private int simbolo = -100;

	private int ganhador;
	
	private int numJogadas;
    
    
    public ControleDaRodada(){
    	this.cliente_ganhou = false;
    	this.terminou = false;
    	this.quemJogaProxima = 1;
    	this.simbolo = -100;
    	this.ganhador = -100;
    	this.numJogadas = 0;
    	this.tabuleiro = new Tabuleiro();
    	
    }
    
    public void setNumJogadas(int numJogadas){
    	this.numJogadas = numJogadas;
    }
    
    public int getNumJogadas(){
    	return this.numJogadas;
    }
    
    
	public boolean isGanhou() {
		return cliente_ganhou;
	}

	public void setGanhou(boolean ganhou) {
		this.cliente_ganhou = ganhou;
	}

	public boolean isTerminou() {
		return terminou;
	}

	public void setTerminou(boolean terminou) {
		this.terminou = terminou;
	}

	public int getQuemJogaProxima() {
		return quemJogaProxima;
	}

	public void setQuemJogaProxima(int quemJogaProxima) {
		this.quemJogaProxima = quemJogaProxima;
	}

	public int getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(int simbolo) {
		this.simbolo = simbolo;
	}    	
    
	//
	public Tabuleiro getTabuleiro(){
		return this.tabuleiro;
	}
	
	//
	public void setTabuleiro(Tabuleiro tabuleiro){
		
	}

	public void setGanhador(int simbolo) {
		this.ganhador = simbolo;
		
	}
	public int getGanhador(){
		return this.ganhador;
	}
	
    
}