package br.com.caelum.leilao.teste;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.dominio.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.servico.Avaliador;

public class TesteAvaliador {

	private Avaliador leiloeiro;
	private Usuario maria;
	private Usuario jose;
	private Usuario joao;

	// novo método que cria o avaliador
	@Before
	public void criaAvaliador() {
		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {

		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 250.0));
		leilao.propoe(new Lance(jose, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		criaAvaliador();
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
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 1000.0));
		leilao.propoe(new Lance(jose, 2000.0));
		leilao.propoe(new Lance(maria, 3000.0));

		criaAvaliador();
		leiloeiro.avalia(leilao);

		assertEquals(3000.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	// @Ignore //pq lá no avaliador ele está pegando 3 elementos e aqui só tem um
	// maiores.subList(0, 3)
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 1000.0));

		criaAvaliador();
		leiloeiro.avalia(leilao);

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.0001);
	}

	/*
	 * @Test public void deveEncontrarOsTresMaioresLances() { Leilao leilao = new
	 * Leilao("Playstation 3 Novo");
	 * 
	 * leilao.propoe(new Lance(joao, 100.0)); leilao.propoe(new Lance(maria,
	 * 200.0)); leilao.propoe(new Lance(joao, 300.0)); leilao.propoe(new
	 * Lance(maria, 400.0));
	 * 
	 * criaAvaliador(); leiloeiro.avalia(leilao);
	 * 
	 * List<Lance> maiores = leiloeiro.getTresMaiores();
	 * 
	 * assertEquals(3, maiores.size()); assertEquals(400.0,
	 * maiores.get(0).getValor(), 0.00001); assertEquals(300.0,
	 * maiores.get(1).getValor(), 0.00001); assertEquals(200.0,
	 * maiores.get(2).getValor(), 0.00001); }
	 */

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		Leilao leilao2 = new CriadorDeLeilao().para("Playstation 3 Novo").lance(joao, 100.0).lance(maria, 200.0)
				.lance(joao, 300.0).lance(maria, 400.0).constroi();

		leiloeiro.avalia(leilao2);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertEquals(3, maiores.size());
		assertEquals(400.0, maiores.get(0).getValor(), 0.00001);
		assertEquals(300.0, maiores.get(1).getValor(), 0.00001);
		assertEquals(200.0, maiores.get(2).getValor(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComLancesEmOrdemRandomica() {
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 450.0));
		leilao.propoe(new Lance(joao, 120.0));
		leilao.propoe(new Lance(maria, 700.0));
		leilao.propoe(new Lance(joao, 630.0));
		leilao.propoe(new Lance(maria, 230.0));

		criaAvaliador();
		leiloeiro.avalia(leilao);

		assertEquals(700.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(120.0, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveEntenderLeilaoComLancesEmOrdemDecrescente() {
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 400.0));
		leilao.propoe(new Lance(maria, 300.0));
		leilao.propoe(new Lance(joao, 200.0));
		leilao.propoe(new Lance(maria, 100.0));

		criaAvaliador();
		leiloeiro.avalia(leilao);

		assertEquals(400.0, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(100.0, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void deveDevolverTodosLancesCasoNaoHajaNoMinimo3() {
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));

		criaAvaliador();
		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(2, maiores.size());
		assertEquals(200, maiores.get(0).getValor(), 0.00001);
		assertEquals(100, maiores.get(1).getValor(), 0.00001);
	}

	@Test
	public void deveDevolverListaVaziaCasoNaoHajaLances() {
		Leilao leilao = new Leilao("Playstation 3 Novo");

		criaAvaliador();
		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(0, maiores.size());
	}

}