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
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author Jorge
 */
@Entity
@Table(name = "tarea")
@NamedQueries({
    @NamedQuery(name = "Tarea.findLast", query = "select i FROM Tarea i where i.owner=?1 ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.findLasts", query = "select i FROM Tarea i where i.owner=?1 ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.findLastByInstanciaProceso", query = "select i FROM Tarea i where i.instanciaProceso=?1 ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.findLastsByInstanciaProceso", query = "select i FROM Tarea i where i.instanciaProceso=?1 ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.findLastsByAuthor", query = "select i FROM Tarea i where i.author=?1 ORDER BY i.id DESC"),
    @NamedQuery(name = "Tarea.findLastsByOwner", query = "select i FROM Tarea i where i.owner=?1 and i.estadoTipo=?2 ORDER BY i.id DESC "),
    @NamedQuery(name = "Tarea.countByOwner", query = "select count(i) FROM Tarea i WHERE i.owner = ?1"),
    @NamedQuery(name = "Tarea.countBussinesEntityByTagAndOwner", query = "select count(m.bussinesEntity) FROM Group g JOIN g.memberships m WHERE g.code=?1 and m.bussinesEntity.owner = ?2"),
    @NamedQuery(name = "Tarea.countBussinesEntityByOwner", query = "select count(t) FROM Tarea t WHERE t.owner = ?1")})
public class Tarea extends BussinesEntity implements Serializable {

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date fechaEnvio;
    @Basic(optional = false)
    @NotNull
    private String Departamento;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private UrgenciaTipo urgenciaTipo;
    @Enumerated(value = EnumType.STRING)
    @Column(nullable = true)
    private EstadoTipo estadoTipo;
    @OneToMany(mappedBy = "tarea", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Documento> documentos;

    @JoinColumn(name = "proceso_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private InstanciaProceso instanciaProceso;

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

    public InstanciaProceso getInstanciaProceso() {
        return instanciaProceso;
    }

    public void setInstanciaProceso(InstanciaProceso instanciaProceso) {
        this.instanciaProceso = instanciaProceso;
    }
    
    public boolean checkStatus(EstadoTipo estadoTipo){
        if (estadoTipo == null) return false;
        return estadoTipo.equals(this.getEstadoTipo());
    }

}
