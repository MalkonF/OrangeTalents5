package br.com.caelum.leilao.dominio;

public class AnoBissexto {

	Integer ano;

	public AnoBissexto(Integer ano) {

		this.ano = ano;
	}

	public AnoBissexto() {

	}

	public boolean eAnoBissexto() {
		if (((ano % 4) == 0) && ((ano % 100) != 0))
			return true;
		else if ((ano % 400) == 0)
			return true;
		else
			return false;
	}

}
