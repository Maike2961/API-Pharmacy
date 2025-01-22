package io.github.farmacia.Farmacia.db.DTO;


import io.github.farmacia.Farmacia.db.model.Usuario;

public record UsuarioDTO(String login, String senha, String roles) {

    public Usuario mapear(){
        Usuario usuario = new Usuario();
        usuario.setLogin(this.login);
        usuario.setSenha(this.senha);
        usuario.setRoles(this.roles);
        return usuario;
    }
    
}
