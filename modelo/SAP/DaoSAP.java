package modelo.SAP;
import com.sap.smb.sbo.api.*;
import java.util.*;
import entidades.*;
import java.text.*;
import modelo.DaoAPS_OIGE3;

/**
 *
 * @author Desarrollo Alsasa
 */
public class DaoSAP extends ConexionSAP{
    
    QueryAPS query = new QueryAPS();
    DaoAPS_OIGE3 daoAPS_OIGE3 = new DaoAPS_OIGE3();
    
    public String addTiempos(int docnum){
        int cod_r=-1;
        int lineNum=-1;
        int docEntry=0;
        int validarLineaOrden=0;
        int contadorValidacionLineas=0;
        String retorno="";
        try{
         System.out.println(System.getProperty("java.library.path"));
            if (connect()==0) {//Conexion a sap
                ArrayList<OWOR> owor = new ArrayList<>();
                owor.addAll((Collection)query.listarOrdenAPS(docnum));//Se extraen las ordenes de producion de base de datis de sistema APS
                
                for (OWOR op : owor) { //Se recorre el arreglo
                    
                    Calendar fecha = new GregorianCalendar();
                    fecha.setTime(query.fechaFinalTiempos(op.getDocnum()));
                    ArrayList<APS_OIGE> oige = new ArrayList<APS_OIGE>();
                    oige.addAll((Collection) query.listarAPS_OIGE(op.getDocnum()));//Se extraen las asignaciones por orden de produccion
                    docEntry=query.listarDocEntryOrden(op.getDocnum());//se extrae el docEntry de cada OP
                    
                    IDocuments idoc = SBOCOMUtil.newDocuments(company, SBOCOMConstants.BoObjectTypes_Document_oInventoryGenExit);// Se llama el tipo de documento deseado
                    idoc.setDocDate(fecha.getTime());
                    idoc.setDocDueDate(fecha.getTime());
                    idoc.setComments("APP-APS TRABAJOS ASIGNADOS PARA OP: " + op.getDocnum());
                    idoc.setDocType(SBOCOMConstants.BoDocumentTypes_dDocument_Items);
                    idoc.getLines().setCurrentLine(0);// se extraeb las lineas del documento
                    
                    for (APS_OIGE obj : oige) {//se recorre el array de las asignaciones
                        if (obj.getActividad().contains("114MLINEA1")) {
                            lineNum=query.obtenerLineNum(op.getDocnum(), obj.getActividad());//se obtiene el numero de linea de la actividad en la orden
                            idoc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders); //Tipo de documento al que se hace referencia
                            idoc.getLines().setBaseEntry(docEntry); //Se manda a que OP se esta haciendo referencia
                            idoc.getLines().setBaseLine(lineNum); //Se manda la linea a la que se esta haciendo referencia
                            idoc.getLines().setQuantity(Double.parseDouble(obj.getTotalTiempo()));
                            idoc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                            idoc.getLines().getUserFields().getFields().item("U_HoraI").setValue(obj.getInicio());
                            idoc.getLines().getUserFields().getFields().item("U_HoraF").setValue(obj.getFin());
                            idoc.getLines().getUserFields().getFields().item("U_CodigoEmp").setValue(String.valueOf(obj.getCodeEmp()));
                            idoc.getLines().getUserFields().getFields().item("U_NomEmpleado").setValue(String.valueOf(obj.getNomEmp()));
//                            idoc.getLines().getUserFields().getFields().item("U_CodigoAct").setValue(obj.getCodeActProd());
//                            idoc.getLines().getUserFields().getFields().item("U_DetDescActividad").setValue(obj.getDescActividadProd());
                            idoc.getLines().getUserFields().getFields().item("U_UniConforme").setValue(obj.getTotalUnidadesConformes());
                            idoc.getLines().getUserFields().getFields().item("U_UniNoConforme").setValue(obj.getTotalUnidadesNoConformes());

                            idoc.getLines().add();
                            //----------
                            ArrayList<APS_OITT> oitt1 = new ArrayList<APS_OITT>();
                            oitt1.addAll((Collection)query.obtenerActividadEmpaque(op.getItemcode())); //Se extrae la actividad para la asignacion de tiempos a la maquina
                            for (APS_OITT aps_oitt1 : oitt1) {
                                lineNum=query.obtenerLineNum(op.getDocnum(), "114Fempacar");
                                if (lineNum!=-1) {
                                    idoc.getLines().setBaseEntry(docEntry); 
                                    idoc.getLines().setBaseLine(lineNum);
                                    idoc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders); 
                                    idoc.getLines().setQuantity(Double.parseDouble(obj.getTotalTiempo()));
                                    idoc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                                    idoc.getLines().getUserFields().getFields().item("U_HoraI").setValue(obj.getInicio());
                                    idoc.getLines().getUserFields().getFields().item("U_HoraF").setValue(obj.getFin());
                                    idoc.getLines().getUserFields().getFields().item("U_CodigoEmp").setValue(String.valueOf(obj.getCodeEmp()));
                                    idoc.getLines().getUserFields().getFields().item("U_NomEmpleado").setValue(String.valueOf(obj.getNomEmp()));
                                    idoc.getLines().add();
                                }
                            }
                        }else{
                            validarLineaOrden=0;
                            ArrayList<APS_OITT> oitt = new ArrayList<APS_OITT>();
                            oitt.addAll((Collection)query.obtenerActividadL(op.getItemcode(), obj.getDescActividad(), obj.getActividad()));//Se extrae la actividad para la asignacion de tiempos al empleado

                            for (APS_OITT aps_oitt : oitt) {
                                validarLineaOrden=query.validarLineaOrden(docnum, aps_oitt.getVisOrder(), obj.getActividad());
                                if (validarLineaOrden!=0) {
                                    contadorValidacionLineas++;
                                }

                                lineNum=query.obtenerLineNumT(op.getDocnum(), aps_oitt.getVisOrder());//se obtiene el numero de linea de la actividad en la orden
                                idoc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders); //Tipo de documento al que se hace referencia
                                idoc.getLines().setBaseEntry(docEntry); //Se manda a que OP se esta haciendo referencia
                                idoc.getLines().setBaseLine(lineNum); //Se manda la linea a la que se esta haciendo referencia
                                idoc.getLines().setQuantity(Double.parseDouble(obj.getTotalTiempo()));
                                idoc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                                idoc.getLines().getUserFields().getFields().item("U_HoraI").setValue(obj.getInicio());
                                idoc.getLines().getUserFields().getFields().item("U_HoraF").setValue(obj.getFin());
                                idoc.getLines().getUserFields().getFields().item("U_CodigoEmp").setValue(String.valueOf(obj.getCodeEmp()));
                                idoc.getLines().getUserFields().getFields().item("U_NomEmpleado").setValue(String.valueOf(obj.getNomEmp()));
//                                idoc.getLines().getUserFields().getFields().item("U_CodigoAct").setValue(obj.getCodeActProd());
//                                idoc.getLines().getUserFields().getFields().item("U_DetDescActividad").setValue(obj.getDescActividadProd());
                                idoc.getLines().getUserFields().getFields().item("U_UniConforme").setValue(obj.getTotalUnidadesConformes());
                                idoc.getLines().getUserFields().getFields().item("U_UniNoConforme").setValue(obj.getTotalUnidadesNoConformes());

                                idoc.getLines().add();

                                ArrayList<APS_OITT> oitt1 = new ArrayList<APS_OITT>();
                                oitt1.addAll((Collection)query.obtenerActividadM(op.getItemcode(), obj.getDescActividad())); //Se extrae la actividad para la asignacion de tiempos a la maquina
                                for (APS_OITT aps_oitt1 : oitt1) {
                                    lineNum=query.obtenerLineNumT(op.getDocnum(), aps_oitt1.getVisOrder());
                                    validarLineaOrden=query.validarLineaOrden(docnum, aps_oitt1.getVisOrder(), aps_oitt1.getActividad());
                                    if (validarLineaOrden!=0) {
                                        contadorValidacionLineas++;
                                    }
                                    if (lineNum!=-1) {
                                        idoc.getLines().setBaseEntry(docEntry); 
                                        idoc.getLines().setBaseLine(lineNum);
                                        idoc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders); 
                                        idoc.getLines().setQuantity(Double.parseDouble(obj.getTotalTiempo()));
                                        idoc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                                        idoc.getLines().getUserFields().getFields().item("U_HoraI").setValue(obj.getInicio());
                                        idoc.getLines().getUserFields().getFields().item("U_HoraF").setValue(obj.getFin());
                                        idoc.getLines().getUserFields().getFields().item("U_CodigoEmp").setValue(String.valueOf(obj.getCodeEmp()));
                                        idoc.getLines().getUserFields().getFields().item("U_NomEmpleado").setValue(String.valueOf(obj.getNomEmp()));
                                        idoc.getLines().add();
                                    }
                                } 
                            } 
                        }
                    }
                    
                    if (contadorValidacionLineas>0) {
                        retorno="LAS LINEAS DE LA ORDEN DE PRODUCCION NO COINCIDEN CON LAS ACTIVIDADES QUE DESEA CARGAR, POR FAVOR CONSULTAR A DEPARTAMENTO DE IT. ";
                    } else {
                        cod_r=idoc.add();//se crea el documento
                        if (cod_r==0) {//se verifica que el documento se creo correctamente
                            retorno=String.valueOf(cod_r);
                            query.actualizarEstadoCarga(op.getDocnum());
                            query.actualizarEstadoTiempos(op.getDocnum());
                        }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                            retorno=error();
                            query.eliminarActividadesProdAPS(op.getDocnum());
                        }
                    }
                    break;
                }
            }else{//Si ocurrio algun error en la conexion se manda el mensaje de error a la pantalla
                retorno=error();
            }
        }catch(Exception e){
            System.out.println("modelo.SAP.DaoSAP.addTiempos(): " + e.getMessage() + ", numOrden: " + docnum);
            retorno=e.getMessage();
        }finally{
            disconnect();
        }
        return retorno;
    }
    
    public String addMateriales(int docnum, String itemCode, String itemName, String cantMateriales, int id){
        int cod_r=-1;
        int lineNum=-1;
        int docEntry=0;
        String retorno="";
        try{
            if (connect()==0) {//Conexion a sap
                ArrayList<OWOR> owor = new ArrayList<OWOR>();
                owor.addAll((Collection)query.listarOrdenAPS(docnum));// se extraen las ordenes
                
                for (OWOR op : owor) {
                    Calendar fecha = new GregorianCalendar();
                    fecha.setTime(query.fechaFinal(op.getDocnum()));
                    String codes=itemCode;
                    String[] codeInsumo=codes.split(",");
                    String[] codeInsumos = new String[codeInsumo.length];

                    String desc=itemName;
                    String[] descInsumo=desc.split(",");
                    String[] descInsumos = new String[descInsumo.length];

                    String cant=cantMateriales;
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
                    
                    docEntry=query.listarDocEntryOrden(op.getDocnum());//se extrae el docEntry de cada OP
                    IDocuments idoc = SBOCOMUtil.newDocuments(company, SBOCOMConstants.BoObjectTypes_Document_oInventoryGenExit);// Se llama el tipo de documento deseado
                    idoc.setDocDate(fecha.getTime());
                    idoc.setDocDueDate(fecha.getTime());
                    idoc.setComments("APP-APS ENTREGA DE MATERIALES PARA OP: " + op.getDocnum());
                    idoc.setDocType(SBOCOMConstants.BoDocumentTypes_dDocument_Items);
                    idoc.getLines().setCurrentLine(0);// se extraeb las lineas del documento
                    
                    for (int i = 0; i < cantAsignadas.length; i++) {
                        if (Double.parseDouble(cantAsignadas[i])!=0) {
                            lineNum=query.obtenerLineNum(op.getDocnum(), codeInsumo[i]);//se obtiene el numero de linea de la actividad en la orden

                            idoc.getLines().setBaseEntry(docEntry); //Se manda a que OP se esta haciendo referencia
                            idoc.getLines().setBaseLine(lineNum); //Se manda la linea a la que se esta haciendo referencia
                            idoc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders); //Tipo de documento al que se hace referencia
                            idoc.getLines().setQuantity(Double.parseDouble(cantAsignadas[i]));
                            idoc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                            idoc.getLines().add();
                        }
                    }
                    
                    cod_r=idoc.add();//se crea el documento
                    if (cod_r==0) {//se verifica que el documento se creo correctamente
                        retorno=String.valueOf(cod_r);
                        query.actualizarEstadoMateriales(op.getDocnum());
                        if(id!=0)
                            query.actualizarEstadoENMT(id,Integer.parseInt(company.getNewObjectCode()));
                    }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                        retorno=error();
                        daoAPS_OIGE3.cancelarENMTOrden(String.valueOf(docnum));
                    }
                    break;
                }
            }else{//Si ocurrio algun error en la conexion se manda el mensaje de error a la pantalla
                retorno=error();
            }
        }catch(Exception e){
            System.out.println("modelo.SAP.DaoSAP.addMateriales(): " + e.getMessage() + ", numOrden: " + docnum);
            retorno=e.getMessage();
        }finally{
            disconnect();
        }
        return retorno;
    }
    
    public String addProduccion(int docnum, int uniConformes, int uniNoConformes){
        int cod_r=-1;
        int docEntry=0;
        double total=0;
        double conformes=uniConformes;
        double noConformes=uniNoConformes;
        String bodega="";
        String retorno="";
        double cantPln=0;
        double cantProducida=0;
        double cantCmpl=0;
        APS_IGN1 APS_IGN1= new APS_IGN1();
        try{
        System.out.println("controlador.DaoSAP.cargarProduccion(): "+uniConformes);
        
                System.out.println("modelo.SAP.DaoSAP.addProduccion(1): " + uniNoConformes);
            if (connect()==0) {//conexion a sap
                System.out.println("modelo.SAP.DaoSAP.addProduccion(1): " + uniConformes);
                
                ArrayList<OWOR> owor = new ArrayList<OWOR>();
                owor.addAll((Collection)query.listarOrdenAPS(docnum));// se extraen las ordenes
                
                if (uniConformes>0) {
                    for (OWOR op : owor) {
                        ArrayList<OWOR> cant = new ArrayList<OWOR>();
                        cant.addAll((Collection)query.comprobarProduccion(op.getDocnum()));
                        for (OWOR cantidad : cant) {
                            cantPln=Double.parseDouble(cantidad.getCantpln());
                            cantProducida=Double.parseDouble(cantidad.getCantcmp());
                            cantCmpl=Double.parseDouble(cantidad.getCantProcesos());
                        }
                        
                        if(cantProducida<cantPln){
                            Calendar fecha = new GregorianCalendar();
                            fecha.setTime(query.fechaFinal(op.getDocnum()));
                            ArrayList<APS_OITT> arr = new ArrayList<APS_OITT>();
                            arr.addAll((Collection)query.obtenerUltimaAct(op.getItemcode()));//Se obtiene cual es la ultima actividad del articulo
                            System.out.println("modelo.SAP.DaoSAP.addProduccion(2): " + uniConformes);
                            docEntry=query.listarDocEntryOrden(op.getDocnum());//docentry del la OP
                            bodega=query.obtenerBodega(op.getItemcode());//Num de bodega a la que se enviara el articulo

                            IDocuments idoc = SBOCOMUtil.newDocuments(company, SBOCOMConstants.BoObjectTypes_Document_oInventoryGenEntry); // Se llama el tipo de documento deseado
                            total=conformes+noConformes; // se suman las unidades conformes y no conformes

                            if((total+cantCmpl)<=Double.parseDouble(op.getCantpln())){//El total de unidades no puede exceder la cantidad planificada

                            System.out.println("modelo.SAP.DaoSAP.addProduccion(3): " + uniConformes);
                                idoc.setDocDate(fecha.getTime());
                                idoc.setDocDueDate(fecha.getTime());
                                idoc.setComments("APP-APS PRODUCCION COMPLETADA DE ORDEN: " + op.getDocnum());
                                idoc.setDocType(SBOCOMConstants.BoDocumentTypes_dDocument_Items);

                                idoc.getLines().add();
                                idoc.getLines().setCurrentLine(0);//Se extraen las lineas del documento
                                idoc.getLines().setBaseEntry(docEntry);//Se manda la op a la que se esta haciendo referencia
                                idoc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders);//se manda el tipo de documento al que se esta haciendo referencia
                                idoc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                                idoc.getLines().setQuantity(conformes); // se guarda la cantidad de unidades conformes
                                idoc.getLines().setTransactionType(SBOCOMConstants.BoTransactionTypeEnum_botrntComplete);//se manda l tipo de transaccion, en este caso son completadas
                                idoc.getLines().setWarehouseCode(bodega);//se manda la bodega en la que se enviara el articulo

                                cod_r=idoc.add();// se crea el documento

                                if (cod_r==0) {//se verifica que el documento se creo correctamente
                                    retorno=String.valueOf(cod_r);
                                    //ACTIALIZACION ESTADO
                                    query.actualizarEstadoProduccion(op.getDocnum());
                                    if(!op.getComments().equals("")){
                                        //SE GUARDA EL COMENTARIO EN SAP
                                        IProductionOrders orden = SBOCOMUtil.getProductionOrders(company, docEntry);
                                        orden.getUserFields().getFields().item("U_COMM_PROD").setValue(op.getComments());
                                        cod_r=orden.update();                
                                        if (cod_r==0) {//se verifica que el documento se actualizo correctamente
                                            retorno=String.valueOf(cod_r);
                                        }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                                            retorno=error();
                                        }
                                    }
                                }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                                    retorno=error();
                                }

                                cod_r = -1;

                                if (noConformes>0) {
                                    IDocuments doc = SBOCOMUtil.newDocuments(company, SBOCOMConstants.BoObjectTypes_Document_oInventoryGenEntry); // Se llama el tipo de documento deseado
                                    doc.setDocDate(fecha.getTime());
                                    doc.setDocDueDate(fecha.getTime());
                                    doc.setComments("APP-APS PRODUCCION RECHAZADA DE ORDEN: " + op.getDocnum());
                                    doc.setDocType(SBOCOMConstants.BoDocumentTypes_dDocument_Items);

                                    doc.getLines().add();
                                    doc.getLines().setCurrentLine(0);//Se extraen las lineas del documento
                                    doc.getLines().setBaseEntry(docEntry);//Se manda la op a la que se esta haciendo referencia
                                    doc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders);//se manda el tipo de documento al que se esta haciendo referencia
                                    doc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                                    doc.getLines().setQuantity(noConformes); // se guarda la cantidad de unidades conformes
                                    doc.getLines().setTransactionType(SBOCOMConstants.BoTransactionTypeEnum_botrntReject);//se manda l tipo de transaccion, en este caso son rechazadas
                                    doc.getLines().setWarehouseCode("B006");//se manda la bodega en la que se enviara el articulo

                                    cod_r=doc.add();// se crea el documento
                                    if (cod_r==0) {//se verifica que el documento se creo correctamente
                                        if(!op.getComments().equals("")){
                                            //SE GUARDA EL COMENTARIO EN SAP
                                            IProductionOrders orden = SBOCOMUtil.getProductionOrders(company, docEntry);
                                            orden.getUserFields().getFields().item("U_COMM_PROD").setValue(op.getComments());
                                            cod_r=orden.update();             

                                            if (cod_r==0) {//se verifica que el documento se actualizo correctamente
                                                retorno=String.valueOf(cod_r);
                                            }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                                                retorno=error();
                                            }
                                        }
                                    }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                                        retorno=error();
                                    }
                                } 
                                
                                //SE ACTUALIZA LA PRODUCCION
                                APS_IGN1.setItemCode(op.getItemcode());
                                APS_IGN1.setCantCompletada(uniConformes);
                                APS_IGN1.setCantRechazada(uniNoConformes);
                                APS_IGN1.setAPS_OWOR(op);
                                query.insertarCantOPs(APS_IGN1);
                            }else{
                                retorno="La producción no debe ser ser mayor a lo planificado. Cant ingresada:" + total +", Cant planificada " + Double.parseDouble(op.getCantpln());
                            }
                        }else{
                            retorno="YA SE GUARDÓ LA PRODUCCIÓN DE ESTA ORDEN";
                        }
                        break;
                    }
                }else if(uniNoConformes>0 && uniConformes==0){
                    for (OWOR op : owor) {
                        Calendar fecha = new GregorianCalendar();
                        fecha.setTime(query.fechaFinal(op.getDocnum()));
                        ArrayList<APS_OITT> arr = new ArrayList<APS_OITT>();
                        arr.addAll((Collection)query.obtenerUltimaAct(op.getItemcode()));//Se obtiene cual es la ultima actividad del articulo
                        total=conformes+noConformes; // se suman las unidades conformes y no conformes
                        docEntry=query.listarDocEntryOrden(op.getDocnum());//docentry del la OP
                        if(total<=Double.parseDouble(op.getCantpln())){//El total de unidades no puede exceder la cantidad planificada
                            if (noConformes>0) {
                                IDocuments doc = SBOCOMUtil.newDocuments(company, SBOCOMConstants.BoObjectTypes_Document_oInventoryGenEntry); // Se llama el tipo de documento deseado
                                doc.setDocDate(fecha.getTime());
                                doc.setDocDueDate(fecha.getTime());
                                doc.setComments("APP-APS PRODUCCION RECHAZADA DE ORDEN: " + op.getDocnum());
                                doc.setDocType(SBOCOMConstants.BoDocumentTypes_dDocument_Items);

                                doc.getLines().add();
                                doc.getLines().setCurrentLine(0);//Se extraen las lineas del documento
                                doc.getLines().setBaseEntry(docEntry);//Se manda la op a la que se esta haciendo referencia
                                doc.getLines().setBaseType(SBOCOMConstants.BoObjectTypes_oProductionOrders);//se manda el tipo de documento al que se esta haciendo referencia
                                doc.getLines().setShipDate(fecha.getTime()); //Se guarda la fecha de entrega
                                doc.getLines().setQuantity(noConformes); // se guarda la cantidad de unidades conformes
                                doc.getLines().setTransactionType(SBOCOMConstants.BoTransactionTypeEnum_botrntReject);//se manda l tipo de transaccion, en este caso son rechazadas
                                doc.getLines().setWarehouseCode("B006");//se manda la bodega en la que se enviara el articulo

                                cod_r=doc.add();// se crea el documento
                                if (cod_r==0) {//se verifica que el documento se creo correctamente
                                    retorno=String.valueOf(cod_r);
                                    
                                        if(!op.getComments().equals("")){
                                            //SE GUARDA EL COMENTARIO EN SAP
                                            IProductionOrders orden = SBOCOMUtil.getProductionOrders(company, docEntry);
                                            orden.getUserFields().getFields().item("U_COMM_PROD").setValue(op.getComments());
                                            cod_r=orden.update();                
                                            if (cod_r==0) {//se verifica que el documento se actualizo correctamente
                                                retorno=String.valueOf(cod_r);
                                            }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                                                retorno=error();
                                            }
                                        }
                                }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                                    retorno=error();
                                }
                                
                                //SE ACTUALIZA LA PRODUCCION
                                APS_IGN1.setItemCode(op.getItemcode());
                                APS_IGN1.setCantCompletada(uniConformes);
                                APS_IGN1.setCantRechazada(uniNoConformes);
                                APS_IGN1.setAPS_OWOR(op);
                                query.insertarCantOPs(APS_IGN1);
                            } 
                        }else{
                            retorno="La producción no debe ser ser mayor a lo planificado, Cant ingresada:" + total +", Cant planificada " + Double.parseDouble(op.getCantpln());
                        }
                        break;
                    }
                }else{
                    retorno="NO PUEDE GUARDAR CANTIDADES MENORES O IGUALES A CERO";
                }
            }else{//si ocurrio un error de conexion a sap se manda el mensaje de error
                retorno= error();
            }
        }catch(Exception e){
            System.out.println("modelo.SAP.DaoSAP.addProduccion(): " + e.getMessage() + ", numOrden: " + docnum);
            retorno=e.getMessage();
        }finally{
            disconnect();
        }
        return retorno;
    }
    
   public String updateStartDateOWOR(int numOrden, int anio, int mes, int dia, int idUsuario){
        String retorno="";
        int cod_r=-1;
        try{
            if (connect()==0) {
                int docEntry = query.listarDocEntryOrden(numOrden);
                Calendar NewDate= new GregorianCalendar();
                NewDate.set(anio, mes-1, dia);
                String mes1=mes<10?"0"+mes:""+mes;
                String dia1=dia<10?"0"+dia:""+dia;
                String nuevafecha=""+anio+"-"+mes1+"-"+dia1+"T00:00:00";
                String oldDate= query.startDate(numOrden).replaceAll(" ", "T");

                IProductionOrders orden = SBOCOMUtil.getProductionOrders(company, docEntry);
                orden.setStartDate(NewDate.getTime());
                cod_r=orden.update();                
                if (cod_r==0) {//se verifica que el documento se actualizo correctamente
                    retorno=String.valueOf(cod_r);
                    query.guardarLogFechas(numOrden, oldDate, nuevafecha, idUsuario);
                }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                    retorno=error();
                }
            }else{
                retorno=error();
            }
        }catch(Exception e){
            System.out.println("modelo.SAP.DaoSAP.updateStartDateOWOR(): " + e.getMessage() + ", numOrden: " + numOrden);
            retorno=e.getMessage();
        }finally{
            disconnect();
        }
        return retorno;
    }
    
    
    public int mostrarCompletadas(int docNum, String itemCode) {
        int completadas=0;
        try {
            completadas=query.obtenerUniConfomes(docNum, itemCode);
        } catch (Exception e) {
            System.out.println("modelo.SAP.DaoSAP.mostrarCompletadas(): " + e.getMessage());
        }
        return completadas;
    }
    
    public int mostrarRechazadas(int docNum, String itemCode) {
        int rechazadas=0;
        try {
            rechazadas=query.obtenerUniNoConfomes(docNum, itemCode);
        } catch (Exception e) {
            System.out.println("modelo.SAP.DaoSAP.mostrarRechazadas(): " + e.getMessage());
        }
        return rechazadas;
    } 
    
    public ArrayList<APS_OIGE3> mostrarMateriales(int docNum, String itemCode) {
        ArrayList<APS_OIGE3> ar = new ArrayList<APS_OIGE3>();
        try {
            ar.addAll((Collection)query.obtenerMateriales(docNum,itemCode));
        } catch (Exception e) {
            System.out.println("modelo.SAP.DaoSAP.mostrarMateriales(): " + e.getMessage());
        }
        return ar;
    }
    
    public ArrayList<APS_OIGE> cargarActividades(int docNum) {
        ArrayList<APS_OIGE> arr = new ArrayList<APS_OIGE>();
        try {
            arr.addAll((Collection)query.cargarActividades(docNum));
        } catch (Exception e) {
            System.out.println("modelo.SAP.DaoSAP.cargarActividades(): " + e.getMessage());
        }
        return arr;
    }
    
    public ArrayList<ActividadProd> listarActividadesProd(String code) {
        ArrayList<ActividadProd> arr = new ArrayList<ActividadProd>();
        try {
            arr.addAll((Collection)query.listarActividadesProd(code));
        } catch (Exception e) {
            System.out.println("modelo.SAP.DaoSAP.listarActividadesProd(): " + e.getMessage());
        }
        return arr;
    }
    
    
    public String addTransferencia(int id){
        int cod_r=-1;
        int lineNum=-1;
        int docEntry=0;
        String retorno="";
        try{
            if (connect()==0) {//Conexion a sap
                ArrayList<APS_OWTR> aps_owtr = new ArrayList<APS_OWTR>();
                aps_owtr.addAll((Collection)query.listarTransferenciasAPS(id));// se extraen las ordenes
                
                for (APS_OWTR owtr : aps_owtr) {
                    
                    ArrayList<APS_WTR1> aps_wtr1 = new ArrayList<APS_WTR1>();
                    aps_wtr1.addAll((Collection)query.obtenerLineasTransferenciasAPS(id));// se extraen las ordenes
                    
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-dd hh:mm:ss");
                    java.util.Date dateC = new java.util.Date();
                    Calendar fechaC = new GregorianCalendar();
                    dateC = sdf.parse(owtr.getDocDate() + " 00:00:00");
                    fechaC.setTime(dateC);
                    
                    IStockTransfer transfer = SBOCOMUtil.newStockTransfer(company);
                    transfer.setDocDate(fechaC.getTime());
                    transfer.setFromWarehouse(owtr.getFiller());
                    transfer.setToWarehouse(owtr.getToWhscode());
                    transfer.setSeries(Integer.parseInt(owtr.getSeries()));
                    transfer.setJournalMemo(owtr.getJrnlMemo());
                    transfer.setComments(owtr.getComments());
                    transfer.getLines().setCurrentLine(0);// se extraeb las lineas del documento

                    for (APS_WTR1 wtr1 : aps_wtr1) {
                        transfer.getLines().setItemCode(wtr1.getItemCode());
                        transfer.getLines().setFromWarehouseCode(wtr1.getFromWhsCod());
                        transfer.getLines().setWarehouseCode(wtr1.getWhsCode());
                        transfer.getLines().setQuantity(Double.parseDouble(wtr1.getQuantity()));
                        transfer.getLines().setWarehouseCode(wtr1.getWhsCode());
                        transfer.getLines().getUserFields().getFields().item("U_OrdenProduccion").setValue(wtr1.getOrdenProduccion());
                        transfer.getLines().add();
                    }
                    
                    cod_r=transfer.add();//se crea el documento
                    if (cod_r==0) {//se verifica que el documento se creo correctamente
                        retorno=String.valueOf(cod_r);
                        query.actualizarEstadoTransferencia(owtr.getIdAPS_OWTR(),Integer.parseInt(query.numTransferencia(Integer.parseInt(company.getNewObjectCode()))));
                    }else{//Si ocurrio algun error se manda el mensaje de error a la pantalla
                        retorno=error();
                    }
                    break;
                }
            }else{//Si ocurrio algun error en la conexion se manda el mensaje de error a la pantalla
                retorno=error();
            }
        }catch(Exception e){
            System.out.println("modelo.SAP.DaoSAP.addMateriales(): " + e.getMessage() );
            retorno=e.getMessage();
        }finally{
            disconnect();
        }
        return retorno;
    }
}