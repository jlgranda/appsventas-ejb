/*
 * Copyright (C) 2021 author
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
package org.jlgranda.fede.model.accounting;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author author
 */
@Entity
@Table(name = "CashBox_Partial")
@NamedQueries({
    @NamedQuery(name = "CashBoxPartial.findByName", query = "SELECT s FROM CashBoxPartial s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    @NamedQuery(name = "CashBoxPartial.findByNameAndOwner", query = "SELECT s FROM CashBoxPartial s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneral", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 ORDER BY 1"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneralAndOwner", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.owner = ?2 ORDER BY s.id DESC"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneralAndOwnerAndPriorityOrder", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.owner = ?2  and s.priority_order = ?3 ORDER BY s.id DESC"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneralAndStatus", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.statusCashBoxPartial = ?2 ORDER BY s.id DESC"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneralAndStatusAndStatusPriority", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.statusCashBoxPartial = ?2 and s.status_priority = ?3 ORDER BY s.id DESC"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneralAndStatusAndId", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.statusCashBoxPartial = ?2 and s.id <> ?3 ORDER BY 1"),
    @NamedQuery(name = "CashBoxPartial.countCashBoxPartialByCashBoxPriorityOrder", query = "SELECT COUNT(s) FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.priority_order = ?2"),
    @NamedQuery(name = "CashBoxPartial.countCashBoxPartialByCashBoxGeneral", query = "SELECT COUNT(s) FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1"),
    @NamedQuery(name = "CashBoxPartial.findByPriority", query = "SELECT s FROM CashBoxPartial s WHERE s.priority = ?1"),
})
public class CashBoxPartial extends PersistentObject<CashBoxPartial> implements Comparable<CashBoxPartial>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cashBoxGeneral_id", insertable = true, updatable = true, nullable = true)
    private CashBoxGeneral cashBoxGeneral;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBoxPartial", fetch = FetchType.LAZY)
    private List<CashBoxDetail> cashBoxDetails = new ArrayList<>();

    BigDecimal cashPartial; //Todo el dinero en efectivo que se tenía antes de hacer un depósito (si hubiese, sería diferente al saldoPartial).

    BigDecimal saldoPartial; //Dinero que queda luego de hacer un depósito y el que se va a desglosar.

    BigDecimal totalcashBills; //Subtotal de billetes.

    BigDecimal totalcashMoneys; //Subtotal de monedas.

    BigDecimal totalCashBreakdown; //Total de dinero desglosado (Suma de billetes y monedas).

    BigDecimal missCashPartial; //Dinero faltante.

    BigDecimal excessCashPartial; //Dinero sobrante.
    
    BigDecimal amountDeposit; //Dinero de depósito.
    
    Account accountDeposit; //Cuenta de depósito.

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CashBoxPartial.Status statusCashBoxPartial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CashBoxPartial.Priority priority_order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CashBoxPartial.StatusPriority status_priority;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CashBoxPartial.Verification verification_TotalBreakdown;
    
    public enum Status {
        OPEN,
        CLOSED;
    }

    public enum Priority {
        BASIC,
        MAIN,
        SECONDARY;
    }
    
    public enum StatusPriority{
        INTERMEDIATE,
        FINAL;
    }
    
    public enum Verification{
        CORRECT,
        INCORRECT,
        NOT_VERIFIED;
    }
            
    public CashBoxGeneral getCashBoxGeneral() {
        return cashBoxGeneral;
    }

    public void setCashBoxGeneral(CashBoxGeneral cashBoxGeneral) {
        this.cashBoxGeneral = cashBoxGeneral;
    }

    public List<CashBoxDetail> getCashBoxDetails() {
        return cashBoxDetails;
    }

    public void setCashBoxDetails(List<CashBoxDetail> cashBoxDetails) {
        this.cashBoxDetails = cashBoxDetails;
    }

    public void addCashBoxDetail(CashBoxDetail cashBoxDetail) {
        cashBoxDetail.setCashBoxPartial(this);
        if (this.cashBoxDetails.contains(cashBoxDetail)) {
            replaceCashBoxDetail(cashBoxDetail);
        } else {
            this.cashBoxDetails.add(cashBoxDetail);
        }
    }

    public CashBoxDetail replaceCashBoxDetail(CashBoxDetail cashBoxDetail) {
        getCashBoxDetails().set(getCashBoxDetails().indexOf(cashBoxDetail), cashBoxDetail);

        return cashBoxDetail;
    }

    public BigDecimal getCashPartial() {
        return cashPartial;
    }

    public void setCashPartial(BigDecimal cashPartial) {
        this.cashPartial = cashPartial;
    }

    public BigDecimal getSaldoPartial() {
        return saldoPartial;
    }

    public void setSaldoPartial(BigDecimal saldoPartial) {
        this.saldoPartial = saldoPartial;
    }

    public BigDecimal getTotalcashBills() {
        return totalcashBills;
    }

    public void setTotalcashBills(BigDecimal totalcashBills) {
        this.totalcashBills = totalcashBills;
    }

    public BigDecimal getTotalcashMoneys() {
        return totalcashMoneys;
    }

    public void setTotalcashMoneys(BigDecimal totalcashMoneys) {
        this.totalcashMoneys = totalcashMoneys;
    }

    public BigDecimal getTotalCashBreakdown() {
        return totalCashBreakdown;
    }

    public void setTotalCashBreakdown(BigDecimal totalCashBreakdown) {
        this.totalCashBreakdown = totalCashBreakdown;
    }

    public BigDecimal getMissCashPartial() {
        return missCashPartial;
    }

    public void setMissCashPartial(BigDecimal missCashPartial) {
        this.missCashPartial = missCashPartial;
    }

    public BigDecimal getExcessCashPartial() {
        return excessCashPartial;
    }

    public void setExcessCashPartial(BigDecimal excessCashPartial) {
        this.excessCashPartial = excessCashPartial;
    }

    public BigDecimal getAmountDeposit() {
        return amountDeposit;
    }

    public void setAmountDeposit(BigDecimal amountDeposit) {
        this.amountDeposit = amountDeposit;
    }

    public Account getAccountDeposit() {
        return accountDeposit;
    }

    public void setAccountDeposit(Account accountDeposit) {
        this.accountDeposit = accountDeposit;
    }

    public Status getStatusCashBoxPartial() {
        return statusCashBoxPartial;
    }

    public void setStatusCashBoxPartial(Status statusCashBoxPartial) {
        this.statusCashBoxPartial = statusCashBoxPartial;
    }

    public Priority getPriority_order() {
        return priority_order;
    }

    public void setPriority_order(Priority priority_order) {
        this.priority_order = priority_order;
    }

    public StatusPriority getStatus_priority() {
        return status_priority;
    }

    public void setStatus_priority(StatusPriority status_priority) {
        this.status_priority = status_priority;
    }

    public Verification getVerification_TotalBreakdown() {
        return verification_TotalBreakdown;
    }

    public void setVerification_TotalBreakdown(Verification verification_TotalBreakdown) {
        this.verification_TotalBreakdown = verification_TotalBreakdown;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31);
        hcb.append(getId());
        return hcb.toHashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        CashBoxPartial other = (CashBoxPartial) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(getId(), other.getId());

        return eb.isEquals();
    }

    @Override
    public int compareTo(CashBoxPartial other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

}