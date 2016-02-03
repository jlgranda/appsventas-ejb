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
import java.util.ArrayList;
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
 * @author dianita
 */
@Entity
@Table(name = "Section")
@DiscriminatorValue(value = "SCTN")
@PrimaryKeyJoinColumn(name = "id")
public class Section extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 7049522576163792111L;
    @ManyToOne
    private Diagnostic diagnostic;
    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL)
    private List<RevisionItem> revisionItems;

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public List<RevisionItem> getRevisionItems() {
        return revisionItems;
    }

    public List<RevisionItem> getRevisionItemsNulls() {
        List<RevisionItem> items = new ArrayList<RevisionItem>();
        for (RevisionItem revisionItem : revisionItems) {
            if (revisionItem.getRevisionItem() == null) {
                items.add(revisionItem);
            }
        }
        return items;
    }

    public List<RevisionItem> getRevisionItemsNotNulls() {
        List<RevisionItem> items = new ArrayList<RevisionItem>();
        for (RevisionItem revisionItem : revisionItems) {
            if (revisionItem.getRevisionItem() != null) {
                items.add(revisionItem);
            }
        }
        return items;
    }

    public void setRevisionItems(List<RevisionItem> revisionItems) {
        this.revisionItems = revisionItems;
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
        Section other = (Section) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }

    @Override
    public String getCanonicalPath() {
        StringBuilder path = new StringBuilder();
        path.append(getDiagnostic().getCanonicalPath());
        path.append(BussinesEntity.SEPARATOR); //TODO hacer que sea personalizable
        path.append(getDiagnostic().getName());
        return path.toString();
    }

    @Override
    public String toString() {
        return "org.eqaula.glue.model.management.Section[ "
                + "id=" + getId() + ","
                + "name=" + getName() + ","
                + "type=" + getType() + ","
                + " ]";
    }
}
