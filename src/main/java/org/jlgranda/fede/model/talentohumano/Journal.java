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
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "JOURNAL")
public class Journal  extends PersistentObject implements Comparable<Journal>, Serializable {

    private static final long serialVersionUID = -2746655196942302436L;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "beginTime", nullable = false)
    protected Date beginTime;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "endTime", updatable = false, nullable = false)
    protected Date endTime;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "employee_id", insertable=false, updatable=false, nullable=false)
    private Employee employee;
    
    @Column(name = "employee_id", insertable=true, updatable=true, nullable=false)
    private Long employeeId;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public int compareTo(Journal t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return getName();
    }
    
}
