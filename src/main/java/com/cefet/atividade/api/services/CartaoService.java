package com.cefet.atividade.api.services;

import java.util.Optional;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.atividade.api.entities.Cartao;
import com.cefet.atividade.api.repositories.CartaoRepository;


@Service
public class CartaoService{
	
	private static final Logger log = LoggerFactory.getLogger(CartaoService.class);

	@Autowired
	private CartaoRepository cartaoRepository;
	
	public Cartao save(Cartao cartao) {
		log.info("Persistindo cartão: {}", cartao);
		return cartaoRepository.save(cartao);
	}
			
	public Optional<Cartao> findOne(Long id) {
		log.info("Excluindo cartão: {}", id);
		return cartaoRepository.findById(id);
	}
	
	public List<Cartao> findAllList() {
		log.info("Listando os cartões");
		return cartaoRepository.findAll();
	}
	
	public void delete(Long id) {
		log.info("Excluindo cartão: {}", id);
		cartaoRepository.deleteById(id);
	}
	
	public Optional<Cartao> findByNumero(String numero) {
		log.info("Buscando um cartão por número{}", numero);
		return cartaoRepository.findByNumero(numero);
	}

}
