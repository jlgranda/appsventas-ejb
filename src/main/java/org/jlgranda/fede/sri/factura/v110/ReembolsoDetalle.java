/*
 * Copyright (C) 2018 jlgranda
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
package org.jlgranda.fede.sri.factura.v110;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "SRI_REEMBOLSO_DETALLE")
public class ReembolsoDetalle extends PersistentObject implements Comparable<ReembolsoDetalle>, Serializable {

    private static final long serialVersionUID = 2708076481033727683L;

    protected String tipoIdentificacionProveedorReembolso;
    protected String identificacionProveedorReembolso;
    protected String codPaisPagoProveedorReembolso;
    protected String tipoProveedorReembolso;
    protected String codDocReembolso;
    protected String estabDocReembolso;
    protected String ptoEmiDocReembolso;
    protected String secuencialDocReembolso;
    protected String fechaEmisionDocReembolso;
    protected String numeroautorizacionDocReemb;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "reembolsoDetalle", fetch = FetchType.LAZY)
    protected List<DetalleImpuesto> detalleImpuestos;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "factura_id", insertable=true, updatable=true, nullable=true)
    private Factura factura;

    /**
     * Obtiene el valor de la propiedad tipoIdentificacionProveedorReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoIdentificacionProveedorReembolso() {
        return tipoIdentificacionProveedorReembolso;
    }

    /**
     * Define el valor de la propiedad tipoIdentificacionProveedorReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoIdentificacionProveedorReembolso(String value) {
        this.tipoIdentificacionProveedorReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad identificacionProveedorReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdentificacionProveedorReembolso() {
        return identificacionProveedorReembolso;
    }

    /**
     * Define el valor de la propiedad identificacionProveedorReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdentificacionProveedorReembolso(String value) {
        this.identificacionProveedorReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad codPaisPagoProveedorReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodPaisPagoProveedorReembolso() {
        return codPaisPagoProveedorReembolso;
    }

    /**
     * Define el valor de la propiedad codPaisPagoProveedorReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodPaisPagoProveedorReembolso(String value) {
        this.codPaisPagoProveedorReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoProveedorReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoProveedorReembolso() {
        return tipoProveedorReembolso;
    }

    /**
     * Define el valor de la propiedad tipoProveedorReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoProveedorReembolso(String value) {
        this.tipoProveedorReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad codDocReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodDocReembolso() {
        return codDocReembolso;
    }

    /**
     * Define el valor de la propiedad codDocReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodDocReembolso(String value) {
        this.codDocReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad estabDocReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEstabDocReembolso() {
        return estabDocReembolso;
    }

    /**
     * Define el valor de la propiedad estabDocReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEstabDocReembolso(String value) {
        this.estabDocReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad ptoEmiDocReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPtoEmiDocReembolso() {
        return ptoEmiDocReembolso;
    }

    /**
     * Define el valor de la propiedad ptoEmiDocReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPtoEmiDocReembolso(String value) {
        this.ptoEmiDocReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad secuencialDocReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getSecuencialDocReembolso() {
        return secuencialDocReembolso;
    }

    /**
     * Define el valor de la propiedad secuencialDocReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setSecuencialDocReembolso(String value) {
        this.secuencialDocReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaEmisionDocReembolso.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFechaEmisionDocReembolso() {
        return fechaEmisionDocReembolso;
    }

    /**
     * Define el valor de la propiedad fechaEmisionDocReembolso.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFechaEmisionDocReembolso(String value) {
        this.fechaEmisionDocReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad numeroautorizacionDocReemb.
     *
     * @return possible object is {@link String }
     *
     */
    public String getNumeroautorizacionDocReemb() {
        return numeroautorizacionDocReemb;
    }

    /**
     * Define el valor de la propiedad numeroautorizacionDocReemb.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setNumeroautorizacionDocReemb(String value) {
        this.numeroautorizacionDocReemb = value;
    }

    

    /**
     * Obtiene el valor de la propiedad detalleImpuestos.
     *
     * @return possible object is {@link DetalleImpuestos }
     *
     */
    public List<DetalleImpuesto> getDetalleImpuestos() {
        return detalleImpuestos;
    }
    

    /**
     * Define el valor de la propiedad detalleImpuestos.
     *
     * @param value allowed object is {@link DetalleImpuestos }
     *
     */
    public void setDetalleImpuestos(List<DetalleImpuesto> detalleImpuestos) {    
        this.detalleImpuestos = detalleImpuestos;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
    
    @Override
    public int compareTo(ReembolsoDetalle o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
