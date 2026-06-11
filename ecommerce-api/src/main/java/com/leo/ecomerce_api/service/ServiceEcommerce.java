package com.leo.ecomerce_api.service;

import com.leo.ecomerce_api.dto.DtoEcommerce;
import com.leo.ecomerce_api.entity.EntityEcommerce;
import com.leo.ecomerce_api.repository.RepositoryEcommerce;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceEcommerce {

    private final RepositoryEcommerce repository;

    public Page<EntityEcommerce> listarTodos(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return repository.findAll(pageable);
    }

    public EntityEcommerce buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("produto não encontrado"));

    }

    public EntityEcommerce criar(DtoEcommerce dto) {
        EntityEcommerce produto = new EntityEcommerce();
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        return repository.save(produto);
    }

    public EntityEcommerce atualizar(Long id, DtoEcommerce dto) {
        EntityEcommerce produto = buscarPorId(id);
        produto.setNome(dto.getNome());
        produto.setDescricao(dto.getDescricao());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        return repository.save(produto);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
