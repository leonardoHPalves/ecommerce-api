package com.leo.ecomerce_api.controller;


import com.leo.ecomerce_api.dto.PedidoDto;
import com.leo.ecomerce_api.entity.Pedido;
import com.leo.ecomerce_api.service.PedidoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class PedidoController {
    private final PedidoService pedidoService;


    @PostMapping
    public ResponseEntity<Pedido> criar(@Valid @RequestBody PedidoDto dto){
        return ResponseEntity.ok(pedidoService.criar(dto));
    }

    @GetMapping
    public ResponseEntity<List<Pedido>> listar(){
        return ResponseEntity.ok(pedidoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id){
        return ResponseEntity.ok(pedidoService.buscarPorId(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Pedido>> listarPorUsuario(@PathVariable Long usuarioId){
        return ResponseEntity.ok(pedidoService.listarPorUsuario(usuarioId));
    }
}
