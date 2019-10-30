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
package com.jlgranda.fede.ejb.mail.reader;

import org.jlgranda.fede.sri.factura.v110.Factura;


/**
 * Contenedor para procesamiento de un archivo xml que contiene la factura electrónica
 * @author jlgranda
 */
public class FacturaReader {
    
    private Factura factura;
    
    private String xml;
    
    private String fileName;
    
    private String from;

    public FacturaReader() {
    }

    public FacturaReader(Factura factura, String xml, String fileName) {
        this.factura = factura;
        this.xml = xml;
        this.fileName = fileName;
    }
    
    public FacturaReader(Factura factura, String xml, String fileName, String from) {
        this.factura = factura;
        this.xml = xml;
        this.fileName = fileName;
        this.from = from;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getXml() {
        return xml;
    }

    public void setXml(String xml) {
        this.xml = xml;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
