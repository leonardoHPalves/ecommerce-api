package com.leo.ecomerce_api.controller;

import com.leo.ecomerce_api.dto.DtoEcommerce;
import com.leo.ecomerce_api.entity.EntityEcommerce;
import com.leo.ecomerce_api.service.ServiceEcommerce;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class ControllerEcommerce {

    private final ServiceEcommerce service;

    @GetMapping
    public Page<EntityEcommerce> listar(@RequestParam(defaultValue = "0" ) int page,
                                        @RequestParam(defaultValue = "10") int size){
        return ResponseEntity.ok(service.listarTodos(page, size)).getBody();
    }

    @GetMapping("/{id}")
    public EntityEcommerce buscar(@PathVariable Long id){
        return service.buscarPorId(id);
    }

    @PostMapping
    public EntityEcommerce criar(@Valid  @RequestBody DtoEcommerce dto){
        return service.criar(dto);
    }

    @PutMapping("/{id}")
    public EntityEcommerce atualizar(@Valid @PathVariable Long id, @Valid @RequestBody DtoEcommerce dto){
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){
        service.deletar(id);
    }


}
