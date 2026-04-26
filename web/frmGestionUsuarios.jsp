<%-- 
    Document   : frmGestionUsuarios
    Created on : 23/03/2022, 11:23:46 AM
    Author     : Desarrollo Alsasa
--%>

<%@page import="java.util.ArrayList"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" session="true" import="entidades.*"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Expires" content="0">
        <meta http-equiv="Last-Modified" content="0">
        <meta http-equiv="Cache-Control" content="no-cache, mustrevalidate">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <title>APS-ALSASA USUARIOS</title>
        <link rel="icon" href="img/favicon.png">
        <link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/sweetalert2.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/jquery-ui/jquery-ui.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/fullcalendar/lib/main.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/select/css/select2.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/select/css/select2-bootstrap-5-theme.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/sidebars.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/style.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .modal-body{
                font-size: small; 
            }
            .dataTables_wrapper{
                font-size: small; 
            }
            .space{
                margin-top: 10px;
                margin-bottom: 10px;
            }
            .txtColor{
                color: #FFFFFF;
            }
            .modal-xl { 
                max-width: 90% !important; 
            }
            .not-active { 
                pointer-events: none; 
                cursor: default; 
            }
            
            
            .dropdown-submenu {
                position: relative;
              }

              .dropdown-submenu>.dropdown-menu {
                top: 0;
                left: 100%;
                margin-top: -6px;
                margin-left: -1px;
                -webkit-border-radius: 0 6px 6px 6px;
                -moz-border-radius: 0 6px 6px;
                border-radius: 0 6px 6px 6px;
              }

              .dropdown-submenu:hover>.dropdown-menu {
                display: block;
              }

              .dropdown-submenu>a:after {
                display: block;
                content: " ";
                width: 0;
                height: 0;
                border-color: transparent;
                border-style: solid;
                border-width: 5px 0 5px 5px;
                border-left-color: #ccc;
                margin-top: 5px;
                margin-right: -10px;
              }

              .dropdown-submenu:hover>a:after {
                border-left-color: #fff;
              }
              
              .dropdown-toggle:hover {
                color: #fff;
              }

              .dropdown-submenu.pull-left {
                float: none;
              }

              .dropdown-submenu.pull-left>.dropdown-menu {
                left: -100%;
                margin-left: 10px;
                -webkit-border-radius: 6px 0 6px 6px;
                -moz-border-radius: 6px 0 6px 6px;
                border-radius: 6px 0 6px 6px;
              }
        </style>
    </head>
    <body>
        <%
            HttpSession s = request.getSession();
        %>
        <div class="d-flex" id="wrapper">
            <!-- Sidebar-->
            <div class="border-end bg-dark" id="sidebar-wrapper" id="navbarSupportedContent">
                <div class="sidebar-heading border-bottom bg-dark" style="color: #FFFFFF">APS-ALSASA</div>
                <div class="list-group list-group-flush">
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmDashboard.jsp"><img src="img/dashboard.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Dashboard</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmOrden.jsp"><img src="img/order.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Ordenes de producción</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmPlanificadas.jsp"><img src="img/planning.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Planificadas</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmLiberadas.jsp"><img src="img/gears.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Liberadas</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmCerradas.jsp"><img src="img/closed.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Cerradas</b></a>
                    <ul class="navbar-nav me-auto list-group-item list-group-item-action list-group-item-dark p-3">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle fontColor" id="submenuTransferencias" role="button" data-bs-toggle="dropdown" aria-expanded="false"><img src="img/fechas.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Transferencias</b></a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item  fontColor" href="frmTransferencias.jsp">Crear transferencia</a></li>
                                <li><hr class="dropdown-divider fontColor"></li>
                                <li><a class="dropdown-item  fontColor" href="frmListarTransferencia.jsp">Listar transferencia</a></li>
                            </ul>
                        </li>
                    </ul>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmRegistroTiempos.jsp"><img src="img/empleado.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Actividades varias</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmEmision.jsp"><img src="img/invoice.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Actividades asignadas</b></a>
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmRecibo.jsp'><img src='img/receipt.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Carga a SAP</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmInformes.jsp"><img src="img/inform.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Informes</b></a>
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAutorizacionOps.jsp'><img src='img/gears.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Autorización OPS</b></a>
                    <ul class="navbar-nav me-auto list-group-item list-group-item-action list-group-item-dark p-3">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle fontColor" id="submenuTransferencias" role="button" data-bs-toggle="dropdown" aria-expanded="false"><img src="img/fechas.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Conteo de inventario</b></a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <%
                                    if (s.getAttribute("usuario")!=null){
                                        if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==7){
                                            out.print("<li><a class='dropdown-item  fontColor' href='frmAsigConteo.jsp'>Asignar articulos</a></li>");
                                            out.print("<li><hr class='sdropdown-divider fontColor'></li>");
                                        }
                                    }
                                %>
                                <li><a class="dropdown-item  fontColor" href="frmListarAsigConteo.jsp">Guardar cantidades</a></li>
                            </ul> 
                        </li>
                    </ul>
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAprobacion.jsp'><img src='img/edition.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Aprobar edición</b></a>
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmGenerarPago.jsp'><img src='img/pay.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Generar pago</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3 active" href="frmGestionUsuarios.jsp"><img src="img/users.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Gestión de usuarios</b></a>
                </div>
            </div>
            
            <!--Cabeza de pagina-->
            <div id="page-content-wrapper">
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom">
                    <div class="container-fluid">
                        <button class="btn btn-dark btn-lg" type="button" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="true" aria-label="Toggle navigation" id="sidebarToggle"><span class="navbar-toggler-icon"></span></button>
                        <%
                           if (s.getAttribute("usuario")!=null) {
                                if ((Integer)s.getAttribute("idRol")!=1){
                                    response.sendRedirect("frmDashboard.jsp");
                                }else{
                                    out.print("<div style='color:#FFF; align:right;'>"+s.getAttribute("nombreUser")+"</div>");
                                    out.println("<button id='cerrarSes' name='cerrarSes' class='btn btn-dark btn-lg' value='Salir'><img src='img/login.png' style='height: 23px; width: 23px;'/>&nbsp;Salir</button>");
                                }
                           }
                           else{
                               response.sendRedirect("CtrlLogin?login=1");
                           }
                        %> 
                    </div>
                </nav>
                <!-- Contenido de la pagina-->
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <div>
                                <br>
                                <center><h4 class="display-6">Gestion de usuarios</h4></center>
                                <br>
                                <!--BOTON DE MODAL DE CREAR USUARIO-->
                                <button type="button" id="btnUsuario" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalCrearUsuario" >Crear usuario</button>
                                <br><br>
                                <!--TABLA DE DATOS DE USUARIOS -->
                                <table id="tablaUsuarios" class="display cell-border" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>Id</th>
                                            <th>Usuario</th>
                                            <th>Contraseña</th>
                                            <th>Rol</th>
                                            <th>Estado</th>
                                            <th>Centros de costo</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTabla">
                                    </tbody>
                                </table>
                                <br>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
                    
        <!--MODAL DE CREAR USUARIO -->
        <div class="modal fade" id="modalCrearUsuario" tabindex="-1" role="dialog" aria-labelledby="modalCrearUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalCrearUsuarioLabel">Crear usuario</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarCU">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <form method="post" action="CtrlUsuario">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group">
                                            <h5 align="left">Usuario: </h5>
                                            <input type="text" id="txtUser" autocomplete="off" class="form-control" placeholder="Ingrese nombre del usuario" required="true">
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group">
                                            <h5 align="left">Contraseña:</h5>
                                            <input type="text" id="txtPass" autocomplete="off" class="form-control" placeholder="Ingrese contraseña del usuario" required="true">
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group" id="selectRolC">
                                            <h5 align="left">Rol:</h5>
                                            <select id="idRolC" class="form-control" required="true"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group" id="selectCecosC">
                                            <h5 align="left">Centros de costo:</h5>
                                            <select id="idCecosC" class="form-control" required="true" multiple="multiple"></select>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" id="btnCrearUsuario">Crear usuario</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarCUsuario">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        <!--FIN MODAL DE CREAR USUARIO -->
        
        <!--MODAL DE EDITAR USUARIO -->
        <div class="modal fade" id="modalEditarUsuario" tabindex="-1" role="dialog" aria-labelledby="modalEditarUsuarioLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalEditarUsuarioLabel">Editar usuario</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarEU">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <form method="post" action="CtrlUsuario">
                                <input type="hidden" id="txtIdUsuario">
                                <input type="hidden" id="txtIdRol">
                                <input type="hidden" id="txtCecosE">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                        <div class="form-group">
                                            <h5 align="left">Usuario: </h5>
                                            <input type="text" id="txtUserE" autocomplete="off" placeholder="Ingrese nombre del usuario" class="form-control" required="true">
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                        <div class="form-group">
                                            <h5 align="left">Contraseña:</h5>
                                            <input type="text" id="txtPassE" autocomplete="off" placeholder="Ingrese contraseña del usuario" class="form-control" required="true">
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                        <div class="form-group" id="selectEstado">
                                            <h5 align="left">Estado:</h5>
                                            <select id="estado" class="form-control" required="true">
                                                <option value="1">Activo</option>
                                                <option value="0">Inactivo</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group" id="selectRolE">
                                            <h5 align="left">Rol:</h5>
                                            <select id="idRolE" class="form-control" required="true"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group" id="selectCecosE">
                                            <h5 align="left">Centros de costo:</h5>
                                            <select id="idCecosE" class="form-control" required="true" multiple="multiple"></select>
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" id="btnEditarUsuario">Editar usuario</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarEUsuario">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        <!--FIN MODAL DE EDITAR USUARIO -->
                    
        <script src="plugins/js/jquery-3.6.0.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/datatables.min.js" type="text/javascript"></script>
        <script src="plugins/jquery-ui/jquery-ui.min.js" type="text/javascript"></script>
        <script src="plugins/js/sweetalert2.js" type="text/javascript"></script>
        <script src="plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
        <script src="plugins/fullcalendar/lib/main.min.js" type="text/javascript"></script>
        <script src="plugins/js/popper.min.js" type="text/javascript"></script>
        <script src="plugins/fullcalendar/lib/locales-all.min.js" type="text/javascript"></script>
        <script src="plugins/js/sidebars.js" type="text/javascript"></script>
        <script src="plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/select2.full.min.js" type="text/javascript"></script>
        <script src="plugins/js/tooltip.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/select2.full.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/i18n/es.js" type="text/javascript"></script>
        <script src="plugins/js/jsGestionUsuarios.js" type="text/javascript"></script>
        <script type="text/javascript"> 
            $(document).ready(function(){                
                $("#cerrarSes").click(function(){
                    Swal.fire({
                        icon:'info',
                        type:'info',
                        title:'Cerrar sesión',
                        text:'¿Seguro que desea cerrar sesión?',
                        showCancelButton:true,
                        cancelButtonColor:'red',
                        cancelButtonText:'Cancelar',                        
                    }).then(result=>{
                       if (result.value) {
                           location.replace("CtrlLogin?logout=1");    
                        } 
                    });                    
                });
            });
        </script>
    </body>
</html>
