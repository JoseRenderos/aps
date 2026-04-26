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
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import java.time.LocalDate;
import java.io.File;
import java.util.*;
import java.io.File.*;
import java.sql.*;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.fill.*;
import modelo.ConexionSDO;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

/**
 *
 * @author Mario Valdez
 */
public class CtrlReportesPDF extends HttpServlet {

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
                        
            if (request.getParameter("reporte")==null) {
                response.sendRedirect("frmInformes.jsp");
            } else {
            
                String reporte=request.getParameter("reporte");
                LocalDate fecha = LocalDate.now();
                try{
                    if (reporte.equals("1")) {
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put(null, null);
                            File jasperFile = new File(servletContext.getRealPath(("reportes/RLA.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=RLA_"+fecha+".pdf");
                            response.setContentLength(bytes.length);
                            
                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();

                        } catch (Exception e) {
                        }
                    } else if(reporte.equals("2")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("docnum", request.getParameter("numOrden"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_orden.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=ReporteOrden"+request.getParameter("numOrden")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("3")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_OrdenFecha.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_OrdenFecha_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("4")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ProdEmp.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ProdEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("5")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("cecos", request.getParameter("cecos"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ControlAvanceV2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ControlAvance_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+"_CECOS_"+request.getParameter("cecos")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("6")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_eficienciaEmp.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_eficienciaEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("7")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("depto", request.getParameter("depto"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_EficienciaPlanta.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_eficienciaPlanta__"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("8")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoProd.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("9")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("idUsuario", Integer.parseInt(request.getParameter("idUsuario")));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_Usuario.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Usuario_"+request.getParameter("usuario")+"_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("10")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("cecos", "%" + request.getParameter("cecos") + "%");
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_OrdenCecos.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_"+request.getParameter("descCecos")+"_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("11")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajo.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("12")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajoL.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_LABOR_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("13")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajoM.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_MAQUINA_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("14")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha", request.getParameter("fecha"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_DatosProp.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Datos_Produccion_"+request.getParameter("fecha")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("15")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("depto", "13");
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_EficienciaEmpaque.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_eficienciaEmpaque__"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if (reporte.equals("17")) {
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put(null, null);
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_AvanceMes.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_AvanceMes.pdf");
                            response.setContentLength(bytes.length);
                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();

                        } catch (Exception e) {
                        }
                    } else if(reporte.equals("18")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajo_V2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("19")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajoL_V2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_TrabajoL_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("20")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajoM_V2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_TrabajoL_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("21")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("itemCode", request.getParameter("itemCode"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HistoricoArt.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_HistoricoArt_"+request.getParameter("itemCode")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("22")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ProdEmpV2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ProdEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("23")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("docNum", Integer.parseInt(request.getParameter("docNum")));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_CostoOP.jasper")));
                            
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con); 

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_CostoOP_"+request.getParameter("docNum")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("24")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("itemCode", request.getParameter("itemCode"));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_CostoArt.jasper")));
                            
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con); 

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_CostoArt_"+request.getParameter("itemCode")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("25")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_CostoFecha.jasper")));
                            
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con); 

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_CostoFecha_desde_"+request.getParameter("fecha1")+"_hasta_"+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("26")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_CostoFechaV2.jasper")));
                            
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con); 

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_CostoFecha_desde_"+request.getParameter("fecha1")+"_hasta_"+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("27")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_Costo_Tinas.jasper")));
                            
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con); 
                            System.out.println(jasperFile.getPath());

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_CostoTinas_desde_"+request.getParameter("fecha1")+"_hasta_"+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("28")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajo_ActVarias.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("29")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajo_planilla.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("30")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ProdEmp_1.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ValEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("31")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoEmp.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("32")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codDepto", Integer.parseInt(request.getParameter("depto")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoEmp_depto.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_depto_"+request.getParameter("depto")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rValorEmp")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ValorEmp.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ValorEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rValorDepto")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codDepto", Integer.parseInt(request.getParameter("depto")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ValorEmp_depto.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ValorEmp_depto_"+request.getParameter("depto")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("RTransferencia")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("id", Integer.parseInt(request.getParameter("id")));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_Transferencia.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Transferencia"+request.getParameter("id")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rPagoMin")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoEmp_min_v2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rPagoMinDepto")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codDepto", Integer.parseInt(request.getParameter("depto")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoEmp_depto_min_v2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_depto_"+request.getParameter("depto")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rPagoMinV3")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoEmp_min_v3.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rPagoTorno")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codeEmp", Integer.parseInt(request.getParameter("codeEmp")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoTorno.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_"+request.getParameter("codeEmp")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rPagoTornoDepto")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codDepto", Integer.parseInt(request.getParameter("depto")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_Pagodepto.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_depto_"+request.getParameter("depto")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rPagoMinDeptoV3")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("codDepto", Integer.parseInt(request.getParameter("depto")));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_PagoEmp_depto_min_v3.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_PagoEmp_depto_"+request.getParameter("depto")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rHorasSH")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajo_SH_ant.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    } else if(reporte.equals("rConsumoMP")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_MP_a_consumir_plan.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_MP_a_consumir_plan_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }  else if(reporte.equals("vinhetaArt")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("ID", request.getParameter("id"));
                            parametro.put("STATUS", request.getParameter("status").equals(null)?"":request.getParameter("status"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/vinhetaArt.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=vinhetaArt_"+request.getParameter("id")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();
                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }  else if(reporte.equals("rEficienciaArt")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("itemCode", request.getParameter("itemcode"));
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_eficienciaArt.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_eficienciaArt_"+request.getParameter("itemcode")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();

                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rhorasPlanilllaV2")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_HorasTrabajo_planilla_V2.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_Cecos_Horas_Trabajo_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rControlhoras")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(("reportes/")));
                            File jasperFile = new File(servletContext.getRealPath(("reportes/Reporte_ControlHoras_planilla.jasper")));
                            byte[] bytes = JasperRunManager.runReportToPdf(jasperFile.getPath(), parametro, con);

                            response.setContentType("application/pdf");
                            response.setHeader("Content-disposition", "filename=Reporte_ControlHoras_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            response.setContentLength(bytes.length);

                            ServletOutputStream ouput =response.getOutputStream();


                            ouput.write(bytes,0,bytes.length);
                            ouput.flush();
                            ouput.close();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }else if(reporte.equals("rControlhorasExcel")){
                        try {
                            ServletContext servletContext = this.getServletContext();
                            JasperReport report = JasperCompileManager.compileReport(servletContext.getRealPath("reportes/Reporte_ControlHoras_planilla.jrxml"));

                            // Conexión con la BD
                            ConexionSDO sdo = new ConexionSDO();
                            Connection con = sdo.con();
                            con.setAutoCommit(false);
                            // Posibles parámetros para pasar al informe. Ninguno en nuestro ejemplo
                            Map parametro = new HashMap();
                            parametro.put("fecha1", request.getParameter("fecha1"));
                            parametro.put("fecha2", request.getParameter("fecha2"));
                            parametro.put("SUBREPORT_DIR",servletContext.getRealPath(servletContext.getRealPath(("reportes/"))));

                            // Obtención de la vista JasperPrint del informe 
                            JasperPrint print = JasperFillManager.fillReport(report, parametro, con);

                            // Mostrando el documento
                            ServletOutputStream servletOutputStream;
                            response.setContentType("application/vnd.ms-excel");
                            response.setHeader("Content-disposition", "filename=Reporte_ControlHoras_"+request.getParameter("fecha1")+" - "+request.getParameter("fecha2")+".pdf");
                            

                            JRXlsxExporter exporterXLS = new JRXlsxExporter();

                            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print);
                            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
                            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                            exporterXLS.exportReport();


//                            // Exportar el informe a formato Excel
//                            JRXlsxExporter exporter = new JRXlsxExporter();
//                            exporter.setExporterInput(new SimpleExporterInput(print));
//                            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput("Informe.xlsx"));
//                            exporter.exportReport();
                        } catch (Exception e) {
                           System.out.println(e.getMessage()); 
                        }
                    }
                }catch(Exception e){
                    System.out.println("CtrlReportesPDF: " +e.getMessage());
                }
            }
        return;
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
