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
package org.jlgranda.fede.model.document;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
import javax.xml.bind.annotation.XmlRootElement;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.SourceType;

/**
 * Modelo de persistencia de factura electrónica fede, almacena datos básicos de
 * la factura y el contenido en detalle esta en el XML accesible vía el atributo
 * contenido
 *
 * @author jlgranda
 */
@Entity
@Table(name = "FACTURA_ELECTRONICA")
@DiscriminatorValue(value = "FEDE")
@PrimaryKeyJoinColumn(name = "facturaElectronicaId")
@NamedQueries({
    @NamedQuery(name = "FacturaElectronica.findBussinesEntityByTag", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1 ORDER BY m.bussinesEntity.fechaEmision DESC"),
    @NamedQuery(name = "FacturaElectronica.findBussinesEntityByTagAndOwner", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2 ORDER BY m.bussinesEntity.fechaEmision DESC"),
    @NamedQuery(name = "FacturaElectronica.findBussinesEntityByTagAndOwnerAndEmision", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2 and m.bussinesEntity.fechaEmision  >= ?3 and m.bussinesEntity.fechaEmision <= ?4 ORDER BY m.bussinesEntity.fechaEmision DESC")
})
@XmlRootElement
public class FacturaElectronica extends BussinesEntity {

    private static final long serialVersionUID = -1326570634296607679L;

    private BigDecimal totalSinImpuestos;

    private BigDecimal totalDescuento;

    //TODO agregar lista de impuestos, cuando sea necesario
    private BigDecimal importeTotal;

    private String moneda;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaEmision")
    private Date fechaEmision;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAutorizacion")
    private Date fechaAutorizacion;

    /**
     * Fuente de XML
     */
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    protected SourceType sourceType;
    
    /**
     * Nombre de archivo de donde se importó la factura
     */
    private String filename;
    /**
     * El contenido XML con los datos de la factura
     */
    @Column(columnDefinition = "TEXT")
    private String contenido;
    
    

    private String claveAcceso;

    private String numeroAutorizacion;

    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

}
