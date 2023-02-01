package br.com.empresa.cm;

import br.com.empresa.cm.modelo.Tabuleiro;
import br.com.empresa.cm.visao.TabuleiroConsole;

public class Start {

//	aqui você define a quantidade de linhas, colunas e minas
	public static void main(String[] args) {
		Tabuleiro tabuleiro = new Tabuleiro(10, 10, 40);
		new TabuleiroConsole(tabuleiro);
	}
}
