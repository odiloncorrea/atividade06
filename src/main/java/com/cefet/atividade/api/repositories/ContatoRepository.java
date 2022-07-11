package com.cefet.atividade.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.atividade.api.entities.Contato;


@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {
		
	Optional<Contato> findByCpf(String cpf);
}
