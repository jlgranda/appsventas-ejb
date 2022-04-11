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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jpapi.model.Organization;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author author
 */
@Entity
@Table(name = "caja_general")
@NamedQueries({
    //@NamedQuery (name="CashBoxGeneral.findByName", query = "SELECT s FROM CashBoxGeneral s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    //@NamedQuery (name="CashBoxGeneral.findByNameAndOwner", query = "SELECT s FROM CashBoxGeneral s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
    //@NamedQuery (name="CashBoxGeneral.findByNameAndOrg", query = "SELECT s FROM CashBoxGeneral s WHERE s.name = ?1 and s.organization = ?2 ORDER BY 1"),
    //@NamedQuery (name="CashBoxGeneral.findByCreatedOnAndOrg", query = "SELECT s FROM CashBoxGeneral s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?3 ORDER BY s.id DESC"),
    //@NamedQuery (name="CashBoxGeneral.findSaldoByCreatedOnAndOrg", query = "SELECT s.cashFinally FROM CashBoxGeneral s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?3 ORDER BY 1"),
    //@NamedQuery (name="CashBoxGeneral.findTotalBreakdownFundByCreatedOnAndOrg", query = "SELECT s.totalBreakdown FROM CashBoxGeneral s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?3 ORDER BY 1"),
    //@NamedQuery (name="CashBoxGeneral.findTotalBreakdownFundByLastCreatedOnAndOrg", query = "SELECT s.totalBreakdown FROM CashBoxGeneral s WHERE s.organization = ?1 and s.createdOn < ?2 ORDER BY s.createdOn DESC"),
    //@NamedQuery (name="CashBoxGeneral.findCashBoxGeneralByLastCreatedOnAndOrg", query = "SELECT s FROM CashBoxGeneral s WHERE s.organization = ?1 and s.createdOn < ?2 ORDER BY s.createdOn DESC"),
    @NamedQuery(name = "CashBoxGeneral.findCashBoxGeneralByOrganizationAndCreatedOn", query = "SELECT s FROM CashBoxGeneral s WHERE s.organization = ?1 and s.createdOn >= ?2 and s.createdOn <= ?3 ORDER BY s.createdOn DESC"),
    @NamedQuery(name = "CashBoxGeneral.findCashBoxGeneralByOrganizationAndLastCreatedOn", query = "SELECT s FROM CashBoxGeneral s WHERE s.organization = ?1 and s.createdOn < ?2 ORDER BY s.createdOn DESC"),})
public class CashBoxGeneral extends PersistentObject<CashBoxGeneral> implements Comparable<CashBoxGeneral>, Serializable {

    @ManyToOne(optional = false)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true, nullable = true)
    private Organization organization;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBoxGeneral", fetch = FetchType.LAZY)
    @OrderBy(value = "createdOn DESC")
    private List<CashBoxPartial> cashBoxPartials = new ArrayList<>();

    private BigDecimal cashGeneral; //Todo el dinero en efectivo recolectado hasta el momento de cerrar el cashBoxGeneral;
    private BigDecimal totalBreakdown; //EL dinero último que se desglosó.
    private BigDecimal missCash; //El dinero faltante final.
    private BigDecimal excessCash; //El dinero sobrante final.
    private BigDecimal cashFinally; //Dinero en efectivo final que queda luego de haber registrado y haber hecho un depósito (si no hay deposito es igual que el totalBreakdown).
    private Boolean statusComplete; //Marca si el cashBoxGeneral está cerrado/completo;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<CashBoxPartial> getCashBoxPartials() {
        return cashBoxPartials;
    }

    public void setCashBoxPartials(List<CashBoxPartial> cashBoxPartials) {
        this.cashBoxPartials = cashBoxPartials;
    }

    public BigDecimal getCashGeneral() {
        return cashGeneral;
    }

    public void setCashGeneral(BigDecimal cashGeneral) {
        this.cashGeneral = cashGeneral;
    }

    public BigDecimal getTotalBreakdown() {
        return totalBreakdown;
    }

    public void setTotalBreakdown(BigDecimal totalBreakdown) {
        this.totalBreakdown = totalBreakdown;
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

    public CashBoxPartial addCashBoxPartial(CashBoxPartial cashBoxPartial) {
        cashBoxPartial.setCashBoxGeneral(this);
        if (this.cashBoxPartials.contains(cashBoxPartial)) {
            replaceCashBoxPartial(cashBoxPartial);
        } else {
            this.cashBoxPartials.add(cashBoxPartial);
        }
        return cashBoxPartial;
    }

    public CashBoxPartial replaceCashBoxPartial(CashBoxPartial cashBoxPartial) {
        getCashBoxPartials().set(getCashBoxPartials().indexOf(cashBoxPartial), cashBoxPartial);
        return cashBoxPartial;
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
        CashBoxGeneral other = (CashBoxGeneral) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(getId(), other.getId());
        return eb.isEquals();
    }

    @Override
    public int compareTo(CashBoxGeneral other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

}
