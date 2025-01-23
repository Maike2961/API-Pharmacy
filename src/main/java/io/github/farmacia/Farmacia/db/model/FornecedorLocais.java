package io.github.farmacia.Farmacia.db.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = FornecedorLocaisDeserializer.class)
public enum FornecedorLocais {
    SAO_PAULO(1L, "sao paulo"), 
    BAHIA(2L, "bahia"), 
    CEARA(3L, "ceara"), 
    MINAS_GERAIS(4L, "minas gerais"), 
    AMAZONAS(5L, "amazonas"), 
    ACRE(6L,"acre"), 
    GOIAS(7L, "goias"), 
    RIO_DE_JANEIRO(8L, "rio de janeiro"), 
    ESPIRITO_SANTO(9l, "espirito santo");

    private Long id;
    private String locais;

    private FornecedorLocais(Long id,String locais){
        this.id = id;
        this.locais = locais;
    }


    
}
