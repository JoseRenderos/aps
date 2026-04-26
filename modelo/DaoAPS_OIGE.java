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

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoAPS_OIGE extends ConexionSDO implements CRUD{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Usuario us;
    APS_OIGE APS_OIGE; 
    APS_OIGE1 APS_OIGE1;
    APS_OIGE2 APS_OIGE2;
    OWOR OWOR;
    String fecha;

    @Override
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            ps=con().prepareStatement("INSERT INTO APS_OIGE(idAPS_OWOR, codeEmp, nomEmp, actividad, descActividad,idUsuario) "+ 
                                      "VALUES((SELECT idAPS_OWOR FROM APS_OWOR WHERE docNum=?),?,?,?,?,?)");
            ps.setInt(1,APS_OIGE.getAPS_OWOR().getDocnum());
            ps.setInt(2,APS_OIGE.getCodeEmp());
            ps.setString(3,APS_OIGE.getNomEmp());
            ps.setString(4,APS_OIGE.getActividad());
            ps.setString(5,APS_OIGE.getDescActividad().equals("Sin descripcion")?null:APS_OIGE.getDescActividad());
            ps.setInt(6,APS_OIGE.getUsuario().getIdUsuario());
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.insertar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int insertarGrupo(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            ps=con().prepareStatement("INSERT INTO APS_OIGE(idAPS_OWOR, codeEmp, nomEmp, actividad, descActividad,idUsuario, asigGrupal) "+ 
                                      "VALUES((SELECT idAPS_OWOR FROM APS_OWOR WHERE docNum=?),?,?,?,?,?,1)");
            ps.setInt(1,APS_OIGE.getAPS_OWOR().getDocnum());
            ps.setInt(2,APS_OIGE.getCodeEmp());
            ps.setString(3,APS_OIGE.getNomEmp());
            ps.setString(4,APS_OIGE.getActividad());
            ps.setString(5,APS_OIGE.getDescActividad().equals("Sin descripcion")?null:APS_OIGE.getDescActividad());
            ps.setInt(6,APS_OIGE.getUsuario().getIdUsuario());
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.insertarGrupo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    @Override
    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
           // if(APS_OIGE.getActividad().equals("126MLINEA1") && APS_OIGE.getActividad().contains("106")){
                String fin=APS_OIGE.getFin().replace(" ", "T");
                ps=con().prepareStatement("UPDATE APS_OIGE "+ 
                                          "SET fin=CAST(? AS DATETIME), "+ 
                                          "totalTiempo=?, "+ 
                                          "totalTiempoMuerto=?, "+ 
                                          "estado=1 "+ 
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC) ");
                ps.setString(1, fin);
                ps.setString(2, APS_OIGE.getTotalTiempo());
                ps.setString(3, APS_OIGE.getTotalTiempoMuerto());
                ps.setInt(4, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(5, APS_OIGE.getActividad());
                ps.setInt(6, APS_OIGE.getCodeEmp());
                ps.setString(7,APS_OIGE.getDescActividad().equals("Sin descripcion")?"":APS_OIGE.getDescActividad());
            /*}else{
                String fin=APS_OIGE.getFin().replace(" ", "T");
                ps=con().prepareStatement("UPDATE APS_OIGE "+ 
                                          "SET fin=CAST(? AS DATETIME), "+ 
                                          "totalTiempo=?, "+ 
                                          "totalTiempoMuerto=?, "+ 
                                          "estado=1 "+ 
                                          "WHERE idAPS_OWOR=(SELECT idAPS_OWOR FROM APS_OWOR WHERE docNum=?) AND actividad=? AND codeEmp=? AND ISNULL(descActividad,' ')=? AND estado=0");
                ps.setString(1, fin);
                ps.setString(2, APS_OIGE.getTotalTiempo());
                ps.setString(3, APS_OIGE.getTotalTiempoMuerto());
                ps.setInt(4, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(5, APS_OIGE.getActividad());
                ps.setInt(6, APS_OIGE.getCodeEmp());
                ps.setString(7, APS_OIGE.getDescActividad().equals("Sin descripcion")?" ":APS_OIGE.getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.modificar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    @Override
    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            ps=con().prepareStatement("DELETE FROM APS_OIGE3 "+ 
                                      "WHERE idAPS_OIGE=(SELECT TOP 1 idAPS_OIGE FROM APS_OIGE WHERE actividad=? AND codeEmp=? AND idAPS_OWOR=(SELECT idAPS_OWOR FROM APS_OWOR WHERE docNum=?) AND estado=0 AND ISNULL(descActividad,'')=? )");
            ps.setString(1, APS_OIGE.getActividad());
            ps.setInt(2, APS_OIGE.getCodeEmp());
            ps.setInt(3, APS_OIGE.getAPS_OWOR().getDocnum());
            ps.setString(4,APS_OIGE.getDescActividad().equals("Sin descripcion")?"":APS_OIGE.getDescActividad());
            res=ps.executeUpdate();
            if (res>0) {
                ps=con().prepareStatement("DELETE FROM APS_OIGE "+ 
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 idAPS_OIGE FROM APS_OIGE WHERE actividad=? AND codeEmp=? AND idAPS_OWOR=(SELECT idAPS_OWOR FROM APS_OWOR WHERE docNum=?) AND estado=0 AND ISNULL(descActividad,'')=? ) ");
                ps.setString(1, APS_OIGE.getActividad());
                ps.setInt(2, APS_OIGE.getCodeEmp());
                ps.setInt(3, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(4,APS_OIGE.getDescActividad().equals("Sin descripcion")?"":APS_OIGE.getDescActividad());
                res=ps.executeUpdate();   
            }
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.eliminar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    
    public int actualizarEdicion(int docNum) throws ClassNotFoundException, SQLException {
       try{
            ps=con().prepareStatement("UPDATE APS_OWOR SET edicion=0 WHERE docNum=?");
            ps.setInt(1, docNum);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.actualizarEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int guardarEdicion(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            String inicio=APS_OIGE.getInicio()+":00";
            String fin=APS_OIGE.getFin()+":00";
            String medioDia=mostrarTiempo()+" 11:59:59";

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");

            java.util.Date dateI = new java.util.Date();
            Calendar fechaI = new GregorianCalendar();

            java.util.Date dateF = new java.util.Date();
            Calendar fechaF = new GregorianCalendar();

            java.util.Date dateM = new java.util.Date();
            Calendar fechaM = new GregorianCalendar();

            dateI = sdf.parse(inicio.replaceAll("T", " "));
            fechaI.setTime(dateI);

            dateF = sdf.parse(fin.replaceAll("T", " "));
            fechaF.setTime(dateF);
            
            dateM = sdf.parse(medioDia);
            fechaM.setTime(dateM);
            if (dateI.before(dateM) && dateF.after(dateM)) {
                ps=con().prepareStatement("UPDATE APS_OIGE SET "+
                                        "inicio=CAST(? AS DATETIME), "+
                                        "fin=CAST(? AS DATETIME), "+
                                        "totalTiempo=DATEDIFF(MINUTE,CAST(? AS DATETIME),CAST(? AS DATETIME))-60, "+
                                        "totalTiempoMuerto=0, "+ 
                                        "estado=1, " +
                                        "uniTotales=?, "+
                                        "uniTotalesConformes=?, "+
                                        "fechaEdicion=GETDATE() "+
                                        "WHERE inicio IS NULL AND idAPS_OWOR=(SELECT T0.idAPS_OWOR FROM APS_OWOR T0 WHERE edicion=2 AND T0.docNum=?)");
                
 
                ps.setString(1, inicio);
                ps.setString(2, fin);
                ps.setString(3, inicio);
                ps.setString(4, fin);
                ps.setInt(5, APS_OIGE.getTotalUnidades());
                ps.setInt(6, APS_OIGE.getTotalUnidadesConformes());
                ps.setInt(7, APS_OIGE.getAPS_OWOR().getDocnum());
                res=ps.executeUpdate();
                if (res!=0) {
                    actualizarEdicion(APS_OIGE.getAPS_OWOR().getDocnum());
                }
            } else {
                ps=con().prepareStatement("UPDATE APS_OIGE SET "+
                                        "inicio=CAST(? AS DATETIME), "+
                                        "fin=CAST(? AS DATETIME), "+
                                        "totalTiempo=DATEDIFF(MINUTE,CAST(? AS DATETIME),CAST(? AS DATETIME)), "+
                                        "totalTiempoMuerto=0, "+ 
                                        "estado=1, " +
                                        "uniTotales=?, "+
                                        "uniTotalesConformes=?, "+
                                        "fechaEdicion=GETDATE() "+
                                        "WHERE inicio IS NULL AND idAPS_OWOR=(SELECT T0.idAPS_OWOR FROM APS_OWOR T0 WHERE edicion=2 AND T0.docNum=?)");
 
                ps.setString(1, inicio);
                ps.setString(2, fin);
                ps.setString(3, inicio);
                ps.setString(4, fin);
                ps.setInt(5, APS_OIGE.getTotalUnidades());
                ps.setInt(6, APS_OIGE.getTotalUnidadesConformes());
                ps.setInt(7, APS_OIGE.getAPS_OWOR().getDocnum());
                res=ps.executeUpdate();
                if (res!=0) {
                    actualizarEdicion(APS_OIGE.getAPS_OWOR().getDocnum());
                }
            }
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.guardarEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    
    
    public String mostrarTiempo() throws ClassNotFoundException, SQLException{
        String Tiempo="";
        try{
            ps = super.con().prepareStatement("SELECT CONVERT(VARCHAR(10),GETDATE(),120)");
            rs = ps.executeQuery();
            while(rs.next()){
               Tiempo = rs.getString(1); 
            } 
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.mostrarTiempo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return Tiempo;
    }
    
    public int GuardarUniTotales(int numOrden, String actividad, int codeEmp,String descActividad) throws ClassNotFoundException, SQLException {//Metodo para seleccioanr todo el tiempo muerto
        int uniTotales=UniTotales(numOrden,actividad,codeEmp);
        try{
            ps = super.con().prepareStatement("UPDATE APS_OIGE "+ 
                                              "SET  uniTotales=? "+ 
                                              "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC) ");
                ps.setInt(1,uniTotales);
                ps.setInt(2,numOrden);
                ps.setString(3,actividad);
                ps.setInt(4,codeEmp);
                ps.setString(5,descActividad.equals("Sin descripcion")?"":descActividad);
                res = ps.executeUpdate();
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.GuardarUniTotales(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int iniciarTiempo(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            //if(APS_OIGE.getActividad().equals("126MLINEA1") && APS_OIGE.getActividad().contains("106")){
                String inicio=APS_OIGE.getInicio().replace(" ", "T");
                ps=con().prepareStatement("UPDATE APS_OIGE "+ 
                                          "SET inicio=CAST(? AS DATETIME) "+ 
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC) ");
                ps.setString(1, inicio);
                ps.setInt(2, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE.getActividad());
                ps.setInt(4, APS_OIGE.getCodeEmp());
                ps.setString(5,APS_OIGE.getDescActividad().equals("Sin descripcion")?"":APS_OIGE.getDescActividad());
            /*}else{
                String inicio=APS_OIGE.getInicio().replace(" ", "T");
                ps=con().prepareStatement("UPDATE APS_OIGE "+ 
                                          "SET inicio=CAST(? AS DATETIME) "+ 
                                          "WHERE idAPS_OWOR=(SELECT idAPS_OWOR FROM APS_OWOR WHERE docNum=?) AND actividad=? AND codeEmp=? AND ISNULL(descActividad,' ')=? AND estado=0");
                ps.setString(1, inicio);
                ps.setInt(2, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE.getActividad());
                ps.setInt(4, APS_OIGE.getCodeEmp());
                ps.setString(5,APS_OIGE.getDescActividad().equals("Sin descripcion")?" ":APS_OIGE.getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.iniciarTiempo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int estadoPausa(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            //if(APS_OIGE.getActividad().equals("126MLINEA1") && APS_OIGE.getActividad().contains("106")){
                ps=con().prepareStatement("UPDATE APS_OIGE " +
                                          "SET estadoPausa=1 " +
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC)");
                ps.setInt(1, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(2, APS_OIGE.getActividad());
                ps.setInt(3, APS_OIGE.getCodeEmp());
                ps.setString(4,APS_OIGE.getDescActividad().equals("Sin descripcion")?"":APS_OIGE.getDescActividad());
            /*}else{
                ps=con().prepareStatement("UPDATE APS_OIGE " +
                                          "SET estadoPausa=1 " +
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND ISNULL(descActividad,' ')=? AND T0.estado=0 ORDER BY T0.idAPS_OIGE DESC)");
                ps.setInt(1, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(2, APS_OIGE.getActividad());
                ps.setInt(3, APS_OIGE.getCodeEmp());
                ps.setString(4,APS_OIGE.getDescActividad().equals("Sin descripcion")?" ":APS_OIGE.getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.estadoPausa(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int estadoPausa1(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE=(APS_OIGE)Ob;
            //if(APS_OIGE.getActividad().equals("126MLINEA1") && APS_OIGE.getActividad().contains("106")){
                ps=con().prepareStatement("UPDATE APS_OIGE " +
                                          "SET estadoPausa=0 " +
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC)");
                ps.setInt(1, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(2, APS_OIGE.getActividad());
                ps.setInt(3, APS_OIGE.getCodeEmp());
                ps.setString(4,APS_OIGE.getDescActividad().equals("Sin descripcion")?"":APS_OIGE.getDescActividad());
            /*}else{
                ps=con().prepareStatement("UPDATE APS_OIGE " +
                                          "SET estadoPausa=0 " +
                                          "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND ISNULL(descActividad,' ')=? AND T0.estado=0 ORDER BY T0.idAPS_OIGE DESC)");
                ps.setInt(1, APS_OIGE.getAPS_OWOR().getDocnum());
                ps.setString(2, APS_OIGE.getActividad());
                ps.setInt(3, APS_OIGE.getCodeEmp());
                ps.setString(4, APS_OIGE.getDescActividad().equals("Sin descripcion")?" ":APS_OIGE.getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.estadoPausa1(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int GuardarUniTotalesConformes(int numOrden, String actividad, int codeEmp,String descActividad) throws ClassNotFoundException, SQLException {//Metodo para seleccioanr todo el tiempo muerto
        int uniTotalesConformes=UniTotalesConformes(numOrden,actividad,codeEmp);
        try{
            ps = super.con().prepareStatement("UPDATE APS_OIGE "+ 
                                              "SET  uniTotalesConformes=? "+ 
                                              "WHERE idAPS_OIGE=(SELECT TOP 1 T0.idAPS_OIGE from APS_OIGE T0 JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC) ");
                ps.setInt(1,uniTotalesConformes);
                ps.setInt(2,numOrden);
                ps.setString(3,actividad);
                ps.setInt(4,codeEmp);
                ps.setString(5,descActividad.equals("Sin descripcion")?"":descActividad);
                res = ps.executeUpdate();
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.GuardarUniTotalesConformes(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int guardarActividadProd(int idAPS_OIGE, String codeAct, String actividad) throws ClassNotFoundException, SQLException {//Metodo para guardar las actividades de produccion
        try{
            ps = super.con().prepareStatement("UPDATE APS_OIGE "+ 
                                              "SET codeActProd=?, "+ 
                                              " descActividadProd=?, estadoCarga=0 "+ 
                                              "WHERE idAPS_OIGE=?");
                ps.setString(1,codeAct);
                ps.setString(2,actividad);
                ps.setInt(3,idAPS_OIGE);
                res = ps.executeUpdate();
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.guardarActividadProd(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<APS_OIGE> mostrarEmision() throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        fecha=fecha();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "  T0.docNum, " +
                                              "  T0.itemCode, " +
                                              "  T0.itemName, " +
                                              "  T0.PlannedQty, " +
                                              "  T1.nomEmp, " +
                                              "  T1.actividad, " +
                                              "  ISNULL(T1.descActividad, 'Sin descripcion') 'descActividad', "+ 
                                              "  ISNULL(CONVERT(VARCHAR(19),T1.inicio,120),'No iniciada') 'inicio', " +
                                              "  ISNULL(CONVERT(VARCHAR(19),T1.fin,120),'No finalizada') 'fin', " +
                                              "  ISNULL(CONVERT(numeric(19,6), T1.totalTiempo)/60,0) 'totalTiempo', " +
                                              "  ISNULL(CONVERT(numeric(19,6), T1.totalTiempoMuerto)/60,0) 'totalTiempo', " +
                                              "  T1.estado " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR WHERE T0.startDate>=?");
            ps.setString(1, fecha);
            rs = ps.executeQuery();
            while(rs.next()){
                OWOR = new OWOR();
                OWOR.setDocnum(rs.getInt(1));
                OWOR.setItemcode(rs.getString(2));
                OWOR.setItemname(rs.getString(3));
                OWOR.setCantpln(rs.getString(4));
                APS_OIGE = new APS_OIGE();
                APS_OIGE.setNomEmp(rs.getString(5));
                APS_OIGE.setActividad(rs.getString(6));
                APS_OIGE.setDescActividad(rs.getString(7));
                APS_OIGE.setInicio(rs.getString(8));
                APS_OIGE.setFin(rs.getString(9));
                APS_OIGE.setTotalTiempo(rs.getString(10));
                APS_OIGE.setTotalTiempoMuerto(rs.getString(11));
                APS_OIGE.setEstado(rs.getInt(12));
                APS_OIGE.setAPS_OWOR(OWOR);
                ar.add(APS_OIGE);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.mostrarEmision(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OIGE> mostrarActividades(int numOrden) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        fecha=fecha();
        try {
            ps=super.con().prepareStatement("SELECT  " +
"    T1.codeEmp,  " +
"    T1.nomEmp,  " +
"    T1.actividad,  " +
"    ISNULL(T1.descActividad, 'Sin descripcion') 'descActividad',  " +
"    FORMAT(T1.inicio, 'yyyy-MM-dd HH:mm:ss tt') 'inicio',  " +
"    FORMAT(T1.fin, 'yyyy-MM-dd HH:mm:ss tt') 'fin',  " +
"    T1.uniTotalesConformes,  " +
"    T1.idAPS_OIGE,  " +
"    ISNULL((  " +
"    SELECT TOP 1  " +
"        CONVERT(numeric(19,2),(K1.Qauntity / K0.Quantity)) 'EstadarH'  " +
"    FROM SBO_ALSASA..ITT1 K0  " +
"    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0. FATHER COLLATE SQL_Latin1_General_CP850_CI_AS  " +
"    WHERE K0.Type = 290  " +
"    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'  " +
"    AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS  " +
"    AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS  " +
"    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ),0) 'estandarH',    " +
"    CASE     " +
"        WHEN     " +
"            (CASE    " +
"                WHEN    " +
"                    (SELECT TOP 1     " +
"                        CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
"                    FROM SBO_ALSASA..ITT1 K0     " +
"                    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
"                    WHERE K0.Type = 290     " +
"                    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
"                    AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"					AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS    " +
"                    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ) IS NULL    " +
"                THEN    " +
"                    (SELECT TOP 1     " +
"                        CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
"                    FROM SBO_ALSASA..ITT1 K0     " +
"                    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
"                    WHERE K0.Type = 290     " +
"                    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
"                    AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS      " +
"                    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR     " +
"                    K0.Type = 290)    " +
"                ELSE    " +
"                    (SELECT TOP 1     " +
"                        CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
"                    FROM SBO_ALSASA..ITT1 K0     " +
"                    JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
"                    WHERE K0.Type = 290     " +
"                    AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
"                    AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS   " +
"					AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"                    AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS )    " +
"            END) IS NULL" +
"			OR ISNULL(CASE  " +
"				WHEN LEFT(T1.actividad,3)='114' " +
"				THEN CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OWOR=T1.idAPS_OWOR)) " +
"				ELSE CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T1.idAPS_OIGE))  " +
"			END,0) =0" +
"    THEN     " +
"        0     " +
"    ELSE     " +
"        CONVERT(numeric(19,2),     " +
"            (ISNULL((     " +
"                CASE  " +
"					WHEN LEFT(T1.actividad,3)='114' " +
"					THEN ISNULL((SELECT CONVERT(NUMERIC(19,6),SUM(K1.uniConformes)) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.codeEmp=T1.codeEmp AND K0.idAPS_OWOR=T1.idAPS_OWOR),0) " +
"					ELSE ISNULL((SELECT CONVERT(NUMERIC(19,6),SUM(K1.uniConformes)) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.codeEmp=T1.codeEmp AND K0.idAPS_OIGE=T1.idAPS_OIGE),0) " +
"				END " +
"            /     " +
"            (ISNULL(CASE  " +
"				WHEN LEFT(T1.actividad,3)='114' " +
"				THEN CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OWOR=T1.idAPS_OWOR)) " +
"				ELSE CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T1.idAPS_OIGE))  " +
"			END,1) " +
"                *     " +
"            ISNULL((     " +
"                CASE    " +
"                    WHEN    " +
"                        (SELECT TOP 1     " +
"                            CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
"                        FROM SBO_ALSASA..ITT1 K0     " +
"                        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
"                        WHERE K0.Type = 290     " +
"                        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
"                        AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"						AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS    " +
"                        AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ) IS NULL    " +
"                    THEN    " +
"                        (SELECT TOP 1     " +
"                            CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
"                        FROM SBO_ALSASA..ITT1 K0     " +
"                        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
"                        WHERE K0.Type = 290     " +
"                        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
"						AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS  " +
"                        AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR     " +
"                        K0.Type = 290)    " +
"                    ELSE     " +
"                        (SELECT TOP 1     " +
"                            CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'     " +
"                        FROM SBO_ALSASA..ITT1 K0     " +
"                        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER     " +
"                        WHERE K0.Type = 290     " +
"                        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'     " +
"                        AND K0.CODE=T1.actividad COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"						AND ISNULL(K0.Comment,'')=ISNULL(T1.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS    " +
"                        AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS )     " +
"                END     " +
"        ),1))     " +
"        ),0)*100)     " +
"    )     " +
"END 'Eficiencia'   " +
"FROM SDO_ALSASA..APS_OWOR T0  " +
"JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
"WHERE T0.docNum=?");
            ps.setInt(1, numOrden);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OIGE = new APS_OIGE();
                APS_OIGE.setCodeEmp(rs.getInt(1));
                APS_OIGE.setNomEmp(rs.getString(2));
                APS_OIGE.setActividad(rs.getString(3));
                APS_OIGE.setDescActividad(rs.getString(4));
                APS_OIGE.setInicio(rs.getString(5));
                APS_OIGE.setFin(rs.getString(6));
                APS_OIGE.setTotalUnidadesConformes(rs.getInt(7));
                APS_OIGE.setIdAPS_OIGE(rs.getInt(8));
                APS_OIGE.setEstado(rs.getInt(9)); 
                APS_OIGE.setDescActividadProd(rs.getString(10)); 
                ar.add(APS_OIGE);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.mostrarActividades(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OIGE> mostrarActividadesRecibo(int numOrden) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        fecha=fecha();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "  T1.nomEmp, " +
                                              "  T1.actividad, " +
                                              "  ISNULL(T1.descActividad, 'Sin descripcion') 'descActividad', " +
                                              "  CASE " +
                                              "     WHEN " +
                                              "		STUFF(( " +
                                              "             SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada " +
                                              "             FROM SDO_ALSASA..APS_OIGE3 K0 " +
                                              "             JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                              "             WHERE K0.idAPS_OIGE=T1.idAPS_OIGE FOR XML PATH ('')) " +
                                              "		,1,2,'') IS NULL " +
                                              "     THEN (SELECT CONCAT(SUBSTRING(K0.insumo,0, CHARINDEX('-', K0.insumo)-1)  ,' Cantidad Asig: ', K0.cantAsignada) FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OIGE=T1.idAPS_OIGE) " +
                                              "     ELSE " +
                                              "		STUFF(( " +
                                              "             SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada " +
                                              "             FROM SDO_ALSASA..APS_OIGE3 K0 " +
                                              "             JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                              "             WHERE K0.idAPS_OIGE=T1.idAPS_OIGE FOR XML PATH ('')) " +
                                              "		,1,2,'') " +
                                              "  END 'insumo', " +
                                              "  FORMAT( T1.inicio, 'yyyy-MM-dd HH:mm:ss tt') 'inicio', " +
                                              "  FORMAT( T1.fin, 'yyyy-MM-dd HH:mm:ss tt') 'fin', " +
                                              "  T1.uniTotalesConformes, " +
                                              "  (SELECT SUM(K0.uniNoConformes+K0.uniRechazadas) FROM SDO_ALSASA..APS_OIGE1 K0 WHERE K0.idAPS_OIGE=T1.idAPS_OIGE) 'noConformes', " +
                                              "  CONVERT(numeric(19,6),CONVERT(numeric(19,6), T1.totalTiempo)/60) 'TotalTiempo', " +
                                              "  T1.estadoCarga, " +
                                              "  ISNULL(STUFF((SELECT ', '+K0.comentario " +
                                              "	  FROM SDO_ALSASA..APS_OIGE1 K0 " +
                                              "	  JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                              "	  WHERE K0.comentario!='' AND K0.comentario IS NOT NULL AND K1.idAPS_OIGE=T1.idAPS_OIGE FOR XML PATH ('')),1,2,''),'') 'Comentarios' "+
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T0.docNum=?");
            ps.setInt(1, numOrden);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OIGE = new APS_OIGE();
                APS_OIGE.setNomEmp(rs.getString(1));
                APS_OIGE.setActividad(rs.getString(2));
                APS_OIGE.setDescActividad(rs.getString(3));
                APS_OIGE.setInsumo(rs.getString(4));
                APS_OIGE.setInicio(rs.getString(5));
                APS_OIGE.setFin(rs.getString(6));            
                APS_OIGE.setTotalUnidadesConformes(rs.getInt(7));
                APS_OIGE.setTotalUnidadesNoConformes(rs.getInt(8));
                APS_OIGE.setTotalTiempo(rs.getString(9));
                APS_OIGE.setEstado(rs.getInt(10));
                APS_OIGE.setDescActividadProd(rs.getString(11));
                ar.add(APS_OIGE);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.mostrarActividadesRecibo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OIGE> mostrarInfoEmision(int numOrden, String nomEmp, String actividad, String inicio) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try {
            ps=super.con().prepareStatement("SELECT " +
                                            "	CASE " +
                                            "		WHEN " +
                                            "			STUFF(( " +
                                            "			SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada " +
                                            "			FROM SDO_ALSASA..APS_OIGE3 K0 " +
                                            "			JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                            "			WHERE K0.idAPS_OIGE=T0.idAPS_OIGE FOR XML PATH ('')) " +
                                            "			,1,2,'') IS NULL " +
                                            "		THEN (SELECT CONCAT(SUBSTRING(K0.insumo,0, CHARINDEX('-', K0.insumo)-1)  ,' Cantidad Asig: ', K0.cantAsignada) FROM SDO_ALSASA..APS_OIGE K0 WHERE K0.idAPS_OIGE=T0.idAPS_OIGE) " +
                                            "		ELSE " +
                                            "			STUFF(( " +
                                            "			SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada " +
                                            "			FROM SDO_ALSASA..APS_OIGE3 K0 " +
                                            "			JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                            "			WHERE K0.idAPS_OIGE=T0.idAPS_OIGE FOR XML PATH ('')) " +
                                            "			,1,2,'') " +
                                            "	END 'insumo', " +
                                            "	T0.uniTotales, " +
                                            "	T0.uniTotalesConformes, " +
                                            "	ISNULL(T0.descActividad,'Sin descripcion') 'descActividad', " +
                                            "	ISNULL(( " +
                                            "	SELECT TOP 1 " +
                                            "		CONVERT(VARCHAR,CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3))) 'EstadarH' " +
                                            "	FROM SBO_ALSASA..ITT1 K0 " +
                                            "	JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER " +
                                            "	COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	WHERE K0.Type = 290 " +
                                            "	AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "	AND K0.CODE=T0.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	AND K0.Comment=T0.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "        K0.Type = 290 " +
                                            "        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "        AND K0.CODE=T0.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "        AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "        AND K0.Comment IS NULL " +
                                            "	),'') 'estandarH', " +
                                            "    CONVERT(numeric(19,2), " +
                                            "        (ISNULL((T0.uniTotales/(CONVERT(numeric(19,6),CONVERT(numeric(19,6), T0.totalTiempo)/60)*ISNULL(( " +
                                            "        SELECT TOP 1 " +
                                            "            CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "        FROM SBO_ALSASA..ITT1 K0 " +
                                            "        JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER " +
                                            "        WHERE K0.Type = 290 " +
                                            "        AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "        AND K0.CODE=T0.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "        AND K0.Comment=T0.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "        AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "            K0.Type = 290 " +
                                            "            AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "            AND K0.CODE=T0.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "            AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "            AND K0.Comment IS NULL " +
                                            "        ),0))),0)*100) " +
                                            "    ) 'Eficiencia' " +
                                            "FROM SDO_ALSASA..APS_OIGE T0 " +
                                            "JOIN SDO_ALSASA..APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR " +
                                            "WHERE T1.docNum=? AND T0.nomEmp like ? AND T0.actividad=? AND CONVERT(CHAR(19),T0.inicio,120)=?");
            ps.setInt(1, numOrden);
            ps.setString(2, '%' + nomEmp + '%');
            ps.setString(3, actividad);
            ps.setString(4, inicio);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OIGE = new APS_OIGE();
                APS_OIGE.setInsumo(rs.getString(1));
                APS_OIGE.setTotalUnidades(rs.getInt(2));
                APS_OIGE.setTotalUnidadesConformes(rs.getInt(3));
                APS_OIGE.setDescActividad(rs.getString(4));
                APS_OIGE.setInicio(rs.getString(5));
                APS_OIGE.setDescActividadProd(rs.getString(6));
                ar.add(APS_OIGE);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE.mostrarInfoEmision(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int comprobar(int numOrden, String actividad) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "  count(idAPS_OIGE) 'cantidad' "+ 
                                              "FROM SDO_ALSASA..APS_OIGE T0 "+ 
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR "+ 
                                              "WHERE T1.docNum=? AND T0.actividad=? AND T0.estado=1");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                rs = ps.executeQuery();
                while(rs.next()){
                   cantidad=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.comprobar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    public ArrayList<OWOR> procesosOrdenAPS() throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        ArrayList<OWOR> ar = new ArrayList<OWOR>();
        try{
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                              "  T0.docNum, " +
                                              "  COUNT(T2.actividad) " +
                                              "FROM SDO_ALSASA..APS_OWOR T0 " +
                                              "JOIN (SELECT DISTINCT K0.docNum,K1.actividad,K1.descActividad FROM SDO_ALSASA..APS_OWOR K0 JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OWOR=K0.idAPS_OWOR) T2 ON T2.docnum=T0.docNum " +
                                              "WHERE T0.startDate>=? " +
                                              "GROUP BY T0.docnum) ");
                ps.setString(1,fecha());
                rs = ps.executeQuery();
                while(rs.next()){
                    OWOR = new OWOR();
                    OWOR.setDocnum(rs.getInt(1));
                    OWOR.setCantProcesos(rs.getString(2));
                    ar.add(OWOR);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.procesosOrdenAPS(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int comprobarEmpleado(int codeEmp) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "  count(idAPS_OIGE) 'cantidad'" +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "WHERE T0.codeEmp=? AND T0.estado=0 AND estadoPausa=0");
                ps.setInt(1,codeEmp);
                rs = ps.executeQuery();
                while(rs.next()){
                   cantidad=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.comprobarEmpleado(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    public ArrayList<APS_OIGE> codesEmpleados(int numOrden) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        ArrayList<APS_OIGE> ar= new ArrayList<APS_OIGE>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "  T0.codeEmp, " +
                                              "  T0.nomEmp " +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "WHERE T0.idAPS_OWOR=(Select K0.idAPS_OWOR FROM SDO_ALSASA..APS_OWOR K0 WHERE K0.docNum=?) AND T0.estado=0 AND T0.asigGrupal=1");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                    APS_OIGE = new APS_OIGE();
                    APS_OIGE.setCodeEmp(rs.getInt(1));
                    APS_OIGE.setNomEmp(rs.getString(2));
                    ar.add(APS_OIGE);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.codesEmpleados(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int comprobarCantAsignadaEmpleado(int numOrden, String actividad, int codeEmp) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              " ISNULL(CAST((SELECT TOP 1 K0.cantAsignada FROM SDO_ALSASA..APS_OIGE3 K0 WHERE K0.idAPS_OIGE=T0.idAPS_OIGE AND K0.itemCode NOT LIKE '%SMED%' ORDER BY K0.cantAsignada ASC) AS FLOAT),0) 'total' " +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "WHERE T1.docNum=? AND T0.codeEmp=? AND T0.actividad=? AND T0.estado=0 ");
            ps.setInt(1,numOrden);
            ps.setInt(2,codeEmp);
            ps.setString(3,actividad);
            rs = ps.executeQuery();
            while(rs.next()){
               cantidad=rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.comprobarCantAsignadaEmpleado(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    
    public int comprobarCantAsignadaEdicion(int numOrden) throws ClassNotFoundException, SQLException {
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              " ISNULL(CAST((SELECT TOP 1 K0.cantAsignada FROM SDO_ALSASA..APS_OIGE3 K0 WHERE K0.idAPS_OIGE=T0.idAPS_OIGE ORDER BY K0.cantAsignada ASC) AS FLOAT),0) 'total' " +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "WHERE T1.docNum=? AND T1.edicion=2 AND T0.inicio IS NULL AND T0.estado=0 ");
            ps.setInt(1,numOrden);
            rs = ps.executeQuery();
            while(rs.next()){
               cantidad=rs.getInt(1);
            }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.comprobarCantAsignadaEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    public int comprobarCantAsignada(int numOrden, String insumo, String actividad) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "   ISNULL(SUM(CAST(T2.cantAsignada AS FLOAT)),0) 'total' " +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "JOIN SDO_ALSASA..APS_OIGE3 T2 ON T2.idAPS_OIGE=T0.idAPS_OIGE " +
                                              "WHERE T1.docNum=? AND T2.itemCode=? AND " +
                                              "T0.actividad NOT LIKE '%MLINEA%' AND " +
                                              "T0.actividad NOT LIKE '%MPRENSAR%' AND " +
                                              "T0.actividad NOT LIKE '%Mpulir%' AND " +
                                              "T0.actividad NOT LIKE '%MFUNDIR%' AND " +
                                              "T0.actividad NOT LIKE '%MPERFORAR%' AND T0.actividad=?");
                ps.setInt(1,numOrden);
                ps.setString(2,insumo);
                ps.setString(3,actividad);  
                rs = ps.executeQuery();
                while(rs.next()){
                   cantidad=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.comprobarCantAsignada(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    public int comprobarUniTotales(int numOrden, String actividad, int codeEmp,String descActividad) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int total=0;
        try{
            //if(actividad.equals("126MLINEA1") && actividad.contains("106")){
                ps = super.con().prepareStatement("SELECT " +
                                              "ISNULL(SUM(T0.uniTotales),0) 'total' " +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "WHERE T1.docNum=? AND T0.actividad=? and codeEmp=? AND T0.estado=0");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                ps.setInt(3,codeEmp);
            /*}else{
                ps = super.con().prepareStatement("SELECT " +
                                              "ISNULL(SUM(T0.uniTotales),0) 'total' " +
                                              "FROM  SDO_ALSASA..APS_OIGE T0 " +
                                              "JOIN SDO_ALSASA..APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "WHERE T1.docNum=? AND T0.actividad=? and codeEmp=? AND ISNULL(descActividad,' ')=? AND T0.estado=0");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                ps.setInt(3,codeEmp);
                ps.setString(4,descActividad.equals("Sin descripcion")?" ":descActividad);
            }*/
                rs = ps.executeQuery();
                while(rs.next()){
                   total=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.comprobarUniTotales(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return total;
    }
    
    public String totalTiempo(int numOrden, String actividad, int codeEmp) throws ClassNotFoundException, SQLException {//Metodo para seleccionar todo el tiempo trabajado
        String total="";
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   SUM(DATEDIFF(MINUTE, T0.inicio,T0.fin)) 'totalTiempo' "+ 
                                              "FROM SDO_ALSASA..APS_OIGE1 T0 "+ 
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE "+ 
                                              "JOIN SDO_ALSASA..APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR "+ 
                                              "WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND t1.estado=0");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                ps.setInt(3,codeEmp);
                rs = ps.executeQuery();
                while(rs.next()){
                   total=rs.getString(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.totalTiempo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return total;
    }
    
    public String totalTiempoMuerto(int numOrden, String actividad, int codeEmp) throws ClassNotFoundException, SQLException {//Metodo para seleccioanr todo el tiempo muerto
        String total="";
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "   SUM(DATEDIFF(MINUTE, T0.inicio,T0.fin)) 'totalTiempoMuerto' "+ 
                                              "FROM SDO_ALSASA..APS_OIGE2 T0 "+ 
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE "+ 
                                              "JOIN SDO_ALSASA..APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR "+ 
                                              "WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND t1.estado=0");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                ps.setInt(3,codeEmp);
                rs = ps.executeQuery();
                while(rs.next()){
                   total=rs.getString(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.totalTiempoMuerto(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return total;
    }

    
    public int UniTotales(int numOrden, String actividad, int codeEmp) throws ClassNotFoundException, SQLException {//Metodo para seleccioanr todo el tiempo muerto
        int total=0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "  SUM(T0.uniTotales) 'Total' " +
                                              "FROM SDO_ALSASA..APS_OIGE1 T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
                                              "JOIN SDO_ALSASA..APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND t1.estado=0");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                ps.setInt(3,codeEmp);
                rs = ps.executeQuery();
                while(rs.next()){
                   total=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.UniTotales(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return total;
    }
    
    public int UniTotalesConformes(int numOrden, String actividad, int codeEmp) throws ClassNotFoundException, SQLException {//Metodo para seleccioanr todo el tiempo muerto
        int total=0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "  SUM(T0.uniConformes) 'Total' " +
                                              "FROM SDO_ALSASA..APS_OIGE1 T0 " +
                                              "JOIN SDO_ALSASA..APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
                                              "JOIN SDO_ALSASA..APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND t1.estado=0");
                ps.setInt(1,numOrden);
                ps.setString(2,actividad);
                ps.setInt(3,codeEmp);
                rs = ps.executeQuery();
                while(rs.next()){
                   total=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.UniTotalesConformes(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return total;
    }
    
    public String estadoMarcacion(int codeEmp,String fecha) throws ClassNotFoundException, SQLException {//Metodo para seleccioanr todo el tiempo muerto
        String estadoMarcacion="";
        try{
            ps = super.con().prepareStatement("SELECT  " +
                                                "	CASE  " +
                                                "		WHEN T0.codigo=305 THEN 'MARCACION'  " +
                                                "		ELSE T0.status  " +
                                                "	END  " +
                                                "FROM SBO_ALSASA..VW_ESTADO_MARCACION T0  " +
                                                "WHERE T0.codigo=? AND T0.fecha=CAST(? AS DATE)");
                ps.setInt(1,codeEmp);
                ps.setString(2,fecha);
                rs = ps.executeQuery();
                while(rs.next()){
                   estadoMarcacion=rs.getString(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE.estadoMarcacion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return estadoMarcacion;
    }
    
    //METODO PARA ENCONTRAR LA FECHA DE CONSULTA(TRES MESES ATRAS)
    private String fecha(){
        Calendar c = new GregorianCalendar();
        String fecha;
        String date;
        int dia;
        dia=Calendar.DATE;
        c.add(Calendar.MONTH, -2);
        c.add(Calendar.DATE, -(dia));
        date=formatearCalendar(c);
        fecha="20"+date.replaceAll("/","");
        fecha= fecha.substring(0, fecha.length()-2);
        fecha+=fecha="01";
        
        return fecha;
    }
    
    //METODO PARA DAR FORMATO A LA FECHA
    private String formatearCalendar(Calendar c) {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.JAPANESE);
        return df.format(c.getTime());
    }
}
