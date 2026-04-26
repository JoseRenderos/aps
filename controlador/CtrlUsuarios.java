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
import com.google.gson.*;
import entidades.*;
import java.util.*;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlUsuarios extends HttpServlet {
    int r=0;
    APS_OCRC1 APS_OCRC1;
    APS_OCRC APS_OCRC;
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
            DaoUsuario dUS= new DaoUsuario();
            DaoAPS_OCRC1 dAPS_OCRC1 = new DaoAPS_OCRC1();
            DaoAPS_OCRC dAPS_OCRC = new DaoAPS_OCRC();
            
            if (request.getParameter("mostrar")!=null) {
                    try {
                        String estado="";
                        String tabla="";
                        int contador=0;
                        ArrayList<Usuario> User= new ArrayList<Usuario>();
                        ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                        User.addAll((Collection)dUS.mostrar());
                        for (Usuario us : User) {
                            APS_OCRC.clear();
                            APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRCUser(us.getIdUsuario(), us.getRol().getIdRol()));
                            
                            for (APS_OCRC OCRC : APS_OCRC) {
                                contador++;
                            }
                            switch(us.getEstado()){
                                case 1:
                                    estado="Activo";
                                  break;
                                case 0:
                                    estado="Inactivo";
                                  break;
                            }
                            tabla+="<tr>";
                            tabla+="<td>"+us.getIdUsuario()+"</td>"+
                                      "<td>"+us.getUsuario()+"</td>"+
                                      "<td>"+us.getPass()+"</td>"+
                                      "<td>"+us.getRol().getRol()+"</td>"+
                                      "<td>"+estado+"</td>";
                            tabla+="<td>";
                            if (us.getRol().getIdRol()==1 || us.getRol().getIdRol()==3 || contador==16) {
                                tabla+="Todos";
                            }else if(contador==0){
                                tabla+="Ninguno";
                            }else{
                                for (APS_OCRC OCRC : APS_OCRC) {
                                    tabla+=OCRC.getNombre()+", ";
                                }
                                tabla=tabla.substring(0, tabla.length()-2);
                            }
                            tabla+="</td></tr>";
                            contador=0;
                        }
                        out.print(tabla);
                        
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.mostrar(): "+e.getMessage());
                    }
                }
                
                //METODO ENVIAR LOS DATOS DE ROLES DE USUARIO PARA CARGARLOS EN LOS SELECT DE ROL
                if (request.getParameter("listarRol")!=null) {
                    try {
                        ArrayList<Rol> rol = new ArrayList<Rol>();
                        rol.addAll((Collection)dUS.listarRol());
                        String json = new Gson().toJson(rol);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.listarRol(): "+e.getMessage());
                    }
                }
            
                //METODO ENVIAR LOS DATOS DE LOS CENTROS DE COSTO PARA CARGARLOS EN LOS SELECT DE CENTROS DE COSTO
                if (request.getParameter("listarCecos")!=null) {
                    try {
                        ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                        APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRC());
                        String json = new Gson().toJson(APS_OCRC);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.listarCecos(): "+e.getMessage());
                    }
                }
                
                //METODO CREAR USUARIO
                if (request.getParameter("crearUsuario")!=null) {
                    try {
                        int rUS=0;
                        String cecos=request.getParameter("cecos");
                        String[] idCecos=cecos.split(",");
                        int[] intArray = new int[idCecos.length];
                        for (int i = 0; i < idCecos.length; i++) {
                            try {
                                intArray[i] = Integer.parseInt(idCecos[i]);
                            } catch (Exception e) {
                            }
                        }
                        Rol rol = new Rol(Integer.parseInt(request.getParameter("idRol")));
                        Usuario us = new Usuario(request.getParameter("usuario"), request.getParameter("pass"), rol);
                        rUS=dUS.insertar(us);
                        if(rUS>0) {
                            if (Integer.parseInt(request.getParameter("idRol"))==2) {
                                for (int i = 0; i < intArray.length; i++) {
                                    r=dAPS_OCRC1.insertar(intArray[i]);
                                }
                                out.print(r);
                            }else{
                                out.print(3);
                            }
                        }else{
                            out.print(2);
                        }
                          
                          
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.crearUsuario(): "+e.getMessage());
                    }
                }
            
                //METODO PARA COMPROBAR SI YA EXISTE ESE USUARIO
                if (request.getParameter("comprobarUsuario")!=null) {
                    try {
                        r=dUS.comprobarUsuario(request.getParameter("usuario"));
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.comprobarUsuario(): "+e.getMessage());
                    }
                }
            
                //METODO PARA COMPROBAR SI YA EXISTE ESE USUARIO
                if (request.getParameter("comprobarUsuarioModificar")!=null) {
                    try {
                        r=dUS.comprobarUsuarioModificar(request.getParameter("usuario"),Integer.parseInt(request.getParameter("idUsuario")));
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.comprobarUsuarioModificar(): "+e.getMessage());
                    }
                }
            
                //METODO PARA OPTENER TODA LA INFO DEL USUARIO
                if (request.getParameter("infoUsuario")!=null) {
                    try {
                        ArrayList<Usuario> us = new ArrayList<Usuario>();
                        us.addAll((Collection)dUS.infoUsuario(request.getParameter("usuario")));
                        String json = new Gson().toJson(us);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.infoUsuario(): "+e.getMessage());
                    }
                }
            
                //METODO OBTENER LOS CENTROS DE COSTO POR EMPLEADO
                if (request.getParameter("cecosUsuario")!=null) {
                    try {
                        ArrayList<APS_OCRC> ocrc = new ArrayList<APS_OCRC>();
                        ocrc.addAll((Collection)dAPS_OCRC.listarAPS_OCRCUser(Integer.parseInt(request.getParameter("idUsuario")),2));
                        String json = new Gson().toJson(ocrc);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.cecosUsuario(): "+e.getMessage());
                    }
                }
            
                //METODO OBTENER LOS CENTROS DE COSTO POR EMPLEADO
                if (request.getParameter("modificarUsuario")!=null) {
                    try { 
                        
                        Rol rol = new Rol(Integer.parseInt(request.getParameter("idRol")));
                        Usuario us = new Usuario(Integer.parseInt(request.getParameter("idUsuario")), request.getParameter("usuario"), request.getParameter("pass"), Integer.parseInt(request.getParameter("estado")), rol);
                        r= dUS.modificar(us);
                        String cecos=request.getParameter("cecos");
                        String[] idCecos=cecos.split(",");
                        int[] intArray = new int[idCecos.length];
                        for (int i = 0; i < idCecos.length; i++) {
                            try {
                                intArray[i] = Integer.parseInt(idCecos[i]);
                            } catch (Exception e) {
                            }
                        }
                        String cecosE=request.getParameter("cecosE");
                        String[] idCecosE=cecosE.split(",");
                        int[] intArrayE = new int[idCecosE.length];
                        for (int i = 0; i < idCecosE.length; i++) {
                            try {
                                intArrayE[i] = Integer.parseInt(idCecosE[i]);
                            } catch (Exception e) {
                            }
                        }
                       
                        ArrayList<Integer> aps_ocrc = new ArrayList<Integer>();
                        ArrayList<Integer> ocrc = new ArrayList<Integer>();
                        for (int i = 0; i < intArray.length; i++) {
                            aps_ocrc.add(intArray[i]);
                        }
                        for (int i = 0; i < intArrayE.length; i++) {
                            ocrc.add(intArrayE[i]);
                        }
                        
                        if (Integer.parseInt(request.getParameter("idRol"))==2) {

                            ArrayList<Integer> eliminar = new ArrayList<Integer>();
                            for (Integer element : ocrc) {
                                if (!aps_ocrc.contains(element)) {//encontrar que cecos no contiene el arreglo arreglo enviado del formulario para agregarlos al arreglo eliminar
                                    eliminar.add(element);
                                }
                            }
                            if (eliminar == null || eliminar.isEmpty()) {
                            } else {
                                for (Integer obj : eliminar) {
                                    APS_OCRC = new APS_OCRC();
                                    APS_OCRC.setIdAPS_OCRC(obj);
                                    APS_OCRC1 = new APS_OCRC1(us, APS_OCRC);
                                    r= dAPS_OCRC1.eliminar(APS_OCRC1);
                                }
                            }

                            ArrayList<Integer> agregar = new ArrayList<Integer>();
                            for (Integer element : aps_ocrc) {
                                if (!ocrc.contains(element)) {
                                    agregar.add(element);
                                }
                            }
                            if (agregar == null || agregar.isEmpty()) {
                            } else {
                                for (Integer obj : agregar) {
                                    APS_OCRC = new APS_OCRC();
                                    APS_OCRC.setIdAPS_OCRC(obj);
                                    APS_OCRC1 = new APS_OCRC1(us, APS_OCRC);
                                    r= dAPS_OCRC1.guardar(APS_OCRC1);
                                }
                            }
                        }else{
                            ArrayList<Integer> eliminar = new ArrayList<Integer>();
                            for (Integer element : ocrc) {
                                if (!aps_ocrc.contains(element)) {//encontrar que cecos no contiene el arreglo arreglo enviado del formulario para agregarlos al arreglo eliminar
                                    eliminar.add(element);
                                }
                            }
                            if (eliminar == null || eliminar.isEmpty()) {
                            } else {
                                for (Integer obj : eliminar) {
                                    APS_OCRC = new APS_OCRC();
                                    APS_OCRC.setIdAPS_OCRC(obj);
                                    APS_OCRC1 = new APS_OCRC1(us, APS_OCRC);
                                    r= dAPS_OCRC1.eliminar(APS_OCRC1);
                                }
                            }
                        }
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlUsuarios.modificarUsuario(): "+e.getMessage());
                    }
                }
        }catch(Exception e){
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
