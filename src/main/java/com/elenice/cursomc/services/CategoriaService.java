package com.elenice.cursomc.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.elenice.cursomc.domain.Categoria;
import com.elenice.cursomc.repositories.CategoriaRepository;
import com.elenice.cursomc.services.exceptions.ObjectNotFoundException;

//classe que faz a consulta no repositório
@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	// Busca produto por id, se não existir, lança uma exception
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

}