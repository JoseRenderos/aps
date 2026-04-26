<%-- 
    Document   : frmLogin
    Created on : 10/03/2022, 07:37:15 AM
    Author     : Desarrollo Alsasa
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8" import="java.util.*" import="entidades.*" session="true"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Expires" content="0">
        <meta http-equiv="Last-Modified" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
        <title>APS-Login</title>
        <link rel="icon" href="img/favicon.png">
        <link href="plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/sweetalert2.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/style.css" rel="stylesheet" type="text/css"/>
        <style>
            .fc-title{
                font-size: .9em;
            }
        </style>
    </head>
    <body class="bg-dark body">
    <center><h2 class="display-4 tittleLogin" ><b>ALSASA</b></h2></center>
    <center><h5 style="color: #FFFFFF;"><b>Alsasa Production System</b></h5></center>
    <br>
        <div align="center" class="row justify-content-center">
            <div class="col-xs-12 col-sm-12 col-md-10 col-lg-7 ">
                <div class="container containerLogin">
                    <div class="col-md-11" style="padding-top: 30px;">
                        <form action="CtrlLogin" method="post"> 
<!--                            <div class="form-group">
                                <h4 align="left">Servidor:</h4>
                                <input type="text" class="form-control" placeholder="" name="txtServidor" disabled="true">
                            </div>
                            <br>-->
                            <div class="form-group">
                                <h4 align="left">Usuario:</h4>
                                <input type="text" class="form-control" placeholder="Ingrese su usuario" required name="txtUsuario">
                            </div>
                            <br>
                            <div class="form-group">
                                <h4 align="left">Contraseña:</h4>
                                <input type="password" class="form-control" placeholder="Ingrese su contraseña" required name="txtContra" onkeypress="return simbolos(event)">
                            </div>
                            <br>
                            <div class="d-grid gap-2">
                                <button class="btn btn-success btn-lg" value="Ingresar" name="btnIngresar" id="btnIngresar"><img src="img/login.png" style="height: 25px; width: 25px;"/>&nbsp;&nbsp;<b>Ingresar</b></button>
                            </div>						
                        </form>                       
                    </div>
                </div>	
            </div>
        </div>
        <script src="plugins/js/jquery-3.6.0.min.js" type="text/javascript"></script>
        <script src="plugins/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="plugins/js/sweetalert2.js" type="text/javascript"></script>
        
        <%
            
            HttpSession s = request.getSession();  
            if (s.getAttribute("usuario")!=null){
                response.sendRedirect("frmDashboard.jsp");
            }else{
                if (request.getAttribute("datosUsuario")!=null) {
                    ArrayList<Usuario> user = new ArrayList<Usuario>();
                    user.addAll((Collection)request.getAttribute("datosUsuario"));
                    for(Usuario arr:user){                       
                        if (arr.getIdUsuario()!=0) {                            
                            s.setAttribute("usuario", arr);                            
                            s.setAttribute("idUsuario", arr.getIdUsuario());
                            s.setAttribute("nombreUser", arr.getUsuario());
                            s.setAttribute("idRol", arr.getRol().getIdRol());
                            s.setAttribute("Rol", arr.getRol().getRol());
                            if (arr.getIdUsuario()==21) {
                                    response.sendRedirect("frmOrden.jsp");
                            } else {
                                response.sendRedirect("frmDashboard.jsp");
                            }
                            
                        }
                    }
                }
            }      
       %>
       <c:if test="${errorDU!=null}">
           <script type="text/javascript">
                    $(document).ready(function(){
                        Swal.fire({
                                icon: 'error',
                                title: '¡Error!',
                                text: '${errorDU}',                          
                                confirmButtonColor: '#3085d6',                                
                                confirmButtonText: 'Entendido. Volver a intentar.'
                              });
                    });
            </script>     
       </c:if>
       <c:if test="${errorC!=null}">
           <script type="text/javascript">
                    $(document).ready(function(){
                        Swal.fire({
                                icon: 'error',
                                title: '¡Error!',
                                text: '${errorC}',                             
                                confirmButtonColor: '#3085d6',                                
                                confirmButtonText: 'Entendido. Volver a intentar.'
                              });
                    });
            </script>  
       </c:if>
       <c:if test="${errorE!=null}">
           <script type="text/javascript">
                    $(document).ready(function(){
                        Swal.fire({
                                icon: 'error',
                                title: '¡Error!',
                                text: '${errorE}',                             
                                confirmButtonColor: '#3085d6',                                
                                confirmButtonText: 'Entendido. Volver a intentar.'
                              });
                    });
            </script>  
       </c:if>
       <c:if test="${logout!=null}">
           <!--<script type="text/javascript">
                    $(document).ready(function(){
                        Swal.fire({
                                icon: 'success',
                                title: '¡Éxito!',
                                text: '${logout}',
                                timer: 1000,
                                showConfirmButton: false
                        });
                    });
            </script>  -->
       </c:if>
       <c:if test="${logoutF!=null}">
           <script type="text/javascript">
                    $(document).ready(function(){
                        Swal.fire({
                                icon: 'error',
                                title: '¡Error!',
                                text: '${logoutF}',                            
                                confirmButtonColor: '#3085d6',                                
                                confirmButtonText: 'Entendido.'
                              });
                    });
            </script>  
       </c:if>
       <c:if test="${login!=null}">
           <script type="text/javascript">
                 /*   $(document).ready(function(){
                        Swal.fire({
                                icon: 'warning',
                                title: '¡Atención!',
                                text: '${login}',                              
                                confirmButtonColor: '#3085d6',                                
                                confirmButtonText: 'Entendido.'
                              });
                    });*/
            </script>  
       </c:if>
        <script>
            function simbolos(e){
                key = e.keyCode || e.which;
                tecla = String.fromCharCode(key).toLowerCase();
                if(tecla=='='  || tecla=='<' || tecla=='>'){
                    return false;
                }
            }
        </script>
    </body>
</html>
