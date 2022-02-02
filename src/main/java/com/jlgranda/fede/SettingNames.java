/*
 * Copyright (C) 2015 jlgranda
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package com.jlgranda.fede;

/**
 *
 * @author jlgranda
 */
public class SettingNames {

    public static String GENERAL_MODULE = "aaa-general"; //asegura siempre estar en el top de la lista
    public static String DEFAULT_INVOICES_GROUP_NAME = "fede.group.default";

    //Popups
    public static final String POPUP_SUBIR_FACTURA_ELECTRONICA = "subir_factura_electronica";
    public static final String POPUP_EDITAR_DOCUMENTO = "editar_documento";
    public static final String POPUP_DESCARGAR_FACTURA_ELECTRONICA = "descargar_factura_electronica";

    public static String POPUP_NUEVA_ETIQUETA = "popup_etiqueta";
    public static String POPUP_FORMULARIO_ORGANIZATION = "popup_organizacion";
    public static String POPUP_FORMULARIO_PRODUCTO = "popup_producto";
    public static String POPUP_FORMULARIO_FACTURA = "popup_factura";
    
    //Edición de clientes
    public static String POPUP_FORMULARIO_PROFILE = "popup_profile";
    public static String POPUP_FORMULARIO_PROVEEDOR_COMPRA = "popup_proveedor_compra";
    public static String POPUP_FORMULARIO_PROVEEDOR_GASTO = "popup_proveedor_compra";
    public static String POPUP_FORMULARIO_PROVEEDOR_INGRESO = "popup_proveedor_compra";
    public static String POPUP_FORMULARIO_PROVEEDOR = "popup_proveedor";
    
    //Edición de records
    public static String POPUP_FORMULARIO_RECORD = "popup_record";
    //Popup Libro Mayor
    public static String POPUP_FORMULARIO_GENERALLEDGER = "popup_general_ledger";
    public static String POPUP_FORMULARIO_GENERALLEDGER_RECORD = "popup_general_ledger_record";
    public static String POPUP_FORMULARIO_RECORDTEMPLATE_EDICION = "popup_recordtemplate";
    public static String POPUP_FORMULARIO_GENERALJOURNAL= "popup_general_journal";
    

    //XML
    public static final String TAG_FECHA_AUTORIZACION = "fede.xml.tag.numeroAutorizacion";

    public static final String TAG_NUMERO_AUTORIZACION = "fede.xml.tag.fechaAutorizacion";

    //Properties
    public static String DASHBOARD_RANGE = "fede.dashboard.range";
    public static String DASHBOARD__SUMMARY_RANGE = "fede.dashboard.summary.range";
    public static String MYLASTS_RANGE = "app.fede.mylasts.range";
    public static String POPUP_WIDTH = "fede.popup.width";
    public static String POPUP_HEIGHT = "fede.popup.height";
    public static String POPUP_LEFT = "fede.popup.left";
    public static String POPUP_TOP = "fede.popup.top";
    public static String POPUP_SMALL_WIDTH = "fede.popup.small.width";
    public static String POPUP_SMALL_HEIGHT = "fede.popup.small.height";
    //MYMETYPE
    public static String MYMETYPE_PDF = "application/pdf";
    public static String MYMETYPE_ZIP = "application/octet-stream";

    //Raíz de modulos
    public static String MODULE = "module-";
    public static String POPUP_FORMULARIO_CAMBIAR_CLAVE = "popup_cambiar_clave";
    public static String POPUP_SELECCIONAR_GRUPOS_USUARIO = "seleccionar_grupos_usuario";
    
    public static String INVOICE_NOTIFY_GAP = "fede.invoice.notify.gap";
    
    public static String PRODUCT_TOP_RANGE = "fede.sales.product.top.range";
    public static String GROUP_TOP_RANGE = "fede.sales.group.top.range";
    public static String ACCOUNT_TOP_RANGE = "fede.sales.account.top.range";
    public static String JOURNAL_TOP_RANGE = "fede.sales.journal.top.range";
    public static String JOURNAL_REPORT_DEFAULT_RANGE = "fede.report.journal.default.range";
    
    public static String DOCUMENTS_REPLY = "app.documents.task.reply";
    public static String DOCUMENTS_FORWARD = "app.documents.task.forward";
    public static String DOCUMENTS_END = "app.documents.task.finalize";

}
