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
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jlgranda.fede.model.sales.Product;
import org.jpapi.model.DeletableObject;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "AggregationDetail")
@NamedQueries({
    @NamedQuery(name = "AggregationDetail.findByAggregation", query = "SELECT aggd FROM AggregationDetail aggd WHERE aggd.aggregation = ?1 order by aggd.cost"),})
public class AggregationDetail extends DeletableObject<AggregationDetail> implements Comparable<AggregationDetail>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "aggregation_id", insertable = true, updatable = true, nullable = true)
    private Aggregation aggregation;

    @ManyToOne(optional = true)
    @JoinColumn(name = "product_id", insertable = true, updatable = true, nullable = true)
    private Product product;

    private BigDecimal quantity;
    private BigDecimal cost;

    public Aggregation getAggregation() {
        return aggregation;
    }

    public void setAggregation(Aggregation aggregation) {
        this.aggregation = aggregation;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
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

        AggregationDetail other = (AggregationDetail) obj;
        EqualsBuilder eb = new EqualsBuilder();

//        eb.append(getId(), other.getId());
        eb.append(getAggregation(), other.getAggregation())
                .append(getProduct(), other.getProduct());

        return eb.isEquals();
    }

    @Override
    public int compareTo(AggregationDetail other) {
        return this.product.getId().compareTo(other.getProduct().getId());
    }

}
