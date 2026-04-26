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
        <title>APS-ALSASA AUTORIZACION OPS</title>
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
            .modal-lg { 
                max-width: 80% !important; 
            }
            
            .modal-xls{ 
                max-width: 98% !important; 
            }
            input[type=checkbox] {
                transform: scale(1.25);
            }  
            
            .borderModal{
                border: 1px solid #000;
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
                    <a class='list-group-item list-group-item-action list-group-item-dark p-3' href='frmRecibo.jsp'><img src='img/receipt.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Carga a SAP</b></a>
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
                            int idUsuario = 0;
                            int idRol = 0;
                        if (s.getAttribute("usuario")!=null){
                            idUsuario = (Integer) s.getAttribute("idUsuario");
                            idRol = (Integer) s.getAttribute("idRol");
                            /*if ((Integer)s.getAttribute("idRol")==1 || (Integer)s.getAttribute("idUsuario")==15 || (Integer)s.getAttribute("idUsuario")==23 || (Integer)s.getAttribute("idUsuario")==43 || (Integer)s.getAttribute("idUsuario")==20 || (Integer)s.getAttribute("idUsuario")==24){
                                out.print("<a class='list-group-item list-group-item-action list-group-item-dark p-3 active' href='frmAutorizacionOps.jsp'><img src='img/gears.png' style='height: 20px; width: 20px;'/>&nbsp;&nbsp;<b>Autorización OPS</b></a>");
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
                                <center><h4 class="display-6">AUTORIZACIÓN ORDENES DE PRODUCCCIÓN</h4></center>
                                <br>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-12">
                            <div align='center'>
                                <table id="tablaOpsCosto" class="display" style="width:100%">
                                    <thead>
                                        <tr>
                                            <th>NUM ORDEN</th>
                                            <th>ITEMCODE</th>
                                            <th>ITEMNAME</th>
                                            <th>FECHA INICIO</th>
                                            <th>FECHA ULTIMO PROCESO</th>
                                            <th>CANT PLANIFICADA</th>
                                            <th>CANT COMPLETADA</th>
                                            <th>CANT RECHAZADA</th>
                                            <th>COSTO PPTO</th>
                                            <th>COSTO REAL</th>
                                            <th>VARIACION</th>
                                            <th>COMENTARIOS COSTOS</th>
                                            <th>COMENTARIOS PROD.</th>
                                            <th>INDICADOR CIERRE</th>
                                            <th>ESTADO</th>
                                        </tr>
                                    </thead>
                                    <tbody id="contenidoTablaOpsCosto">

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>

        <!--MODAL DE ORDEN-->
        <div class="modal fade" id="modalOrden" tabindex="-1" role="dialog" aria-labelledby="modalOrdenLabel" aria-hidden="true" align="center">
            <div class="modal-dialog modal-dialog-centered modal-xl" role="document">
              <div class="modal-content">
                    <div class="modal-header">
                  <h3 class="modal-title" id="modalOrdenLabel">DATOS DE ORDEN DE PRODUCCION</h3>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="btnCerrarOrden1">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid" align="left">
                        <input type="hidden" id="txtIdUsuario" value="<%=idUsuario%>">
                        <input type="hidden" id="txtIdRol" value="<%=idRol%>">
                        <input type="hidden" id="txtItemCode">
                        <center><h5>DATOS DE ORDEN</h5></center>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <p>
                                    <h6><b>NUMERO DE ORDEN SAP: </b><spam id="numOrden"></spam></h6>
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <p>
                                    <h6><b>FECHA DE DOCUMENTO: </b><spam id="fechaDoc"></spam></h6>
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <p>
                                    <h6><b>ARTICULO: </b><spam id="codeArt"></spam> - <spam id="nomArt"></spam></h6>
                                </p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                <p>
                                    <h6><b>CANTIDAD PLANIFICADA: </b><spam id="cantPln"></spam></h6>
                                </p>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                <p>
                                    <h6><b>CANTIDAD COMPLETADA: </b><spam id="cantCmplt"></spam></h6>
                                </p>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                <p>
                                    <h6><b>CANTIDAD REHAZADA: </b><spam id="cantRjct"></spam></h6>
                                </p>
                            </div>
                        </div>
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
                                    <h6><b>VARIACION: </b><spam id="variacion"></spam></h6>
                                </p>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-4 col-lg-4">
                                <div align="left"><h6><label><b>COMENTARIOS: </b></label>&nbsp;&nbsp;&nbsp;<input name="select_allCommets" value="0" id="select_allCommets" type="checkbox" />&nbsp;&nbsp;Seleccionar todos</h6></div>
                                <div class="form-group mb-3" id="selectComentario1" style="margin-top: 8px;">
                                    <select id="selectComentario" name="selectComentario" class="form-control" autocomplete="off" style="width: 100%" multiple="multiple"></select>
                                </div>
                            </div>
                            <div class="col-xs-2 col-sm-4 col-form-label">
                                <button type="button" id="btnGuardarComCostos" class="btn btn-primary btl-lg">Guardar comentario</button>
                            </div>
                        </div>
                        <hr>
                        <div class="row">
                            <center><h5>CONSUMO DE MATERIALES Y RECURSOS SAP</h5></center>
                            <table id="tablaDatosMaterialesOr" class="display" style="width:100%">
                                <thead>
                                    <tr>
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
                                        <th>COMENTARIO</th>
                                    </tr>
                                </thead>
                                <tbody id="contenidoTablaDatosMaterialesOr">

                                </tbody>
                            </table>
                        </div>
                        <hr>
                        <div class="row">
                            <center><h5>CARGA DE MATERIALES</h5></center>
                            <table id="tablaMaterialesR" class="display" style="width:100%">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>DOCNUM SAP</th>
                                        <th>FECHA </th>
                                        <th>USUARIO REPORTE</th>
                                        <th>ESTADO</th>
                                    </tr>
                                </thead>
                                <tbody id="contenidoTablaMaterialesR">

                                </tbody>
                            </table>
                        </div>
                        <hr>
                        <div class="row">
                            <center><h5>TERMINACIÓN DE REPORTE</h5></center>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <h5 align="left">CANTIDAD COMPLETADA: </h5>
                                    <input type="text" id="txtCompletada" class="form-control form-control" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" placeholder="Ingrese las unidades conformes">
                                </div>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <div class="form-group">
                                    <h5 align="left">CANTIDAD RECHAZADA: </h5>
                                    <input type="text" id="txtRechazada" class="form-control form-control" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" placeholder="Ingrese las unidades no conformes">
                                </div>
                            </div>
                        </div>
                        <p style="color: red;">*La producción no debe exceder la cantidad planificada</p>
                        <div align="right">
                            <button type="button" class="btn btn-success" id="btnGuardarCant">Guardar cantidades</button>
                            <button type="button" class="btn btn-success" id="btnCargarProduccionSap">Cargar Producción</button>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-xs-12 col-sm-12 col-md-7 col-lg-6">
                                <div align="left"><h5><label>COMENTARIO: </label></h5></div>
                                <div class="form-group mb-3" id="comentario1">
                                    <textarea id="txtComentario" class="form-control" rows="3"></textarea>
                                </div>
                                <button type="button" class="btn btn-primary" id="btnGuardarComentario">Guardar comentario</button>
                                <button type="button" class="btn btn-success" id="btnAutorizar">Autorizar</button>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-5 col-lg-6">
                                <div class="row" align="right">
                                    <div class="col-12">
                                        
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarOrden">Cerrar</button>
                </div>
              </div>
            </div>
        </div>
        <!--MODAL ARTICULOS CARGA DE MATERIALES-->
        <div class="modal fade" id="modalCargarMateriales" tabindex="-1" role="dialog" aria-labelledby="modalCargarMaterialesLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-lg" role="document">
              <div class="modal-content borderModal">
                <div class="modal-header">
                  <h3 class="modal-title" id="modalCargarMaterialesLabel">Cargar Materiales</h3>
                    <button type="button" class=" close" data-dismiss="modal" aria-label="Close" id="btnCerrarM">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body">
                    <div class="container-fluid">
                        <br>
                        <div class="row" >
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <p><h6><b>ID: </b><spam id="idENMT"></spam></h6></p>
                                <input type="hidden" id="txtEstado"/>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <p><h6><b>NUMERO SAP: </b><spam id="docNumENMT"></spam></h6></p>
                            </div>
                        </div>
                        <div class="row" >
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <p><h6><b>FECHA: </b><spam id="fechaENMT"></spam></h6></p>
                            </div>
                            <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                <p><h6><b>USUARIO: </b><spam id="usuarioENMT"></spam></h6></p>
                            </div>
                        </div>
                        <hr>
                        <div class="row" >
                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                <table id="tablaArt" class="display" width="100%">
                                    <thead>
                                       <tr>
                                          <th>COD ARTICULO</th> 
                                          <th>DESCRIPCION</th>
                                          <th>BOGEGA CONSUMO</th>
                                          <th>STOCK BODEGA</th>
                                          <th>CANTIDAD</th>
                                       </tr>
                                    </thead>
                                    <tbody id="contenidoTablaArt">

                                    </tbody>
                                 </table>
                            </div>
                        </div>
                            <!--<div class="row" id="divMateriales">

                            </div>
                            <br>
                            <div align="right">
                                <button type="button" class="btn btn-primary" data-dismiss="modal" id="btnGuardarCambiosM">Guardar</button>
                            </div>-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-success" id="btnCargarMaterialesSap" >Cargar Materiales</button>
                    <button type="button" class="btn btn-danger"  id="btnCancelarMaterialesSap" >Cancelar entrega</button>
                    <!--<button type="button" class="btn btn-warning" data-dismiss="modal" id="btnEditarM">Editar</button>--> 
                    <button type="button" class="btn btn-secondary" data-dismiss="modal" id="btnCerrarM1">Cerrar</button>
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
        <script src="plugins/js/jsAutorizacionOps.js" type="text/javascript"></script>
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

 