package br.com.passos.api_reserva_de_mesas.service;

import br.com.passos.api_reserva_de_mesas.domain.usuario.Role;
import br.com.passos.api_reserva_de_mesas.domain.usuario.Usuario;
import br.com.passos.api_reserva_de_mesas.domain.usuario.UsuarioRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTest {

    @InjectMocks
    private UsuarioService usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    @DisplayName("Deve criar um usuário com sucesso")
    void deveCriarUsuarioComSucesso(){
        //Arrange
        var usuario = criarUsuarioDefault();
        when(usuarioRepository.existsByEmail("paulo@paulo.com")).thenReturn(false);
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        //Act
        var resultado = usuarioService.cadastrar(usuario);

        //Assert
        assertNotNull(resultado);
        assertEquals("Paulo", resultado.getNome());
        assertEquals("paulo@paulo.com", resultado.getEmail());
        verify(usuarioRepository).existsByEmail("paulo@paulo.com");
        verify(usuarioRepository).save(any(Usuario.class));
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já está cadastrado")
    void deveLancarExcecaoQuandoEmailJaCadastrado(){
        //Arrange
        var usuario = criarUsuarioDefault();
        when(usuarioRepository.existsByEmail("paulo@paulo.com")).thenReturn(true);

        //Act & Assert
        EmailJaCadastradoException exception = assertThrows(
                EmailJaCadastradoException.class,
                () -> usuarioService.cadastrar(usuario)
        );

        assertNotNull(exception.getMessage());
        verify(usuarioRepository).existsByEmail("paulo@paulo.com");
        verify(usuarioRepository, never()).save(any(Usuario.class));
    }

    private Usuario criarUsuarioDefault() {
        var usuario = new Usuario();
        usuario.setNome("Paulo");
        usuario.setEmail("paulo@paulo.com");
        usuario.setSenha("123456789");
        usuario.setRole(Role.ADMIN);
        return usuario;
    }

}