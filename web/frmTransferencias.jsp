<%-- 
    Document   : frmDeptoEmp
    Created on : 02-07-2023, 04:02:50 PM
    Author     : Mario Valdez
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
        <title>APS-ALSASA TRANSFERENCIA STOCK</title>
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
                    <a class="list-group-item list-group-item-action list-group-item-dark p-3" href="frmCerradas.jsp"><img src="img/closed.png" style="height: 20px; width: 20px;"/>&nbsp;&nbsp;<b>Cerradas</b></a>
                    <ul class="navbar-nav me-auto list-group-item list-group-item-action list-group-item-dark p-3 active">
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
                        
                    <input type="hidden" id="txtIdRol" value="<%=idRol%>">
                    <input type="hidden" id="txtIdUsuario" value="<%=idUsuario%>">
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
                                <center><h4 class="display-6">TRANSFERENCIAS DE STOCK</h4></center>
                                <br>
                                <div class="row">
                                    <input type="text" id="txtPrecio" class="form-control" disabled="true" hidden>
                                    <div class="row" align="left">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div align="left"><h5><label>ARTICULOS </label></h5></div>
                                            <div class="form-group mb-3" id="codeArticulos1">
                                                <select id="codeArticulos" name="codeArticulos" class="form-control" autocomplete="off" style="width: 100%"></select>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div align="left"><h5><label>FECHA TRANSFERENCIA:</label></h5></div>
                                            <div class="form-group mb-3" id="txtFecha1">
                                                <input type="date" id="txtFecha" class="form-control" >
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div align="left"><h5><label>CODIGO DE ARTICULO:</label></h5></div>
                                            <div class="form-group mb-3" id="txtCodeArticulo1">
                                                <input type="text" id="txtCodeArticulo" class="form-control" disabled="true">
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div align="left"><h5><label>DESCRIPCION:</label></h5></div>
                                            <div class="form-group mb-3" id="txtDescArticulo1">
                                                <input type="text" id="txtDescArticulo" class="form-control" disabled="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-3">
                                            <div align="left"><h5><label>BODEGA ORIGEN: </label></h5></div>
                                            <div class="form-group mb-3" id="bodegaOrigen1">
                                                <select id="bodegaOrigen" name="bodegaOrigen" class="form-control" autocomplete="off" style="width: 100%">
                                                    <option value="B002">B002</option>
                                                    <option value="B003">B003</option>
                                                    <option value="B004">B004</option>
                                                    <option value="B108">B101</option>
                                                    <option value="B108">B108</option>
                                                    <option value="B111">B111</option>
                                                    <option value="B126">B126</option>
                                                    <option value="B912">B912</option>
                                                    <%
                                                        if (s.getAttribute("usuario")!=null){
                                                            idUsuario=(Integer)s.getAttribute("idUsuario");
                                                            idRol=(Integer)s.getAttribute("idRol");
                                                            if (idRol==1 || idUsuario==13) {
                                                                %>
                                                                 <option value="B005">B005</option>
                                                                <%
                                                            }
                                                        }
                                                    %>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-3">
                                            <div align="left"><h5><label>STOCK ORIGEN:</label></h5></div>
                                            <div class="form-group mb-3" id="txtStockOrigen1">
                                                <input type="text" id="txtStockOrigen" class="form-control" disabled="true">
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-3">
                                            <div align="left"><h5><label>BODEGA DESTINO: </label></h5></div>
                                            <div class="form-group mb-3" id="bodegaDestino1">
                                                <select id="bodegaDestino" name="bodegaDestino" class="form-control" autocomplete="off" style="width: 100%">
                                                    <option value="B001">B001</option>
                                                    <option value="B002">B002</option>
                                                    <option value="B003">B003</option>
                                                    <option value="B004">B004</option>
                                                    <option value="B005">B005</option>
                                                    <option value="B108">B101</option>
                                                    <option value="B108">B108</option>
                                                    <option value="B111">B111</option>
                                                    <option value="B126">B126</option>
                                                    <option value="B912">B912</option>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-4 col-lg-3">
                                            <div align="left"><h5><label>STOCK DESTINO</label></h5></div>
                                            <div class="form-group mb-3" id="txtStockDestino1">
                                                <input type="text" id="txtStockDestino" class="form-control" disabled="true">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div align="left"><h5><label>CANT TRASLADO:</label></h5></div>
                                            <div class="form-group mb-3" id="txtCantSolici1">
                                                <input type="text" id="txtCantSolici" class="form-control" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" placeholder="Ingrese cantidad a trasladar" autocomplete="off">
                                            </div>
                                        </div>
                                        <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                                            <div align="left"><h5><label>NUMERO ORDEN:</label></h5></div>
                                            <div class="form-group mb-3" id="txtNumOrden1">
                                                <input type="text" id="txtNumOrden" class="form-control" onkeypress="return soloNumeros(event)" onkeyup="return numberMobile(event)" placeholder="Ingrese el numero de op" autocomplete="off">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                            <div align="right">
                                                <button type="button" class="btn btn-lg btn-primary" id="btnAgregarArticulo"><img src="img/plus.png" width="25px" style="padding-bottom: 4px">&nbsp;Agregar</button>
                                                <br>
                                                <hr style="background-color: #fff;">
                                                <!-- TABLA DE ARTICULOS DE ORDEN DE VENTA -->
                                                <table id="tablaArticulos" class="display" style="width:100%">
                                                    <thead>
                                                        <tr>
                                                            <th>COD ARTICULO</th>
                                                            <th>DESCRIPCION</th>
                                                            <th>BODEGA ORIGEN</th>
                                                            <th>STOCK ORIGEN</th>
                                                            <th>BODEGA DESTINO</th>
                                                            <th>STOCK DESTINO</th>
                                                            <th>CANTIDAD TRASLADO</th>
                                                            <th>NUM ORDEN</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody id="contenidoTablaArticulos">

                                                    </tbody>
                                                </table>
                                                <div align="right">
                                                    <button type="button" class="btn btn-danger" id="btnBorrarArticulo"><img src="img/borrar.png" width="25px" style="padding-bottom: 4px"></button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                <br>
                                <div class="row">
                                    <div class="col-xs-12 col-sm-12 col-md-5 col-lg-4">
                                        <div class="row">
                                            <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                                                <div align="left"><h5><label>COMENTARIO: </label></h5></div>
                                                <div class="form-group mb-3" id="comentario1">
                                                    <textarea id="txtComentario" maxlength="237" class="form-control" rows="4"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                     </div>
                                </div>
                                <br>
                                <div align="right">
                                    <button type="submit" class="btn btn-lg btn-success" id="btnGuardarTranferencia" form="fromOrdenVenta"><img src="img/save.png" width="25px" style="padding-bottom: 4px">&nbsp;Guardar</button>
                                </div>
                            </div>
                        </div>
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
        <script src="plugins/js/tooltip.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/select2.full.min.js" type="text/javascript"></script>
        <script src="plugins/select/js/i18n/es.js" type="text/javascript"></script>
        <script src="plugins/js/jsTransferencia.js" type="text/javascript"></script>
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