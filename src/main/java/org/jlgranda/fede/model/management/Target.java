/*
 * Copyright (C) 2015 jlgranda
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
package org.jlgranda.fede.model.management;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jpapi.model.BussinesEntity;

/*
 * @author dianita
 */
@Entity
@Table(name = "Target")
@DiscriminatorValue(value = "TGT")
@PrimaryKeyJoinColumn(name = "id")
public class Target extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = 449175027015485864L;

    public enum Type {

        CAUSE,
        EFFECT;

        private Type() {
        }
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Target.Type targetType;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "periodFrom", nullable = false)
    private Date from;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "periodTo", nullable = false)
    private Date to;
    private String unit;
    private BigDecimal value;
    private Long sequence;
    @ManyToOne
    private Measure measure;
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Initiative> initiatives = new org.apache.commons.collections.list.TreeList();
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Method> methods = new org.apache.commons.collections.list.TreeList();
    @OneToMany(mappedBy = "target", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TargetValue> values = new org.apache.commons.collections.list.TreeList();

    public Type getTargetType() {
        return targetType;
    }

    public void setTargetType(Type targetType) {
        this.targetType = targetType;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public List<Initiative> getInitiatives() {
        return initiatives;
    }

    public void setInitiatives(List<Initiative> initiatives) {
        this.initiatives = initiatives;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public void setMethods(List<Method> methods) {
        this.methods = methods;
    }

    public boolean addMethod(Method method) {
        if (!this.getMethods().contains(method)) {
            method.setTarget(this);
            return this.getMethods().add(method);
        }
        return false;
    }

    public boolean removeMethod(Method method) {
        method.setTarget(null);
        return this.getMethods().remove(method);
    }

    public boolean addTargetValue(TargetValue targetValue) {
        if (!this.getValues().contains(targetValue)) {
            targetValue.setTarget(this);
            return this.getValues().add(targetValue);
        }
        return false;
    }

    public boolean removeTargetValue(TargetValue targetValue) {
        targetValue.setTarget(null);
        return this.getValues().remove(targetValue);
    }
    
    public boolean addInitiative(Initiative initiative) {
        if (!this.getInitiatives().contains(initiative)) {
            initiative.setTarget(this);
            return this.getInitiatives().add(initiative);
        }
        return false;
    }

    public boolean removeInitiative(Initiative initiative) {
        initiative.setTarget(null);
        return this.getInitiatives().remove(initiative);
    }

    public List<TargetValue> getValues() {
        return values;
    }

    public void setValues(List<TargetValue> values) {
        this.values = values;
    }

    @Transient
    public BigDecimal getCurrentValue() {
        BigDecimal _value = new BigDecimal(80);
        for (TargetValue tv : getValues()) {
            if (Boolean.TRUE.equals(tv.getCurrent())) {
                _value = tv.getValue();
                break;
            }
        }
        return _value;
    }

    public Long getSequence() {
        return sequence;
    }

    public void setSequence(Long sequence) {
        this.sequence = sequence;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 31). // two randomly chosen prime numbers
                // if deriving: appendSuper(super.hashCode()).
                append(getName()).
                append(getType()).
                toHashCode();
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
        Target other = (Target) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }

    @Override
    public String getCanonicalPath() {
        StringBuilder path = new StringBuilder();
        path.append(getMeasure().getCanonicalPath());
        path.append(BussinesEntity.SEPARATOR); //TODO hacer que sea personalizable
        path.append(getMeasure().getName());
        return path.toString();
    }

    @Override
    public String toString() {
        /*return "org.eqaula.glue.model.management.Target[ "
         + "id=" + getId() + ","
         + "name=" + getName() + ","
         + "type=" + getType() + ","
         + " ]";*/
        return getName();
    }

    public List<Target.Type> getTargetTypes() {
        return Arrays.asList(Target.Type.values());
    }
}
