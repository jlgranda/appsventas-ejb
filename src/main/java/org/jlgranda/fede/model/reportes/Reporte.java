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
package org.jlgranda.fede.model.reportes;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.jpapi.model.DeletableObject;
import org.jpapi.model.Organization;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "reporte")
@NamedQueries({
    @NamedQuery(name = "Reporte.findByProductAndOrg", query = "SELECT r FROM Reporte r WHERE r.id = ?1 and r.organization = ?2 and r.deleted = false ORDER BY r.id DESC"),
    @NamedQuery(name = "Reporte.findByModuloAndOrg", query = "SELECT r FROM Reporte r WHERE r.modulo = ?1 and r.organization = ?2 and r.deleted = false ORDER BY r.name ASC"),
})
public class Reporte extends DeletableObject<Reporte> implements Comparable<Reporte>, Serializable {
    
    /**
     * Ruta del archivo jrxml para jasper reports
     */
    @Column( name = "ruta_archivo_xml")
    private String rutaArchivoXml;
    
    /**
     * Módulo al que pertenece el reporte
     */
    @Column( name = "modulo")
    private String modulo;
    
    /**
     * El tipo de reporte: Lista, Individual
     */
    @Column( name = "tipo")
    private String tipo;
    
    /**
     * Parametros dinámicos del reporte
     */
    @Column( name = "parametros")
    private String parametros;
    
    @ManyToOne(optional = true)
    @JoinColumn(name = "organization_id", insertable = true, updatable = true)
    private Organization organization;

    public String getRutaArchivoXml() {
        return rutaArchivoXml;
    }

    public void setRutaArchivoXml(String rutaArchivoXml) {
        this.rutaArchivoXml = rutaArchivoXml;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getParametros() {
        return parametros;
    }

    public void setParametros(String parametros) {
        this.parametros = parametros;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    
    @Override
    public int compareTo(Reporte other) {
        return this.createdOn.compareTo(other.getCreatedOn());
    }
    
}
