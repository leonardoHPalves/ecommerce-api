package com.leo.ecomerce_api.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class PedidoDto {

    @NotNull(message = "Usuário é obrigatório")
    private Long usuarioId;

    @NotEmpty(message = "Pedido deve ter pelo menos um produto")
    private List<Long> produtosIds;
}
