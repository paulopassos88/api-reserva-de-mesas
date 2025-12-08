package br.com.passos.api_reserva_de_mesas.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioRequest(

        @NotBlank(message = "Usuário é obrigatório")
        @Size(min = 3, max = 255, message = "Usuário deve ter entre 3 e 150 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Usuário deve conter apenas letras, números, pontos, hífens e underscores")
        String nome,

        @NotBlank(message = "Email é obrigatório")
        @Email(message = "Email deve ter um formato válido")
        @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
        String email,

        @NotBlank(message = "Senha é obrigatório")
        @Size(min = 3, max = 10, message = "Senha deve ter entre 3 e 10 caracteres")
        @Pattern(regexp = "^[a-zA-Z0-9._-]+$", message = "Senha deve conter apenas letras, números, pontos, hífens e underscores")
        String senha,

        @NotBlank(message = "Status é obrigatório")
        Role role
) {
}
