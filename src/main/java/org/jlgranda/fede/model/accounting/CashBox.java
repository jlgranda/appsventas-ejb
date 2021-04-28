/*
 * Copyright (C) 2021 kellypaulinc
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
 * @author kellypaulinc
 */
@Entity
@Table(name = "CashBox")
@NamedQueries({
    @NamedQuery(name = "CashBox.findByName", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    @NamedQuery(name = "CashBox.findByNameAndOwner", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
    @NamedQuery(name = "CashBox.findByCashBoxGeneral", query = "SELECT s FROM CashBox s WHERE s.cashBoxGeneral = ?1 ORDER BY 1"),
    @NamedQuery(name = "CashBox.findByCashBoxGeneralAndOwner", query = "SELECT s FROM CashBox s WHERE s.cashBoxGeneral = ?1 and s.owner = ?2 ORDER BY 1"),
    @NamedQuery(name = "CashBox.findByCashBoxGeneralAndStatus", query = "SELECT s FROM CashBox s WHERE s.cashBoxGeneral = ?1 and s.statusCashBox = ?2 ORDER BY 1"),
    @NamedQuery(name = "CashBox.findByCashBoxGeneralAndStatusAndId", query = "SELECT s FROM CashBox s WHERE s.cashBoxGeneral = ?1 and s.statusCashBox = ?2 and s.id <> ?3 ORDER BY 1"),
})
public class CashBox extends PersistentObject<CashBox> implements Comparable<CashBox>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "cashBoxGeneral_id", insertable = true, updatable = true, nullable = true)
    private CashBoxGeneral cashBoxGeneral;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBox", fetch = FetchType.LAZY)
    private List<CashBoxDetail> cashBoxDetails = new ArrayList<>();

    BigDecimal cashPartial;

    BigDecimal saldoPartial;

    BigDecimal missCashPartial;

    BigDecimal excessCashPartial;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private CashBox.Status statusCashBox;

    public enum Status {
        OPEN,
        CLOSED;
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
        cashBoxDetail.setCashBox(this);
        if (this.cashBoxDetails.contains(cashBoxDetail)) {
            replaceCashBoxDetail(cashBoxDetail);
        } else {
            this.cashBoxDetails.add(cashBoxDetail);
        }
    }

    public CashBoxDetail replaceCashBoxDetail(CashBoxDetail cashBoxDetail) {
        System.out.println("\ngetCashboxDetails(): " + getCashBoxDetails());
        getCashBoxDetails().set(getCashBoxDetails().indexOf(cashBoxDetail), cashBoxDetail);
        System.out.println("\ngetCashboxDetails(): " + getCashBoxDetails());
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

    public Status getStatusCashBox() {
        return statusCashBox;
    }

    public void setStatusCashBox(Status statusCashBox) {
        this.statusCashBox = statusCashBox;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31); // two randomly chosen prime numbers
        // if deriving: appendSuper(super.hashCode()).
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
        CashBox other = (CashBox) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(getId(), other.getId());
        return eb.isEquals();
    }

    @Override
    public int compareTo(CashBox other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

}
