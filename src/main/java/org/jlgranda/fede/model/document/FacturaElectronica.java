/*
 * Copyright (C) 2015 jlgranda
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
package org.jlgranda.fede.model.document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.Where;
import org.jpapi.model.Organization;
import org.jlgranda.fede.model.sales.Payment;
import org.jpapi.model.DeletableObject;
import org.jpapi.model.SourceType;
import org.jpapi.util.Lists;

/**
 * Modelo de persistencia de factura electrónica fede, almacena datos básicos de
 * la factura y el contenido en detalle esta en el XML accesible vía el atributo
 * contenido
 *
 * @author jlgranda
 */
@Entity
@Table(name = "FACTURA_ELECTRONICA")
@NamedQueries({
//    @NamedQuery(name = "FacturaElectronica.findBussinesEntityByTag", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1 ORDER BY m.bussinesEntity.fechaEmision DESC"),
//    @NamedQuery(name = "FacturaElectronica.findBussinesEntityByTagAndOwner", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2 ORDER BY m.bussinesEntity.fechaEmision DESC"),
//    @NamedQuery(name = "FacturaElectronica.findBussinesEntityByTagAndOwnerAndEmision", query = "select m.bussinesEntity FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2 and m.bussinesEntity.fechaEmision  >= ?3 and m.bussinesEntity.fechaEmision <= ?4 ORDER BY m.bussinesEntity.fechaEmision DESC"),
    @NamedQuery(name = "FacturaElectronica.findByOrg", query = "SELECT f FROM FacturaElectronica f WHERE f.organization=?1 and f.active=?2 ORDER BY f.lastUpdate DESC"),
    @NamedQuery(name = "FacturaElectronica.findCodeByOrg", query = "SELECT f.code FROM FacturaElectronica f WHERE f.organization=?1 and f.active=?2 ORDER BY f.lastUpdate DESC"),
    @NamedQuery(name = "FacturaElectronica.findByOwnerAndEmision", query = "SELECT f FROM FacturaElectronica f WHERE f.owner = ?1 and f.fechaEmision  >= ?2 and f.fechaEmision <= ?3 and f.active=?4 ORDER BY f.fechaEmision DESC"),
    @NamedQuery(name = "FacturaElectronica.findByOwnerAndEmisionAndEmissionType", query = "SELECT f FROM FacturaElectronica f WHERE f.owner = ?1 and f.fechaEmision  >= ?2 and f.fechaEmision <= ?3 and f.emissionType=?4 and f.active=?5 ORDER BY f.fechaEmision DESC"),
    @NamedQuery(name = "FacturaElectronica.findByOwnerAndEmisionAndEmissionTypeAndOrg", query = "SELECT f FROM FacturaElectronica f WHERE f.owner = ?1 and f.fechaEmision>=?2 and f.fechaEmision<=?3 and f.emissionType=?4 and f.active=?5 and f.organization=?6 ORDER BY f.fechaEmision DESC"),
    @NamedQuery(name = "FacturaElectronica.countBussinesEntityByTagAndOwner", query = "select count(m.bussinesEntity) FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2"),
    @NamedQuery(name = "FacturaElectronica.countBussinesEntityByOwner", query = "select count(f) FROM FacturaElectronica f WHERE f.owner = ?1"),
    @NamedQuery(name = "FacturaElectronica.countBussinesEntityByOrg", query = "select count(f) FROM FacturaElectronica f WHERE f.organization = ?1"),
    @NamedQuery(name = "FacturaElectronica.findLastsByOwner", query = "select f FROM FacturaElectronica f where f.owner=?1 ORDER BY f.id DESC"),
    @NamedQuery(name = "FacturaElectronica.findTotalBetween", query = "select sum(f.importeTotal) from FacturaElectronica f WHERE f.owner=?1 and f.fechaEmision >= ?2 and f.fechaEmision <= ?3"),
    @NamedQuery(name = "FacturaElectronica.findTotalByEmissionTypeBetween", query = "select sum(f.importeTotal) from FacturaElectronica f WHERE f.owner=?1 and f.fechaEmision >= ?2 and f.fechaEmision <= ?3 and f.emissionType=?4"),
    @NamedQuery(name = "FacturaElectronica.findTotalByEmissionTypeBetweenOrg", query = "select sum(f.importeTotal) from FacturaElectronica f WHERE f.organization=?1 and f.fechaEmision >= ?2 and f.fechaEmision <= ?3 and f.emissionType=?4"),
    @NamedQuery(name = "FacturaElectronica.findTotalByEmissionTypePayBetweenOrg", query = "select sum(p.amount) from Payment p LEFT JOIN p.facturaElectronica f WHERE f.organization=?1 and p.createdOn >= ?2 and p.createdOn <= ?3 and f.emissionType=?4"),
    @NamedQuery(name = "FacturaElectronica.findTopTotalBussinesEntityIdsBetween", query = "select sb.initials, sum(f.importeTotal), sb.firstname, sb.surname from FacturaElectronica f JOIN f.author sb WHERE f.author=sb.id and f.fechaEmision >= ?1 and f.fechaEmision <= ?2 GROUP BY sb ORDER BY sum(f.importeTotal) DESC"),
    @NamedQuery(name = "FacturaElectronica.findTopTotalBussinesEntityIdsBetweenOrg", query = "select sb.initials, sum(f.importeTotal), sb.firstname, sb.surname from FacturaElectronica f JOIN f.author sb WHERE f.organization=?1 and f.fechaEmision >= ?2 and f.fechaEmision <= ?3 GROUP BY sb ORDER BY sum(f.importeTotal) DESC"),})
public class FacturaElectronica extends DeletableObject<FacturaElectronica> implements Serializable {

    private static final long serialVersionUID = -1326570634296607679L;

    private BigDecimal totalSinImpuestos;

    private BigDecimal totalIVA0 = BigDecimal.ZERO;

    private BigDecimal totalIVA12;

    private BigDecimal totalDescuento = BigDecimal.ZERO;

    private BigDecimal importeTotal;

    private String moneda;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaEmision")
    private Date fechaEmision;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaAutorizacion")
    private Date fechaAutorizacion;

    /**
     * Fuente de XML
     */
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    protected SourceType sourceType;

    /**
     * Naturaleza de la factura
     */
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    protected EmissionType emissionType;

    /**
     * Clasificación de la factura
     */
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    protected FacturaType facturaType;

    /**
     * Nombre de archivo de donde se importó la factura
     */
    private String filename;
    /**
     * El contenido XML con los datos de la factura
     */
    @Column(columnDefinition = "TEXT")
    private String contenido;

    private String claveAcceso;

    private String numeroAutorizacion;

    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true, nullable = true)
    private Organization organization;

    /**
     * Para el seguimiento de pagos en facturas a crédito
     */
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "facturaElectronica", fetch = FetchType.LAZY)
    @Where(clause = "deleted = false") //sólo no eliminados
    @OrderBy(value = "id")
    private List<Payment> payments = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechaVencimiento")
    private Date fechaVencimiento;

    @Transient
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ultima_fecha_pago")
    private Date ultimaFechaPago;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "facturaElectronica", fetch = FetchType.LAZY)
    private List<FacturaElectronicaDetail> facturaElectronicaDetails = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = true, name = "document_type")
    private FacturaElectronica.DocumentType documentType;

    @Transient
    private BigDecimal subtotalIVA0 = BigDecimal.ZERO;

    @Transient
    private BigDecimal subtotalIVA12 = BigDecimal.ZERO;

    /**
     * Referencia al record registrado
     */
    @Column(name = "record_id", nullable = true)
    private Long recordId;

    public enum DocumentType {
        FACTURA,
        NOTA_COMPRA,
        PRODUCCION,
    }

    public BigDecimal getTotalSinImpuestos() {
        return totalSinImpuestos;
    }

    public void setTotalSinImpuestos(BigDecimal totalSinImpuestos) {
        this.totalSinImpuestos = totalSinImpuestos;
    }

    public BigDecimal getTotalIVA0() {
        return totalIVA0;
    }

    public void setTotalIVA0(BigDecimal totalIVA0) {
        this.totalIVA0 = totalIVA0;
    }

    public BigDecimal getTotalIVA12() {
        return totalIVA12;
    }

    public void setTotalIVA12(BigDecimal totalIVA12) {
        this.totalIVA12 = totalIVA12;
    }

    public BigDecimal getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(BigDecimal totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }

    public void setImporteTotal(BigDecimal importeTotal) {
        this.importeTotal = importeTotal;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public Date getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(Date fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public Date getFechaAutorizacion() {
        return fechaAutorizacion;
    }

    public void setFechaAutorizacion(Date fechaAutorizacion) {
        this.fechaAutorizacion = fechaAutorizacion;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public SourceType getSourceType() {
        return sourceType;
    }

    public void setSourceType(SourceType sourceType) {
        this.sourceType = sourceType;
    }

    public EmissionType getEmissionType() {
        return emissionType;
    }

    public void setEmissionType(EmissionType emissionType) {
        this.emissionType = emissionType;
    }

    public FacturaType getFacturaType() {
        return facturaType;
    }

    public void setFacturaType(FacturaType facturaType) {
        this.facturaType = facturaType;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getClaveAcceso() {
        return claveAcceso;
    }

    public void setClaveAcceso(String claveAcceso) {
        this.claveAcceso = claveAcceso;
    }

    public String getNumeroAutorizacion() {
        return numeroAutorizacion;
    }

    public void setNumeroAutorizacion(String numeroAutorizacion) {
        this.numeroAutorizacion = numeroAutorizacion;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public Payment addPayment(Payment payment) {
        payment.setFacturaElectronica(this);
        if (this.payments.contains(payment)) {
            replacePayment(payment);
        } else {
            this.payments.add(payment);
        }
        return payment;
    }

    public Payment replacePayment(Payment payment) {
        getPayments().set(getPayments().indexOf(payment), payment);
        return payment;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getUltimaFechaPago() {
        return ultimaFechaPago;
    }

    public void setUltimaFechaPago(Date ultimaFechaPago) {
        this.ultimaFechaPago = ultimaFechaPago;
    }

    public List<FacturaElectronicaDetail> getFacturaElectronicaDetails() {
        return facturaElectronicaDetails;
    }

    public void setFacturaElectronicaDetails(List<FacturaElectronicaDetail> facturaElectronicaDetails) {
        this.facturaElectronicaDetails = facturaElectronicaDetails;
    }

    public FacturaElectronicaDetail addFacturaElectronicaDetail(FacturaElectronicaDetail facturaElectronicaDetail) {
        facturaElectronicaDetail.setFacturaElectronica(this);
        if (this.facturaElectronicaDetails.contains(facturaElectronicaDetail)) {
            replaceFacturaElectronicaDetail(facturaElectronicaDetail);
        } else {
            this.facturaElectronicaDetails.add(facturaElectronicaDetail);
        }
        return facturaElectronicaDetail;
    }

    public FacturaElectronicaDetail replaceFacturaElectronicaDetail(FacturaElectronicaDetail facturaElectronicaDetail) {
        getFacturaElectronicaDetails().set(getFacturaElectronicaDetails().indexOf(facturaElectronicaDetail), facturaElectronicaDetail);
        return facturaElectronicaDetail;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public BigDecimal getSubtotalIVA0() {
        return subtotalIVA0;
    }

    public void setSubtotalIVA0(BigDecimal subtotalIVA0) {
        this.subtotalIVA0 = subtotalIVA0;
    }

    public BigDecimal getSubtotalIVA12() {
        return subtotalIVA12;
    }

    public void setSubtotalIVA12(BigDecimal subtotalIVA12) {
        this.subtotalIVA12 = subtotalIVA12;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.documentType);
        hash = 59 * hash + Objects.hashCode(this.emissionType);
        hash = 59 * hash + Objects.hashCode(this.payments);
        hash = 59 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FacturaElectronica other = (FacturaElectronica) obj;
        if (!Objects.equals(this.moneda, other.moneda)) {
            return false;
        }
        if (!Objects.equals(this.filename, other.filename)) {
            return false;
        }
        if (!Objects.equals(this.contenido, other.contenido)) {
            return false;
        }
        if (!Objects.equals(this.claveAcceso, other.claveAcceso)) {
            return false;
        }
        if (!Objects.equals(this.numeroAutorizacion, other.numeroAutorizacion)) {
            return false;
        }
        if (!Objects.equals(this.totalSinImpuestos, other.totalSinImpuestos)) {
            return false;
        }
        if (!Objects.equals(this.totalIVA0, other.totalIVA0)) {
            return false;
        }
        if (!Objects.equals(this.totalIVA12, other.totalIVA12)) {
            return false;
        }
        if (!Objects.equals(this.totalDescuento, other.totalDescuento)) {
            return false;
        }
        if (!Objects.equals(this.importeTotal, other.importeTotal)) {
            return false;
        }
        if (!Objects.equals(this.fechaEmision, other.fechaEmision)) {
            return false;
        }
        if (!Objects.equals(this.fechaAutorizacion, other.fechaAutorizacion)) {
            return false;
        }
        if (this.sourceType != other.sourceType) {
            return false;
        }
        if (this.emissionType != other.emissionType) {
            return false;
        }
        if (!Objects.equals(this.organization, other.organization)) {
            return false;
        }
        if (!Objects.equals(this.payments, other.payments)) {
            return false;
        }
        if (!Objects.equals(this.fechaVencimiento, other.fechaVencimiento)) {
            return false;
        }
        if (!Objects.equals(this.ultimaFechaPago, other.ultimaFechaPago)) {
            return false;
        }
        if (!Objects.equals(this.facturaElectronicaDetails, other.facturaElectronicaDetails)) {
            return false;
        }
        if (this.documentType != other.documentType) {
            return false;
        }
        if (!Objects.equals(this.subtotalIVA0, other.subtotalIVA0)) {
            return false;
        }
        return Objects.equals(this.subtotalIVA12, other.subtotalIVA12);
    }

    @Transient
    public String getSummary() {
        List<FacturaElectronicaDetail> list = getFacturaElectronicaDetails();
        Collections.sort(list);
        Collections.reverse(list);
        return Lists.toString(list, ", ");
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }
}
