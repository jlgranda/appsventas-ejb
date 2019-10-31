/*
 * Copyright (C) 2019 jlgrandadev
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
package org.jlgranda.fede.model.gifts;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgrandadev
 */


@Entity
@Table(name = "Gift")
/*@NamedQueries({ @NamedQuery(name = "Setting.findByName", query = "select s FROM Setting s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
    @NamedQuery(name = "Setting.findByNameAndOwner", query = "select s FROM Setting s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1")})*/
public class GiftEntity  extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 4656592712301655223L;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sharingDate")
    private Date sharingDate;
    
    @Column(name = "sharingCount")
    private Integer sharingCount;
    
    @Column(name = "shared")
    private boolean shared;
    
    @Column(name = "imageBasePath")
    private String imageBasePath;
    
    @Column(name = "discountValue")
    private BigDecimal discountValue;
    
    @Column(name = "discountType")
    private String discountType;


    /**
     * @return the sharingDate
     */
    public Date getSharingDate() {
        return sharingDate;
    }

    /**
     * @param sharingDate the sharingDate to set
     */
    public void setSharingDate(Date sharingDate) {
        this.sharingDate = sharingDate;
    }

    /**
     * @return the sharingCount
     */
    public Integer getSharingCount() {
        return sharingCount;
    }

    /**
     * @param sharingCount the sharingCount to set
     */
    public void setSharingCount(Integer sharingCount) {
        this.sharingCount = sharingCount;
    }

    /**
     * @return the shared
     */
    public boolean isShared() {
        return shared;
    }

    /**
     * @param shared the shared to set
     */
    public void setShared(boolean shared) {
        this.shared = shared;
    }

    /**
     * @return the discountValue
     */
    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    /**
     * @param discountValue the discountValue to set
     */
    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public String getImageBasePath() {
        return imageBasePath;
    }

    public void setImageBasePath(String imageBasePath) {
        this.imageBasePath = imageBasePath;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31); // two randomly chosen prime numbers
        // if deriving: appendSuper(super.hashCode()).

        hcb.append(getName());

        return hcb.toHashCode();
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
        GiftEntity other = (GiftEntity) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(getName(), other.getName());

        return eb.isEquals();
    }    


}
