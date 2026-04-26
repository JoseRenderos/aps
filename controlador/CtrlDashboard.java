/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.Gson;
import entidades.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 
import modelo.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlDashboard extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DaoOWOR dOWOR= new DaoOWOR();
            DaoGraficas dGraficas = new DaoGraficas();
            DaoAPS_OCRC dOcrc = new DaoAPS_OCRC();
            //CODIGO PARA ENVIAR CONTENIDO DE LA tarjeta de cantidad de ordenes de produccion
            if (request.getParameter("cantOrdenes")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    int r=0;
                    if (idRol==2) {
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dOWOR.cantOrdenes(idUsuario, idRol));
                        for (OWOR or : Orden) {
                            r++;
                        }
                    } else {
                         //Integer.parseInt(request.getParameter("condicion"))
                        r=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    out.print(r+" / 100%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cantOrdenesP")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    int r=0;
                    int total=0;
                    if (idRol==2) {
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dOWOR.cantOrdenesP(idUsuario, idRol));
                        for (OWOR or : Orden) {
                            r++;
                        }
                    } else {
                        r=dOWOR.contarOrdenesP(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    if (idRol==2) {
                        ArrayList<OWOR> Orden1= new ArrayList<OWOR>();
                        Orden1.addAll((Collection)dOWOR.cantOrdenes(idUsuario, idRol));
                        for (OWOR or : Orden1) {
                            total++;
                        }
                    } else {
                        total=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    out.print(r+" / "+formato.format((Double.parseDouble(r+"")/Double.parseDouble(total+""))*100)+"%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cantOrdenesL")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    int r=0;
                    int total=0;
                    if (idRol==2) {
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dOWOR.cantOrdenesL(idUsuario, idRol));
                        for (OWOR or : Orden) {
                            r++;
                        }
                    } else {
                        r=dOWOR.contarOrdenesL(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    if (idRol==2) {
                        ArrayList<OWOR> Orden1= new ArrayList<OWOR>();
                        Orden1.addAll((Collection)dOWOR.cantOrdenes(idUsuario, idRol));
                        for (OWOR or : Orden1) {
                            total++;
                        }
                    } else {
                        total=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    out.print(r+" / "+formato.format((Double.parseDouble(r+"")/Double.parseDouble(total+""))*100)+"%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cantOrdenesC")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    int r=0;
                    int total=0;
                    if (idRol==2) {
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dOWOR.cantOrdenesC(idUsuario, idRol));
                        for (OWOR or : Orden) {
                            r++;
                        }
                    } else {
                        r=dOWOR.contarOrdenesC(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    if (idRol==2) {
                        ArrayList<OWOR> Orden1= new ArrayList<OWOR>();
                        Orden1.addAll((Collection)dOWOR.cantOrdenes(idUsuario, idRol));
                        for (OWOR or : Orden1) {
                            total++;
                        }
                    } else {
                        total=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), 1);
                    }
                    out.print(r+" / "+formato.format((Double.parseDouble(r+"")/Double.parseDouble(total+""))*100)+"%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("obtenerSeries")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    int cant=0;
                    int cantL=0;
                    int cantP=0;
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dOWOR.cantOrdenes(idUsuario, idRol));
                    ArrayList<OWOR> OrdenL= new ArrayList<OWOR>();
                    OrdenL.addAll((Collection)dOWOR.cantOrdenesL(idUsuario, idRol));
                    ArrayList<OWOR> OrdenP= new ArrayList<OWOR>();
                    OrdenP.addAll((Collection)dOWOR.cantOrdenesP(idUsuario, idRol));
                    for (OWOR or : Orden) {
                        cant++;
                    }
                    for (OWOR orL : OrdenL) {
                        cantL++;
                    }
                    for (OWOR orP : OrdenP) {
                        cantP++;
                    }
                    String series="{\"total\":\""+cant+"\",\"liberadas\":\""+cantL+"\",\"planificadas\":\""+cantP+"\"}";
                    String json = new Gson().toJson(series);
                    out.print(json);

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("contarOrdenesMes")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.contarOrdenes());
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("contarOrdenesCecos")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.contarOrdenesCecos(request.getParameter("cecos")));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarContarOrdenesCecos")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.filtrarContarOrdenesCecos(request.getParameter("fechaI"),request.getParameter("fechaF"),request.getParameter("cecos")));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesPorDia")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesPorDia());
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesPorCecos")!=null) {
                try {
                    ArrayList<CantCecos> ar= new ArrayList<CantCecos>();
                    ar.addAll((Collection)dGraficas.mostrarOrdenesPorCecos());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesTorno")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("101"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesPulido")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("102"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesRemachado")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("103"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesSellado")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("104"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesPrensas")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("105"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesFundicion")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("106"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesTorno_auto")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("108"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesEmbuticion")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("109"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesRemachado_capelli")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("110"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesCocinetas")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("111"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesEmpaque_planta")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("114"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesLinea_prod_1")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("126"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOrdenesLinea_prod_2")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.mostrarOrdenesCecos("127"));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("listarCecos")!=null) {
                try {
                    ArrayList<APS_OCRC> arr= new ArrayList<APS_OCRC>();
                    arr.addAll((Collection)dOcrc.listarAPS_OCRCUser(Integer.parseInt(request.getParameter("idUsuario")), 0));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarHorasTrabajoMesActual")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    ArrayList<Horas> arr= new ArrayList<Horas>();
                    arr.addAll((Collection)dGraficas.mostrarHorasTrabajoMes(idUsuario,idRol));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarHorasTrabajoDetalleMesActual")!=null) {
                try {
                    ArrayList<Horas> arr= new ArrayList<Horas>();
                    arr.addAll((Collection)dGraficas.mostrarHorasTrabajoDetalleMes());
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarHorasTrabajo")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    ArrayList<Horas> arr= new ArrayList<Horas>();
                    arr.addAll((Collection)dGraficas.mostrarHorasTrabajo(idUsuario,idRol));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarHorasTrabajoFecha")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    ArrayList<Horas> arr= new ArrayList<Horas>();
                    arr.addAll((Collection)dGraficas.mostrarHorasTrabajoFecha(idUsuario,idRol,request.getParameter("fecha"),Integer.parseInt(request.getParameter("mayor"))));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("conteoArticulos")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.conteoArticulos(request.getParameter("cecos")));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("conteoArticulosPlanta")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.conteoArticulosPlanta());
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("avanceMes")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.avanceMes());
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("produccionGrupos")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.produccionGrupos(request.getParameter("grupo")));
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("produccionGruposDia")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.produccionGruposDia());
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("produccionGruposMes")!=null) {
                try {
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dGraficas.produccionGruposMes());
                    String json= new Gson().toJson(Orden);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoPlanta")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoPlanta());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoPlantaFecha")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoPlantaFecha());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCumplimientoPlanta")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.filtrarCumplimientoPlanta(request.getParameter("fechaI"), request.getParameter("fechaF")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoPlantaArticulos")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoPlantaArticulos());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoPlantaFechaArticulos")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoPlantaFechaArticulos());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCumplimientoPlantaArticulos")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.filtrarCumplimientoPlantaArticulos(request.getParameter("fechaI"), request.getParameter("fechaF")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("horasDelMes")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.horasDelMes());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("horasDelMesFechaActual")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.horasDelMesFechaActual());
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarHorasDelMes")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.filtrarHorasDelMes(request.getParameter("fechaI"), request.getParameter("fechaF")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCantOrdenes")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    int r=0;
                    int total=0;
                        r=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                    out.print(r+" / 100%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCantOrdenesP")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    int r=0;
                    int total=0;
                        r=dOWOR.contarOrdenesP(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                        total=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                    out.print(r+" / "+formato.format((Double.parseDouble(r+"")/Double.parseDouble(total+""))*100)+"%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCantOrdenesL")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    int r=0;
                    int total=0;
                        r=dOWOR.contarOrdenesL(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                        total=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                    out.print(r+" / "+formato.format((Double.parseDouble(r+"")/Double.parseDouble(total+""))*100)+"%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCantOrdenesC")!=null) {
                try {
                    DecimalFormat formato = new DecimalFormat("######");
                    int r=0;
                    int total=0;
                        r=dOWOR.contarOrdenesC(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                        total=dOWOR.contarOrdenes(request.getParameter("fechaI"), request.getParameter("fechaF"), Integer.parseInt(request.getParameter("condicion")));
                    out.print(r+" / "+formato.format((Double.parseDouble(r+"")/Double.parseDouble(total+""))*100)+"%");

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoCecos")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoCecos(request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoCecosFechaActual")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoCecosFechaActual(request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoCecosArticulos")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoCecosArticulos(request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("cumplimientoCecosArticulosFechaActual")!=null) {
                try {
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.cumplimientoCecosArticulosFechaActual(request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            
            if (request.getParameter("horasDelMesCecos")!=null) {
                try { 
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.horasDelMesCecos(request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("horasDelMesCecosFechaActual")!=null) {
                try { 
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.horasDelMesCecosFechaActual(request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCumplimientoCecos")!=null) {
                try { 
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.filtrarCumplimientoCecos(request.getParameter("fechaI"),request.getParameter("fechaF"),request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarCumplimientoCecosArticulos")!=null) {
                try { 
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.filtrarCumplimientoCecosArticulos(request.getParameter("fechaI"),request.getParameter("fechaF"),request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("filtrarHorasDelMesCecos")!=null) {
                try { 
                    ArrayList<OWOR> ar= new ArrayList<OWOR>();
                    ar.addAll((Collection)dOWOR.filtrarHorasDelMesCecos(request.getParameter("fechaI"),request.getParameter("fechaF"),request.getParameter("cecos")));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("contarOpsMayor_cincoPorciento")!=null) {
                try {
                    int r=0;
                        r=dOWOR.contarOpsMayor_cincoPorciento();
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.contarOpsMayor_cincoPorciento(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("contarOpsMenor_cincoPorciento")!=null) {
                try {
                    int r=0;
                        r=dOWOR.contarOpsMenor_cincoPorciento();
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.contarOpsMenor_cincoPorciento(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOPS_costoMayor_cincoPorciento")!=null) {
                try {
                    String tabla="";
                    ArrayList<Costo_OP> costo_OPs = new ArrayList<Costo_OP>();
                    costo_OPs.addAll((Collection)dOWOR.mostrarOPS_costoMayor_cincoPorciento());
                    for (Costo_OP obj : costo_OPs) {

                        tabla+="<tr>"+
                                  "<td>"+obj.getDocnum()+"</td>"+
                                  "<td>"+obj.getItemCode_Orden()+"</td>"+
                                  "<td>"+obj.getItemname_Orden()+"</td>"+
                                  "<td>"+obj.getStartDate()+"</td>"+
                                  "<td>"+obj.getCloseDate()+"</td>"+
                                  "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                  "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                  "<td>"+obj.getRjctQty_Orden()+"</td>"+
                                  "<td>"+obj.getCosto_Pry()+"</td>"+
                                  "<td>"+obj.getCosto_Uni()+"</td>"+
                                  "<td>"+obj.getVariacion()+"%</td>"+
                                "</tr>";
                    }
                    out.print(tabla);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.mostrarOPS_costoMayor_cincoPorciento(): " + e.getMessage());
                }
            } 
            
            if (request.getParameter("mostrarOPS_costoMenor_cincoPorciento")!=null) {
                try {
                    String tabla="";
                    ArrayList<Costo_OP> costo_OPs = new ArrayList<Costo_OP>();
                    costo_OPs.addAll((Collection)dOWOR.mostrarOPS_costoMenor_cincoPorciento());
                    for (Costo_OP obj : costo_OPs) {

                        tabla+="<tr>"+
                                  "<td>"+obj.getDocnum()+"</td>"+
                                  "<td>"+obj.getItemCode_Orden()+"</td>"+
                                  "<td>"+obj.getItemname_Orden()+"</td>"+
                                  "<td>"+obj.getStartDate()+"</td>"+
                                  "<td>"+obj.getCloseDate()+"</td>"+
                                  "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                  "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                  "<td>"+obj.getRjctQty_Orden()+"</td>"+
                                  "<td>"+obj.getCosto_Pry()+"</td>"+
                                  "<td>"+obj.getCosto_Uni()+"</td>"+
                                  "<td>"+obj.getVariacion()+"%</td>"+
                                "</tr>";
                    }
                    out.print(tabla);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.mostrarOPS_costoMenor_cincoPorciento(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("contarOpsMayor_cincoPorciento_Mes")!=null) {
                try {
                    int r=0;
                        r=dOWOR.contarOpsMayor_cincoPorciento_Mes();
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.contarOpsMayor_cincoPorciento_Mes(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("contarOpsMenor_cincoPorciento_Mes")!=null) {
                try {
                    int r=0;
                        r=dOWOR.contarOpsMenor_cincoPorciento_Mes();
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.contarOpsMenor_cincoPorciento_Mes(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOPS_costoMayor_cincoPorciento_Mes")!=null) {
                try {
                    String tabla="";
                    ArrayList<Costo_OP> costo_OPs = new ArrayList<Costo_OP>();
                    costo_OPs.addAll((Collection)dOWOR.mostrarOPS_costoMayor_cincoPorciento_Mes());
                    for (Costo_OP obj : costo_OPs) {

                        tabla+="<tr>"+
                                  "<td>"+obj.getDocnum()+"</td>"+
                                  "<td>"+obj.getItemCode_Orden()+"</td>"+
                                  "<td>"+obj.getItemname_Orden()+"</td>"+
                                  "<td>"+obj.getStartDate()+"</td>"+
                                  "<td>"+obj.getCloseDate()+"</td>"+
                                  "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                  "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                  "<td>"+obj.getRjctQty_Orden()+"</td>"+
                                  "<td>"+obj.getCosto_Pry()+"</td>"+
                                  "<td>"+obj.getCosto_Uni()+"</td>"+
                                  "<td>"+obj.getVariacion()+"%</td>"+
                                "</tr>";
                    }
                    out.print(tabla);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.mostrarOPS_costoMayor_cincoPorciento_Mes(): " + e.getMessage());
                }
            } 
            
            if (request.getParameter("mostrarOPS_costoMenor_cincoPorciento_Mes")!=null) {
                try {
                    String tabla="";
                    ArrayList<Costo_OP> costo_OPs = new ArrayList<Costo_OP>();
                    costo_OPs.addAll((Collection)dOWOR.mostrarOPS_costoMenor_cincoPorciento_Mes());
                    for (Costo_OP obj : costo_OPs) {

                        tabla+="<tr>"+
                                  "<td>"+obj.getDocnum()+"</td>"+
                                  "<td>"+obj.getItemCode_Orden()+"</td>"+
                                  "<td>"+obj.getItemname_Orden()+"</td>"+
                                  "<td>"+obj.getStartDate()+"</td>"+
                                  "<td>"+obj.getCloseDate()+"</td>"+
                                  "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                  "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                  "<td>"+obj.getRjctQty_Orden()+"</td>"+
                                  "<td>"+obj.getCosto_Pry()+"</td>"+
                                  "<td>"+obj.getCosto_Uni()+"</td>"+
                                  "<td>"+obj.getVariacion()+"%</td>"+
                                "</tr>";
                    }
                    out.print(tabla);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.mostrarOPS_costoMenor_cincoPorciento_Mes(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("contarOpsMayor_cincoPorciento_Filtro")!=null) {
                try {
                    int r=0;
                        r=dOWOR.contarOpsMayor_cincoPorciento_Filtro(request.getParameter("fecha1"),request.getParameter("fecha2"));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.contarOpsMayor_cincoPorciento_Filtro(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("contarOpsMenor_cincoPorciento_Filtro")!=null) {
                try {
                    int r=0;
                        r=dOWOR.contarOpsMenor_cincoPorciento_Filtro(request.getParameter("fecha1"),request.getParameter("fecha2"));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.contarOpsMenor_cincoPorciento_Filtro(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarOPS_costoMayor_cincoPorciento_Filtro")!=null) {
                try {
                    String tabla="";
                    ArrayList<Costo_OP> costo_OPs = new ArrayList<Costo_OP>();
                    costo_OPs.addAll((Collection)dOWOR.mostrarOPS_costoMayor_cincoPorciento_Filtro(request.getParameter("fecha1"),request.getParameter("fecha2")));
                    for (Costo_OP obj : costo_OPs) {

                        tabla+="<tr>"+
                                  "<td>"+obj.getDocnum()+"</td>"+
                                  "<td>"+obj.getItemCode_Orden()+"</td>"+
                                  "<td>"+obj.getItemname_Orden()+"</td>"+
                                  "<td>"+obj.getStartDate()+"</td>"+
                                  "<td>"+obj.getCloseDate()+"</td>"+
                                  "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                  "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                  "<td>"+obj.getRjctQty_Orden()+"</td>"+
                                  "<td>"+obj.getCosto_Pry()+"</td>"+
                                  "<td>"+obj.getCosto_Uni()+"</td>"+
                                  "<td>"+obj.getVariacion()+"%</td>"+
                                "</tr>";
                    }
                    out.print(tabla);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.mostrarOPS_costoMayor_cincoPorciento_Filtro(): " + e.getMessage());
                }
            } 
            
            if (request.getParameter("mostrarOPS_costoMenor_cincoPorciento_Filtro")!=null) {
                try {
                    String tabla="";
                    ArrayList<Costo_OP> costo_OPs = new ArrayList<Costo_OP>();
                    costo_OPs.addAll((Collection)dOWOR.mostrarOPS_costoMenor_cincoPorciento_Filtro(request.getParameter("fecha1"),request.getParameter("fecha2")));
                    for (Costo_OP obj : costo_OPs) {

                        tabla+="<tr>"+
                                  "<td>"+obj.getDocnum()+"</td>"+
                                  "<td>"+obj.getItemCode_Orden()+"</td>"+
                                  "<td>"+obj.getItemname_Orden()+"</td>"+
                                  "<td>"+obj.getStartDate()+"</td>"+
                                  "<td>"+obj.getCloseDate()+"</td>"+
                                  "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                  "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                  "<td>"+obj.getRjctQty_Orden()+"</td>"+
                                  "<td>"+obj.getCosto_Pry()+"</td>"+
                                  "<td>"+obj.getCosto_Uni()+"</td>"+
                                  "<td>"+obj.getVariacion()+"%</td>"+
                                "</tr>";
                    }
                    out.print(tabla);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlDashboard.mostrarOPS_costoMenor_cincoPorciento_Filtro(): " + e.getMessage());
                }
            }
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

