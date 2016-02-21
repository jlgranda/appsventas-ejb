/*
 * Copyright (C) 2016 Jorge
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
package net.tecnopro.document.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "tarea")
@NamedQueries({
    @NamedQuery(name = "Tarea.findLast", query = "select i FROM Tarea i ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.findLasts", query = "select i FROM Tarea i ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.countByOwner", query = "select count(i) FROM Tarea i WHERE i.owner = ?1"),
    @NamedQuery(name = "Tarea.countBussinesEntityByTagAndOwner", query = "select count(m.bussinesEntity) FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2"),
    @NamedQuery(name = "Tarea.countBussinesEntityByOwner", query = "select count(f) FROM Tarea f WHERE f.owner = ?1")})
@XmlRootElement
public class Tarea extends BussinesEntity implements Serializable {

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 250)
    private String Departamento;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private UrgenciaTipo urgenciaTipo;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private EstadoTipo estadoTipo;
    @OneToMany(mappedBy = "tarea")
    private List<Documento> documentos;

    public Tarea() {
        this.documentos = new ArrayList<>();
    }

    public Tarea(String titulo, Date fechaEnvio,
            String Departamento, UrgenciaTipo urgencyType,
            EstadoTipo estadoType) {

        this.Departamento = Departamento;
        this.urgenciaTipo = urgencyType;
        this.estadoTipo = estadoType;
    }

    public Date getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String Departamento) {
        this.Departamento = Departamento;
    }

    public UrgenciaTipo getUrgencyType() {
        return urgenciaTipo;
    }

    public void setUrgencyType(UrgenciaTipo urgencyType) {
        this.urgenciaTipo = urgencyType;
    }

    public EstadoTipo getEstadoTipo() {
        return estadoTipo;
    }

    public void setEstadoTipo(EstadoTipo estadoTipo) {
        this.estadoTipo = estadoTipo;
    }

    public List<Documento> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<Documento> documentos) {
        this.documentos = documentos;
    }

}
