/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import entidades.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.RequestDispatcher;
import modelo.*;
import com.google.gson.Gson;
import java.text.DecimalFormat;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlCerradas extends HttpServlet {

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
            throws ServletException, IOException {response.setContentType("text/html;charset=UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            DaoOWOR dor = new DaoOWOR();
            DaoWOR1 dIns = new DaoWOR1();
            DaoAPS_OIGE dOIGE = new DaoAPS_OIGE();
            DaoOWTR dOWTR = new DaoOWTR();
            DaoAPS_OIGE3 dOIGE3 = new DaoAPS_OIGE3();
            DaoAPS_OWOR dAPSO= new DaoAPS_OWOR();
            DaoAPS_OCRC dAPS_OCRC= new DaoAPS_OCRC();
            DaoOITT dItem = new DaoOITT();
            
            if (request.getParameter("mostrar")!=null) {
                try {
                    HttpSession s = request.getSession();
                    int idUsuario=(Integer)s.getAttribute("idUsuario");
                    int idRol=(Integer)s.getAttribute("idRol");
                    int proceso=1;
                     String estado="";
                    double CantPln;
                    double Cantcmp;
                    double CantRjc;
                    ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                    Orden.addAll((Collection)dor.mostrarC(idUsuario, idRol));
                    ArrayList<APS_OIGE> oige= new ArrayList<APS_OIGE>();
                    oige.addAll((Collection)dAPSO.comprobarEstado());
                    for (OWOR or : Orden) {
                        proceso=1;
                        for (APS_OIGE ar : oige) {
                            if (or.getDocnum()==ar.getAPS_OWOR().getDocnum()) {
                                proceso=ar.getEstado();
                            }
                        }
                        switch(or.getStatus()){
                            case "L":
                                estado="Cerrada";
                            break;
                        }
                      CantPln = Double.parseDouble(or.getCantpln());
                      Cantcmp = Double.parseDouble(or.getCantcmp());
                      CantRjc = Double.parseDouble(or.getCantrjc());
                      out.println("<tr><td>"+or.getDocnum()+"</td>"+
                                  "<td>"+or.getStartdate()+"</td>"+
                                  "<td>"+or.getDuedate()+"</td>"+
                                  "<td>"+or.getItemcode()+"</td>"+
                                  "<td>"+or.getItemname()+"</td>"+
                                  "<td>"+estado+"</td>"+
                                  "<td>"+or.getType()+"</td>"+
                                  "<td>"+CantPln+"</td>"+
                                  "<td>"+Cantcmp+"</td>"+
                                  "<td>"+CantRjc+"</td>"+
                                  "<td>"+proceso+"</td></tr>");
                    }
                } catch (Exception e) {
                    request.setAttribute("error", e.getMessage());
                }
            }
            
            //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ORDENES DE FABROCACION
            if (request.getParameter("filtrarOrdenes")!=null) {
                 try {
                     String proceso="";
                     String estado="";
                     String CantPln;
                     String Cantcmp;
                     String CantRjc;
                     ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                     Orden.addAll((Collection)dor.filtrarOrdenesC(request.getParameter("cecos")));
                     ArrayList<APS_OIGE> oige= new ArrayList<APS_OIGE>();
                     oige.addAll((Collection)dAPSO.comprobarEstado());
                     DecimalFormat formato = new DecimalFormat("######.###");
                     for (OWOR or : Orden) {
                         proceso="Sin asignar";
                         for (APS_OIGE ar : oige) {
                             if (or.getDocnum()==ar.getAPS_OWOR().getDocnum()) {
                                 if (ar.getEstado()==0) {
                                     proceso="En fabricacion";
                                 } else {
                                     proceso="Terminado";
                                 }
                             }
                         }
                         switch(or.getStatus()){
                             case "R":
                                 estado="Liberada";
                             break;
                             case "P":
                                 estado="Planificada";
                             break;
                         }
                       CantPln =formato.format(Double.parseDouble(or.getCantpln()));
                       Cantcmp = formato.format(Double.parseDouble(or.getCantcmp()));
                       CantRjc = formato.format(Double.parseDouble(or.getCantrjc()));
                       out.println("<tr><td>"+or.getDocnum()+"</td>"+
                                   "<td>"+or.getStartdate()+"</td>"+
                                   "<td>"+or.getDuedate()+"</td>"+
                                   "<td>"+or.getItemcode()+"</td>"+
                                   "<td>"+or.getItemname()+"</td>"+
                                   "<td>"+estado+"</td>"+
                                   "<td>"+or.getType()+"</td>"+
                                   "<td>"+CantPln+"</td>"+
                                   "<td>"+Cantcmp+"</td>"+
                                   "<td>"+CantRjc+"</td>"+
                                   "<td>"+proceso+"</td></tr>");
                     }
                 } catch (Exception e) {
                     request.setAttribute("error", e.getMessage());
                 }
             }
               
            //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE MATERIALES DE CADA ORDEN DE FABRICACION
            if (request.getParameter("mostrarMateriales")!=null) {
                 try {
                     ArrayList<WOR1> ins= new ArrayList<WOR1>();
                     ins.addAll((Collection)dIns.mostrarMateriales(Integer.parseInt(request.getParameter("numOrden"))));
                     for (WOR1 obj : ins) {
                         out.println("<tr>"+ 
                                   "<td>"+obj.getItemCode()+"</td>"+
                                   "<td>"+obj.getItemName()+"</td>"+
                                   "<td>"+obj.getReleaseQty()+"</td>"+
                                   "<td>"+obj.getCantEntregada()+"</td>"+
                                   "</tr>");
                     }
                 } catch (Exception e) {
                     request.setAttribute("error", e.getMessage());
                 }
             }
               
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE TRASLADOS DE UNA OP
               if (request.getParameter("mostrarTraslados")!=null) {
                    try {
                        ArrayList<OWTR> owtr= new ArrayList<OWTR>();
                        owtr.addAll((Collection)dOWTR.mostrar(request.getParameter("numOrden")));
                        for (OWTR obj : owtr) {
                            out.println("<tr>"+ 
                                            "<td>"+obj.getDocNum()+"</td>"+
                                            "<td>"+obj.getItemCode()+"</td>"+
                                            "<td>"+obj.getItemName()+"</td>"+
                                            "<td>"+obj.getQuantity()+"</td>"+
                                            "<td>"+obj.getShipDate()+"</td>"+
                                            "<td>"+obj.getBodega()+"</td>"+
                                        "</tr>");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
               
               
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ACTIVIADADES DE CADA ORDEN DE FABRICACION
               if (request.getParameter("mostrarActividades")!=null) {
                   double porcentaje=0;
                    try {
                        ArrayList<APS_OIGE> ACT= new ArrayList<APS_OIGE>();
                        ACT.addAll((Collection)dOIGE.mostrarActividades(Integer.parseInt(request.getParameter("numOrden"))));
                        String inicio="";
                        String fin="";
                        String codeInsumo="";
                        for (APS_OIGE obj : ACT) {
                            codeInsumo="";
                            ArrayList<APS_OIGE3> material= new ArrayList<APS_OIGE3>();
                            material.addAll((Collection)dOIGE3.mostrar(request.getParameter("numOrden"), obj.getIdAPS_OIGE()));
                            for (APS_OIGE3 oige3 : material) {
                                codeInsumo+=oige3.getItemCode()+" - Cantidad: "+oige3.getCantAsignada()+", ";
                            }
                            codeInsumo=codeInsumo.substring(0, codeInsumo.length()-2);
                            
                            if (obj.getInicio()==null) {
                                inicio="No iniciada";
                            }else{
                                inicio=obj.getInicio();
                            }
                            if (obj.getFin()==null) {
                                fin="No finalizada";
                            }else{
                                fin=obj.getFin();
                            }
                            out.println("<tr>"+ 
                                      "<td>"+obj.getNomEmp()+"</td>"+
                                      "<td>"+obj.getActividad()+"</td>"+
                                      "<td>"+obj.getDescActividad()+"</td>"+
                                      "<td>"+codeInsumo+"</td>"+
                                      "<td>"+inicio+"</td>"+
                                      "<td>"+fin+"</td>"+
                                      "<td>"+obj.getTotalUnidadesConformes()+"</td>"+ 
                                      "<td>"+obj.getEstado()+"</td>"+ 
                                      "<td>"+obj.getDescActividadProd()+"%</td>"+ 
                                      "</tr>");
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
               
                if (request.getParameter("filtrarAgenda")!=null) {
                    try {
                        ArrayList<OWOR> arr= new ArrayList<OWOR>();
                        arr.addAll((Collection)dor.filtrarOrdenesC(request.getParameter("cecos")));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
               
                if (request.getParameter("mostrarAgenda")!=null) {
                    try {
                        ArrayList<OWOR> arr= new ArrayList<OWOR>();
                        HttpSession s = request.getSession();
                        int idUsuario=(Integer)s.getAttribute("idUsuario");
                        int idRol=(Integer)s.getAttribute("idRol");
                        arr.addAll((Collection)dor.mostrarC(idUsuario,idRol));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
                
                //METODO ENVIAR LOS DATOS DE LOS CENTROS DE COSTO PARA CARGARLOS EN LOS SELECT DE CENTROS DE COSTO
                if (request.getParameter("listarCecosO")!=null) {
                    try {
                        if (Integer.parseInt(request.getParameter("idRol"))==2) {
                            ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                            APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRCUser(Integer.parseInt(request.getParameter("idUsuario")),Integer.parseInt(request.getParameter("idRol"))));
                            String json = new Gson().toJson(APS_OCRC);
                            out.print(json);
                        } else {
                            ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                            APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRC());
                            String json = new Gson().toJson(APS_OCRC);
                            out.print(json);
                        }
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
               
               //METODO ENVIAR LOS DATOS LAS ORDENES FATHER DE LA ORDEN SELECCIONADA EN AGENDA
               if (request.getParameter("mostrarItem")!=null) {
                    try {
                        ArrayList<OITT> Item= new ArrayList<OITT>();
                        Item.addAll((Collection)dItem.mostrar(request.getParameter("itemCode")));
                        String json= new Gson().toJson(Item);
                        out.print(json);
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
               
                //METODO ENVIAR LOS DATOS DE LA ORDEN SELECCIONADA EN AGENDA
                if (request.getParameter("obtenerOrden")!=null) {
                    try {
                        ArrayList<OWOR> ar= new ArrayList<OWOR>();
                        ar.addAll((Collection)dor.obtenerOrden(Integer.parseInt(request.getParameter("numOrden"))));
                        String json= new Gson().toJson(ar);
                        out.print(json);
                    } catch (Exception e) {
                        request.setAttribute("error", e.getMessage());
                    }
                }
               
                if (request.getParameter("filtrarAgendaArt")!=null) {
                    try {
                        ArrayList<OWOR> arr= new ArrayList<OWOR>();
                        arr.addAll((Collection)dor.filtrarOrdenesCArt(request.getParameter("itemCode")));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlCerradas.filtrarAgendaArt(): "+e.getMessage());
                    }
                }

                if (request.getParameter("listarArticulosA")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dItem.mostrarArticulosHistorico());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlCerradas.listarArticulosA(): " + e.getMessage());
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
