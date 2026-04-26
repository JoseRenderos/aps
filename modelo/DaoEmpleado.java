/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;
import entidades.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoEmpleado extends ConexionSDO{
    PreparedStatement ps;
    ResultSet rs;
    Empleado em; 
    Departamento depto;
    Actividades act;
    int res;
    
    public ArrayList<Object> mostrar() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
//            ps=super.con().prepareStatement("SELECT DISTINCT	" + 
//                                            "   T0.emp_code CodEmp, " + 
//                                            "   (SELECT K0.first_name + ' '  + ISNULL(K0.last_name,'') FROM zkbiotime..personnel_employee K0 WHERE K0.emp_code = T0.emp_code) NombreEmp, " + 
//                                            "   (SELECT L0.dept_name FROM zkbiotime..personnel_department L0 WHERE L0.dept_code = (SELECT CASE WHEN H0.department_id=21 THEN 42 ELSE H0.department_id END FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = T0.emp_code)) Departamento " + 
//                                            "FROM zkbiotime..iclock_transaction T0 " + 
//                                            "WHERE T0.emp_code NOT IN (1,2) AND (SELECT H0.department_id FROM zkbiotime..personnel_employee H0 WHERE H0.emp_code = T0.emp_code) IN (2,3,4,5,6,7,8,9,10,11,16,13,21,42) AND CONVERT(CHAR(10),T0.punch_time,112)=CONVERT(CHAR(10),GETDATE(),112) ORDER BY CodEmp ASC");
            ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_LISTADO_EMPLEADOS_PRODUCCION");
            rs = ps.executeQuery();
            
            while(rs.next()){
               em= new Empleado(rs.getInt(1), rs.getString(2));
               ar.add(em);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoEmpleado.mostrar(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }  
    
    public ArrayList<Object> mostrarTodos() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT DISTINCT " +
                                              "  emp_code, " +
                                              "  first_name + ' '  + ISNULL(last_name,'') NombreEmp " +
                                              "FROM zkbiotime..personnel_employee " +
                                              "WHERE department_id IN (2,3,4,5,6,7,8,9,10,11,13,16,21,42) ORDER BY emp_code ASC");
            rs = ps.executeQuery();
            
            while(rs.next()){
               em= new Empleado(rs.getInt(1), rs.getString(2));
               ar.add(em);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoEmpleado.mostrarTodos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Object> mostrarDeptos() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT " +
                                              "L0.dept_code, " +
                                              "L0.dept_name " +
                                              "FROM zkbiotime..personnel_department L0 WHERE l0.dept_code IN (3,4,5,6,7,8,9,10,13)");
            rs = ps.executeQuery();
            
            while(rs.next()){
               depto= new Departamento(rs.getString(1), rs.getString(2));
               ar.add(depto);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoEmpleado.mostrarDeptos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int insertar(Object Ob,int idUsuario) throws ClassNotFoundException, SQLException {
        try{
            em=(Empleado)Ob;
            ps=con().prepareStatement("INSERT INTO DEPTO_EMP(codEmp,nomEmp,depto_code,depto,fecha,idUsuario) VALUES(?,?,?,?,?,?)");
            ps.setInt(1,em.getCodigo());
            ps.setString(2, em.getNombre());
            ps.setInt(3, em.getCodDepto());
            ps.setString(4, em.getDepto());
            ps.setString(5, em.getFecha().replaceAll("-", ""));
            ps.setInt(6, idUsuario);
            res=ps.executeUpdate();
        }catch(Exception ex) {
            System.out.println("modelo.DaoEmpleado.insertar(): "+ex.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
    
    public ArrayList<Object> mostrarActividades() throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT * FROM ACT_VARIAS");
            rs = ps.executeQuery();
            while(rs.next()){
               act= new Actividades(rs.getString(1), rs.getString(2));
               ar.add(act);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoEmpleado.mostrarActividades(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public int obtenerDeptoEmp(int CodEmp) throws ClassNotFoundException, SQLException {
        ArrayList<Object> ar = new ArrayList<Object>();
        try {
            ps = super.con().prepareStatement("SELECT cod_Depto  FROM EMPLEADOS_ALSASA_V2 T0 WHERE codEmp=?");
            ps.setInt(1,CodEmp);
            rs = ps.executeQuery();
            while(rs.next()){
               res=rs.getInt(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoEmpleado.obtenerDeptoEmp(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return res;
    }
}
