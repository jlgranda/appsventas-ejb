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
import org.jpapi.model.Organization;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author kellypaulinc
 */
@Entity
@Table(name = "CashBox_General")
@NamedQueries({ @NamedQuery (name="CashBoxGeneral.findByName", query = "SELECT s FROM CashBoxGeneral s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery (name="CashBoxGeneral.findByNameAndOwner", query = "SELECT s FROM CashBoxGeneral s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
@NamedQuery (name="CashBoxGeneral.findByNameAndOrg", query = "SELECT s FROM CashBoxGeneral s WHERE s.name = ?1 and s.organization = ?2 ORDER BY 1"),
@NamedQuery (name="CashBoxGeneral.findByCreatedOnAndOrg", query = "SELECT s FROM CashBoxGeneral s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?3 ORDER BY 1"),
@NamedQuery (name="CashBoxGeneral.findSaldoByCreatedOnAndOrg", query = "SELECT s.saldoFinal FROM CashBoxGeneral s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?3 ORDER BY 1"),
})
public class CashBoxGeneral extends PersistentObject<CashBoxGeneral> implements Comparable <CashBoxGeneral>, Serializable {
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "account_id", insertable=true, updatable=true, nullable=true)
    private Account account;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBoxGeneral", fetch = FetchType.LAZY)
    private List<CashBox> cashBoxs = new ArrayList<>();
    
    BigDecimal cashFinal;
    
    BigDecimal saldoFinal;
    
    BigDecimal missCashFinal;
    
    BigDecimal excessCashFinal;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)    
    private CashBoxGeneral.Status statusCashBoxGeneral;

    public enum Status {
        OPEN,
        CLOSED;
    }
    
    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<CashBox> getCashBoxs() {
        return cashBoxs;
    }

    public void setCashBoxs(List<CashBox> cashBoxs) {
        this.cashBoxs = cashBoxs;
    }
    
    public CashBox addCashBox(CashBox cashBox){
        cashBox.setCashBoxGeneral(this);
        if(this.cashBoxs.contains(cashBox)){
            replaceCashBox(cashBox);
        }else{
            this.cashBoxs.add(cashBox);
        }
        return cashBox;
    }
    
    public CashBox replaceCashBox(CashBox cashBox){
        getCashBoxs().set(getCashBoxs().indexOf(cashBox), cashBox);
        return cashBox;
    }
    
    public BigDecimal getCashFinal() {
        return cashFinal;
    }

    public void setCashFinal(BigDecimal cashFinal) {
        this.cashFinal = cashFinal;
    }

    public BigDecimal getSaldoFinal() {
        return saldoFinal;
    }

    public void setSaldoFinal(BigDecimal saldoFinal) {
        this.saldoFinal = saldoFinal;
    }

    public BigDecimal getMissCashFinal() {
        return missCashFinal;
    }

    public void setMissCashFinal(BigDecimal missCashFinal) {
        this.missCashFinal = missCashFinal;
    }

    public BigDecimal getExcessCashFinal() {
        return excessCashFinal;
    }

    public void setExcessCashFinal(BigDecimal excessCashFinal) {
        this.excessCashFinal = excessCashFinal;
    }

    public Status getStatusCashBoxGeneral() {
        return statusCashBoxGeneral;
    }

    public void setStatusCashBoxGeneral(Status statusCashBoxGeneral) {
        this.statusCashBoxGeneral = statusCashBoxGeneral;
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
