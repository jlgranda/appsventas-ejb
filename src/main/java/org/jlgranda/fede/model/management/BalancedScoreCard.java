/*
 * Copyright (C) 2015 jlgranda
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
package org.jlgranda.fede.model.management;

import org.jpapi.model.Organization;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jpapi.model.BussinesEntity;
/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "BalancedScorecard")
@DiscriminatorValue(value = "BSC")
@PrimaryKeyJoinColumn(name = "id")
@NamedQueries({
    @NamedQuery(name = "BalancedScoreCard.findByOwner", query = "select b FROM BalancedScoreCard b WHERE b.owner=?1 ORDER BY b.name ASC"),
    @NamedQuery(name = "BalancedScoreCard.findByOwnerAndOrganization", query = "select b FROM BalancedScoreCard b WHERE b.owner=?1 and b.organization = ?2 ORDER BY b.name ASC"),
    @NamedQuery(name = "BalancedScoreCard.countByOwner", query = "select count(b) FROM BalancedScoreCard b WHERE b.owner = ?1"),
    @NamedQuery(name = "BalancedScoreCard.countByOwnerAndOrganization", query = "select count(b) FROM BalancedScoreCard b WHERE b.owner = ?1 and b.organization = ?2")
})
public class BalancedScoreCard extends BussinesEntity implements Serializable {
    private static final long serialVersionUID = -8149661791009499829L;
    
    @ManyToOne
    private Organization organization;
    
    @OneToMany(mappedBy = "balancedScorecard", cascade = CascadeType.ALL)
    List<Perspective> perspectives;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<Perspective> getPerspectives() {
        return perspectives;
    }

    public void setPerspectives(List<Perspective> perspectives) {
        this.perspectives = perspectives;
    }

    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getName()).
                append(getType()).
                toHashCode();
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
        BalancedScoreCard other = (BalancedScoreCard) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }
    
    @Override
    public String getCanonicalPath(){
        StringBuilder path = new StringBuilder();
        path.append(getOrganization().getCanonicalPath());
        return path.toString();
    }
    
    @Override
    public String toString() {
       /* return "org.eqaula.glue.model.management.BalancedScoreCard[ "
                + "id=" + getId() + ","
                + "name=" + getName() + ","
                + "type=" + getType() + ","
                + " ]";*/
        return "" + getId();
    }
}
