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
@Table(name = "caja_parcial")
@NamedQueries({
    @NamedQuery(name = "CashBoxPartial.findByOrganizationAndLastId", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral.organization = ?1 ORDER BY s.id DESC"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneral", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 ORDER BY s.createdOn DESC"),
    @NamedQuery(name = "CashBoxPartial.findByCashBoxGeneralAndPriority", query = "SELECT s FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1 and s.priority = ?2 ORDER BY s.priority DESC"),
    @NamedQuery(name = "CashBoxPartial.countCashBoxPartialByCashBoxGeneral", query = "SELECT COUNT(s) FROM CashBoxPartial s WHERE s.cashBoxGeneral = ?1"),})
public class CashBoxPartial extends PersistentObject<CashBoxPartial> implements Comparable<CashBoxPartial>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cashBoxGeneral_id", insertable = true, updatable = true, nullable = true)
    private CashBoxGeneral cashBoxGeneral;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBoxPartial", fetch = FetchType.LAZY)
    private List<CashBoxDetail> cashBoxDetails = new ArrayList<>();

    private BigDecimal cashPartial; //Todo el dinero en efectivo recolectado hasta el momento de hacer el registro de billetes/monedas.
    private BigDecimal totalCashBills; //Subtotal de billetes.
    private BigDecimal totalCashMoneys; //Subtotal de monedas.
    private BigDecimal totalCashBreakdown; //Total de dinero desglosado.
    private BigDecimal missCash; //Dinero faltante.
    private BigDecimal excessCash; //Dinero sobrante.

    private BigDecimal amountDeposit; //Dinero de depósito.
    @ManyToOne()
    private Account accountDeposit; //Cuenta de depósito.
    private BigDecimal cashFinally; //Dinero en efectivo final que queda luego de haber registrado y haber hecho un depósito (si no hay deposito es igual que el totalCashBreakdown).

    private Boolean statusComplete; //Marca si el cashBoxPartial está cerrado/completo;
    private Boolean statusFinally; //Marca si el cashBoxPartial es el final del día;

    @Enumerated(EnumType.STRING) //Marca el tipo de verificación del cashBoxPartial
    @Column(nullable = true)
    private CashBoxPartial.Verification typeVerification;

    public enum Verification {
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

    public BigDecimal getCashPartial() {
        return cashPartial;
    }

    public void setCashPartial(BigDecimal cashPartial) {
        this.cashPartial = cashPartial;
    }

    public BigDecimal getTotalCashBills() {
        return totalCashBills;
    }

    public void setTotalCashBills(BigDecimal totalCashBills) {
        this.totalCashBills = totalCashBills;
    }

    public BigDecimal getTotalCashMoneys() {
        return totalCashMoneys;
    }

    public void setTotalCashMoneys(BigDecimal totalCashMoneys) {
        this.totalCashMoneys = totalCashMoneys;
    }

    public BigDecimal getTotalCashBreakdown() {
        return totalCashBreakdown;
    }

    public void setTotalCashBreakdown(BigDecimal totalCashBreakdown) {
        this.totalCashBreakdown = totalCashBreakdown;
    }

    public BigDecimal getMissCash() {
        return missCash;
    }

    public void setMissCash(BigDecimal missCash) {
        this.missCash = missCash;
    }

    public BigDecimal getExcessCash() {
        return excessCash;
    }

    public void setExcessCash(BigDecimal excessCash) {
        this.excessCash = excessCash;
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

    public BigDecimal getCashFinally() {
        return cashFinally;
    }

    public void setCashFinally(BigDecimal cashFinally) {
        this.cashFinally = cashFinally;
    }

    public Boolean getStatusComplete() {
        return statusComplete;
    }

    public void setStatusComplete(Boolean statusComplete) {
        this.statusComplete = statusComplete;
    }

    public Boolean getStatusFinally() {
        return statusFinally;
    }

    public void setStatusFinally(Boolean statusFinally) {
        this.statusFinally = statusFinally;
    }

    public Verification getTypeVerification() {
        return typeVerification;
    }

    public void setTypeVerification(Verification typeVerification) {
        this.typeVerification = typeVerification;
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
