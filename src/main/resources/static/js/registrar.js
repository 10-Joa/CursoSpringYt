// Call the dataTables jQuery plugin
$(document).ready(function() {

});

async function registrarUsuarios(){
let datosUsuario= {}; //AQUIIRAN TODOS LOS DATOS DEL FORMULARIO DEL HTML REGISTRAR
     datosUsuario.nombre = document.getElementById('exampleFirstName').value;
     datosUsuario.apellido = document.getElementById('exampleLastName').value;
     datosUsuario.email = document.getElementById('exampleInputEmail').value;
     datosUsuario.password = document.getElementById('exampleInputPassword').value;

let repetir_pass = document.getElementById('exampleRepeatPassword').value;

if(repetir_pass !== datosUsuario.password ){
alert("La contraseña es incorrecta");
return;
}
//aqui en donde dice usuarios es la url del controllers
     const request = await fetch('api/usuarios',{
     method:'POST',
     headers: {
     'Accept': 'application/json',
     'Content-Type' : 'application/json'
     },
     body: JSON.stringify(datosUsuario)
     });

     alert("La cuenta fue creada con exito");
     window.location.href = 'login.html';
     }

