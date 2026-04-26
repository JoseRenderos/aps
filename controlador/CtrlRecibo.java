/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import com.google.gson.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.*;
import modelo.SAP.*;
import java.util.*;
import entidades.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.*;
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
import javax.management.Query;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlRecibo extends HttpServlet {

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
    private Gson gson = new Gson();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        try (PrintWriter out = response.getWriter()) {
            DaoOWOR dOwor = new DaoOWOR();
            DaoAPS_OIGE dOIGE = new DaoAPS_OIGE();
            DaoAPS_OIGE3 dOIGE3 = new DaoAPS_OIGE3();
            DaoSAP sap = new DaoSAP();
            QueryAPS query = new QueryAPS();
            DaoAPS_OWOR dAps_owor = new DaoAPS_OWOR();
            DaoAPS_IGN1 dAps_ign1 = new DaoAPS_IGN1();
            if (request.getParameter("mostrarOrden")!=null) {
                try{
                    ArrayList<OWOR> ar = new ArrayList<>();
                    ar.addAll((Collection)dOwor.buscarOrden(Integer.parseInt(request.getParameter("numOrden"))));
                    String json= new Gson().toJson(ar);
                    out.print(json);
                }catch(Exception e){
                     System.out.println("controlador.CtrlRecibo.mostrarOrden(): "+e.getMessage());
                }
            }

            //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ACTIVIADADES DE CADA ORDEN DE FABRICACION
            if (request.getParameter("mostrarActividades")!=null) {                
                try {
                    ArrayList<APS_OIGE> ACT= new ArrayList<APS_OIGE>();
                    ACT.addAll((Collection)dOIGE.mostrarActividadesRecibo(Integer.parseInt(request.getParameter("numOrden"))));
                    String inicio="";
                    String fin="";
                    for (APS_OIGE obj : ACT) {
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
                                        "<td>"+obj.getInsumo()+"</td>"+
                                        "<td>"+inicio+"</td>"+
                                        "<td>"+fin+"</td>"+
                                        "<td>"+obj.getTotalUnidadesConformes()+"</td>"+
                                        "<td>"+obj.getTotalUnidadesNoConformes()+"</td>"+ 
                                        "<td>"+obj.getTotalTiempo()+"</td>"+ 
                                        "<td>"+obj.getEstado()+"</td>"+ 
                                        "<td>"+obj.getDescActividadProd()+"</td>"+ 
                                    "</tr>");
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.mostrarActividades(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("cargarTiempos")!=null){
                try{
                    String respuesta="";
                    respuesta=sap.addTiempos(Integer.parseInt(request.getParameter("numOrden")));
                    out.print(respuesta);
                }catch(Exception e){
                     System.out.println("controlador.CtrlRecibo.cargarTiempos(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("cargarMateriales")!=null){
                try{
                    int respuesta=0;
                    respuesta=dOIGE3.guardarENMT(request.getParameter("itemCode"), 
                                                 request.getParameter("cantMateriales"), 
                                                 Integer.parseInt(request.getParameter("idUsuario")), 
                                                 Integer.parseInt(request.getParameter("numOrden")));
                    //respuesta=sap.addMateriales(Integer.parseInt(request.getParameter("numOrden")),request.getParameter("itemCode"),request.getParameter("itemName"),request.getParameter("cantMateriales"));
                    out.print(respuesta);
                }catch(Exception e){
                     System.out.println("controlador.CtrlRecibo.cargarMateriales(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("cargarProduccion")!=null){
                try{
                    int respuesta=0;
                    double variacion=0;
                    APS_IGN1 aps_ign1 = new APS_IGN1();
                    OWOR owor = new OWOR();
                    aps_ign1.setItemCode(request.getParameter("itemCode"));
                    aps_ign1.setCantCompletada(Integer.parseInt(request.getParameter("uniConformes")));
                    aps_ign1.setCantRechazada(Integer.parseInt(request.getParameter("uniNoConformes")));
                    owor.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                    aps_ign1.setAPS_OWOR(owor);
                    respuesta=dAps_ign1.insertar(aps_ign1);
                    if (respuesta!=0) {
                        variacion=dAps_ign1.obtenerVariacionCosto(Integer.parseInt(request.getParameter("numOrden")));
                        if (variacion>5.0) {
                            enviarCorreoCostoMayor(request, response, request.getParameter("numOrden"));
                            //dAps_ign1.enviarCorreoCostoMayor(Integer.parseInt(request.getParameter("numOrden")));
                        }else if (variacion<-5.0) {
                            enviarCorreoCostoMenor(request, response, request.getParameter("numOrden"));
                            //dAps_ign1.enviarCorreoCostoMenor(Integer.parseInt(request.getParameter("numOrden")));
                        }else if (variacion>=-5.0 && variacion<=5.0) {
                            enviarCorreoCostoCorrecto(request, response, request.getParameter("numOrden"));
                            //dAps_ign1.enviarCorreoCostoCorrecto(Integer.parseInt(request.getParameter("numOrden")));
                        }
                    }
                    //respuesta=sap.addProduccion(Integer.parseInt(request.getParameter("numOrden")), Integer.parseInt(request.getParameter("uniConformes")), Integer.parseInt(request.getParameter("uniNoConformes")));
                    out.print(respuesta);
                }catch(Exception e){
                     System.out.println("controlador.CtrlRecibo.cargarProduccion(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("mostrarCompletadas")!=null){
                try{
                    int Completadas=0;
                    Completadas=sap.mostrarCompletadas(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("itemCode"));
                    out.print(Completadas);
                }catch(Exception e){
                     System.out.println("controlador.CtrlRecibo.mostrarCompletadas(): "+e.getMessage());
                }
            }
            
            if(request.getParameter("mostrarRechazadas")!=null){
                try{
                    int rechazadas=0;
                    rechazadas=sap.mostrarRechazadas(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("itemCode"));
                    out.print(rechazadas);
                }catch(Exception e){
                     System.out.println("controlador.CtrlRecibo.mostrarRechazadas(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarMateriales")!=null) {                
                try {
                    ArrayList<APS_OIGE3> arr= new ArrayList<APS_OIGE3>();
                    arr.addAll((Collection)sap.mostrarMateriales(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("itemCode")));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.mostrarMateriales(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("cargarActividades")!=null) {                
                try {
                    int conta=1;
                    ArrayList<APS_OIGE> ACT= new ArrayList<APS_OIGE>();
                    ACT.addAll((Collection)sap.cargarActividades(Integer.parseInt(request.getParameter("numOrden"))));
                    for (APS_OIGE obj : ACT) {
                        out.println("<tr>"+
                                    "<td><input type='checkbox' name='id[]' value='"+conta+"'><p id='idAPS_OIGE"+conta+"' hidden='true'>"+obj.getIdAPS_OIGE()+"</p></td>" +
                                    "<td><div class='form-group' id='selectActividades"+conta+"'><select id='ActividadProd"+conta+"' name='ActividadProd[]' class='form-control' autocomplete='off' style='width: 100%' placeholder='Seleccione una actividad'></select></div></td>"+ 
                                    "<td>"+obj.getNomEmp()+"</td>"+
                                    "<td>"+obj.getActividad()+"</td>"+
                                    "<td>"+obj.getTotalTiempo()+"</td>"+ 
                                    "<td>"+obj.getTotalUnidadesConformes()+"</td>"+
                                    "<td>"+obj.getTotalUnidadesNoConformes()+"</td>"+ 
                                    "<td>"+obj.getInsumo()+"</td>"+ 
                                    "<td>"+obj.getEstado()+"</td>"+ 
                                    "</tr>");
                    conta++;
                    }
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.cargarActividades(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("listarActividadesProd")!=null) {                
                try {
                    ArrayList<ActividadProd> arr= new ArrayList<ActividadProd>();
                    arr.addAll((Collection)sap.listarActividadesProd(request.getParameter("code")));
                    String json= new Gson().toJson(arr);
                    out.print(json);
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.listarActividadesProd(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("guardarActividadProd")!=null) {                
                try {
                    int res=0;
                    res=dOIGE.guardarActividadProd(Integer.parseInt(request.getParameter("id")), request.getParameter("codeAct"), request.getParameter("descActividad"));
                    out.print(res); 
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.guardarActividadProd(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("comprobarEstadoCargas")!=null) {                
                try {
                    int res=0;
                    res=dAps_owor.comprobarEstadoCargas(Integer.parseInt(request.getParameter("numOrden")));
                    out.print(res); 
                } catch (Exception e) {
                     System.out.println("controlador.CtrlRecibo.comprobarEstadoCargas(): "+e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarCantCmpAPS")!=null) {
                try {
                    int r=0;
                        r=dAps_ign1.mostrarCantCmpAPS(Integer.parseInt(request.getParameter("id")));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.mostrarCantCmpAPS(): " + e.getMessage());
                }
            }
            
            if (request.getParameter("mostrarCantRjctAPS")!=null) {
                try {
                    int r=0;
                    r=dAps_ign1.mostrarCantRjctAPS(Integer.parseInt(request.getParameter("id")));
                    out.print(r);

                } catch (Exception e) {
                    System.out.println("controlador.CtrlAutorizacionOps.mostrarCantRjctAPS(): " + e.getMessage());
                }
            }
            
        }
        processRequest(request, response);
    }

    private void enviarCorreoCostoMayor(HttpServletRequest request, HttpServletResponse response,String docNum)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        try (PrintWriter out = response.getWriter()) {
            DaoAPS_IGN1 daoAPS_IGN1 = new DaoAPS_IGN1();
            try {    

                ServletContext servletContext = this.getServletContext();
                System.out.println("");
                System.out.println("********************************");
                System.out.println("*        ENVIANDO CORREO       *");
                System.out.println("********************************");
                System.out.println("");

                String asunto = "COSTO DE OP " +docNum + " MAYOR AL 5% DE VARIACION";
                String mensaje="El costo de la orden de produccion " +docNum + " es mayor al 5% de variacion, por favor revisar en aplicacion APS\n";



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
                String cc1 = "lestrada@alsasa.com";

                MimeMessage mail = new MimeMessage(sesion);

                mail.setFrom(new InternetAddress (correoEnvia));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
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

    private void enviarCorreoCostoMenor(HttpServletRequest request, HttpServletResponse response,String docNum)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        try (PrintWriter out = response.getWriter()) {
            DaoAPS_IGN1 daoAPS_IGN1 = new DaoAPS_IGN1();
            try {    

                ServletContext servletContext = this.getServletContext();
                System.out.println("");
                System.out.println("********************************");
                System.out.println("*        ENVIANDO CORREO       *");
                System.out.println("********************************");
                System.out.println("");

                String asunto = "COSTO DE OP " +docNum + " MENOR AL -5% DE VARIACION";
                String mensaje="El costo de la orden de produccion " +docNum + " es menor al -5% de variacion, por favor revisar en aplicacion APS\n";

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
                String cc1 = "lestrada@alsasa.com";

                MimeMessage mail = new MimeMessage(sesion);

                mail.setFrom(new InternetAddress (correoEnvia));
                mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
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

    private void enviarCorreoCostoCorrecto(HttpServletRequest request, HttpServletResponse response,String docNum)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8"); 
        try (PrintWriter out = response.getWriter()) {
            DaoAPS_IGN1 daoAPS_IGN1 = new DaoAPS_IGN1();
            try {    

                ServletContext servletContext = this.getServletContext();
                System.out.println("");
                System.out.println("********************************");
                System.out.println("*        ENVIANDO CORREO       *");
                System.out.println("********************************");
                System.out.println("");

                String asunto = "COSTO DE OP " +docNum + " CORRECTO";
                String mensaje="El costo de la orden de produccion " +docNum + " no tiene una variacion menor a -5% o mayor 5%, por favor revisar en aplicacion APS\n";

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
