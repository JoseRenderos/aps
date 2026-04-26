/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import java.sql.*;
import entidades.*;
import java.util.ArrayList;
/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoUsuario extends ConexionSDO implements CRUD{
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Usuario us;
    Rol r;
    
    @Override
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "US.idUsuario, " +
                                              "US.Usuario, " +
                                              "CONVERT(VARCHAR(MAX),DECRYPTBYPASSPHRASE('password', US.password)) pass, " +
                                              "US.estado, " +
                                              "US.idRol, " +
                                              "R.rol " +
                                              "FROM USUARIO US " +
                                              "JOIN ROL R ON US.idRol=R.idRol WHERE R.idRol=1 OR R.idRol=2 OR R.idRol=3 OR R.idRol=7");
            rs = ps.executeQuery();
            while(rs.next()){
                r = new Rol(rs.getInt(5), rs.getString(6));
               us = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), r);
               ar.add(us);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }

    @Override
    public int insertar(Object Ob) throws ClassNotFoundException, SQLException {
        us = (Usuario)Ob;
        try {
            ps = super.con().prepareStatement("insert into usuario (usuario,password,idRol) values(?,ENCRYPTBYPASSPHRASE('password', ?),?)");
            ps.setString(1,us.getUsuario());
            ps.setString(2,us.getPass());
            ps.setInt(3, us.getRol().getIdRol());
            res = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.insertar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    @Override
    public int modificar(Object Ob) throws ClassNotFoundException, SQLException {
        us = (Usuario)Ob;
        try {
            ps = super.con().prepareStatement("UPDATE USUARIO " +
                                              "SET usuario=?, password=ENCRYPTBYPASSPHRASE('password', ?), estado=?, idRol=? " +
                                              "WHERE idUsuario=?");
            ps.setString(1,us.getUsuario());
            ps.setString(2,us.getPass());
            ps.setInt(3, us.getEstado());
            ps.setInt(4, us.getRol().getIdRol());
            ps.setInt(5, us.getIdUsuario());
            res = ps.executeUpdate();
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.modificar(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }

    @Override
    public int eliminar(Object Ob) throws ClassNotFoundException, SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public ArrayList<Usuario> login(Usuario user)throws ClassNotFoundException, SQLException{
         
        ArrayList<Usuario> ar = new ArrayList<Usuario>();
        try {
            ps = super.con().prepareStatement("SELECT "+ 
                                              "T0.*, "+ 
                                              "T1.rol "+ 
                                              "FROM USUARIO T0 "+
                                              "JOIN ROL T1 ON T0.idRol=T1.idRol "+ 
                                              "WHERE T0.usuario=? and CONVERT(VARCHAR(MAX),DECRYPTBYPASSPHRASE('password', T0.password))=? AND T1.idRol!=4 AND T1.idRol!=5 AND T1.idRol!=6");
            ps.setString(1, user.getUsuario());
            ps.setString(2, user.getPass());
            rs = ps.executeQuery();
            while(rs.next()){
                r= new Rol(rs.getInt(4),rs.getString(8));
                us = new Usuario(rs.getInt(1), rs.getString(2),rs.getInt(3), r);
                ar.add(us);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoUsuario.login(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Rol> listarRol() throws ClassNotFoundException, SQLException{
        ArrayList<Rol> ar = new ArrayList<Rol>();
        try {
            ps = super.con().prepareStatement("SELECT * FROM ROL WHERE idRol!=4 AND idRol!=5 AND idRol!=6 ORDER BY idRol DESC");
            rs = ps.executeQuery();
            while(rs.next()){
                r= new Rol(rs.getInt(1), rs.getString(2));
                ar.add(r);
            }            
        } catch (Exception e){
            System.out.println("modelo.DaoUsuario.listarRol(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int comprobarUsuario(String usuario) throws ClassNotFoundException, SQLException {
        try {
            ps = super.con().prepareStatement("SELECT count(idUsuario) FROM USUARIO WHERE Usuario=?");
            ps.setString(1, usuario);
            rs=ps.executeQuery();
            while(rs.next()){
                res=rs.getInt(1);
            } 
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.comprobarUsuario(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public int comprobarUsuarioModificar(String usuario, int idUsuario) throws ClassNotFoundException, SQLException {
        try {
            ps = super.con().prepareStatement("SELECT count(idUsuario) FROM USUARIO WHERE Usuario=? AND idUsuario!=?");
            ps.setString(1, usuario);
            ps.setInt(2, idUsuario);
            rs=ps.executeQuery();
            while(rs.next()){
                res=rs.getInt(1);
            } 
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.comprobarUsuarioModificar(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<Usuario> infoUsuario(String usuario) throws ClassNotFoundException, SQLException {
        ArrayList<Usuario> ar = new ArrayList<Usuario>();
        try {
            ps = super.con().prepareStatement("SELECT T0.idUsuario, " +
                                              "T0.Usuario, " +
                                              "CONVERT(VARCHAR(MAX),DECRYPTBYPASSPHRASE('password', T0.password)) pass, " +
                                              "T0.estado, " +
                                              "T0.idRol " +
                                              "FROM " +
                                              "USUARIO T0 " +
                                              "WHERE T0.usuario=?");
            ps.setString(1, usuario);
            rs=ps.executeQuery();
            while(rs.next()){
                r = new Rol(rs.getInt(5));
                us = new Usuario(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), r);
                ar.add(us);
            } 
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.infoUsuario(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> listarUsuarios() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "US.idUsuario, " +
                                              "US.Usuario " +
                                              "FROM USUARIO US WHERE US.idRol!=1 AND US.idRol!=4");
            rs = ps.executeQuery();
            while(rs.next()){
               us = new Usuario();
               us.setIdUsuario(rs.getInt(1));
               us.setUsuario(rs.getString(2));
               ar.add(us);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoUsuario.listarUsuarios(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
}
