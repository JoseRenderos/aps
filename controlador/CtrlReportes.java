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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.*;
import modelo.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlReportes extends HttpServlet {

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
        } catch (Exception e) {
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

                DaoEmpleado dEmp = new DaoEmpleado();
                DaoUsuario dUsuario = new DaoUsuario();
                DaoAPS_OCRC dAPS_OCRC = new DaoAPS_OCRC();
                DaoAPS_OIGE dAPS_OIGE = new DaoAPS_OIGE();
                DaoOITT dOITT = new DaoOITT();
                DaoPlanillaOracle daoPlanillaOracle = new DaoPlanillaOracle();
                DaoQuerysSQL daoQuerysSQL = new DaoQuerysSQL();
                
                
                if (request.getParameter("mostrarTodosEmpleados")!=null) {
                    try {
                        ArrayList<Empleado> Emp = new ArrayList<Empleado>();
                        Emp.addAll((Collection)dEmp.mostrar());
                        String json = new Gson().toJson(Emp);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarTodosEmpleados(): " + e.getMessage());
                    }
                }
                
                if (request.getParameter("mostrarUsuarios")!=null) {
                    try {
                        ArrayList<Usuario> us = new ArrayList<Usuario>();
                        us.addAll((Collection)dUsuario.listarUsuarios());
                        String json = new Gson().toJson(us);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarUsuarios(): " + e.getMessage());
                    }
                }

                //METODO ENVIAR LOS DATOS DE LOS CENTROS DE COSTO PARA CARGARLOS EN LOS SELECT DE CENTROS DE COSTO
                if (request.getParameter("listarCecos")!=null) {
                    try {
                        ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                        APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRC());
                        String json = new Gson().toJson(APS_OCRC);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.listarCecos(): " + e.getMessage());
                    }
                }

                if (request.getParameter("listarArticulos")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dOITT.mostrarArticulosProyeccion());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.listarArticulos(): " + e.getMessage());
                    }
                }

                if (request.getParameter("listarArticulosCosto")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dOITT.mostrarArticulosCosto());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.listarArticulosCosto(): " + e.getMessage());
                    }
                }

                if (request.getParameter("listarArticulosHistorico")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dOITT.mostrarArticulosHistorico());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.listarArticulosHistorico(): " + e.getMessage());
                    }
                }

                if (request.getParameter("listarArticulosInventario")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dOITT.mostrarArticulosInventario());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarArticulosInventario(): " + e.getMessage());
                    }
                }

                if (request.getParameter("listarStatusArt")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dOITT.mostrarStatusArt());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarStatusArt(): " + e.getMessage());
                    }
                }
                
                if (request.getParameter("ejecutarProyeccion")!=null) {
                    String resultado="";
                    try {
                        resultado=dOITT.ejecutarProyeccion(request.getParameter("codeArt"), request.getParameter("cant"));
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.ejecutarProyeccion(): " + e.getMessage());
                        resultado="CONTROLADOR:" + e.getMessage();
                    }
                    out.print(resultado);
                }

                if (request.getParameter("mostrarFechaPlanilla")!=null) {
                    try {
                        Planilla planilla;
                        planilla = daoPlanillaOracle.mostrar();
                        String json = new Gson().toJson(planilla);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarFechaPlanilla(): " + e.getMessage());
                    }
                }

                if (request.getParameter("generarPagosProdEmp")!=null) {
                    try {
                        DecimalFormat formato = new DecimalFormat("######.######");
                        
                        ArrayList<Empleado_pago> empleados = new ArrayList<Empleado_pago>();
                        ArrayList<Empleado_pago> pagos  = new ArrayList<Empleado_pago>();
                        int res=0;
                        int res1=0;
                        double pagoQuincena = 0;
                        double pagoProd = 0;
                        double pagoBono = 0;
                        empleados.addAll((Collection)daoQuerysSQL.obtenerEmpleados(request.getParameter("fecha1"), request.getParameter("fecha2")));
                        daoQuerysSQL.truncarEmpleados();
                        daoQuerysSQL.truncarPagoQuincena();
                        res=daoQuerysSQL.truncarPagoDia();
                            for (Empleado_pago empleado : empleados) {
                                pagoQuincena = 0;
                                pagoProd = 0;
                                pagoBono = 0; 
                                pagos = new ArrayList<Empleado_pago>();
                                if (empleado.getCodDepto().equals("101")) {
                                    pagos.addAll((Collection)daoQuerysSQL.obtenerPagoTorno(empleado.getCodEmp(), request.getParameter("fecha1"), request.getParameter("fecha2")));
                                } else {
                                    pagos.addAll((Collection)daoQuerysSQL.obtenerPago(empleado.getCodEmp(), request.getParameter("fecha1"), request.getParameter("fecha2")));
                                }
                                System.out.println("controlador.CtrlReportes.doPost(): " + empleado.getCodEmp());
                                for (Empleado_pago pago : pagos) {
                                    
                                    pagoQuincena += Double.parseDouble(pago.getSueldoDia());
                                    pagoProd += Double.parseDouble(pago.getValor());
                                    pagoBono += Double.parseDouble(pago.getBono());
                                    res=daoQuerysSQL.insertarPagoDia(empleado.getCodEmp(), request.getParameter("fecha1"), request.getParameter("fecha2"), pago.getFecha(),(Double.parseDouble(pago.getSueldoDia())+Double.parseDouble(pago.getBono())));
                                }
                                    daoQuerysSQL.insertarPagoQuincena(empleado.getCodEmp(), request.getParameter("fecha1"), request.getParameter("fecha2"), (pagoQuincena+pagoBono));
                                     //res=daoQuerysSQL.insertarPagoDia(empleado.getCodEmp(), request.getParameter("fecha1"), request.getParameter("fecha2"), (pagoQuincena+pagoBono));
                            }
                        out.println(res);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.generarPagosProdEmp(): " + e.getMessage());
                    }
                }
                
                if (request.getParameter("obtenerDeptoEmp")!=null) {
                    int resultado=0;
                    try {
                        resultado=dEmp.obtenerDeptoEmp(Integer.parseInt(request.getParameter("codeEmp")));
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.ejecutarProyeccion(): " + e.getMessage());
                    }
                    out.print(resultado);
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
