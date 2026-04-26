<%-- 
    Document   : frmOrden
    Created on : 11/03/2022, 01:10:39 PM
    Author     : Desarrollo Alsasa
--%>

<%@page import="java.util.GregorianCalendar"%>
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
        <title>APS-ALSASA ORDENES</title>
        <link rel="icon" href="img/favicon.png">
        <link href="plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/sweetalert2.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/DataTables/DataTables-1.11.5/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/DataTables/responsive.bootstrap.min.css" rel="stylesheet" type="text/css"/>
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
                max-width: 95% !important; 
            }
            .modal-lg { 
                max-width: 85% !important; 
            }
            .not-active { 
                pointer-events: none; 
                cursor: default; 
            }
            input[type=checkbox] {
                transform: scale(1.75);
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
            
            .textbreak{
                -webkit-hyphens: auto;
                -moz-hyphens: auto;
                -ms-hyphens: auto;
                hyphens: auto;
                word-break: break-all;
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
                    <a class="list-group-item list-group-item-action list-group-item-dark active p-3" href="frmOrden.jsp"><img src="img/order.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Ordenes de producción</b></a>
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
                        int idUsuario = 0;
                        int idRol = 0;
                        if (s.getAttribute("usuario") != null) {
                            idUsuario = (Integer) s.getAttribute("idUsuario");
                            idRol = (Integer) s.getAttribute("idRol");
                            /*if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idUsuario")==15 || (Integer)s.getAttribute("idUsuario")==23 || (Integer)s.getAttribute("idUsuario")==43 || (Integer)s.getAttribute("idUsuario")==20 || (Integer)s.getAttribute("idUsuario")==24){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAutorizacionOps.jsp'><img src='img/gears.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Autorización OPS</b></a>");
                            }*/
                            if ((Integer) s.getAttribute("idRol") == 1 || (Integer) s.getAttribute("idRol") == 7) {
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmAprobacion.jsp'><img src='img/edition.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Aprobar edición</b></a>");
                            }
                            if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==3 || (Integer)s.getAttribute("idRol")==7){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmGenerarPago.jsp'><img src='img/pay.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Generar pago</b></a>");
                            }
                            if ((Integer) s.getAttribute("idRol") == 1) {
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
                        <button class="btn btn-dark btn-lg" type="button" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation" id="sidebarToggle"><span class="navbar-toggler-icon"></span></button>
                            <%
                                if (s.getAttribute("usuario") != null) {
                                    out.print("<div style='color:#FFF; align:right;'>" + s.getAttribute("nombreUser") + "</div>");
                                    out.println("<button id='cerrarSes' name='cerrarSes' class='btn btn-dark btn-lg' value='Salir'><img src='img/login.png' style='height: 23px; width: 23px;'/>&nbsp;Salir</button>");
                                } else {
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
                                <center><h4 class="display-6">Ordenes de fabricación</h4></center>
                                <br>
                                <!--BOTON DE MODAL DE AGENDA-->
                                <button type="button" id="btnAgenda" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#modalAgenda" >Agenda</button>
                               
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
                                                    <!-- JR <button type="button" id="btnFiltrar" class="btn btn-success">Filtrar</button> --->
                                                    <!-- JR                             <button type="button" id="btnCargarTodos" class="btn btn-info">Cargar Todos</button> -->
                                                </div>
                                            </div>
                                        </div>
                                    </div>    
                                </div>
                                <!--BOTON CARGAR ORDENES -->
                                <div class="col-xs-2 col-sm-6 col-form-label" style="margin-top: 26px;">
    <button type="button" id="btnFiltrar" class="btn btn-success">Filtrar</button>
    <button type="button" id="btnCargarTodos" class="btn btn-info">Cargar Todos</button>
    
    <button type="button" class="btn btn-warning" onclick="abrirSelectorExcel()">
        <i class="fa fa-file-excel-o"></i> Cargar Ordenes
    </button>
</div>

<form id="formExcel" action="CtrlOrden" method="POST" enctype="multipart/form-data" style="display:none;">
    <input type="file" id="fileExcel" name="fileExcel" accept=".xlsx, .xls" onchange="enviarExcel()">
    <input type="hidden" name="cargarExcelBoton" value="true">
</form>

<script>
function abrirSelectorExcel() {
    document.getElementById('fileExcel').click();
}

function enviarExcel() {
    if (document.getElementById('fileExcel').value !== "") {
        Swal.fire({
            title: '¿Confirmar carga?',
            text: "Se procesará el archivo de órdenes de producción seleccionado",
            icon: 'question',
            showCancelButton: true,
            confirmButtonText: 'Sí, subir',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.value) {
                document.getElementById('formExcel').submit();
            } else {
                document.getElementById('fileExcel').value = ""; // Limpiar
            }
        });
    }
}
</script>
                              </script>
                                <!--TABLA DE DATOS DE ORDENES -->
                                <table id="tablaOrdenes" class="display hover table-responsive table-condensed" style="width:100%">
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
        <div class="modal fade" id="modalAgenda" tabindex="-1" role="dialog" aria-labelledby="modalAgendaLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalAgendalLabel">Agenda de ordenes de producción</h3>
                        <button type="button" class=" close" data-dismiss="modal" aria-label="Close" id="btnCerrarA">
                            <span aria-hidden="false">&times;</span>
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
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarAgenda">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE AGENDA -->


        <!--MODAL DE ORDEN -->
        <div class="modal fade" id="modalOrden" role="dialog" aria-labelledby="modalOrdenLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalOrdenLabel">Orden de producción</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarOr">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <!--CONTENIDO DE MODAL-->
                            <div class="row" >
                                <form method="post" action="Ctrlorden">
                                    <input type="hidden" id="txtIdUsuario" value="<%=idUsuario%>">
                                    <input type="hidden" id="txtIdRol" value="<%=idRol%>">
                                    <input type="hidden" id="txtStatus">
                                    <input type="hidden" id="txtContador">
                                    <input type="hidden" id="txtId">
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-3">
                                            <div class="form-group">
                                                <h5 align="left">Numero de orden:</h5>
                                                <input type="text" id="txtNumOrden" class="form-control form-control-sm" disabled="true">
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-4">
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                                    <p>
                                                        <h6><b>COSTO PPTO: </b><spam id="costoPln"></spam></h6>
                                                    </p>
                                                </div>
                                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                                    <p>
                                                        <h6><b>COSTO REAL: </b><spam id="cantReal"></spam></h6>
                                                    </p>
                                                </div>
                                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                                    <p>
                                                        <h6><b>VARIACIÓN: </b><spam id="variacion"></spam></h6>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5" id="divAprobacion" hidden>
                                            <div class="d-flex" style="margin-top: 14px;">
                                                <h5 align="left" style="margin-top: 8px;">Solicitar edición:&nbsp;&nbsp;&nbsp;</h5><button type="button" id="btnsolicitarEdicion" class="btn btn-primary mx-1">Solicitar</button>
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
                                        <table id="tablaActividades" class="display" style="width:100%">
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
                            <br>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                    <div class="form-group" align="left">
                                        <button id="btnImprimir" class="btn btn-primary"><img src="img/printer.png" alt="" width="20px" height="20px"/>&nbsp; Imprimir Orden</button>
                                        <button id="btnEstadoCierre" class="btn btn-success">Lista para cierre</button>
                                        <button id="btnModiOrden" class="btn btn-success">Modificar Orden</button>
                                        <!--<h5 align="left"><label>Listo para cierre de OP: </label><input name="estadoCierre" value="0" id="estadoCierre" type="checkbox" /></h5>-->
                                    </div>
                                </div>
                            </div>
                            
                            <!-- Inicio form -->
                            <div class="row">
                                <form id="frmAsignar" name="frmAsignar" method="POST">
                                    <div id="Registro">  
                                        <hr style="margin-top: 30px;">
                                        <center><h4>Asignación de labor</h4></center>
                                        <div id="divRegistroHoras" style="margin-top: 20px;">
                                            <section id="1" class="">
                                                <div class="row" style="margin-top: 10px;">
                                                    <div class="col-12">
                                                        <div align="right">
                                                            <button type="button" class="btn btn-info" id="btnAsignar1">Asignar</button>
                                                            <button type="button" class="btn btn-warning" id="btnInsumosAsig1" hidden="true">ver Insumos</button>
                                                            <button type="button" class="btn btn-danger btn-sm" id="btnEliminarAsignacion1" hidden="true">
                                                                <span>
                                                                    <img src="img/menos.png" style="width: 30px; height: 30px;"/>
                                                                </span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <input type="hidden" id="txtNumCaptura1">
                                                    <input type="hidden" id="txtNumAsignacion1" value="1">
                                                    <input type="hidden" id="txtComprobarUniTotales1">
                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                        <div class="form-group" id="selectEmpleados1">
                                                            <h5><label>Empleado:</label></h5>
                                                            <select id="codeEmpleados1" name="codeEmpleados1" class="form-control 1" autocomplete="off" style="width: 100%"></select>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                                                        <div class="form-group" id="selectActividades1">
                                                            <h5 align="left"><label>Actividad:</label></h5>
                                                            <select id="codeActividad1" name="codeActividad1" class="form-control 1" autocomplete="off" style="width: 100%"></select>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                                                        <div class="form-group">
                                                            <h5 align="left"><label>Estandar por hora: </label></h5>
                                                            <input type="text" id="txtEstandarH1" class="form-control 1" name="txtEstandarH[]" autocomplete="off" disabled>
                                                        </div>
                                                    </div>
                                                </div> 
                                                <div class="row" style="margin-top: 15px;">
                                                    <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
                                                        <div class="form-group" id="selectInsumos1">
                                                            <h5 align="left"><label>Insumo:</label></h5>
                                                            <select id="insumos1" class="form-control 1" autocomplete="off" style="width: 100%" multiple="multiple"></select>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                                                        <div class="form-group" style="padding-top: 10px; padding-left: 5px;">
                                                            &nbsp;<h5><input name="select_all1" value="0" id="select_all1" type="checkbox" />&nbsp;&nbsp;Seleccionar todos</h5>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row" align="center">
                                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
                                                        <fieldset class="border rounded-3 p-3">
                                                            <legend class="float-none w-auto px-3">Control de tiempo</legend>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 space">
                                                                    <div class="form-group row">
                                                                        <label for="txtTiempo1" class="col-xs-12 col-sm-2 col-form-label"><h5>Tiempo</h5></label>
                                                                        <div class="col-xs-12 col-sm-10">
                                                                            <input type="text" class="form-control 1" id="txtTiempo1" name="txtTiempo1" placeholder="hh:mm" disabled="true">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-7">
                                                                    <div class="row btnTiempos1">
                                                                        <div class="col-xs-12 col-sm-4 space">
                                                                            <button type="button" class="btn btn-primary btn-lg 1" id="btnIniciarTiempo1" name="btnIniciarTiempo">Iniciar</button>
                                                                        </div>
                                                                        <div class="col-xs-12 col-sm-4 space" id="capturaTiempo">
                                                                            <button type="button" class="btn btn-warning btn-lg 1" id="btnCapturaTiempo1" name="btnCapturaTiempo">Captura 1</button>
                                                                            <button type="button" class="btn btn-warning btn-lg 1" id="btnReiniciarTiempo1" name="btnReiniciarTiempo">Reiniciar</button>
                                                                        </div>
                                                                        <div class="col-xs-12 col-sm-4 space">
                                                                            <button type="button" class="btn btn-danger btn-lg 1" id="btnFinTiempo1" name="btnFinTiempo">Finalizar</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row" id="divRegistroUnidades1">
                                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
                                                        <fieldset class="border rounded-3 p-3">
                                                            <legend class="float-none w-auto px-3">Registro de unidades</legend>
                                                            <h5><p><b>Unidades guardadas: </b><spam id="uniGuardadas1"></spam></p></h5>
                                                            <br>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades conformes:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniConformes1"  name="txtUniConformes[]" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required>                                                    
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades no conformes:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniNoConformes1" name="txtUniNoConformes[]" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required value="0"> 
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades Rechazadas:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniRechazadas1"  name="txtUniRechazadas[]" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required value="0"> 
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades Totales:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniTotales1"  name="txtUniTotales[]" autocomplete="off" disabled="true" required=""> 
                                                                </div>
                                                            </div>
                                                            <br>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                                    <h5><label>Comentario:</label></h5>
                                                                    <input type="text" class="form-control 1" autocomplete="off" id="txtComentario1"> 
                                                                </div>
                                                            </div>
                                                            <br>
                                                            <div class="row align-items-center">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                                    <button type="button" class="btn btn-success btn-lg" id="btnGuardarUnidades1">Guardar Unidades</button>
                                                                </div>
                                                            </div>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                            </section>
                                        </div>
                                        <div id="asignacionExtra">

                                        </div>

                                        <div class="row" style="margin-top: 15px; margin-bottom: 10px">
                                            <div align="right">
                                                <div class="col-12"> 
                                                    <button type="button" class="btn btn-success" id="btnAgregarAsignacion">
                                                        <span>
                                                            <img src="img/plus.png" style="width: 30px; height: 30px;"/>
                                                        </span>
                                                    </button>
                                                </div>
                                            </div>
                                        </div>                     
                                    </div>
                                </form>
                                <!--Fin Form-->

                            </div>
                            <!-- Inicio form -->
                            <div class="row">
                                <form id="frmAsignarGrupo" name="frmAsignarGrupo" method="POST">
                                    <div id="RegistroGrupo">  
                                        <hr style="margin-top: 30px;">
                                        <center><h4>Asignación de labor</h4></center>
                                        <div id="divRegistroHorasGrupo" style="margin-top: 20px;">
                                            <section id="Grupo1" class="">
                                                <div class="row" style="margin-top: 10px;">
                                                    <div class="col-12">
                                                        <div align="right">
                                                            <button type="button" class="btn btn-info" id="btnAsignarGrupo">Asignar</button>
                                                            <!--                                                        <button type="button" class="btn btn-warning" id="btnInsumosAsigGrupo" hidden="true">ver Insumos</button>-->
                                                            <button type="button" class="btn btn-danger btn-sm" id="btnEliminarAsignacionGrupo" hidden="true">
                                                                <span>
                                                                    <img src="img/menos.png" style="width: 30px; height: 30px;"/>
                                                                </span>
                                                            </button>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row">
                                                    <input type="hidden" id="txtNumCapturaGrupo">
                                                    <input type="hidden" id="txtNumAsignacionGrupo" value="1">
                                                    <input type="hidden" id="txtComprobarUniTotalesGrupo">
                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                                        <div class="form-group" id="selectEmpleadosGrupo">
                                                            <h5><label>Empleado:</label></h5>
                                                            <select id="codeEmpleadosGrupo" name="codeEmpleadosGrupo" class="form-control 1" autocomplete="off" style="width: 100%" multiple="multiple"></select>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                                                        <div class="form-group" id="selectActividadesGrupo">
                                                            <h5 align="left"><label>Actividad:</label></h5>
                                                            <select id="codeActividadGrupo" name="codeActividadGrupo" class="form-control 1" autocomplete="off" style="width: 100%"></select>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                                                        <div class="form-group">
                                                            <h5 align="left"><label>Estandar por hora: </label></h5>
                                                            <input type="text" id="txtEstandarHGrupo" class="form-control 1" name="txtEstandarHGrupo" autocomplete="off" disabled>
                                                        </div>
                                                    </div>
                                                </div> 
                                                <div class="row" style="margin-top: 15px;">
                                                    <div class="col-xs-12 col-sm-12 col-md-9 col-lg-9">
                                                        <div class="form-group" id="selectInsumosGrupo">
                                                            <h5 align="left"><label>Insumo:</label></h5>
                                                            <select id="insumosGrupo" class="form-control 1" autocomplete="off" style="width: 100%" multiple="multiple"></select>
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-3 col-lg-3">
                                                        <div class="form-group" style="padding-top: 10px; padding-left: 5px;">
                                                            &nbsp;<h5><input name="select_allGrupo" value="0" id="select_allGrupo" type="checkbox" />&nbsp;&nbsp;Seleccionar todos</h5>
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row" align="center">
                                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
                                                        <fieldset class="border rounded-3 p-3">
                                                            <legend class="float-none w-auto px-3">Control de tiempo</legend>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-5 space">
                                                                    <div class="form-group row">
                                                                        <label for="txtTiempoGrupo" class="col-xs-12 col-sm-2 col-form-label"><h5>Tiempo</h5></label>
                                                                        <div class="col-xs-12 col-sm-10">
                                                                            <input type="text" class="form-control 1" id="txtTiempoGrupo" name="txtTiempoGrupo" placeholder="hh:mm" disabled="true">
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-7">
                                                                    <div class="row btnTiemposGrupo">
                                                                        <div class="col-xs-12 col-sm-4 space">
                                                                            <button type="button" class="btn btn-primary btn-lg 1" id="btnIniciarTiempoGrupo" name="btnIniciarTiempoGrupo">Iniciar</button>
                                                                        </div>
                                                                        <div class="col-xs-12 col-sm-4 space" id="capturaTiempoGrupo">
                                                                            <button type="button" class="btn btn-warning btn-lg 1" id="btnCapturaTiempoGrupo" name="btnCapturaTiempoGrupo">Captura 1</button>
                                                                            <button type="button" class="btn btn-warning btn-lg 1" id="btnReiniciarTiempoGrupo" name="btnReiniciarTiempoGrupo">Reiniciar</button>
                                                                        </div>
                                                                        <div class="col-xs-12 col-sm-4 space">
                                                                            <button type="button" class="btn btn-danger btn-lg 1" id="btnFinTiempoGrupo" name="btnFinTiempoGrupo">Finalizar</button>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row" id="divRegistroUnidadesGrupo">
                                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
                                                        <fieldset class="border rounded-3 p-3">
                                                            <legend class="float-none w-auto px-3">Registro de unidades</legend>
                                                            <h5><p><b>Unidades guardadas: </b><spam id="uniGuardadasGrupo"></spam></p></h5>
                                                            <br>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades conformes:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniConformesGrupo"  name="txtUniConformesGrupo" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades no conformes:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniNoConformesGrupo" name="txtUniNoConformesGrupo" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required> 
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades Rechazadas:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniRechazadasGrupo"  name="txtUniRechazadasGrupo" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required> 
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades Totales:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniTotalesGrupo"  name="txtUniTotalesGrupo" autocomplete="off" disabled="true" required=""> 
                                                                </div>
                                                            </div>
                                                            <br>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                                    <h5><label>Comentario:</label></h5>
                                                                    <input type="text" class="form-control 1" autocomplete="off" id="txtComentarioGrupo"> 
                                                                </div>
                                                            </div>
                                                            <br>
                                                            <div class="row align-items-center">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                                    <button type="button" class="btn btn-success btn-lg" id="btnGuardarUnidadesGrupo">Guardar Unidades</button>
                                                                </div>
                                                            </div>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                            </section>
                                        </div>
                                    </div>
                                </form>
                                <!--Fin Form-->
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <form id="frmEdicion" name="frmAsignarGrupo" method="POST">
                                        <div id="edicionActividades">  
                                            <hr style="margin-top: 30px;">
                                            <center><h4>Edición de actividades</h4></center>
                                            <div id="divEdicionActividades" style="margin-top: 20px;">
                                                <div class="form-group row">
                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" style="margin-top: 8px;">
                                                        <div class="form-group">
                                                            <h5><label>Inicio:</label></h5>
                                                            <input type="datetime-local" name="input" id="txtInicioEdicion" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                                        </div>
                                                    </div>
                                                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" style="margin-top: 8px;">
                                                        <div class="form-group" >
                                                            <h5><label>Fin:</label></h5>
                                                            <input type="datetime-local" name="input" id="txtFinEdicion" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                                        </div>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row" id="divRegistroUnidadeEdicion">
                                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12"> 
                                                        <fieldset class="border rounded-3 p-3">
                                                            <legend class="float-none w-auto px-3">Registro de unidades</legend>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades conformes:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniConformesEdicion"  name="txtUniConformesEdicion" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades no conformes:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniNoConformesEdicion" name="txtUniNoConformesEdicion" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required value="0"> 
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades Rechazadas:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniRechazadasEdicion"  name="txtUniRechazadasEdicion" autocomplete="off" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" required value="0"> 
                                                                </div>
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-3">
                                                                    <h5><label>Unidades Totales:</label></h5>
                                                                    <input type="text" class="form-control 1" id="txtUniTotalesEdicion"  name="txtUniTotalesEdicion" autocomplete="off" disabled="true" required=""> 
                                                                </div>
                                                            </div>
                                                            <br>
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                                    <h5><label>Comentario:</label></h5>
                                                                    <input type="text" class="form-control 1" autocomplete="off" id="txtComentarioEdicion"> 
                                                                </div>
                                                            </div>
                                                        </fieldset>
                                                    </div>
                                                </div>
                                                <br>
                                                <div class="row align-items-center">
                                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                        <button type="button" class="btn btn-success btn-lg" id="btnGuardarEdicion">Guardar</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer" align="left">
                        <button type="button" class="btn btn-success" id="btnTerminado">Terminación</button>
                        <button type="button" class="btn btn-info" id="btnIrAgenda">Ir agenda</button>
                        <button type="button" class="btn btn-primary" id="btnEdicionActividades" hidden>Editar actividades</button>
                        <button type="button" class="btn btn-primary" id="btnAsignarHorasGrupo">Asignar Grupo</button>
                        <button type="button" class="btn btn-success" id="btnAsignarHoras">Asignar Tiempo</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarOrden">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE ORDEN -->

        <!--MODAL DE ASIGNAR CANTIDADES -->
        <div class="modal fade" id="modalAsignarCant1" tabindex="-1" role="dialog" aria-labelledby="modalAsignarCantLabel1" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalAsignarCantLabel1">Asignar Cantidades</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarAsignarCant11">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <div class="ros">
                                <table id="tablaMateriales11" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>ITEMCODE</th>
                                            <th>DESCRIPCION</th>
                                            <th>CANTIDAD LIBERADA</th>
                                            <th>CANTIDAD ENTREGADA</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTablaMateriales11">

                                    </tbody>
                                </table>
                            </div>
                            <hr>
                            <br>
                            <form action="" method="POST" id="formAsigCant1">
                                <div class="row" id="divAsignarCant1">

                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success" id="btnAsignarCant" form="formAsigCant1">Guardar</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarAsignarCant1">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE ASIGNAR CANTIDADES -->

        <!--MODAL DE ASIGNAR CANTIDADES GRUPO-->
        <div class="modal fade" id="modalAsignarCantGrupo" tabindex="-1" role="dialog" aria-labelledby="modalAsignarCantGrupoLabel1" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalAsignarCantGrupoLabel1">Asignar Cantidades</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarAsignarCant1Grupo">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <div class="ros">
                                <table id="tablaMateriales1Grupo" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>ITEMCODE</th>
                                            <th>DESCRIPCION</th>
                                            <th>CANTIDAD LIBERADA</th>
                                            <th>CANTIDAD ENTREGADA</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTablaMateriales1Grupo">

                                    </tbody>
                                </table>
                            </div>
                            <hr>
                            <br>
                            <form action="" method="POST" id="formAsigCantGrupo">
                                <div class="row" id="divAsignarCantGrupo">

                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-success" id="btnAsignarCantGrupo" form="formAsigCantGrupo">Guardar</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarAsignarCantGrupo">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE ASIGNAR CANTIDADES -->

        <!--MODAL DE INSUMOS ASIGNADOS -->
        <div class="modal fade" id="modalInsumosAsig" tabindex="-1" role="dialog" aria-labelledby="modalInsumosAsigLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalInsumosAsigLabel">Insumos Asignados</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCantConsumida1">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <div class="ros">
                                <table id="tablaInsumosAsig" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>ITEMCODE</th>
                                            <th>DESCRIPCION</th>
                                            <th>CANTIDAD ASIGNADA</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTablaInsumosAsig">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarInsumosAsig">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE INSUMOS ASIGNADOS -->

        <!--MODAL DE TRASLADOS DE MATERIALES-->
        <div class="modal fade" id="modalTraslados" tabindex="-1" role="dialog" aria-labelledby="modalTrasladosLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalTrasladosLabel">Traslados de materiales</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarTraslados1">
                            <span aria-hidden="false">&times;</span>
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

        <!--MODAL DE SOLICITUD DE EDICION-->
        <div class="modal fade" id="modalSolicitarEdicion" tabindex="-1" role="dialog" aria-labelledby="modalSolicitarEdicionLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalSolicitarEdicionLabel">SOLICITAR EDICIÓN</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarSolicitud1">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <div align="left"><h5><label>COMENTARIO: </label></h5></div>
                                    <div class="form-group mb-3" id="comentario1">
                                        <textarea id="txtComentarioSolicitud" class="form-control" rows="3"></textarea>
                                    </div>
                                    <p style="color: red;">*El comentario debe tener minimo de 80 caracteres</p>
                                    <div>
                                        <button type="button" class="btn btn-primary" id="btGuardarSolicitud">Solicitar</button>
                                    </div>
                                </div>
                            </div>
                        </div>                    
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarSolicitud">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE SOLICITUD DE EDICION-->

        <!--MODAL DE ESTADO DE CIERRE-->
        <div class="modal fade" id="modalEstadoCierre" tabindex="-1" role="dialog" aria-labelledby="modalEstadoCierreLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalEstadoCierreLabel">RESUMEN ESTADO DE CIERRE</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarEstadoCierre1">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <center><h5>COSTO GENERAL OP</h5></center>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                            <p>
                                                <h6><b>COSTO PPTO: </b><spam id="costoPlnC"></spam></h6>
                                            </p>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                            <p>
                                                <h6><b>COSTO REAL: </b><spam id="cantRealC"></spam></h6>
                                            </p>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                            <p>
                                                <h6><b>VARIACIÓN: </b><spam id="variacionC"></spam></h6>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                    <center><h5>COSTO POR LINEA</h5></center>
                                    <table id="tablaCostoOr" class="display" style="width:100%">
                                        <thead>
                                            <tr>
                                                <th></th>
                                                <th>CODE ARTICULO</th>
                                                <th>DESCRIPCION</th>
                                                <th>TIPO LISTA</th>
                                                <th>CANTIDAD PLANIFICADA</th>
                                                <th>CANTIDAD CONSUMIDA SAP</th>
                                                <th>CANTIDAD CONSUMIDA APP</th>
                                                <th>TIPO MATERIAL</th>
                                                <th>COSTO UNITARIO PLN</th>
                                                <th>COSTO UNITARIO REAL</th>
                                                <th>COSTO TOTAL PLN</th>
                                                <th>COSTO TOTAL REAL</th>
                                                <th>VARIACIÓN</th>
                                                <th>PORCENTAJE VARIACION</th>
                                                <th>COMENTARIOS</th>
                                            </tr>
                                        </thead>
                                        <tbody id="contenidoTablaCostoOr">

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <br>
                            <div align="right">
                                <button id="btnECierre" class="btn btn-success">Cerrar Orden</button>
                            </div>
                        </div>                    
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarEstadoCierre">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE ESTADO DE CIERRE-->

        <!--MODAL DE TERMINACION DE OP-->
        <div class="modal fade" id="modalTerminacionOP" tabindex="-1" role="dialog" aria-labelledby="modalTerminacionOPLabel" aria-hidden="false" align="center">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
                <div class="modal-content borderModal">
                    <div class="modal-header">
                        <h3 class="modal-title" id="modalTerminacionOPLabel">TERMINACIÓN DE ORDEN</h3>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarTerminacion1">
                            <span aria-hidden="false">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <div class="container-fluid" align="left">
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
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
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <div class="row">
                                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                                    <p>
                                                        <h6><b>COSTO PPTO: </b><spam id="costoPlnT"></spam></h6>
                                                    </p>
                                                </div>
                                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                                    <p>
                                                        <h6><b>COSTO REAL: </b><spam id="cantRealT"></spam></h6>
                                                    </p>
                                                </div>
                                                <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                                    <p>
                                                        <h6><b>VARIACIÓN: </b><spam id="variacionT"></spam></h6>
                                                    </p>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <center><h5>COSTO POR LINEA</h5></center>
                                <table id="tablaDatosMaterialesOr" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th></th>
                                            <th>CODE ARTICULO</th>
                                            <th>DESCRIPCION</th>
                                            <th>TIPO LISTA</th>
                                            <th>CANTIDAD PLANIFICADA</th>
                                            <th>CANTIDAD CONSUMIDA SAP</th>
                                            <th>CANTIDAD CONSUMIDA APP</th>
                                            <th>TIPO MATERIAL</th>
                                            <th>COSTO UNITARIO PLN</th>
                                            <th>COSTO UNITARIO REAL</th>
                                            <th>COSTO TOTAL PLN</th>
                                            <th>COSTO TOTAL REAL</th>
                                            <th>VARIACIÓN</th>
                                            <th>PORCENTAJE VARIACION</th>
                                            <th>COMENTARIOS</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTablaDatosMaterialesOr">

                                    </tbody>
                                </table>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
                                    <h3>TIEMPOS</h3>
                                    <table id="tablaActSap" class="display" width="100%">
                                        <thead>
                                           <tr>
                                               <th><input name="select_act" value="0" id="select_all" type="checkbox" /></th>
                                              <!--<th>Actividad Prod</th>-->
                                              <th>EMPLEADO</th>
                                              <th>ACTIVIDAD</th>
                                              <th>INICIO</th>
                                              <th>FIN</th>
                                              <th>TIEMPO</th>
                                              <th>UNI. COMPLETADAS</th>
                                              <th>UNI. RECHAZADAS</th>
                                              <th>COMENTARIOS</th>
                                              <th>ESTADO</th>
                                              <th>EFICIENCIA</th>
                                           </tr>
                                        </thead>
                                        <tbody id="contenidoTablaActSap">

                                        </tbody>
                                     </table>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
                                    <h3>MATERIALES</h3>
                                    <br>
                                    <!--<div align="left">
                                        <p><h6><b>Estado Materiales: </b><spam id="estadoM1"></spam></h6></p>    
                                    </div>-->
                                    
                                    <table id="tablaArt" class="display" width="100%">
                                        <thead>
                                           <tr>
                                              <th><input name="select_art" value="0" id="select_allArt" type="checkbox"/></th>
                                              <th>COD ARTICULO</th> 
                                              <th>DESCRIPCION</th>
                                              <th>STOCK</th>
                                              <th>BODEGA</th>
                                              <th>CANTIDAD A CONSUMIR</th>
                                           </tr>
                                        </thead>
                                        <tbody id="contenidoTablaArt">

                                        </tbody>
                                     </table>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" align="center">
                                    <h3>PRODUCCIÓN (TERMINACIÓN DE REPORTE)</h3>
                                    <br>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div class="form-group">
                                                <h5 align="left">Cantidad Planificada: </h5>
                                                <input type="text" id="txtPlanificada" class="form-control form-control-sm" disabled="true">
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div class="form-group" align="left">
                                                <h5 align="left"><label>Cargar producción: </label>&nbsp;&nbsp;<input name="cargarProduccion" value="0" id="cargarProduccion" type="checkbox" checked/></h5>
                                            </div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row" align="left">
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
                                    <div align="left">
                                        <p style="color: red;">*La producción no debe exceder la cantidad planificada</p>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
                                            <div align="left"><h5><label>OBSERVACIÓN: </label></h5></div>
                                            <div class="form-group mb-3" id="observacion1">
                                                <textarea id="txtObservacion" class="form-control" rows="2"></textarea>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
                                            <div align="left"><h5><label>PLAN DE ACCIÓN: </label></h5></div>
                                            <div class="form-group mb-3" id="PlanAccion1">
                                                <textarea id="txtPlanAccion" class="form-control" rows="2"></textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
                                            <div align="left"><h5><label>RESPONSABLE: </label></h5></div>
                                            <div class="form-group mb-3" id="responsable1">
                                                <input type="text" id="txtResponsable" class="form-control form-control-sm" />
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-6">
                                            <div align="left"><h5><label>FECHA ACCIÓN: </label></h5></div>
                                            <div class="form-group mb-3" id="fechaAccion1">
                                                <input type="date" id="txtfechaAccion" class="form-control form-control-sm" />
                                            </div>
                                        </div>
                                    </div>
                                    <div align="left">
                                        <p style="color: red;">*El plan de accion debe tener minimo de 80 caracteres</p>
                                    </div>
                                    <br>
                                    <hr>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" hidden>
                                            <div class="form-group" align="left">
                                                <h5 align="left"><label>Listo para cierre de OP: </label>&nbsp;&nbsp;<input name="estadoCierre" value="0" id="estadoCierre" type="checkbox" /></h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>                    
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" id="btnTerminar">Terminar</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarTerminacion">Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
        <!--FIN MODAL DE TERMINACION DE OP-->

        <script src="plugins/js/jquery-3.6.0.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/datatables.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/DataTables-1.11.5/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/dataTables.responsive.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/dataTables.buttons.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/buttons/buttons.html5.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/buttons/buttons.print.min.js" type="text/javascript"></script>
        <script src="plugins/jszip/jszip.min.js" type="text/javascript"></script>
        <script src="plugins/pdfmake/pdfmake.min.js" type="text/javascript"></script>
        <script src="plugins/pdfmake/vfs_fonts.js" type="text/javascript"></script>
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
        <script src="plugins/js/moment.min.js" type="text/javascript"></script>
        <script src="plugins/js/jsOrden.js" type="text/javascript"></script>
        <script type="text/javascript">
            $(document).ready(function () {
                $("#cerrarSes").click(function () {
                    Swal.fire({
                        icon: 'info',
                        type: 'info',
                        title: 'Cerrar sesión',
                        text: '¿Seguro que desea cerrar sesión?',
                        showCancelButton: true,
                        cancelButtonColor: 'red',
                        cancelButtonText: 'Cancelar',
                    }).then(result => {
                        if (result.value) {
                            location.replace("CtrlLogin?logout=1");
                        }
                    });
                });
            });
        </script>
        <!-- ALERTA PARA MOSTRAR ERRORES DEL CONTROLADOR -->
        <c:if test="${error!=null}">
            <script type="text/javascript">
                $(document).ready(function () {
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
        <script type="text/javascript">
            $(document).ready(function () {
                $("#cerrarSes").click(function () {
                    Swal.fire({
                        icon: 'info',
                        type: 'info',
                        title: 'Cerrar sesión',
                        text: '¿Seguro que desea cerrar sesión?',
                        showCancelButton: true,
                        cancelButtonColor: 'red',
                        cancelButtonText: 'Cancelar',
                    }).then(result => {
                        if (result.value) {
                            location.replace("CtrlLogin?logout=1");
                        }
                    });
                });
            });
        </script>
        <!-- ALERTA PARA MOSTRAR ERRORES DEL CONTROLADOR -->
        <c:if test="${error!=null}">
            <script type="text/javascript">
                $(document).ready(function () {
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
