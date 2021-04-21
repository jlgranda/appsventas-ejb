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
import javax.persistence.Entity;
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
@Table(name = "Cash_Box")
@NamedQueries({
@NamedQuery(name = "CashBox.findByName", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery(name = "CashBox.findByNameAndOwner", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
@NamedQuery(name = "CashBox.findByNameAndOrg", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.organization = ?2 ORDER BY 1"),
@NamedQuery(name = "CashBox.findByCreatedOnAndOrg", query = "SELECT s FROM CashBox s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?3 ORDER BY 1"),
})
public class CashBox extends PersistentObject<CashBox> implements Comparable <CashBox>, Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBox", fetch = FetchType.LAZY)
    private List<CashBoxDetail> cashboxDetails = new ArrayList<>();
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "account_id", insertable=true, updatable=true, nullable=true)
    private Account account;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;
    
    BigDecimal amountTotal;
    
    BigDecimal saldoCash;
    
    BigDecimal missingCash;
    
    BigDecimal excessCash;

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
    
    public List<CashBoxDetail> getCashboxDetails() {
        return cashboxDetails;
    }

    public void setCashboxDetails(List<CashBoxDetail> cashboxDetails) {
        this.cashboxDetails = cashboxDetails;
    }

    public void addCashBoxDetail(CashBoxDetail cashBoxDetail){
        cashBoxDetail.setCashBox(this);
        if(this.cashboxDetails.contains(cashBoxDetail)){
            replaceCashBoxDetail(cashBoxDetail);
        }else{
            this.cashboxDetails.add(cashBoxDetail);
        }
    }
    
    public CashBoxDetail replaceCashBoxDetail(CashBoxDetail cashBoxDetail){
        getCashboxDetails().set(getCashboxDetails().indexOf(cashBoxDetail), cashBoxDetail);
        return cashBoxDetail;
    }

    public BigDecimal getAmountTotal() {
        return amountTotal;
    }

    public void setAmountTotal(BigDecimal amountTotal) {
        this.amountTotal = amountTotal;
    }

    public BigDecimal getSaldoCash() {
        return saldoCash;
    }

    public void setSaldoCash(BigDecimal saldoCash) {
        this.saldoCash = saldoCash;
    }

    public BigDecimal getMissingCash() {
        return missingCash;
    }

    public void setMissingCash(BigDecimal missingCash) {
        this.missingCash = missingCash;
    }

    public BigDecimal getExcessCash() {
        return excessCash;
    }

    public void setExcessCash(BigDecimal excessCash) {
        this.excessCash = excessCash;
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