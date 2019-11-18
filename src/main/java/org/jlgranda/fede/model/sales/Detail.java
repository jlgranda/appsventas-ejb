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

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "INVOICE_DETAIL")
public class Detail extends PersistentObject implements Comparable<Detail>, Serializable {

    private static final long serialVersionUID = 2616187458131227603L;
    
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "invoice_id", insertable=true, updatable=true, nullable=true)
    private Invoice invoice;
    
    @ManyToOne(optional = true, cascade = {CascadeType.ALL})
    @JoinColumn(name = "product_id", insertable=false, updatable=false, nullable=false)
    private Product product;
    
    @Column(name = "product_id", insertable=true, updatable=true, nullable=false)
    private Long productId;
    
    private float amount;
    
    private String unit;
    
    @Column
    private BigDecimal price;

    public Detail() {
        this.product = null;
        this.amount = 0;
        this.unit = "u";
        this.showAmountInSummary = true;
    }

    public Detail(Product product, float amount) {
        this.product = product;
        this.productId = product.getId();
        this.amount = amount;
        this.unit = "u";
        this.showAmountInSummary = true;
    }
    
    public Detail(Product product, float amount, String unit) {
        this.productId = product.getId();
        this.product = product;
        this.amount = amount;
        this.unit = unit;
        this.showAmountInSummary = true;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
        if (product != null)
            setProductId(product.getId());
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    
    @Override
    public int hashCode() {
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31); // two randomly chosen prime numbers
        // if deriving: appendSuper(super.hashCode()).
        if (getProduct() != null) {
            hcb.append(getInvoice()).
                    append(getProductId()).
                    append(getProduct());
        } else {
            hcb.append(getInvoice()).
                    append(getProductId());
        }

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
        Detail other = (Detail) obj;
        EqualsBuilder eb = new EqualsBuilder();
        
        if (other.getProduct() != null) {
            eb.append(getProductId(), other.getProductId()).
                    append(getProduct(), other.getProduct()).
                    append(getInvoice(), other.getInvoice());
        } else {
            eb.append(getProductId(), other.getProductId()).
                    append(getInvoice(), other.getInvoice());

        }
        return eb.isEquals();
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        
        if (isShowAmountInSummary()){
            str.append("(")
            .append(getAmount())
            .append(")")
            .append(" ")
            .append(getProduct() == null ? getProductId() : getProduct().getName());
        } else {
            str.append(getProduct() == null ? getProductId() : getProduct().getName());
        }
        return str.toString();
    }
    
    @Transient
    private boolean showAmountInSummary;

    public boolean isShowAmountInSummary() {
        return showAmountInSummary;
    }

    public void setShowAmountInSummary(boolean showAmountInSummary) {
        this.showAmountInSummary = showAmountInSummary;
    }

    @Override
    public int compareTo(Detail other) {
        return this.product.getName().compareTo(other.product.getName());
    }
     
}
