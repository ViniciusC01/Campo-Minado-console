package br.com.empresa.cm.modelo;

import java.util.ArrayList;
import java.util.List;

import br.com.empresa.cm.excecao.BombaException;

public class Campo {

	private int linha;
	private int coluna;

	boolean aberto;
	boolean minado;
	boolean marcado;

	List<Campo> vizinhos = new ArrayList<>();
//  linha 3 coluna 3
//		l 3	       4
	public Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = linha != vizinho.linha;
		boolean colunaDiferente = coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	void alternarMarcacao() {
		if (!aberto) {
			this.marcado = !marcado;
		}
	};

	boolean vizinhacaSegura() {
		return vizinhos.stream().noneMatch(v -> v.minado);
	}

	boolean abrir() {
		if (!aberto && !marcado) {
			aberto = true;

			if (minado) {
				throw new BombaException();
			}
			if (vizinhacaSegura()) {
				vizinhos.forEach(v -> v.abrir());
			}
			return true;
		} else {
			return false;
		}

	}

	boolean objetivoAlcancado() {
		boolean desvendado = !minado && aberto;
		boolean protegido = minado && marcado;

		return desvendado || protegido;
	}

	long minasNaVizinhaca() {
		return vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		this.aberto = false;
		this.marcado = false;
		this.minado = false;

	}
	
	void visualizarCampo() {
		this.aberto = true;
	}

	void minar() {
		minado = true;
	}

	public String toString() {
		if (marcado) {
			return "X";
		} else if (aberto && minado) {
			return "*";
		} else if (aberto && minasNaVizinhaca() > 0) {
			return Long.toString(minasNaVizinhaca());
		} else if (aberto) {
			return " ";
		} else {
			return "?";
		}

	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

}
