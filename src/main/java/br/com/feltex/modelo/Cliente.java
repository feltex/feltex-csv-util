package br.com.feltex.modelo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Cliente {
    private String nome;
    private String email;
    private String telefone;
}
