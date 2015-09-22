/**
 * Created by JuanDa on 19/09/2015.
 */
var inicioSesion='<form  onsubmit="login();"><input id="email" type="text" placeholder="Usuario" style="border-radius: 3px;border:none;width: 30%" required> '+
    '<input id="password" type="password" placeholder="Clave" style="border-radius: 3px;border:none;width: 30%" required> '+
    '<input  type="submit"  style="background-color: #FED136;color:white;border-radius: 3px;border:none" value="Entrar"><br>' +
    '<label id="errorLogin" style="color:red;display:none">Datos incorectos.</label></form>';

var menuUsuario='<button  onclick="window.location=\'ListaConcursos.html\'"class="page-scroll btn btn-sm" style="background-color:#FED136;color:white" >Mis Concursos</button> '+
    '<button onclick="logout();" type="button" class="page-scroll btn btn-sm" style="background-color:#FED136;color:white">'+
    '<span class="glyphicon glyphicon-log-out"></span>Salir</button>';


if(window.localStorage.getItem("usuario")!="" && window.localStorage.getItem("usuario")!=null){
    var div=$("#sesion");
    div.empty();
    div.append(menuUsuario);
    $("#btnRegistrar").hide();
}else{
    var div=$("#sesion");
    div.empty();
    div.append(inicioSesion);
    $("#btnRegistrar").show();
}

function logout(){
    window.localStorage.setItem("usuario","");
    window.location="index.html";
}

function login(){

    var archivoValidacion = "http://localhost:9000/user/"+$("#email").val()+"/"+$("#password").val();
    if ($("#email").val()!="" && $("#password").val()!=""){
        $.getJSON( archivoValidacion, { })
            .done(function(respuestaServer) {
                if (respuestaServer=="error"){
                    $("#errorLogin").show();
                }else{
                    localStorage.setItem("usuario",JSON.stringify(respuestaServer));
                    window.location.reload(true);
                }

            });
    }

}