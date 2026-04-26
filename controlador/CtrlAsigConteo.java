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
import modelo.SAP.DaoSAP;
import java.util.*;
import entidades.*;
import java.sql.*;
import com.google.gson.*;

/**
 *
 * @author Mario Valdez
 */
public class CtrlAsigConteo extends HttpServlet {

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
            out.println("<title>Servlet CtrlAsigConteo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CtrlAsigConteo at " + request.getContextPath() + "</h1>");
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
            DaoOWTR daoOWTR = new DaoOWTR();
            DaoSAP sap = new DaoSAP();
            DaoAPS_OWOR dAps_owor = new DaoAPS_OWOR();
            DaoAPS_IGN1 dAps_ign1 = new DaoAPS_IGN1();
            DaoCINV daoCINV = new DaoCINV();
            
            if (request.getParameter("listarArticulos")!=null) {
                try {
                    ArrayList<Articulo> articulos = new ArrayList<Articulo>();
                    articulos.addAll((Collection)daoOWTR.mostrarArticulosCINV());
                    String json = new Gson().toJson(articulos);
                    out.print(json);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlTransferencias.listarArticulos(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("guardarCINV")!=null) {
                try {
                    int respuesta=0;
                    respuesta=daoCINV.guardarCINV(
                        request.getParameter("itemCode"),
                        request.getParameter("Comments"),
                        Integer.parseInt(request.getParameter("idUsuario")),
                        request.getParameter("fecha"),
                        request.getParameter("bodega")
                    );
                    out.print(respuesta);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
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
