/*
 * Copyright (C) 2018 jlgranda
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
package org.jlgranda.fede.model.talentohumano;

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
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.jpapi.model.Organization;
import org.jpapi.model.DeletableObject;

/**
 * Especialización por organización de la entidad Subject, el campo owner indica a que Subject pertenece.
 * @author jlgranda
 */
@Entity
@Table(name = "EMPLOYEE")
@NamedQueries({
    @NamedQuery(name = "Employee.findByOwner", query = "SELECT e FROM Employee e WHERE e.owner = ?1 and e.deleted = false"),
    @NamedQuery(name = "Employee.findByOwnerAndOrganization", query = "SELECT e FROM Employee e WHERE e.owner = ?1 and e.organization = ?2 and e.deleted = false"),
    @NamedQuery(name = "Employee.findByOwnerCodeAndName", query = "SELECT e FROM Employee e WHERE lower(e.owner.code) like lower(:code) or lower(e.owner.firstname) like lower(:firstname) or lower(e.owner.surname) like lower(:surname)")
})
public class Employee extends DeletableObject implements Comparable<Employee>, Serializable {

    private static final long serialVersionUID = -1016927888119107404L;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;

    @ManyToOne(optional = true)
    @JoinColumn(name = "role", nullable = true)
    private JobRole role;
    
    @OneToMany(cascade = {CascadeType.MERGE}, mappedBy = "employee", fetch = FetchType.LAZY)
    @OrderBy("beginTime DESC")
    private List<Journal> journals = new ArrayList<>();

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    public JobRole getRole() {
        return role;
    }

    public void setRole(JobRole role) {
        this.role = role;
    }

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals;
    }
    
    
    @Override
    public int compareTo(Employee t) {
        if (getId() == null  && t != null && t.getId() != null)
            return -1;
        if (getId() != null  && t == null)
            return 1;
        return getId().compareTo(t.getId());
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }
    
}
