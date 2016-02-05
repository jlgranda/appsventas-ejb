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

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
@Table(name = "Perspective")
@DiscriminatorValue(value = "PTV")
@PrimaryKeyJoinColumn(name = "id")
public class Perspective extends BussinesEntity implements Serializable {
    
    private static final long serialVersionUID = -4535279430871708301L;
    
    private Long sequence;
            
    @OneToMany(mappedBy = "perspective", cascade = CascadeType.ALL)
    private  List<Theme> themes;

    @ManyToOne
    private BalancedScoreCard balancedScorecard;
    
    public List<Theme> getThemes() {
        return themes;
    }

    public void setThemes(List<Theme> themes) {
        this.themes = themes;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public BalancedScoreCard getBalancedScorecard() {
        return balancedScorecard;
    }

    public void setBalancedScorecard(BalancedScoreCard balancedScorecard) {
        this.balancedScorecard = balancedScorecard;
    }

    public boolean addTheme(Theme theme){
        theme.setPerspective(this);
        return getThemes().add(theme);
    }
    
    public boolean removeTheme(Theme theme){
        if (getThemes().contains(theme)){
            return getThemes().remove(theme);
        }
        return false;
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
        Perspective other = (Perspective) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }
    
    @Override
    public String getCanonicalPath(){
        StringBuilder path = new StringBuilder();
        path.append(getBalancedScorecard().getCanonicalPath());
        path.append(BussinesEntity.SEPARATOR); //TODO hacer que sea personalizable
        path.append(getBalancedScorecard().getName());
        return path.toString();
    }
    
    @Override
    public String toString() {
        /*return "org.eqaula.glue.model.management.Perspective[ "
                + "id=" + getId() + ","
                + "name=" + getName() + ","
                + "type=" + getType() + ","
                + " ]";*/
        return getName();
    }
}
