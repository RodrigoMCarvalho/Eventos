package br.com.eventos.repository;

import org.springframework.data.repository.CrudRepository;
import br.com.eventos.models.Evento;

public interface EventoRepository extends CrudRepository<Evento, String>{
	Evento findByCodigo(long codigo);
}
