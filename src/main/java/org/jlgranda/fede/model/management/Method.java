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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.jpapi.model.BussinesEntity;

/*
 * @author dianita
 */
@Entity
@Table(name = "Method")
@DiscriminatorValue(value = "MTHD")
@PrimaryKeyJoinColumn(name = "id")
public class Method extends BussinesEntity implements Serializable {

    private static final long serialVersionUID = -2099259320492517035L;

    public enum Type {

        SEMAPHORE,
        GRAPH;

        private Type() {
        }
    }
    
    public enum ContentType {
        GLUE,
        RULE,
        SCRIPT,
        SOAP,
        REST;

        private ContentType() {
        }
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Method.Type methodType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Method.ContentType contentType;
    
    @Column(length = 4096)
    private String content;
   
    
    @ManyToOne
    private Target target;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
    
    
    public Type getMethodType() {
        return methodType;
    }

    public void setMethodType(Type methodType) {
        this.methodType = methodType;
    }

    public ContentType getContentType() {
        return contentType;
    }

    public void setContentType(ContentType contentType) {
        this.contentType = contentType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
        Method other = (Method) obj;
        return new EqualsBuilder().
                // if deriving: appendSuper(super.equals(obj)).
                append(getName(), other.getName()).
                append(getType(), other.getType()).
                isEquals();
    }

    @Override
    public String getCanonicalPath() {
        StringBuilder path = new StringBuilder();
        path.append(getTarget().getCanonicalPath());
        path.append(BussinesEntity.SEPARATOR); //TODO hacer que sea personalizable
        path.append(getName());
        return path.toString();
    }

    @Override
    public String toString() {
        /*return "org.eqaula.glue.model.management.Method[ "
         + "id=" + getId() + ","
         + "name=" + getName() + ","
         + "type=" + getType() + ","
         + " ]";*/
        return getName();
    }
    
    public List<Method.Type> getMethodTypes() {
        return Arrays.asList(Method.Type.values());
    }
    
    public List<Method.ContentType> getContentTypes() {
        return Arrays.asList(Method.ContentType.values());
    }
    
    @Transient
    private List<Wrap> wrappers = new ArrayList<Wrap>();
    
    public List<Wrap> getWrappers() {
        return wrappers;
    }
    
    public void addWrap(Wrap wrap){
        this.wrappers.add(wrap);
    }
    
    public static class Wrap {
        
        private int  sequence;

        private String value;

        public Wrap() {

        }

        public Wrap(final int sequence) {
            this.sequence = sequence;
            this.value = "";
        }
        
        public Wrap(final String value) {
            this.value = value;
        }

        public int getSequence() {
            return this.sequence;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }


        public String toString() {
            return "Wrap(" + this.sequence + "/" + this.value + ")";
        }
    }
}
