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
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jpapi.model.DeletableObject;
import org.jpapi.model.Organization;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "record_template")
public class RecordTemplate extends DeletableObject<RecordTemplate> implements Comparable<RecordTemplate>, Serializable {
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recordTemplate", fetch = FetchType.LAZY)
//    private List<RecordDetailTemplate> recordDetailTemplates = new ArrayList<>();
    
   
    @Column (name = "rule", length = 10240)
    private String rule;

    public RecordTemplate() {
    }
    
    public RecordTemplate(String code, String name) {
        setCode(code);
        setName(name);
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }
    
//    public List<RecordDetailTemplate> getRecordDetailTemplates() {
//        return recordDetailTemplates;
//    }
//
//    public void setRecordDetailTemplates(List<RecordDetailTemplate> recordDetailTemplates) {
//        this.recordDetailTemplates = recordDetailTemplates;
//    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.organization);
        hash = 37 * hash + Objects.hashCode(this.name);
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
        final RecordTemplate other = (RecordTemplate) obj;

        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.organization, other.organization)) {
            return false;
        }
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }
    
    
    @Override
    public int compareTo(RecordTemplate other){
        return this.createdOn.compareTo(other.getCreatedOn());
    }
    
}
