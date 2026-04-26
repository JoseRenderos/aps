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
import com.google.gson.Gson;
import java.util.*;
import entidades.*;
import java.text.DecimalFormat;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlEmision extends HttpServlet {

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
        DaoAPS_OIGE dOIGE= new DaoAPS_OIGE();
        DaoAPS_OIGE1 dOIGE1 = new DaoAPS_OIGE1();
        DaoOWOR dor = new DaoOWOR();
        
        //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE EMISION DE PRODUCCION
            if (request.getParameter("mostrar")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    String totalTiempo="";
                    String TotalTiempoMuerto="";
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dor.obtenerNumOrdenes(idUsuario,idRol));
                    ArrayList<APS_OIGE> OrdenE= new ArrayList<APS_OIGE>();
                    OrdenE.addAll((Collection)dOIGE.mostrarEmision());
                    DecimalFormat formato = new DecimalFormat("####.###");
                    for (APS_OIGE obj : OrdenE) {
                        for (OWOR owor : Orden) {
                            if (owor.getDocnum()==obj.getAPS_OWOR().getDocnum()) {
                                totalTiempo = formato.format(Double.parseDouble(obj.getTotalTiempo()));
                                TotalTiempoMuerto = formato.format(Double.parseDouble(obj.getTotalTiempoMuerto()));
                                out.println("<tr><td>"+obj.getAPS_OWOR().getDocnum()+"</td>"+
                                              "<td>"+obj.getAPS_OWOR().getItemcode()+"</td>"+
                                              "<td>"+obj.getAPS_OWOR().getItemname()+"</td>"+
                                              "<td>"+formato.format(Double.parseDouble(obj.getAPS_OWOR().getCantpln()))+"</td>"+
                                              "<td>"+obj.getNomEmp()+"</td>"+
                                              "<td>"+obj.getActividad()+"</td>"+
                                              "<td>"+obj.getDescActividad()+"</td>"+
                                              "<td>"+obj.getInicio()+"</td>"+
                                              "<td>"+obj.getFin()+"</td>"+
                                              "<td>"+totalTiempo+"</td>"+
                                              "<td>"+TotalTiempoMuerto+"</td>"+
                                              "<td>"+obj.getEstado()+"</td></tr>"); 
                            }
                        }
                           
                    }

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE DETALLE DE TIEMPO
            if (request.getParameter("mostrarControlTiempo")!=null) {
                try {
                    ArrayList<APS_OIGE1> oige1= new ArrayList<APS_OIGE1>();
                    oige1.addAll((Collection)dOIGE1.mostrarControlTiempo(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("nomEmp"), request.getParameter("actividad")));
                    for (APS_OIGE1 obj : oige1) {
                        out.println("<tr><td>"+obj.getNumCaptura()+"</td>"+ 
                                      "<td>"+obj.getInicio()+"</td>"+
                                      "<td>"+obj.getFin()+"</td>"+
                                      "<td>"+obj.getUniConformes()+"</td>"+
                                      "<td>"+obj.getUniNoConformes()+"</td>"+
                                      "<td>"+obj.getUniRechazadas()+"</td>"+
                                      "<td>"+obj.getUniTotales()+"</td>"+
                                      "<td>"+obj.getComentario()+"</td></tr>");
                    }

                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }  
            
            //METODO ENVIAR LOS DATOS DE LA ACTIVIDAD A MODAL DE EMISION DE PRODUCCION
            if (request.getParameter("mostrarInfoEmision")!=null) {
                 try {
                     ArrayList<APS_OIGE> oige= new ArrayList<APS_OIGE>();
                     oige.addAll((Collection)dOIGE.mostrarInfoEmision(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("nomEmp"), request.getParameter("actividad"), request.getParameter("inicio")));
                     String json= new Gson().toJson(oige);
                     out.print(json);
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
