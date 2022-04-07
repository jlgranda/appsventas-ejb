/*
 * Copyright (C) 2022 usuario
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
package org.jlgranda.fede.model.production;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.hibernate.annotations.Where;
import org.jlgranda.fede.model.sales.Product;
import org.jpapi.model.DeletableObject;
import org.jpapi.model.Organization;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "aggregation")
@NamedQueries({
    @NamedQuery(name = "Aggregation.findProductsOfAggregationsByOrganization", query = "SELECT DISTINCT agg.product FROM Aggregation agg WHERE agg.organization = ?1"),})
public class Aggregation extends DeletableObject<Aggregation> implements Comparable<Aggregation>, Serializable {

    @ManyToOne()
    @JoinColumn(name = "organization_id", insertable = true, updatable = true, nullable = true)
    private Organization organization;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = true, updatable = true, nullable = true, unique = true)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "aggregation", fetch = FetchType.LAZY)
    @Where(clause = "deleted = false") //sólo no eliminados
    @OrderBy(value = "cost ASC")
    private List<AggregationDetail> aggregationDetails = new ArrayList<>();

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

    public List<AggregationDetail> getAggregationDetails() {
        return aggregationDetails;
    }

    public void setAggregationDetails(List<AggregationDetail> aggregationDetails) {
        this.aggregationDetails = aggregationDetails;
    }

    public AggregationDetail addAggregationDetail(AggregationDetail aggregationDetail) {
        aggregationDetail.setAggregation(this);
        if (this.aggregationDetails.contains(aggregationDetail)) {
            replaceAggregationDetail(aggregationDetail);
        } else {
            this.aggregationDetails.add(aggregationDetail);
        }
        return aggregationDetail;
    }
    public AggregationDetail removeAggregationDetail(AggregationDetail aggregationDetail) {
        if (this.aggregationDetails.contains(aggregationDetail)) {
            this.aggregationDetails.remove(aggregationDetail);
        }
        return aggregationDetail;
    }
    
    public AggregationDetail replaceAggregationDetail(AggregationDetail detail) {
        getAggregationDetails().set(getAggregationDetails().indexOf(detail), detail);
        return detail;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31);
        hcb.append(getOrganization()).append(getProduct());
        return hcb.toHashCode();
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
        Aggregation other = (Aggregation) obj;
        if (!Objects.equals(this.organization, other.organization)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Aggregation other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

}
