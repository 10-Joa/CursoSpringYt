package com.example.CursoSpringYt.controllers;

import com.example.CursoSpringYt.DAO.UsuarioDAO;
import com.example.CursoSpringYt.models.Usuario;
import com.example.CursoSpringYt.utils.JWTUtils;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioDAO usuarioDAO;

    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value = "api/usuarios", method = RequestMethod.GET)
    public List<Usuario> getUsuarios(@RequestHeader(value = "Authorization") String token){  //aqui pbtenemos el token desde el header del fethc q lo mandamos para verificar si es el mismo y hacer q muestre datos
        if(!validarToken(token)){return null;}  //quiere decir que si es falso retorna null

        return usuarioDAO.getUsuarios();
    }

    public boolean validarToken(String token){
        String usuarioIDtoke= jwtUtils.getKey(token); //con esto obtengo el id del usuario si esque el token coincide si es correcto el token es valido
      /*  if(usuarioIDtoke == null ){
            return new ArrayList<>(); //si no es correcto osea nulo entrega la lista vacia
        }*/
        return usuarioIDtoke !=null;   //si el usuario token es diferente de null siempre devuelve verdadero
    }

    @RequestMapping(value = "api/usuarios" , method = RequestMethod.POST)
    public void registrarUsuario(@RequestBody Usuario usuario){
        //para encriptar la contraseña hash con argon2
        Argon2 argon2= Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(1,1024,1, usuario.getPassword()); //aqui la encripto primero 1 interaciones, 1024 en memotia,1paralelismo , en interacciones puede se mas de 1 solo q se pondria mas lento
        usuario.setPassword(hash); //aqui guardo ya la contraseña encriptada
        usuarioDAO.crearUsuario(usuario);
    }

    @RequestMapping(value = "api/usuarios/{id}" , method = RequestMethod.DELETE)
    public void eliminarUsuario(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return ;}  //quiere decir que si es falso retorna null

        usuarioDAO.deleteUser(id);
    }


}
