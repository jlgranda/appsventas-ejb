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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

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
 *         &lt;element name="codigo" type="{}codigo"/&gt;
 *         &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/&gt;
 *         &lt;element name="descuentoAdicional" type="{}descuentoAdicional" minOccurs="0"/&gt;
 *         &lt;element name="baseImponible" type="{}baseImponible"/&gt;
 *         &lt;element name="tarifa" type="{}tarifa" minOccurs="0"/&gt;
 *         &lt;element name="valor" type="{}valor"/&gt;
 *         &lt;element name="valorDevolucionIva" type="{}valorDevolucionIva" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@Entity
@Table(name = "SRI_TOTAL_IMPUESTO")
public class TotalImpuesto extends PersistentObject implements Comparable<Pago>, Serializable  {

    private static final long serialVersionUID = 2385238294874810586L;

    protected String codigo;
    protected String codigoPorcentaje;
    protected BigDecimal descuentoAdicional;
    protected BigDecimal baseImponible;
    protected BigDecimal tarifa;
    protected BigDecimal valor;
    protected BigDecimal valorDevolucionIva;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "info_factura_id", insertable=true, updatable=true, nullable=true)
    private InfoFactura infoFactura;
    

    /**
     * Obtiene el valor de la propiedad codigo.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoPorcentaje.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    /**
     * Define el valor de la propiedad codigoPorcentaje.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodigoPorcentaje(String value) {
        this.codigoPorcentaje = value;
    }

    /**
     * Obtiene el valor de la propiedad descuentoAdicional.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getDescuentoAdicional() {
        return descuentoAdicional;
    }

    /**
     * Define el valor de la propiedad descuentoAdicional.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setDescuentoAdicional(BigDecimal value) {
        this.descuentoAdicional = value;
    }

    /**
     * Obtiene el valor de la propiedad baseImponible.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    /**
     * Define el valor de la propiedad baseImponible.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setBaseImponible(BigDecimal value) {
        this.baseImponible = value;
    }

    /**
     * Obtiene el valor de la propiedad tarifa.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * Define el valor de la propiedad tarifa.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTarifa(BigDecimal value) {
        this.tarifa = value;
    }

    /**
     * Obtiene el valor de la propiedad valor.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define el valor de la propiedad valor.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

    /**
     * Obtiene el valor de la propiedad valorDevolucionIva.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getValorDevolucionIva() {
        return valorDevolucionIva;
    }

    /**
     * Define el valor de la propiedad valorDevolucionIva.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setValorDevolucionIva(BigDecimal value) {
        this.valorDevolucionIva = value;
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