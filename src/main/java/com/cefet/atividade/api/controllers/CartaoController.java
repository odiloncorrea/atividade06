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

import com.cefet.atividade.api.entities.Cartao;
import com.cefet.atividade.api.services.CartaoService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/cartao")
@Api(value = "cartao", tags = "Atividade - Cartao")
@CrossOrigin(origins = "*")
public class CartaoController {
	
	private static final Logger log = LoggerFactory.getLogger(CartaoController.class);

	@Autowired
	private CartaoService cartaoService;

	public CartaoController() {
	}
	
	
	/**
     * {@code POST  /} : Create a new cartao.
     *
     * @param pessoa the cartao to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartao, or with status {@code 400 (Bad Request)} if the cartao has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Cartao> createCartao(@Valid @RequestBody Cartao cartao) throws URISyntaxException {
        log.debug("REST request to save Cartao : {}", cartao);
        if (cartao.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo cartao não pode ter um ID");
        }
        Cartao result = cartaoService.save(cartao);
        return ResponseEntity.created(new URI("/api/cartao/" + result.getId()))
                .body(result);
    }
    
    /**
     * {@code PUT  /cartao} : Atualiza um cartao existente Update.
     *
     * @param cartao o cartão a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o cartão atualizado,
     * ou com status {@code 400 (Bad Request)} se o cartão não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o cartão não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Cartao> updateCartao(@Valid @RequestBody Cartao cartao) throws URISyntaxException {
        log.debug("REST request to update cartao : {}", cartao);
        if (cartao.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid veiculo id null");
        }
        Cartao result = cartaoService.save(cartao);
        return ResponseEntity.ok()
                .body(result);
    }
    
	
    /**
     * {@code GET  /cartao/:id} : get the "id" cartao.
     *
     * @param id o id do cartao que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o cartao, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Cartao> getCartao(@PathVariable Long id) {
        log.info("REST request to get cartao : {}", id);
        Optional<Cartao> cartao = cartaoService.findOne(id);
        if(cartao.isPresent()) {
            return ResponseEntity.ok().body(cartao.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Cartao>> getCartao(){
       List<Cartao> lista = cartaoService.findAllList();
       if(lista.size() > 0) {
           return ResponseEntity.ok().body(lista);
       }else{
           return ResponseEntity.notFound().build();
       }
    }
    
	
    /**
     * {@code DELETE  /cartao/:id} : delete pelo "id" cartao.
     *
     * @param id o id do cartao que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCartao(@PathVariable Long id) {
        log.info("REST request to delete cartao : {}", id);
        cartaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
  
    /**
     * {@code GET  /cartao/:cpf/exists} : get the "numero" cartao.
     *
     * @param cpf o cpf do cartao que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)}, ou com status {@code 204 (Not Found)}.
     */
    @GetMapping("/{numero}/exists")
    public ResponseEntity<Boolean> isExisting(@PathVariable String numero){
        log.info("REST request to get cartao By numero : {}", numero);

        if(cartaoService.findByNumero(numero).isPresent()) {
            return ResponseEntity.ok().body(Boolean.TRUE);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
