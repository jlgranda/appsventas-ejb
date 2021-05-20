/*
 * Copyright (C) 2021 kellypaulinc
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
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jlgranda.fede.model.document.FacturaElectronica;
import org.jpapi.model.PersistentObject;

/**
 *
 * @author kellypaulinc
 */
@Entity
@Table(name = "Kardex_detail")
@NamedQueries({
    @NamedQuery(name = "KardexDetail.findTotalQuantityByKardexAndCode", query = "SELECT kd.operation_type, sum(kd.quantity) FROM KardexDetail kd WHERE kd.kardex=?1 and kd.code=?2 GROUP BY kd.operation_type ORDER BY kd.operation_type"),
})
public class KardexDetail extends PersistentObject implements Comparable<KardexDetail>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "kardex_id", insertable = true, updatable = true, nullable = true)
    private Kardex kardex;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private KardexDetail.OperationType operation_type;
    
    private BigDecimal unit_value;
    private Long quantity;
    private BigDecimal total_value;
    private Long cummulative_quantity;
    private BigDecimal cummulative_total_value;
    
    @ManyToOne
    @JoinColumn (name = "facturaElectronica_id", insertable = true, updatable = true, nullable = true)
    private FacturaElectronica facturaElectronica; //Detalle de compra
    
    @ManyToOne
    @JoinColumn (name = "invoice_id", insertable = true, updatable = true, nullable = true)
    private Invoice invoice; //Detalle de venta
    
    public enum OperationType{
        EXISTENCIA_INICIAL,
        PRODUCCION,
        COMPRA,
        VENTA,
        DEVOLUCION_COMPRA,
        DEVOLUCION_VENTA,
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public OperationType getOperation_type() {
        return operation_type;
    }

    public void setOperation_type(OperationType operation_type) {
        this.operation_type = operation_type;
    }
    
    public BigDecimal getUnit_value() {
        return unit_value;
    }

    public void setUnit_value(BigDecimal unit_value) {
        this.unit_value = unit_value;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotal_value() {
        return total_value;
    }

    public void setTotal_value(BigDecimal total_value) {
        this.total_value = total_value;
    }

    public Long getCummulative_quantity() {
        return cummulative_quantity;
    }

    public void setCummulative_quantity(Long cummulative_quantity) {
        this.cummulative_quantity = cummulative_quantity;
    }

    public BigDecimal getCummulative_total_value() {
        return cummulative_total_value;
    }

    public void setCummulative_total_value(BigDecimal cummulative_total_value) {
        this.cummulative_total_value = cummulative_total_value;
    }
    
    public FacturaElectronica getFacturaElectronica() {
        return facturaElectronica;
    }

    public void setFacturaElectronica(FacturaElectronica facturaElectronica) {
        this.facturaElectronica = facturaElectronica;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }
    
    @Override
    public int hashCode(){
        HashCodeBuilder hcb = new HashCodeBuilder(17, 31);
        hcb.append(getId());
        return hcb.toHashCode();
    }
    
    @Override
    public boolean equals(final Object obj){
        if(this==obj){
            return true;
        }
        if(obj == null){
            return false;
        }
        if(getClass()!=obj.getClass()){
            return false;
        }
        
        KardexDetail other = (KardexDetail) obj;
        EqualsBuilder eb = new EqualsBuilder();
        
//        eb.append(getId(), other.getId());
        eb.append(getId(), other.getId()).append(getOperation_type(), other.getOperation_type());
        
        return eb.isEquals();
    }
    
    @Override
    public int compareTo(KardexDetail other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }                                                                                                                                           
    
}
