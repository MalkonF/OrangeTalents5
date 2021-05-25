package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.pm73.dominio.Leilao;
import br.com.caelum.pm73.dominio.Usuario;

public class LeilaoDaoTest {
	private Session session;
	private LeilaoDao leilaoDao;
	private UsuarioDao usuarioDao;

	@Before
	public void antes() {
		session = new CriadorDeSessao().getSession();
		leilaoDao = new LeilaoDao(session);
		usuarioDao = new UsuarioDao(session);
		// sempre é uma boa prática fazer os testes em bd em contextos de transação para
		// sempre o banco iniciar limpo
		session.beginTransaction();
	}

	@After
	public void depois() {
		session.getTransaction().rollback(); // acabou os testes o banco volta pro estado anterior
		session.close();
	}

	@Test
	public void deveContarLeiloesNaoEncerrados() {
		// criamos um usuario
		Usuario mauricio = new Usuario("Mauricio Aniche", "mauricio@aniche.com.br");

		// criamos os dois leiloes
		Leilao ativo = new Leilao("Geladeira", 1500.0, mauricio, false);
		Leilao encerrado = new Leilao("XBox", 700.0, mauricio, false);
		encerrado.encerra();

		// persistimos todos no banco
		usuarioDao.salvar(mauricio);
		leilaoDao.salvar(ativo);
		leilaoDao.salvar(encerrado);

		// invocamos a acao que queremos testar
		// pedimos o total para o DAO
		long total = leilaoDao.total();

		assertEquals(1L, total);
	}
}
