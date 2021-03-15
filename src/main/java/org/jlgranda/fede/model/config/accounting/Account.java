/*
 * Copyright (C) 2021 jlgranda
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
package org.jlgranda.fede.model.config.accounting;

//import com.sun.org.slf4j.internal.LoggerFactory;
//import com.sun.org.slf4j.internal.LoggerFactory;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;
import org.slf4j.Logger;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "Account")
@NamedQueries({ @NamedQuery(name = "Account.findByName", query = "select s FROM Account s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery(name = "Account.findByNameAndOwner", query = "select s FROM Account s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1")})
public class Account extends PersistentObject<Account> implements Comparable<Account>, Serializable {
    
    private static final long serialVersionUID = -6428094275651428620L;
    
    @Column(nullable = true, length = 1024)
    protected Long cuentapadreid;
    
    public Account(){
        
    }
    public Account(String code, String name){
        super();
        setCode(code); 
        setName(name);        
    }

    public Long getCuentapadreid() {
        return cuentapadreid;
    }

    public void setCuentapadreid(Long cuentapadreid) {
        this.cuentapadreid = cuentapadreid;
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
        Account other = (Account) obj;
        return new org.apache.commons.lang.builder.EqualsBuilder().
                append(getId(), other.getId()).
                append(getCode(), other.getCode()).
                append(getName(), other.getName()).
                isEquals();
    }
    
    @Override
    public String toString() {
        return String.valueOf(getId());
    }
    
    @Override
    public int compareTo(Account t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
        
}
