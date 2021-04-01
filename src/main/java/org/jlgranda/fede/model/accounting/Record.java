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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jlgranda.fede.model.document.FacturaElectronica;
import org.jpapi.model.PersistentObject;

@Entity
@Table(name = "Record")
@NamedQueries({ @NamedQuery(name = "Record.findByName", query = "select s FROM Record s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery(name = "Record.findByNameAndOwner", query = "select s FROM Record s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1"),
@NamedQuery(name = "Record.findByJournalAndFact", query = "select s FROM Record s WHERE s.journal = ?1 and s.facturaElectronica = ?2 ORDER BY 1"),
})
public class Record extends PersistentObject<Record> implements Comparable<Record>, Serializable {

    @ManyToOne (optional = false, cascade = {CascadeType.ALL})
    @JoinColumn (name = "journal_id", insertable = true, updatable = true, nullable = true)
    GeneralJournal journal;
    
    @OneToOne
    @JoinColumn (name = "facturaElectronica_id")
    private FacturaElectronica facturaElectronica;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "record", fetch = FetchType.LAZY)
    private List<RecordDetail> recordDetails = new ArrayList<>();

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "emissionDate")
    private Date emissionDate;

    public GeneralJournal getJournal() {
        return journal;
    }

    public void setJournal(GeneralJournal journal) {
        this.journal = journal;
    }

    public List<RecordDetail> getRecordDetails() {
        return recordDetails;
    }

    public void setRecordDetails(List<RecordDetail> recordDetails) {
        this.recordDetails = recordDetails;
    }

    public Date getEmissionDate() {
        return emissionDate;
    }

    public void setEmissionDate(Date emissionDate) {
        this.emissionDate = emissionDate;
    }

    public void addRecordDetailNew(RecordDetail recordDetail) {
        recordDetail.setRecord(this);
        this.recordDetails.add(recordDetail);
    }
    
    public void addRecordDetail(RecordDetail recordDetail) {
        recordDetail.setRecord(this);
        if(this.recordDetails.contains(recordDetail)){
            replaceRecordDetail(recordDetail);
        }else{
            this.recordDetails.add(recordDetail);
        }
    }
    
    public RecordDetail replaceRecordDetail(RecordDetail recordDetail){
        getRecordDetails().set(getRecordDetails().indexOf(recordDetail), recordDetail);
        return recordDetail;
    }
    
    public List<RecordDetail> removeRecordsDetails(){
        this.recordDetails.removeAll(getRecordDetails());
        setRecordDetails(this.recordDetails);
        return getRecordDetails();
    }
    
    public FacturaElectronica getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(FacturaElectronica facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
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
        Record other = (Record) obj;
        EqualsBuilder eb = new EqualsBuilder();
        
        eb.append(getId(), other.getId());
        return eb.isEquals();
    }
    
    @Override
    public int compareTo(Record other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

    
}
