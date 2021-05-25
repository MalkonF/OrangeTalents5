package br.com.caelum.pm73.dao;

import static org.junit.Assert.assertEquals;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;
import org.mockito.Mockito;

import br.com.caelum.pm73.dominio.Usuario;

public class UsuarioDaoTest {

	@Test
	public void deveEncontrarPeloNomeEEmailMockado() {

		// Usar mock não faz sentido em banco de dados
		Session session = Mockito.mock(Session.class); // simula uma connection com banco de dados
		Query query = Mockito.mock(Query.class);
		UsuarioDao usuarioDao = new UsuarioDao(session);

		Usuario usuario = new Usuario("João da Silva", "joao@dasilva.com.br");
		String sql = "from Usuario u where u.nome = :nome and x.email = :email";

		Mockito.when(session.createQuery(sql)).thenReturn(query);// aqui é mocado tb o createQuery
		Mockito.when(query.uniqueResult()).thenReturn(usuario); // qd executa a query acima ela retorna um user
		Mockito.when(query.setParameter("nome", "João da Silva")).thenReturn(query);
		Mockito.when(query.setParameter("email", "joao@dasilva.com.br")).thenReturn(query);
		// as 2 últimas linhas retorna a query da linha 21 com os parametros passados

		Usuario usuarioDoBanco = usuarioDao.porNomeEEmail("João da Silva", "joao@dasilva.com.br");

		assertEquals(usuario.getNome(), usuarioDoBanco.getNome());
		assertEquals(usuario.getEmail(), usuarioDoBanco.getEmail());
	}

}
