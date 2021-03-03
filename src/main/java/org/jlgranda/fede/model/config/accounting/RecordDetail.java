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
package org.jlgranda.fede.model.config.accounting;

/**
 *
 * @author jlgranda
 */


import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

@Entity
@Table(name = "Record")
@NamedQueries({ @NamedQuery(name = "RecordDetail.findByName", query = "select s FROM RecordDetail s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery(name = "RecordDetail.findByNameAndOwner", query = "select s FROM RecordDetail s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1")})
public class RecordDetail extends PersistentObject<RecordDetail> implements Comparable<RecordDetail>, Serializable {

    Account account;
    
    String recordType;
    
    Long bussineEntityId;
    
    BigDecimal amount;
    
    @ManyToOne
    Record record;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
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

    @Override
    public int compareTo(RecordDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
