<%-- 
    Document   : frmInformes
    Created on : 18/04/2022, 01:23:27 PM
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
        <title>APS-ALSASA INFORMES</title>
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
                    <a class="list-group-item list-group-item-action list-group-item-dark active p-3" href="frmInformes.jsp"><img src="img/inform.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Informes</b></a>
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
                        int idUsuario=0;
                        if (s.getAttribute("usuario")!=null){
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
                                <center><h4 class="display-6">Informes</h4></center>
                                <br>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6" align="left" id="selectReportes" >
                                <select name="reportes" id="reportes" class="form form-control" autocomplete="off" style="width: 100%">
                                    <option value="rVinhetaArt">Viñeta articulo inventario</option>
                                    <option value="1">Reporte labores asignadas</option>
                                    <option value="2">Reporte de orden produccion</option>
                                    <option value="3">Reporte de ordenes por fecha</option>
                                    <option value="10">Reporte ordenes por Centro de costos</option>
                                    <option value="rComparativaPlan">Reporte comparativa de plan por CECOS</option>
                                    <option value="4">Reporte produccion por empleado</option>
                                    <option value="30">Reporte de valorizado de producion por empleado</option>
                                    <!--<option value="31">Reporte de pago a empleados</option>
                                    <option value="32">Reporte de pago por departamento</option>-->
                                    <option value="rValorEmp">Reporte de valor de asignación a empleados</option>
                                    <option value="rValorDepto">Reporte de valor de asignación por departamento</option>
                                    <!--<option value="rPagoMin">Reporte de pago a empleados V2</option>
                                    <option value="rPagoMinDepto">Reporte de pago por departamento V2</option>-->
                                    <option value="rPagoMinV3">Reporte de pago a empleados V3</option>
                                    <option value="rPagoMinDeptoV3">Reporte de pago por departamento V3</option>
                                    <option value="rConsumoMP">Reporte de materia prima a consumir</option>
                                    <option value="5">Reporte de control de avance</option>
                                    <option value="6">Reporte eficiencia por empleado</option>
                                    <option value="7">Reporte eficiencia de planta</option>
                                    <option value="rEficienciaArt">Reporte eficiencia de articulo</option>
                                    <option value="15">Reporte eficiencia de empaque</option>
                                    <%
                                        if (s.getAttribute("usuario")!=null){
                                            idUsuario=(Integer)s.getAttribute("idUsuario");
                                            if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==3 || (Integer)s.getAttribute("idRol")==7){
                                                %>
                                                    <option value="17">Reporte cumplimiento de ordenes</option>
                                                    <!--<option value="8">Reporte pago produccion</option>-->
                                                <%
                                            }
                                        }
                                    %>
                                    <!--<option value="9">Reporte ordenes por usuario</option>-->
                                    
                                    <%
                                        if (s.getAttribute("usuario")!=null){
                                            idUsuario=(Integer)s.getAttribute("idUsuario");
                                            if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==3 || (Integer)s.getAttribute("idRol")==7){
                                                %>
                                                
                                                    <option value="23">Reporte analisis de costo por articulo</option>
                                                    <option value="22">Reporte analisis de costo por OP</option>
                                                    <option value="24">Reporte analisis de costo por Fecha</option>
                                                    <option value="25">Reporte analisis de costo TINAS</option>
                                                    <!--<option value="11">Reporte Horas de trabajo(TOTAL)</option>
                                                    <option value="12">Reporte Horas de trabajo(MANO DE OBRA)</option>
                                                    <option value="13">Reporte Horas de trabajo(MAQUINA)</option>-->
                                                    <%
                                            }
                                        }
                                                    %>
                                                    <!--<option value="18">Reporte Horas de trabajo con horas estandar (TOTAL)</option>
                                                    <option value="19">Reporte Horas de trabajo con horas estandar (MANO DE OBRA)</option>
                                                    <option value="20">Reporte Horas de trabajo con horas estandar (MAQUINA)</option>-->
                                                    <option value="26">Reporte Horas de trabajo con Actividades Varias (TOTAL)</option>
                                                    <option value="28">Reporte Horas de trabajo con horas empleados activos (TOTAL)</option>
                                                    <option value="rhorasPlanilllaV2">Reporte Horas de trabajo con horas empleados activos V2 (TOTAL)</option>
                                                    <option value="rControlhoras">Reporte Control de Horas</option>
                                                    <!--<option value="rHorasSH">Reporte Horas de trabajo con estandar anterior</option>-->
                                                    <option value="27">Reporte Ordenes cerradas con actividades</option>
                                    <%
                                        if (s.getAttribute("usuario")!=null){
                                            idUsuario=(Integer)s.getAttribute("idUsuario");
                                            if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idRol")==3 || (Integer)s.getAttribute("idRol")==7){
                                                %>
                                                    <option value="21">Reporte Historico de tiempos de articulo</option>
                                                    <option value="14">Reporte Datos de produccion</option>
                                                    <option value="29">Reporte proyeccion de costo de Articulo</option>
                                                    <option value="16">Ejecutar proyeccion de produccion</option>
                                                    <!--<option value="33">Generar Pagos Empleados</option>-->
                                                <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>
                        </div>
                    </div>
                    <br>
                    <fieldset class="border rounded-3 p-3" >
                        <legend class="float-none w-auto px-3">REPORTE</legend>
                        <div class="row" id="ReporteLaboresAsignada" name="reporte">
                            <div class="col-12">
                                <h5>Reporte labores asignadas el dia de hoy: </h5>
                                <button id="btnRLA" class="btn btn-primary">Ver reporte</button>
                            </div>
                        </div>
                        <div class="row" id="ReporteOrden" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte labores por orden de produccion: </h5>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-6 col" style="margin-top: 8px;">
                                        <input type="text" name="input" id="NumOrden1" class="form form-control" value="" placeholder="Ingrese el numero de orden" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" />
                                    </div>
                                    <div class="col-xs-2 col-sm-4 col-form-label">
                                        <button type="button" id="btnReporteOrden" class="btn btn-primary">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteOrFecha" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte de ordenes por fecha: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-5 col-sm-5 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaO1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-sm-5 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaO2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>                                
                                <div class="form-group row">
                                    <div class="col-xs-12 col-sm-12 col-form-label" >
                                            <br>
                                            <button type="button" id="btnReporteOrFecha" class="btn btn-primary btl-lg" >Ver reporte</button>&nbsp;
                                            <button type="button" id="btnReporteOrFechaExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>&nbsp;
                                            <button type="button" id="btnReporteOrSAP" class="btn btn-success btl-lg" >Ver reporte Fec. Original </button>&nbsp;
                                            <button type="button" id="btnReporteOrSAPActual" class="btn btn-success btl-lg" >Ver reporte Fec. Actual</button>&nbsp;
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteProdEmp" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte de produccion por empleado: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleados">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleados" name="codeEmpleados" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFecha1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFecha2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReporteProdEmp" class="btn btn-primary btl-lg">Ver reporte</button>
                                            <button type="button" id="btnReporteProdEmpV2" class="btn btn-primary btl-lg">Ver reporte con detalle</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteValEmp" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte de valorizado de producion por empleado: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosVal">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosVal" name="codeEmpleados" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaVal1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaVal2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReporteValEmp" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteControlAvance" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Eficiencia de planta </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectDeptoCA">
                                            <h5><label>Departamento:</label></h5>
                                            <select id="codeDeptoCA" name="codeDeptoCA" class="form-control" autocomplete="off" style="width: 100%">
                                                <option value="TODOS">TODOS</option>
                                                <option value="101">TORNO</option>
                                                <option value="102">PULIDO</option>
                                                <option value="103">REMACHADO</option>
                                                <option value="104">SELLADO</option>
                                                <option value="105">PRENSA</option>
                                                <option value="106">FUNDICION</option>
                                                <option value="108">TORNO AUTOMATICO</option>
                                                <option value="109">EMBUTICION</option>
                                                <option value="110">REMACHADO CAPELLI</option>
                                                <option value="111">COCINETAS</option>
                                                <option value="114">EMPAQUE PLANTA</option>
                                                <option value="126">LINEA DE PRODUCCION 1</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaCA1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaCA2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReporteControlAvance" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteEficienciaEmp" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Eficiencia por empleado: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosE">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosE" name="codeEmpleadosE" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaE1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaE2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReporteEficienciaEmp" class="btn btn-primary btl-lg">Ver reporte</button>
                                            <button type="button" id="btnReporteEficienciaEmpExcel" class="btn btn-success btl-lg">Ver reporte excel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="rEficienciaArt" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Eficiencia por Articulo: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectArticulosE">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeArticulosE" name="codeArticulosE" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaEArt1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaEArt2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReporteEficienciaArt" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteEficienciaEP" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Eficiencia de planta </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectDepto">
                                            <h5><label>Departamento:</label></h5>
                                            <select id="codeDepto" name="codeDepto" class="form-control" autocomplete="off" style="width: 100%">
                                                <option value="-1">TODOS</option>
                                                <option value="3">TORNO</option>
                                                <option value="4">TORNO AUTOMATICO</option>
                                                <option value="5">PRENSA</option>
                                                <option value="6">PULIDO</option>
                                                <option value="7">REMACHADO</option>
                                                <option value="8">LINEA PRODUCCION 1</option>
                                                <option value="9">FUNDICION</option>
                                                <option value="10">COCINETAS</option>
                                                <option value="13">EMPAQUE PLANTA</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaEP1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaEP2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReporteEficienciaEP" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteAvanceOps" name="reporte">
                            <div class="col-12">
                                <h5>Reporte avance de cumplimiento de ordenes de producción: </h5>
                                <button id="btnAvanceOps" class="btn btn-primary">Ver reporte</button>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoEmp" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte pago de produccion: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosP">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosP" name="codeEmpleadosP" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaP1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaP2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReportePagoEmp" class="btn btn-primary btl-lg">Ver reporte</button>
                                            <button type="button" id="btnReportePagoEmpExcel" class="btn btn-success btl-lg">Ver reporte excel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteUsuario" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte de ordenes por Usuario: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectUsuario">
                                            <h5><label>Usuario: </label></h5>
                                            <select id="idUsuario" name="idUsuario" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaU1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaU2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReporteUsuario" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteComPlan" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte comparativa de plan por CECOS: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaCP1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaCP2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-12 col-sm-12 col-form-label">
                                        <button type="button" id="btnReporteComPlan" class="btn btn-success btl-lg" >Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteCecos" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de ordenes por Centro de costos: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectCecos">
                                            <h5><label>Cecos: </label></h5>
                                            <select id="Cecos" name="Cecos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaC1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaC2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-12 col-sm-12 col-form-label">
                                        <button type="button" id="btnReporteCecos" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <button type="button" id="btnReporteOrCecosExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>
                                        <button type="button" id="btnReporteOrCecosSAP" class="btn btn-success btl-lg" >Ver reporte Fec. Original</button>
                                        <button type="button" id="btnReporteOrCecosSAPActual" class="btn btn-success btl-lg" >Ver reporte Fec. Actual</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row" id="ReporteCostosOrden" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte analisis de costos por OP: </h5>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-6 col" style="margin-top: 8px;">
                                        <input type="text" name="input" id="NumOrdenCosto" class="form form-control" value="" placeholder="Ingrese el numero de orden" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" />
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <button type="button" id="btnReporteCostoOrden" class="btn btn-primary">Ver reporte</button>&nbsp;
                                        <button type="button" id="btnReporteCostoOrdenExcel" class="btn btn-success">Ver reporte Excel</button>
                                        <button type="button" id="btnReporteCostoOrdenExcel2" class="btn btn-success">Ver reporte Excel V2</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHoras" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaH1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaH2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHoras" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteOrCecosExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasL" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo (Mano de obra): </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHL1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHL2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasL" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasM" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo (Maquina): </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHM1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHM2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasM" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasEstandar" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con horas estandar: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHS1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHS2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasEstandar" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <button type="button" id="btnReporteHorasEstandarExcel" class="btn btn-success btl-lg" >Ver reporte por fecha</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasEstandarL" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con horas estandar (MANO DE OBRA): </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHSL1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHSL2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasEstandarL" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteOrCecosExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasEstandarM" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con horas estandar (MAQUINA): </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHSM1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHSM2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasEstandarM" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteOrCecosExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteOrCerradasAct" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de ordenes cerradas con actividades: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaOC1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaOC2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteOrCerradasAct" class="btn btn-success btl-lg" >Ver reporte Excel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasAct" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con actividades varias: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHAC1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHAC2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasAct" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="rHorasSH" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con estandar anterior: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHSH1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHSH2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasSH" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteHorasPlanilla" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con Horas de empleados activos: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHplanilla1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHplanilla2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasPlanilla" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <button type="button" id="btnReporteHorasPlanillaExcel" class="btn btn-success btl-lg">Ver reporte Excel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="rhorasPlanilllaV2" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Horas de trabajo con Horas de empleados activos V2: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaHplanillaV21" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaHplanillaV22" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteHorasPlanillaV2" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteHorasPlanillaExcel" class="btn btn-success btl-lg">Ver reporte Excel</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="rControlHoras" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de control de horas: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaControlHoras1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaControlHoras2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteControlHoras" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteHorasPlanillaExcel" class="btn btn-success btl-lg">Ver reporte Excel</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteCHoras" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte datos de produccion: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha</label></h5>
                                            <input type="date" name="input" id="txtFechaCH" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnReporteCHoras" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteOrCecosExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteEficienciaEE" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de Eficiencia de empaque </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaEE1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaEE2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReporteEficienciaEE" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="EjecutarProyeccion" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Ejecutar proyeccion de produccion</h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-5 col-sm-7 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectCodeArticulos">
                                            <h5><label>Codigo de articulo:</label></h5>
                                            <select id="codeArticulos" name="cecos" class="form-control" autocomplete="off" style="width: 100%" placeholder="Seleccione un articulo"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Cantidad:</label></h5>
                                            <input type="text" name="input" id="txtCantArt" class="form form-control" value="" placeholder="Ingrese la cantidad de articulos"  onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)"/>
                                        </div>
                                    </div>
                                    <div class="col-xs-2 col-sm-2 col-form-label" style="padding-bottom: 1.5em;padding-top: 1.5em;">
                                        <button type="button" class="btn btn-primary" id="btnAgregarArticulo"><img src="img/plus.png" width="25px" style="padding-bottom: 4px">&nbsp;Agregar</button>
                                    </div>
                                </div>
                                
                                <div  class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-5 col-lg-5">
                                        <div align="left">
                                            <input type="file" id="filePlantilla" accept=".xls,.xlsx" class="form-control"/>   
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-12 col-md-2 col-lg-2">
                                        <div align="left">
                                            <button class="btn btn-success" id="btnCargarPlantilla" name="btnCargarPlantilla">Cargar Tabla</button>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row" align="center">
                                   
                                    <table id="tablaArticulos" class="display" style="width:100%">
                                        <thead>
                                            <tr>
                                                <th>COD ARTICULO</th>
                                                <th>DESCRIPCION</th>
                                                <th>CANTIDAD SOLICITADA</th>
                                            </tr>
                                        </thead>
                                        <tbody id="contenidoTablaArticulos">

                                        </tbody>
                                    </table>
                                </div>
                                <div align="right">
                                    <button type="button" class="btn btn-danger" id="btnBorrarArticulo"><img src="img/borrar.png" width="25px" style="padding-bottom: 4px"></button>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnEjecutar" class="btn btn-success btn-lg">Ejecutar</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row" id="ReporteHistoricoArt" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte historico de articulos: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-12 col-sm-6 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectCodeArticulosH">
                                            <h5><label>CodeArticulos: </label></h5>
                                            <select id="codeArticulosH" name="Cecos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <button type="button" id="btnReporteHistoricoArt" class="btn btn-primary btl-lg" >Ver reporte</button>
                                        <button type="button" id="btnReporteHistoricoArtExcel" class="btn btn-success btl-lg" >Ver reporte Excel</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteCostosArt" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de costo por articulo: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectCodeArticulosCosto">
                                            <h5><label>Articulo: </label></h5>
                                            <select id="codeArticulosCosto" name="Cecos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaCosto1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaCosto2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <button type="button" id="btnReporteCostoArt" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteCostosFecha" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de costo por fecha: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaCostoF1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaCostoF2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <button type="button" id="btnReporteCostoFecha" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <button type="button" id="btnReporteCostoFechaV2" class="btn btn-primary btl-lg">Ver reporte V2</button>
                                        <button type="button" id="btnReporteCostoFechaExcelV2" class="btn btn-success btl-lg">Ver reporte Excel V2</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteCostosTinas" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de costo de tinas: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaTinasF1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaTinasF2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <button type="button" id="btnReporteCostoTinas" class="btn btn-primary btl-lg">Ver reporte</button>
                                        <!--<button type="button" id="btnReporteCostoFechaV2" class="btn btn-primary btl-lg">Ver reporte V2</button>
                                        <button type="button" id="btnReporteCostoFechaExcelV2" class="btn btn-success btl-lg">Ver reporte Excel V2</button>-->
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteCostoArticulo" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de proyeccion de costo de articulos</h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-5 col-sm-7 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectCodeArticulosCosteo">
                                            <h5><label>Codigo de articulo:</label></h5>
                                            <select id="codeArticulosCosteo" name="cecos" class="form-control" autocomplete="off" style="width: 100%" placeholder="Seleccione un articulo"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-5 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Cantidad:</label></h5>
                                            <input type="text" name="input" id="txtCantArtCosto" class="form form-control" value="" placeholder="Ingrese la cantidad de articulos"  onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)"/>
                                        </div>
                                    </div>
                                    <div class="col-xs-2 col-sm-2 col-form-label" style="padding-bottom: 1.5em;padding-top: 1.5em;">
                                        <button type="button" class="btn btn-success" id="btnReporteCostoArticulo">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoProd" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte pago a empleados </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosPE">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosPE" name="codeEmpleadosP" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaPE1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaPE2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReportePagoProd" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoDepto" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de pago por departamento </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectDeptoPD">
                                            <h5><label>Departamento:</label></h5>
                                            <select id="codeDeptoPD" name="codeDepto" class="form-control" autocomplete="off" style="width: 100%">
                                                <option value="-1">TODOS</option>
                                                <option value="3">TORNO</option>
                                                <option value="4">TORNO AUTOMATICO</option>
                                                <option value="5">PRENSA</option>
                                                <option value="6">PULIDO</option>
                                                <option value="7">REMACHADO</option>
                                                <option value="8">LINEA PRODUCCION 1</option>
                                                <option value="9">FUNDICION</option>
                                                <option value="10">COCINETAS</option>
                                                <option value="13">EMPAQUE PLANTA</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaPD1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaPD2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReportePagoPD" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteValorEmp" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte valor de asignación a empleados </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosVE">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosVE" name="codeEmpleadosV" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaVE1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaVE2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReporteValorEmp" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReporteValorDepto" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de valor de asignacion por departamento </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectDeptoVD">
                                            <h5><label>Departamento:</label></h5>
                                            <select id="codeDeptoVD" name="codeDepto" class="form-control" autocomplete="off" style="width: 100%">
                                                <option value="-1">TODOS</option>
                                                <option value="3">TORNO</option>
                                                <option value="4">TORNO AUTOMATICO</option>
                                                <option value="5">PRENSA</option>
                                                <option value="6">PULIDO</option>
                                                <option value="7">REMACHADO</option>
                                                <option value="8">LINEA PRODUCCION 1</option>
                                                <option value="9">FUNDICION</option>
                                                <option value="10">COCINETAS</option>
                                                <option value="13">EMPAQUE PLANTA</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaVD1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaVD2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReportePagoVD" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoProdMin" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte pago a empleados V2</h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosPEMin">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosPEMin" name="codeEmpleadosPMin" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaPEMin1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaPEMin2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReportePagoProdMin" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoDeptoMin" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte pago a empleados por departamento V2</h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectDeptoPDMin">
                                            <h5><label>Departamento:</label></h5>
                                            <select id="codeDeptoPDMin" name="codeDeptoMin" class="form-control" autocomplete="off" style="width: 100%">
                                                <option value="-1">TODOS</option>
                                                <option value="3">TORNO</option>
                                                <option value="4">TORNO AUTOMATICO</option>
                                                <option value="5">PRENSA</option>
                                                <option value="6">PULIDO</option>
                                                <option value="7">REMACHADO</option>
                                                <option value="8">LINEA PRODUCCION 1</option>
                                                <option value="9">FUNDICION</option>
                                                <option value="10">COCINETAS</option>
                                                <option value="13">EMPAQUE PLANTA</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaPDMin1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaPDMin2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReportePagoPDMin" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoProdMinV3" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                
                                <h5>Reporte pago a empleados V3</h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectEmpleadosPEMinV3">
                                            <h5><label>Empleado:</label></h5>
                                            <select id="codeEmpleadosPEMinV3" name="codeEmpleadosPMin" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaPEMinV31" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaPEMinV32" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                            <button type="button" id="btnReportePagoProdMinV3" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="ReportePagoDeptoMinV3" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte pago a empleados por departamento V3</h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectDeptoPDMinV3">
                                            <h5><label>Departamento:</label></h5>
                                            <select id="codeDeptoPDMinV3" name="codeDeptoMinV3" class="form-control" autocomplete="off" style="width: 100%">
                                                <option value="-1">TODOS</option>
                                                <option value="3">TORNO</option>
                                                <option value="4">TORNO AUTOMATICO</option>
                                                <option value="5">PRENSA</option>
                                                <option value="6">PULIDO</option>
                                                <option value="7">REMACHADO</option>
                                                <option value="8">LINEA PRODUCCION 1</option>
                                                <option value="9">FUNDICION</option>
                                                <option value="10">COCINETAS</option>
                                                <option value="13">EMPAQUE PLANTA</option>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaPDMinV31" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaPDMinV32" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col-form-label">
                                            <button type="button" id="btnReportePagoPDMinV3" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="GenerarPagosEmp" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Generar pagos por producción a empleados: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaGP1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaGP2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnGenerarPagosEmp" class="btn btn-primary btl-lg">Generar pagos</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row" id="rConsumoMP" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Reporte de materia prima a consumir según fechas: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group">
                                            <h5><label>Fecha inicio:</label></h5>
                                            <input type="date" name="input" id="txtFechaCsmMP1" class="form form-control" value="" placeholder="Ingrese fecha inicio" />
                                        </div>
                                    </div>
                                    <div class="col-xs-3 col-sm-3 col" style="margin-top: 8px;">
                                        <div class="form-group" >
                                            <h5><label>Fecha fin:</label></h5>
                                            <input type="date" name="input" id="txtFechaCsmMP2" class="form form-control" value="" placeholder="Ingrese fecha fin" />
                                        </div>
                                    </div>
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <br>
                                        <button type="button" id="btnRConsumoMP" class="btn btn-primary btl-lg">Ver reporte</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        
                        <div class="row" id="ReporteVinhetaArt" name="reporte">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 space">
                                <h5>Viñeta de articulos inventario: </h5>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-12 col-sm-6 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectCodeArticulosInv">
                                            <h5><label>CodeArticulos: </label></h5>
                                            <select id="codeArticulosInv" name="Cecos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                    <div class="col-xs-12 col-sm-6 col" style="margin-top: 8px;">
                                        <div class="form-group" id="selectStatus1">
                                            <h5><label>Estado: </label></h5>
                                            <select id="selectStatus" name="Cecos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                        </div>
                                    </div>
                                </div>
                                <br>
                                <div class="form-group row">
                                    <div class="col-xs-6 col-sm-6 col-form-label">
                                        <button type="button" id="btnReporteVinhetaArt" class="btn btn-primary btl-lg" >Ver Viñeta</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </fieldset> 
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
        <script src="plugins/xlsx/xlsx.full.min.js" type="text/javascript"></script>
        <script src="plugins/js/jsInformes.js" type="text/javascript"></script>
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