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
import modelo.SAP.DaoSAP;
import com.google.gson.Gson;
import java.util.*;
import entidades.*;
import java.text.DecimalFormat;

import java.io.InputStream;
import javax.servlet.http.Part;
import javax.servlet.annotation.MultipartConfig;
import org.apache.poi.ss.usermodel.*;

/**
 *
 * @author Mario Valdez
 */

@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 1024 * 1024 * 10,
    maxRequestSize = 1024 * 1024 * 50
)
public class CtrlOrden extends HttpServlet {

    OWOR or;
    APS_OIGE oige;
    APS_OIGE1 oige1;
    APS_OIGE2 oige2;
    APS_OIGE3 oige3;
    Usuario us;
    OWTR owtr;
    int r=0;
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
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CtrlOrden</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CtrlOrden at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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
               DaoOWOR dO = new DaoOWOR();
               DaoOITT dItem = new DaoOITT();
                DaoOITT dOITT = new DaoOITT();
               DaoEmpleado dEmp = new DaoEmpleado();
               DaoActividad dAct = new DaoActividad();
               DaoWOR1 dIns = new DaoWOR1();
               DaoOWTR dOWTR = new DaoOWTR();
               DaoAPS_OIGE dOIGE = new DaoAPS_OIGE();
               DaoAPS_OIGE1 dOIGE1 = new DaoAPS_OIGE1();
               DaoAPS_OIGE2 dOIGE2 = new DaoAPS_OIGE2();
               DaoAPS_OIGE3 dOIGE3 = new DaoAPS_OIGE3();
               DaoAPS_OWOR dAPSO= new DaoAPS_OWOR();              
               DaoAPS_OCRC dAPS_OCRC= new DaoAPS_OCRC();
               DaoAPS_IGN1 dAPS_IGN1 = new DaoAPS_IGN1();
               DaoSAP dSAP = new DaoSAP();
                       
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ORDENES DE FABROCACION
               if (request.getParameter("mostrar")!=null) {
                    try {
                        HttpSession s = request.getSession();
                        int idUsuario=(Integer)s.getAttribute("idUsuario");
                        int idRol=(Integer)s.getAttribute("idRol");
                        String proceso="";
                        String estado="";
                        String CantPln;
                        String Cantcmp;
                        String CantRjc;
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dO.mostrar(idUsuario, idRol));
                        ArrayList<APS_OIGE> oige= new ArrayList<APS_OIGE>();
                        oige.addAll((Collection)dAPSO.comprobarEstadoTabla());
                        DecimalFormat formato = new DecimalFormat("######.###");
                        for (OWOR or : Orden) {
                            proceso="Sin asignar";
                            for (APS_OIGE ar : oige) {
                                if (or.getDocnum()==ar.getAPS_OWOR().getDocnum()) {
                                    if (ar.getEstado()==0) {
                                        proceso="En fabricacion";
                                    } else {
                                        proceso="Terminado";
                                    }
                                }
                            }
                            switch(or.getStatus()){
                                case "R":
                                    estado="Liberada";
                                break;
                                case "P":
                                    estado="Planificada";
                                break;
                                case "C":
                                    estado="Cancelada";
                                break;
                                case "L":
                                    estado="Cerrada";
                                break;
                            }
                          CantPln =formato.format(Double.parseDouble(or.getCantpln()));
                          Cantcmp = formato.format(Double.parseDouble(or.getCantcmp()));
                          CantRjc = formato.format(Double.parseDouble(or.getCantrjc()));
                          out.println("<tr><td>"+or.getDocnum()+"</td>"+
                                      "<td>"+or.getStartdate()+"</td>"+
                                      "<td>"+or.getDuedate()+"</td>"+
                                      "<td>"+or.getItemcode()+"</td>"+
                                      "<td>"+or.getItemname()+"</td>"+
                                      "<td>"+estado+"</td>"+
                                      "<td>"+or.getType()+"</td>"+
                                      "<td>"+CantPln+"</td>"+
                                      "<td>"+Cantcmp+"</td>"+
                                      "<td>"+CantRjc+"</td>"+
                                      "<td>"+proceso+"</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrar(): "+e.getMessage());
                    }
                } 
               
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ORDENES DE FABROCACION
               if (request.getParameter("filtrarOrdenes")!=null) {
                    try {
                        String proceso="";
                        String estado="";
                        String CantPln;
                        String Cantcmp;
                        String CantRjc;
                        ArrayList<OWOR> Orden= new ArrayList<OWOR>();
                        Orden.addAll((Collection)dO.filtrarOrdenes(request.getParameter("cecos")));
                        ArrayList<APS_OIGE> oige= new ArrayList<APS_OIGE>();
                        oige.addAll((Collection)dAPSO.comprobarEstadoTabla());
                        DecimalFormat formato = new DecimalFormat("######.###");
                        for (OWOR or : Orden) {
                            proceso="Sin asignar";
                            for (APS_OIGE ar : oige) {
                                if (or.getDocnum()==ar.getAPS_OWOR().getDocnum()) {
                                    if (ar.getEstado()==0) {
                                        proceso="En fabricacion";
                                    } else {
                                        proceso="Terminado";
                                    }
                                }
                            }
                            switch(or.getStatus()){
                                case "R":
                                    estado="Liberada";
                                break;
                                case "P":
                                    estado="Planificada";
                                break;
                                case "C":
                                    estado="Cancelada";
                                break;
                                case "L":
                                    estado="Cerrada";
                                break;
                            }
                          CantPln =formato.format(Double.parseDouble(or.getCantpln()));
                          Cantcmp = formato.format(Double.parseDouble(or.getCantcmp()));
                          CantRjc = formato.format(Double.parseDouble(or.getCantrjc()));
                          out.println("<tr><td>"+or.getDocnum()+"</td>"+
                                      "<td>"+or.getStartdate()+"</td>"+
                                      "<td>"+or.getDuedate()+"</td>"+
                                      "<td>"+or.getItemcode()+"</td>"+
                                      "<td>"+or.getItemname()+"</td>"+
                                      "<td>"+estado+"</td>"+
                                      "<td>"+or.getType()+"</td>"+
                                      "<td>"+CantPln+"</td>"+
                                      "<td>"+Cantcmp+"</td>"+
                                      "<td>"+CantRjc+"</td>"+
                                      "<td>"+proceso+"</td></tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.filtrarOrdenes(): "+e.getMessage());
                    }
                }
               
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE ACTIVIADADES DE CADA ORDEN DE FABRICACION
               if (request.getParameter("mostrarActividades")!=null) {
                   double porcentaje=0;
                    try {
                        ArrayList<APS_OIGE> ACT= new ArrayList<APS_OIGE>();
                        ACT.addAll((Collection)dOIGE.mostrarActividades(Integer.parseInt(request.getParameter("numOrden"))));
                        String inicio="";
                        String fin="";
                        String codeInsumo="";
                        for (APS_OIGE obj : ACT) {
                            codeInsumo="";
                            ArrayList<APS_OIGE3> material= new ArrayList<APS_OIGE3>();
                            material.addAll((Collection)dOIGE3.mostrar(request.getParameter("numOrden"), obj.getIdAPS_OIGE()));
                            for (APS_OIGE3 oige3 : material) {
                                codeInsumo+=oige3.getItemCode()+" - Cantidad: "+oige3.getCantAsignada()+", ";
                            }
                            if(codeInsumo.length()!=0){
                                codeInsumo=codeInsumo.substring(0, codeInsumo.length()-2);
                            }
                            
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
                                      "<td>"+codeInsumo+"</td>"+
                                      "<td>"+inicio+"</td>"+
                                      "<td>"+fin+"</td>"+
                                      "<td>"+obj.getTotalUnidadesConformes()+"</td>"+ 
                                      "<td>"+obj.getEstado()+"</td>"+ 
                                      "<td>"+obj.getDescActividadProd()+"%</td>"+ 
                                      "</tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarActividades(): "+e.getMessage());
                    }
                }
               
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE MATERIALES DE CADA ORDEN DE FABRICACION
               if (request.getParameter("mostrarMateriales")!=null) {
                    try {
                        ArrayList<WOR1> ins= new ArrayList<WOR1>();
                        ins.addAll((Collection)dIns.mostrarMateriales(Integer.parseInt(request.getParameter("numOrden"))));
                        for (WOR1 obj : ins) {
                            out.println("<tr>"+ 
                                      "<td>"+obj.getItemCode()+"</td>"+
                                      "<td>"+obj.getItemName()+"</td>"+
                                      "<td>"+obj.getReleaseQty()+"</td>"+
                                      "<td>"+obj.getCantEntregada()+"</td>"+
                                      "</tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarMateriales(): "+e.getMessage());
                    }
                }
               
               //CODIGO PARA ENVIAR CONTENIDO DE LA TABLA DE TRASLADOS DE UNA OP
               if (request.getParameter("mostrarTraslados")!=null) {
                    try {
                        ArrayList<OWTR> owtr= new ArrayList<OWTR>();
                        owtr.addAll((Collection)dOWTR.mostrar(request.getParameter("numOrden")));
                        for (OWTR obj : owtr) {
                            out.println("<tr>"+ 
                                            "<td>"+obj.getDocNum()+"</td>"+
                                            "<td>"+obj.getItemCode()+"</td>"+
                                            "<td>"+obj.getItemName()+"</td>"+
                                            "<td>"+obj.getQuantity()+"</td>"+
                                            "<td>"+obj.getShipDate()+"</td>"+
                                            "<td>"+obj.getBodega()+"</td>"+
                                        "</tr>");
                        }
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarTraslados(): "+e.getMessage());
                    }
                }
               
                //METODO ENVIAR LOS DATOS DE LA ORDEN SELECCIONADA EN AGENDA
                if (request.getParameter("obtenerOrden")!=null) {
                    try {
                        ArrayList<OWOR> ar= new ArrayList<OWOR>();
                        ar.addAll((Collection)dO.obtenerOrden(Integer.parseInt(request.getParameter("numOrden"))));
                        String json= new Gson().toJson(ar);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.obtenerOrden(): "+e.getMessage());
                    }
                }
               
                if (request.getParameter("filtrarAgenda")!=null) {
                    try {
                        ArrayList<OWOR> arr= new ArrayList<OWOR>();
                        arr.addAll((Collection)dO.filtrarOrdenes(request.getParameter("cecos")));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.filtrarAgenda(): "+e.getMessage());
                    }
                }
               
                if (request.getParameter("filtrarAgendaArt")!=null) {
                    try {
                        ArrayList<OWOR> arr= new ArrayList<OWOR>();
                        arr.addAll((Collection)dO.filtrarOrdenesArt(request.getParameter("itemCode")));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.filtrarAgendaArt(): "+e.getMessage());
                    }
                }
               
                if (request.getParameter("mostrarAgenda")!=null) {
                    try {
                        ArrayList<OWOR> arr= new ArrayList<OWOR>();
                        HttpSession s = request.getSession();
                        int idUsuario=(Integer)s.getAttribute("idUsuario");
                        int idRol=(Integer)s.getAttribute("idRol");
                        arr.addAll((Collection)dO.mostrar(idUsuario,idRol));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarAgenda(): "+e.getMessage());
                    }
                }
               
               //METODO ENVIAR LOS DATOS LAS ORDENES FATHER DE LA ORDEN SELECCIONADA EN AGENDA
               if (request.getParameter("mostrarEstado")!=null) {
                    try {
                        ArrayList<APS_OIGE> ar= new ArrayList<APS_OIGE>();
                        ar.addAll((Collection)dAPSO.comprobarEstado());
                        String json= new Gson().toJson(ar);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarEstado(): "+e.getMessage());
                    }
                }
               
               //METODO ENVIAR LOS DATOS LAS ORDENES FATHER DE LA ORDEN SELECCIONADA EN AGENDA
               if (request.getParameter("mostrarItem")!=null) {
                    try {
                        ArrayList<OITT> Item= new ArrayList<OITT>();
                        Item.addAll((Collection)dItem.mostrar(request.getParameter("itemCode")));
                        String json= new Gson().toJson(Item);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarItem(): "+e.getMessage());
                    }
                }
            
                if (request.getParameter("cargarExcelBoton")!=null) {
                    try {
                        HttpSession sesion = request.getSession();
                        int idUsr = (Integer)sesion.getAttribute("idUsuario");
                        Part filePart = request.getPart("fileExcel");
                        if (filePart != null && filePart.getSize() > 0) {
                            InputStream fileContent = filePart.getInputStream();
                            Workbook workbook = WorkbookFactory.create(fileContent);
                            Sheet sheet = workbook.getSheetAt(0);
                            int insertados = 0;
                            int errores = 0;
                            StringBuilder detalle = new StringBuilder();

                            for (Row row : sheet) {
                                if (row.getRowNum() == 0) continue;
                                try {
                                    int docNum = (int) row.getCell(5).getNumericCellValue();
                                    int codeEmp = (int) row.getCell(3).getNumericCellValue();
                                    String nomEmp = row.getCell(4).getStringCellValue();
                                    String actividad = row.getCell(7).getStringCellValue();
                                    String descActividad = row.getCell(6).getStringCellValue();

                                    OWOR owor = new OWOR();
                                    owor.setDocnum(docNum);

                                    Usuario usuario = new Usuario();
                                    usuario.setIdUsuario(idUsr);

                                    APS_OIGE registro = new APS_OIGE();
                                    registro.setAPS_OWOR(owor);
                                    registro.setCodeEmp(codeEmp);
                                    registro.setNomEmp(nomEmp);
                                    registro.setActividad(actividad);
                                    registro.setDescActividad(descActividad);
                                    registro.setUsuario(usuario);

                                    int resultado = dOIGE.insertar(registro);
                                    if (resultado > 0) {
                                        insertados++;
                                    } else {
                                        errores++;
                                        detalle.append("Fila ").append(row.getRowNum() + 1).append(": no se pudo insertar. ");
                                    }
                                } catch (Exception ex) {
                                    errores++;
                                    detalle.append("Fila ").append(row.getRowNum() + 1).append(": ").append(ex.getMessage()).append(". ");
                                }
                            }
                            workbook.close();
                            out.print("{\"insertados\":" + insertados + ",\"errores\":" + errores + ",\"detalle\":\"" + detalle.toString().replace("\"", "'") + "\"}");
                        } else {
                            out.print("{\"insertados\":0,\"errores\":1,\"detalle\":\"Archivo vacio o no seleccionado\"}");
                        }
                    } catch (Exception e) {
                        out.print("{\"insertados\":0,\"errores\":1,\"detalle\":\"" + e.getMessage().replace("\"", "'") + "\"}");
                        System.out.println("controlador.CtrlOrden.cargarExcel(): " + e.getMessage());
                    }
                }
               
                //METODO ENVIAR LOS DATOS DE LOS EMPLEADOS AL SELECTOR EN EL REGISTRO DE HORAS
                if (request.getParameter("mostrarEmpleados")!=null) {
                    try {
                        ArrayList<Empleado> Emp = new ArrayList<Empleado>();
                        Emp.addAll((Collection)dEmp.mostrar());
                        String json = new Gson().toJson(Emp);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarEmpleados(): "+e.getMessage());
                    }
                }
            
                //METODO ENVIAR LOS DATOS DE TODOS LOS EMPLEADOS AL SELECTOR EN EL REGISTRO DE HORAS
                if (request.getParameter("mostrarTodosEmpleados")!=null) {
                    try {
                        ArrayList<Empleado> Emp = new ArrayList<Empleado>();
                        Emp.addAll((Collection)dEmp.mostrar());
                        String json = new Gson().toJson(Emp);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarTodosEmpleados(): "+e.getMessage());
                    }
                }
                
                //METODO ENVIAR LOS DATOS DE LAS ACTIVIDADES DE ORDEN AL SELECTOR EN EL REGISTRO DE HORAS
                if (request.getParameter("listarActividades")!=null) {
                    try {
                        ArrayList<ActividadSBO> Act = new ArrayList<ActividadSBO>();
                        Act.addAll((Collection)dAct.mostrar(request.getParameter("itemCode")));
                        String json = new Gson().toJson(Act);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.listarActividades(): "+e.getMessage());
                    }
                }

                //METODO ENVIAR LOS DATOS DE LOS DE ORDEN AL SELECTOR EN EL REGISTRO DE HORAS
                if (request.getParameter("mostrarInsumos")!=null) {
                    try {
                        ArrayList<WOR1> Ins = new ArrayList<WOR1>();
                        Ins.addAll((Collection)dIns.mostrar(request.getParameter("numOrden")));
                        String json = new Gson().toJson(Ins);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarInsumos(): "+e.getMessage());
                    }
                }
                
                //METODO ENVIAR LOS DATOS DE LOS CENTROS DE COSTO PARA CARGARLOS EN LOS SELECT DE CENTROS DE COSTO
                if (request.getParameter("listarCecosO")!=null) {
                    try {
                        if (Integer.parseInt(request.getParameter("idRol"))==2) {
                            ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                            APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRCUser(Integer.parseInt(request.getParameter("idUsuario")),Integer.parseInt(request.getParameter("idRol"))));
                            String json = new Gson().toJson(APS_OCRC);
                            out.print(json);
                        } else {
                            ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                            APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRC());
                            String json = new Gson().toJson(APS_OCRC);
                            out.print(json);
                        }
                    } catch (Exception e) { 
                        System.out.println("controlador.CtrlOrden.listarCecosO(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar si hay una asignacion de labor activa
                if (request.getParameter("comprobar")!=null) {
                    try {
                        int numorden=dOIGE.comprobar(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"));
                        out.print(numorden);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobar(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("mostrarEstandarH")!=null) {
                    try {
                        int estandarH=dItem.mostrarEstandarH(request.getParameter("item"),request.getParameter("code"),request.getParameter("descripcion"));
                        out.print(estandarH);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarEstandarH(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("comprobarEmpleado")!=null) {
                    try {
                        int cantidad=0;//dOIGE.comprobarEmpleado(Integer.parseInt(request.getParameter("codeEmp")));
                        out.print(cantidad);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarEmpleado(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("comprobarCantAsignadaEmpleado")!=null) {
                  try {
                    if(request.getParameter("actividad").contains("105") || request.getParameter("actividad").contains("114") || request.getParameter("actividad").contains("111") || request.getParameter("actividad").contains("106") || request.getParameter("itemCode").contains("SMED")){
                        out.print(999999999);
                    }else{
                      int total=dOIGE.comprobarCantAsignadaEmpleado(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codeEmp")));
                      out.print(total);
                    }
                  } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarCantAsignadaEmpleado(): "+e.getMessage());
                  }
                }
                
                if (request.getParameter("comprobarCantAsignada")!=null) {
                    try {
                    int total=dOIGE.comprobarCantAsignada(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("insumo"), request.getParameter("actividad"));
                        out.print(total);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarCantAsignada(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("comprobarCantEntregada")!=null) {
                    try {
                        //if(request.getParameter("actividad").contains("114MLINEA1") || request.getParameter("actividad").contains("111M") || request.getParameter("itemCode").contains("SMED")){
                            out.print(999999999);
                        /*}else{
                            double cant=dO.comprobarCantEntregada(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("insumo"));
                            out.print(cant);
                        }*/
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarCantEntregada(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("comprobarUniTotales")!=null) {
                    try {
                        int total=dOIGE.comprobarUniTotales(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codeEmp")), request.getParameter("descActividad"));
                        out.print(total);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarUniTotales(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar si hay una asignacion de labor activa
                if (request.getParameter("comprobarAsignacion")!=null) {
                    try {
                        int numorden=dOIGE1.comprobar(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(numorden);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarAsignacion(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar si hay una asignacion de labor activa
                if (request.getParameter("comprobarAsignacionGrupo")!=null) {
                    try {
                        int numorden=dOIGE1.comprobarGrupo(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(numorden);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarAsignacionGrupo(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar que empleado tiene asignada una actividad y cual es dicha actividad y el numero de captura de tiempo actual
                if (request.getParameter("asignado")!=null) {
                    try {
                        ArrayList<APS_OIGE1> oige1 = new ArrayList<APS_OIGE1>();
                        oige1.addAll((Collection)dOIGE1.asignado(Integer.parseInt(request.getParameter("numOrden"))));
                        String json = new Gson().toJson(oige1);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.asignado(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar que empleado tiene asignada una actividad y cual es dicha actividad y el numero de captura de tiempo actual
                if (request.getParameter("asignadoGrupo")!=null) {
                    try {
                        ArrayList<APS_OIGE1> oige1 = new ArrayList<APS_OIGE1>();
                        oige1.addAll((Collection)dOIGE1.asignado(Integer.parseInt(request.getParameter("numOrden"))));
                        String json = new Gson().toJson(oige1);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.asignadoGrupo(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar que empleado tiene asignada una actividad y cual es dicha actividad y el numero de captura de tiempo actual
                if (request.getParameter("marterialesAsignados")!=null) {
                    try {
                        ArrayList<APS_OIGE3> oige3 = new ArrayList<APS_OIGE3>();
                        oige3.addAll((Collection)dOIGE3.mostrar1(request.getParameter("numOrden"),Integer.parseInt(request.getParameter("codeEmp"))));
                        String json = new Gson().toJson(oige3);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.marterialesAsignados(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar que empleado tiene asignada una actividad y cual es dicha actividad y el numero de captura de tiempo actual
                if (request.getParameter("codesEmpleados")!=null) {
                    try {
                        ArrayList<APS_OIGE> oige = new ArrayList<APS_OIGE>();
                        oige.addAll((Collection)dOIGE.codesEmpleados(Integer.parseInt(request.getParameter("numOrden"))));
                        String json = new Gson().toJson(oige);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.codesEmpleados(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar info de orden
                if (request.getParameter("guardarOrden")!=null) {
                    try {
                        or= new OWOR(Integer.parseInt(request.getParameter("numOrden")), 
                                     request.getParameter("fechaInicio"), 
                                     request.getParameter("fechaFin"), 
                                     request.getParameter("itemCode"), 
                                     request.getParameter("itemName"), 
                                     request.getParameter("status"), "S", 
                                     request.getParameter("cantPln"), 
                                     request.getParameter("cantCmpl"),  
                                     request.getParameter("cantRjct"));
                        r= dAPSO.insertar(or);                    
                        out.print(r);

                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarOrden(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar asignacion de labor(APS_OIGE)
                if (request.getParameter("guardarAsignacion")!=null) {
                    try { 
                        String codes=request.getParameter("codeInsumo");
                        String[] codeInsumo=codes.split(",");
                        String[] codeInsumos = new String[codeInsumo.length];
                        
                        String desc=request.getParameter("descInsumo");
                        String[] descInsumo=desc.split(",");
                        String[] descInsumos = new String[descInsumo.length];
                        
                        String cant=request.getParameter("cantAsignada");
                        String[] cantAsig=cant.split(",");
                        String[] cantAsignadas = new String[cantAsig.length];
                            for (int i = 0; i < codeInsumos.length; i++) {
                                try {
                                    codeInsumos[i] = codeInsumo[i];
                                    descInsumos[i] = descInsumo[i];
                                    cantAsignadas[i] = cantAsig[i];
                                } catch (Exception e) {
                                }
                            }
                            us= new Usuario();
                            us.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
                            or = new OWOR();
                            or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                            oige= new APS_OIGE();
                            oige.setCodeEmp(Integer.parseInt(request.getParameter("codeEmp")));
                            oige.setNomEmp(request.getParameter("nomEmpleado"));
                            oige.setActividad(request.getParameter("actividad"));
                            oige.setDescActividad(request.getParameter("descActividad"));
    //                        oige.setInsumo(request.getParameter("insumo"));
    //                        oige.setCantAsignada(Integer.parseInt(request.getParameter("cantAsignada")));
                            oige.setUsuario(us);
                            oige.setAPS_OWOR(or);
                            r= dOIGE.insertar(oige); 
                            if (r>0) {
                                for (int i = 0; i < codeInsumos.length; i++) {
                                    oige3 = new APS_OIGE3();
                                    oige3.setItemCode(codeInsumos[i]);
                                    oige3.setItemName(descInsumos[i]);
                                    oige3.setCantAsignada(cantAsignadas[i]);
                                    oige3.setAPS_OIGE(oige);
                                    r=dOIGE3.insertar(oige3);
                                }
                            }
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarAsignacion(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar asignacion de labor(APS_OIGE)
                if (request.getParameter("guardarAsignacionGrupo")!=null) {
                    try { 
                        String codes=request.getParameter("codeInsumo");
                        String[] codeInsumo=codes.split(",");
                        String[] codeInsumos = new String[codeInsumo.length];
                        
                        String desc=request.getParameter("descInsumo");
                        String[] descInsumo=desc.split(",");
                        String[] descInsumos = new String[descInsumo.length];
                        
                        String cant=request.getParameter("cantAsignada");
                        String[] cantAsig=cant.split(",");
                        String[] cantAsignadas = new String[cantAsig.length];
                            for (int i = 0; i < codeInsumos.length; i++) {
                                try {
                                    codeInsumos[i] = codeInsumo[i];
                                    descInsumos[i] = descInsumo[i];
                                    cantAsignadas[i] = cantAsig[i];
                                } catch (Exception e) {
                                }
                            }
                            us= new Usuario();
                            us.setIdUsuario(Integer.parseInt(request.getParameter("idUsuario")));
                            or = new OWOR();
                            or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                            oige= new APS_OIGE();
                            oige.setCodeEmp(Integer.parseInt(request.getParameter("codeEmp")));
                            oige.setNomEmp(request.getParameter("nomEmpleado"));
                            oige.setActividad(request.getParameter("actividad"));
                            oige.setDescActividad(request.getParameter("descActividad"));
    //                        oige.setInsumo(request.getParameter("insumo"));
    //                        oige.setCantAsignada(Integer.parseInt(request.getParameter("cantAsignada")));
                            oige.setUsuario(us);
                            oige.setAPS_OWOR(or);
                            r= dOIGE.insertarGrupo(oige); 
                            if (r>0) {
                                for (int i = 0; i < codeInsumos.length; i++) {
                                    oige3 = new APS_OIGE3();
                                    oige3.setItemCode(codeInsumos[i]);
                                    oige3.setItemName(descInsumos[i]);
                                    oige3.setCantAsignada(cantAsignadas[i]);
                                    oige3.setAPS_OIGE(oige);
                                    r=dOIGE3.insertar(oige3);
                                }
                            }
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarAsignacionGrupo(): "+e.getMessage());
                    }
                }
                
                //Metodo para iniciar el tiempo en asignacion de labor(APS_OIGE2)
                if (request.getParameter("iniciarTiempo")!=null) {
                    try {
                        or= new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige= new APS_OIGE();
                        oige.setInicio(request.getParameter("inicio"));
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        r= dOIGE.iniciarTiempo(oige);            
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.iniciarTiempo(): "+e.getMessage());
                    }
                }
                
                //Metodo para eliminar asignacion de labor(APS_OIGE2)
                if (request.getParameter("eliminarAsignacion")!=null) {
                    try {
                        or= new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige= new APS_OIGE();
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descAtividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        r= dOIGE.eliminar(oige);             
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.eliminarAsignacion(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar el detalle asignacion de labor(APS_OIGE1)
                if (request.getParameter("guardarDetalleAsignacion")!=null) {
                    try {
                        or = new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige = new APS_OIGE();
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        oige1 = new APS_OIGE1();
                        oige1.setInicio(request.getParameter("inicio")); 
                        oige1.setNumCaptura(Integer.parseInt(request.getParameter("numCaptura")));
                        oige1.setAPS_OIGE(oige);
                        r= dOIGE1.insertar(oige1);             
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarDetalleAsignacion(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar el Tiempo muerto en asignacion de labor(APS_OIGE2)
                if (request.getParameter("guardarTiempoMuerto")!=null) {
                    try {
                        or = new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige = new APS_OIGE();
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        oige2 = new APS_OIGE2();
                        oige2.setInicio(request.getParameter("inicio"));
                        oige2.setAPS_OIGE(oige);
                        r= dOIGE.estadoPausa(oige);
                        r= dOIGE2.insertar(oige2);             
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarTiempoMuerto(): "+e.getMessage());
                    }
                }
                        
                //Metodo para guardar el fin de la captura de tiempo en la asignacion de labor(APS_OIGE1)
                if (request.getParameter("guardarCaptura")!=null) {
                    try {
                        or= new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige= new APS_OIGE();
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        oige1 = new APS_OIGE1();
                        oige1.setFin(request.getParameter("fin"));
                        oige1.setAPS_OIGE(oige);
                        r= dOIGE1.modificarFin(oige1);
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarCaptura(): "+e.getMessage());
                    }
                }
                        
                //Metodo para guardar la cantidad de unidades realizadas en la captura de tiempo(APS_OIGE1)
                if (request.getParameter("guardarUni")!=null) {
                    try {
                        or= new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige= new APS_OIGE();
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codeEmp")));
                        oige.setAPS_OWOR(or);
                        oige1 = new APS_OIGE1();
                        oige1.setUniConformes(request.getParameter("conformes"));
                        oige1.setUniNoConformes(request.getParameter("noConformes"));
                        oige1.setUniRechazadas(request.getParameter("rechazadas"));
                        oige1.setUniTotales(request.getParameter("total"));
                        oige1.setComentario(request.getParameter("comentario"));
                        oige1.setAPS_OIGE(oige);
                        r= dOIGE1.modificarUnidades(oige1);
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarUni(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar el Tiempo muerto en asignacion de labor(APS_OIGE2)
                if (request.getParameter("finTiempoMuerto")!=null) {
                    try {
                        or= new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige= new APS_OIGE();
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        oige2 = new APS_OIGE2();
                        oige2.setFin(request.getParameter("fin")); 
                        oige2.setAPS_OIGE(oige);
                        r=dOIGE.estadoPausa1(oige);
                        r= dOIGE2.modificar(oige2);        
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.finTiempoMuerto(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar el total de unidades completadas en la asignacion(APS_OIGE)
                if (request.getParameter("guardarUniTotales")!=null) {
                    try {
                        r= dOIGE.GuardarUniTotales(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codEmp")),request.getParameter("descActividad")); 
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarUniTotales(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar el total de unidades completadas en la asignacion(APS_OIGE)
                if (request.getParameter("guardarUniTotalesConformes")!=null) {
                    try {
                        r= dOIGE.GuardarUniTotalesConformes(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codEmp")),request.getParameter("descActividad"));
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarUniTotalesConformes(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar el Tiempo muerto en asignacion de labor(APS_OIGE2)
                if (request.getParameter("finAsignacion")!=null) {
                    try {
                        or= new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        oige= new APS_OIGE();
                        oige.setFin(request.getParameter("fin"));
                        oige.setActividad(request.getParameter("actividad"));
                        oige.setDescActividad(request.getParameter("descActividad"));
                        oige.setCodeEmp(Integer.parseInt(request.getParameter("codEmp")));
                        oige.setAPS_OWOR(or);
                        oige.setTotalTiempo(dOIGE.totalTiempo(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codEmp"))));
                        oige.setTotalTiempoMuerto(dOIGE.totalTiempoMuerto(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codEmp"))));
                        r= dOIGE.modificar(oige);             
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.finAsignacion(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("listarCecos")!=null) {
                    try {
                        ArrayList<APS_OCRC> APS_OCRC = new ArrayList<APS_OCRC>();
                        APS_OCRC.addAll((Collection)dAPS_OCRC.listarAPS_OCRC());
                        String json = new Gson().toJson(APS_OCRC);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.listarCecos(): "+e.getMessage());
                    }
                }
                
                
                if (request.getParameter("updateStartDate")!=null) {
                    try {
                        String retorno="";
                        int numOrden=Integer.parseInt(request.getParameter("numOrden"));
                        int dia=Integer.parseInt(request.getParameter("dia"));
                        int mes=Integer.parseInt(request.getParameter("mes"));
                        int anio=Integer.parseInt(request.getParameter("anio"));
                        int idUsuario=Integer.parseInt(request.getParameter("idUsuario"));
                        retorno=dSAP.updateStartDateOWOR(numOrden, anio, mes, dia, idUsuario);
                        out.print(retorno);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.updateStartDate(): "+e.getMessage());
                    }
                }
                
                //METODOS PARA COMPROBAR SI UNA ORDEN PERTENECEA LINEA O EMPAQUE
                if (request.getParameter("comprobarCecosOrden")!=null) {
                    try {
                        r= dO.comprobarCecosOrden(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarCecosOrden(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar si hay asignaciones no iniciadas en op
                if (request.getParameter("comprobarInicioOrden")!=null) {
                    try {
                        int retorno=dAPSO.comprobarInicioOrden(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(retorno);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarInicioOrden(): "+e.getMessage());
                    }
                }
                
                //Metodo para solicitar edicion en de tiempo de actividades
                if (request.getParameter("solicitarEdicion")!=null) {
                    try {
                        r= dAPSO.solicitarEdicion(Integer.parseInt(request.getParameter("numOrden")),request.getParameter("comentario"));
                        out.print(r);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.solicitarEdicion(): "+e.getMessage());
                    }
                }
                
                //Metodo para comprobar si hay asignaciones no iniciadas en op
                if (request.getParameter("comprobarAprobacionOrden")!=null) {
                    try {
                        int retorno=dAPSO.comprobarAprobacionOrden(Integer.parseInt(request.getParameter("numOrden")));
                        out.print(retorno);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.comprobarAprobacionOrden(): "+e.getMessage());
                    }
                }
                
                //Metodo para guardar edicion de actividades
                if (request.getParameter("guardarEdicion")!=null) {
                    try {
                        or =new OWOR();
                        or.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        
                        oige= new APS_OIGE();
                        oige.setInicio(request.getParameter("inicio"));
                        oige.setFin(request.getParameter("fin"));
                        oige.setTotalUnidades(Integer.parseInt(request.getParameter("uniTotales")));
                        oige.setTotalUnidadesConformes(Integer.parseInt(request.getParameter("uniConformes")));
                        oige.setAPS_OWOR(or);
                        
                        oige1= new APS_OIGE1();
                        oige1.setInicio(request.getParameter("inicio"));
                        oige1.setFin(request.getParameter("fin"));
                        oige1.setUniConformes(request.getParameter("uniConformes"));
                        oige1.setUniNoConformes(request.getParameter("uniNoConformes"));
                        oige1.setUniRechazadas(request.getParameter("uniRechazadas"));
                        oige1.setUniTotales(request.getParameter("uniTotales"));
                        oige1.setComentario(request.getParameter("comentario"));
                        r=dOIGE1.insertarEdicion(oige1,Integer.parseInt(request.getParameter("numOrden")));
                        
                        if (r!=0) {
                            r= dOIGE.guardarEdicion(oige);
                            out.print(r);
                        } else {
                            out.print(-1);
                        }
                        
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarEdicion(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("comprobarCantAsignadaEdicion")!=null) {
                  try {
                    if(request.getParameter("actividad").contains("105") || request.getParameter("actividad").contains("114") || request.getParameter("actividad").contains("111") || request.getParameter("actividad").contains("106")){
                        out.print(999999999);
                    }else{
                      int total=dOIGE.comprobarCantAsignadaEmpleado(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("actividad"), Integer.parseInt(request.getParameter("codeEmp")));
                      out.print(total);
                    }
                  } catch (Exception e) {
                      System.out.println("controlador.CtrlOrden.comprobarCantAsignadaEdicion(): "+e.getMessage());
                  }
                }

                if (request.getParameter("listarArticulosA")!=null) {
                    try {
                        ArrayList<OITT> OITT = new ArrayList<OITT>();
                        OITT.addAll((Collection)dOITT.mostrarArticulosHistorico());
                        String json = new Gson().toJson(OITT);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlReportes.listarArticulosA(): " + e.getMessage());
                    }
                }
                
                if (request.getParameter("mostrarCostosOp")!=null) {
                    try {
                        ArrayList<Costo_OP> Costo_OP = new ArrayList<Costo_OP>();
                        Costo_OP.addAll((Collection)dAPS_IGN1.mostrarCostosOp(Integer.parseInt(request.getParameter("docNum"))));
                        String json = new Gson().toJson(Costo_OP);
                        out.print(json);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.mostrarCostosOp(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("cargarActividades")!=null) {                
                    try {
                        int conta=1;
                        ArrayList<APS_OIGE> ACT= new ArrayList<APS_OIGE>();
                        ACT.addAll((Collection)dSAP.cargarActividades(Integer.parseInt(request.getParameter("numOrden"))));
                        for (APS_OIGE obj : ACT) {
                            out.println("<tr>"+
                                        "<td><input type='checkbox' name='select_act' value='"+conta+"' "+(obj.getEstado()!=1?"checked":"")+"><p id='idAPS_OIGE"+conta+"' hidden='true'>"+obj.getIdAPS_OIGE()+"</p></td>" +
                                        //"<td><div class='form-group' id='selectActividades"+conta+"'><select id='ActividadProd"+conta+"' name='ActividadProd[]' class='form-control' autocomplete='off' style='width: 100%' placeholder='Seleccione una actividad'></select></div></td>"+ 
                                        "<td>"+obj.getNomEmp()+"</td>"+
                                        "<td>"+obj.getActividad()+"</td>"+
                                        "<td>"+obj.getInicio()+"</td>"+
                                        "<td>"+obj.getFin()+"</td>"+
                                        "<td>"+obj.getTotalTiempo()+"</td>"+ 
                                        "<td>"+obj.getTotalUnidadesConformes()+"</td>"+
                                        "<td>"+obj.getTotalUnidadesNoConformes()+"</td>"+ 
                                        "<td>"+obj.getInsumo()+"</td>"+ 
                                        "<td>"+obj.getEstado()+"</td>"+ 
                                        "<td>"+obj.getDescActividad()+"%</td>"+ 
                                        "</tr>");
                        conta++;
                        }
                    } catch (Exception e) {
                         System.out.println("controlador.CtrlOrden.cargarActividades(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("mostrarMaterialesCarga")!=null) {                
                    try {
                        ArrayList<APS_OIGE3> arr= new ArrayList<APS_OIGE3>();
                        arr.addAll((Collection)dSAP.mostrarMateriales(Integer.parseInt(request.getParameter("numOrden")), request.getParameter("itemCode")));
                        String json= new Gson().toJson(arr);
                        out.print(json);
                    } catch (Exception e) {
                         System.out.println("controlador.CtrlOrden.mostrarMateriales(): "+e.getMessage());
                    }
                }
                
                if(request.getParameter("cargarMateriales")!=null){
                    try{
                        String respuesta="";
                        respuesta=dSAP.addMateriales(Integer.parseInt(request.getParameter("numOrden")),request.getParameter("itemCode"),request.getParameter("itemName"),request.getParameter("cantMateriales"),0);
                        out.print(respuesta);
                    }catch(Exception e){
                        e.printStackTrace();
                         System.out.println("controlador.CtrlOrden.cargarMateriales(): "+e.getMessage());
                    }
                }
            
                if(request.getParameter("cargarProduccion")!=null){
                    try{
                        String respuesta="";
                        APS_IGN1 aps_ign1 = new APS_IGN1();
                        OWOR owor = new OWOR();
                        owor.setDocnum(Integer.parseInt(request.getParameter("numOrden")));
                        aps_ign1.setAPS_OWOR(owor);
                         System.out.println("controlador.CtrlOrden.cargarProduccion(): "+request.getParameter("numOrden"));
                        //dAPS_IGN1.eliminar(aps_ign1);
                        respuesta=dSAP.addProduccion(Integer.parseInt(request.getParameter("numOrden")), Integer.parseInt(request.getParameter("uniConformes")), Integer.parseInt(request.getParameter("uniNoConformes")));
                         System.out.println("controlador.CtrlOrden.cargarProduccion(): "+request.getParameter("uniNoConformes"));
                        out.print(respuesta);

                    }catch(Exception e){
                         System.out.println("controlador.CtrlOrden.cargarProduccion(): "+e.getMessage());
                    }
                }
                
                if(request.getParameter("cargarMaterialesAPS")!=null){
                    try{
                        int respuesta=0;
                        
                        dOIGE3.cancelarENMTOrden(request.getParameter("numOrden"));
                        
                        respuesta=dOIGE3.guardarENMT(request.getParameter("itemCode"), 
                                                     request.getParameter("cantMateriales"), 
                                                     Integer.parseInt(request.getParameter("idUsuario")), 
                                                     Integer.parseInt(request.getParameter("numOrden")));
                        //respuesta=sap.addMateriales(Integer.parseInt(request.getParameter("numOrden")),request.getParameter("itemCode"),request.getParameter("itemName"),request.getParameter("cantMateriales"));
                        out.print(respuesta);
                    }catch(Exception e){
                         System.out.println("controlador.CtrlOrden.cargarMaterialesAPS(): "+e.getMessage());
                    }
                }
            
                if(request.getParameter("cargarProduccionAPS")!=null){
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
                        dAPS_IGN1.eliminar(aps_ign1);
                            respuesta=dAPS_IGN1.insertar(aps_ign1);
                        //respuesta=sap.addProduccion(Integer.parseInt(request.getParameter("numOrden")), Integer.parseInt(request.getParameter("uniConformes")), Integer.parseInt(request.getParameter("uniNoConformes")));
                        out.print(respuesta);
                    }catch(Exception e){
                         System.out.println("controlador.CtrlRecibo.cargarProduccionAPS(): "+e.getMessage());
                    }
                }
            
                if(request.getParameter("estadoMarcacion")!=null){
                    try{
                        String respuesta="";
                        respuesta=dOIGE.estadoMarcacion(Integer.parseInt(request.getParameter("codeEmp")),request.getParameter("fecha"));
                        out.print(respuesta);
                    }catch(Exception e){
                         System.out.println("controlador.CtrlOrden.estadoMarcacion(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("guardarPlanAccion")!=null) {
                    try {
                        int respuesta=dAPSO.guardarPlanAccion(Integer.parseInt(request.getParameter("docNum")),
                                                              request.getParameter("planAccion"),
                                                              request.getParameter("observacion"),
                                                              request.getParameter("fechaAccion"),
                                                              request.getParameter("responsable"));
                        out.print(respuesta);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.planAccion(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("guardarEstadoCierre")!=null) {
                    try {
                        int respuesta=dAPSO.guardarEstadoCierre(Integer.parseInt(request.getParameter("docNum")));
                        out.print(respuesta);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.planAccion(): "+e.getMessage());
                    }
                }
            
                if (request.getParameter("mostrarInsumosOp")!=null) {                
                    try {
                        int count=1;
                        ArrayList<Costo_OP> ops= new ArrayList<Costo_OP>();
                        ops.addAll((Collection)dAPS_IGN1.mostrarInsumosOp(request.getParameter("docNum")));
                        String estado="";
                        for (Costo_OP obj : ops) {
                            out.println("<tr>"+ 
                                            "<td>"+(count)+ "</td>"+
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
                                            "<td><input type='text' class='form-control' id='commentVar" + count + "' value='" + obj.getComentarios() + "'></td>"+
                                        "</tr>");
                            count++;
                        }
                    } catch (Exception e) {
                         System.out.println("controlador.CtrlAutorizacionOps.mostrarActividades(): "+e.getMessage());
                    }
                }
            
                if (request.getParameter("mostrarCostosLineasOp")!=null) {                
                    try {
                        int count=1;
                        ArrayList<Costo_OP> ops= new ArrayList<Costo_OP>();
                        ops.addAll((Collection)dAPS_IGN1.mostrarInsumosOp(request.getParameter("docNum")));
                        String estado="";
                        for (Costo_OP obj : ops) {
                            out.println("<tr>"+ 
                                            "<td>"+(count)+ "</td>"+
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
                                            "<td><input type='text' class='form-control' id='commentVarC" + count + "' value='" + obj.getComentarios() + "'></td>"+
                                        "</tr>");
                            count++;
                        }
                    } catch (Exception e) {
                         System.out.println("controlador.CtrlAutorizacionOps.mostrarActividades(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("guardarComentarios")!=null) {
                    try {
                        CVAR cvar = new CVAR();
                        cvar.setDocNum(Integer.parseInt(request.getParameter("docNum")));
                        cvar.setLineNum(Integer.parseInt(request.getParameter("lineNum")));
                        cvar.setItemCode(request.getParameter("itemCode"));
                        cvar.setVariacion(Double.parseDouble(request.getParameter("variacion")));
                        cvar.setComentario(request.getParameter("comentario"));
                        int respuesta=dO.guardarComentarios(cvar);
                        out.print(respuesta);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.guardarComentarios(): "+e.getMessage());
                    }
                }
                
                if (request.getParameter("eliminarComentarios")!=null) {
                    try {
                        int respuesta=dO.eliminarComentarios(Integer.parseInt(request.getParameter("docNum")));
                        out.print(respuesta);
                    } catch (Exception e) {
                        System.out.println("controlador.CtrlOrden.eliminarComentarios(): "+e.getMessage());
                    }
                }
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
