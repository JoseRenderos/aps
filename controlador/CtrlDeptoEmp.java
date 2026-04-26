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
public class CtrlDeptoEmp extends HttpServlet {
    Empleado Empleado;
    Usuario us;
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
            
                //METODO ENVIAR LOS DATOS DE LOS EMPLEADOS AL SELECTOR 
                if (request.getParameter("mostrarTodosEmpleados")!=null) {
                    try {
                        ArrayList<Empleado> Emp = new ArrayList<Empleado>();
                        Emp.addAll((Collection)dEmp.mostrarTodos());
                        String json = new Gson().toJson(Emp);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            
                //METODO ENVIAR LOS DATOS DE LOS DEPARTAMENTOS AL SELECTOR 
                if (request.getParameter("mostrarDepartamentos")!=null) {
                    try {
                        ArrayList<Departamento> depto = new ArrayList<Departamento>();
                        depto.addAll((Collection)dEmp.mostrarDeptos());
                        String json = new Gson().toJson(depto);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                if (request.getParameter("guardarAsignacionDepto")!=null) {
                    try {
                        Empleado= new Empleado();
                        Empleado.setCodigo(Integer.parseInt(request.getParameter("codeEmp")));
                        Empleado.setNombre(request.getParameter("nomEmp"));
                        Empleado.setCodDepto(Integer.parseInt(request.getParameter("codeDepto")));
                        Empleado.setDepto(request.getParameter("depto"));
                        Empleado.setFecha(request.getParameter("fecha"));
                        r= dEmp.insertar(Empleado,Integer.parseInt(request.getParameter("idUsuario")));
                        out.print(r);

                    } catch (Exception e) {
                        System.out.println(e.getMessage());
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
