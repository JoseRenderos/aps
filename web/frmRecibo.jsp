<%-- 
    Document   : frmRecibo
    Created on : 12/05/2022, 11:33:45 AM
    Author     : Desarrollo Alsasa
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Collection"%>
<%@page import="entidades.OWOR"%>
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
        <title>APS-ALSASA CARGA SAP</title>
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
            .modal-xl{ 
                max-width: 90% !important; 
            }
            
            .modal-xls{ 
                max-width: 98% !important; 
            }
            input[type=checkbox] {
                transform: scale(1.25);
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
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3 active' href='frmRecibo.jsp'><img src='img/receipt.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Carga a SAP</b></a>
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmInformes.jsp"><img src="img/inform.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Informes</b></a>
                    <!--<a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmDeptoEmp.jsp"><img src="img/empleado.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Asignar departamento</b></a>-->
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAutorizacionOps.jsp'><img src='img/gears.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Autorización OPS</b></a>
                    <ul class="navbar-nav me-auto list-group-item list-group-item-action list-group-item-dark p-3">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle fontColor" id="submenuTransferencias" role="button" data-bs-toggle="dropdown" aria-expanded="false"><img src="img/fechas.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Conteo de inventario</b></a>
                            <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
                                <%
                                    HttpSession s = request.getSession();
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
                    <%
                        int idUsuario=0;
                        int idRol=0;
                        if (s.getAttribute("usuario")!=null){
                            idUsuario=(Integer)s.getAttribute("idUsuario");
                            idRol=(Integer)s.getAttribute("idRol");
                            
                            /*if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idUsuario")==15 || (Integer)s.getAttribute("idUsuario")==23 || (Integer)s.getAttribute("idUsuario")==43 || (Integer)s.getAttribute("idUsuario")==20 || (Integer)s.getAttribute("idUsuario")==24){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAutorizacionOps.jsp'><img src='img/gears.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Autorización OPS</b></a>");
                            }*/
                            if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==7){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAprobacion.jsp'><img src='img/edition.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Aprobar edición</b></a>");
                            }
                            if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==3 || (Integer)s.getAttribute("idRol")==7){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmGenerarPago.jsp'><img src='img/pay.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Generar pago</b></a>");
                            }
                            if ((Integer)s.getAttribute("idRol")==1){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmGestionUsuarios.jsp'><img src='img/users.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Gestión de usuarios</b></a>");
                            }
                        } 
                    %> 
                </div>
            </div>
            
            <!--Cabeza de pagina-->
            <div id="page-content-wrapper">
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark border-bottom">
                    <div class="container-fluid">
                        <button class="btn btn-dark btn-lg" type="button" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="true" aria-label="Toggle navigation" id="sidebarToggle"><span class="navbar-toggler-icon"></span></button>
                       <%
                           if (s.getAttribute("usuario")!=null) {
                                out.print("<div style='color:#FFF; align:right;'>"+s.getAttribute("nombreUser")+"</div>");
                                out.println("<button id='cerrarSes' name='cerrarSes' class='btn btn-dark btn-lg' value='Salir'><img src='img/login.png' style='height: 23px; width: 23px;'/>&nbsp;Salir</button>");
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
                    <input type="hidden" id="txtIdRol" value="<%=idRol%>">
                    <input type="hidden" id="txtIdUsuario" value="<%=idUsuario%>">
                        <div class="col-12">
                            <div align='center'>
                                <br>
                                <center><h4 class="display-6">Carga a SAP</h4></center>
                                <br>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                            <div class="form-group row">
                                <div class="col-xs-3 col-sm-6 col" style="margin-top: 8px;">
                                    <input type="text" name="NumOrden" id="txtNumOrdenBuscar" class="form form-control" value="" placeholder="Ingrese el numero de orden" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" />
                                </div>
                                <div class="col-xs-2 col-sm-4 col-form-label">
                                    <button type="button" id="btnMostrarOrden" class="btn btn-primary">Ver Orden</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="divInfo">
                        <div id="divOrden">
                            <div class="row">
                                <div class="col-12">
                                    <br>
                                    <hr>
                                    <center><h4>Informacion de orden de producción</h4></center>
                                    <br>
                                </div>
                            </div>
                            <div class="row">
                                <input type="hidden" id="txtEstadoTiempos">
                                <input type="hidden" id="txtEstadoMateriales">
                                <input type="hidden" id="txtEstadoProduccion">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
                                            <div class="form-group">
                                                <h5 align="left">Numero de orden:</h5>
                                                <input type="text" id="txtNumOrden" class="form-control form-control-sm" disabled="true" width="50%">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" style="margin-top: 12px;">
                                    <div class="form-group">
                                        <button id="btnImprimir" class="btn btn-primary"><img src="img/printer.png" alt="" width="20px" height="20px"/>&nbsp; Imprimir Orden</button>
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Fecha de inicio:</h5>
                                        <input type="text" id="txtFechaInicio" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Fecha de fin:</h5>
                                        <input type="text" id="txtFechaFin" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Item code:</h5>
                                        <input type="text" id="txtItemCode" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Descripcion:</h5>
                                        <input type="text" id="txtDescrip" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Status:</h5>
                                        <input type="text" id="txtStatus" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Type:</h5>
                                        <input type="text" id="txtType" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                    <div class="form-group">
                                        <h5 align="left">Cantidad planificada:</h5>
                                        <input type="text" id="txtCantPln" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                    <div class="form-group">
                                        <h5 align="left">Cantidad completada:</h5>
                                        <input type="text" id="txtCantCmp" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                    <div class="form-group">
                                        <h5 align="left">Cantidad rechazada:</h5>
                                        <input type="text" id="txtCantRjc" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Estado materiales:</h5>
                                        <input type="text" id="txtEstadoM" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group">
                                        <h5 align="left">Estado produccion</h5>
                                        <input type="text" id="txtEstadoP" class="form-control form-control-sm" disabled="true">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <br>
                        <div class="divTablaActividades">
                            <hr>
                            <center><h4>Labores asignadas</h4></center>
                            <!-- TABLA DE ACTIVIDADES POR ORDEN DE PRODUCCION -->
                            <table id="tablaActivadades" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>EMPLEADO</th>
                                        <th>ACTIVIDAD</th>
                                        <th>DESCRIPCION ACTIVIDAD</th>
                                        <th>INSUMO</th>
                                        <th>INICIO</th>
                                        <th>FIN</th>
                                        <th>UNI CONFORMES</th>
                                        <th>UNI NO CONFORMES</th>
                                        <th>TOTAL TIEMPO</th>
                                        <th>ESTADO</th>
                                        <th>COMENTARIOS</th>
                                    </tr>
                                </thead>
                                <tbody id="contenidoTablaAct">

                                </tbody>
                            </table>
                        </div>
                        <br>
                        <div id="botonesCargaSAP" style="padding-bottom: 25px;">
                            <hr>
                            <center><h5>Carga de datos a SAP</h5></center>
                            <br>
                            <div align="center">
                                <button id="btnCargarTiempos" class="btn btn-success btn-lg">Cargar Tiempos</button>&nbsp;&nbsp;
                                <button id="btnCargarMateriales" class="btn btn-info btn-lg">Cargar Materiales</button>&nbsp;&nbsp;
                                <button id="btnCargarProduccion" class="btn btn-primary btn-lg">Cargar Producción</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalCargarTiempos" tabindex="-1" role="dialog" aria-labelledby="modalCargarTiemposLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl modal-xls" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalCargarTiemposLabel">Cargar Tiempos</h3>
                    <button type="button" class=" close" data-dismiss="modal" aria-label="Close" id="btnCerrarT">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
                                <p>
                                    <h6><b>Orden de producción: </b><spam id="numOrden"></spam></h6>
                                </p>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-7 col-lg-8">
                                <p>
                                    <h6><b>Articulo: </b><spam id="articulo"></spam></h6>
                                </p>
                            </div>
                        </div>
                        <br>
                        <table id="tablaActSap" class="display" width="100%">
                            <thead>
                               <tr>
                                   <th><input name="select_all" value="0" id="select_all" type="checkbox" /></th>
                                  <th>Actividad Prod</th>
                                  <th>Empleado</th>
                                  <th>Actividad</th>
                                  <th>Tiempo</th>
                                  <th>Uni Completadas</th>
                                  <th>Uni Rechazadas</th>
                                  <th>Comentarios</th>
                                  <th>Estado</th>
                               </tr>
                            </thead>
                            <tbody id="contenidoTablaActSap">
                                
                            </tbody>
                         </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal" id="btnCargarTiemposSap">Cargar Tiempos</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarT1">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
                    
        <div class="modal fade" id="modalCargarMateriales" tabindex="-1" role="dialog" aria-labelledby="modalCargarMaterialesLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalCargarMaterialesLabel">Cargar Materiales</h3>
                    <button type="button" class=" close" data-dismiss="modal" aria-label="Close" id="btnCerrarM">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <br>
                        <p><h6><b>Estado Materiales: </b><spam id="estadoM1"></spam></h6></p>
                        <br>
                            <table id="tablaArt" class="display" width="100%">
                                <thead>
                                   <tr>
                                      <th><input name="select_allArt" value="0" id="select_allArt" type="checkbox"/></th>
                                      <th>COD ARTICULO</th> 
                                      <th>DESCRIPCION</th> 
                                      <th>STOCK</th> 
                                      <th>BODEGA</th>
                                      <th>CANTIDAD</th>
                                   </tr>
                                </thead>
                                <tbody id="contenidoTablaArt">
                                            
                                </tbody>
                             </table>
                            <!--<div class="row" id="divMateriales">

                            </div>
                            <br>
                            <div align="right">
                                <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnGuardarCambiosM">Guardar</button>
                            </div>-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal" id="btnCargarMaterialesSap" form="formMateriales">Cargar Materiales</button>
                    <!--<button type="button" class="btn btn-warning" data-dismiss="modal" id="btnEditarM">Editar</button>--> 
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarM1">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
                    
        <div class="modal fade" id="modalCargarProduccion" tabindex="-1" role="dialog" aria-labelledby="modalCargarProduccionLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalCargarProduccionLabel">Cargar Producción</h3>
                    <button type="button" class=" close" data-dismiss="modal" aria-label="Close" id="btnCerrar">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <br>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <h5 align="left">Cantidad Planificada: </h5>
                                    <input type="text" id="txtPlanificada" class="form-control form-control-sm" disabled="true">
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <p><h6><b>Unidades Completadas reportadas: </b><spam id="uniCmplt"></spam></h6></p>
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <p><h6><b>Unidades rechazadas reportadas: </b><spam id="uniRjct"></spam></h6></p>
                                </div>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <h5 align="left">Cantidad Completada: </h5>
                                    <input type="text" id="txtCompletada" class="form-control form-control-sm" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" >
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <h5 align="left">Cantidad Rechazada: </h5>
                                    <input type="text" id="txtRechazada" class="form-control form-control-sm" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" >
                                </div>
                            </div>
                        </div>
                        <p style="color: red;">*La producción no debe exceder la cantidad planificada</p>
                        <div align="right">
                            <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnGuardarCambios">Guardar</button>
                        </div>
                        
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" data-dismiss="modal" id="btnCargarProduccionSap">Cargar Producción</button>
                    <button type="button" class="btn btn-warning" data-dismiss="modal" id="btnEditar">Editar</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrar1">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        
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
        <script src="plugins/select/js/i18n/es.js" type="text/javascript"></script>
        <script src="plugins/js/tooltip.min.js" type="text/javascript"></script>
        <script src="plugins/js/jsRecibo.js" type="text/javascript"></script>
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
        <c:if test="${error!=null}">
           <script type="text/javascript">
                    $(document).ready(function(){
                        Swal.fire({
                                icon: 'error',
                                title: '¡Error!',
                                text: '${error}',
                                type: 'error',                                
                                confirmButtonColor: '#3085d6',                                
                                confirmButtonText: 'Entendido. Volver a intentar.'
                              });
                    });
            </script>     
       </c:if>
    </body>
</html>

