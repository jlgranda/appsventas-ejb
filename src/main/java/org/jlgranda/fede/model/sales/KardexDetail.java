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
import org.jpapi.model.DeletableObject;

/**
 *
 * @author author
 */
@Entity
@Table(name = "Kardex_detail")
@NamedQueries({
    @NamedQuery(name = "KardexDetail.findTotalQuantityByKardexAndCode", query = "SELECT kd.operationType, sum(kd.quantity) FROM KardexDetail kd WHERE kd.kardex=?1 and kd.code=?2 and kd.deleted = false GROUP BY kd.operationType ORDER BY kd.operationType"),
    @NamedQuery(name = "KardexDetail.findByKardexAndBussinesEntityTypeAndBussinesEntityIdAndOperation", query = "SELECT kd FROM KardexDetail kd WHERE kd.kardex = ?1 and kd.bussinesEntityType = ?2 and kd.bussinesEntityId = ?3 and kd.operationType = ?4 and kd.deleted = false"),
    //@NamedQuery(name = "KardexDetail.findByKardexAndFacturaAndOperation", query = "SELECT kd FROM KardexDetail kd WHERE kd.kardex = ?1 and kd.facturaElectronica = ?2 and kd.operationType = ?3 and kd.deleted = false"),
    //@NamedQuery(name = "KardexDetail.findByKardexAndFacturaAndOperation", query = "SELECT kd FROM KardexDetail kd WHERE kd.kardex = ?1 and kd.facturaElectronica = ?2 and kd.operationType = ?3 and kd.deleted = false"),
})
public class KardexDetail extends DeletableObject<KardexDetail> implements Comparable<KardexDetail>, Serializable {

    @ManyToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "kardex_id", insertable = true, updatable = true, nullable = true)
    private Kardex kardex;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "operation_type", nullable = false)
    private KardexDetail.OperationType operationType;
    
    @Column(name = "unit_value")
    private BigDecimal unitValue;
    private BigDecimal quantity;
    @Column(name = "total_value")
    private BigDecimal totalValue;
    @Column(name = "cummulative_quantity")
    private BigDecimal cummulativeQuantity;
    @Column(name = "cummulative_total_value")
    private BigDecimal cummulativeTotalValue;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_ingreso_bodega")
    private Date entryOn;
    
    
    /**
     * Referencia a la factura electronica de origen, para compras
     */
    @Column(name = "bussines_entity_type", nullable = true)
    private String bussinesEntityType;
    
    /**
     * Referencia a la factura electronica de origen, para compras
     */
    @Column(name = "bussines_entity_id", nullable = true)
    private Long bussinesEntityId;

    public enum OperationType{
        EXISTENCIA_INICIAL,
        PRODUCCION,
        COMPRA,
        VENTA,
        SALIDA_INVENTARIO,
        DEVOLUCION_COMPRA,
        DEVOLUCION_VENTA,
    }

    public Kardex getKardex() {
        return kardex;
    }

    public void setKardex(Kardex kardex) {
        this.kardex = kardex;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public BigDecimal getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(BigDecimal unitValue) {
        this.unitValue = unitValue;
    }

    
    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public BigDecimal getCummulativeQuantity() {
        return cummulativeQuantity;
    }

    public void setCummulativeQuantity(BigDecimal cummulativeQuantity) {
        this.cummulativeQuantity = cummulativeQuantity;
    }

    public BigDecimal getCummulativeTotalValue() {
        return cummulativeTotalValue;
    }

    public void setCummulativeTotalValue(BigDecimal cummulativeTotalValue) {
        this.cummulativeTotalValue = cummulativeTotalValue;
    }

    public String getBussinesEntityType() {
        return bussinesEntityType;
    }

    public void setBussinesEntityType(String bussinesEntityType) {
        this.bussinesEntityType = bussinesEntityType;
    }

    public Long getBussinesEntityId() {
        return bussinesEntityId;
    }

    public void setBussinesEntityId(Long bussinesEntityId) {
        this.bussinesEntityId = bussinesEntityId;
    }
    
    public Date getEntryOn() {
        return entryOn;
    }

    public void setEntryOn(Date entryOn) {
        this.entryOn = entryOn;
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
        eb.append(getId(), other.getId()).append(getOperationType(), other.getOperationType());
        
        return eb.isEquals();
    }
    
    @Override
    public int compareTo(KardexDetail other) {
        //return this.createdOn.compareTo(other.getCreatedOn());
        return this.entryOn.compareTo(other.getCreatedOn());
    }                                                                                                                                           
    
}
