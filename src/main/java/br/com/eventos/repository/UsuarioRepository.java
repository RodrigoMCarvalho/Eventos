package br.com.eventos.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.eventos.models.Usuario;

public interface UsuarioRepository extends CrudRepository<Usuario, String>{
	Usuario findByLogin(String login);
}
