package br.com.empresa.cm.visao;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

import br.com.empresa.cm.excecao.BombaException;
import br.com.empresa.cm.excecao.SairException;
import br.com.empresa.cm.modelo.Tabuleiro;

public class TabuleiroConsole {

	private Tabuleiro tabuleiro;
	private Scanner sc = new Scanner(System.in);

	public TabuleiroConsole(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;

		executarJogo();
	}

	private void executarJogo() {

		try {
			boolean continuar = true;
			while (continuar) {
				System.out.println("JOGO CAMPO MINADO\n\nJOGABILIDADE:"
						+ "\nPasso 1- \ndigitar a posição escolhida por via do eixo x y os separando por ','."
						+ "\nPasso 2- \nescolher entre opção 1- abrir o campo escolhido ou 2- marcar/descamarcar o campo.\n");
				cicloDoJogo();

				System.out.println("Deseja reiniciar? s/n");
				String resposta = sc.nextLine();

				if (resposta.equalsIgnoreCase("n")) {
					continuar = false;
				} else {
					tabuleiro.reiniciar();
				}
			}
		} catch (SairException e) {
			e.getMessage();
		} finally {
			sc.close();
		}
	}

	void cicloDoJogo() {
		
		
		try {
			while (!tabuleiro.objetivoAlcancado()) {
				System.out.println(tabuleiro);

				String digitado = capturarValorDigitado("Digite (x,y):");

				Iterator<Integer> xy = Arrays.stream(digitado.split(",")).map(e -> Integer.parseInt(e.trim()))
						.iterator();

				digitado = capturarValorDigitado("1 - Abrir 2- Marcar/Desmarcar");

				if (digitado.equals("1")) {
					tabuleiro.abrir(xy.next(), xy.next());
				} else if (digitado.equals("2")) {
					tabuleiro.AlterarMarcacao(xy.next(), xy.next());
				}

			}
			if (tabuleiro.objetivoAlcancado()) {
				System.out.println("Você ganhou!!!");
			}
			
			
		} catch (BombaException e) {
			System.out.println(e.getMessage());
			System.out.println(tabuleiro.vizualizarCampos());
		}
	}

	private String capturarValorDigitado(String texto) {
		System.out.println(texto);

		String digitado = sc.nextLine();

		if (digitado.equalsIgnoreCase("sair")) {
			throw new SairException();
		}

		return digitado;
	}

//	capturarValorDigitado(text)
}
