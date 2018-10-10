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
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.jlgranda.fede.model.sales.Payment;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
/**
 * <p>
 * Clase Java para anonymous complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="formaPago" type="{}formaPago"/&gt;
 *         &lt;element name="total" type="{}total"/&gt;
 *         &lt;element name="plazo" type="{}plazo" minOccurs="0"/&gt;
 *         &lt;element name="unidadTiempo" type="{}unidadTiempo" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@Entity
@Table(name = "SRI_PAGO")
public class Pago extends PersistentObject implements Comparable<Pago>, Serializable {

    private static final long serialVersionUID = 7831760984077958289L;

    protected String formaPago;
    protected BigDecimal total;
    protected BigDecimal plazo;
    protected String unidadTiempo;

    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "info_factura_id", insertable = true, updatable = true, nullable = true)
    private InfoFactura infoFactura;

    /**
     * Obtiene el valor de la propiedad formaPago.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFormaPago() {
        return formaPago;
    }

    /**
     * Define el valor de la propiedad formaPago.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFormaPago(String value) {
        this.formaPago = value;
    }

    /**
     * Obtiene el valor de la propiedad total.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTotal() {
        return total;
    }

    /**
     * Define el valor de la propiedad total.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTotal(BigDecimal value) {
        this.total = value;
    }

    /**
     * Obtiene el valor de la propiedad plazo.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getPlazo() {
        return plazo;
    }

    /**
     * Define el valor de la propiedad plazo.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setPlazo(BigDecimal value) {
        this.plazo = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadTiempo.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUnidadTiempo() {
        return unidadTiempo;
    }

    /**
     * Define el valor de la propiedad unidadTiempo.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUnidadTiempo(String value) {
        this.unidadTiempo = value;
    }

    public InfoFactura getInfoFactura() {
        return infoFactura;
    }

    public void setInfoFactura(InfoFactura infoFactura) {
        this.infoFactura = infoFactura;
    }

    @Override
    public int compareTo(Pago o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
