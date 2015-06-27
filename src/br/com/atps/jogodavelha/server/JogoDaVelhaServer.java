package br.com.atps.jogodavelha.server;


public class JogoDaVelhaServer {
	
	static Jogo jogo;

	public static void main(String[] args) {
			jogo = new Jogo();
			jogo.executa();
		}
		
}
