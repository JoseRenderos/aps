<%-- 
    Document   : frmDashboard
    Created on : 10/03/2022, 09:18:38 AM
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
        <title>APS-ALSASA DASHBOARD</title>
        <link rel="icon" href="img/favicon.png">
        <link href="plugins/bootstrap/css/bootstrap.css" rel="stylesheet" type="text/css"/>
        <!--DATETABLE-->
        <link href="plugins/DataTables/datatables.min.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/DataTables/DataTables-1.11.5/css/jquery.dataTables.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/DataTables/responsive.bootstrap.min.css" rel="stylesheet" type="text/css"/>
        
        <link href="plugins/css/sweetalert2.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/sidebars.css" rel="stylesheet" type="text/css"/>
        <link href="plugins/css/style.css" rel="stylesheet" type="text/css"/>
        <style type="text/css">
            .modal-xl { 
                max-width: 98% !important; 
            }
            
            .modal-lg { 
                max-width: 98% !important; 
            }
            
            .not-active { 
                pointer-events: none; 
                cursor: default; 
            }
            .highcharts-data-table table {
                border-collapse: collapse;
                border-spacing: 0;
                background: white;
                min-width: 100%;
                margin-top: 10px;
                font-family: sans-serif;
                font-size: 0.9em;
            }
            .highcharts-data-table td, .highcharts-data-table th, .highcharts-data-table caption {
                border: 1px solid silver;
                padding: 0.5em;
            }
            .highcharts-data-table tr:nth-child(even), .highcharts-data-table thead tr {
                background: #f8f8f8;
            }
            .highcharts-data-table tr:hover {
                background: #eff;
            }
            .highcharts-data-table caption {
                border-bottom: none;
                font-size: 1.1em;
                font-weight: bold;
            }
            .margen{
                margin: 0px;
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
                    <a class="list-group-item list-group-item-action list-group-item-dark active p-3" href="#"><img src="img/dashboard.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Dashboard</b></a>
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
                        int idUsuario=0;
                        if (s.getAttribute("usuario")!=null){
                            idRol=(Integer)s.getAttribute("idRol");
                            idUsuario=(Integer)s.getAttribute("idUsuario"); 
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
                                out.print("<div style='color:#FFF; align:right;'>"+s.getAttribute("nombreUser")+"</div>");/*+"("+s.getAttribute("Rol")+")"*/
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
                    <input type="hidden" id="txtIdRol" value="<%=idRol%>">
                    <input type="hidden" id="txtIdUsuario" value="<%=idUsuario%>">
                <%
                    
                    if (s.getAttribute("usuario")!=null){
                        if ((Integer)s.getAttribute("idUsuario")!=42) {
                %>    
                    <div class="row">
                        <div class="col-12">
                            <br>
                            <div align="center">
                                <div class="row">
                                    <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (PLANTA)</h5></center>
                                </div>
                                <br>
                                <div class="row" id="filtroPlanta" align="left">
                                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                        <h5>FILTRO DE DATOS DE PLANTA: </h5>
                                        <div class="form-group row">
                                            <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                                <div class="form-group">
                                                    <h5><label>FECHA INICIO:</label></h5>
                                                    <input type="date" name="input" id="txtFechaIPlanta" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                                </div>
                                            </div>
                                            <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                                <div class="form-group" >
                                                    <h5><label>FECHA FIN:</label></h5>
                                                    <input type="date" name="input" id="txtFechaFPlanta" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                                </div>
                                            </div>
                                            <div class="col-xs-6 col-sm-6 col-form-label">
                                                <br>
                                                <button type="button" id="btnFiltrarPlanta" class="btn btn-success btl-lg">Filtrar</button>
                                                <button type="button" id="btnCargarTodos" class="btn btn-info">Cargar Todo</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="row">
                                    <div class="col-lg-3">
                                        <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                            <div class="card-header">PLANIFICADAS</div>
                                            <div class="card-body">
                                                <center><h5 class="card-title"></h5>
                                                 <h3><p class="card-text" id="cantOrdenesP"></p></h5></center>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="col-lg-3">
                                        <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                            <div class="card-header">LIBERADAS</div>
                                            <div class="card-body">
                                                <center><h5 class="card-title"></h5>
                                                 <h3><p class="card-text" id="cantOrdenesL"></p></h5></center>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="col-lg-3">
                                        <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                            <div class="card-header">CERRADAS</div>
                                            <div class="card-body">
                                                <center><h5 class="card-title"></h5>
                                                 <h3><p class="card-text" id="cantOrdenesC"></p></h5></center>
                                            </div>
                                        </div> 
                                    </div>
                                    <div class="col-lg-3">
                                        <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                            <div class="card-header">TOTALES</div>
                                            <div class="card-body">
                                                <center><h5 class="card-title"></h5>
                                                    <h3><p class="card-text" id="cantOrdenes"></p></h5></center>
                                        
                                            </div>
                                        </div> 
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="datosProduccion">
                        <div id="cumplimientoPlanta" name="cumplimiento" hidden>
                            <div class="row">
                                <div class="col-12">
                                    <div align="center">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                    <div class="card-header">
                                                      CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                    </div>
                                                    <div class="card-body">
                                                        <h6><p id="pCumplientoActual"></p></h6>
                                                        <h6><p id="OrCmpActual"></p></h6>
                                                        <h6><p id="OrActual"></p></h6>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                    <div class="card-header">
                                                      CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                    </div>
                                                    <div class="card-body">
                                                        <h6><p id="pCumplientoArtActual"></p></h6>
                                                        <h6><p id="OrCmpArtActual"></p></h6>
                                                        <h6><p id="">&nbsp;</p></h6>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                    <div class="card-header">
                                                      CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                    </div>
                                                    <div class="card-body">
                                                        <h6><p id="pCumplientoMes"></p></h6>
                                                        <h6><p id="OrCmpMes"></p></h6>
                                                        <h6><p id="OrMes"></p></h6>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                    <div class="card-header">
                                                      CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                    </div>
                                                    <div class="card-body">
                                                        <h6><p id="pCumplientoArtMes"></p></h6>
                                                        <h6><p id="OrCmpArtMes"></p></h6>
                                                        <h6><p id="">&nbsp;</p></h6>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                    <div class="card-header">
                                                      CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                    </div>
                                                    <div class="card-body">
                                                        <h6><p id="pCumplientoHorasMes"></p></h6>
                                                        <h6><p id="OrCmpHorasMes"></p></h6>
                                                        <h6><p id="">&nbsp;</p></h6>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                    <div class="card-header">
                                                      CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                    </div>
                                                    <div class="card-body">
                                                        <h6><p id="pCumplientoHorasMesFechaActual"></p></h6>
                                                        <h6><p id="OrCmpHorasMesFechaActual"></p></h6>
                                                        <h6><p id="">&nbsp;</p></h6>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-12">
                                    <div align="center">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white mb-3" style="width: 100%; background-color: rgba(129, 129, 129, 1) !important;" id="cardCostoMayor">
                                                    <div class="card-header">
                                                      CANTIDAD DE ORDENES COSTO MAYOR AL 5% (DIA ANTERIOR)
                                                    </div>
                                                    <div class="card-body">
                                                        <h4><p id="pCostoMayor"></p></h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%; background-color: rgba(129, 129, 129, 1) !important;" id="cardCostoMenor">
                                                    <div class="card-header">
                                                      CANTIDAD DE ORDENES COSTO MENOR AL 5% (DIA ANTERIOR)
                                                    </div>
                                                    <div class="card-body">
                                                        <h4><p id="pCostoMenor"></p></h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%; background-color: rgba(129, 129, 129, 1) !important;" id="cardCostoMayorPeriodo">
                                                    <div class="card-header">
                                                      CANTIDAD DE ORDENES COSTO MAYOR AL 5% TOTAL PERIODO
                                                    </div>
                                                    <div class="card-body">
                                                        <h4><p id="pCostoMayorPeriodo"></p></h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%; background-color: rgba(129, 129, 129, 1) !important;" id="cardCostoMenorPeriodo">
                                                    <div class="card-header">
                                                      CANTIDAD DE ORDENES COSTO MENOR AL 5% TOTAL PERIODO
                                                    </div>
                                                    <div class="card-body">
                                                        <h4><p id="pCostoMenorPeriodo"></p></h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%; background-color: rgba(129, 129, 129, 1) !important;" id="cardCostoMayorFiltro">
                                                    <div class="card-header">
                                                      CANTIDAD DE ORDENES COSTO MAYOR AL 5% SEGUN FILTRO
                                                    </div>
                                                    <div class="card-body">
                                                        <h4><p id="pCostoMayorFiltro"></p></h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                <div class="card text-white bg-secondary mb-3" style="width: 100%; background-color: rgba(129, 129, 129, 1) !important;" id="cardCostoMenorFiltro">
                                                    <div class="card-header">
                                                      CANTIDAD DE ORDENES COSTO MENOR AL 5% SEGUN FILTRO
                                                    </div>
                                                    <div class="card-body">
                                                        <h4><p id="pCostoMenorFiltro"></p></h4>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-12">
                                <button type="button" id="btnMostrarDatos" class="btn btn-success btn-lg">Mostrar datos</button>
                                <button type="button" id="btnOcultarDatos" class="btn btn-success btn-lg">Ocultar datos</button>
                            </div>
                        </div>
                        <br>
                        <div id="datosProduccion1">
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoTorno" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (TORNO)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPTORNO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLTORNO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCTORNO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesTORNO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoTorno" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualTORNO"></p></h6>
                                                                            <h6><p id="OrCmpActualTORNO"></p></h6>
                                                                            <h6><p id="OrActualTORNO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualTORNO"></h6>
                                                                            <h6><p id="OrCmpArtActualTORNO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesTORNO"></p></h6>
                                                                            <h6><p id="OrCmpMesTORNO"></p></h6>
                                                                            <h6><p id="OrMesTORNO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesTORNO"></p></h6>
                                                                            <h6><p id="OrCmpArtMesTORNO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesTORNO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesTORNO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualTORNO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualTORNO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoPulido" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (PULIDO)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPPULIDO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLPULIDO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCPULIDO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesPULIDO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoPulido" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualPULIDO"></p></h6>
                                                                            <h6><p id="OrCmpActualPULIDO"></p></h6>
                                                                            <h6><p id="OrActualPULIDO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualPULIDO"></p></h6>
                                                                            <h6><p id="OrCmpArtActualPULIDO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesPULIDO"></p></h6>
                                                                            <h6><p id="OrCmpMesPULIDO"></p></h6>
                                                                            <h6><p id="OrMesPULIDO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesPULIDO"></p></h6>
                                                                            <h6><p id="OrCmpArtMesPULIDO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesPULIDO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesPULIDO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualPULIDO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualPULIDO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoRemachado" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (REMACHADO)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPREMACHADO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLREMACHADO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCREMACHADO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesREMACHADO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoRemachado" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualREMACHADO"></p></h6>
                                                                            <h6><p id="OrCmpActualREMACHADO"></p></h6>
                                                                            <h6><p id="OrActualREMACHADO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualREMACHADO"></p></h6>
                                                                            <h6><p id="OrCmpArtActualREMACHADO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesREMACHADO"></p></h6>
                                                                            <h6><p id="OrCmpMesREMACHADO"></p></h6>
                                                                            <h6><p id="OrMesREMACHADO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesREMACHADO"></p></h6>
                                                                            <h6><p id="OrCmpArtMesREMACHADO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesREMACHADO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesREMACHADO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualREMACHADO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualREMACHADO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoPrensa" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (PRENSA)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPPRENSA"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLPRENSA"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCPRENSA"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesPRENSA"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoPrensa" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualPRENSA"></p></h6>
                                                                            <h6><p id="OrCmpActualPRENSA"></p></h6>
                                                                            <h6><p id="OrActualPRENSA"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualPRENSA"></p></h6>
                                                                            <h6><p id="OrCmpArtActualPRENSA"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesPRENSA"></p></h6>
                                                                            <h6><p id="OrCmpMesPRENSA"></p></h6>
                                                                            <h6><p id="OrMesPRENSA"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesPRENSA"></p></h6>
                                                                            <h6><p id="OrCmpArtMesPRENSA"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesPRENSA"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesPRENSA"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualPRENSA"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualPRENSA"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoFundicion" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (FUNDICION)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPFUNDICION"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLFUNDICION"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCFUNDICION"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesFUNDICION"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoFundicion" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualFUNDICION"></p></h6>
                                                                            <h6><p id="OrCmpActualFUNDICION"></p></h6>
                                                                            <h6><p id="OrActualFUNDICION"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualFUNDICION"></p></h6>
                                                                            <h6><p id="OrCmpArtActualFUNDICION"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesFUNDICION"></p></h6>
                                                                            <h6><p id="OrCmpMesFUNDICION"></p></h6>
                                                                            <h6><p id="OrMesFUNDICION"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesFUNDICION"></p></h6>
                                                                            <h6><p id="OrCmpArtMesFUNDICION"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFUNDICION"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFUNDICION"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualFUNDICION"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualFUNDICION"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoTornoAuto" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (TORNO AUTOMATICO)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPTORNOAUTO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLTORNOAUTO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCTORNOAUTO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesTORNOAUTO"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoTornoauto" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrCmpActualTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrActualTORNOAUTO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrCmpArtActualTORNOAUTO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrCmpMesTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrMesTORNOAUTO"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrCmpArtMesTORNOAUTO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesTORNOAUTO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualTORNOAUTO"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualTORNOAUTO"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoEmpaque" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (EMPAQUE PLANTA)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPEMPAQUE"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLEMPAQUE"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCEMPAQUE"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesEMPAQUE"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoEmpaque" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualEMPAQUE"></p></h6>
                                                                            <h6><p id="OrCmpActualEMPAQUE"></p></h6>
                                                                            <h6><p id="OrActualEMPAQUE"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualEMPAQUE"></p></h6>
                                                                            <h6><p id="OrCmpArtActualEMPAQUE"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesEMPAQUE"></p></h6>
                                                                            <h6><p id="OrCmpMesEMPAQUE"></p></h6>
                                                                            <h6><p id="OrMesEMPAQUE"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesEMPAQUE"></p></h6>
                                                                            <h6><p id="OrCmpArtMesEMPAQUE"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesEMPAQUE"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesEMPAQUE"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualEMPAQUE"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualEMPAQUE"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <div id="cumplimientoLinea1" name="cumplimiento" hidden>
                                <div class="row">
                                    <div class="col-12">
                                        <hr>
                                        <div align="center">
                                            <div class="row">
                                                <center><h5>CANTIDAD DE ORDENES DE PRODUCCIÓN (LINEA DE PRODUCCION 1)</h5></center>
                                            </div>
                                            <br>
                                            <div class="row">
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-warning mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">PLANIFICADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesPLINEA1"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-danger mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">LIBERADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesLLINEA1"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-primary mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">CERRADAS</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                             <h3><p class="card-text" id="cantOrdenesCLINEA1"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                                <div class="col-lg-3">
                                                    <div class="card text-white bg-success mb-3" style="max-width: 18rem;">
                                                        <div class="card-header">TOTALES</div>
                                                        <div class="card-body">
                                                            <center><h5 class="card-title"></h5>
                                                                <h3><p class="card-text" id="cantOrdenesLINEA1"></p></h5></center>
                                                        </div>
                                                    </div> 
                                                </div>
                                            </div>
                                            <div id="cumplimientoLinea1" name="cumplimiento">
                                                <div class="row">
                                                    <div class="col-12">
                                                        <div align="center">
                                                            <div class="row">
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoActualLINEA1"></p></h6>
                                                                            <h6><p id="OrCmpActualLINEA1"></p></h6>
                                                                            <h6><p id="OrActualLINEA1"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtActualLINEA1"></p></h6>
                                                                            <h6><p id="OrCmpArtActualLINEA1"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(154, 159, 164 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ORDENES TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoMesLINEA1"></p></h6>
                                                                            <h6><p id="OrCmpMesLINEA1"></p></h6>
                                                                            <h6><p id="OrMesLINEA1"></p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white mb-3" style="width: 100%; background-color: rgba(98, 100, 102 ,1) !important;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO ARTICULOS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoArtMesLINEA1"></p></h6>
                                                                            <h6><p id="OrCmpArtMesLINEA1"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS TOTAL PERIODO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesLINEA1"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesLINEA1"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-2">
                                                                    <div class="card text-white bg-secondary mb-3" style="width: 100%;">
                                                                        <div class="card-header">
                                                                          CUMPLIMIENTO DE HORAS SEGUN FILTRO
                                                                        </div>
                                                                        <div class="card-body">
                                                                            <h6><p id="pCumplientoHorasMesFechaActualLINEA1"></p></h6>
                                                                            <h6><p id="OrCmpHorasMesFechaActualLINEA1"></p></h6>
                                                                            <h6><p id="">&nbsp;</p></h6>
                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            
                                        </div>
                                    </div>
                                </div>
                                <br>
                            </div>
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                            <!--//////////////////////////////////////////////////////////////////////////////////////////////////////////////-->
                        </div>
                    </div>
                    <div align="left">
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-5 col" style="margin-top: 8px;">
                                        <div class="form-group" id="txtFecha1">
                                            <h6><label>Ver horas por fecha: </label></h6>
                                            <input type="date" name="input" id="txtFecha" class="form form-control" value="" placeholder="Ingrese fecha" />
                                        </div>
                                    </div>
                                    <div class="col-xs-2 col-sm-6 col-form-label" style="margin-top: 26px;">
                                        <button type="button" id="btnVerFecha" class="btn btn-success">ver fecha</button>
                                        <button type="button" id="btnVerHoy" class="btn btn-info">ver Hoy</button>
                                    </div>
                                </div>
                            </div>
                        </div>    
                    </div>
                    <div class="row">
                        <div class="col-12">
                        <div id="container"></div>
                        </div>
                    </div>
                    <hr>
                    
                    <div class="row">
                        <div class="col-12">
                            <button type="button" id="btnMostrarGraficos" class="btn btn-success btn-lg">Mostrar Graficos</button>
                            <button type="button" id="btnOcultarGraficos" class="btn btn-success btn-lg">Ocultar Graficos</button>
                        </div>
                    </div>
                    <div id="GraficosProd">
                        <div class="row">
                            <div class="col-12">
                            <div id="containerHorasMes"></div>
                            </div>
                        </div>
                        <%
                                if (s.getAttribute("usuario")!=null){
                                    idRol=(Integer)s.getAttribute("idRol");
                                    idUsuario=(Integer)s.getAttribute("idUsuario"); 
                                    if ((Integer)s.getAttribute("idRol")!=2){
                                        %>
                                            <div class="row">
                                                <hr> 
                                                <div class="col-12">
                                                <div id="containerHorasDetalleMes"></div>
                                                </div>
                                            </div>
                                            <div class="row">
                                                <hr> 
                                                <div class="col-12">
                                                <div id="containerAvanceMes"></div>
                                                </div>
                                            </div>
                                        <%
                                    }
                                }
                            }
                            }
                        %> 


                        <div id="carouselProduccion" class="carousel slide" data-bs-ride="carousel" data-bs-interval="false">
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <div class="row d-block w-100" id="produccionMes">
                                        <hr> 
                                        <div class="col-12">
                                        <div id="containerProduccionGruposMes"></div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <div id="produccionDia" class="d-block w-100">
                                        <hr class="margen">
                                        <center><h5>Produccion de grupos (dia actual)</h5></center>
                                        <div class="row ">
                                            <div class="col-lg-4">
                                                <img src="img/Logoalsasa.jpg" width="100%" alt="Logoalsasa"/>
                                            </div>
                                            <div class="col-lg-4">
                                                <div id="containerProduccionGrupo1"></div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div id="containerProduccionGrupo2"></div>
                                            </div>
                                        </div>
                                        <hr class="margen">
                                        <div class="row">
                                            <div class="col-lg-4">
                                              <div id="containerProduccionGrupo3"></div>  
                                            </div>
                                            <div class="col-lg-4">
                                                <div id="containerProduccionGrupo4"></div>
                                            </div>
                                            <div class="col-lg-4">
                                                <div id="containerProduccionTotal"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="carousel-item">
                                    <img src="img/CANASTA_FEB.png" width="95%" alt="CANASTA_FEB"/>
                                </div>
                                <div class="carousel-item">
                                    <img src="img/EQUIPOS_PRODUCCION.png" width="90%" alt="EQUIPOS_PRODUCCION"/>
                                </div>
                            </div>
                            <button class="carousel-control-prev" id="btnCaPrev" type="button" data-bs-target="#carouselProduccion" data-bs-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Previous</span>
                            </button>
                            <button class="carousel-control-next" id="btnCaNext" type="button" data-bs-target="#carouselProduccion" data-bs-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="visually-hidden">Next</span>
                            </button>
                        </div>

                    <%

                        if (s.getAttribute("usuario")!=null){
                            if ((Integer)s.getAttribute("idUsuario")!=42) {
                    %>    
                      <!--  <hr class="margen">
                        <div class="row">
                            <div class="col-lg-3">
                                <div id="containerConteoArticulosPlanta"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos101"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos102"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos103"></div>
                            </div>
                        </div>
                        <hr class="margen">
                        <div class="row">
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos105"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos106"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos108"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos109"></div>
                            </div>
                        </div>
                        <hr class="margen">
                        <div class="row">
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos110"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos111"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos114"></div>
                            </div>
                            <div class="col-lg-3">
                                <div id="containerConteoArticulos126"></div>
                            </div>
                        </div>-->
                        <div id="graficas">
                            <div class="row" id="total" hidden>
                                <hr>
                                <div id="container1"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenP"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenL"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenC"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="cecos" hidden> 
                                <hr>
                                <div id="container2"></div>
                            </div>
                            <div class="row" id="TORNO" hidden>
                                <hr>
                                <div id="containerTorno"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPTorno"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLTorno"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCTorno"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="PULIDO" hidden>
                                <hr>
                                <div id="containerPulido"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPPulido"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLPulido"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCPulido"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="REMACHADO" hidden>
                                <hr>
                                <div id="containerRemachado"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPRemachado"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLRemachado"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCRemachado"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="SELLADO" hidden>
                                <hr>
                                <div id="containerSellado"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPSellado"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLSellado"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCSellado"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="PRENSAS" hidden>
                                <hr>
                                <div id="containerPrensas"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPPrensas"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLPrensas"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCPrensas"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="FUNDICION" hidden>
                                <hr>
                                <div id="containerFundicion"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPFundicion"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLFundicion"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCFundicion"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="TORNO_AUTOMATICO" hidden>
                                <hr>
                                <div id="containerTorno_automatico"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPTorno_automatico"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLTorno_automatico"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCTorno_automatico"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="EMBUTICION" hidden>
                                <hr>
                                <div id="containerEmbuticion"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPEmbuticion"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLEmbuticion"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCEmbuticion"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="REMACHADO_CAPELLI" hidden>
                                <hr>
                                <div id="containerRemachado_capelli"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPRemachado_capelli"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLRemachado_capelli"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCRemachado_capelli"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="COCINETAS" hidden>
                                <hr>
                                <div id="containerCocinetas"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPCocinetas"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLCocinetas"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCCocinetas"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="EMPAQUE_PLANTA" hidden>
                                <hr>
                                <div id="containerEmpaque_planta"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPEmpaque_planta"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLEmpaque_planta"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCEmpaque_planta"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="LINEA_PRODUCCION_1" hidden>
                                <hr>
                                <div id="containerLinea_produccion_1"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPLinea_produccion_1"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLLinea_produccion_1"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCLinea_produccion_1"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                            <div class="row" id="LINEA_PRODUCCION_2" hidden>
                                <hr>
                                <div id="containerLinea_produccion_2"></div>
                                <div align="right">
                                    <div class="btn btn-danger">Planificadas: <spam id="cantidadOrdenPLinea_produccion_2"></spam></div>
                                    <div class="btn btn-primary">Liberadas: <spam id="cantidadOrdenLLinea_produccion_2"></spam></div>
                                    <div class="btn btn-success">Cerradas: <spam id="cantidadOrdenCLinea_produccion_2"></spam></div>
                                </div>
                                <br>
                                <br>
                            </div>
                        </div>
                        <br>

                    <%
                            }
                        }
                    %>  
                    </div>
                </div>
            </div>
        </div>
        
        <!--MODAL DE ORDEN CON COSTO MAYOR AL 5%-->
        <div class="modal fade" id="modalCostoMayor" tabindex="-1" role="dialog" aria-labelledby="modalCostoMayorLabel" aria-hidden="true" align="center">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                    <div class="modal-header">
                  <h3 class="modal-title" id="modalCostoMayorLabel">OPS COSTO MAYOR AL 5%</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarCostoMayor1">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" align="left">
                        <div class="row">
                            <table id="tablaDatosOrsCostoMayor" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>DOCNUM</th>
                                        <th>CODIGO ARTICULO</th>
                                        <th>DESCRIPCION</th>
                                        <th>FECHA INICIO</th>
                                        <th>FECHA CIERRE</th>
                                        <th>CANT PLANIFICADA</th>
                                        <th>CANT COMPLETADA</th>
                                        <th>CANT RECHAZADA</th>
                                        <th>COSTO UNITARIO PRESUPUESTADO</th>
                                        <th>COSTO UNITARIO REAL</th>
                                        <th>VARIACION</th>
                                    </tr>
                                </thead>
                                <tbody id="contenidoTablaDatosOrsCostoMayor">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarCostoMayor">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        
        <!--MODAL DE ORDEN CON COSTO MENOR AL 5%-->
        <div class="modal fade" id="modalCostoMenor" tabindex="-1" role="dialog" aria-labelledby="modalCostoMenorLabel" aria-hidden="true" align="center">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                    <div class="modal-header">
                  <h3 class="modal-title" id="modalCostoMenorLabel">OPS COSTO MENOR AL 5%</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarCostoMenor1">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" align="left">
                        <div class="row">
                            <table id="tablaDatosOrsCostoMenor" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>DOCNUM</th>
                                        <th>CODIGO ARTICULO</th>
                                        <th>DESCRIPCION</th>
                                        <th>FECHA INICIO</th>
                                        <th>FECHA CIERRE</th>
                                        <th>CANT PLANIFICADA</th>
                                        <th>CANT COMPLETADA</th>
                                        <th>CANT RECHAZADA</th>
                                        <th>COSTO UNITARIO PRESUPUESTADO</th>
                                        <th>COSTO UNITARIO REAL</th>
                                        <th>VARIACION</th>
                                    </tr>
                                </thead>
                                <tbody id="contenidoTablaDatosOrsCostoMenor">

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarCostoMenor">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        
        
        <script src="plugins/js/jquery-3.6.0.min.js" type="text/javascript"></script>
        <script src="plugins/js/popper.min.js" type="text/javascript"></script>
        <script src="plugins/bootstrap/js/bootstrap.js" type="text/javascript"></script>
        <script src="plugins/DataTables/datatables.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/DataTables-1.11.5/js/jquery.dataTables.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/dataTables.responsive.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/dataTables.buttons.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/buttons/buttons.html5.min.js" type="text/javascript"></script>
        <script src="plugins/DataTables/buttons/buttons.print.min.js" type="text/javascript"></script>
        <script src="plugins/js/sweetalert2.js" type="text/javascript"></script>
        <script src="plugins/blockui/jquery.blockUI.js" type="text/javascript"></script>
        <script src="plugins/js/sidebars.js" type="text/javascript"></script>
        <script src="plugins/highcharts/highcharts.js" type="text/javascript"></script>
        <script src="plugins/highcharts/highcharts-more.js" type="text/javascript"></script>
        <script src="plugins/js/jsDashboard.js" type="text/javascript"></script>
        <script src="plugins/highcharts/modules/exporting.js" type="text/javascript"></script>
        <script src="plugins/highcharts/modules/export-data.js" type="text/javascript"></script>
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