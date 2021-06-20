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
package org.jlgranda.fede.model.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.jlgranda.fede.model.document.DocumentType;
import org.jlgranda.fede.model.document.EmissionType;
import org.jlgranda.fede.model.document.EnvironmentType;
import org.jpapi.model.Organization;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.TaxType;
import org.jpapi.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Invoice document in fede
 * @author jlgranda
 */
@Entity
@Table(name = "INVOICE")
@DiscriminatorValue(value = "INV")
@PrimaryKeyJoinColumn(name = "invoiceId")
@NamedQueries({
    @NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i ORDER BY i.id DESC"),
    @NamedQuery(name = "Invoice.findByCode", query = "SELECT i FROM Invoice i WHERE i.code = ?1"),
    @NamedQuery(name = "Invoice.findByDocumentType", query = "SELECT i FROM Invoice i WHERE i.documentType = ?1 AND i.active=?2 AND i.emissionOn BETWEEN ?3 AND ?4 ORDER BY i.lastUpdate DESC"),
    @NamedQuery(name = "Invoice.findByDocumentTypeAndAuthor", query = "SELECT i FROM Invoice i WHERE i.documentType = ?1 and i.author = ?2  AND i.active=?3 AND i.emissionOn BETWEEN ?4 AND ?5 ORDER BY i.lastUpdate DESC"),
    @NamedQuery(name = "Invoice.findByDocumentTypeAndOrg", query = "SELECT i FROM Invoice i WHERE i.documentType = ?1 and i.organization = ?2  AND i.active=?3 AND i.emissionOn BETWEEN ?4 AND ?5 ORDER BY i.lastUpdate DESC"),
    @NamedQuery(name = "Invoice.findByDocumentTypeAndStatusAndOrg", query = "select i from Invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 ORDER BY i.lastUpdate DESC"),
    @NamedQuery(name = "Invoice.findByOrganizationAndDocumentTypeAndEmission", query = "select i from Invoice i WHERE i.organization=?1 and i.emissionOn BETWEEN ?2 AND ?3  and i.documentType=?4 ORDER BY i.emissionOn DESC"),
    @NamedQuery(name = "Invoice.findByOrganizationAndAuthorAndDocumentTypeAndEmission", query = "select i from Invoice i WHERE i.organization=?1 and i.author = ?2 and i.emissionOn BETWEEN ?3 AND ?4 and i.documentType=?5 ORDER BY i.emissionOn DESC"),
    @NamedQuery(name = "Invoice.findSequencialByDocumentTypeAndStatusAndOrg", query = "select i.sequencial from Invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 ORDER BY i.lastUpdate DESC"),
    @NamedQuery(name = "Invoice.findByDocumentTypeAndAuthorOrderByCode", query = "SELECT i FROM Invoice i WHERE i.documentType = ?1 and i.author = ?2  AND i.active=?3 AND i.emissionOn BETWEEN ?4 AND ?5 ORDER BY i.code DESC"),
    @NamedQuery(name = "Invoice.findByDocumentTypeAndOwner", query = "SELECT i FROM Invoice i WHERE i.documentType = ?1 AND i.owner = ?2 AND i.active=?3 AND i.emissionOn BETWEEN ?4 AND ?5 ORDER BY i.id DESC"),
    @NamedQuery(name = "Invoice.countByDocumentTypeAndOwner", query = "SELECT count(i) FROM Invoice i WHERE i.documentType = ?1 and i.owner = ?2 AND i.active=?3 AND i.emissionOn BETWEEN ?4 AND ?5"),
    @NamedQuery(name = "Invoice.countByDocumentTypeAndAuthor", query = "SELECT count(i) FROM Invoice i WHERE i.documentType = ?1 and i.author = ?2 AND i.active=?3"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesPaxBetween", query = "select sum(i.pax) from Invoice i WHERE i.author=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesPaxBetweenOrg", query = "select sum(i.pax) from Invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesPaxEmissionBetween", query = "select i.pax, i.emissionOn from Invoice i WHERE i.author=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesDiscountBetween", query = "select sum(p.amount), sum(p.discount), sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.author=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesDiscountBetweenOrg", query = "select sum(p.amount), sum(p.discount), sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesPaymentDateDiscountBetweenOrg", query = "select sum(p.amount), sum(p.discount), sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and p.datePaymentCancel >= ?4 and p.datePaymentCancel <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesMethodBetweenOrg", query = "select sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5 and p.method = ?6"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesMethodPaymentDateBetweenOrg", query = "select sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and p.datePaymentCancel >= ?4 and p.datePaymentCancel <= ?5 and p.method = ?6"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesSourceMethodPaymentDateBetweenOrg", query = "select sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.organization=?1 and i.documentTypeSource=?2 and i.status=?3 and p.datePaymentCancel >= ?4 and p.datePaymentCancel <= ?5 and p.method = ?6"),
    @NamedQuery(name = "Invoice.findTotalInvoiceBussinesSalesDiscountBetween", query = "select i.code, i.boardNumber, i.emissionOn, p.amount, p.discount, i.id from Payment p LEFT JOIN p.invoice i WHERE i.author=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5 and p.discount> ?6"),
    @NamedQuery(name = "Invoice.findTotalInvoiceBussinesSalesDiscountBetweenOrg", query = "select i.code, i.boardNumber, i.emissionOn, p.amount, p.discount, i.id from Payment p LEFT JOIN p.invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5 and p.discount> ?6"),
    @NamedQuery(name = "Invoice.countTotalInvoiceBetween", query = "select count(i) from Invoice i WHERE i.author=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.countTotalInvoiceBetweenOrg", query = "select count(i) from Invoice i WHERE i.organization=?1 and i.documentType=?2 and i.status=?3 and i.emissionOn >= ?4 and i.emissionOn <= ?5"),
    @NamedQuery(name = "Invoice.findTotalInvoiceSalesDiscountByOwnerBetween", query = "select sum(p.amount), sum(p.discount), sum(p.amount-p.discount) from Payment p LEFT JOIN p.invoice i WHERE i.author=?1 and i.owner=?2 and i.documentType=?3 and i.status=?4 and i.emissionOn >= ?5 and i.emissionOn <= ?6"),
})

public class Invoice extends BussinesEntity {
    
    private static Logger log = LoggerFactory.getLogger(Invoice.class);
    
    private static final long serialVersionUID = 2087202727290952436L;
    
    public static final double IVA=0d;
    
    /**
     * Número de mesa en la que se generó en invoice.
     */
    private String boardNumber;
    
    private EnvironmentType environmentType;
    
    private EmissionType emissionType;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "emissionOn")
    private Date emissionOn;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;
    
    private DocumentType documentType;
    
    private DocumentType documentTypeSource;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "establishment_id", insertable=false, updatable=false, nullable=true)
    private Establishment establishment;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "emissionpoint_id", insertable=false, updatable=false, nullable=true)
    private EmissionPoint emissionPoint;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<Detail> details = new ArrayList<>();
    
    private String sequencial;
    
    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<Payment> payments = new ArrayList<>();
    
    @Column(nullable = true)
    private Boolean printAlias;
    
    @Column(nullable = true, length = 1024)
    protected String printAliasSummary;
    
    @Column(nullable = true, length = 1024)
    protected Long pax;

    public String getBoardNumber() {
        return boardNumber;
    }

    public void setBoardNumber(String boardNumber) {
        this.boardNumber = boardNumber;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public EmissionType getEmissionType() {
        return emissionType;
    }

    public void setEmissionType(EmissionType emissionType) {
        this.emissionType = emissionType;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentCode) {
        this.documentType = documentCode;
    }

    public DocumentType getDocumentTypeSource() {
        return documentTypeSource;
    }

    public void setDocumentTypeSource(DocumentType documentTypeSource) {
        this.documentTypeSource = documentTypeSource;
    }

    public Establishment getEstablishment() {
        return establishment;
    }

    public void setEstablishment(Establishment establishment) {
        this.establishment = establishment;
    }

    public EmissionPoint getEmissionPoint() {
        return emissionPoint;
    }

    public void setEmissionPoint(EmissionPoint emissionPoint) {
        this.emissionPoint = emissionPoint;
    }

    public Date getEmissionOn() {
        return emissionOn;
    }

    public void setEmissionOn(Date emissionOn) {
        this.emissionOn = emissionOn;
    }

    public Detail addDetail(Detail detail){
        detail.setInvoice(this);
        if (this.details.contains(detail)){
            replaceDetail(detail);
        } else {
            this.details.add(detail);
        }
        return detail;
    }
    
    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public String getSequencial() {
        return sequencial;
    }

    public void setSequencial(String sequencial) {
        this.sequencial = sequencial;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    
    public Payment addPayment(Payment payment){
        payment.setInvoice(this);
        if (this.payments.contains(payment)){
            replacePayment(payment);
        } else {
            this.payments.add(payment);
        }
        return payment;
    }

    public Boolean getPrintAlias() {
        return printAlias;
    }

    public void setPrintAlias(Boolean printAlias) {
        this.printAlias = printAlias;
    }

    public String getPrintAliasSummary() {
        return printAliasSummary;
    }

    public void setPrintAliasSummary(String printAliasSummary) {
        this.printAliasSummary = printAliasSummary;
    }
    @Transient
    public Long getPax() {
        return pax;
    }

    public void setPax(Long pax) {
        this.pax = pax;
    }
    
    @Transient
    public String getSummary(){
        List<Detail> list = getDetails();
        Collections.sort(list);
        Collections.reverse(list);
        return Lists.toString(list, ", ");
    }
    
    
    /**
     * Calcula el subtotal del detalle de la factura de venta
     * @return 
     */
    @Transient
    public BigDecimal getTotalSinImpuesto(){
        BigDecimal total = new BigDecimal(0);
        for (Detail d : getDetails()){
            total = total.add(d.getPrice().multiply(BigDecimal.valueOf(d.getAmount())));
        }
        
        return total;
    }
    /**
     * Calcula el subtotal del detalle de la factura de venta
     * @return 
     */
    @Transient
    public BigDecimal getTotal(){
        return getTotalSinImpuesto().add(getTotalTax(TaxType.IVA));
    }
    
    /**
     * Calcula el subtotal del detalle de la factura de venta por tipo de impuesto
     * @param taxType
     * @return 
     */
    public BigDecimal getTotalTax(TaxType taxType){
        BigDecimal total = new BigDecimal(0);
        for (Detail d : getDetails()){
            if (taxType.equals(d.getProduct().getTaxType())){
                //TODO identificar el porcentaje en función del tipo de impuesto
                total = total.add(d.getPrice().multiply(BigDecimal.valueOf(d.getAmount())).multiply(BigDecimal.valueOf(IVA)));
            }
        }
        
        return total;
    }
    
    @Transient
    public BigDecimal getPaymentsDiscount(){
        return getPaymentsField("discount");
    }
    
    @Transient
    public BigDecimal getPaymentsAmount(){
        return getPaymentsField("amount");
    }
    
    @Transient
    public BigDecimal getPaymentsCash(){
        return getPaymentsField("cash");
    }
    
    @Transient
    public BigDecimal getPaymentsChange(){
        return getPaymentsField("change");
    }
    
    @Transient
    public BigDecimal getPaymentsField(String field) {
        BigDecimal total = new BigDecimal(0);
        for (Payment p : getPayments()) {
            if (null != field) switch (field) {
                case "amount":
                    total = total.add(p.getAmount());
                    break;
                case "cash":
                    total = total.add(p.getCash());
                    break;
                case "change":
                    total = total.add(p.getChange());
                    break;
                case "discount":
                    total = total.add(p.getDiscount());
                    break;
                default:
                    break;
            }
        }

        return total;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.documentType);
        hash = 59 * hash + Objects.hashCode(this.emissionPoint);
        hash = 59 * hash + Objects.hashCode(this.sequencial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (this.documentType != other.documentType) {
            return false;
        }
        if (!Objects.equals(this.emissionPoint, other.emissionPoint)) {
            return false;
        }
        if (!Objects.equals(this.sequencial, other.sequencial)) {
            return false;
        }
        return true;
    }

    public Detail replaceDetail(Detail detail) {
    
        getDetails().set(getDetails().indexOf(detail), detail);
        return detail;
    }
    
    public Payment replacePayment(Payment payment) {
    
        getPayments().set(getPayments().indexOf(payment), payment);
        return payment;
    }

}
