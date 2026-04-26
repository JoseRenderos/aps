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
import modelo.DaoUsuario;
import entidades.Usuario;
import java.util.*;

/**
 *
 * @author Desarrollo Alsasa
 */
public class CtrlLogin extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    Usuario us;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
           RequestDispatcher rd;
           DaoUsuario du = new DaoUsuario();
           ArrayList<Usuario> arr= new ArrayList<Usuario>();
           
            if(request.getParameter("btnIngresar")!=null) {
                try {
                    us = new Usuario(request.getParameter("txtUsuario"),
                            request.getParameter("txtContra"));
                    arr.addAll(du.login(us));
                    if (!arr.isEmpty()) {
                        for (Usuario usuario : arr) {
                            if (usuario.getEstado()==1) {
                                request.setAttribute("datosUsuario", arr);
                            } else {
                                request.setAttribute("errorE", "No tiene acceso al sistema");
                            }
                        }
                    }else{
                        request.setAttribute("errorDU", "Usuario y/o contraseña incorrectos.");
                    }
                } catch (Exception e) {
                    request.setAttribute("errorC", "Error al iniciar sesion");
                }
            }
            
            if (request.getParameter("logout")!=null) {
                try {
                    HttpSession s = request.getSession();
                    s.removeAttribute("usuario");
                    s.invalidate();
                    request.setAttribute("logout", "La sesión se cerró");
                    
                } catch (Exception e) {
                     request.setAttribute("logoutF", "La sesión no se cerró");
                }
            }
            if (request.getParameter("login")!=null) {
                    request.setAttribute("login", "Debe iniciar sesión antes");
            }
           
           rd = request.getRequestDispatcher("frmLogin.jsp");
           rd.forward(request, response);
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
