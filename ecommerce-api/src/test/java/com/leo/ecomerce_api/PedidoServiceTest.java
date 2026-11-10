package com.leo.ecomerce_api;

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
import com.leo.ecomerce_api.service.PedidoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PedidoServiceTest {

    @InjectMocks
    private PedidoService pedidoService;

    @Mock
    private PedidoRepository pedidoRepository;

    @Mock
    private RepositoryEcommerce produtoEcommerce;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void deveLancarExcecaoQuandoEstoqueInsuficiente() {
        EntityEcommerce produto = new EntityEcommerce();
        produto.setId(1L);
        produto.setEstoque(2);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(produtoEcommerce.findAllById(List.of(1L)))
                .thenReturn(List.of(produto));

        PedidoItemDto item = new PedidoItemDto();
        item.setProdutoId(1L);
        item.setQuantidade(5);

        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setUsuarioId(1L);
        pedidoDto.setItens(List.of(item));

        assertThrows(
                EstoqueInsuficienteException.class,
                () -> pedidoService.criar(pedidoDto)
        );
    }

    @Test
    void deveCriarPedidoQuandoEstoqueSuficiente(){
        EntityEcommerce produto = new EntityEcommerce();
        produto.setId(1L);
        produto.setEstoque(10);
        produto.setPreco(100.0);

        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(produtoEcommerce.findAllById(List.of(1L)))
                .thenReturn(List.of(produto));

        PedidoItemDto item = new PedidoItemDto();
        item.setProdutoId(1L);
        item.setQuantidade(3);

        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setUsuarioId(1L);
        pedidoDto.setItens(List.of(item));

        when(pedidoRepository.save(ArgumentMatchers.any(Pedido.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Pedido pedidoCriado = pedidoService.criar(pedidoDto);

        assertEquals(300.0, pedidoCriado.getTotal());
        assertEquals(7, produto.getEstoque());

        verify(pedidoRepository, times(1)).save(ArgumentMatchers.any(Pedido.class));

    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExiste() {
        Usuario usuario = new Usuario();
        usuario.setId(1L);

        when(usuarioRepository.findById(1L))
                .thenReturn(Optional.of(usuario));

        when(produtoEcommerce.findAllById(List.of(99L)))
                .thenReturn(List.of());

        PedidoItemDto item = new PedidoItemDto();
        item.setProdutoId(99L);
        item.setQuantidade(1);

        PedidoDto pedidoDto = new PedidoDto();
        pedidoDto.setUsuarioId(1L);
        pedidoDto.setItens(List.of(item));

        assertThrows(
                ProdutoNaoENcontradoException.class,
                () -> pedidoService.criar(pedidoDto)
        );

        verify(pedidoRepository, never()).save(ArgumentMatchers.any(Pedido.class));

    }

}
