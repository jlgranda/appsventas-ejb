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
import org.jpapi.model.DeletableObject;
import org.jpapi.model.Organization;

/**
 * Basado en documentación: https://youtu.be/eyZ-p9UCKOY?t=841
 * @author jlgranda
 */
@Entity
@Table(name = "General_Journal")
@NamedQueries({ @NamedQuery(name = "GeneralJournal.findByName", query = "select s FROM GeneralJournal s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery(name = "GeneralJournal.findByNameAndOwner", query = "select s FROM GeneralJournal s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
@NamedQuery(name = "GeneralJournal.findByCreatedOnAndOrg", query = "select s FROM GeneralJournal s WHERE s.createdOn >= ?1 and s.createdOn<= ?2 and s.organization = ?3 ORDER BY 1"),
@NamedQuery(name = "GeneralJournal.findByCreatedOnAndOrganization", query = "SELECT s FROM GeneralJournal s WHERE s.createdOn >= ?1 AND s.createdOn <= ?1 AND s.organization = ?2 AND s.deleted = false ORDER BY 1"),
})
public class GeneralJournal extends DeletableObject<GeneralJournal> implements Comparable<GeneralJournal>, Serializable {
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "journal", fetch = FetchType.LAZY)
    private List<Record> records = new ArrayList<>();

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
    
    public Record addRecord(Record record){
        record.setJournal(this);
        if(this.records.contains(record)){
            replaceRecord(record);
        }else{
            this.records.add(record);
        }
        return record;
    }
    
    public Record replaceRecord(Record record){
        getRecords().set(getRecords().indexOf(record), record);
        return record;
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
        GeneralJournal other = (GeneralJournal) obj;
        EqualsBuilder eb = new EqualsBuilder();
        
        eb.append(getId(), other.getId());
        return eb.isEquals();
    }
    @Override
    public int compareTo(GeneralJournal other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

}
