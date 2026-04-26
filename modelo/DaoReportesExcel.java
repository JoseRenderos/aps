/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import entidades.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Mario Valdez
 */
public class DaoReportesExcel extends ConexionSBO{
    
    PreparedStatement ps;
    ResultSet rs;
    int res;
    Usuario us;
    APS_OIGE APS_OIGE; 
    APS_OIGE1 APS_OIGE1;
    APS_OIGE2 APS_OIGE2;
    OWOR OWOR;
    PagoEmp pago ;
    String fecha;
    EficienciaEmp eficienciaEmp;
    Costo_OP costo_OP;
    HorasTrabajo hTrabajo;
    OPCierre oPCierre;
    NART nart;
    NCART ncart;
    NPRF nprf;
    
    public ArrayList<APS_OIGE> mostrarReporteExcel(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try {
            ps=super.con().prepareStatement("SELECT DISTINCT  " +
"        T0.DOCNUM 'NUMERODEOP',  " +
"        T0.ItemCode 'ITEMCODE',  " +
"        (SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME',  " +
"        CASE  " +
"            WHEN T0.status='P' THEN 'Planificada'  " +
"            WHEN T0.status='R' THEN 'Liberada'  " +
"            WHEN T0.status='C' THEN 'Cancelada'  " +
"            WHEN T0.status='L' THEN 'Cerrada'  " +
"        END 'STATUS',  " +
"        CONVERT(numeric(19,2),T0.PlannedQty) 'CANTPLN',  " +
"        CONVERT(VARCHAR,CONVERT(numeric(19,2),T0.CmpltQty)) 'CANTCMP',  " +
"        CONVERT(VARCHAR,CONVERT(numeric(19,2),T0.RjctQty)) 'CANTRJC',  " +
"        CAST(T0.StartDate AS DATE) 'INICIO',  " +
"        CAST(T0.DueDate AS DATE)'VENCIMIENTO',  " +
"        ISNULL((SELECT K0.usuario FROM SDO_ALSASA..USUARIO K0 WHERE K0.idUsuario=T2.idUsuario),'') 'USUARIO',  " +
"        ISNULL(T2.actividad,'') 'ACTIVIDAD',  " +
"        ISNULL(T2.descActividad,'') 'DESC_ACT',  " +
"        ISNULL(T2.nomEmp,'') 'EMPL',  " +
"        ISNULL(STUFF((  " +
"            SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada  " +
"            FROM SDO_ALSASA..APS_OIGE3 K0  " +
"            JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE  " +
"            WHERE K0.idAPS_OIGE=T2.idAPS_OIGE FOR XML PATH (''))  " +
"        ,1,2,''),'')  'insumo',  " +
"        FORMAT(T2.inicio, 'dd-MM-yyyy HH-mm') 'INICIO_ACT',  " +
"        FORMAT(T2.fin, 'dd-MM-yyyy HH-mm') 'FIN_ACT',  " +
"        CONVERT(numeric(19,2),CONVERT(numeric(19,2), T2.totalTiempo)/60) 'TIEMPO',  " +
"        ISNULL(T2.uniTotales,'') 'UNITOTALES',  " +
"        ISNULL(T2.uniTotalesConformes,'') 'COMPLETADAS',  " +
"        ISNULL((SELECT SUM(K0.uniNoConformes+K0.uniRechazadas) FROM SDO_ALSASA..APS_OIGE1 K0 WHERE K0.idAPS_OIGE=T2.idAPS_OIGE),'') 'RECHAZADAS',  " +
"        ISNULL((   " +
"			SELECT TOP 1   " +
"				CONVERT(numeric(19,2),(K1.Qauntity / K0.Quantity)) 'EstadarH'   " +
"			FROM SBO_ALSASA..ITT1 K0   " +
"			JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0. FATHER COLLATE SQL_Latin1_General_CP850_CI_AS   " +
"			WHERE K0.Type = 290   " +
"			AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'   " +
"			AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS   " +
"			AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS   " +
"			AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ),0) 'estandarH',  " +
"			   CASE      " +
"			WHEN      " +
"				(CASE     " +
"					WHEN     " +
"						(SELECT TOP 1      " +
"							CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'      " +
"						FROM SBO_ALSASA..ITT1 K0      " +
"						JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER      " +
"						WHERE K0.Type = 290      " +
"						AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'      " +
"						AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS      " +
"						AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"						AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ) IS NULL     " +
"					THEN     " +
"						(SELECT TOP 1      " +
"							CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'      " +
"						FROM SBO_ALSASA..ITT1 K0      " +
"						JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER      " +
"						WHERE K0.Type = 290      " +
"						AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'      " +
"						AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS       " +
"						AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR      " +
"						K0.Type = 290)     " +
"					ELSE     " +
"						(SELECT TOP 1      " +
"							CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'      " +
"						FROM SBO_ALSASA..ITT1 K0      " +
"						JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER      " +
"						WHERE K0.Type = 290      " +
"						AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'      " +
"						AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS    " +
"						AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS      " +
"						AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS )     " +
"				END) IS NULL " +
"				OR ISNULL(CASE   " +
"					WHEN LEFT(T2.actividad,3)='114'  " +
"					THEN CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OWOR=T2.idAPS_OWOR))  " +
"					ELSE CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T2.idAPS_OIGE))   " +
"				END,0) =0 " +
"		THEN      " +
"			0  " +
"		ELSE      " +
"			CONVERT(numeric(19,2),      " +
"				(ISNULL((      " +
"					CASE   " +
"						WHEN LEFT(T2.actividad,3)='114'  " +
"						THEN ISNULL((SELECT CONVERT(NUMERIC(19,6),SUM(K1.uniConformes)) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.codeEmp=T2.codeEmp AND K0.idAPS_OWOR=T2.idAPS_OWOR),0)  " +
"						ELSE ISNULL((SELECT CONVERT(NUMERIC(19,6),SUM(K1.uniConformes)) FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.codeEmp=T2.codeEmp AND K0.idAPS_OIGE=T2.idAPS_OIGE),0)  " +
"					END  " +
"				/      " +
"				(ISNULL(CASE   " +
"					WHEN LEFT(T2.actividad,3)='114'  " +
"					THEN CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OWOR=T2.idAPS_OWOR))  " +
"					ELSE CONVERT(NUMERIC(19,2), (SELECT CONVERT(NUMERIC(19,6),SUM(CASE WHEN DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) IS NULL THEN 0.01 ELSE DATEDIFF(MINUTE,K1.inicio,CASE WHEN K1.fin IS NULL THEN DATEADD(MINUTE,1,K1.inicio) ELSE K1.fin END) END))/60 FROM SDO_ALSASA..APS_OIGE K0 JOIN SDO_ALSASA..APS_OIGE1 K1 ON K0.idAPS_OIGE=K1.idAPS_OIGE WHERE K0.idAPS_OIGE=T2.idAPS_OIGE))   " +
"				END,1)  " +
"					*      " +
"				ISNULL((      " +
"					CASE     " +
"						WHEN     " +
"							(SELECT TOP 1      " +
"								CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'      " +
"							FROM SBO_ALSASA..ITT1 K0      " +
"							JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER      " +
"							WHERE K0.Type = 290      " +
"							AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'      " +
"							AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS      " +
"							AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"							AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS ) IS NULL     " +
"						THEN     " +
"							(SELECT TOP 1      " +
"								CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'      " +
"							FROM SBO_ALSASA..ITT1 K0      " +
"							JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER      " +
"							WHERE K0.Type = 290      " +
"							AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'      " +
"							AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS   " +
"							AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR      " +
"							K0.Type = 290)     " +
"						ELSE      " +
"							(SELECT TOP 1      " +
"								CONVERT(numeric(19,6),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH'      " +
"							FROM SBO_ALSASA..ITT1 K0      " +
"							JOIN SBO_ALSASA..OITT K1 ON K1.Code = K0.FATHER      " +
"							WHERE K0.Type = 290      " +
"							AND (SELECT J0.ResType FROM SBO_ALSASA..ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L'      " +
"							AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS      " +
"							AND ISNULL(K0.Comment,'')=ISNULL(T2.DescActividad,'') COLLATE SQL_Latin1_General_CP850_CI_AS     " +
"							AND K1.CODE=T0.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS )      " +
"					END      " +
"			),1))      " +
"			),0)*100)      " +
"		)      " +
"	END 'Eficiencia',  " +
"        ISNULL(STUFF((SELECT ', '+ K0.comentario  " +
"            FROM SDO_ALSASA..APS_OIGE1 K0  " +
"            JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE  " +
"            WHERE K0.comentario!='' AND K0.comentario IS NOT NULL AND K1.idAPS_OIGE=T2.idAPS_OIGE FOR XML PATH ('')),1,2,''),'') 'COMENTARIOS',  " +
"        CASE " +
"            WHEN (SELECT COUNT(DISTINCT K0.docEntry) FROM IGE1 K0 WHERE K0.baseEntry=T0.docEntry)='0'  " +
"			THEN ''  " +
"			ELSE  " +
"                'SI'  " +
"        END 'EMISION_PROD'  " +
"    FROM OWOR T0  " +
"    JOIN SDO_ALSASA..APS_OWOR T1 ON T0.docNum=T1.docNum  " +
"    JOIN SDO_ALSASA..APS_OIGE T2 ON T1.idAPS_OWOR=T2.idAPS_OWOR " +
"    WHERE CAST(T0.StartDate AS DATE) BETWEEN ? and ? " +
"    AND ISNULL(T2.uniTotales,0)!=0 AND ISNULL(T2.totalTiempo,0)!=0 ");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                us= new Usuario();
                APS_OIGE = new APS_OIGE();
                OWOR = new OWOR();
                OWOR.setDocnum(rs.getInt(1));
                OWOR.setItemcode(rs.getString(2));
                OWOR.setItemname(rs.getString(3));
                OWOR.setStatus(rs.getString(4));
                OWOR.setCantpln(rs.getString(5));
                OWOR.setCantcmp(rs.getString(6));
                OWOR.setCantrjc(rs.getString(7));
                OWOR.setStartdate(rs.getString(8));
                OWOR.setDuedate(rs.getString(9));
                us.setUsuario(rs.getString(10));
                APS_OIGE.setActividad(rs.getString(11));
                APS_OIGE.setDescActividad(rs.getString(12));
                APS_OIGE.setNomEmp(rs.getString(13));
                APS_OIGE.setInsumo(rs.getString(14));
                APS_OIGE.setInicio(rs.getString(15));
                APS_OIGE.setFin(rs.getString(16));
                APS_OIGE.setTotalTiempo(rs.getString(17));
                APS_OIGE.setTotalUnidades(rs.getInt(18));
                APS_OIGE.setTotalUnidadesConformes(rs.getInt(19));
                APS_OIGE.setTotalUnidadesNoConformes(rs.getInt(20));
                APS_OIGE.setTotalTiempoMuerto(rs.getString(21));
                APS_OIGE.setCodeActProd(rs.getString(22));
                APS_OIGE.setDescActividadProd(rs.getString(23));
                us.setPass(rs.getString(24));
                APS_OIGE.setAPS_OWOR(OWOR); 
                APS_OIGE.setUsuario(us);
                ar.add(APS_OIGE);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcel(): "+e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    public ArrayList<APS_OIGE> mostrarReporteExcelCecos(String fecha1, String fecha2, String cecos) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try {
            ps=super.con().prepareStatement("SELECT DISTINCT " +
                                            "   T0.DOCNUM 'NUMERODEOP', " +
                                            "	T0.ItemCode 'ITEMCODE', " +
                                            "	(SELECT K0.ITEMNAME FROM OITM K0 WHERE K0.ITEMCODE=T0.ITEMCODE) 'ITEMNAME', " +
                                            "	CASE " +
                                            "       WHEN T0.status='P' THEN 'Planificada' " +
                                            "       WHEN T0.status='R' THEN 'Liberada' " +
                                            "       WHEN T0.status='C' THEN 'Cancelada' " +
                                            "       WHEN T0.status='L' THEN 'Cerrada' " +
                                            "	END 'STATUS', " +
                                            "	CONVERT(numeric(19,2),T0.PlannedQty) 'CANTPLN', " +
                                            "	CASE" +
                                            "       WHEN " +
                                            "           CONVERT(VARCHAR,CONVERT(numeric(19,2),T0.CmpltQty))='0.00' " +
                                            "       THEN '' " +
                                            "   ELSE " +
                                            "       CONVERT(VARCHAR,CONVERT(numeric(19,2),T0.CmpltQty)) " +
                                            "   END 'CANTCMP', " +
                                            "   CASE " +
                                            "       WHEN " +
                                            "           CONVERT(VARCHAR,CONVERT(numeric(19,2),T0.RjctQty))='0.00' " +
                                            "       THEN '' " +
                                            "       ELSE " +
                                            "           CONVERT(VARCHAR,CONVERT(numeric(19,2),T0.RjctQty)) " +
                                            "	END 'CANTRJC', " +
                                            "	CONVERT(VARCHAR(10),T0.StartDate,103) 'INICIO', " +
                                            "	CONVERT(VARCHAR(10),T0.DueDate,103)'VENCIMIENTO', " +
                                            "	ISNULL(T4.usuario,'') 'USUARIO', " +
                                            "	ISNULL(T2.actividad,'') 'ACTIVIDAD', " +
                                            "	ISNULL(T2.descActividad,'') 'DESC_ACT', " +
                                            "	ISNULL(T2.nomEmp,'') 'EMPL', " +
                                            "	CASE " +
                                            "       WHEN " +
                                            "           STUFF(( " +
                                            "               SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada " +
                                            "               FROM SDO_ALSASA..APS_OIGE3 K0 " +
                                            "               JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                            "               WHERE K0.idAPS_OIGE=T2.idAPS_OIGE FOR XML PATH ('')) " +
                                            "               ,1,2,'') IS NULL " +
                                            "       THEN " +
                                            "           ISNULL((SELECT " +
                                            "           CONCAT(SUBSTRING(K0.insumo,0, CHARINDEX('-', K0.insumo)-1)  ,' Cantidad Asig: ', " +
                                            "           K0.cantAsignada) " +
                                            "           FROM SDO_ALSASA..APS_OIGE K0 " +
                                            "           WHERE K0.idAPS_OIGE=T2.idAPS_OIGE),'') " +
                                            "       ELSE " +
                                            "           ISNULL(STUFF(( " +
                                            "               SELECT ', ' + K0.itemCode + ' Cantidad Asig: ' + K0.cantAsignada " +
                                            "               FROM SDO_ALSASA..APS_OIGE3 K0 " +
                                            "               JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                            "               WHERE K0.idAPS_OIGE=T2.idAPS_OIGE FOR XML PATH ('')) " +
                                            "           ,1,2,''),'') " +
                                            "   END 'insumo', " +
                                            "	ISNULL(CONVERT(VARCHAR(19),T2.inicio,120),'') 'INICIO_ACT', " +
                                            "	ISNULL(CONVERT(VARCHAR(19),T2.fin,120),'') 'FIN_ACT', " +
                                            "	ISNULL(CONVERT(VARCHAR,CONVERT(numeric(19,2),CONVERT(numeric(19,2), T2.totalTiempo)/60)),'') 'TIEMPO', " +
                                            "	ISNULL(T2.uniTotales,'') 'UNITOTALES', " +
                                            "	ISNULL(T2.uniTotalesConformes,'') 'COMPLETADAS', " +
                                            "	ISNULL((T3.uniNoConformes+T3.uniRechazadas),'') 'RECHAZADAS', " +
                                            "   ISNULL(( " +
                                            "	SELECT TOP 1 " +
                                            "       CONVERT(VARCHAR,CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3))) 'EstadarH' " +
                                            "	FROM ITT1 K0 " +
                                            "	JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "	COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "   WHERE  K0.Type = 290 " +
                                            "	AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "	AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	AND K0.Comment=T2.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	OR " +
                                            "	K0.Type = 290 " +
                                            "	AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "	AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "	AND K0.Comment is null	), '') 'estandarH', " +
                                            "	CASE " +
                                            "	WHEN " +
                                            "    (CASE" +
                                            "       WHEN" +
                                            "           (SELECT TOP 1 " +
                                            "               CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "           FROM ITT1 K0 " +
                                            "		JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "		WHERE K0.Type = 290 " +
                                            "		AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "		AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K0.Comment=T2.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "		K0.Type = 290 " +
                                            "		AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "		AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K0.Comment IS NULL) IS NULL" +
                                            "       THEN" +
                                            "           (SELECT TOP 1 " +
                                            "               CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "		FROM ITT1 K0 " +
                                            "		JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "		WHERE K0.Type = 290 " +
                                            "		AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "		AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "		K0.Type = 290)" +
                                            "       ELSE" +
                                            "		(SELECT TOP 1 " +
                                            "               CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "		FROM ITT1 K0 " +
                                            "		JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "		WHERE K0.Type = 290 " +
                                            "		AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "		AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K0.Comment=T2.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "		K0.Type = 290 " +
                                            "		AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "		AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "		AND K0.Comment IS NULL)" +
                                            "	END) IS NULL" +
                                            "	THEN " +
                                            "       0 " +
                                            "	ELSE " +
                                            "       CONVERT(numeric(19,2), " +
                                            "           (ISNULL(( " +
                                            "               CASE " +
                                            "                   WHEN " +
                                            "                       T2.uniTotales=0 " +
                                            "			THEN 1 " +
                                            "			ELSE " +
                                            "                       ISNULL(T2.uniTotales,1) " +
                                            "			END " +
                                            "			/ " +
                                            "			(CASE " +
                                            "                       WHEN " +
                                            "                           (CONVERT(numeric(19,6),CONVERT(numeric(19,6), T2.totalTiempo)/60))=0 " +
                                            "                       THEN 1 " +
                                            "                       ELSE " +
                                            "                           ISNULL((CONVERT(numeric(19,6),CONVERT(numeric(19,6), T2.totalTiempo)/60)),1) " +
                                            "                       END " +
                                            "                   * " +
                                            "                   ISNULL(( " +
                                            "                       CASE" +
                                            "                           WHEN" +
                                            "                               (SELECT TOP 1 " +
                                            "                                   CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "                               FROM ITT1 K0 " +
                                            "                               JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "                               WHERE K0.Type = 290 " +
                                            "                               AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "                               AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K0.Comment=T2.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "                               K0.Type = 290 " +
                                            "                               AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "                               AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K0.Comment IS NULL) IS NULL" +
                                            "                           THEN" +
                                            "                               (SELECT TOP 1 " +
                                            "                                   CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "                               FROM ITT1 K0 " +
                                            "                               JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "                               WHERE K0.Type = 290 " +
                                            "                               AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "                               AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "                               K0.Type = 290)" +
                                            "                           ELSE " +
                                            "                               (SELECT TOP 1 " +
                                            "                                   CONVERT(numeric(19,0),ROUND((K1.Qauntity / K0.Quantity), 3)) 'EstadarH' " +
                                            "                               FROM ITT1 K0 " +
                                            "                               JOIN OITT K1 ON K1.Code = K0.FATHER " +
                                            "                               WHERE K0.Type = 290 " +
                                            "                               AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "                               AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K0.Comment=T2.DescActividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS OR " +
                                            "                               K0.Type = 290 " +
                                            "                               AND (SELECT J0.ResType FROM ORSC J0 WHERE J0.VisResCode = K0.Code) = 'L' " +
                                            "                               AND K0.CODE=T2.actividad COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K1.CODE=T1.itemCode COLLATE SQL_Latin1_General_CP850_CI_AS " +
                                            "                               AND K0.Comment IS NULL) " +
                                            "                       END " +
                                            "			),1)) " +
                                            "           ),0)*100) " +
                                            "		) " +
                                            "	END 'Eficiencia', " +
                                            "	ISNULL(STUFF((SELECT ', '+ K0.comentario " +
                                            "       FROM SDO_ALSASA..APS_OIGE1 K0 " +
                                            "       JOIN SDO_ALSASA..APS_OIGE K1 ON K1.idAPS_OIGE=K0.idAPS_OIGE " +
                                            "       WHERE K0.comentario!='' AND K0.comentario IS NOT NULL AND K1.idAPS_OIGE=T2.idAPS_OIGE FOR XML PATH ('')),1,2,''),'') 'COMENTARIOS',	"+
                                            "   CASE" +
                                            "       WHEN" +
                                            "           (SELECT COUNT(DISTINCT K0.docEntry) FROM IGE1 K0 WHERE K0.baseEntry=T0.docEntry)='0' " +
                                            "	THEN '' " +
                                            "	ELSE " +
                                            "           'SI' " +
                                            "   END 'EMISION_PROD' " +
                                            "FROM OWOR T0 " +
                                            "LEFT JOIN SDO_ALSASA..APS_OWOR T1 ON T0.docNum=T1.docNum " +
                                            "LEFT JOIN SDO_ALSASA..APS_OIGE T2 ON T1.idAPS_OWOR=T2.idAPS_OWOR " +
                                            "LEFT JOIN SDO_ALSASA..APS_OIGE1 T3 ON T2.idAPS_OIGE=T3.idAPS_OIGE " +
                                            "LEFT JOIN SDO_ALSASA..USUARIO T4 ON T4.idUsuario=T2.idUsuario "+
                                            "LEFT JOIN WOR1 T5 ON T0.DocEntry=T5.DocEntry " +
                                            "WHERE CONVERT(VARCHAR(10),T0.StartDate,23) BETWEEN ? AND ? "+
                                            "AND (SELECT H0.VisResCode FROM ORSC H0 WHERE H0.VisResCode=T5.ITEMCODE AND T5.ItemType=290) LIKE ?");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            ps.setString(3, "%"+cecos+"%");
            rs = ps.executeQuery();
            while(rs.next()){
                us= new Usuario();
                APS_OIGE = new APS_OIGE();
                OWOR = new OWOR();
                OWOR.setDocnum(rs.getInt(1));
                OWOR.setItemcode(rs.getString(2));
                OWOR.setItemname(rs.getString(3));
                OWOR.setStatus(rs.getString(4));
                OWOR.setCantpln(rs.getString(5));
                OWOR.setCantcmp(rs.getString(6));
                OWOR.setCantrjc(rs.getString(7));
                OWOR.setStartdate(rs.getString(8));
                OWOR.setDuedate(rs.getString(9));
                us.setUsuario(rs.getString(10));
                APS_OIGE.setActividad(rs.getString(11));
                APS_OIGE.setDescActividad(rs.getString(12));
                APS_OIGE.setNomEmp(rs.getString(13));
                APS_OIGE.setInsumo(rs.getString(14));
                APS_OIGE.setInicio(rs.getString(15));
                APS_OIGE.setFin(rs.getString(16));
                APS_OIGE.setTotalTiempo(rs.getString(17));
                APS_OIGE.setTotalUnidades(rs.getInt(18));
                APS_OIGE.setTotalUnidadesConformes(rs.getInt(19));
                APS_OIGE.setTotalUnidadesNoConformes(rs.getInt(20));
                APS_OIGE.setTotalTiempoMuerto(rs.getString(21));
                APS_OIGE.setCodeActProd(rs.getString(22));
                APS_OIGE.setDescActividadProd(rs.getString(23));
                us.setPass(rs.getString(24));
                APS_OIGE.setAPS_OWOR(OWOR); 
                APS_OIGE.setUsuario(us);
                ar.add(APS_OIGE);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCecos(): " +e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return quitarDuplicados(ar);
    }
    
    public ArrayList<PagoEmp> mostrarReporteExcelPagoEmp(String fecha1, String fecha2, String codEmp) throws ClassNotFoundException, SQLException {
        ArrayList<PagoEmp> ar = new ArrayList<PagoEmp>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..[SP_PAGO_EMPLEADO] " +
                                            "   @codeEmp=? " +
                                            "  ,@FECHA1=?" +
                                            "  ,@FECHA2=?");
            ps.setString(1, codEmp);
            ps.setString(2, fecha1);
            ps.setString(3, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                pago = new PagoEmp();
                pago.setNumOrden(rs.getString(2));
                pago.setItemCode(rs.getString(4));
                pago.setItemName(rs.getString(5));
                pago.setFecha(rs.getString(6));
                pago.setUnidadesConformes(rs.getString(7));
                pago.setMetaDia(rs.getString(10));
                pago.setFactorMeta(rs.getString(11));
                pago.setFactorBajoMeta(rs.getString(12));
                pago.setFactorSobreMeta(rs.getString(13));
                pago.setUniMeta(rs.getString(14));
                pago.setUniBajoMeta(rs.getString(15));
                pago.setUniSobreMeta(rs.getString(16));
                pago.setPagoMeta(rs.getString(17));
                pago.setPagoBajoMeta(rs.getString(18));
                pago.setPagoSobreMeta(rs.getString(19));
                pago.setPagoDia(rs.getString(20));
                ar.add(pago);
                
            }
        } catch (Exception e) {
             System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelPagoEmp(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<EficienciaEmp> mostrarReporteExcelEficienciaEmp(String fecha1, String fecha2, String codEmp) throws ClassNotFoundException, SQLException {
        ArrayList<EficienciaEmp> ar = new ArrayList<EficienciaEmp>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..[SP_EFICIENCIA_EMP] " +
                                            "  @fecha1=?" +
                                            " ,@fecha2=? "+
                                            " ,@codeEmp=? ");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            ps.setString(3, codEmp);
            rs = ps.executeQuery();
            while(rs.next()){
                eficienciaEmp = new EficienciaEmp();
                eficienciaEmp.setNomEmp(rs.getString(1));
                eficienciaEmp.setDocNum(rs.getString(2));
                eficienciaEmp.setItemCode(rs.getString(3));
                eficienciaEmp.setItemName(rs.getString(4));
                eficienciaEmp.setActividad(rs.getString(5));
                eficienciaEmp.setInicio(rs.getString(6));
                eficienciaEmp.setFin(rs.getString(7));
                eficienciaEmp.setDescActividad(rs.getString(8));
                eficienciaEmp.setTotalTiempo(rs.getString(9));
                eficienciaEmp.setUniTotalesConformes(rs.getString(10));
                eficienciaEmp.setEstandarH(rs.getString(11));
                eficienciaEmp.setRitmoReal(rs.getString(12));
                eficienciaEmp.setEficiencia(rs.getString(13));
                eficienciaEmp.setPorcentajeHoras(rs.getString(14));
                eficienciaEmp.setEficienciaPonderada(rs.getString(15));
                ar.add(eficienciaEmp);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelEficienciaEmp(): "+e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<APS_OIGE> mostrarHistoricoArticulos(String itemCode) throws ClassNotFoundException, SQLException {
        ArrayList<APS_OIGE> ar = new ArrayList<APS_OIGE>();
        try{
             System.out.println(itemCode);
            ps=super.con().prepareStatement("SELECT  " +
                                            "	T0.docNum, " +
                                            "	T0.itemCode, " +
                                            "	T0.ITEMNAME, " +
                                            "	T0.nomEmp, " +
                                            "	T0.actividad, " +
                                            "	T0.descActividad, " +
                                            "	CONVERT(VARCHAR(10),T0.inicio,105)+' '+CONVERT(VARCHAR(8),T0.inicio,24) inicio, " +
                                            "	CONVERT(VARCHAR(10),T0.fin,105)+' '+CONVERT(VARCHAR(8),T0.fin,24) fin, " +
                                            "	T0.totalTiempo, " +
                                            "	T0.totalTiempoMuerto, " +
                                            "	T0.UnidadesConformes, " +
                                            "	T0.ritmo_real, " +
                                            "	T0.EstandarH " +
                                            "FROM SDO_ALSASA..VW_HISTORICO_ARTICULOS_APP T0 WHERE itemCode=?");
            ps.setString(1, itemCode);
            rs = ps.executeQuery();
            while(rs.next()){
                APS_OIGE aps_oige = new APS_OIGE();
                OWOR aps_owor = new OWOR();
                aps_owor.setDocnum(rs.getInt(1));
                aps_owor.setItemcode(rs.getString(2));
                aps_owor.setItemname(rs.getString(3));
                aps_oige.setNomEmp(rs.getString(4));
                aps_oige.setActividad(rs.getString(5));
                aps_oige.setDescActividad(rs.getString(6));
                aps_oige.setInicio(rs.getString(7));
                aps_oige.setFin(rs.getString(8));
                aps_oige.setTotalTiempo(rs.getString(9));
                aps_oige.setTotalTiempoMuerto(rs.getString(10));
                aps_oige.setTotalUnidadesConformes(rs.getInt(11));
                aps_oige.setEstado(rs.getInt(12));
                aps_oige.setDescActividadProd(rs.getString(13));
                aps_oige.setAPS_OWOR(aps_owor);
                ar.add(aps_oige);
                
            }
        } catch (Exception e) {
             System.out.println("modelo.DaoReportesExcel.mostrarHistoricoArticulos(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT  " +
                                            "	T0.[DOCNUM], " +
                                            "	T0.[ITEMCODE_ORDEN], " +
                                            "	T0.[ITEMNAME_ORDEN], " +
                                            "	T0.[PLNQTY_ORDEN], " +
                                            "	T0.[CMPTQTY_ORDEN], " +
                                            "	T0.[RJCTQTY_ORDEN], " +
                                            "	T0.[LINENUM], " +
                                            "	T0.[TIPO], " +
                                            "	T0.[ITEMCODE], " +
                                            "	T0.[ITEMNAME], " +
                                            "	T0.[PLNQTY], " +
                                            "	T0.[ISSUEQTY], " +
                                            "	T0.[VARIACION], " +
                                            "	T0.[EFICIENCIA], " +
                                            "	T0.[COSTO_PRY], " +
                                            "	T0.[COSTO_UNI], " +
                                            "	T0.[TOTAL_VARIACION_COSTO], " +
                                            "	T0.[VARIACION_UNI_COSTO], " +
                                            "	T0.[%_COSTO], " +
                                            "	T0.[TOTAL_VARIACION_CONSUMO], " +
                                            "	T0.[VARIACION_UNI_CONSUMO], " +
                                            "	T0.[%_CONSUMO], " +
                                            "	T0.[COSTO_TOTAL_PRY], " +
                                            "	T0.[COSTO_TOTAL_UNI], " +
                                            "	T0.[TOTAL_VARIACION], " +
                                            "	T0.[VARIACION_UNI], " +
                                            "	T0.[%_VARICACION_LINEA], " +
                                            "	T0.[%] " +
                                            "FROM SDO_ALSASA..VW_ANALISIS_COSTOS_OPS T0 " +
                                            " WHERE T0.DOCNUM=? ORDER BY T0.[LINENUM] ASC ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setPlnQty_Orden(rs.getString(4));
                costo_OP.setCmptQty_Orden(rs.getString(5));
                costo_OP.setRjctQty_Orden(rs.getString(6));
                costo_OP.setLineNum(rs.getString(7));
                costo_OP.setTipo(rs.getString(8));
                costo_OP.setItemCode(rs.getString(9));
                costo_OP.setItemName(rs.getString(10));
                costo_OP.setPlnQty(rs.getString(11));
                costo_OP.setIssueQty(rs.getString(12));
                costo_OP.setVariacion(rs.getString(13));
                costo_OP.setEficiencia(rs.getString(14));
                costo_OP.setCosto_Pry(rs.getString(15));
                costo_OP.setCosto_Uni(rs.getString(16));
                costo_OP.setTotal_Variacion_Costo(rs.getString(17));
                costo_OP.setVariacion_Uni_Costo(rs.getString(18));
                costo_OP.setPorCiento_Costo(rs.getString(19));
                costo_OP.setTotal_Variacion_Consumo(rs.getString(20));
                costo_OP.setVariacion_Uni_Consumo(rs.getString(21));
                costo_OP.setPorCiento_Consumo(rs.getString(22));
                costo_OP.setCosto_Total_Pry(rs.getString(23));
                costo_OP.setCosto_Total_Uni(rs.getString(24));
                costo_OP.setTotal_Variacion(rs.getString(25));
                costo_OP.setVariacion_Uni(rs.getString(26));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(27));
                costo_OP.setPorCiento(rs.getString(28));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }    
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP_TOTAL(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT "+
                                            "T0.[DOCNUM], " +
                                            "T0.[ITEMCODE_ORDEN], " +
                                            "T0.[ITEMNAME_ORDEN], " +
                                            "T0.[STARDATE], " +
                                            "T0.[DUEDATE], " +
                                            "T0.[PLNQTY_ORDEN], " +
                                            "T0.[CMPTQTY_ORDEN], " +
                                            "T0.[RJCTQTY_ORDEN], " +
                                            "T0.[EFICIENCIA], " +
                                            "T0.[COSTO_UNI_PLANIFICADO], " +
                                            "T0.[COSTO_UNI_REAL], " +
                                            "T0.[VARIACION_COSTO], " +
                                            "T0.[%_VARIACION], " +
                                            "T0.[COSTO_PRY_PPTO], " +
                                            "T0.[VARIACION_PRY], " +
                                            "T0.[%_VARIACION_PRY] "+
                                            "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS T0 WHERE T0.DOCNUM=? ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setStartDate(rs.getString(4));
                costo_OP.setDueDate(rs.getString(5));
                costo_OP.setPlnQty_Orden(rs.getString(6));
                costo_OP.setCmptQty_Orden(rs.getString(7));
                costo_OP.setRjctQty_Orden(rs.getString(8));
                costo_OP.setEficiencia(rs.getString(9));
                costo_OP.setCosto_Pry(rs.getString(10));
                costo_OP.setCosto_Uni(rs.getString(11));
                costo_OP.setTotal_Variacion_Costo(rs.getString(12));
                costo_OP.setPorCiento_Costo(rs.getString(13));
                costo_OP.setCosto_Total_Pry(rs.getString(14));
                costo_OP.setVariacion(rs.getString(15));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(16));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP_TOTAL(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP_Suma(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT   " +
                                            "    T0.[DOCNUM],  " +
                                            "    T0.[ITEMCODE_ORDEN],  " +
                                            "    T0.[ITEMNAME_ORDEN],  " +
                                            "    T0.[PLNQTY_ORDEN],  " +
                                            "    T0.[CMPTQTY_ORDEN],  " +
                                            "    T0.[RJCTQTY_ORDEN], " +
                                            "    SUM(T0.[TOTAL_VARIACION_COSTO]) [TOTAL_VARIACION_COSTO],  " +
                                            "    SUM(T0.[VARIACION_UNI_COSTO]) [VARIACION_UNI_COSTO],  " +
                                            "    CASE WHEN SUM(T0.[COSTO_TOTAL_PRY])=0 THEN 0 ELSE CAST((SUM(T0.[TOTAL_VARIACION_COSTO])/SUM(T0.[COSTO_TOTAL_PRY]))*100 AS NUMERIC(19,2)) END [%_COSTO],  " +
                                            "    SUM(T0.[TOTAL_VARIACION_CONSUMO]) [TOTAL_VARIACION_CONSUMO],  " +
                                            "    SUM(T0.[VARIACION_UNI_CONSUMO]) [VARIACION_UNI_CONSUMO],  " +
                                            "    CASE WHEN SUM(T0.[COSTO_TOTAL_PRY])=0 THEN 0 ELSE CAST((SUM(T0.[TOTAL_VARIACION_CONSUMO])/SUM(T0.[COSTO_TOTAL_PRY]))*100 AS NUMERIC(19,2)) END [%_CONSUMO],  " +
                                            "    SUM(T0.[COSTO_TOTAL_PRY]) [COSTO_TOTAL_PRY],  " +
                                            "    SUM(T0.[COSTO_TOTAL_UNI]) [COSTO_TOTAL_UNI],  " +
                                            "    SUM(T0.[TOTAL_VARIACION]) [TOTAL_VARIACION],  " +
                                            "    SUM(T0.[VARIACION_UNI]) [VARIACION_UNI],  " +
                                            "    CASE WHEN SUM(T0.[COSTO_TOTAL_PRY])=0 THEN 0 ELSE CAST((SUM(T0.[TOTAL_VARIACION])/SUM(T0.[COSTO_TOTAL_PRY]))*100 AS NUMERIC(19,2)) END [%_VARICACION_LINEA] " +
                                            "FROM SDO_ALSASA..VW_ANALISIS_COSTOS_OPS T0  " +
                                            "WHERE T0.DOCNUM=?  " +
                                            "GROUP BY  " +
                                            "	T0.[DOCNUM],  " +
                                            "    T0.[ITEMCODE_ORDEN],  " +
                                            "    T0.[ITEMNAME_ORDEN],  " +
                                            "    T0.[PLNQTY_ORDEN],  " +
                                            "    T0.[CMPTQTY_ORDEN],  " +
                                            "    T0.[RJCTQTY_ORDEN] ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setPlnQty_Orden(rs.getString(4));
                costo_OP.setCmptQty_Orden(rs.getString(5));
                costo_OP.setRjctQty_Orden(rs.getString(6));
                costo_OP.setTotal_Variacion_Costo(rs.getString(7));
                costo_OP.setVariacion_Uni_Costo(rs.getString(8));
                costo_OP.setPorCiento_Costo(rs.getString(9));
                costo_OP.setTotal_Variacion_Consumo(rs.getString(10));
                costo_OP.setVariacion_Uni_Consumo(rs.getString(11));
                costo_OP.setPorCiento_Consumo(rs.getString(12));
                costo_OP.setCosto_Total_Pry(rs.getString(13));
                costo_OP.setCosto_Total_Uni(rs.getString(14));
                costo_OP.setTotal_Variacion(rs.getString(15));
                costo_OP.setVariacion_Uni(rs.getString(16));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(17));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP_V2(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT  " +
                                            "	T0.[DOCNUM], " +
                                            "	T0.[ITEMCODE_ORDEN], " +
                                            "	T0.[ITEMNAME_ORDEN], " +
                                            "	T0.[PLNQTY_ORDEN], " +
                                            "	T0.[CMPTQTY_ORDEN], " +
                                            "	T0.[RJCTQTY_ORDEN], " +
                                            "	T0.[LINENUM], " +
                                            "	T0.[TIPO], " +
                                            "	T0.[ITEMCODE], " +
                                            "	T0.[ITEMNAME], " +
                                            "	T0.[PLNQTY], " +
                                            "	T0.[ISSUEQTY], " +
                                            "	T0.[VARIACION], " +
                                            "	T0.[EFICIENCIA], " +
                                            "	T0.[COSTO_PRY], " +
                                            "	T0.[COSTO_UNI], " +
                                            "	T0.[TOTAL_VARIACION_COSTO], " +
                                            "	T0.[VARIACION_UNI_COSTO], " +
                                            "	T0.[%_COSTO], " +
                                            "	T0.[TOTAL_VARIACION_CONSUMO], " +
                                            "	T0.[VARIACION_UNI_CONSUMO], " +
                                            "	T0.[%_CONSUMO], " +
                                            "	T0.[COSTO_TOTAL_PRY], " +
                                            "	T0.[COSTO_TOTAL_UNI], " +
                                            "	T0.[TOTAL_VARIACION], " +
                                            "	T0.[VARIACION_UNI], " +
                                            "	T0.[%_VARICACION_LINEA], " +
                                            "	T0.[%], " +
                                            "   T0.[TIPO_LISTA] " +
                                            "FROM SDO_ALSASA..VW_ANALISIS_COSTOS_OPS_V2 T0 " +
                                            " WHERE T0.DOCNUM=? ORDER BY T0.[LINENUM] ASC  ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setPlnQty_Orden(rs.getString(4));
                costo_OP.setCmptQty_Orden(rs.getString(5));
                costo_OP.setRjctQty_Orden(rs.getString(6));
                costo_OP.setLineNum(rs.getString(7));
                costo_OP.setTipo(rs.getString(8));
                costo_OP.setItemCode(rs.getString(9));
                costo_OP.setItemName(rs.getString(10));
                costo_OP.setPlnQty(rs.getString(11));
                costo_OP.setIssueQty(rs.getString(12));
                costo_OP.setVariacion(rs.getString(13));
                costo_OP.setEficiencia(rs.getString(14));
                costo_OP.setCosto_Pry(rs.getString(15));
                costo_OP.setCosto_Uni(rs.getString(16));
                costo_OP.setTotal_Variacion_Costo(rs.getString(17));
                costo_OP.setVariacion_Uni_Costo(rs.getString(18));
                costo_OP.setPorCiento_Costo(rs.getString(19));
                costo_OP.setTotal_Variacion_Consumo(rs.getString(20));
                costo_OP.setVariacion_Uni_Consumo(rs.getString(21));
                costo_OP.setPorCiento_Consumo(rs.getString(22));
                costo_OP.setCosto_Total_Pry(rs.getString(23));
                costo_OP.setCosto_Total_Uni(rs.getString(24));
                costo_OP.setTotal_Variacion(rs.getString(25));
                costo_OP.setVariacion_Uni(rs.getString(26));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(27));
                costo_OP.setPorCiento(rs.getString(28));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP_V2(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }  
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP2(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT  " +
                                            "	T0.[DOCNUM], " +
                                            "	T0.[ITEMCODE_ORDEN], " +
                                            "	T0.[ITEMNAME_ORDEN], " +
                                            "	T0.[PLNQTY_ORDEN], " +
                                            "	T0.[CMPTQTY_ORDEN], " +
                                            "	T0.[RJCTQTY_ORDEN], " +
                                            "	T0.[LINENUM], " +
                                            "	T0.[TIPO], " +
                                            "	T0.[ITEMCODE], " +
                                            "	T0.[ITEMNAME], " +
                                            "	T0.[PLNQTY], " +
                                            "	T0.[ISSUEQTY], " +
                                            "	T0.[VARIACION], " +
                                            "	T0.[EFICIENCIA], " +
                                            "	T0.[COSTO_PRY], " +
                                            "	T0.[COSTO_UNI], " +
                                            "	T0.[TOTAL_VARIACION_COSTO], " +
                                            "	T0.[VARIACION_UNI_COSTO], " +
                                            "	T0.[%_COSTO], " +
                                            "	T0.[TOTAL_VARIACION_CONSUMO], " +
                                            "	T0.[VARIACION_UNI_CONSUMO], " +
                                            "	T0.[%_CONSUMO], " +
                                            "	T0.[COSTO_TOTAL_PRY], " +
                                            "	T0.[COSTO_TOTAL_UNI], " +
                                            "	T0.[TOTAL_VARIACION], " +
                                            "	T0.[VARIACION_UNI], " +
                                            "	T0.[%_VARICACION_LINEA], " +
                                            "	T0.[%], " +
                                            "   T0.[TIPO_LISTA] " +
                                            "FROM SDO_ALSASA..VW_ANALISIS_COSTOS_OPS_V2 T0 " +
                                            " WHERE T0.DOCNUM=? ORDER BY T0.[LINENUM] ASC ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setPlnQty_Orden(rs.getString(4));
                costo_OP.setCmptQty_Orden(rs.getString(5));
                costo_OP.setRjctQty_Orden(rs.getString(6));
                costo_OP.setLineNum(rs.getString(7));
                costo_OP.setTipo(rs.getString(8));
                costo_OP.setItemCode(rs.getString(9));
                costo_OP.setItemName(rs.getString(10));
                costo_OP.setPlnQty(rs.getString(11));
                costo_OP.setIssueQty(rs.getString(12));
                costo_OP.setVariacion(rs.getString(13));
                costo_OP.setEficiencia(rs.getString(14));
                costo_OP.setCosto_Pry(rs.getString(15));
                costo_OP.setCosto_Uni(rs.getString(16));
                costo_OP.setTotal_Variacion_Costo(rs.getString(17));
                costo_OP.setVariacion_Uni_Costo(rs.getString(18));
                costo_OP.setPorCiento_Costo(rs.getString(19));
                costo_OP.setTotal_Variacion_Consumo(rs.getString(20));
                costo_OP.setVariacion_Uni_Consumo(rs.getString(21));
                costo_OP.setPorCiento_Consumo(rs.getString(22));
                costo_OP.setCosto_Total_Pry(rs.getString(23));
                costo_OP.setCosto_Total_Uni(rs.getString(24));
                costo_OP.setTotal_Variacion(rs.getString(25));
                costo_OP.setVariacion_Uni(rs.getString(26));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(27));
                costo_OP.setPorCiento(rs.getString(28));
                costo_OP.setTipo_lista(rs.getString(29));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP2(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }  
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP_TOTAL_V2(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT "+
                                            "T0.[DOCNUM], " +
                                            "T0.[ITEMCODE_ORDEN], " +
                                            "T0.[ITEMNAME_ORDEN], " +
                                            "T0.[STARDATE], " +
                                            "T0.[DUEDATE], " +
                                            "T0.[PLNQTY_ORDEN], " +
                                            "T0.[CMPTQTY_ORDEN], " +
                                            "T0.[RJCTQTY_ORDEN], " +
                                            "T0.[EFICIENCIA], " +
                                            "T0.[COSTO_UNI_PLANIFICADO], " +
                                            "T0.[COSTO_UNI_REAL], " +
                                            "T0.[VARIACION_COSTO], " +
                                            "T0.[%_VARIACION], " +
                                            "T0.[COSTO_PRY_PPTO], " +
                                            "T0.[VARIACION_PRY], " +
                                            "T0.[%_VARIACION_PRY], " +
                                            "T0.[COSTO_TOTAL_PLN], " +
                                            "T0.[COSTO_TOTAL_PPTO], " +
                                            "T0.[COSTO_TOTAL_CONSUMIDO], " +
                                            "T0.[HORAS_PLANIFICADAS], " +
                                            "T0.[HORAS_PRESUPUESTADAS], " +
                                            "T0.[HORAS_CONSUMIDAS] " +
                                            "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS_V2 T0 " +
                                            "WHERE T0.DOCNUM=? ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setStartDate(rs.getString(4));
                costo_OP.setDueDate(rs.getString(5));
                costo_OP.setPlnQty_Orden(rs.getString(6));
                costo_OP.setCmptQty_Orden(rs.getString(7));
                costo_OP.setRjctQty_Orden(rs.getString(8));
                costo_OP.setEficiencia(rs.getString(9));
                costo_OP.setCosto_Pry(rs.getString(10));
                costo_OP.setCosto_Uni(rs.getString(11));
                costo_OP.setTotal_Variacion_Costo(rs.getString(12));
                costo_OP.setPorCiento_Costo(rs.getString(13));
                costo_OP.setCosto_Total_Pry(rs.getString(14));
                costo_OP.setVariacion(rs.getString(15));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(16));
                costo_OP.setTotal_Costo_Planificado(rs.getString(17));
                costo_OP.setTotal_Costo_Presupuestado(rs.getString(18));
                costo_OP.setTotal_Costo_Consumido(rs.getString(19));
                costo_OP.setHoras_Planificadas(rs.getString(20));
                costo_OP.setHoras_Presupuestadas(rs.getString(21));
                costo_OP.setHoras_Consumidas(rs.getString(22));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP_TOTAL_V2(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_OP_Suma_V2(int docNum) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT   " +
                                            "    T0.[DOCNUM],  " +
                                            "    T0.[ITEMCODE_ORDEN],  " +
                                            "    T0.[ITEMNAME_ORDEN],  " +
                                            "    T0.[PLNQTY_ORDEN],  " +
                                            "    T0.[CMPTQTY_ORDEN],  " +
                                            "    T0.[RJCTQTY_ORDEN], " +
                                            "    SUM(T0.[TOTAL_VARIACION_COSTO]) [TOTAL_VARIACION_COSTO],  " +
                                            "    SUM(T0.[VARIACION_UNI_COSTO]) [VARIACION_UNI_COSTO],  " +
                                            "    CASE WHEN SUM(T0.[COSTO_TOTAL_PRY])=0 THEN 0 ELSE CAST((SUM(T0.[TOTAL_VARIACION_COSTO])/SUM(T0.[COSTO_TOTAL_PRY]))*100 AS NUMERIC(19,2)) END [%_COSTO],  " +
                                            "    SUM(T0.[TOTAL_VARIACION_CONSUMO]) [TOTAL_VARIACION_CONSUMO],  " +
                                            "    SUM(T0.[VARIACION_UNI_CONSUMO]) [VARIACION_UNI_CONSUMO],  " +
                                            "    CASE WHEN SUM(T0.[COSTO_TOTAL_PRY])=0 THEN 0 ELSE CAST((SUM(T0.[TOTAL_VARIACION_CONSUMO])/SUM(T0.[COSTO_TOTAL_PRY]))*100 AS NUMERIC(19,2)) END [%_CONSUMO],  " +
                                            "    SUM(T0.[COSTO_TOTAL_PRY]) [COSTO_TOTAL_PRY],  " +
                                            "    SUM(T0.[COSTO_TOTAL_UNI]) [COSTO_TOTAL_UNI],  " +
                                            "    SUM(T0.[TOTAL_VARIACION]) [TOTAL_VARIACION],  " +
                                            "    SUM(T0.[VARIACION_UNI]) [VARIACION_UNI],  " +
                                            "    CASE WHEN SUM(T0.[COSTO_TOTAL_PRY])=0 THEN 0 ELSE CAST((SUM(T0.[TOTAL_VARIACION])/SUM(T0.[COSTO_TOTAL_PRY]))*100 AS NUMERIC(19,2)) END [%_VARICACION_LINEA] " +
                                            "FROM SDO_ALSASA..VW_ANALISIS_COSTOS_OPS_V2 T0  " +
                                            "WHERE T0.DOCNUM=?  " +
                                            "GROUP BY  " +
                                            "	T0.[DOCNUM],  " +
                                            "    T0.[ITEMCODE_ORDEN],  " +
                                            "    T0.[ITEMNAME_ORDEN],  " +
                                            "    T0.[PLNQTY_ORDEN],  " +
                                            "    T0.[CMPTQTY_ORDEN],  " +
                                            "    T0.[RJCTQTY_ORDEN] ");
            ps.setInt(1, docNum);
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setPlnQty_Orden(rs.getString(4));
                costo_OP.setCmptQty_Orden(rs.getString(5));
                costo_OP.setRjctQty_Orden(rs.getString(6));
                costo_OP.setTotal_Variacion_Costo(rs.getString(7));
                costo_OP.setVariacion_Uni_Costo(rs.getString(8));
                costo_OP.setPorCiento_Costo(rs.getString(9));
                costo_OP.setTotal_Variacion_Consumo(rs.getString(10));
                costo_OP.setVariacion_Uni_Consumo(rs.getString(11));
                costo_OP.setPorCiento_Consumo(rs.getString(12));
                costo_OP.setCosto_Total_Pry(rs.getString(13));
                costo_OP.setCosto_Total_Uni(rs.getString(14));
                costo_OP.setTotal_Variacion(rs.getString(15));
                costo_OP.setVariacion_Uni(rs.getString(16));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(17));
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_OP_Suma_V2(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }  
    
    public ArrayList<Costo_OP> mostrarReporteExcelCosto_Fecha_TOTAL_V2(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<Costo_OP> ar = new ArrayList<Costo_OP>();
        try{
            ps=super.con().prepareStatement("SELECT "+
                                            "T0.[DOCNUM], " +
                                            "T0.[ITEMCODE_ORDEN], " +
                                            "T0.[ITEMNAME_ORDEN], " +
                                            "T0.[STARDATE], " +
                                            "T0.[DUEDATE], " +
                                            "T0.[PLNQTY_ORDEN], " +
                                            "T0.[CMPTQTY_ORDEN], " +
                                            "T0.[RJCTQTY_ORDEN], " +
                                            "T0.[EFICIENCIA], " +
                                            "T0.[COSTO_UNI_PLANIFICADO], " +
                                            "T0.[COSTO_UNI_REAL], " +
                                            "T0.[VARIACION_COSTO], " +
                                            "T0.[%_VARIACION], " +
                                            "T0.[COSTO_PRY_PPTO], " +
                                            "T0.[VARIACION_PRY], " +
                                            "T0.[%_VARIACION_PRY], " +
                                            "T0.[COSTO_TOTAL_PLN], " +
                                            "T0.[COSTO_TOTAL_PPTO], " +
                                            "T0.[COSTO_TOTAL_CONSUMIDO], " +
                                            "T0.[HORAS_PLANIFICADAS], " +
                                            "T0.[HORAS_PRESUPUESTADAS], " +
                                            "T0.[HORAS_CONSUMIDAS], " +
                                            "CAST(T1.[CLOSEDATE] AS DATE) CLOSEDATE " +
                                            "FROM SDO_ALSASA..VW_COSTO_TOTAL_OPS_V2 T0  " +
                                            "JOIN OWOR T1 ON T0.docNum=T1.docnum " +
                                            "WHERE T1.CloseDate BETWEEN ? AND ? ");
            ps.setString(1, fecha1.replaceAll("-", ""));
            ps.setString(2, fecha2.replaceAll("-", ""));
            rs = ps.executeQuery();
            while(rs.next()){
                costo_OP = new Costo_OP();
                costo_OP.setDocnum(rs.getString(1));
                costo_OP.setItemCode_Orden(rs.getString(2));
                costo_OP.setItemname_Orden(rs.getString(3));
                costo_OP.setStartDate(rs.getString(4));
                costo_OP.setDueDate(rs.getString(5));
                costo_OP.setPlnQty_Orden(rs.getString(6));
                costo_OP.setCmptQty_Orden(rs.getString(7));
                costo_OP.setRjctQty_Orden(rs.getString(8));
                costo_OP.setEficiencia(rs.getString(9));
                costo_OP.setCosto_Pry(rs.getString(10));
                costo_OP.setCosto_Uni(rs.getString(11));
                costo_OP.setTotal_Variacion_Costo(rs.getString(12));
                costo_OP.setPorCiento_Costo(rs.getString(13));
                costo_OP.setCosto_Total_Pry(rs.getString(14));
                costo_OP.setVariacion(rs.getString(15));
                costo_OP.setPorCiento_Variacion_Total(rs.getString(16));
                costo_OP.setTotal_Costo_Planificado(rs.getString(17));
                costo_OP.setTotal_Costo_Presupuestado(rs.getString(18));
                costo_OP.setTotal_Costo_Consumido(rs.getString(19));
                costo_OP.setHoras_Planificadas(rs.getString(20));
                costo_OP.setHoras_Presupuestadas(rs.getString(21));
                costo_OP.setHoras_Consumidas(rs.getString(22));
                costo_OP.setCloseDate(rs.getString(23));
                
                ar.add(costo_OP);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporteExcelCosto_Fecha_TOTAL_V2(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }  
    
    public ArrayList<HorasTrabajo> mostrarReportehoras_Trabajo_Fecha(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<HorasTrabajo> ar = new ArrayList<HorasTrabajo>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..[SP_HORAS_TOTAL_SUMA] @FECHA1=?,@FECHA2=? ");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                hTrabajo = new HorasTrabajo();
                hTrabajo.setFecha(rs.getString(1));
                hTrabajo.setHorasPlanificadas(rs.getString(2));
                hTrabajo.setHorasDisponibles(rs.getString(3));
                hTrabajo.setHorasConsumidas(rs.getString(4));
                hTrabajo.setHorasEstandarCompletadas(rs.getString(5));
                hTrabajo.setHorasEstandarRechazadas(rs.getString(6));
                hTrabajo.setHorasEstandarTotal(rs.getString(7));
                hTrabajo.sethPropPD(rs.getString(8));
                hTrabajo.sethPropCP(rs.getString(9));
                hTrabajo.sethPropEP(rs.getString(10));
                hTrabajo.sethPropCD(rs.getString(11));
                hTrabajo.sethPropEC(rs.getString(12));
                hTrabajo.setCostoHEstandarTotal(rs.getString(13));
                hTrabajo.setCostoHPlanificadas(rs.getString(14));
                hTrabajo.setCostoHConsumidas(rs.getString(15));
                hTrabajo.setCostoHDisponibles(rs.getString(16));
                hTrabajo.setCostoHNoConsumidas(rs.getString(17));
                ar.add(hTrabajo);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReportehoras_Trabajo_Fecha(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<HorasTrabajo> mostrarReportehoras_Trabajo_Fecha_Total(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<HorasTrabajo> ar = new ArrayList<HorasTrabajo>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..[SP_HORAS_TRABAJO_TOTAL_SUMA] @FECHA1=?,@FECHA2=? ");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                hTrabajo = new HorasTrabajo();
                hTrabajo.setHorasPlanificadas(rs.getString(1));
                hTrabajo.setHorasDisponibles(rs.getString(2));
                hTrabajo.setHorasConsumidas(rs.getString(3));
                hTrabajo.setHorasEstandarCompletadas(rs.getString(4));
                hTrabajo.setHorasEstandarRechazadas(rs.getString(5));
                hTrabajo.setHorasEstandarTotal(rs.getString(6));
                hTrabajo.sethPropPD(rs.getString(7));
                hTrabajo.sethPropCP(rs.getString(8));
                hTrabajo.sethPropEP(rs.getString(9));
                hTrabajo.sethPropCD(rs.getString(10));
                hTrabajo.sethPropEC(rs.getString(11));
                hTrabajo.setCostoHEstandarTotal(rs.getString(12));
                hTrabajo.setCostoHPlanificadas(rs.getString(13));
                hTrabajo.setCostoHConsumidas(rs.getString(14));
                hTrabajo.setCostoHDisponibles(rs.getString(15));
                hTrabajo.setCostoHNoConsumidas(rs.getString(16));
                ar.add(hTrabajo);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReportehoras_Trabajo_Fecha_Total(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<HorasTrabajo> mostrarReportehoras_Planilla_Fecha(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<HorasTrabajo> ar = new ArrayList<HorasTrabajo>();
        try{
            ps=super.con().prepareStatement("EXEC SDO_ALSASA..[SP_HORAS_PLANILLA_FECHA] @FECHA1=?,@FECHA2=? ");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                hTrabajo = new HorasTrabajo();
                hTrabajo.setFecha(rs.getString(1));
                hTrabajo.setCECOS(rs.getString(2));
                hTrabajo.setHorasPlanificadas(rs.getString(3));
                hTrabajo.setHorasDisponiblesActivas(rs.getString(4));
                hTrabajo.setHorasDisponibles(rs.getString(5));
                hTrabajo.setHorasConsumidas(rs.getString(6));
                hTrabajo.setHorasConsumidasSMED(rs.getString(7));
                hTrabajo.setHorasEstandarTotal(rs.getString(8));
                hTrabajo.setCostoHEstandarTotal(rs.getString(9));
                hTrabajo.setCostoHEstandarTotal(rs.getString(9));
                hTrabajo.setCostoHPlanificadas(rs.getString(10));
                hTrabajo.setCostoHConsumidas(rs.getString(11));
                hTrabajo.setCostoHConsumidasSMED(rs.getString(12));
                hTrabajo.setCostoHDisponiblesActivas(rs.getString(13));
                hTrabajo.setCostoHDisponibles(rs.getString(14));
                hTrabajo.setCostoHNoConsumidas(rs.getString(15));
                hTrabajo.setCostoHNoConsumidasActivas(rs.getString(16));
                ar.add(hTrabajo);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReportehoras_Trabajo_Fecha(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<OPCierre> mostrarReporte_OpCierre(String fecha1, String fecha2) throws ClassNotFoundException, SQLException {
        ArrayList<OPCierre> ar = new ArrayList<OPCierre>();
        try{
            ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..VW_OPS_CERRADAS_ACTIVIDADES WHERE FECHA_CIERRE BETWEEN ? AND ?");
            ps.setString(1, fecha1);
            ps.setString(2, fecha2);
            rs = ps.executeQuery();
            while(rs.next()){
                oPCierre = new OPCierre();
                oPCierre.setDocNum(rs.getString(1));
                oPCierre.setFechaInicio(rs.getString(2));
                oPCierre.setFechaCierre(rs.getString(3));
                oPCierre.setCodArticulo(rs.getString(4));
                oPCierre.setNomArticulo(rs.getString(5));
                oPCierre.setCantPln(rs.getString(6));
                oPCierre.setCantCmp(rs.getString(7));
                oPCierre.setCantRjc(rs.getString(8));
                oPCierre.setActividad(rs.getString(9));
                oPCierre.setDescActividad(rs.getString(10));
                oPCierre.setHorasCosumidas(rs.getString(11));
                oPCierre.setHorasEstandar(rs.getString(12));
                ar.add(oPCierre);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.mostrarReporte_OpCierre(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<NART> MostrarNivelesCosteoArticulo() throws ClassNotFoundException, SQLException {
        ArrayList<NART> ar = new ArrayList<NART>();
        try{
            ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..NART");
            rs = ps.executeQuery();
            while(rs.next()){
                nart = new NART();
                nart.setNivel(rs.getString(1));
                nart.setItemcode(rs.getString(2));
                nart.setItemName(rs.getString(3));
                nart.setCantidad(rs.getString(4));
                nart.setMaterial(rs.getString(5));
                nart.setMaterialName(rs.getString(6));
                nart.setMaterialTipo(rs.getString(7));
                nart.setNuminbuy(rs.getString(8));
                nart.setPeso(rs.getString(9));
                nart.setCant_material(rs.getString(10));
                nart.setCant_requerimiento(rs.getString(11));
                nart.setCosto_calculado(rs.getString(12));
                nart.setCecos(rs.getString(13));
                nart.setItemCode_padre(rs.getString(14));
                ar.add(nart);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.MostrarNivelesCosteoArticulo(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<NCART> MostrarCosteoArticulo() throws ClassNotFoundException, SQLException {
        ArrayList<NCART> ar = new ArrayList<NCART>();
        try{
            ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..NCART");
            rs = ps.executeQuery();
            while(rs.next()){
                ncart = new NCART();
                ncart.setItemcode(rs.getString(1));
                ncart.setItemName(rs.getString(2));
                ncart.setCosto_calculado(rs.getString(3));
                ar.add(ncart);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.MostrarCosteoArticulo(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public ArrayList<NPRF> MostrarCosteoArticulosFabricados() throws ClassNotFoundException, SQLException {
        ArrayList<NPRF> ar = new ArrayList<NPRF>();
        try{
            ps=super.con().prepareStatement("SELECT * FROM SDO_ALSASA..NPRF");
            rs = ps.executeQuery();
            while(rs.next()){
                nprf = new NPRF();
                nprf.setItemcode(rs.getString(1));
                nprf.setItemName(rs.getString(2));
                nprf.setCosto_calculado(rs.getString(3));
                ar.add(nprf);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.MostrarCosteoArticuloFabricados(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return ar;
    }
    
    public String ejecutarCosteoArticulo(String itemCode, String cantidad) throws ClassNotFoundException, SQLException {
        String result = "";
        try{
            ps=super.con().prepareStatement("EXECUTE SDO_ALSASA..[SP_GENERAR_COSTO_ARTICULO] " +
                                            "   @ITEMCODE=? " +
                                            "  ,@CANT_REQ=? ");
                ps.setString(1,itemCode); 
                ps.setString(2,cantidad); 
            rs = ps.executeQuery();
            while(rs.next()){
                result=rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println("modelo.DaoReportesExcel.ejecutarCosteoArticulo(): " + e.getMessage());
        }finally{
            super.con().close();
            ps.close();
        }
        return result;
    }
    
    //METODO PARA QUITAR LAS ORDENES DUPLICADAS DEL ARREGLO AL REPETIR LA CONSULTA DE ORDENES POR AREA
    private ArrayList<APS_OIGE> quitarDuplicados(ArrayList<APS_OIGE> arr){
        ArrayList<APS_OIGE> arr2 = new ArrayList<APS_OIGE>();
        for (APS_OIGE element : arr) {
            if (!arr2.contains(element)) {
                arr2.add(element);
            }
        }
        return arr2;
    }
}
