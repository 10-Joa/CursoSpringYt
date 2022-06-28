package com.example.CursoSpringYt.DAO;

import com.example.CursoSpringYt.models.Usuario;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository  //hace referencia a la base de datos
@Transactional //se usa para q sepa q vamos a meter consutlas sql
public class UsuarioDaoImplement implements UsuarioDAO{

    @PersistenceContext
    private EntityManager entityManager;  //esto cree para consultara la base

    @Override
    public List<Usuario> getUsuarios() {
        //aqui haremos una consulta cpn hibernate que hace un from a la clase usuario porque esa se conecta con la base
        String query = "FROM Usuario";
        List<Usuario>  lisUser = entityManager.createQuery(query).getResultList();
        return lisUser;
        //podemos devolver el dato como arriba o de etsa forma mas rapida
         //return entityManager.createQuery(query).getResultList();

    }

    @Override
    public void deleteUser(Long id) {
        Usuario usuario= entityManager.find(Usuario.class, id); //con esto busco a el usuario en la base como me conecte con la clase a asa base solo llamo a la base
        entityManager.remove(usuario); //aqui lo eliminaria
    }

    @Override
    public void crearUsuario(Usuario usuario) {
        entityManager.merge(usuario); //con esto se actualiza mayormente los datos y se registra
    }

    @Override
    public Usuario ObtenerUsuarioPorPassword(Usuario usuario) {
        String query = "FROM Usuario WHERE email = :email"; //asi lo mando el email y la contra con esos parametros para evitar inyecciones sql de hackign
        List<Usuario> lista = entityManager.createQuery(query) //esta lista trae de la base
                .setParameter("email", usuario.getEmail())
                .getResultList();
        //si esa lista esta vacia entonces no encontro un usuario igual, asi q no puede iniciar sesion

        //verifico si la lista esta vacia entonces retorna false
        if(lista.isEmpty()){
            return null;
        }
        //ante obtenemos el password de la lista que esta en la base
        String passwordCompare = lista.get(0).getPassword();

        //como ahora lo guardamos con hash lo que haremos es comparar con un metodo de argon verify
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        //aqui comparemos la base con lo q viene del usuario
        Boolean resultado_comparacion = argon2.verify(passwordCompare,usuario.getPassword());
        if(resultado_comparacion){
            return lista.get(0);
        }
        return null;

       // return !lista.isEmpty();

    }
}
