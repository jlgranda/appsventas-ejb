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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "SRI_INFO_FACTURA")
public class InfoFactura   extends PersistentObject implements Comparable<InfoFactura>, Serializable {

    private static final long serialVersionUID = -1762871337609514597L;

    protected String fechaEmision;
    protected String dirEstablecimiento;
    protected String contribuyenteEspecial;
    protected ObligadoContabilidad obligadoContabilidad;
    protected String comercioExterior;
    protected String incoTermFactura;
    protected String lugarIncoTerm;
    protected String paisOrigen;
    protected String puertoEmbarque;
    protected String puertoDestino;
    protected String paisDestino;
    protected String paisAdquisicion;
    protected String tipoIdentificacionComprador;
    protected String guiaRemision;
    protected String razonSocialComprador;
    protected String identificacionComprador;
    protected String direccionComprador;
    protected BigDecimal totalSinImpuestos;
    protected String incoTermTotalSinImpuestos;
    protected BigDecimal totalDescuento;
    protected String codDocReembolso;
    protected BigDecimal totalComprobantesReembolso;
    protected BigDecimal totalBaseImponibleReembolso;
    protected BigDecimal totalImpuestoReembolso;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "infoFactura", fetch = FetchType.LAZY)
    protected List<TotalImpuesto> totalConImpuestos;
    protected BigDecimal propina;
    protected BigDecimal fleteInternacional;
    protected BigDecimal seguroInternacional;
    protected BigDecimal gastosAduaneros;
    protected BigDecimal gastosTransporteOtros;
    protected BigDecimal importeTotal;
    protected String moneda;
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "infoFactura", fetch = FetchType.LAZY)
    protected List<Pago> pagos;
    protected BigDecimal valorRetIva;
    protected BigDecimal valorRetRenta;

    /**
     * Obtiene el valor de la propiedad fechaEmision.
     *
     * @return possible object is {@link String }
     *
     */
    public String getFechaEmision() {
        return fechaEmision;
    }

    /**
     * Define el valor de la propiedad fechaEmision.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setFechaEmision(String value) {
        this.fechaEmision = value;
    }

    /**
     * Obtiene el valor de la propiedad dirEstablecimiento.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDirEstablecimiento() {
        return dirEstablecimiento;
    }

    /**
     * Define el valor de la propiedad dirEstablecimiento.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDirEstablecimiento(String value) {
        this.dirEstablecimiento = value;
    }

    /**
     * Obtiene el valor de la propiedad contribuyenteEspecial.
     *
     * @return possible object is {@link String }
     *
     */
    public String getContribuyenteEspecial() {
        return contribuyenteEspecial;
    }

    /**
     * Define el valor de la propiedad contribuyenteEspecial.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setContribuyenteEspecial(String value) {
        this.contribuyenteEspecial = value;
    }

    /**
     * Obtiene el valor de la propiedad obligadoContabilidad.
     *
     * @return possible object is {@link ObligadoContabilidad }
     *
     */
    public ObligadoContabilidad getObligadoContabilidad() {
        return obligadoContabilidad;
    }

    /**
     * Define el valor de la propiedad obligadoContabilidad.
     *
     * @param value allowed object is {@link ObligadoContabilidad }
     *
     */
    public void setObligadoContabilidad(ObligadoContabilidad value) {
        this.obligadoContabilidad = value;
    }

    /**
     * Obtiene el valor de la propiedad comercioExterior.
     *
     * @return possible object is {@link String }
     *
     */
    public String getComercioExterior() {
        return comercioExterior;
    }

    /**
     * Define el valor de la propiedad comercioExterior.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setComercioExterior(String value) {
        this.comercioExterior = value;
    }

    /**
     * Obtiene el valor de la propiedad incoTermFactura.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIncoTermFactura() {
        return incoTermFactura;
    }

    /**
     * Define el valor de la propiedad incoTermFactura.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIncoTermFactura(String value) {
        this.incoTermFactura = value;
    }

    /**
     * Obtiene el valor de la propiedad lugarIncoTerm.
     *
     * @return possible object is {@link String }
     *
     */
    public String getLugarIncoTerm() {
        return lugarIncoTerm;
    }

    /**
     * Define el valor de la propiedad lugarIncoTerm.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setLugarIncoTerm(String value) {
        this.lugarIncoTerm = value;
    }

    /**
     * Obtiene el valor de la propiedad paisOrigen.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPaisOrigen() {
        return paisOrigen;
    }

    /**
     * Define el valor de la propiedad paisOrigen.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPaisOrigen(String value) {
        this.paisOrigen = value;
    }

    /**
     * Obtiene el valor de la propiedad puertoEmbarque.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPuertoEmbarque() {
        return puertoEmbarque;
    }

    /**
     * Define el valor de la propiedad puertoEmbarque.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPuertoEmbarque(String value) {
        this.puertoEmbarque = value;
    }

    /**
     * Obtiene el valor de la propiedad puertoDestino.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPuertoDestino() {
        return puertoDestino;
    }

    /**
     * Define el valor de la propiedad puertoDestino.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPuertoDestino(String value) {
        this.puertoDestino = value;
    }

    /**
     * Obtiene el valor de la propiedad paisDestino.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPaisDestino() {
        return paisDestino;
    }

    /**
     * Define el valor de la propiedad paisDestino.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPaisDestino(String value) {
        this.paisDestino = value;
    }

    /**
     * Obtiene el valor de la propiedad paisAdquisicion.
     *
     * @return possible object is {@link String }
     *
     */
    public String getPaisAdquisicion() {
        return paisAdquisicion;
    }

    /**
     * Define el valor de la propiedad paisAdquisicion.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setPaisAdquisicion(String value) {
        this.paisAdquisicion = value;
    }

    /**
     * Obtiene el valor de la propiedad tipoIdentificacionComprador.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTipoIdentificacionComprador() {
        return tipoIdentificacionComprador;
    }

    /**
     * Define el valor de la propiedad tipoIdentificacionComprador.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTipoIdentificacionComprador(String value) {
        this.tipoIdentificacionComprador = value;
    }

    /**
     * Obtiene el valor de la propiedad guiaRemision.
     *
     * @return possible object is {@link String }
     *
     */
    public String getGuiaRemision() {
        return guiaRemision;
    }

    /**
     * Define el valor de la propiedad guiaRemision.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setGuiaRemision(String value) {
        this.guiaRemision = value;
    }

    /**
     * Obtiene el valor de la propiedad razonSocialComprador.
     *
     * @return possible object is {@link String }
     *
     */
    public String getRazonSocialComprador() {
        return razonSocialComprador;
    }

    /**
     * Define el valor de la propiedad razonSocialComprador.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setRazonSocialComprador(String value) {
        this.razonSocialComprador = value;
    }

    /**
     * Obtiene el valor de la propiedad identificacionComprador.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIdentificacionComprador() {
        return identificacionComprador;
    }

    /**
     * Define el valor de la propiedad identificacionComprador.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIdentificacionComprador(String value) {
        this.identificacionComprador = value;
    }

    /**
     * Obtiene el valor de la propiedad direccionComprador.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDireccionComprador() {
        return direccionComprador;
    }

    /**
     * Define el valor de la propiedad direccionComprador.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDireccionComprador(String value) {
        this.direccionComprador = value;
    }

    /**
     * Obtiene el valor de la propiedad totalSinImpuestos.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    /**
     * Define el valor de la propiedad totalSinImpuestos.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTotalSinImpuestos(BigDecimal value) {
        this.totalSinImpuestos = value;
    }

    /**
     * Obtiene el valor de la propiedad incoTermTotalSinImpuestos.
     *
     * @return possible object is {@link String }
     *
     */
    public String getIncoTermTotalSinImpuestos() {
        return incoTermTotalSinImpuestos;
    }

    /**
     * Define el valor de la propiedad incoTermTotalSinImpuestos.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setIncoTermTotalSinImpuestos(String value) {
        this.incoTermTotalSinImpuestos = value;
    }

    /**
     * Obtiene el valor de la propiedad totalDescuento.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    /**
     * Define el valor de la propiedad totalDescuento.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTotalDescuento(BigDecimal value) {
        this.totalDescuento = value;
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
     * Obtiene el valor de la propiedad totalComprobantesReembolso.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTotalComprobantesReembolso() {
        return totalComprobantesReembolso;
    }

    /**
     * Define el valor de la propiedad totalComprobantesReembolso.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTotalComprobantesReembolso(BigDecimal value) {
        this.totalComprobantesReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad totalBaseImponibleReembolso.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTotalBaseImponibleReembolso() {
        return totalBaseImponibleReembolso;
    }

    /**
     * Define el valor de la propiedad totalBaseImponibleReembolso.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTotalBaseImponibleReembolso(BigDecimal value) {
        this.totalBaseImponibleReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad totalImpuestoReembolso.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getTotalImpuestoReembolso() {
        return totalImpuestoReembolso;
    }

    /**
     * Define el valor de la propiedad totalImpuestoReembolso.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setTotalImpuestoReembolso(BigDecimal value) {
        this.totalImpuestoReembolso = value;
    }

    /**
     * Obtiene el valor de la propiedad totalConImpuestos.
     *
     * @return possible object is {@link Factura.InfoFactura.TotalConImpuestos }
     *
     */
    public List<TotalImpuesto> getTotalConImpuestos() {
        return totalConImpuestos;
    }

    /**
     * Define el valor de la propiedad totalConImpuestos.
     *
     * @param value allowed object is
         *     {@link Factura.InfoFactura.TotalConImpuestos }
     *
     */
    public void setTotalConImpuestos(List<TotalImpuesto> value) {
        this.totalConImpuestos = value;
    }

    /**
     * Obtiene el valor de la propiedad propina.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getPropina() {
        return propina;
    }

    /**
     * Define el valor de la propiedad propina.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setPropina(BigDecimal value) {
        this.propina = value;
    }

    /**
     * Obtiene el valor de la propiedad fleteInternacional.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getFleteInternacional() {
        return fleteInternacional;
    }

    /**
     * Define el valor de la propiedad fleteInternacional.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setFleteInternacional(BigDecimal value) {
        this.fleteInternacional = value;
    }

    /**
     * Obtiene el valor de la propiedad seguroInternacional.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getSeguroInternacional() {
        return seguroInternacional;
    }

    /**
     * Define el valor de la propiedad seguroInternacional.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setSeguroInternacional(BigDecimal value) {
        this.seguroInternacional = value;
    }

    /**
     * Obtiene el valor de la propiedad gastosAduaneros.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getGastosAduaneros() {
        return gastosAduaneros;
    }

    /**
     * Define el valor de la propiedad gastosAduaneros.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setGastosAduaneros(BigDecimal value) {
        this.gastosAduaneros = value;
    }

    /**
     * Obtiene el valor de la propiedad gastosTransporteOtros.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getGastosTransporteOtros() {
        return gastosTransporteOtros;
    }

    /**
     * Define el valor de la propiedad gastosTransporteOtros.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setGastosTransporteOtros(BigDecimal value) {
        this.gastosTransporteOtros = value;
    }

    /**
     * Obtiene el valor de la propiedad importeTotal.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    /**
     * Define el valor de la propiedad importeTotal.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setImporteTotal(BigDecimal value) {
        this.importeTotal = value;
    }

    /**
     * Obtiene el valor de la propiedad moneda.
     *
     * @return possible object is {@link String }
     *
     */
    public String getMoneda() {
        return moneda;
    }

    /**
     * Define el valor de la propiedad moneda.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setMoneda(String value) {
        this.moneda = value;
    }

    /**
     * Obtiene el valor de la propiedad pagos.
     *
     * @return possible object is {@link Pagos }
     *
     */
    public List<Pago> getPagos() {
        return pagos;
    }

    /**
     * Define el valor de la propiedad pagos.
     *
     * @param value allowed object is {@link Pagos }
     *
     */
    public void setPagos(List<Pago> value) {
        this.pagos = value;
    }

    /**
     * Obtiene el valor de la propiedad valorRetIva.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getValorRetIva() {
        return valorRetIva;
    }

    /**
     * Define el valor de la propiedad valorRetIva.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setValorRetIva(BigDecimal value) {
        this.valorRetIva = value;
    }

    /**
     * Obtiene el valor de la propiedad valorRetRenta.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getValorRetRenta() {
        return valorRetRenta;
    }

    /**
     * Define el valor de la propiedad valorRetRenta.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setValorRetRenta(BigDecimal value) {
        this.valorRetRenta = value;
    }

    @Override
    public int compareTo(InfoFactura o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
