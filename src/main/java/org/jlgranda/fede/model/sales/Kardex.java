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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
    @NamedQuery(name = "Kardex.findByProductAndOrg", query = "SELECT kd FROM Kardex kd WHERE kd.product = ?1 and kd.organization = ?2 and kd.deleted = false ORDER BY kd.id DESC"),
})
public class Kardex extends DeletableObject<Kardex> implements Comparable<Kardex>, Serializable {

    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true)
    private Organization organization;

    @OneToOne
    @JoinColumn(name = "product_id", insertable = true, updatable = true)
    private Product product;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kardex", fetch = FetchType.LAZY)
    private List<KardexDetail> kardexDetails = new ArrayList<>();
    
    private BigDecimal quantity;
    private BigDecimal fund;
    @Column( name = "unit_measure")
    private String unitMeasure;
    @Column( name = "unit_minimum")
    private BigDecimal unitMinimum;
    @Column( name = "unit_maximum")
    private BigDecimal unitMaximum;
    
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

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getFund() {
        return fund;
    }

    public void setFund(BigDecimal fund) {
        this.fund = fund;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }

    public BigDecimal getUnitMinimum() {
        return unitMinimum;
    }

    public void setUnitMinimum(BigDecimal unitMinimum) {
        this.unitMinimum = unitMinimum;
    }

    public BigDecimal getUnitMaximum() {
        return unitMaximum;
    }

    public void setUnitMaximum(BigDecimal unitMaximum) {
        this.unitMaximum = unitMaximum;
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

    /**
     * Encuentra la instancia <tt>KardexDetail</tt> para la bussinesEntityId y bussinesEntityType y el operationType
     * @param bussinesEntityType
     * @param bussinesEntityId
     * @param operationType
     * @return El <tt>KardexDetail</tt> que coinicide con los criterios datos o null en caso contrario
     */
    public KardexDetail findKardexDetail(String bussinesEntityType, Long bussinesEntityId, KardexDetail.OperationType operationType) {

        return getKardexDetails().stream()
                .filter(detail -> bussinesEntityId.equals(detail.getBussinesEntityId())
                && bussinesEntityType.equals(detail.getBussinesEntityType())
                && operationType.equals(detail.getOperationType()))
                .findAny()
                .orElse(null);
    }
}
