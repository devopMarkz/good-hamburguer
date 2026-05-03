package com.devopMarkz.good_hamburguer.model.enums;

import lombok.Getter;

@Getter
public enum Categoria {
    SANDUICHES("sanduiches"),
    ACOMPANHAMENTOS("acompanhamentos");

    final String descricao;

    Categoria(String descricao){
        this.descricao = descricao;
    }
}
