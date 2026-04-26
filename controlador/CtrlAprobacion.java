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
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import modelo.*;
import modelo.SAP.DaoSAP;
import com.google.gson.Gson;
import java.util.*;
import entidades.*;
import java.text.DecimalFormat;

/**
 *
 * @author Mario Valdez
 */
public class CtrlAprobacion extends HttpServlet {
    OWOR or;
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
                DaoAPS_OWOR dAPSO= new DaoAPS_OWOR();
                       
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ORDENES DE FABROCACION
               if (request.getParameter("mostrar")!=null) {
                    try {
                        HttpSession s = request.getSession();
                        int conta=1;
                        String CantPln;
                        String Cantcmp;
                        String CantRjc;
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dAPSO.mostrarOrdenesSolicitudEdicion());
                        DecimalFormat formato = new DecimalFormat("######.###");
                        for (OWOR or : Orden) {
                          CantPln =formato.format(Double.parseDouble(or.getCantpln()));
                          Cantcmp = formato.format(Double.parseDouble(or.getCantcmp()));
                          CantRjc = formato.format(Double.parseDouble(or.getCantrjc()));
                          out.println("<tr><td>"+or.getDocnum()+"</td>"+
                                      "<td>"+or.getStartdate()+"</td>"+
                                      "<td>"+or.getDuedate()+"</td>"+
                                      "<td>"+or.getItemcode()+"</td>"+
                                      "<td>"+or.getItemname()+"</td>"+
                                      "<td>"+CantPln+"</td>"+
                                      "<td>"+Cantcmp+"</td>"+
                                      "<td>"+CantRjc+"</td>"+
                                      "<td>"+or.getComments()+"</td>"+
                                      "<td> <button type='button' class='btn btn-success btnAprobar' id='btnAprobar"+conta+"'><img src='img/aprobar.png' style='height: 20px; width: 20px;'></button>&nbsp;<button type='button' class='btn btn-danger btnRechazar' id='btnRechazar"+conta+"'><img src='img/rechazar.png' style='height: 20px; width: 20px;'></button></td></tr>");
                          conta++;
                        } 
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
                
                //Metodo para aprobar edicion en de tiempo de actividades
                if (request.getParameter("AprobarSolicitudEdicion")!=null) {
                    try {
                        r= dAPSO.AprobarSolicitudEdicion(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(r);
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
                
                //Metodo para rechazar edicion en de tiempo de actividades
                if (request.getParameter("RechazarSolicitudEdicion")!=null) {
                    try {
                        r= dAPSO.RechazarSolicitudEdicion(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(r);
                    } catch (Exception e) {
                        out.print(e.getMessage());
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
