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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TOSHIBA
 */
@Entity
@Table(name = "sri_catastros_ruc")
@PrimaryKeyJoinColumn(name = "catastro_id")
@NamedQueries({})
public class SRICatastrosRuc implements Serializable {
   
//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 20)
//    @Column(name = "digital_cert_id")
//    private String digitalCertId;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "catastro_id", updatable = false, nullable = false)
    private Long catastroId;
    
//    @Column(length = 1024,  name = "digital_cert", nullable = false)
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] digitalCert;
    
    @Column(name = "numero_ruc", nullable = false)
    private String numeroRuc;
    
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;
    
    @Column(name = "nombre_comercia", nullable = false)
    private String nombreComercia;
    
    @Column(name = "estado_contribuyente", nullable = false)
    private String estadoContribuyente;
    
    @Column(name = "clase_contribuyente", nullable = false)
    private String claseContribuyente;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_actividades")
    private Date fechaInicioActividades;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_actualizacion")
    private Date fechaActualizacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_suspencion_definitiva")
    private Date fechaSuspencionDefinitiva;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fechar_reinicio_actividades")
    private Date fecharReinicioActividades;
    
    @Column(name = "obligado", nullable = false)
    private String obligado;
    
    @Column(name = "tipo_contribuyente", nullable = false)
    private String tipoContribuyente;
    
    @Column(name = "numero_establecimiento", nullable = false)
    private String numeroEstablecimiento;
    
    @Column(name = "nombre_fantasia_comercial", nullable = false)
    private String nombreFantasiaComercial;
    
    @Column(name = "estado_establecimiento", nullable = false)
    private String estadoEstablecimiento;
    
    @Column(name = "descripcion_provincia", nullable = false)
    private String descripcionProvincia;
    
    @Column(name = "descripcion_canton", nullable = false)
    private String descripcionCanton;
    
    @Column(name = "descripcion_parroquia", nullable = false)
    private String descripcionParroquia;
    
    @Column(name = "codigo_ciu", nullable = false)
    private String codigoCiu;
    
    @Column(name = "actividad_economica", nullable = false)
    private String actividadEconomica;

//
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "owner", nullable = true)
//    private Subject owner;
//
//    @Column(nullable = true)
//    protected Boolean active;

    public Long getCatastroId() {
        return catastroId;
    }

    public void setCatastroId(Long catastroId) {
        this.catastroId = catastroId;
    }

    public String getNumeroRuc() {
        return numeroRuc;
    }

    public void setNumeroRuc(String numeroRuc) {
        this.numeroRuc = numeroRuc;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreComercia() {
        return nombreComercia;
    }

    public void setNombreComercia(String nombreComercia) {
        this.nombreComercia = nombreComercia;
    }

    public String getEstadoContribuyente() {
        return estadoContribuyente;
    }

    public void setEstadoContribuyente(String estadoContribuyente) {
        this.estadoContribuyente = estadoContribuyente;
    }

    public String getClaseContribuyente() {
        return claseContribuyente;
    }

    public void setClaseContribuyente(String claseContribuyente) {
        this.claseContribuyente = claseContribuyente;
    }

    public Date getFechaInicioActividades() {
        return fechaInicioActividades;
    }

    public void setFechaInicioActividades(Date fechaInicioActividades) {
        this.fechaInicioActividades = fechaInicioActividades;
    }

    public Date getFechaActualizacion() {
        return fechaActualizacion;
    }

    public void setFechaActualizacion(Date fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }

    public Date getFechaSuspencionDefinitiva() {
        return fechaSuspencionDefinitiva;
    }

    public void setFechaSuspencionDefinitiva(Date fechaSuspencionDefinitiva) {
        this.fechaSuspencionDefinitiva = fechaSuspencionDefinitiva;
    }

    public Date getFecharReinicioActividades() {
        return fecharReinicioActividades;
    }

    public void setFecharReinicioActividades(Date fecharReinicioActividades) {
        this.fecharReinicioActividades = fecharReinicioActividades;
    }

    public String getObligado() {
        return obligado;
    }

    public void setObligado(String obligado) {
        this.obligado = obligado;
    }

    public String getNumeroEstablecimiento() {
        return numeroEstablecimiento;
    }

    public void setNumeroEstablecimiento(String numeroEstablecimiento) {
        this.numeroEstablecimiento = numeroEstablecimiento;
    }

    public String getNombreFantasiaComercial() {
        return nombreFantasiaComercial;
    }

    public void setNombreFantasiaComercial(String nombreFantasiaComercial) {
        this.nombreFantasiaComercial = nombreFantasiaComercial;
    }

    public String getEstadoEstablecimiento() {
        return estadoEstablecimiento;
    }

    public void setEstadoEstablecimiento(String estadoEstablecimiento) {
        this.estadoEstablecimiento = estadoEstablecimiento;
    }

    public String getDescripcionProvincia() {
        return descripcionProvincia;
    }

    public void setDescripcionProvincia(String descripcionProvincia) {
        this.descripcionProvincia = descripcionProvincia;
    }

    public String getDescripcionCanton() {
        return descripcionCanton;
    }

    public void setDescripcionCanton(String descripcionCanton) {
        this.descripcionCanton = descripcionCanton;
    }

    public String getDescripcionParroquia() {
        return descripcionParroquia;
    }

    public void setDescripcionParroquia(String descripcionParroquia) {
        this.descripcionParroquia = descripcionParroquia;
    }

    public String getCodigoCiu() {
        return codigoCiu;
    }

    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(String tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public void setCodigoCiu(String codigoCiu) {
        this.codigoCiu = codigoCiu;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public void setCreatedOn(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setLastUpdate(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setStatus(String toString) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setConfirmed(boolean b) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setActivationTime(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setExpirationTime(Date addDays) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setAuthor(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
