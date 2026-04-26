/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
import java.util.*;
import entidades.*;
import java.text.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;

/**
 *
 * @author Mario Valdez
 */
public class DaoAPS_IGN1 extends ConexionSBO implements CRUD{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Usuario us;
    APS_IGN1 APS_IGN1; 
    Costo_OP costo_OP;

    @Override
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_IGN1=(APS_IGN1)Ob;
            ps=con().prepareStatement("INSERT INTO SDO_ALSASA..APS_IGN1(itemCode,cantCompletada,cantRechazada,idAPS_OWOR) "+ 
                                      "VALUES(?,?,?,(SELECT idAPS_OWOR FROM SDO_ALSASA..APS_OWOR WHERE docNum=?))");
            ps.setString(1,APS_IGN1.getItemCode()); 
            ps.setInt(2,APS_IGN1.getCantCompletada());
            ps.setInt(3,APS_IGN1.getCantRechazada());
            ps.setInt(4,APS_IGN1.getAPS_OWOR().getDocnum());
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_IGN1.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
        
    }

    @Override
    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_IGN1=(APS_IGN1)Ob;
            ps=con().prepareStatement("DELETE FROM SDO_ALSASA..APS_IGN1 WHERE idAPS_OWOR=(SELECT idAPS_OWOR FROM SDO_ALSASA..APS_OWOR WHERE docNum=?)");
            ps.setInt(1,APS_IGN1.getAPS_OWOR().getDocnum());
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_IGN1.eliminar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<Costo_OP> mostrarOpsCosto() throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try {
            ps = super.con().prepareStatement("SELECT * FROM SDO_ALSASA..[VW_COSTOS_OPS_APP]  ");
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_OP = new Costo_OP();
                   costo_OP.setDocnum(rs.getString(1));
                   costo_OP.setItemCode_Orden(rs.getString(2));
                   costo_OP.setItemname_Orden(rs.getString(3));
                   costo_OP.setStartDate(rs.getString(4));
                   costo_OP.setPlnQty_Orden(rs.getString(5));
                   costo_OP.setCmptQty_Orden(rs.getString(6));
                   costo_OP.setRjctQty_Orden(rs.getString(7));
                   costo_OP.setCosto_Total_Pry(rs.getString(8));
                   costo_OP.setCosto_Total_Uni(rs.getString(9));
                   costo_OP.setPorCiento_Consumo(rs.getString(10));
                   costo_OP.setEstadoCarga(rs.getString(11));
                   costo_OP.setCloseDate(rs.getString(12));
                   costo_OP.setComentarios(rs.getString(13));
                   costo_OP.setComentariosProd(rs.getString(14));
                   costo_OP.setEstadoCierre(rs.getString(15));
                   ar.add(costo_OP);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarOpsCosto(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Costo_OP> mostrarCostosOp(int docnum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try {
            ps = super.con().prepareStatement("EXEC SDO_ALSASA..[SP_VARIACION_ORDEN] @docNum=?  ");
            ps.setInt(1,docnum);
            rs = ps.executeQuery();
            while(rs.next()){
               costo_OP = new Costo_OP();
               costo_OP.setCosto_Total_Pry(rs.getString(1));
               costo_OP.setCosto_Total_Uni(rs.getString(2));
               costo_OP.setPorCiento_Consumo(rs.getString(3));
               ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarCostosOp(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    
    public ArrayList<Costo_OP> mostrarInsumosOp(String docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try {
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..SP_LINEAS_ORDEN @DOCNUM=?");
                ps.setString(1, docNum);
                rs = ps.executeQuery();
                while(rs.next()){
                   costo_OP = new Costo_OP();
                   costo_OP.setItemCode(rs.getString(1));
                   costo_OP.setItemName(rs.getString(2));
                   costo_OP.setPlnQty(rs.getString(3));
                   costo_OP.setIssueQty(rs.getString(4));
                   costo_OP.setCmptQty_Orden(rs.getString(5));
                   costo_OP.setTipo(rs.getString(6));
                   costo_OP.setCosto_Pry(rs.getString(7));
                   costo_OP.setCosto_Uni(rs.getString(8));
                   costo_OP.setCosto_Total_Pry(rs.getString(9));
                   costo_OP.setCosto_Total_Uni(rs.getString(10));
                   costo_OP.setVariacion(rs.getString(11));
                   costo_OP.setPorCiento_Variacion_Total(rs.getString(12));
                   costo_OP.setComentariosProd(rs.getString(13));
                   costo_OP.setComentarios(rs.getString(16));
                   ar.add(costo_OP);
                }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarInsumosOp(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int mostrarCantCmpOp(int docNum) throws ClassNotFoundException, SQLException {
        int cant=0;
        try {
            ps = super.con().prepareStatement("SELECT T0.cmpltQty FROM OWOR T0 WHERE T0.docNum=? ");
            ps.setInt(1,docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                cant=rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarCantCmpOp()");
        }finally{
            super.con().close();
            ps.close();
        }
        return cant;
    }
        
    public int mostrarCantRjctOp(int docNum) throws ClassNotFoundException, SQLException {
        int cant=0;
        try {
            ps = super.con().prepareStatement("SELECT T0.rjctQty FROM OWOR T0 WHERE T0.docNum=? ");
            ps.setInt(1,docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                cant=rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarCantRjctOp()");
        }finally{
            super.con().close();
            ps.close();
        }
        return cant;
    }
    
    public int mostrarCantCmpAPS(int id) throws ClassNotFoundException, SQLException {
        int cant=0;
        try {
            ps = super.con().prepareStatement("SELECT T0.cantCompletada FROM SDO_ALSASA..APS_IGN1 T0 JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? ");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                cant=rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarCantCmpAPS()");
        }finally{
            super.con().close();
            ps.close();
        }
        return cant;
    }
        
    public int mostrarCantRjctAPS(int id) throws ClassNotFoundException, SQLException {
        int cant=0;
        try {
            ps = super.con().prepareStatement("SELECT T0.cantRechazada FROM SDO_ALSASA..APS_IGN1 T0 JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? ");
            ps.setInt(1,id);
            rs = ps.executeQuery();
            while(rs.next()){
                cant=rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.mostrarCantRjctAPS()");
        }finally{
            super.con().close();
            ps.close();
        }
        return cant;
    }
        
    public String obtenerCorreoUsuario(int idUsuario) throws ClassNotFoundException, SQLException {
        String result="";
        try {
            ps = super.con().prepareStatement("SELECT T0.correo FROM SDO_ALSASA..USUARIO T0 WHERE T0.idUsuario=? ");
            ps.setInt(1,idUsuario);
            rs = ps.executeQuery();
            while(rs.next()){
                result=rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.obtenerCorreoUsuario()");
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
        
    public double obtenerVariacionCosto(int docNum) throws ClassNotFoundException, SQLException {
        double result=0;
        try {
            ps = super.con().prepareStatement("SELECT T0.VARIACION FROM SDO_ALSASA..VW_COSTOS_OPS_APP T0 WHERE T0.docNum=? ");
            ps.setInt(1,docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                result=rs.getDouble(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoAPS_IGN1.obtenerVariacionCosto()");
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
    
    public void enviarCorreoCostoMayor(int docNum) throws ClassNotFoundException, SQLException{
        try {    
            String asunto = "COSTO DE OP " +docNum + " MAYOR AL 5% DE VARIACION";
            String mensaje="El costo de la orden de produccion " +docNum + " es mayor al 5% de variacion, por favor revisar en aplicacion APS ";
            
            
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
            String cc = obtenerCorreoUsuario(23);//kevin
            String cc1 = "largueta@alsasa.com";
            String ccGT = "apineda@fhconsultingsa.com";
            
            MimeMessage mail = new MimeMessage(sesion);
            
            
            mail.setFrom(new InternetAddress(correoEnvia));
            
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
            //mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc1));
            //mail.addRecipients(Message.RecipientType.CC, addresses);
            mail.setSubject(asunto);
            mail.setText(mensaje);
             
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia,contrasena);
            transportar.sendMessage(mail, mail.getAllRecipients());          
            transportar.close();
        } catch (AddressException ex) {
           System.out.println("Error: envio de correo aprobado 1: "+ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println("Error: envio de correo aprobado 2: "+ex.getMessage());
        }
    }
    
    public void enviarCorreoCostoMenor(int docNum) throws ClassNotFoundException, SQLException{
        try {    
            int slpCode=0;
            
            String asunto = "COSTO DE OP " +docNum + " MENOR AL -5% DE VARIACION";
            String mensaje="El costo de la orden de produccion " +docNum + " es menor al -5% de variacion, por favor revisar en aplicacion APS ";
            
            
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
            String cc = obtenerCorreoUsuario(23);//kevin
            String cc1 = "largueta@alsasa.com";
            String ccGT = "apineda@fhconsultingsa.com";
            
            MimeMessage mail = new MimeMessage(sesion);
            
            
            mail.setFrom(new InternetAddress(correoEnvia));
            
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
            //mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc1));
            //mail.addRecipients(Message.RecipientType.CC, addresses);
            mail.setSubject(asunto);
            mail.setText(mensaje);
             
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia,contrasena);
            transportar.sendMessage(mail, mail.getAllRecipients());          
            transportar.close();
        } catch (AddressException ex) {
           System.out.println("Error: envio de correo aprobado 1: "+ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println("Error: envio de correo aprobado 2: "+ex.getMessage());
        }
    }
    
    public void enviarCorreoCostoCorrecto(int docNum) throws ClassNotFoundException, SQLException{
        try {    
            int slpCode=0;
            
            String asunto = "COSTO DE OP " +docNum + " CORRECTO";
            String mensaje="El costo de la orden de produccion " +docNum + " no tiene una variacion menor a -5% o mayor 5%, por favor revisar en aplicacion APS ";
            
            
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
            String cc = obtenerCorreoUsuario(23);//kevin
            String cc1 = obtenerCorreoUsuario(15);//mario
            String cc2 = obtenerCorreoUsuario(43);//aux costos
            
            MimeMessage mail = new MimeMessage(sesion);
            
            
            mail.setFrom(new InternetAddress(correoEnvia));
            
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc1));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc2));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia,contrasena);
            transportar.sendMessage(mail, mail.getAllRecipients());          
            transportar.close();
        } catch (AddressException ex) {
           System.out.println("Error: envio de correo aprobado 1: "+ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println("Error: envio de correo aprobado 2: "+ex.getMessage());
        }
    }
    
    public void enviarCorreoCostoAutorizado(int docNum) throws ClassNotFoundException, SQLException{
        try {    
            int slpCode=0;
            DaoOWOR daoOWOR = new DaoOWOR();
            String asunto = "OP " +docNum + " Autorizada";
            String mensaje="Orden de produccion " + docNum + " autorizada por Gerencia de producción:  ";
            mensaje+="---------------------------------------------------------------------------- ";
            mensaje+="Comentario: " + daoOWOR.obtenerComentario(docNum)+" ";
            mensaje+=" Por favor revisar en aplicacion APS";
            
            
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
            String cc = obtenerCorreoUsuario(23);//kevin
            String cc1 = obtenerCorreoUsuario(15);//mario
            String cc2 = obtenerCorreoUsuario(43);//aux costos
            
            MimeMessage mail = new MimeMessage(sesion);
            
            
            mail.setFrom(new InternetAddress(correoEnvia));
            
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc1));
            mail.addRecipient(Message.RecipientType.TO, new InternetAddress (cc2));
            mail.setSubject(asunto);
            mail.setText(mensaje);
            Transport transportar = sesion.getTransport("smtp");
            transportar.connect(correoEnvia,contrasena);
            transportar.sendMessage(mail, mail.getAllRecipients());          
            transportar.close();
        } catch (AddressException ex) {
           System.out.println("Error: envio de correo aprobado 1: "+ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println("Error: envio de correo aprobado 2: "+ex.getMessage());
        }
    }
}
