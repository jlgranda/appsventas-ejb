/*
 * Copyright (C) 2022 jlgranda
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
package org.jlgranda.fede.model.compras;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.jpapi.model.Organization;
import org.jpapi.model.DeletableObject;

/**
 * Especialización por organización de la entidad Subject, el campo owner indica
 * a que Subject pertenece.
 *
 * @author jlgranda
 */
@Entity
@Table(name = "Proveedor")
@NamedQueries({
    @NamedQuery(name = "Proveedor.findByOwner", query = "SELECT e FROM Proveedor e WHERE e.owner = ?1 and e.deleted = false"),
    @NamedQuery(name = "Proveedor.findByOrganization", query = "SELECT e FROM Proveedor e WHERE e.organization = ?2 and e.deleted = false"),
    @NamedQuery(name = "Proveedor.findByOrganizationCodeOrName", query = "SELECT e FROM Proveedor e WHERE e.organization = :organization  and e.deleted = false and (lower(e.owner.code) like lower(:code) or lower(e.owner.firstname) like lower(:firstname) or lower(e.owner.surname) like lower(:surname))"),
    @NamedQuery(name = "Proveedor.findByOwnerCodeAndName", query = "SELECT e FROM Proveedor e WHERE lower(e.owner.code) like lower(:code) or lower(e.owner.firstname) like lower(:firstname) or lower(e.owner.surname) like lower(:surname)"),
})
public class Proveedor extends DeletableObject implements Comparable<Proveedor>, Serializable {

    private static final long serialVersionUID = -1016927888119107404L;

    @Column(name = "credito_maximo_monto")
    private Long creditoMaximoMonto;

    @Column(name = "credito_maximo_dias")
    private Long creditoMaximoDias;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_preferida_pago")
    private Date fechaPreferidaPago;

    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true, nullable = true)
    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Date getFechaPreferidaPago() {
        return fechaPreferidaPago;
    }

    public void setFechaPreferidaPago(Date fechaPreferidaPago) {
        this.fechaPreferidaPago = fechaPreferidaPago;
    }

    public Long getCreditoMaximoMonto() {
        return creditoMaximoMonto;
    }

    public void setCreditoMaximoMonto(Long creditoMaximoMonto) {
        this.creditoMaximoMonto = creditoMaximoMonto;
    }

    public Long getCreditoMaximoDias() {
        return creditoMaximoDias;
    }

    public void setCreditoMaximoDias(Long creditoMaximoDias) {
        this.creditoMaximoDias = creditoMaximoDias;
    }

    @Override
    public int compareTo(Proveedor t) {
        if (getId() == null && t != null && t.getId() != null) {
            return -1;
        }
        if (getId() != null && t == null) {
            return 1;
        }
        return getId().compareTo(t.getId());
    }

    @Override
    public String toString() {
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }

}
