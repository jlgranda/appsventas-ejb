//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.07.21 a las 07:00:02 PM ECT 
//
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
 * Clase Java para detalleImpuestos complex type.
 *
 * <p>
 * El siguiente fragmento de esquema especifica el contenido que se espera que
 * haya en esta clase.
 *
 * <pre>
 * &lt;complexType name="detalleImpuestos"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="detalleImpuesto" maxOccurs="unbounded"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="codigo" type="{}codigoReembolso"/&gt;
 *                   &lt;element name="codigoPorcentaje" type="{}codigoPorcentajeReembolso"/&gt;
 *                   &lt;element name="tarifa" type="{}tarifaReembolso"/&gt;
 *                   &lt;element name="baseImponibleReembolso" type="{}baseImponibleReembolso"/&gt;
 *                   &lt;element name="impuestoReembolso" type="{}impuestoReembolso"/&gt;
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
@Table(name = "SRI_DETALLE_IMPUËSTO")
public class DetalleImpuesto extends PersistentObject implements Comparable<DetalleImpuesto>, Serializable {

    private static final long serialVersionUID = -8619333609976064859L;

    protected String codigo;
    protected String codigoPorcentaje;
    protected String tarifa;
    protected BigDecimal baseImponibleReembolso;
    protected BigDecimal impuestoReembolso;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "reembolso_detalle_id", insertable=true, updatable=true, nullable=true)
    private ReembolsoDetalle reembolsoDetalle;

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
     * @return possible object is {@link String }
     *
     */
    public String getTarifa() {
        return tarifa;
    }

    /**
     * Define el valor de la propiedad tarifa.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTarifa(String value) {
        this.tarifa = value;
    }

    /**
     * Obtiene el valor de la propiedad baseImponibleReembolso.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getBaseImponibleReembolso() {
        return baseImponibleReembolso;
    }

    /**
     * Define el valor de la propiedad baseImponibleReembolso.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setBaseImponibleReembolso(BigDecimal value) {
        this.baseImponibleReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad impuestoReembolso.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getImpuestoReembolso() {
        return impuestoReembolso;
    }

    /**
     * Define el valor de la propiedad impuestoReembolso.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setImpuestoReembolso(BigDecimal value) {
        this.impuestoReembolso = value;
    }

    public ReembolsoDetalle getReembolsoDetalle() {
        return reembolsoDetalle;
    }

    public void setReembolsoDetalle(ReembolsoDetalle reembolsoDetalle) {
        this.reembolsoDetalle = reembolsoDetalle;
    }

    @Override
    public int compareTo(DetalleImpuesto o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
