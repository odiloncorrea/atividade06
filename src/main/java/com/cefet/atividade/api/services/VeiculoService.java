package com.cefet.atividade.api.services;

import java.util.Optional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.atividade.api.entities.Veiculo;
import com.cefet.atividade.api.repositories.VeiculoRepository;


@Service
public class VeiculoService{
	
	private static final Logger log = LoggerFactory.getLogger(VeiculoService.class);

	@Autowired
	private VeiculoRepository veiculoRepository;
	
	public Veiculo save(Veiculo veiculo) {
		log.info("Persistindo veiculo: {}", veiculo);
		return veiculoRepository.save(veiculo);
	}
			
	public Optional<Veiculo> findOne(Long id) {
		log.info("Excluindo veiculo: {}", id);
		return veiculoRepository.findById(id);
	}
	
	public List<Veiculo> findAllList() {
		log.info("Listando os veiculos");
		return veiculoRepository.findAll();
	}
	
	public void delete(Long id) {
		log.info("Excluindo veiculo: {}", id);
		veiculoRepository.deleteById(id);
	}
	
	public Optional<Veiculo> findByPlaca(String placa) {
		log.info("Buscando um veiculo pela placa {}", placa);
		return veiculoRepository.findByPlaca(placa);
	}

}
