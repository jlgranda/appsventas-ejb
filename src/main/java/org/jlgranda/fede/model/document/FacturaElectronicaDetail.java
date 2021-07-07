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
package org.jlgranda.fede.model.document;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jlgranda.fede.model.Detailable;
import org.jlgranda.fede.model.sales.Product;
import org.jpapi.model.DeletableObject;

/**
 *
 * @author author
 */
@Entity
@Table(name = "Factura_Electronica_Detail")
@NamedQueries({})
public class FacturaElectronicaDetail extends DeletableObject<FacturaElectronicaDetail> implements Comparable<FacturaElectronicaDetail>, Serializable, Detailable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "facturaelectronica_id", insertable = true, updatable = true, nullable = true)
    private FacturaElectronica facturaElectronica;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", insertable = true, updatable = true, nullable = true)
    private Product product;
    
    private BigDecimal amount;
    
    @Column(name = "unit_value")
    private BigDecimal unitValue;
    @Column(name = "total_value")
    private BigDecimal totalValue;
    @Column(name = "tax_value")
    private BigDecimal taxValue = BigDecimal.ZERO;
    
    public FacturaElectronicaDetail(){
        this.showAmountInSummary = true;
    }
    public FacturaElectronica getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(FacturaElectronica facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

    @Override
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(BigDecimal unitValue) {
        this.unitValue = unitValue;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
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

        FacturaElectronicaDetail other = (FacturaElectronicaDetail) obj;
        EqualsBuilder eb = new EqualsBuilder();

//        eb.append(getId(), other.getId());
        eb.append(getProduct(), other.getProduct());

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
            .append(getProduct().getName());
        } else {
            str.append(getProduct().getName());
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

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    
    @Override
    public int compareTo(FacturaElectronicaDetail other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }

    /**
     *
     * @return
     */
    @Override
    public Long getBussinesEntityId() {
        return getFacturaElectronica().getId();
    }

    @Override
    public String getBussinesEntityType() {
        return FacturaElectronica.class.getSimpleName();
    }

    @Override
    public BigDecimal getAmount() {
        return this.amount;
    }

    @Override
    public BigDecimal getPrice() {
        return this.getUnitValue();
    }

    @Override
    public String getUnit() {
        return "u"; //unidades
    }
    
    @Override
    public String getBussinesEntityCode() {
        return getFacturaElectronica().getCode();
    }

}
