/*
 * Copyright (C) 2016 jlgranda
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
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.jpapi.model.DeletableObject;
import org.jpapi.model.Organization;

/**
 * Instancia de proceso de trámite
 * @author jlgranda
 */
@Entity
@Table(name = "instancia_proceso")
@NamedQueries({
    @NamedQuery(name = "instanciaProceso.findLast", query = "select p FROM InstanciaProceso p where p.owner=?1 ORDER BY p.id DESC"),})
public class InstanciaProceso extends DeletableObject<InstanciaProceso> implements Comparable<InstanciaProceso>, Serializable {

    private static final long serialVersionUID = 931470573990535517L;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true)
    private Organization organization;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "instanciaProceso", fetch = FetchType.LAZY)
    @OrderBy("id ASC")
    private List<Tarea> tareas;

    private ProcesoTipo procesoTipo;

    public ProcesoTipo getProcesoTipo() {
        return procesoTipo;
    }

    public void setProcesoTipo(ProcesoTipo procesoTipo) {
        this.procesoTipo = procesoTipo;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
    
    public void addTarea(Tarea t){
        t.setInstanciaProceso(this);
        this.tareas.add(t);
    }
    
    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int compareTo(InstanciaProceso other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }
}
