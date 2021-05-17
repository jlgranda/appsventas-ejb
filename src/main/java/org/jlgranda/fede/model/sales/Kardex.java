/*
 * Copyright (C) 2021 author
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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jpapi.model.DeletableObject;
import org.jpapi.model.Organization;

/**
 *
 * @author author
 */
@Entity
@Table(name = "Kardex")
public class Kardex extends DeletableObject<Kardex> implements Comparable<Kardex>, Serializable {

    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true)
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = true, updatable = true)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kardex", fetch = FetchType.LAZY)
    private List<KardexDetail> kardexDetails = new ArrayList<>();
    
    private Long quantity;
    private BigDecimal fund;
    private String unit_measure;
    private Long unit_minimum;
    private Long unit_maximum;
    
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<KardexDetail> getKardexDetails() {
        return kardexDetails;
    }

    public void setKardexDetails(List<KardexDetail> kardexDetails) {
        this.kardexDetails = kardexDetails;
    }
    
    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public String getUnit_measure() {
        return unit_measure;
    }

    public void setUnit_measure(String unit_measure) {
        this.unit_measure = unit_measure;
    }

    public Long getUnit_minimum() {
        return unit_minimum;
    }

    public void setUnit_minimum(Long unit_minimum) {
        this.unit_minimum = unit_minimum;
    }

    public Long getUnit_maximum() {
        return unit_maximum;
    }

    public void setUnit_maximum(Long unit_maximum) {
        this.unit_maximum = unit_maximum;
    }
    
    public void addKardexDetail(KardexDetail kardexDetail){
        kardexDetail.setKardex(this);
        if(this.kardexDetails.contains(kardexDetail)){
            replaceKardexDetail(kardexDetail);
        }else{
            this.kardexDetails.add(kardexDetail);
        }
    }
    
    public KardexDetail replaceKardexDetail(KardexDetail kardexDetail){
        getKardexDetails().set(getKardexDetails().indexOf(kardexDetail), kardexDetail);
        return kardexDetail;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31);
        hcb.append(getId());
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

        Kardex other = (Kardex) obj;
        EqualsBuilder eb = new EqualsBuilder();

        eb.append(getId(), other.getId());

        return eb.isEquals();
    }

    @Override
    public int compareTo(Kardex other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }
}
