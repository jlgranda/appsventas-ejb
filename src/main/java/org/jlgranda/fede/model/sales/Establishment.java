/*
 * Copyright (C) 2016 jlgranda
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
package org.jlgranda.fede.model.sales;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.Organization;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.BussinesEntityAttribute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "ESTABLISHMENT")
//@DiscriminatorValue(value = "ESTB")
//@PrimaryKeyJoinColumn(name = "establishmentId")
public class Establishment extends DeletableObject<Establishment> implements Serializable {
    
    private static Logger log = LoggerFactory.getLogger(Establishment.class);
    
    @ManyToOne
    @JoinColumn(name = "organization_id", insertable=false, updatable=false, nullable=false)
    private Organization organization;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "establishment", fetch = FetchType.LAZY)
    private List<EmissionPoint> emissionPoints = new ArrayList<EmissionPoint>();

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public List<EmissionPoint> getEmissionPoints() {
        return emissionPoints;
    }

    public void setEmissionPoints(List<EmissionPoint> emissionPoints) {
        this.emissionPoints = emissionPoints;
    }
}
