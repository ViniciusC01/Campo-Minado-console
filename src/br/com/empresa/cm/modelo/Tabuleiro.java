package br.com.empresa.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

	private int linhas;
	private int colunas;
	private int minas;

	private final List<Campo> campos = new ArrayList<>();
	List<Integer> PosicoesCamposBomba = new ArrayList<>();

	public Tabuleiro(int linhas, int colunas, int minas) {
		this.linhas = linhas;
		this.colunas = colunas;
		this.minas = minas;

		gerarCampos();
		associarOsVizinhos();
		sortearMinas();
//		mostrarBombas();
	}

	public void mostrarBombas() {
		System.out.println("Funcao mostrar bombas:	");

		System.out.print("posicoes minas: -");
		for (Integer integer : PosicoesCamposBomba) {
			System.out.print(integer + "-");
		}
		System.out.println("\n");

		boolean mostrarTodasPosicoes = false;

		for (int i = 0; i < campos.size(); i++) {

			if (!PosicaoNaoTemBomba(i)) {
				campos.get(i).aberto = true;
				System.out.print(" " + campos.get(i).toString() + " ");
				campos.get(i).aberto = false;

			} else if (mostrarTodasPosicoes) {
				if (PosicaoNaoTemBomba(i)) {
					System.out.print(" " + campos.get(i).toString() + " ");
				}

			} else {
				if (i <= 9 && PosicaoNaoTemBomba(i)) {
					System.out.print(" " + i + " ");
				}

				if (i >= 10 && PosicaoNaoTemBomba(i)) {
					System.out.print(i + " ");
				}

			}
			if (campos.get(i).getColuna() == 5) {
				System.out.println();
			}

		}
		System.out.println();

	}

	private boolean PosicaoNaoTemBomba(int posicao) {
		boolean noneMatch = PosicoesCamposBomba.stream().distinct().noneMatch(v -> v.equals(posicao));
		return noneMatch;
	}

	private void gerarCampos() {

		for (int linha = 0; linha < linhas; linha++) {
			for (int coluna = 0; coluna < colunas; coluna++) {
				campos.add(new Campo(linha, coluna));
			}
		}

	}

	private void associarOsVizinhos() {
		for (Campo c1 : campos) {
			for (Campo c2 : campos) {
				c1.adicionarVizinho(c2);

			}
		}

	}

	private void sortearMinas() {
		long minasArmadas = 0;
		Predicate<Campo> minado = c -> c.minado;

		do {
			int aleatorio = (int) (Math.random() * campos.size());
			campos.get(aleatorio).minar();
			minasArmadas = campos.stream().filter(minado).count();

			PosicoesCamposBomba.add(aleatorio);
//			System.out.println("Posicao: "+aleatorio);
//			mostrarBombas();
//			System.out.println(minasArmadas+"<"+minas+"?");
		} while (minasArmadas < minas);

	}

	int marcacoesRestantes() {
		Predicate<Campo> marcado = c -> c.marcado;
		long marcados = campos.stream().filter(marcado).count();
		int minas = this.minas;

		int marcacoesRestantes = (int) (minas - marcados);
		return marcacoesRestantes;
	}

	public void abrir(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.abrir());
		;
	}

	public void AlterarMarcacao(int linha, int coluna) {
		campos.stream().filter(c -> c.getLinha() == linha && c.getColuna() == coluna).findFirst()
				.ifPresent(c -> c.alternarMarcacao());

		;
	}

	public boolean objetivoAlcancado() {
		return campos.stream().allMatch(c -> c.objetivoAlcancado());
	}

	public void reiniciar() {
		campos.stream().forEach(c -> c.reiniciar());
		sortearMinas();
		;
	}

	public String vizualizarCampos() {
		campos.stream().forEach(c -> c.visualizarCampo());
		return toString();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = 0;
		sb.append("Marcações restantes: "+marcacoesRestantes()+"\n");
		
		sb.append("  ");
		for (int l = 0; l < colunas; l++) {
			sb.append(" ");
			sb.append(l);
			sb.append(" ");
		}
		sb.append("\n");

		for (int l = 0; l < linhas; l++) {
			sb.append(l);

			sb.append(" ");
			for (int j = 0; j < colunas; j++) {
				sb.append(" ");
				sb.append(campos.get(i));
				sb.append(" ");
				i++;

			}
			sb.append("\n");
		}
		return sb.toString();
	}
}