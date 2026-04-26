/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entidades;

/**
 *
 * @author Desarrollo Alsasa
 */
public class Usuario {
    private int idUsuario;
    private String usuario;
    private String pass;
    private int estado;
    private Rol rol;

    public Usuario() {
    }
    //constructor para guardar datos obtenidos de la consulta de mostrar usuario
    public Usuario(int idUsuario, String usuario, String pass, int estado, Rol rol) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.pass = pass;
        this.estado = estado;
        this.rol = rol;
    }
    
    //Constructor para guardar datos obtenidos del la consulta login
    public Usuario(int idUsuario, String usuario, int estado, Rol rol) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.pass = pass;
        this.estado = estado;
        this.rol = rol;
    }

    //Constructor para guardar un usuario
    public Usuario(String usuario, String pass, Rol rol) {
        this.usuario = usuario;
        this.pass = pass;
        this.rol = rol;
    }
    
    //constructor para guardar los datos obtenidos por el usuario en el login
    public Usuario(String usuario, String pass) {
        this.usuario = usuario;
        this.pass = pass;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}
