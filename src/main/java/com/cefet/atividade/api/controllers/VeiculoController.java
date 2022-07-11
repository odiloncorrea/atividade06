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

import com.cefet.atividade.api.entities.Veiculo;
import com.cefet.atividade.api.services.VeiculoService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/veiculo")
@Api(value = "veiculo", tags = "Atividade - Veiculo")
@CrossOrigin(origins = "*")
public class VeiculoController {
	
	private static final Logger log = LoggerFactory.getLogger(VeiculoController.class);

	@Autowired
	private VeiculoService veiculoService;

	public VeiculoController() {
	}
	
	
	/**
     * {@code POST  /} : Create a new veiculo.
     *
     * @param pessoa the veiculo to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new veiculo, or with status {@code 400 (Bad Request)} if the veiculo has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/")
    public ResponseEntity<Veiculo> createVeiculo(@Valid @RequestBody Veiculo veiculo) throws URISyntaxException {
        log.debug("REST request to save Veiculo : {}", veiculo);
        if (veiculo.getId() != null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Um novo veiculo não pode ter um ID");
        }
        Veiculo result = veiculoService.save(veiculo);
        return ResponseEntity.created(new URI("/api/veiculo/" + result.getId()))
                .body(result);
    }
    
    /**
     * {@code PUT  /veiculo} : Atualiza um veiculo existente Update.
     *
     * @param veiculo o veiculo a ser atulizado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no corpo o veiculo atualizado,
     * ou com status {@code 400 (Bad Request)} se o veiculo não é válido,
     * ou com status {@code 500 (Internal Server Error)} se o veiculo não pode ser atualizado.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/")
    public ResponseEntity<Veiculo> updateVeiculo(@Valid @RequestBody Veiculo veiculo) throws URISyntaxException {
        log.debug("REST request to update veiculo : {}", veiculo);
        if (veiculo.getId() == null) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid Veiculo id null");
        }
        Veiculo result = veiculoService.save(veiculo);
        return ResponseEntity.ok()
                .body(result);
    }
    
	
    /**
     * {@code GET  /veiculo/:id} : get the "id" veiculo.
     *
     * @param id o id do veiculo que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)} e no body o veiculo, ou com status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Veiculo> getVeiculo(@PathVariable Long id) {
        log.info("REST request to get veiculo : {}", id);
        Optional<Veiculo> veiculo = veiculoService.findOne(id);
        if(veiculo.isPresent()) {
            return ResponseEntity.ok().body(veiculo.get());
        }else{
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/")
    public ResponseEntity<List<Veiculo>> getVeiculo(){
       List<Veiculo> lista = veiculoService.findAllList();
       if(lista.size() > 0) {
           return ResponseEntity.ok().body(lista);
       }else{
           return ResponseEntity.notFound().build();
       }
    }
    
	
    /**
     * {@code DELETE  /veiculo/:id} : delete pelo "id" veiculo.
     *
     * @param id o id do veiculo que será delete.
     * @return o {@link ResponseEntity} com status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVeiculo(@PathVariable Long id) {
        log.info("REST request to delete veiculo : {}", id);
        veiculoService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
  
    /**
     * {@code GET  /veiculo/:placa/exists} : get the "placa" veiculo.
     *
     * @param placa o placa do veiculo que será buscado.
     * @return o {@link ResponseEntity} com status {@code 200 (OK)}, ou com status {@code 204 (Not Found)}.
     */
    @GetMapping("/{placa}/exists")
    public ResponseEntity<Boolean> isExisting(@PathVariable String placa){
        log.info("REST request to get veiculo by placa : {}", placa);

        if(veiculoService.findByPlaca(placa).isPresent()) {
            return ResponseEntity.ok().body(Boolean.TRUE);
        }else{
            return ResponseEntity.noContent().build();
        }
    }
}
