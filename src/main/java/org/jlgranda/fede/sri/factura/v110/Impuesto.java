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
 * Contiene la informacion de los impuestos
 * 
 * <p>Clase Java para impuesto complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="impuesto"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="codigo" type="{}codigo"/&gt;
 *         &lt;element name="codigoPorcentaje" type="{}codigoPorcentaje"/&gt;
 *         &lt;element name="tarifa" type="{}tarifa"/&gt;
 *         &lt;element name="baseImponible" type="{}baseImponible"/&gt;
 *         &lt;element name="valor" type="{}valor"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */

@Entity
@Table(name = "SRI_IMPUESTO")
public class Impuesto extends PersistentObject implements Comparable<Impuesto>, Serializable {

    private static final long serialVersionUID = -8190749683370114743L;

    protected String codigo;
    protected String codigoPorcentaje;
    protected BigDecimal tarifa;
    protected BigDecimal baseImponible;
    protected BigDecimal valor;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "detalle_id", insertable=true, updatable=true, nullable=true)
    protected Detalle detalle;

    /**
     * Obtiene el valor de la propiedad codigo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Define el valor de la propiedad codigo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigo(String value) {
        this.codigo = value;
    }

    /**
     * Obtiene el valor de la propiedad codigoPorcentaje.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    /**
     * Define el valor de la propiedad codigoPorcentaje.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodigoPorcentaje(String value) {
        this.codigoPorcentaje = value;
    }

    /**
     * Obtiene el valor de la propiedad tarifa.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTarifa() {
        return tarifa;
    }

    /**
     * Define el valor de la propiedad tarifa.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTarifa(BigDecimal value) {
        this.tarifa = value;
    }

    /**
     * Obtiene el valor de la propiedad baseImponible.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    /**
     * Define el valor de la propiedad baseImponible.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBaseImponible(BigDecimal value) {
        this.baseImponible = value;
    }

    /**
     * Obtiene el valor de la propiedad valor.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValor() {
        return valor;
    }

    /**
     * Define el valor de la propiedad valor.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValor(BigDecimal value) {
        this.valor = value;
    }

    public Detalle getDetalle() {
        return detalle;
    }

    public void setDetalle(Detalle detallle) {
        this.detalle = detallle;
    }

    @Override
    public int compareTo(Impuesto o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
