package br.com.caelum.leilao.teste;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.dominio.servico.Avaliador;

public class TesteAvaliador {

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		System.out.println(leiloeiro.getMaiorLance()); // imprime 400.0
		System.out.println(leiloeiro.getMenorLance()); // imprime 250.0

		double maiorEsperado = 400;
		double menorEsperado = 250;

		// System.out.println(maiorEsperado == leiloeiro.getMaiorLance());
		Assert.assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		// System.out.println(menorEsperado == leiloeiro.getMenorLance());
		Assert.assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);

	}
}