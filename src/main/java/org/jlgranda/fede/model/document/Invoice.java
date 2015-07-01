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
package org.jlgranda.fede.model.document;

import java.util.Objects;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.profile.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A Invoice document in matidoo
 * @author jlgranda
 */
@Entity
@Table(name = "INVOICE")
@DiscriminatorValue(value = "INV")
@PrimaryKeyJoinColumn(name = "invoiceId")
public class Invoice extends BussinesEntity {
    
    private static Logger log = LoggerFactory.getLogger(Invoice.class);
    
    private EnvironmentType environmentType;
    
    private EmissionType emissionType;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "emisor", nullable = false)
    private Subject emisor;
    
    private String password;
    
    private DocumentType documentType;
    
    private String establishment;
    
    private String emissionPoint;
    
    private String sequencial;
    
    private String principalAddres;

    public static Logger getLog() {
        return log;
    }

    public static void setLog(Logger log) {
        Invoice.log = log;
    }

    public EnvironmentType getEnvironmentType() {
        return environmentType;
    }

    public void setEnvironmentType(EnvironmentType environmentType) {
        this.environmentType = environmentType;
    }

    public EmissionType getEmissionType() {
        return emissionType;
    }

    public void setEmissionType(EmissionType emissionType) {
        this.emissionType = emissionType;
    }

    public Subject getEmisor() {
        return emisor;
    }

    public void setEmisor(Subject emisor) {
        this.emisor = emisor;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentCode) {
        this.documentType = documentCode;
    }

    public String getEstablishment() {
        return establishment;
    }

    public void setEstablishment(String establishment) {
        this.establishment = establishment;
    }

    public String getEmissionPoint() {
        return emissionPoint;
    }

    public void setEmissionPoint(String emissionPoint) {
        this.emissionPoint = emissionPoint;
    }

    public String getSequencial() {
        return sequencial;
    }

    public void setSequencial(String sequencial) {
        this.sequencial = sequencial;
    }

    public String getPrincipalAddres() {
        return principalAddres;
    }

    public void setPrincipalAddres(String principalAddres) {
        this.principalAddres = principalAddres;
    }


    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.documentType);
        hash = 59 * hash + Objects.hashCode(this.emissionPoint);
        hash = 59 * hash + Objects.hashCode(this.sequencial);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Invoice other = (Invoice) obj;
        if (this.documentType != other.documentType) {
            return false;
        }
        if (!Objects.equals(this.emissionPoint, other.emissionPoint)) {
            return false;
        }
        if (!Objects.equals(this.sequencial, other.sequencial)) {
            return false;
        }
        return true;
    }
}
