package com.cefet.atividade.api.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.cefet.atividade.api.entities.Contato;
import com.cefet.atividade.api.services.ContatoService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/contatos")
@Api(value = "contatos", tags = "Aplicativo 05 - Contatos")
@CrossOrigin(origins = "*")
public class ContatoController {
	
	private static final Logger log = LoggerFactory.getLogger(ContatoController.class);

	@Autowired
	private ContatoService contatoService;

	public ContatoController() {
	}
	
	
	/**
     * {@code POST  /} : Create a new contato.
     *
     * @param pessoa the contato to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contato, or with status {@code 400 (Bad Request)} if the contato has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Contato> createPessoa(@Valid @RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", contato);
        if (contato.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo contato não pode ter um ID");
        }
        Contato result = contatoService.save(contato);
        return ResponseEntity.created(new URI("/api/contatos/" + result.getId()))
                .body(result);
    }
    
    /**
     * {@code PUT  /contatos} : Atualiza um contato existente Update.
     *
     * @param contato o contato a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o contato atualizado,
     * ou com status {@code 400 (Bad Request)} se o contato não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o contato não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Contato> updatePessoa(@Valid @RequestBody Contato contato) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}", contato);
        if (contato.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Pessoa id null");
        }
        Contato result = contatoService.save(contato);
        return ResponseEntity.ok()
                .body(result);
    }
    
	
    /**
     * {@code GET  /contatos/:id} : get the "id" contato.
     *
     * @param id o id do contato que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o contato, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Contato> getContato(@PathVariable Long id) {
        log.info("REST request to get Contato : {}", id);
        Optional<Contato> contato = contatoService.findOne(id);
        if(contato.isPresent()) {
            return ResponseEntity.ok().body(contato.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Contato>> getContatoss(){
       List<Contato> lista = contatoService.findAllList();
       if(lista.size() > 0) {
           return ResponseEntity.ok().body(lista);
       }else{
           return ResponseEntity.notFound().build();
       }
    }
    
	
    /**
     * {@code DELETE  /contatos/:id} : delete pelo "id" contato.
     *
     * @param id o id do contatos que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContato(@PathVariable Long id) {
        log.info("REST request to delete contato : {}", id);
        contatoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
  
    /**
     * {@code GET  /contatos/:cpf/exists} : get the "cpf" contato.
     *
     * @param cpf o cpf do contato que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)}, ou com status {@code 204 (Not Found)}.
     */
    @GetMapping("/{cpf}/exists")
    public ResponseEntity<Boolean> isExisting(@PathVariable String cpf){
        log.info("REST request to get Contato By Cpf : {}", cpf);

        if(contatoService.findByCpf(cpf).isPresent()) {
            return ResponseEntity.ok().body(Boolean.TRUE);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
