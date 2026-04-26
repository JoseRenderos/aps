<%-- 
    Document   : frmCerradas
    Created on : 12/04/2022, 09:23:04 AM
    Author     : Desarrollo Alsasa
--%>

<%@page import="java.text.DecimalFormat"%>
<%@page import="java.util.Collection"%>
<%@page import="entidades.OWOR"%>
<%@page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <title>APS-ALSASA CERRADAS</title>
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
            .modal-lg { 
                max-width: 70% !important; 
            }
            .not-active { 
                pointer-events: none; 
                cursor: default; 
            } 
            .borderModal{
                border: 1px solid #000;
            }
            
            .fc-event {
                border-width: 3px; /* Cambia el grosor del borde según tus preferencias */
            }
            .fc-daygrid-event {
                white-space: normal !important;
                align-items: normal !important;
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
            int idUsuario=0;
            
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
                    <a class="list-group-item list-group-item-action list-group-item-dark active p-3" href="frmCerradas.jsp"><img src="img/closed.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Cerradas</b></a>
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
                    <!--<a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmDeptoEmp.jsp"><img src="img/empleado.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Asignar departamento</b></a>-->
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
                    <%
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
                        <div class="col-12">
                            <div align='center'>
                                <br>
                                <center><h4 class="display-6">Ordenes de fabricación Cerradas</h4></center>
                                <br>
                                <!--BOTON DE MODAL DE AGENDA-->
                                <button type="button" id="btnAgendaC" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalAgendaP" >Agenda</button>
                                <br>
                                <br>
                                <div align="left">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                            <div class="form-group row">
                                                <div class="col-xs-3 col-sm-5 col" style="margin-top: 8px;">
                                                    <div class="form-group" id="selectCecos">
                                                        <h6><label>Filtrar por centro de costo: </label></h6>
                                                        <select id="codeCecos" name="codeCecos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                                    </div>
                                                </div>
                                                <div class="col-xs-2 col-sm-6 col-form-label" style="margin-top: 26px;">
                                                    <button type="button" id="btnFiltrar" class="btn btn-success">Filtrar</button>
                                                    <button type="button" id="btnCargarTodos" class="btn btn-info">Cargar Todos</button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>    
                                </div>
                                <!--TABLA DE DATOS DE ORDENES -->
                                <table id="tablaOrdenes" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>Nº</th>
                                            <th>FECHADEOP</th>
                                            <th>FECHAVENCDEOP</th>
                                            <th>ITEMCODE</th>
                                            <th>ITEMNAME</th>
                                            <th>STATUS</th>
                                            <th>TIPO</th>
                                            <th>CANTPLN</th>
                                            <th>CANTCMP</th>
                                            <th>CANTRJC</th>
                                            <th>ESTADO</th>
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
        <!--MODAL DE AGENDA -->
        <div class="modal fade" id="modalAgendaC" tabindex="-1" role="dialog" aria-labelledby="modalAgendaCLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalAgendaClLabel">Agenda de ordenes de fabricación Cerradas</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarAC">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <div align="left">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 space">
                                    <div class="form-group row">
                                        <div class="col-xs-4 col-sm-6 col">
                                            <div class="form-group" id="selectCecosA">
                                                <h6><label>Filtrar por centro de costo: </label></h6>
                                                <select id="codeCecosA" name="codeCecosA" class="form-control" autocomplete="off" style="width: 100%"></select>
                                            </div>
                                        </div>
                                        <div class="col-xs-2 col-sm-6 col-form-label" style="margin-top: 20px;">
                                            <button type="button" id="btnFiltrarA" class="btn btn-success">Filtrar</button>
                                            <button type="button" id="btnCargarTodosA" class="btn btn-info">Cargar Todos</button>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6 space">
                                    <div class="form-group row">
                                        <div class="col-xs-4 col-sm-6 col">
                                            <div class="form-group" id="selectArtA">
                                                <h6><label>Filtrar por CM: </label></h6>
                                                <select id="codeArtA" name="codeArtA" class="form-control" autocomplete="off" style="width: 100%"></select>
                                            </div>
                                        </div>
                                        <div class="col-xs-2 col-sm-6 col-form-label" style="margin-top: 20px;">
                                            <button type="button" id="btnFiltrarArtA" class="btn btn-success">Filtrar</button>
                                            <button type="button" id="btnCargarTodosArtA" class="btn btn-info">Cargar Todos</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div id="calendar">

                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarAgendaC">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        <!--FIN MODAL DE AGENDA -->
        
        <!--MODAL DE ORDEN -->
        <div class="modal fade" id="modalOrden" role="dialog" aria-labelledby="modalOrdenLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalOrdenLabel">Orden de producción</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarOr">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <!--CONTENIDO DE MODAL-->
                        <div class="row">
                            <form method="post" action="Ctrlorden">
                                <input type="hidden" id="txtIdUsuario" value="<%=idUsuario%>">
                                <input type="hidden" id="txtIdRol" value="<%=idRol%>">
                                <input type="hidden" id="txtStatus">
                                <input type="hidden" id="txtContador">
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3">
                                        <div class="form-group">
                                            <h5 align="left">Numero de orden:</h5>
                                            <input type="text" id="txtNumOrden" class="form-control form-control-sm" disabled="true">
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
                                            <h5 align="left">Estado: </h5>
                                            <input type="text" id="txtEstado" class="form-control form-control-sm" disabled="true">
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                        <div class="form-group">
                                            <h5 align="left">Comentarios:</h5>
                                            <input type="text" id="txtComentarios" class="form-control form-control-sm" disabled="true">
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
                                            <h5 align="left">Cantidad completada</h5>
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
                            </form>
                            <br>
                            <div align="center"  style="margin-top: 15px;">
                                <hr>
                                <h5>Entrega de materiales</h5>
                                <!-- TABLA DE Entrega DE MATERIALES POR ORDEN DE PRODUCCION -->
                                <table id="tablaMateriales" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>ITEMCODE</th>
                                            <th>DESCRIPCION</th>
                                            <th>CANTIDAD LIBERADA</th>
                                            <th>CANTIDAD ENTREGADA</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTablaMateriales">

                                    </tbody>
                                </table>
                                <br>
                                <div align='right'>
                                    <button id="btnTraslados" class="btn btn-success">Ver traslados</button>
                                </div>
                                <div class="divTablaActividades">
                                    <hr>
                                    <h5>Labores asignadas</h5>
                                    <!-- TABLA DE ACTIVIDADES POR ORDEN DE PRODUCCION -->
                                    <table id="tablaActivadades" class="display" style="width:100%">
                                        <thead>
                                            <tr>
                                                <th>EMPLEADO</th>
                                                <th>ACTIVIDAD</th>
                                                <th>DESCRIPCION ACTIVIDAD</th>
                                                <th>INSUMOS</th>
                                                <th>INICIO</th>
                                                <th>FIN</th>
                                                <th>TOTAL UNIDADES</th>
                                                <th>ESTANDAR H</th>
                                                <th>EFICIENCIA</th>
                                            </tr>
                                        </thead>
                                        <tbody id="contenidoTablaAct">

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="modal-footer" align="left">
                    <button type="button" class="btn btn-info" id="btnIrAgenda">Ir agenda</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarOrden">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        <!--FIN MODAL DE ORDEN -->
        
        <!--MODAL DE TRASLADOS DE MATERIALES-->
        <div class="modal fade" id="modalTraslados" tabindex="-1" role="dialog" aria-labelledby="modalTrasladosLabel" aria-hidden="true" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
              <div class="modal-content borderModal">
                    <div class="modal-header">
                  <h3 class="modal-title" id="modalTrasladosLabel">Traslados de materiales</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarTraslados1">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" align="left">
                        <div class="ros">
                            <table id="tablaTraslados" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>NUM TRASLADO</th>
                                        <th>ITEMCODE</th>
                                        <th>DESCRIPCION</th>
                                        <th>CANTIDAD</th>
                                        <th>FECHA</th>
                                        <th>BODEGA</th>
                                    </tr>
                                </thead>
                                <tbody id="contenidoTablaTraslados">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarTraslados">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        <!--FIN MODAL DE TRASLADOS DE MATERIALES-->
        
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
        <script src="plugins/js/tooltip.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/select2.full.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/i18n/es.js" type="text/javascript"></script>
        <script src="plugins/js/jsCerradas.js" type="text/javascript"></script>
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