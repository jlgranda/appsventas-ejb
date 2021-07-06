/*
 * Copyright (C) 2021 jlgranda
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

/**
 *
 * @author jlgranda
 */


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.jpapi.model.DeletableObject;

@Entity
@Table(name = "Record_Detail")
@NamedQueries({ @NamedQuery(name = "RecordDetail.findByName", query = "select s FROM RecordDetail s WHERE s.name = ?1 and s.owner is null  and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findByNameAndOwner", query = "select s FROM RecordDetail s WHERE s.name = ?1 and s.owner = ?2  and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findByRecordAndAccount", query = "select s FROM RecordDetail s WHERE s.record =?1 and s.account = ?2  and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findByTopAccountAndOrg", query = "select s FROM RecordDetail s, GeneralJournal j WHERE s.account = ?1 and s.createdOn <=?2 and s.record.generalJournalId = j.id and  j.organization = ?3  and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findByTopAccAndOrg", query = "select s.createdOn, s.record.description, s.amount, s.recordDetailType, s.id FROM RecordDetail s, GeneralJournal j  WHERE s.account = ?1 and s.createdOn >=?2 and s.createdOn <=?3 and s.record.generalJournalId = j.id and  j.organization = ?4  and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findByAccountAndOrganization", query = "select s FROM RecordDetail s, GeneralJournal j  WHERE s.account = ?1 and s.createdOn >=?2 and s.createdOn <=?3 and s.record.generalJournalId = j.id and j.organization = ?4  and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findTotalByAccountAndType", query = "select sum(s.amount) FROM RecordDetail s, GeneralJournal j WHERE s.account = ?1 and s.recordDetailType= ?2 and s.createdOn >=?3 and s.createdOn <=?4 and s.record.generalJournalId = j.id and j.organization = ?5 and s.deleted = false ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findTotalByAccountAndTypeAndNotClassInvoiceFacturaElectronica", query = "select sum(s.amount) FROM RecordDetail s, GeneralJournal j WHERE s.account = ?1 and s.recordDetailType= ?2 and s.createdOn >=?3 and s.createdOn <=?4 and s.record.generalJournalId = j.id and j.organization = ?5 and (s.record.bussinesEntityType IS NULL or (s.record.bussinesEntityType<>'Invoice' and s.record.bussinesEntityType<>'FacturaElectronica')) and s.deleted = false ORDER BY 1"),
})
public class RecordDetail extends DeletableObject<RecordDetail> implements Comparable<RecordDetail>, Serializable {

    /*
    Tipo de recordDetail
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)    
    private RecordDetail.RecordTDetailType recordDetailType;
    
    /**
     * Id de la entidad de negocio involucrada en el detalle de registro
     */
    Long bussineEntityId;
    
    BigDecimal amount;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "account_id", insertable=true, updatable=true, nullable=true)
    private Account account;
    
    @ManyToOne (optional = false, cascade = {CascadeType.ALL})
    @JoinColumn (name = "record_id", insertable = true, updatable = true, nullable = true)
    Record record;
    
    //Campos para instanciación desde Rules
    @Transient
    private String accountId;
    
    @Transient
    private String accountCode;
    
    @Transient
    private String accountName;
    
    public enum RecordTDetailType {
        DEBE,
        HABER;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    public RecordTDetailType getRecordDetailType() {
        return recordDetailType;
    }

    public void setRecordDetailType(RecordTDetailType recordDetailType) {
        this.recordDetailType = recordDetailType;
    }
    
    public void setRecordDetailTypeFromName(String recordDetailTypeName) {
        this.recordDetailType = RecordTDetailType.valueOf(recordDetailTypeName);
    }

    public Long getBussineEntityId() {
        return bussineEntityId;
    }

    public void setBussineEntityId(Long bussineEntityId) {
        this.bussineEntityId = bussineEntityId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountCode() {
        if (this.account != null){
            accountCode = this.account.getCode();
        }
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

     @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.recordDetailType);
        hash = 53 * hash + Objects.hashCode(this.account);
        hash = 53 * hash + Objects.hashCode(this.accountName);
        hash = 53 * hash + Objects.hashCode(this.accountCode);
        hash = 53 * hash + Objects.hashCode(this.getId());
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
        final RecordDetail other = (RecordDetail) obj;
        if (this.recordDetailType != other.recordDetailType) {
            return false;
        }
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        
        if (!Objects.equals(this.account, other.account)) {
            return false;
        }
        if (!Objects.equals(this.accountName, other.accountName)) {
            return false;
        }
        if (!Objects.equals(this.accountCode, other.accountCode)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public int compareTo(RecordDetail other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }
    
}
