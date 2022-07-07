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

/**
 *
 * @author TOSHIBA
 */
@Entity
@Table(name = "sri_catastros_rimpe")
@PrimaryKeyJoinColumn(name = "catastro_rimpe_id")
@NamedQueries({})
public class SRICatastrosRimpe implements Serializable {

//    @Id
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 20)
//    @Column(name = "digital_cert_id")
//    private String digitalCertId;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "catastro_rimpe_id", updatable = false, nullable = false)
    private Long catastroRimpeId;

//    @Column(length = 1024,  name = "digital_cert", nullable = false)
//    @Basic(fetch = FetchType.LAZY)
//    private byte[] digitalCert;
    @Column(name = "numero_ruc", nullable = false)
    private String numeroRuc;

    @Column(name = "razon_social", nullable = false)
    private String razonSocial;

    @Column(name = "zona", nullable = false)
    private String zona;

    @Column(name = "regimen", nullable = false)
    private String regimen;

    @Column(name = "negocio_popular", nullable = false)
    private String negocioPopular;

    public Long getCatastroRimpeId() {
        return catastroRimpeId;
    }

    public void setCatastroRimpeId(Long catastroRimpeId) {
        this.catastroRimpeId = catastroRimpeId;
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

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        this.regimen = regimen;
    }

    public String getNegocioPopular() {
        return negocioPopular;
    }

    public void setNegocioPopular(String negocioPopular) {
        this.negocioPopular = negocioPopular;
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
