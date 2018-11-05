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
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "JOBROLE")
public class JobRole extends PersistentObject implements Comparable<JobRole>, Serializable{
    
    private static final long serialVersionUID = -8790803118826094605L;
    
    private BigDecimal remuneration;
    
    private BigDecimal dedication;

    public BigDecimal getRemuneration() {
        return remuneration;
    }

    public void setRemuneration(BigDecimal remuneration) {
        this.remuneration = remuneration;
    }

    public BigDecimal getDedication() {
        return dedication;
    }

    public void setDedication(BigDecimal dedication) {
        this.dedication = dedication;
    }
    

    @Override
    public int compareTo(JobRole t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
