package io.github.farmacia.Farmacia.db.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class FornecedorLocaisDeserializer extends JsonDeserializer<FornecedorLocais>{
    @Override
    public FornecedorLocais deserialize(JsonParser local, DeserializationContext arg1)
            throws IOException, JacksonException {
        String texto = local.getText().toUpperCase().replace(" ", "_");;
        return FornecedorLocais.valueOf(texto);
    }

}
