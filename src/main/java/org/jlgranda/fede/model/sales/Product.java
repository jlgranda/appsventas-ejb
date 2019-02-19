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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "Product")
@DiscriminatorValue(value = "PROD")
@PrimaryKeyJoinColumn(name = "productId")
@NamedQueries({
    @NamedQuery(name = "Product.findById", query = "select p FROM Product p WHERE p.id = ?1"),
    @NamedQuery(name = "Product.findByOrganization", query = "select p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProduct", query = "select p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProducts", query = "select p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProductIdsBetween", query = "SELECT p.id,  d.lastUpdate FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.lastUpdate >= ? and d.lastUpdate <= ? ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIds", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIdsBetween", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.createdOn >= ? and d.createdOn <= ? GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductNames", query = "SELECT p.name,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) AND d.createdOn BETWEEN ?1 AND ?2 GROUP BY p.name ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countProduct", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE p.id = ?1 AND d.createdOn BETWEEN ?2 AND ?3 GROUP BY p.id ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countSoldProductByOwner", query = "SELECT p,  sum(d.amount) FROM Detail d JOIN d.product p WHERE p.owner = ?1 AND d.createdOn BETWEEN ?2 AND ?3 GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countByOwner", query = "select count(p) FROM Product p WHERE p.owner = ?1"),
})

public class Product extends BussinesEntity {

    private static final long serialVersionUID = -1320148041663418996L;
    
    @Column
    private String icon;
    
    @Column
    private BigDecimal price;
    
    @Column
    private ProductType productType;
    
    @Column(length = 1024)
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    public Product() {
        icon = "fa fa-question-circle "; //icon by default
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    
    @Override
    public int hashCode() {
        return new org.apache.commons.lang.builder.HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getCode()).
                append(getName()).
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
        Product other = (Product) obj;
        return new org.apache.commons.lang.builder.EqualsBuilder().
                append(getCode(), other.getCode()).
                append(getName(), other.getName()).
                isEquals();
    }

}
