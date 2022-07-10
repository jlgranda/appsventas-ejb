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
@Table(name = "sri_catastros_empre_fantasma")
@PrimaryKeyJoinColumn(name = "catastro_empre_id")
@NamedQueries({})
public class SRICatastrosEmpreFantasma implements Serializable {

//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 20)
//    @Column(name = "digital_cert_id")
//    private String digitalCertId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "catastro_empre_id", updatable = false, nullable = false)
    private Long catastroRimpeId;

//    @Column(length = 1024,  name = "digital_cert", nullable = false)
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] digitalCert;
    @Column(name = "numero", nullable = false)
    private String numero;
    
    @Column(name = "numero_ruc", nullable = false)
    private String numeroRuc;
    
    @Column(name = "razon_social", nullable = false)
    private String razonSocial;
    
    @Column(name = "tipo_contribuyente", nullable = false)
    private String tipoContribuyente;
    
    @Column(name = "zona", nullable = false)
    private String zona;
    
    @Column(name = "provincia", nullable = false)
    private String provincia;
    
    @Column(name = "oficio", nullable = false)
    private String oficio;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_notificacion1")
    private Date fechaNotificacion1;
    
    @Column(name = "nro_resolucion", nullable = false)
    private String nroResolucion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_notificacion2")
    private Date fechaNotificacion2;
    
    @Column(name = "estado_ruc", nullable = false)
    private String estadoRuc;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_inicio_calificacion")
    private Date fechaInicioCalificacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_fin_calificacion")
    private Date fechaFinCalificacion;
    
    @Column(name = "resolucion", nullable = false)
    private String resolucion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_notificacion_resolucion")
    private Date fechaNotificacionResolucion;
    
    @Column(name = "num_oficio_reactivacion", nullable = false)
    private String numOficioReactivacion;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_notificacion3")
    private Date fechaNotificacion3;
    
    @Column(name = "estado", nullable = false)
    private String esatdo;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "fecha_reactivacion")
    private Date fechaReactivacion;
    
    @Column(name = "instancia_inpugna", nullable = false)
    private String instanciaInpugna;
    
    @Column(name = "estado_inpugna", nullable = false)
    private String estadoInpugna;

    public Long getCatastroRimpeId() {
        return catastroRimpeId;
    }

    public void setCatastroRimpeId(Long catastroRimpeId) {
        this.catastroRimpeId = catastroRimpeId;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
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

    public String getTipoContribuyente() {
        return tipoContribuyente;
    }

    public void setTipoContribuyente(String tipoContribuyente) {
        this.tipoContribuyente = tipoContribuyente;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getOficio() {
        return oficio;
    }

    public void setOficio(String oficio) {
        this.oficio = oficio;
    }

    public Date getFechaNotificacion1() {
        return fechaNotificacion1;
    }

    public void setFechaNotificacion1(Date fechaNotificacion1) {
        this.fechaNotificacion1 = fechaNotificacion1;
    }

    public String getNroResolucion() {
        return nroResolucion;
    }

    public void setNroResolucion(String nroResolucion) {
        this.nroResolucion = nroResolucion;
    }

    public Date getFechaNotificacion2() {
        return fechaNotificacion2;
    }

    public void setFechaNotificacion2(Date fechaNotificacion2) {
        this.fechaNotificacion2 = fechaNotificacion2;
    }

    public String getEstadoRuc() {
        return estadoRuc;
    }

    public void setEstadoRuc(String estadoRuc) {
        this.estadoRuc = estadoRuc;
    }

    public Date getFechaInicioCalificacion() {
        return fechaInicioCalificacion;
    }

    public void setFechaInicioCalificacion(Date fechaInicioCalificacion) {
        this.fechaInicioCalificacion = fechaInicioCalificacion;
    }

    public Date getFechaFinCalificacion() {
        return fechaFinCalificacion;
    }

    public void setFechaFinCalificacion(Date fechaFinCalificacion) {
        this.fechaFinCalificacion = fechaFinCalificacion;
    }

    public String getResolucion() {
        return resolucion;
    }

    public void setResolucion(String resolucion) {
        this.resolucion = resolucion;
    }

    public Date getFechaNotificacionResolucion() {
        return fechaNotificacionResolucion;
    }

    public void setFechaNotificacionResolucion(Date fechaNotificacionResolucion) {
        this.fechaNotificacionResolucion = fechaNotificacionResolucion;
    }

    public String getNumOficioReactivacion() {
        return numOficioReactivacion;
    }

    public void setNumOficioReactivacion(String numOficioReactivacion) {
        this.numOficioReactivacion = numOficioReactivacion;
    }

    public Date getFechaNotificacion3() {
        return fechaNotificacion3;
    }

    public void setFechaNotificacion3(Date fechaNotificacion3) {
        this.fechaNotificacion3 = fechaNotificacion3;
    }

    public String getEsatdo() {
        return esatdo;
    }

    public void setEstado(String esatdo) {
        this.esatdo = esatdo;
    }

    public Date getFechaReactivacion() {
        return fechaReactivacion;
    }

    public void setFechaReactivacion(Date fechaReactivacion) {
        this.fechaReactivacion = fechaReactivacion;
    }

    public String getInstanciaInpugna() {
        return instanciaInpugna;
    }

    public void setInstanciaInpugna(String instanciaInpugna) {
        this.instanciaInpugna = instanciaInpugna;
    }

    public String getEstadoInpugna() {
        return estadoInpugna;
    }

    public void setEstadoInpugna(String estadoInpugna) {
        this.estadoInpugna = estadoInpugna;
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

    public void setActivationTime(Date now) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void setAuthor(Object object) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


}
