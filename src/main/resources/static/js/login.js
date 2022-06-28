// Call the dataTables jQuery plugin
$(document).ready(function() {

});

async function IniciarSesion(){
let datosUsuario= {}; //AQUIIRAN TODOS LOS DATOS DEL FORMULARIO DEL HTML REGISTRAR
     datosUsuario.email = document.getElementById('txtEmail').value;
     datosUsuario.password = document.getElementById('txtPassword').value;

//aqui en donde dice usuarios es la url del controllers
     const request = await fetch('api/login',{
     method:'POST',
     headers: {
     'Accept': 'application/json',
     'Content-Type' : 'application/json'
     },
     body: JSON.stringify(datosUsuario)
     });

     //const respuesta = await request.json();  //como la respuesta del api es un string y no un json cambiamos la request
     const respuesta = await request.text();

     if(respuesta != 'FAIL'){ //por ahora pregunto que no haya sido un error
     //ahora lo guardamos en el localstorage
     localStorage.token= respuesta;
     localStorage.email= datosUsuario.email;
     window.location.href = 'usuarios.html';
     }else{
     alert("usuario Incorrecto");
     }

     }
