package com.leo.ecomerce_api.service;


import com.leo.ecomerce_api.dto.PedidoDto;
import com.leo.ecomerce_api.entity.EntityEcommerce;
import com.leo.ecomerce_api.entity.Pedido;
import com.leo.ecomerce_api.entity.Usuario;
import com.leo.ecomerce_api.repository.PedidoRepository;
import com.leo.ecomerce_api.repository.RepositoryEcommerce;
import com.leo.ecomerce_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    private final RepositoryEcommerce produtoRepository;

    private final UsuarioRepository usuarioRepository;



    public Pedido criar(PedidoDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<EntityEcommerce> produtos = produtoRepository.findAllById(dto.getProdutosIds());
        if (produtos.isEmpty()){
            throw new RuntimeException("Nenhum produto encontrado");
        }

        Double total = produtos.stream()
                .mapToDouble(EntityEcommerce ::getPreco )
                .sum();

        Pedido pedido = new Pedido();
        pedido.setUsuario(usuario);
        pedido.setProdutos(produtos);
        pedido.setTotal(total);
        pedido.setStatus("PENDENTE");
        pedido.setDataPedido(LocalDateTime.now());

        return pedidoRepository.save(pedido);
}

public  List<Pedido>listarTodos() {
    return pedidoRepository.findAll();
}

public List<Pedido> listarPorUsuario(Long usuarioId) {
        return pedidoRepository.findByUsuarioId(usuarioId);
}

public  Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
}

}

