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

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "record_template")
public class RecordDetailTemplate extends PersistentObject<RecordDetailTemplate> implements Comparable<RecordDetailTemplate>, Serializable { 

    /*
    Tipo de recordDetail
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)    
    private RecordDetail.RecordTDetailType recordDetailType;
    
    /**
     * La clase de la cual se debe obtener el valor
     */
    String clazz;
    
    /**
     * El attributo de clase de la cual se debe obtener el valor
     */
    String field;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "account_id", insertable=true, updatable=true, nullable=true)
    private Account account;

    public RecordDetail.RecordTDetailType getRecordDetailType() {
        return recordDetailType;
    }

    public void setRecordDetailType(RecordDetail.RecordTDetailType recordDetailType) {
        this.recordDetailType = recordDetailType;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
    
    @ManyToOne (optional = false, cascade = {CascadeType.ALL})
    @JoinColumn (name = "record_template_id", insertable = true, updatable = true, nullable = true)
    RecordTemplate recordTemplate;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.recordDetailType);
        hash = 37 * hash + Objects.hashCode(this.clazz);
        hash = 37 * hash + Objects.hashCode(this.field);
        hash = 37 * hash + Objects.hashCode(this.account);
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
        final RecordDetailTemplate other = (RecordDetailTemplate) obj;
        if (!Objects.equals(this.clazz, other.clazz)) {
            return false;
        }
        if (!Objects.equals(this.field, other.field)) {
            return false;
        }
        if (this.recordDetailType != other.recordDetailType) {
            return false;
        }
        return Objects.equals(this.account, other.account);
    }
   
    @Override
    public int compareTo(RecordDetailTemplate other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }
}
