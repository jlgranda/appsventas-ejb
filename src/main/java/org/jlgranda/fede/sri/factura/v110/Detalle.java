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
 *         &lt;element name="codigoPrincipal" type="{}codigoPrincipal" minOccurs="0"/&gt;
 *         &lt;element name="codigoAuxiliar" type="{}codigoAuxiliar" minOccurs="0"/&gt;
 *         &lt;element name="descripcion" type="{}descripcion"/&gt;
 *         &lt;element name="unidadMedida" type="{}unidadMedida" minOccurs="0"/&gt;
 *         &lt;element name="cantidad" type="{}cantidad"/&gt;
 *         &lt;element name="precioUnitario" type="{}precioUnitario"/&gt;
 *         &lt;element name="descuento" type="{}descuento"/&gt;
 *         &lt;element name="precioTotalSinImpuesto" type="{}precioTotalSinImpuesto"/&gt;
 *         &lt;element name="detallesAdicionales" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="detAdicional" maxOccurs="3"&gt;
 *                     &lt;complexType&gt;
 *                       &lt;complexContent&gt;
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                           &lt;attribute name="nombre"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                 &lt;pattern value="[^\n]*"/&gt;
 *                                 &lt;minLength value="1"/&gt;
 *                                 &lt;maxLength value="300"/&gt;
 *                               &lt;/restriction&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                           &lt;attribute name="valor"&gt;
 *                             &lt;simpleType&gt;
 *                               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                                 &lt;pattern value="[^\n]*"/&gt;
 *                                 &lt;minLength value="1"/&gt;
 *                                 &lt;maxLength value="300"/&gt;
 *                               &lt;/restriction&gt;
 *                             &lt;/simpleType&gt;
 *                           &lt;/attribute&gt;
 *                         &lt;/restriction&gt;
 *                       &lt;/complexContent&gt;
 *                     &lt;/complexType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="impuestos"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="impuesto" type="{}impuesto" maxOccurs="unbounded"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@Entity
@Table(name = "SRI_DETALLE")
public class Detalle extends PersistentObject implements Comparable<Detalle>, Serializable {

    private static final long serialVersionUID = 158321048552119523L;

    protected String codigoPrincipal;
    protected String codigoAuxiliar;
    protected String descripcion;
    protected String unidadMedida;
    protected BigDecimal cantidad;
    protected BigDecimal precioUnitario;
    protected BigDecimal descuento;
    protected BigDecimal precioTotalSinImpuesto;
    //protected Factura.Detalles.Detalle.DetallesAdicionales detallesAdicionales;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "detalle", fetch = FetchType.LAZY)
    protected List<Impuesto> impuestos;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "factura_id", insertable=true, updatable=true, nullable=true)
    private Factura factura;

    /**
     * Obtiene el valor de la propiedad codigoPrincipal.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodigoPrincipal() {
        return codigoPrincipal;
    }

    /**
     * Define el valor de la propiedad codigoPrincipal.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodigoPrincipal(String value) {
        this.codigoPrincipal = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoAuxiliar.
     *
     * @return possible object is {@link String }
     *
     */
    public String getCodigoAuxiliar() {
        return codigoAuxiliar;
    }

    /**
     * Define el valor de la propiedad codigoAuxiliar.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setCodigoAuxiliar(String value) {
        this.codigoAuxiliar = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad unidadMedida.
     *
     * @return possible object is {@link String }
     *
     */
    public String getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Define el valor de la propiedad unidadMedida.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setUnidadMedida(String value) {
        this.unidadMedida = value;
    }

    /**
     * Obtiene el valor de la propiedad cantidad.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getCantidad() {
        return cantidad;
    }

    /**
     * Define el valor de la propiedad cantidad.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setCantidad(BigDecimal value) {
        this.cantidad = value;
    }

    /**
     * Obtiene el valor de la propiedad precioUnitario.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Define el valor de la propiedad precioUnitario.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setPrecioUnitario(BigDecimal value) {
        this.precioUnitario = value;
    }

    /**
     * Obtiene el valor de la propiedad descuento.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getDescuento() {
        return descuento;
    }

    /**
     * Define el valor de la propiedad descuento.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setDescuento(BigDecimal value) {
        this.descuento = value;
    }

    /**
     * Obtiene el valor de la propiedad precioTotalSinImpuesto.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getPrecioTotalSinImpuesto() {
        return precioTotalSinImpuesto;
    }

    /**
     * Define el valor de la propiedad precioTotalSinImpuesto.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setPrecioTotalSinImpuesto(BigDecimal value) {
        this.precioTotalSinImpuesto = value;
    }
    
    
    public List<Impuesto> getImpuestos() {
        return impuestos;
    }

//    /**
//     * Obtiene el valor de la propiedad detallesAdicionales.
//     *
//     * @return possible object is
//             *     {@link Factura.Detalles.Detalle.DetallesAdicionales }
//     *
//     */
//    public Factura.Detalles.Detalle.DetallesAdicionales getDetallesAdicionales() {
//        return detallesAdicionales;
//    }
//
//    /**
//     * Define el valor de la propiedad detallesAdicionales.
//     *
//     * @param value allowed object is
//             *     {@link Factura.Detalles.Detalle.DetallesAdicionales }
//     *
//     */
//    public void setDetallesAdicionales(Factura.Detalles.Detalle.DetallesAdicionales value) {
//        this.detallesAdicionales = value;
//    }
//
//    /**
//     * Obtiene el valor de la propiedad impuestos.
//     *
//     * @return possible object is {@link Factura.Detalles.Detalle.Impuestos }
//     *
//     */
//    public Factura.Detalles.Detalle.Impuestos getImpuestos() {
//        return impuestos;
//    }
//
//    /**
//     * Define el valor de la propiedad impuestos.
//     *
//     * @param value allowed object is
//             *     {@link Factura.Detalles.Detalle.Impuestos }
//     *
//     */
//    public void setImpuestos(Factura.Detalles.Detalle.Impuestos value) {
//        this.impuestos = value;
//
//    }
    public void setImpuestos(List<Impuesto> impuestos) {
        this.impuestos = impuestos;
    }

    public Factura getFactura() {
        return factura;
    }
    
    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    @Override
    public int compareTo(Detalle o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
