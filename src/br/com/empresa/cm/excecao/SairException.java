package br.com.empresa.cm.excecao;

@SuppressWarnings("serial")
public class SairException extends RuntimeException{

	@Override
	public String getMessage() {
		return "Você saiu";
	}
}
