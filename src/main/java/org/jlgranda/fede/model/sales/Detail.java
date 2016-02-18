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

import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "INVOICE_DETAIL")
public class Detail extends PersistentObject implements Comparable<Detail> {
    
    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "invoice_id", insertable=true, updatable=false, nullable=true)
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
//        this.productId = null;
        this.amount = 0;
        this.unit = "u";
    }

    public Detail(Product product, float amount) {
        this.product = product;
        this.productId = product.getId();
        this.amount = amount;
        this.unit = "u";
    }
    
    public Detail(Product product, float amount, String unit) {
        this.productId = product.getId();
        this.product = product;
        this.amount = amount;
        this.unit = unit;
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
    public String toString() {
        return "(" + Math.round(getAmount()) + ") " + getProduct().getName();//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(Detail other) {
        return this.product.getName().compareTo(other.product.getName());
    }
     
}
