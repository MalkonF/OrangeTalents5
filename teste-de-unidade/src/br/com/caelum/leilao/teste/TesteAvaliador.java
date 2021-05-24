package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

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
		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		// System.out.println(menorEsperado == leiloeiro.getMenorLance());
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void deveEntenderLancesEmOrdemCrescenteComOutrosValores() {
		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 1000.0));
		leilao.propoe(new Lance(jose, 2000.0));
		leilao.propoe(new Lance(maria, 3000.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(3000.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	// @Ignore //pq lá no avaliador ele está pegando 3 elementos e aqui só tem um
	// maiores.subList(0, 3)
	public void deveEntenderLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("João");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 1000.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
		Usuario joao = new Usuario("Joao");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 450.0));
		leilao.propoe(new Lance(joao, 120.0));
		leilao.propoe(new Lance(maria, 700.0));
		leilao.propoe(new Lance(joao, 630.0));
		leilao.propoe(new Lance(maria, 230.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
		Usuario joao = new Usuario("Joao");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 100.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
	}
	
	@Test
    public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
        Usuario joao = new Usuario("João");
        Usuario maria = new Usuario("Maria");
        Leilao leilao = new Leilao("Playstation 3 Novo");

        leilao.propoe(new Lance(joao, 100.0));
        leilao.propoe(new Lance(maria, 200.0));

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(2, maiores.size());
        assertEquals(200, maiores.get(0).getValor(), 0.00001);
        assertEquals(100, maiores.get(1).getValor(), 0.00001);
    }
	
	@Test
    public void deveDevolverListaVaziaCasoNaoHajaLances() {
        Leilao leilao = new Leilao("Playstation 3 Novo");

        Avaliador leiloeiro = new Avaliador();
        leiloeiro.avalia(leilao);

        List<Lance> maiores = leiloeiro.getTresMaiores();

        assertEquals(0, maiores.size());
    }
}