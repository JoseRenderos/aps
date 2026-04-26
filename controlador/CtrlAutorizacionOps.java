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
import modelo.SAP.QueryAPS;
import java.util.*;
import entidades.*;
import com.google.gson.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Mario Valdez
 */
public class CtrlAutorizacionOps extends HttpServlet {

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
            DaoAPS_IGN1 dAps_ign1 = new DaoAPS_IGN1();
            DaoSAP sap = new DaoSAP();
            DaoOWOR daoOWOR = new DaoOWOR();
            DaoAPS_OIGE3 dOIGE3 = new DaoAPS_OIGE3();
            if (request.getParameter("mostrarOpsCosto")!=null) {                
                try {
                    ArrayList<Costo_OP> ops= new ArrayList<Costo_OP>();
                    ops.addAll((Collection)dAps_ign1.mostrarOpsCosto());
                    String estado="";
                    String estadoCierre="";
                    for (Costo_OP obj : ops) {
                        if (obj.getEstadoCarga().equals("0")) {
                            estado="PRODUCCION EN ESPERA";
                        }else if (obj.getEstadoCarga().equals("1")) {
                            estado="PRODUCCION CARGADA";
                        }else if (obj.getEstadoCarga().equals("2")) {
                            estado="AUTORIZADA";
                        }
                        
                        if (obj.getEstadoCierre().equals("Y")) {
                            estadoCierre="LISTA PARA CIERRE";
                        }else if (obj.getEstadoCierre().equals("N")) {
                            estadoCierre="INCOMPLETA";
                        }
                        out.println("<tr>"+ 
                                        "<td>"+obj.getDocnum()+"</td>"+
                                        "<td>"+obj.getItemCode_Orden()+"</td>"+
                                        "<td>"+obj.getItemname_Orden()+"</td>"+
                                        "<td>"+obj.getStartDate()+"</td>"+
                                        "<td>"+obj.getCloseDate()+"</td>"+
                                        "<td>"+obj.getPlnQty_Orden()+"</td>"+
                                        "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                        "<td>"+obj.getRjctQty_Orden()+"</td>"+ 
                                        "<td>"+obj.getCosto_Total_Pry()+"</td>"+ 
                                        "<td>"+obj.getCosto_Total_Uni()+"</td>"+ 
                                        "<td>"+obj.getPorCiento_Consumo()+"%</td>"+ 
                                        "<td>"+obj.getComentarios()+"</td>"+ 
                                        "<td>"+obj.getComentariosProd()+"</td>"+ 
                                        "<td>"+estadoCierre+"</td>"+ 
                                        "<td>"+estado+"</td>"+ 
                                    "</tr>");
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlAutorizacionOps.mostrarActividades(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarInsumosOp")!=null) {                
                try {
                    ArrayList<Costo_OP> ops= new ArrayList<Costo_OP>();
                    ops.addAll((Collection)dAps_ign1.mostrarInsumosOp(request.getParameter("docNum")));
                    String estado="";
                    for (Costo_OP obj : ops) {
                        out.println("<tr>"+ 
                                        "<td>"+obj.getItemCode()+"</td>"+
                                        "<td>"+obj.getItemName()+"</td>"+
                                        "<td>"+obj.getComentariosProd()+"</td>"+
                                        "<td>"+obj.getPlnQty()+"</td>"+
                                        "<td>"+obj.getIssueQty()+"</td>"+
                                        "<td>"+obj.getCmptQty_Orden()+"</td>"+
                                        "<td>"+obj.getTipo()+"</td>"+
                                        "<td>"+obj.getCosto_Pry()+"</td>"+
                                        "<td>"+obj.getCosto_Uni()+"</td>"+
                                        "<td>"+obj.getCosto_Total_Pry()+"</td>"+
                                        "<td>"+obj.getCosto_Total_Uni()+"</td>"+
                                        "<td>"+obj.getVariacion()+"</td>"+
                                        "<td>"+obj.getPorCiento_Variacion_Total()+"%</td>"+
                                        "<td>"+obj.getComentarios()+"</td>"+
                                    "</tr>");
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlAutorizacionOps.mostrarActividades(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarENMT")!=null) {                
                try {
                    ArrayList<APS_ENMT> enmt= new ArrayList<APS_ENMT>();
                    enmt.addAll((Collection)dOIGE3.mostrarENMT(request.getParameter("docNum")));
                    String estado="";
                    for (APS_ENMT obj : enmt) {
                        out.println("<tr>"+ 
                                        "<td>"+obj.getIdAPS_ENMT()+"</td>"+
                                        "<td>"+obj.getDocNum()+"</td>"+
                                        "<td>"+obj.getDocDate()+"</td>"+
                                        "<td>"+obj.getUsuario()+"</td>"+
                                        "<td>"+obj.getEstado()+"</td>"+
                                    "</tr>");
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlAutorizacionOps.mostrarENMT(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarENMT1")!=null) {                
                try {
                    ArrayList<APS_ENMT1> enmt1= new ArrayList<APS_ENMT1>();
                    enmt1.addAll((Collection)dOIGE3.mostrarENMT1(request.getParameter("id")));
                    String estado="";
                    int conta=1;
                    for (APS_ENMT1 obj : enmt1) {
                        out.println("<tr>"+ 
                                        "<td><p id='codeArticulo"+conta+"' hidden='true'>"+obj.getItemCode()+"</p><p id='nomArticulo"+conta+"' hidden='true'>"+obj.getItemName()+"</p>"+obj.getItemCode()+"</td>"+
                                        "<td>"+obj.getItemName()+"</td>"+
                                        "<td>"+obj.getBodega()+"</td>"+
                                        "<td>"+obj.getStock()+"</td>"+
                                        "<td><input type='text' value='"+obj.getCantidad()+"' id='txtCantSolicitada"+conta+"' name='cantSolicitada[]' class='form-control' autocomplete='off' style='width: 100%' onkeypress='return soloNumeros(event)'></td>"+
                                    "</tr>");
                        conta++;
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlAutorizacionOps.mostrarENMT1(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("cargarProduccion")!=null){
                try{
                    String respuesta="";
                    int res=-1;
                    APS_IGN1 aps_ign1 = new APS_IGN1();
                    OWOR owor = new OWOR();
                    aps_ign1.setItemCode(request.getParameter("itemCode"));
                    aps_ign1.setCantCompletada(Integer.parseInt(request.getParameter("uniConformes")));
                    aps_ign1.setCantRechazada(Integer.parseInt(request.getParameter("uniNoConformes")));
                    owor.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                    aps_ign1.setAPS_OWOR(owor);
                    if (dAps_ign1.eliminar(aps_ign1)!=0) {
                        respuesta=sap.addProduccion(Integer.parseInt(request.getParameter("numOrden")), Integer.parseInt(request.getParameter("uniConformes")), Integer.parseInt(request.getParameter("uniNoConformes")));
                        out.print(respuesta);
                    } else {
                        out.print(-1);
                    }
                    
                }catch(Exception e){
                     System.out.println("controlador.CtrlAutorizacionOps.cargarProduccion(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("guardarProduccion")!=null){
                try{
                    String respuesta="";
                    int res=-1;
                    APS_IGN1 aps_ign1 = new APS_IGN1();
                    OWOR owor = new OWOR();
                    QueryAPS queryAPS = new QueryAPS();
                    aps_ign1.setItemCode(request.getParameter("itemCode"));
                    aps_ign1.setCantCompletada(Integer.parseInt(request.getParameter("uniConformes")));
                    aps_ign1.setCantRechazada(Integer.parseInt(request.getParameter("uniNoConformes")));
                    owor.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                    aps_ign1.setAPS_OWOR(owor);
                    if (dAps_ign1.eliminar(aps_ign1)!=0) {
                         if (dAps_ign1.insertar(aps_ign1)==0) {
                            out.print(-1);     
                         }
                    } else {
                        out.print(-1);
                    }
                    
                }catch(Exception e){
                     System.out.println("controlador.CtrlAutorizacionOps.cargarProduccion(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarCantCmpOp")!=null) {
                try {
                    int r=0;
                        r=dAps_ign1.mostrarCantCmpOp(Integer.parseInt(request.getParameter("docNum")));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.mostrarCantCmpOp(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarCantRjctOp")!=null) {
                try {
                    int r=0;
                    r=dAps_ign1.mostrarCantRjctOp(Integer.parseInt(request.getParameter("docNum")));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.mostrarCantRjctOp(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("actualizarComentarioOp")!=null) {
                try {
                    int r=0;
                    r=daoOWOR.actualizarComentarioOp(Integer.parseInt(request.getParameter("numOrden")),request.getParameter("comentario"));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.actualizarComentarioOp(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("actualizarEstadoAutorizacionOp")!=null) {
                try {
                    int r=0;
                    r=daoOWOR.actualizarEstadoAutorizacionOp(Integer.parseInt(request.getParameter("numOrden")));
                    if (r!=0) {
                        enviarCorreoCostoAutorizado(request, response, request.getParameter("numOrden"));
                    }
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.actualizarEstadoAutorizacionOp(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("obtenerComentario")!=null) {
                try {
                    String r="";
                    r=daoOWOR.obtenerComentario(Integer.parseInt(request.getParameter("docNum")));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.actualizarEstadoAutorizacionOp(): " + e.getMessage());
                }
            }
            
            
            if (request.getParameter("cancelarENMT")!=null) {
                try {
                    int r=0;
                    r=dOIGE3.cancelarENMT(request.getParameter("id"));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.cancelarENMT(): " + e.getMessage());
                }
            }
            
            if(request.getParameter("cargarMateriales")!=null){
                try{
                    String respuesta="";
                    respuesta=sap.addMateriales(Integer.parseInt(request.getParameter("numOrden")),request.getParameter("itemCode"),request.getParameter("itemName"),request.getParameter("cantMateriales"),Integer.parseInt(request.getParameter("id")));
                    out.print(respuesta);
                }catch(Exception e){
                     System.out.println("controlador.CtrlAutorizacionOps.cargarMateriales(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("guardarComentariosCostos")!=null) {
                try {
                    int r=0;
                    r=daoOWOR.guardarComentariosCostos(Integer.parseInt(request.getParameter("docnum")),request.getParameter("ComentariosCosto"));
                    out.print(r);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.guardarComentariosCostos(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("obtenerComentarioCostos")!=null) {
                try {
                    ArrayList<ObjGen> us = new ArrayList<ObjGen>();
                    us.addAll((Collection)daoOWOR.obtenerComentarioCostos());
                    String json = new Gson().toJson(us);
                    out.print(json);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.obtenerComentarioCostos(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("obtenerComentarioOP")!=null) {
                try {
                    ArrayList<ObjGen> us = new ArrayList<ObjGen>();
                    us.addAll((Collection)daoOWOR.obtenerComentarioOP(Integer.parseInt(request.getParameter("docnum"))));
                    String json = new Gson().toJson(us);
                    out.print(json);
                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.obtenerComentarioOP(): " + e.getMessage());
                }
            }
        }
        processRequest(request, response);
    }

    private void enviarCorreoCostoAutorizado(HttpServletRequest request, HttpServletResponse response,String docNum)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        try (PrintWriter out = response.getWriter()) {
            DaoAPS_IGN1 daoAPS_IGN1 = new DaoAPS_IGN1();
            DaoOWOR daoOWOR = new DaoOWOR();
            try {    

                ServletContext servletContext = this.getServletContext();
                System.out.println("");
                System.out.println("********************************");
                System.out.println("*        ENVIANDO CORREO       *");
                System.out.println("********************************");
                System.out.println("");

                String asunto = "OP " +docNum + " Autorizada";
                String mensaje="Orden de produccion " + docNum + " autorizada por Gerencia de producción: \n";
                mensaje+="----------------------------------------------------------------------------\n";
                mensaje+="Comentario: " + daoOWOR.obtenerComentario(Integer.parseInt(docNum))+"\n";
                mensaje+="\nPor favor revisar en aplicacion APS";

                ConexionSDO sdo = new ConexionSDO();
                Connection con = sdo.con();
                con.setAutoCommit(false);
                Map parametro = new HashMap();
                parametro.put("docnum", docNum);
                parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_orden.jasper")));
                byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                InputStream inputStream = new ByteArrayInputStream(bytes,0,bytes.length);
                DataSource source;

                source = new ByteArrayDataSource(inputStream, "application/pdf");

                MimeBodyPart messageBodyPart = new MimeBodyPart();
                MimeBodyPart messageBodyPart1 = new MimeBodyPart();

                messageBodyPart.setDataHandler(new DataHandler(source));
                messageBodyPart.setFileName("ReporteOrden"+docNum+".pdf");

                MimeMultipart multiPart = new MimeMultipart();
                multiPart.addBodyPart(messageBodyPart);

                messageBodyPart1.setText(mensaje);
                multiPart.addBodyPart(messageBodyPart1);

                Properties propiedad = new Properties();
                propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
                propiedad.setProperty("mail.smtp.starttls.enable", "true");
                propiedad.setProperty("mail.smtp.port", "587");
                propiedad.setProperty("mail.smtp.auth","true");
                propiedad.setProperty("mail.smtp.ssl.trust", "smtp.gmail.com");
                propiedad.setProperty("mail.smtp.ssl.protocols", "TLSv1.2");

                Session sesion = Session.getDefaultInstance(propiedad);
    //            String correoEnvia = "m.valdezh84@gmail.com";
    //            String contrasena = "fadmqrcftpwokgzd";
                String correoEnvia = "ventas.alsasa@gmail.com";
                String contrasena = "hespztbyjnhtpltb";
                String cc = daoAPS_IGN1.obtenerCorreoUsuario(23);//kevin
                String cc1 = daoAPS_IGN1.obtenerCorreoUsuario(15);//mario
                String cc2 = daoAPS_IGN1.obtenerCorreoUsuario(43);//aux costos

                MimeMessage mail = new MimeMessage(sesion);

                mail.setFrom(new InternetAddress (correoEnvia));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc1));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc2));
                mail.setSubject(asunto);
                mail.setContent(multiPart);

                Transport transportar = sesion.getTransport("smtp");
                transportar.connect(correoEnvia,contrasena);
                transportar.sendMessage(mail, mail.getAllRecipients());          
                transportar.close();

                System.out.println("");
                System.out.println("--------------------------------");
                System.out.println("| CORREO ENVIADO CORRECTAMENTE |");
                System.out.println("--------------------------------");
                System.out.println("");
                out.print(1);
            } catch (AddressException ex) {
               System.out.println("Error: envio de correo 1: "+ex.getMessage());
               out.print(0);
            } catch (MessagingException ex) {
                System.out.println("Error: envio de correo 2: "+ex.getMessage());
                out.print(0);
            } catch (Exception e) { 
                e.printStackTrace();
                out.print(0);
            }
        }
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
