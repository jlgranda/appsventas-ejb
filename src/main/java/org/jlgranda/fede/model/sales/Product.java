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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.Organization;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.Group;
import org.jpapi.model.TaxType;

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
    @NamedQuery(name = "Product.findByOrganization", query = "select p FROM Product p ORDER BY p.name DESC"),
    @NamedQuery(name = "Product.findByProductType", query = "select p FROM Product p WHERE p.productType = ?1 ORDER BY p.name DESC"),
    @NamedQuery(name = "Product.findLastProduct", query = "select p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProductOrg", query = "select p FROM Product p WHERE p.organization=?1 ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProducts", query = "select p FROM Product p ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProductsOrg", query = "select p FROM Product p WHERE p.organization=?1 ORDER BY p.id DESC"),
    @NamedQuery(name = "Product.findLastProductIdsBetween", query = "SELECT p.id,  d.lastUpdate FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.lastUpdate >= ?1 and d.lastUpdate <= ?2 ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIds", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIdsBetween", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.createdOn >= ?1 and d.createdOn <= ?2 GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIdsBetweenOrg", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.invoice.organization=?1 and d.createdOn >= ?2 and d.createdOn <= ?3 GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIdsBetweenPrice", query = "SELECT p.id,  sum(d.price*d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.createdOn >= ?1 and d.createdOn <= ?2 GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIdsBetweenPriceOrg", query = "SELECT p.id,  sum(d.price*d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.invoice.organization=?1 and d.createdOn >= ?2 and d.createdOn <= ?3 GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductIdsBetweenCategoryOrg", query = "SELECT p.category.name,  sum(d.price*d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.invoice.organization=?1 and d.createdOn >= ?2 and d.createdOn <= ?3 GROUP BY p.category.name ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductNames", query = "SELECT p.name,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) AND d.createdOn BETWEEN ?1 AND ?2 GROUP BY p.name ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.findTopProductNamesOrg", query = "SELECT p.name,  sum(d.amount) FROM Detail d JOIN d.product p WHERE not p.id in (75, 676,672) and d.invoice.organization=?1 AND d.createdOn BETWEEN ?1 AND ?2 GROUP BY p.name ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countProduct", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE p.id = ?1 AND d.createdOn BETWEEN ?2 AND ?3 GROUP BY p.id ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countProductOrg", query = "SELECT p.id,  sum(d.amount) FROM Detail d JOIN d.product p WHERE p.id = ?1 AND p.organization = ?2 AND d.createdOn BETWEEN ?3 AND ?4 GROUP BY p.id ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countSoldProductByOwner", query = "SELECT p,  sum(d.amount) FROM Detail d JOIN d.product p WHERE p.owner = ?1 AND d.createdOn BETWEEN ?2 AND ?3 GROUP BY p ORDER BY 2 DESC"),
    @NamedQuery(name = "Product.countByOwner", query = "select count(p) FROM Product p WHERE p.owner = ?1"),
    @NamedQuery(name = "Product.countProductByCategories", query = "select g.name, count(p.category) FROM Product p JOIN p.category g WHERE not p.id in (75, 676,672) and p.createdOn >= ?1 and p.createdOn <= ?2 GROUP BY p.category, g.name ORDER BY COUNT(p.category) DESC"),
})
//SELECT
//b.name,
//count(p.group_id)
//FROM public.product as p
//INNER JOIN public.ggroup as g on g.groupid=p.group_id
//INNER JOIN bussinesentity as b on b.id=g.groupid
//WHERE not p.productid in (75, 676,672) AND
// b.createdon >='2021-03-01' AND b.createdon <='2021-03-14'
//GROUP BY p.group_id, b.name;
public class Product extends BussinesEntity {

    private static final long serialVersionUID = -1320148041663418996L;
    
    @Column
    private String icon;
    
    @Column
    private BigDecimal price;
    
    @Column
    private ProductType productType;
    
    @Column
    private TaxType taxType;
    
    @Column(length = 1024)
    @Basic(fetch = FetchType.LAZY)
    private byte[] photo;

    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable=true, updatable=true, nullable=true)
    private Organization organization;

    @ManyToOne
    @JoinColumn(name = "group_id", insertable=true, updatable=true, nullable=true)
    private Group category;

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

    public TaxType getTaxType() {
        return taxType;
    }

    public void setTaxType(TaxType taxType) {
        this.taxType = taxType;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    public Group getCategory() {
        return category;
    }

    public void setCategory(Group category) {
        this.category = category;
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
                append(getId(), other.getId()).
                append(getCode(), other.getCode()).
                append(getName(), other.getName()).
                isEquals();
    }
    
    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }

}
