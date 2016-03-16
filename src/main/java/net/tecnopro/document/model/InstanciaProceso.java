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
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "instancia_proceso")
@NamedQueries({
    @NamedQuery(name = "instanciaProceso.findLast", query = "select p FROM InstanciaProceso p where p.owner=?1 ORDER BY p.id DESC"),})
public class InstanciaProceso extends BussinesEntity implements Serializable {

    @OneToMany(mappedBy = "instanciaProceso")
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
}
