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
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;


/**
 * <p>Basada en esquema de facturas electrónicas v110 del SRI Ecuador.</p>
 * <p>Especificación pública</p>
 * 
 */
@Entity
@Table(name = "SRI_FACTURA")
@DiscriminatorValue(value = "FACT")
@PrimaryKeyJoinColumn(name = "facturaId")
@NamedQueries({    
})
public class Factura  extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 1949166151528851240L;

    /**
     *
     */
    protected InfoTributaria infoTributaria;
    
    @OneToOne
    @JoinColumn(name = "info_factura_id")
    protected InfoFactura infoFactura;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "factura", fetch = FetchType.LAZY)
    protected List<Detalle> detalles;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "factura", fetch = FetchType.LAZY)
    protected List<ReembolsoDetalle> reembolsos;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "factura", fetch = FetchType.LAZY)
    protected List<Retencion> retenciones;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "factura", fetch = FetchType.LAZY)
    protected List<CampoAdicional> infoAdicional;
    
    //TODO determina que info falta
    //protected SignatureType signature;
    protected String SRIId;
    protected String SRIVersion;

    /**
     * Obtiene el valor de la propiedad infoTributaria.
     * 
     * @return
     *     possible object is
     *     {@link InfoTributaria }
     *     
     */
    public InfoTributaria getInfoTributaria() {
        return infoTributaria;
    }

    /**
     * Define el valor de la propiedad infoTributaria.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoTributaria }
     *     
     */
    public void setInfoTributaria(InfoTributaria value) {
        this.infoTributaria = value;
    }

    public String getSRIId() {
        return SRIId;
    }

    public void setSRIId(String SRIId) {
        this.SRIId = SRIId;
    }

    public String getSRIVersion() {
        return SRIVersion;
    }

    public void setSRIVersion(String SRIVersion) {
        this.SRIVersion = SRIVersion;
    }
    
    /**
     * Obtiene el valor de la propiedad infoFactura.
     * 
     * @return
     *     possible object is
     *     {@link Factura.InfoFactura }
     *     
     */
    public InfoFactura getInfoFactura() {
        return infoFactura;
    }

    /**
     * Define el valor de la propiedad infoFactura.
     * 
     * @param value
     *     allowed object is
     *     {@link Factura.InfoFactura }
     *     
     */
    public void setInfoFactura(InfoFactura value) {
        this.infoFactura = value;
    }

    public List<Detalle> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<Detalle> detalles) {
        this.detalles = detalles;
    }

    public List<ReembolsoDetalle> getReembolsos() {
        return reembolsos;
    }

    public void setReembolsos(List<ReembolsoDetalle> reembolsos) {
        this.reembolsos = reembolsos;
    }

    public List<Retencion> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<Retencion> retenciones) {
        this.retenciones = retenciones;
    }
    
}
