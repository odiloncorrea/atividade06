package com.cefet.atividade.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cefet.atividade.api.entities.Veiculo;


@Repository
public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
		
	Optional<Veiculo> findByPlaca(String placa);
}
