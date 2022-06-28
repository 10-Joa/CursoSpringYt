// Call the dataTables jQuery plugin
$(document).ready(function() {

       cargarUsuarios();
  $('#usuarios').DataTable();
});

async function cargarUsuarios(){
//aqui en donde dice usuarios es la url del controllers
     const request = await fetch('api/usuarios',{
     method:'GET',
     headers: getHeaders()
     });

     const usuarios = await request.json();
     console.log(usuarios);

     let listadoHTML ='';
     for(let usuario of usuarios){
          let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle"><i class="fas fa-trash"></i></a>';
          let telefono = usuario.telefono == null ? '-' : usuario.telefono;
          let userHtml = ' <tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'+usuario.email+'</td><td>'+telefono+'</td><td>'+botonEliminar+'</td></tr>';
          listadoHTML += userHtml;
     }
              document.querySelector('#usuarios tbody').outerHTML = listadoHTML;

     }


//lo reutilizo mejor para cada fetch
     function getHeaders(){
          return {
          'Accept': 'application/json',
          'Content-Type' : 'application/json',
          'Authorization': localStorage.token
          };
     }

     async function eliminarUsuario(id){
     if(!confirm('¿Desea eliminar el Usuario?')){  //propio del template confirm muestra un cartel con una pregunta loq hacemos es que si la respuesta es no haga return osea nada
     return;
     }
     //el getHeaders del token le mando cuando ya implemente el jwt
      const request = await fetch('api/usuarios/' + id ,{
          method:'DELETE',
          headers: getHeaders()
          });
          console.log(usuarios);

          location.reload();

     }
