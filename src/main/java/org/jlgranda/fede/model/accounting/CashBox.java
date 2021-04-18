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
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@NamedQuery(name = "CashBox.findByNameAndOwner", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 2"),
@NamedQuery(name = "CashBox.findByNameAndOrg", query = "SELECT s FROM CashBox s WHERE s.name = ?1 and s.organization = ?2 ORDER BY 2"),
@NamedQuery(name = "CashBox.findByCreatedOnAndOrg", query = "SELECT s FROM CashBox s WHERE s.createdOn>= ?1 and s.createdOn <= ?2 and s.organization = ?2 ORDER BY 2"),
})
public class CashBox extends PersistentObject<CashBox> implements Comparable <CashBox>, Serializable {
    
    @ManyToOne (optional = false, cascade = (CascadeType.ALL))
    @JoinColumn (name = "account_id", insertable = true, updatable = true, nullable = true)
    Account account;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cashBox", fetch = FetchType.LAZY)
    private List<CashBoxDetail> cashboxDetails = new ArrayList<>();
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "emissionDate")
    private Date emissionDate;
            
    BigDecimal amount;
    
    BigDecimal saldo;

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


    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
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

//    public void addRecordDetailNew(RecordDetail recordDetail) {
//        recordDetail.setRecord(this);
//        this.recordDetails.add(recordDetail);
//    }
//    
//    public void addRecordDetail(RecordDetail recordDetail) {
//        recordDetail.setRecord(this);
//        if(this.recordDetails.contains(recordDetail)){
//            replaceRecordDetail(recordDetail);
//        }else{
//            this.recordDetails.add(recordDetail);
//        }
//    }
//    
//    public RecordDetail replaceRecordDetail(RecordDetail recordDetail){
//        getRecordDetails().set(getRecordDetails().indexOf(recordDetail), recordDetail);
//        return recordDetail;
//    }
//    

//    @Override
//    public int hashCode() {
//        HashCodeBuilder hcb = new HashCodeBuilder(17, 31); // two randomly chosen prime numbers
//        // if deriving: appendSuper(super.hashCode()).
//        hcb.append(getId());
//
//        return hcb.toHashCode();
//    }
//
//    @Override
//    public boolean equals(final Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        Record other = (Record) obj;
//        EqualsBuilder eb = new EqualsBuilder();
//        
//        eb.append(getId(), other.getId());
//        return eb.isEquals();
//    }
//    
//    @Override
//    public int compareTo(Record other) {
//        return this.createdOn.compareTo(other.getCreatedOn());
//    }
//
//    
//}
