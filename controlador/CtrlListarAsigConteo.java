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
import modelo.*;
import modelo.SAP.DaoSAP;
import java.util.*;

/**
 *
 * @author Mario Valdez
 */
public class CtrlListarAsigConteo extends HttpServlet {

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
            out.println("<title>Servlet CtrlListarAsigConteo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CtrlListarAsigConteo at " + request.getContextPath() + "</h1>");
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
            DaoSAP daoSAP = new DaoSAP();
            DaoCINV daoCINV = new DaoCINV();
            if (request.getParameter("mostrarAsigConteo")!=null) {                
                try {
                    ArrayList<CINV> CINV= new ArrayList<CINV>();
                    CINV.addAll((Collection)daoCINV.mostrar());
                    String estado="";
                    for (CINV obj : CINV) {
                        out.println("<tr>"+ 
                                        "<td>"+obj.getId_CINV()+"</td>"+
                                        "<td>"+obj.getDocDate()+"</td>"+
                                        "<td>"+obj.getComentario()+"</td>"+
                                        "<td>"+obj.getUsuario()+"</td>"+
                                        "<td>"+obj.getBodega()+"</td>"+
                                        "<td>"+obj.getEstado()+"</td>"+
                                    "</tr>");
                    } 
                } catch (Exception e) {
                     System.out.println("controlador.CtrlListarAsigConteo.mostrarAsigConteo(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarLineasAsigConteo")!=null) {                
                try {
                    int conta=1;
                    ArrayList<CINV1> CINV1= new ArrayList<CINV1>();
                    CINV1.addAll((Collection)daoCINV.mostrarLineas(Integer.parseInt(request.getParameter("id"))));
                    for (CINV1 obj : CINV1) {
                        out.println("<tr>"+
                                    "<td><input type='checkbox' name='id[]' value='"+conta+"' class='selectArt'><p id='itemCode"+conta+"' hidden='true'>"+obj.getItemcode()+"</p><p id='idLinea"+conta+"' hidden='true'>"+obj.getId()+"</p><p id='estadoLinea"+conta+"' hidden='true'>"+obj.getEstado()+"</p></td>" +
                                    "<td>"+obj.getId()+"</td>"+
                                    "<td>"+conta+"</td>"+ 
                                    "<td>"+obj.getItemcode()+"</td>"+
                                    "<td>"+obj.getItemName()+"</td>"+
                                    "<td><p id='stockSap"+conta+"' "+(obj.getEstado().equals("L")?"disabled":"")+">"+obj.getStockSAP()+"</p></td>"+
                                    "<td><input type='text' value='"+obj.getCantidadContada()+"' id='txtCantContada"+conta+"' name='cantContada' class='form-control' autocomplete='off' style='width: 100%' onkeypress='return soloNumeros(event)' "+(obj.getEstado().equals("L")?"disabled":"")+"></td> "+
                                    "<td><div class='form-group' id='selectBodega"+conta+"'><select id='bodega"+conta+"' name='bodegas' class='form-control' autocomplete='off' style='width: 100%' placeholder='Seleccione una bodega' "+(obj.getEstado().equals("L")?"disabled":"")+"></select></div></td>"+ 
                                    "<td><div class='form-group' id='selectUbicacion"+conta+"'><select id='ubicacion"+conta+"' name='ubicaciones' class='form-control' autocomplete='off' style='width: 100%' placeholder='Seleccione una ubicacion' disabled></select></div></td>"+ 
                                    "<td><input type='text' value='"+obj.getComentarioConteo()+"' id='txtComentarioConteo"+conta+"' name='comentarioConteo' class='form-control' autocomplete='off' style='width: 100%' "+(obj.getEstado().equals("L")?"disabled":"")+"></td>"+
                                    "<td><div class='form-group' id='selectEstado"+conta+"'><select id='estadoConteo"+conta+"' name='estados' class='form-control' autocomplete='off' style='width: 100%' placeholder='Seleccione un estado' "+(obj.getEstado().equals("L")?"disabled":"")+"></select></div></td>"+ 
                                    "</tr>");
                    conta++;
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlListarAsigConteo.mostrarLineasAsigConteo(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("actualizarCantidades")!=null) {
                try {
                    int respuesta=0;
                    respuesta=daoCINV.actualizarCantidades(
                        request.getParameter("itemCode"),
                        request.getParameter("cantidad"),
                        request.getParameter("comments"),
                        request.getParameter("bodega"),
                        request.getParameter("ubicacion"),
                        Integer.parseInt(request.getParameter("idUsuario")),
                        Integer.parseInt(request.getParameter("id"))
                    );
                    out.print(respuesta);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("actualizarEstado")!=null) {
                try {
                    int respuesta=0;
                    respuesta=daoCINV.actualizarEstado(
                        request.getParameter("itemCode"),
                        request.getParameter("estado"),
                        Integer.parseInt(request.getParameter("id"))
                    );
                    out.print(respuesta);
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            if (request.getParameter("listarUbicaciones")!=null) {                
                try {
                    ArrayList<OBIN> arr= new ArrayList<OBIN>();
                    arr.addAll((Collection)daoCINV.listarUbicaciones(request.getParameter("bodega")));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.listarUbicaciones(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("listarBodega")!=null) {                
                try {
                    ArrayList<OWHS> arr= new ArrayList<OWHS>();
                    arr.addAll((Collection)daoCINV.listarBodega());
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.listarBodega(): "+e.getMessage());
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
