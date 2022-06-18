/*
 * Copyright (C) 2022 TOSHIBA
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
package org.jlgranda.fede.model.sri;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.jpapi.model.profile.Subject;

/**
 *
 * @author TOSHIBA
 */
@Entity
@Table(name = "sri_digital_cert")
@PrimaryKeyJoinColumn(name = "digital_cert_id")
@NamedQueries({})

public class SriDigitalCert implements Serializable {
   
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 20)
//    @Column(name = "digital_cert_id")
//    private String digitalCertId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "digital_cert_id", updatable = false, nullable = false)
    private Long digitalCertId;
    
    @Column(length = 1024,  name = "digital_cert", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] digitalCert;
    
    @Column(name = "password", nullable = false)
    private String password;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "insert_date")
    private Date insertDate;

    @ManyToOne(optional = true)
    @JoinColumn(name = "owner", nullable = true)
    private Subject owner;

    @Column(nullable = true)
    protected Boolean active;

    public Long getDigitalCertId() {
        return digitalCertId;
    }

    public void setDigitalCertId(Long digitalCertId) {
        this.digitalCertId = digitalCertId;
    }

    public byte[] getDigitalCert() {
        return digitalCert;
    }

    public void setDigitalCert(byte[] digitalCert) {
        this.digitalCert = digitalCert;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public Subject getOwner() {
        return owner;
    }

    public void setOwner(Subject owner) {
        this.owner = owner;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }   
    

}
