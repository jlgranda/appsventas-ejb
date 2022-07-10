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
package com.jlgranda.fede.ejb.sri;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.sri.SRICatastrosRimpe;
import org.jlgranda.fede.model.sri.SRICatastrosRimpe_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;

/**
 *
 * @author TOSHIBAextends BussinesEntityHome<Subject> 
 */
@Stateless
public class SRICatastrosRimpeService extends BussinesEntityHome<SRICatastrosRimpe> {

    private static final long serialVersionUID = -4487467890746594655L;
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(SRICatastrosRimpe.class);
    }


    public boolean usersExist() {
        Query q = em.createQuery("SELECT U FROM sri_catastros_rimpe U");
        return !q.getResultList().isEmpty();
    }
    
//    @Override
//    public SRICatastrosRuc createInstance() {
//
//        SRICatastrosRuc _instance = new SRICatastrosRuc();
//        _instance.setCreatedOn(Dates.now());
//        _instance.setLastUpdate(Dates.now());
//        _instance.setStatus(StatusType.ACTIVE.toString());
//        _instance.setConfirmed(true);
//        _instance.setActivationTime(Dates.now());
//        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setAuthor(null); //Establecer al usuario actual
//        return _instance;
//    }
    
//    @Override
//    public SriDigitalCert createInstance() {
//
//        SriDigitalCert _instance = new SriDigitalCert();
//        _instance.setCreatedOn(Dates.now());
//        _instance.setLastUpdate(Dates.now());
//        _instance.setStatus(StatusType.ACTIVE.toString());
//        _instance.setActivationTime(Dates.now());
//        //_instance.setExpirationTime();
//        _instance.setAuthor(null); //Establecer al usuario actual
//        _instance.setEnvironmentType(EnvironmentType.TEST);
//        _instance.setEmissionType(EmissionType.SALE);
//        _instance.setDocumentType(DocumentType.PRE_INVOICE);
//        _instance.setEmissionOn(Dates.now()); 
//        
//        return _instance;
//    }
//    public SriDigitalCert createInstance(DocumentType documentType, EnvironmentType environmentType){
//
//        SriDigitalCert _instance = new SriDigitalCert();
//        _instance.setCreatedOn(Dates.now());
//        _instance.setLastUpdate(Dates.now());
//        _instance.setStatus(StatusType.ACTIVE.toString());
//        _instance.setActivationTime(Dates.now());
//        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setAuthor(null); //Establecer al usuario actual
//        _instance.setEnvironmentType(environmentType);
//        _instance.setEmissionType(EmissionType.SALE);
//        _instance.setDocumentType(documentType);
//        return _instance;
//    }
    public long count() {
        return super.count(SRICatastrosRimpe.class);
    }

    
    
    @Override
    public SRICatastrosRimpe createInstance() {

        SRICatastrosRimpe _instance = new SRICatastrosRimpe();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        //_instance.setExpirationTime();
        _instance.setAuthor(null); //Establecer al usuario actual
        
        return _instance;
    }
//    @Override
//    public SRICatastrosRuc createInstance() {
//
//        SRICatastrosRuc _instance = new SRICatastrosRuc();
//        _instance.setCreatedOn(Dates.now());
//        _instance.setLastUpdate(Dates.now());
//        _instance.setStatus(StatusType.ACTIVE.toString());
//        _instance.setConfirmed(true);
//        _instance.setActivationTime(Dates.now());
//        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setAuthor(null); //Establecer al usuario actual
//        return _instance;
//    }
    
    public List<SRICatastrosRimpe> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SRICatastrosRimpe> query = builder.createQuery(SRICatastrosRimpe.class);

        Root<SRICatastrosRimpe> from = query.from(SRICatastrosRimpe.class);
        query.select(from).orderBy(builder.desc(from.get(SRICatastrosRimpe_.NUMERO_RUC)));
        return getResultList(query, maxresults, firstresult);
    }

    public void save(Long id, SRICatastrosRimpe_ _subject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
