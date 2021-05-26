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
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.jpapi.model.DeletableObject;

/**
 *
 * @author kellypaulinc
 */
@Entity
@Table (name = "Cash")
@NamedQueries({ @NamedQuery(name = "Cash.findByName", query = "SELECT s FROM Cash s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    @NamedQuery(name="Cash.findByNameAndOwner",query = "SELECT s FROM Cash s  WHERE s.name = ?1 and s.owner = ? 2 ORDER BY 1"),
})
public class Cash extends DeletableObject<Cash> implements Comparable<Cash>, Serializable{
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)    
    private Cash.DenominationType denominationType;
    
    @Column(nullable = false)
    private String denomination;
    
    @Column(nullable = false)
    BigDecimal valueDenomination;

    public enum DenominationType {
        BILL,
        MONEY;
    }

    public DenominationType getDenominationType() {
        return denominationType;
    }

    public void setDenominationType(DenominationType denominationType) {
        this.denominationType = denominationType;
    }

    public String getDenomination() {
        return denomination;
    }

    public void setDenomination(String denomination) {
        this.denomination = denomination;
    }

    public BigDecimal getValueDenomination() {
        return valueDenomination;
    }

    public void setValueDenomination(BigDecimal valueDenomination) {
        this.valueDenomination = valueDenomination;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.getId());
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
        final Cash other = (Cash) obj;
        if (!Objects.equals(this.getId(), other.getId())) {
            return false;
        }
        return true;
    }
    
    @Override
    public int compareTo(Cash other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }
    
}
