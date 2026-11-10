package com.leo.ecomerce_api.service;


import com.leo.ecomerce_api.dto.PedidoDto;
import com.leo.ecomerce_api.dto.PedidoItemDto;
import com.leo.ecomerce_api.entity.EntityEcommerce;
import com.leo.ecomerce_api.entity.Pedido;
import com.leo.ecomerce_api.entity.Usuario;
import com.leo.ecomerce_api.exception.EstoqueInsuficienteException;
import com.leo.ecomerce_api.exception.ProdutoNaoENcontradoException;
import com.leo.ecomerce_api.repository.PedidoRepository;
import com.leo.ecomerce_api.repository.RepositoryEcommerce;
import com.leo.ecomerce_api.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoService {
    private final PedidoRepository pedidoRepository;

    private final RepositoryEcommerce produtoRepository;

    private final UsuarioRepository usuarioRepository;


    @Transactional
    public Pedido criar(PedidoDto dto) {
        Usuario usuario = usuarioRepository.findById(dto.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        List<Long> ids = dto.getItens()
                .stream()
                .map(PedidoItemDto::getProdutoId)
                .toList();

        List<EntityEcommerce> produtos = produtoRepository.findAllById(ids);

        double total = 0.0;

        for (PedidoItemDto item : dto.getItens()) {

            EntityEcommerce produto = produtos.stream()
                    .filter(p -> p.getId().equals(item.getProdutoId()))
                    .findFirst()
                    .orElseThrow(() -> new ProdutoNaoENcontradoException(
                            "O produto de ID " + item.getProdutoId() + " Não existe"
                    ));


            if(item.getQuantidade() == null || item.getQuantidade() <= 0) {
                throw new RuntimeException("Quantidade insuficiente");
            }
            if(produto.getEstoque() < item.getQuantidade()) {
                throw new EstoqueInsuficienteException("Estoque insuficiente");
            }

            produto.setEstoque(
                    produto.getEstoque() - item.getQuantidade()
            );

            total += produto.getPreco() * item.getQuantidade();


        }

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

