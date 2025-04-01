package io.github.farmacia.Farmacia.db.service;

import io.github.farmacia.Farmacia.db.exceptions.OperacaoNaoPermitida;
import io.github.farmacia.Farmacia.db.model.Fornecedor;
import io.github.farmacia.Farmacia.db.model.FornecedorLocais;
import io.github.farmacia.Farmacia.db.model.Item;
import io.github.farmacia.Farmacia.db.repository.FornecedorRepository;
import io.github.farmacia.Farmacia.db.repository.ItemRepository;
import io.github.farmacia.Farmacia.db.validador.ValidadorFornecedor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DataJpaTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class FornecedorServiceTest {

    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ValidadorFornecedor validadorFornecedor;

    @InjectMocks
    private FornecedorService service;

    @Captor
    private ArgumentCaptor<Fornecedor> fornecedorArgumentCaptor;

    @Captor
    private ArgumentCaptor<Item> itemArgumentCaptor;

    @Captor
    private ArgumentCaptor<UUID> uuidArgumentCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Nested
    class createFornecedor {
        

        @Test
        @DisplayName("should Call Validation And Save With Success")
        void shouldCallValidationAndSaveWithSuccess() {

            var fornecedor = createFornecedores();
            doReturn(fornecedor).when(fornecedorRepository)
                    .save(fornecedorArgumentCaptor.capture());

            Fornecedor salvar = service.salvar(fornecedor);

            assertNotNull(salvar);
            verify(validadorFornecedor, times(1)).validar(fornecedor);
            verify(fornecedorRepository, times(1)).save(fornecedorArgumentCaptor.capture());
            verifyNoMoreInteractions(fornecedorRepository, validadorFornecedor);

        }

    }

    @Nested
    class listFornecedor{


        @Test
        @DisplayName("Should All Fornecedores with Success When No Filter")
        void shouldAllFornecedoresWithSuccessWhenNoFilter(){

            Fornecedor fornecedor2 = new Fornecedor();
            fornecedor2.setId(UUID.randomUUID());
            fornecedor2.setCompanhia("Empresa Teste 2");
            fornecedor2.setDataCadastro(LocalDateTime.now());
            fornecedor2.setLocal(FornecedorLocais.ACRE);
            fornecedor2.setDataAtualizacao(null);

            Fornecedor fornecedor = createFornecedores();
            doReturn(List.of(fornecedor, fornecedor2)).when(fornecedorRepository).findAll();

            List<Fornecedor> fornecedorsList = service.pesquisa(null);

            assertNotNull(fornecedorsList);

            assertEquals(2, fornecedorsList.size());

            verify(fornecedorRepository, times(0)).findByCompanhiaLike("%New%");
            verify(fornecedorRepository, times(1)).findAll();
        }


        @Test
        @DisplayName("Should All Fornecedores with Success By Letter Of Companhia Name")
        void shouldListFornecedoresWithSuccessByLetterOfCompanhiaName(){

            Fornecedor fornecedor2 = new Fornecedor();
            fornecedor2.setId(UUID.randomUUID());
            fornecedor2.setCompanhia("New Empresa Teste");
            fornecedor2.setDataCadastro(LocalDateTime.now());
            fornecedor2.setLocal(FornecedorLocais.ACRE);
            fornecedor2.setDataAtualizacao(null);

            
            Fornecedor fornecedor3 = new Fornecedor();
            fornecedor3.setId(UUID.randomUUID());
            fornecedor3.setCompanhia("New Empresa Teste");
            fornecedor3.setDataCadastro(LocalDateTime.now());
            fornecedor3.setLocal(FornecedorLocais.ACRE);
            fornecedor3.setDataAtualizacao(null);

           //Fornecedor fornecedor = createFornecedores();

            doReturn(List.of(fornecedor2, fornecedor3)).when(fornecedorRepository).findByCompanhiaLike("%New%");

            List<Fornecedor> fornecedorsList = service.pesquisa("New");

            assertNotNull(fornecedorsList);

            assertEquals(2, fornecedorsList.size());

            verify(fornecedorRepository, times(1)).findByCompanhiaLike("%New%");
            verify(fornecedorRepository, times(0)).findAll();
        }


        @Test
        @DisplayName("Should return a empty list when no matches found")
        void shouldListEmptyFornecedoresWhenNotMatches(){


            doReturn(Collections.emptyList()).when(fornecedorRepository).findByCompanhiaLike("%NoContent%");

            List<Fornecedor> fornecedorsList = service.pesquisa("NoContent");

            assertNotNull(fornecedorsList);

            verify(fornecedorRepository, times(1)).findByCompanhiaLike("%NoContent%");
            verify(fornecedorRepository, times(0)).findAll();
        }



    }

    @Nested
    class GetFornecedorById{


        @Test
        @DisplayName("Should get fornecedor by id with success when Optional Is Present")
        void shouldGetFornecedorByIdWithSuccessWhenOptionalIsPresent(){

            Fornecedor fornecedor = createFornecedores();

            doReturn(Optional.of(fornecedor)).when(fornecedorRepository).findById(uuidArgumentCaptor.capture());

            Optional<Fornecedor> byId = service.getById(fornecedor.getId());

            assertTrue(byId.isPresent());

            assertEquals(fornecedor.getId(), uuidArgumentCaptor.getValue());
        }


        @Test
        @DisplayName("Should not get fornecedor by id with success when Optional Is Empty")
        void shouldNotGetFornecedorByIdWithSuccessWhenOptionalIsEmpty(){

            Fornecedor fornecedor = createFornecedores();

            doReturn(Optional.empty()).when(fornecedorRepository).findById(uuidArgumentCaptor.capture());

            Optional<Fornecedor> byId = service.getById(fornecedor.getId());

            assertTrue(byId.isEmpty());

            assertEquals(fornecedor.getId(), uuidArgumentCaptor.getValue());
        }
    }

    @Nested
    class DeleteById{

        Fornecedor fornecedor = createFornecedores();

        @Test
        @DisplayName("Should delete a Fornecedor By Id With Success")
        void shouldDeleteAFornecedorByIdWithSuccess(){

            doReturn(false).when(itemRepository).existsByFornecedor(fornecedorArgumentCaptor.capture());
            doNothing().when(fornecedorRepository).delete(fornecedorArgumentCaptor.capture());

            service.deletar(fornecedor);

            assertEquals(fornecedor, fornecedorArgumentCaptor.getValue());

            verify(fornecedorRepository, times(1)).delete(fornecedorArgumentCaptor.getValue());
            verify(itemRepository, times(1)).existsByFornecedor(fornecedorArgumentCaptor.getValue());
        }


        @Test
        @DisplayName("Should throw exception when fornecedor has items")
        void shouldThrowExceptionWhenFornecedorHasItems() {

            doReturn(true).when(itemRepository).existsByFornecedor(fornecedorArgumentCaptor.capture());

            assertThrows(OperacaoNaoPermitida.class, () -> {
                service.deletar(fornecedor);
            });

            assertEquals(fornecedor, fornecedorArgumentCaptor.getValue());

            verify(itemRepository, times(1)).existsByFornecedor(fornecedorArgumentCaptor.getValue());
            verify(fornecedorRepository, times(0)).delete(fornecedorArgumentCaptor.getValue());
        }
    }

    @Nested
    class UpdateFornecedorById{

        @Test
        @DisplayName("should update a fornecedor by id with success")
        void shouldUpdateAFornecedorByIdWithSuccess(){

            Fornecedor fornecedor = new Fornecedor();
            fornecedor.setId(UUID.randomUUID());
            fornecedor.setCompanhia("Empresa Teste 2");
            fornecedor.setDataCadastro(LocalDateTime.now());
            fornecedor.setLocal(FornecedorLocais.CEARA);
            fornecedor.setDataAtualizacao(null);

            doNothing().when(validadorFornecedor).validar(fornecedor);
            doReturn(fornecedor).when(fornecedorRepository).save(fornecedorArgumentCaptor.capture());

            service.atualizar(fornecedor);

            assertEquals(fornecedor, fornecedorArgumentCaptor.getValue());
            assertEquals(fornecedor.getId(), fornecedorArgumentCaptor.getValue().getId());
            assertEquals(fornecedor.getCompanhia(), fornecedorArgumentCaptor.getValue().getCompanhia());
            assertEquals(fornecedor.getLocal(), fornecedorArgumentCaptor.getValue().getLocal());
            

            verify(fornecedorRepository, times(1)).save(fornecedorArgumentCaptor.getValue());
            verify(validadorFornecedor, times(1)).validar(fornecedorArgumentCaptor.getValue());
            verifyNoMoreInteractions(validadorFornecedor, fornecedorRepository);

        }


        @Test
        @DisplayName("should not update a fornecedor by id with success when id is null")
        void shouldNotUpdateAFornecedorByIdWhenIdIsNull(){

            Fornecedor fornecedor = createFornecedores();
            fornecedor.setId(null);

            IllegalArgumentException assertThrows = assertThrows(IllegalArgumentException.class, () -> {
                            service.atualizar(fornecedor);
                        });;

            assertEquals("Para Atualizar, é necessario ter um usuário cadastrado", assertThrows.getMessage());

            verifyNoInteractions(fornecedorRepository);
            verifyNoInteractions(validadorFornecedor);
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