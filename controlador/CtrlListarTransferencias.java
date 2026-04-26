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
public class CtrlListarTransferencias extends HttpServlet {

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
            DaoOWTR daoOWTR = new DaoOWTR();
            DaoSAP daoSAP = new DaoSAP();
                System.out.println("doPost_mostrarTransferencias: "+request.getParameter("mostrarTransferencias"));
            if (request.getParameter("mostrarTransferencias")!=null) {                
                try {
                    ArrayList<APS_OWTR> owtr= new ArrayList<APS_OWTR>();
                    owtr.addAll((Collection)daoOWTR.mostrarTrasferencias());
                    String estado="";
                    for (APS_OWTR obj : owtr) {
                        out.println("<tr>"+ 
                                        "<td>"+obj.getIdAPS_OWTR()+"</td>"+
                                        "<td>"+obj.getDocNum()+"</td>"+
                                        "<td>"+obj.getDocDate()+"</td>"+
                                        "<td>"+obj.getFiller()+"</td>"+
                                        "<td>"+obj.getToWhscode()+"</td>"+
                                        "<td>"+obj.getComments()+"</td>"+
                                        "<td>"+obj.getEstado()+"</td>"+
                                    "</tr>");
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlListarTransferencias.mostrarTransferencias(): "+e.getMessage());
                }
            }
            if (request.getParameter("mostrarLineasTransferencia")!=null) {                
                try {
                    ArrayList<APS_WTR1> wtr1= new ArrayList<APS_WTR1>();
                    wtr1.addAll((Collection)daoOWTR.mostrarLineasTrasferencia(Integer.parseInt(request.getParameter("id"))));
                    String estado="";
                    for (APS_WTR1 obj : wtr1) {
                        out.println("<tr>"+ 
                                        "<td>"+obj.getIdAPS_WTR1()+"</td>"+
                                        "<td>"+obj.getItemCode()+"</td>"+
                                        "<td>"+obj.getItemName()+"</td>"+
                                        "<td>"+obj.getFromWhsCod()+"</td>"+
                                        "<td>"+obj.getStockOrigen()+"</td>"+
                                        "<td>"+obj.getWhsCode()+"</td>"+
                                        "<td>"+obj.getStockDestino()+"</td>"+
                                        "<td>"+obj.getQuantity()+"</td>"+
                                        "<td>"+obj.getOrdenProduccion()+"</td>"+
                                    "</tr>");
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlListarTransferencias.mostrarLineasTransferencia(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("cargarTransferencia")!=null) {
                try {
                    String r="";
                    r=daoSAP.addTransferencia(Integer.parseInt(request.getParameter("id")));
                    out.print(r);

                } catch (Exception e) {
                    out.print(e.getMessage());
                    System.out.println("controlador.CtrlListarTransferencias.cargarTransferencia(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("cancelarTransferencia")!=null) {
                try {
                    int r=0;
                    r=daoOWTR.cancelarTransferencia(Integer.parseInt(request.getParameter("id")));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.cancelarTransferencia(): " + e.getMessage());
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
