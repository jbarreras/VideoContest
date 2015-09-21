/**
 * Created by JuanDa on 20/09/2015.
 */

var app = angular.module("VideoContest", [])

    .controller('ContestCtrl', ['$scope','$http', 'upload', function ($scope,$http, upload)
    {

        $scope.getFecha=function(fecha){
            var currentTime = new Date(parseInt(fecha));
            var month = currentTime.getMonth() + 1;
            if (month.toString().length==1){
                month="0"+month;
            }
            var day = currentTime.getDate();
            if (day.toString().length==1){
                day="0"+day;
            }
            var year = currentTime.getFullYear();
            var date = year + "-" +month+ "-" +day;
            return date;
        }
        $scope.service = "http://localhost:9000/contest";
        $scope.allContests={};

        $scope.ponerDatos= function(id){
            //alert("IDDDDD "+id);
            $http.get($scope.service+"/findbyid/"+id).success(function(response) {

                $("#nombre").val(response.name);
                $("#banner").val(response.banner);
                $("#url").val(response.url);
                $("#fechaInicio").val($scope.getFecha(response.startDate.toString()));
                $("#fechaFin").val($scope.getFecha(response.finishDate.toString()));
                $("#descripcion").val(response.description);
                //contest.user=JSON.parse(localStorage.getItem("usuario"));
            });
        }

        $scope.ponerDatosConcurso= function(){
            //alert("IDDDDD "+id);
            $http.get($scope.service+"/findbyid/"+4).success(function(response) {

                $("#nombre").text(response.name);
                var p=$("#descripcion")
                p.empty();
                p.append('<img  src="'+response.banner+'" style="float: right;width: 600px;height: 300px" class="img-responsive">');
                p.append(response.description);
                p.append('<br><br><label>Fecha de inicio: '+$scope.getFecha(response.startDate.toString())+'</label><br>');
                p.append('<label>Fecha de fin: '+$scope.getFecha(response.finishDate.toString())+'</label><br>');
            });
        }
        var ruta=window.location.pathname;
        var ruta = ruta.substring(ruta.lastIndexOf("/")+1,ruta.lastIndexOf("."));
        if (ruta=="ListaConcursos"){
            window.localStorage.setItem("idConcurso","");
        }else if(ruta=="VerConcursoUsuario"){
            $scope.ponerDatosConcurso();
        }

        if(window.localStorage.getItem("mensajeConcurso")!="" && window.localStorage.getItem("mensajeConcurso")!=null) {
            $("#menConcurso").text(window.localStorage.getItem("mensajeConcurso"));
            window.localStorage.setItem("mensajeConcurso","");
        }
        if(window.localStorage.getItem("idConcurso")!="" && window.localStorage.getItem("idConcurso")!=null) {
            $scope.ponerDatos(window.localStorage.getItem("idConcurso"));

        }

        $scope.FindAll = function() {
            $http.get($scope.service+"/"+JSON.parse(localStorage.getItem("usuario")).id).success(function(response) {
                $scope.allContests = response;
            });
        };

        $scope.editarConcurso = function(id) {
            window.localStorage.setItem("idConcurso",id);
            window.location="CrearConcurso.html";
        }




        $scope.grabar= function(){
            var contest=new Object();
            contest.name=$("#nombre").val();
            contest.banner=$("#banner").val();
            contest.url=$("#url").val();
            contest.startDate=$("#fechaInicio").val();
            contest.finishDate=$("#fechaFin").val();
            contest.description=$("#descripcion").val();
            contest.user=JSON.parse(localStorage.getItem("usuario"));
            if(window.localStorage.getItem("idConcurso")!="" && window.localStorage.getItem("idConcurso")!=null) {
                contest.id=window.localStorage.getItem("idConcurso");
            }
            $scope.Persist(contest);
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
                    $("#error").text("Error inesperado.")
                }else{
                    window.localStorage.setItem("mensajeConcurso","Se grabo el concurso con exito.");
                    window.location="ListaConcursos.html";
                }
                //$scope.FindAll();
            });
        };

        $scope.eliminarConcurso= function(id){
            var contest=new Object();
            contest.name="";
            contest.banner="";
            contest.url="";
            contest.startDate="";
            contest.finishDate="";
            contest.description="";
            contest.user=JSON.parse(localStorage.getItem("usuario"));
            contest.id=id;

            $scope.Delete(contest);
            return false;
        }

        $scope.Delete = function(data) {
            var editKey = "$edit";
            var hasKey = "$$hashKey";

            if (data[editKey])
                delete data[editKey];

            if (data[hasKey])
                delete data[hasKey];

            $http({
                method : "POST",
                url : $scope.service+"/eliminar",
                data : data,
                headers : {
                    'Content-Type' : 'application/json'
                }
            }).success(function(response) {
                if (response=="ok"){
                    $("#menConcurso").text("Se elimino con exito");
                    $scope.FindAll();
                }else{
                    $("#menConcurso").text("Error inesperado.")
                }

            });
        };


        $scope.uploadFile = function()
        {
            var name = $scope.name;
            var file = $scope.file;

            upload.uploadFile(file, name).then(function(res)
            {
                console.log(res);
            })
        }
    }])

    .directive('uploaderModel', ["$parse", function ($parse) {
        return {
            restrict: 'A',
            link: function (scope, iElement, iAttrs)
            {
                iElement.on("change", function(e)
                {
                    $parse(iAttrs.uploaderModel).assign(scope, iElement[0].files[0]);
                });
            }
        };
    }])

    .service('upload', ["$http", "$q", function ($http, $q)
    {
        this.uploadFile = function(file, name)
        {
            var deferred = $q.defer();
            var formData = new FormData();
            formData.append("name", name);
            formData.append("file", file);
            return $http.post("http://localhost:9000/upload", formData, {
                headers: {
                    "Content-type": undefined
                },
                transformRequest: angular.identity
            })
                .success(function(res)
                {
                    deferred.resolve(res);
                })
                .error(function(msg, code)
                {
                    deferred.reject(msg);
                })
            return deferred.promise;
        }
    }])
