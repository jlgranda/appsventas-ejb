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
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.jlgranda.fede.model.sales.Product;
import org.jpapi.model.DeletableObject;

/**
 *
 * @author usuario
 */
@Entity
@Table(name = "aggregation_detail")
@NamedQueries({
    @NamedQuery(name = "Aggregation.findByAggregation", query = "SELECT aggd FROM AggregationDetail aggd WHERE aggd.aggregation = ?1 order by aggd.cost"),})
public class AggregationDetail extends DeletableObject<AggregationDetail> implements Comparable<AggregationDetail>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "aggregation_id", insertable = true, updatable = true, nullable = true)
    private Aggregation aggregation;

    @OneToOne(optional = false, cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false, nullable = false)
    private Product product;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "quantity")
    private BigDecimal quantity;
    
    @Column(name = "cost")
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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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
        int hash = 3;
        //hash = 37 * hash + Objects.hashCode(this.aggregation);
        //hash = 37 * hash + Objects.hashCode(this.product);
        hash = 37 * hash + Objects.hashCode(this.productId);
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
        final AggregationDetail other = (AggregationDetail) obj;
        if (!Objects.equals(this.aggregation, other.aggregation)) {
            return false;
        }
        if (!Objects.equals(this.product, other.product)) {
            return false;
        }
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(AggregationDetail t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
