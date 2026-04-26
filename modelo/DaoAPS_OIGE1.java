/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.*;
import java.sql.*;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoAPS_OIGE1 extends ConexionSDO implements CRUD{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    APS_OIGE APS_OIGE; 
    APS_OIGE1 APS_OIGE1;
    
    @Override
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE1=(APS_OIGE1)Ob;
            String inicio=APS_OIGE1.getInicio().replace(" ", "T");
            
            //if(APS_OIGE1.getAPS_OIGE().getActividad().equals("126MLINEA1") && APS_OIGE1.getAPS_OIGE().getActividad().contains("106")){
                ps=con().prepareStatement("INSERT INTO APS_OIGE1(inicio, numCaptura,idAPS_OIGE) "+ 
                                          "VALUES(CAST(? AS DATETIME),?,(SELECT TOP 1 T0.idAPS_OIGE FROM APS_OIGE T0 JOIN APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE DESC))");
                ps.setString(1,inicio);

                ps.setInt(2, APS_OIGE1.getNumCaptura());
                ps.setInt(3, APS_OIGE1.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(4,APS_OIGE1.getAPS_OIGE().getActividad());
                ps.setInt(5,APS_OIGE1.getAPS_OIGE().getCodeEmp());
                ps.setString(6,APS_OIGE1.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?"":APS_OIGE1.getAPS_OIGE().getDescActividad());
            /*}else{
            ps=con().prepareStatement("INSERT INTO APS_OIGE1(inicio, numCaptura,idAPS_OIGE) "+ 
                                      "VALUES(CAST(? AS DATETIME),?,(SELECT TOP 1 T0.idAPS_OIGE FROM APS_OIGE T0 JOIN APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR WHERE T1.docNum=? AND T0.actividad=? AND T0.codeEmp=? AND T0.estado=0 AND ISNULL(T0.descActividad, ' ')=? ORDER BY T0.idAPS_OIGE DESC))");
            ps.setString(1,inicio);
            
            ps.setInt(2, APS_OIGE1.getNumCaptura());
            ps.setInt(3, APS_OIGE1.getAPS_OIGE().getAPS_OWOR().getDocnum());
            ps.setString(4,APS_OIGE1.getAPS_OIGE().getActividad());
            ps.setInt(5,APS_OIGE1.getAPS_OIGE().getCodeEmp());
            ps.setString(6, APS_OIGE1.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?" ":APS_OIGE1.getAPS_OIGE().getDescActividad());
            }*/
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE1.insertar(): " + ex.getMessage());
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int comprobar(int numOrden) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "count(idAPS_OIGE) 'cantidad' "+ 
                                              "FROM APS_OIGE T0 "+ 
                                              "JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR "+ 
                                              "WHERE T1.docNum=? AND T0.estado=0");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   cantidad=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE1.comprobar(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    
    public int comprobarGrupo(int numOrden) throws ClassNotFoundException, SQLException {//Metodo para comprobar si existe una actividad realizandose para la orden selecccionada
        int cantidad=0;
        try{
            ps = super.con().prepareStatement("SELECT "+ 
                                              "count(idAPS_OIGE) 'cantidad' "+ 
                                              "FROM APS_OIGE T0 "+ 
                                              "JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR "+ 
                                              "WHERE T1.docNum=? AND T0.estado=0 AND T0.asigGrupal=1");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   cantidad=rs.getInt(1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE1.comprobarGrupo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return cantidad;
    }
    
    public ArrayList<APS_OIGE1> asignado(int numOrden) throws ClassNotFoundException, SQLException {//Metodo para encontrar el empleado y la actividad asignados a una orden
        ArrayList<APS_OIGE1> ar = new ArrayList<APS_OIGE1>();
        try{
            ps = super.con().prepareStatement("SELECT " +
                                              "   T0.codeEmp, " +
                                              "   T0.actividad,  " +
                                              "   T0.inicio,  " +
                                              "   (SELECT TOP 1 T2.fin FROM APS_OIGE1 T2 WHERE T2.idAPS_OIGE=T0.idAPS_OIGE ORDER BY idAPS_OIGE1 DESC) 'fin', " +
                                              "   (SELECT TOP 1 T2.numCaptura FROM APS_OIGE1 T2 WHERE T2.idAPS_OIGE=T0.idAPS_OIGE ORDER BY idAPS_OIGE1 DESC) 'NumCaptura', " +
                                              "   (SELECT TOP 1 T2.uniTotales FROM APS_OIGE1 T2 WHERE T2.idAPS_OIGE=T0.idAPS_OIGE ORDER BY idAPS_OIGE1 DESC) 'uniTotales', " +
                                              "   ISNULL(T0.descActividad,'Sin descripcion') " +
                                              "FROM APS_OIGE T0 " +
                                              "JOIN APS_OWOR T1 ON T1.idAPS_OWOR=T0.idAPS_OWOR " +
                                              "WHERE T1.docNum=? AND T0.estado=0");
                ps.setInt(1,numOrden);
                rs = ps.executeQuery();
                while(rs.next()){
                   APS_OIGE = new APS_OIGE();
                   APS_OIGE.setCodeEmp(rs.getInt(1));
                   APS_OIGE.setActividad(rs.getString(2));
                   APS_OIGE.setInicio(rs.getString(3));
                   APS_OIGE.setDescActividad(rs.getString(7));
                   APS_OIGE1= new APS_OIGE1();
                   APS_OIGE1.setFin(rs.getString(4));
                   APS_OIGE1.setNumCaptura(rs.getInt(5));
                   APS_OIGE1.setUniTotales(rs.getString(6));
                   APS_OIGE1.setAPS_OIGE(APS_OIGE);
                   ar.add(APS_OIGE1);
                }
        }catch(Exception ex){
            System.out.println("modelo.DaoAPS_OIGE1.asignado(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int modificarFin(Object Ob) throws ClassNotFoundException, SQLException {
       try{
            APS_OIGE1=(APS_OIGE1)Ob;
            String fin=APS_OIGE1.getFin().replace(" ", "T");
            //if(APS_OIGE1.getAPS_OIGE().getActividad().equals("126MLINEA1") && APS_OIGE1.getAPS_OIGE().getActividad().contains("106")){
                ps=con().prepareStatement("UPDATE APS_OIGE1 "+ 
                                          "SET fin=CAST(? AS DATETIME) "+ 
                                          "WHERE idAPS_OIGE1=(SELECT TOP 1 T0.idAPS_OIGE1 from APS_OIGE1 T0 JOIN APS_OIGE T1 ON T1.idAPS_OIGE=T0.idAPS_OIGE JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND estado=0 AND ISNULL(descActividad,'')=? ORDER BY T0.idAPS_OIGE1 DESC)");
                ps.setString(1, fin);
                ps.setInt(2, APS_OIGE1.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE1.getAPS_OIGE().getActividad());
                ps.setInt(4, APS_OIGE1.getAPS_OIGE().getCodeEmp());
                ps.setString(5,APS_OIGE1.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?"":APS_OIGE1.getAPS_OIGE().getDescActividad());
           /* }else{
                ps=con().prepareStatement("UPDATE APS_OIGE1 "+ 
                                          "SET fin=CAST(? AS DATETIME) "+ 
                                          "WHERE idAPS_OIGE1=(SELECT TOP 1 T0.idAPS_OIGE1 from APS_OIGE1 T0 JOIN APS_OIGE T1 ON T1.idAPS_OIGE=T0.idAPS_OIGE JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND ISNULL(descActividad,' ')=? AND estado=0 ORDER BY T0.idAPS_OIGE1 DESC)");
                ps.setString(1, fin);
                ps.setInt(2, APS_OIGE1.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(3, APS_OIGE1.getAPS_OIGE().getActividad());
                ps.setInt(4, APS_OIGE1.getAPS_OIGE().getCodeEmp());
                ps.setString(5, APS_OIGE1.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?" ":APS_OIGE1.getAPS_OIGE().getDescActividad());
            }*/
            
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE1.modificarFin(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int modificarUnidades(Object Ob) throws ClassNotFoundException, SQLException {
       try{
            APS_OIGE1=(APS_OIGE1)Ob;
            //if(APS_OIGE1.getAPS_OIGE().getActividad().equals("126MLINEA1") && APS_OIGE1.getAPS_OIGE().getActividad().contains("106")){
                ps=con().prepareStatement("UPDATE APS_OIGE1 "+ 
                                          "SET uniConformes=?, "+ 
                                          "uniNoConformes=?, "+ 
                                          "uniRechazadas=?, "+ 
                                          "uniTotales=?, "+ 
                                          "comentario=? "+ 
                                          "WHERE idAPS_OIGE1=(SELECT TOP 1 T0.idAPS_OIGE1 from APS_OIGE1 T0 JOIN APS_OIGE T1 ON T1.idAPS_OIGE=T0.idAPS_OIGE JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND T1.estado=0 AND T0.fin!='' AND ISNULL(T1.descActividad,'')=? ORDER BY T0.idAPS_OIGE1 DESC)");
                ps.setString(1, APS_OIGE1.getUniConformes());
                ps.setString(2, APS_OIGE1.getUniNoConformes());
                ps.setString(3, APS_OIGE1.getUniRechazadas());
                ps.setString(4, APS_OIGE1.getUniTotales());
                ps.setString(5, APS_OIGE1.getComentario());
                ps.setInt(6, APS_OIGE1.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(7, APS_OIGE1.getAPS_OIGE().getActividad());
                ps.setInt(8, APS_OIGE1.getAPS_OIGE().getCodeEmp());
                ps.setString(9,APS_OIGE1.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?"":APS_OIGE1.getAPS_OIGE().getDescActividad());
            /*}else{
                ps=con().prepareStatement("UPDATE APS_OIGE1 "+ 
                                          "SET uniConformes=?, "+ 
                                          "uniNoConformes=?, "+ 
                                          "uniRechazadas=?, "+ 
                                          "uniTotales=?, "+ 
                                          "comentario=? "+ 
                                          "WHERE idAPS_OIGE1=(SELECT TOP 1 T0.idAPS_OIGE1 from APS_OIGE1 T0 JOIN APS_OIGE T1 ON T1.idAPS_OIGE=T0.idAPS_OIGE JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR WHERE T2.docNum=? AND T1.actividad=? AND T1.codeEmp=? AND T1.estado=0 AND T0.fin!='' AND ISNULL(T1.descActividad, ' ')=? ORDER BY T0.idAPS_OIGE1 DESC)");
                ps.setString(1, APS_OIGE1.getUniConformes());
                ps.setString(2, APS_OIGE1.getUniNoConformes());
                ps.setString(3, APS_OIGE1.getUniRechazadas());
                ps.setString(4, APS_OIGE1.getUniTotales());
                ps.setString(5, APS_OIGE1.getComentario());
                ps.setInt(6, APS_OIGE1.getAPS_OIGE().getAPS_OWOR().getDocnum());
                ps.setString(7, APS_OIGE1.getAPS_OIGE().getActividad());
                ps.setInt(8, APS_OIGE1.getAPS_OIGE().getCodeEmp());
                ps.setString(9, APS_OIGE1.getAPS_OIGE().getDescActividad().equals("Sin descripcion")?" ":APS_OIGE1.getAPS_OIGE().getDescActividad());
            }
            */
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE1.modificarUnidades(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<APS_OIGE1> mostrarControlTiempo(int numOrden, String nomEmp, String actividad) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE1> ar = new ArrayList<APS_OIGE1>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "  ISNULL(CONVERT(VARCHAR(19),T0.inicio,120),'No Iniciada') 'inicio'," +
                                              "  ISNULL(CONVERT(VARCHAR(19),T0.fin,120),'No finalizada') 'fin', " +
                                              "  T0.numCaptura, " +
                                              "  ISNULL(T0.uniConformes,0), " +
                                              "  ISNULL(T0.uniNoConformes,0), " +
                                              "  ISNULL(T0.uniRechazadas,0), " +
                                              "  ISNULL(T0.uniTotales,0), " +
                                              "  ISNULL(T0.comentario,' ') 'comentario' " +
                                              "FROM APS_OIGE1 T0 " +
                                              "JOIN APS_OIGE T1 ON T0.idAPS_OIGE=T1.idAPS_OIGE " +
                                              "JOIN APS_OWOR T2 ON T2.idAPS_OWOR=T1.idAPS_OWOR " +
                                              "WHERE T2.docNum=? AND T1.nomEmp like ? AND T1.actividad=?");
            ps.setInt(1, numOrden);
            ps.setString(2, '%' + nomEmp + '%');
            ps.setString(3, actividad);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OIGE1 = new APS_OIGE1();
                APS_OIGE1.setInicio(rs.getString(1));
                APS_OIGE1.setFin(rs.getString(2));
                APS_OIGE1.setNumCaptura(rs.getInt(3));
                APS_OIGE1.setUniConformes(rs.getString(4));
                APS_OIGE1.setUniNoConformes(rs.getString(5));
                APS_OIGE1.setUniRechazadas(rs.getString(6));
                APS_OIGE1.setUniTotales(rs.getString(7));
                APS_OIGE1.setComentario(rs.getString(8));
                ar.add(APS_OIGE1);
            }
        } catch (Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE1.mostrarControlTiempo(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int insertarEdicion(Object Ob, int docNum) throws ClassNotFoundException, SQLException {
        try{
            APS_OIGE1=(APS_OIGE1)Ob;
            String inicio=APS_OIGE1.getInicio()+":00";
            String fin=APS_OIGE1.getFin()+":00";
            ps=con().prepareStatement("SELECT "+
                                      "T0.idAPS_OIGE "+
                                      "FROM APS_OIGE T0 "+
                                      "JOIN APS_OWOR T1 ON T0.idAPS_OWOR=T1.idAPS_OWOR "+
                                      "WHERE T1.edicion=2 AND T0.inicio IS NULL AND T1.docNum=?");
            ps.setInt(1,docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                ps=con().prepareStatement("INSERT INTO APS_OIGE1(inicio,fin,numCaptura,uniConformes,uniNoConformes,uniRechazadas,uniTotales,idAPS_OIGE,comentario) " +
                                          "VALUES(CAST(? AS DATETIME),CAST(? AS DATETIME),1,?,?,?,?,?,?)");
                ps.setString(1,inicio);
                ps.setString(2,fin);
                ps.setString(3, APS_OIGE1.getUniConformes());
                ps.setString(4, APS_OIGE1.getUniNoConformes());
                ps.setString(5, APS_OIGE1.getUniRechazadas());
                ps.setString(6, APS_OIGE1.getUniTotales());
                ps.setInt(7, rs.getInt(1));
                ps.setString(8, APS_OIGE1.getComentario());
                res=ps.executeUpdate();
            }
        }catch(Exception ex) {
            System.out.println("modelo.DaoAPS_OIGE1.insertarEdicion(): " + ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        System.out.println(res);
        return res;
    }
}
