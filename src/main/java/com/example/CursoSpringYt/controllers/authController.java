package com.example.CursoSpringYt.controllers;

import com.example.CursoSpringYt.DAO.UsuarioDAO;
import com.example.CursoSpringYt.models.Usuario;
import com.example.CursoSpringYt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class authController {

    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private JWTUtils jwtUtils;

    @RequestMapping(value = "api/login" , method = RequestMethod.POST)
    public String loginUsuario(@RequestBody Usuario usuario){
        Usuario usuario_logeado = usuarioDAO.ObtenerUsuarioPorPassword(usuario);
        if(usuario_logeado != null){
           String tokenJWT =  jwtUtils.create(String.valueOf(usuario_logeado.getId()), usuario_logeado.getEmail()); //para utilizar el JWT se necesita el id para poder mandar mejor las credenciales del inicio sesion
            //entonces esa liena de codigo devuelte un token creado por jwt

           return tokenJWT;
        }else{
           return "incorrecto";
        }
    }

}
