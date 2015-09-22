/**
 * Created by JuanDa on 19/09/2015.
 */
(function() {

    var ctrl = function($scope, $http) {
        $scope.service = "http://localhost:9000/user";
        $scope.divMensaje=function(mensaje){
            if (mensaje!=""){
                $("#txtMensaje").text(mensaje);
                $("#divMensaje").show('slow');
                $("#btnMensaje").focus();
            }else{
                $("#divMensaje").hide('slow');
                //$("#"+focoAnterior).focus();
            }
        }


        if(window.localStorage.getItem("mensaje")!="" && window.localStorage.getItem("mensaje")!=null) {
            $scope.divMensaje(window.localStorage.getItem("mensaje"));
            window.localStorage.setItem("mensaje","");
        }

        $scope.loginn=function(){
            if ($("#email").val()!="" && $("#password").val()!=""){
                $http.get($scope.service+"/"+$("#email").val()+"/"+$("#password").val()).success(function(response) {
                    if (response=="error"){
                        $("#errorLogin").show();
                    }else{
                        localStorage.setItem("usuario",response);
                        window.location.reload(true);
                    }
                });
            }


        }


        $scope.registrar= function(){
            if($("#clave").val()==$("#clave2").val()){
                var user=new Object();
                user.name=$("#nombres").val();
                user.lastName=$("#apellidos").val();
                user.email=$("#correo").val();
                user.password=$("#clave").val();
                $scope.Persist(user);
            }else{
                $("#error").text("Las claves deben ser iguales.")
            }


            return false;
        }

        $scope.Persist = function(data) {
            var editKey = "$edit";
            var hasKey = "$$hashKey";

            if (data[editKey])
                delete data[editKey];

            if (data[hasKey])
                delete data[hasKey];

            $http({
                method : "POST",
                url : $scope.service,
                data : data,
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).success(function(response) {
                if (response.id==undefined){
                    $("#error").text("EL correo ingresado ya existe.")
                }else{
                    localStorage.setItem("usuario",JSON.stringify(response));
                    window.localStorage.setItem("mensaje","Felicidades, ahora estas registrado y puedes crear nuevos concursos.");
                    window.location="index.html";
                }
                //$scope.FindAll();
            });
        };


    };
    var module = angular.module("VideoContest");
    module.controller("UserCtrl", ctrl);


}());
