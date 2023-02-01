package br.com.empresa.cm.excecao;

@SuppressWarnings("serial")
public class BombaException extends RuntimeException {
	
	@Override
	public String getMessage() {
		return "BOOOMM! você perdeu";
	}

}
