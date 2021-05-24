package br.com.caelum.leilao.dominio.servico;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import br.com.caelum.leilao.dominio.AnoBissexto;

public class AnoBissextoTest {

	@Test
	public void deveRetornarAnoBissexto() {
		AnoBissexto anoBissexto = new AnoBissexto();
		AnoBissexto anoBissexto2 = new AnoBissexto();

		anoBissexto = new AnoBissexto(2016);
		anoBissexto2 = new AnoBissexto(2012);
		assertEquals(true, anoBissexto.eAnoBissexto());
		assertEquals(true, anoBissexto2.eAnoBissexto());
	}

	@Test
	public void naoDeveRetornarAnoBissexto() {
		AnoBissexto anoBissexto = new AnoBissexto();
		AnoBissexto anoBissexto2 = new AnoBissexto();

		anoBissexto = new AnoBissexto(2015);
		anoBissexto2 = new AnoBissexto(2011);

		assertEquals(false, anoBissexto.eAnoBissexto());
		assertEquals(false, anoBissexto2.eAnoBissexto());
	}

}
