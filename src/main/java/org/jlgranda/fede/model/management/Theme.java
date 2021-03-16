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
@Table(name = "Theme")
@DiscriminatorValue(value = "THM")
@PrimaryKeyJoinColumn(name = "id")
public class Theme extends BussinesEntity implements Serializable {
    
    private static final long serialVersionUID = -7436571688718703657L;
    
    @ManyToOne
    private Proprietor proprietor;
    
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<Objetive> objetives;

    @ManyToOne
    private Perspective perspective;
    
    @ManyToOne
    private Organization organization;
    
    @OneToMany(mappedBy = "theme", cascade = CascadeType.ALL)
    private List<Macroprocess> macroprocess;

    public Proprietor getProprietor() {
        return proprietor;
    }

    public void setProprietor(Proprietor proprietor) {
        this.proprietor = proprietor;
    }

    public List<Objetive> getObjetives() {
        return objetives;
    }

    public void setObjetives(List<Objetive> objetives) {
        this.objetives = objetives;
    }

    public Perspective getPerspective() {
        return perspective;
    }

    public void setPerspective(Perspective perspective) {
        this.perspective = perspective;
    }

    public List<Macroprocess> getMacroprocess() {
        return macroprocess;
    }

    public void setMacroprocess(List<Macroprocess> macroprocess) {
        this.macroprocess = macroprocess;
    }

    public Organization getOrganization() {
        return this.organization;
    }

    public void setOrganization(Organization organization) {
        this.organization=organization;
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
        Theme other = (Theme) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }
    
     @Override
    public String getCanonicalPath(){
        StringBuilder path = new StringBuilder();
        path.append(getPerspective().getCanonicalPath());
        path.append(BussinesEntity.SEPARATOR); //TODO hacer que sea personalizable
        path.append(getPerspective().getName());
        return path.toString();
    }
    
    @Override
    public String toString() {
        /*return "org.eqaula.glue.model.management.Theme[ "
                + "id=" + getId() + ","
                + "name=" + getName() + ","
                + "type=" + getType() + ","
                + " ]";*/
        return getName() + ":" + getDescription();
    }
}
