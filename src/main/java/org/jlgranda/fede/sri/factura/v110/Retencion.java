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
 *
 * @author jlgranda
 */
@Entity
@Table(name = "SRI_RETENCION")
public class Retencion  extends PersistentObject implements Comparable<Retencion>, Serializable {

    private static final long serialVersionUID = 6273862491218867862L;

    protected String codigo;
    protected String codigoPorcentaje;
    protected BigDecimal tarifa;
    protected BigDecimal valor;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "factura_id", insertable=true, updatable=true, nullable=true)
    private Factura factura;

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

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public int compareTo(Retencion o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
