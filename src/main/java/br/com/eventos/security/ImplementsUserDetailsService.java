package br.com.eventos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import br.com.eventos.models.Usuario;
import br.com.eventos.repository.UsuarioRepository;

public class ImplementsUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository repo;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		
		System.out.println("Login: " + login);
		
		Usuario usuario = repo.findByLogin(login);

		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não localizado!");
		}

		return usuario;
	}

}
