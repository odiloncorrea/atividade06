package com.cefet.atividade.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.atividade.api.entities.Cartao;


@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {
		
	Optional<Cartao> findByNumero(String numero);
}
