package com.example.CursoSpringYt.DAO;

import com.example.CursoSpringYt.models.Usuario;

import java.util.List;

public interface UsuarioDAO {

    List<Usuario> getUsuarios();

    void deleteUser(Long id);

    void crearUsuario(Usuario usuario);

    Usuario ObtenerUsuarioPorPassword(Usuario usuario);
}
