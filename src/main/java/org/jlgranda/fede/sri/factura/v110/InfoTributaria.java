//
// Este archivo ha sido generado por la arquitectura JavaTM para la implantación de la referencia de enlace (JAXB) XML v2.2.11 
// Visite <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Todas las modificaciones realizadas en este archivo se perderán si se vuelve a compilar el esquema de origen. 
// Generado el: 2018.07.21 a las 07:00:02 PM ECT 
//


package org.jlgranda.fede.sri.factura.v110;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.DeletableObject;


/**
 * Contiene la informacion tributaria generica
 * 
 * <p>Clase Java para infoTributaria complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="infoTributaria"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ambiente" type="{}ambiente"/&gt;
 *         &lt;element name="tipoEmision" type="{}tipoEmision"/&gt;
 *         &lt;element name="razonSocial" type="{}razonSocial"/&gt;
 *         &lt;element name="nombreComercial" type="{}nombreComercial" minOccurs="0"/&gt;
 *         &lt;element name="ruc" type="{}numeroRuc"/&gt;
 *         &lt;element name="claveAcceso" type="{}claveAcceso"/&gt;
 *         &lt;element name="codDoc" type="{}codDoc"/&gt;
 *         &lt;element name="estab" type="{}establecimiento"/&gt;
 *         &lt;element name="ptoEmi" type="{}puntoEmision"/&gt;
 *         &lt;element name="secuencial" type="{}secuencial"/&gt;
 *         &lt;element name="dirMatriz" type="{}dirMatriz"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */

@Embeddable
public class InfoTributaria implements Serializable {

    private static final long serialVersionUID = 1204843865332902715L;

    protected String ambiente;
    protected String tipoEmision;
    protected String razonSocial;
    protected String nombreComercial;
    protected String ruc;
    protected String claveAcceso;
    protected String codDoc;
    protected String estab;
    protected String ptoEmi;
    protected String secuencial;
    protected String dirMatriz;

    /**
     * Obtiene el valor de la propiedad ambiente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmbiente() {
        return ambiente;
    }

    /**
     * Define el valor de la propiedad ambiente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmbiente(String value) {
        this.ambiente = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoEmision.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoEmision() {
        return tipoEmision;
    }

    /**
     * Define el valor de la propiedad tipoEmision.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoEmision(String value) {
        this.tipoEmision = value;
    }

    /**
     * Obtiene el valor de la propiedad razonSocial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRazonSocial() {
        return razonSocial;
    }

    /**
     * Define el valor de la propiedad razonSocial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRazonSocial(String value) {
        this.razonSocial = value;
    }

    /**
     * Obtiene el valor de la propiedad nombreComercial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNombreComercial() {
        return nombreComercial;
    }

    /**
     * Define el valor de la propiedad nombreComercial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNombreComercial(String value) {
        this.nombreComercial = value;
    }

    /**
     * Obtiene el valor de la propiedad ruc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuc() {
        return ruc;
    }

    /**
     * Define el valor de la propiedad ruc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuc(String value) {
        this.ruc = value;
    }

    /**
     * Obtiene el valor de la propiedad claveAcceso.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveAcceso() {
        return claveAcceso;
    }

    /**
     * Define el valor de la propiedad claveAcceso.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveAcceso(String value) {
        this.claveAcceso = value;
    }

    /**
     * Obtiene el valor de la propiedad codDoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDoc() {
        return codDoc;
    }

    /**
     * Define el valor de la propiedad codDoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDoc(String value) {
        this.codDoc = value;
    }

    /**
     * Obtiene el valor de la propiedad estab.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstab() {
        return estab;
    }

    /**
     * Define el valor de la propiedad estab.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstab(String value) {
        this.estab = value;
    }

    /**
     * Obtiene el valor de la propiedad ptoEmi.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtoEmi() {
        return ptoEmi;
    }

    /**
     * Define el valor de la propiedad ptoEmi.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtoEmi(String value) {
        this.ptoEmi = value;
    }

    /**
     * Obtiene el valor de la propiedad secuencial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSecuencial() {
        return secuencial;
    }

    /**
     * Define el valor de la propiedad secuencial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSecuencial(String value) {
        this.secuencial = value;
    }

    /**
     * Obtiene el valor de la propiedad dirMatriz.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirMatriz() {
        return dirMatriz;
    }

    /**
     * Define el valor de la propiedad dirMatriz.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirMatriz(String value) {
        this.dirMatriz = value;
    }

}
