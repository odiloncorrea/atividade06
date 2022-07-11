package com.cefet.atividade.api.services;

import java.util.Optional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.atividade.api.entities.Contato;
import com.cefet.atividade.api.repositories.ContatoRepository;


@Service
public class ContatoService{
	
	private static final Logger log = LoggerFactory.getLogger(ContatoService.class);

	@Autowired
	private ContatoRepository contatoRepository;
	
	public Contato save(Contato contato) {
		log.info("Persistindo contato: {}", contato);
		return contatoRepository.save(contato);
	}
			
	public Optional<Contato> findOne(Long id) {
		log.info("Excluindo contato: {}", id);
		return contatoRepository.findById(id);
	}
	
	public List<Contato> findAllList() {
		log.info("Listando os contatos");
		return contatoRepository.findAll();
	}
	
	public void delete(Long id) {
		log.info("Excluindo contato: {}", id);
		contatoRepository.deleteById(id);
	}
	
	public Optional<Contato> findByCpf(String cpf) {
		log.info("Buscando um contato para o cpf {}", cpf);
		return contatoRepository.findByCpf(cpf);
	}

}
