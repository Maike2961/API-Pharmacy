package io.github.farmacia.Farmacia.db.validador;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import io.github.farmacia.Farmacia.db.exceptions.ResgistroDuplicado;
import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.model.FornecedorLocais;
import io.github.farmacia.Farmacia.db.repository.FornecedorRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class ValidadorFornecedorTest {


    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private ValidadorFornecedor validadorFornecedor;

    @Captor
    private ArgumentCaptor<String> fornecedorArgumentCaptor;

    @Captor
    private ArgumentCaptor<FornecedorLocais> fornecedorLocaisArgumentCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }



    @Nested
    class validadorFornecedoresTest{

        @Test
        @DisplayName("should Pass When No Exist Fornecedor With Same Local And Companhia")
        void shouldPassWhenNoExistFornecedorWithSameLocalAndCompanhia(){
            Fornecedor fornecedor = createFornecedores();


            doReturn(Optional.of(fornecedor)).when(fornecedorRepository)
            .findByCompanhiaAndLocal(fornecedorArgumentCaptor.capture(), fornecedorLocaisArgumentCaptor.capture());

            validadorFornecedor.validar(fornecedor);

            assertEquals(fornecedor.getLocal(), fornecedorLocaisArgumentCaptor.getValue());

            verify(fornecedorRepository, times(1)).findByCompanhiaAndLocal(fornecedorArgumentCaptor.getValue(), fornecedorLocaisArgumentCaptor.getValue());
        }


        @Test
        @DisplayName("Should return a exception when there is a duplicate")
        void shouldThrowWhenDuplicateExistsWithNullId() {

            Fornecedor fornecedor = createFornecedores();
            Fornecedor existente = createFornecedores();

            doReturn(Optional.of(existente)).when(fornecedorRepository)
            .findByCompanhiaAndLocal(fornecedorArgumentCaptor.capture(), fornecedorLocaisArgumentCaptor.capture());

            ResgistroDuplicado exception = assertThrows(ResgistroDuplicado.class, () ->{
                validadorFornecedor.validar(fornecedor);
            });
            
            assertEquals("Fornecedor ja cadastrado com esse local", exception.getMessage());
            
        }
    }

    private Fornecedor createFornecedores(){
        Fornecedor fornecedor = new Fornecedor();
        fornecedor.setId(UUID.randomUUID());
        fornecedor.setCompanhia("Empresa Teste");
        fornecedor.setDataCadastro(LocalDateTime.now());
        fornecedor.setLocal(FornecedorLocais.ACRE);
        fornecedor.setDataAtualizacao(null);
        return fornecedor;
    }
}
