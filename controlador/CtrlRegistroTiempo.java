/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.*;
import com.google.gson.Gson;
import java.util.*;
import entidades.*;
import java.text.DecimalFormat;

/**
 *
 * @author Mario Valdez
 */
public class CtrlRegistroTiempo extends HttpServlet {
    Empleado Empleado;
    Usuario us;
    Actividades actividades;
    RTAV rtav;
    int r=0;

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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CtrlRegistroTiempo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CtrlRegistroTiempo at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
                DaoRegistroTiempos dRT = new DaoRegistroTiempos();
            
                //METODO ENVIAR LOS DATOS DE LOS EMPLEADOS AL SELECTOR 
                if (request.getParameter("mostrarTodosEmpleados")!=null) {
                    try {
                        ArrayList<Empleado> Emp = new ArrayList<Empleado>();
                        Emp.addAll((Collection)dEmp.mostrarTodos());
                        String json = new Gson().toJson(Emp);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarTodosEmpleados(): " + e.getMessage());
                    }
                }
            
                //METODO ENVIAR DATOS DE LOS ARTICULOS PRESUPUESTADOS AL DASHBOARD
                if (request.getParameter("mostrarActividadesRegistradas")!=null) {
                    try {
                        ArrayList<RTAV> ar= new ArrayList<RTAV>();
                        ar.addAll((Collection)dRT.mostrarActividadesRegistradas()); 
                        for (RTAV obj : ar) {
                            out.println("<tr>"+
                                        "<td>"+obj.getId_RTAV()+"</td>"+
                                        "<td>"+obj.getCodeEmp()+"</td>"+
                                        "<td>"+obj.getNomEmp()+"</td>"+
                                        "<td>"+obj.getInicio()+"</td>"+
                                        "<td>"+obj.getFin()+"</td>"+
                                        "<td>"+obj.getTiempo()+"</td>"+
                                        "<td>"+obj.getActividades().getActividad()+"</td>"+
                                        "</tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarActividadesRegistradas(): " + e.getMessage());
                    }
                }
            
                //METODO ENVIAR LAS ACTIVIDADES AL FORMULARIO
                if (request.getParameter("mostrarActividades")!=null) {
                    try {
                        ArrayList<Actividades> act = new ArrayList<Actividades>();
                        act.addAll((Collection)dEmp.mostrarActividades());
                        String json = new Gson().toJson(act);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.mostrarActividades(): " + e.getMessage());
                    }
                }
                
                if (request.getParameter("guardarActividad")!=null) {
                    try {
                        actividades = new Actividades();
                        actividades.setId(request.getParameter("codeActividad"));
                        rtav = new RTAV();
                        rtav.setCodeEmp(Integer.parseInt(request.getParameter("codeEmp")));
                        rtav.setNomEmp(request.getParameter("nomEmp"));
                        rtav.setInicio(request.getParameter("inicio"));
                        rtav.setFin(request.getParameter("fin"));
                        rtav.setActividades(actividades);
                        r= dRT.insertar(rtav,Integer.parseInt(request.getParameter("idUsuario")));
                        out.print(r);

                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.guardarActividad(): " + e.getMessage());
                    }
                }
                
                if (request.getParameter("eliminarActividad")!=null) {
                    try {
                        r= dRT.eliminar(Integer.parseInt(request.getParameter("idActividad")));
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.eliminarActividad(): " + e.getMessage());
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
