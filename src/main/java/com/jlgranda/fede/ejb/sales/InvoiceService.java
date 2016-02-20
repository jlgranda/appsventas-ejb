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
package com.jlgranda.fede.ejb.sales;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.document.DocumentType;
import org.jlgranda.fede.model.document.EmissionType;
import org.jlgranda.fede.model.document.EnvironmentType;
import org.jlgranda.fede.model.sales.Invoice;
import org.jlgranda.fede.model.sales.Invoice_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;

/**
 *
 * @author jlgranda
 */
@Stateless
public class InvoiceService extends BussinesEntityHome<Invoice> {
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Invoice.class);
    }

    @Override
    public Invoice createInstance() {

        Invoice _instance = new Invoice();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setEnvironmentType(EnvironmentType.TEST);
        _instance.setEmissionType(EmissionType.SALE);
        _instance.setDocumentType(DocumentType.PRE_INVOICE);
        return _instance;
    }
    
    public Invoice createInstance(DocumentType documentType, EnvironmentType environmentType){

        Invoice _instance = new Invoice();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setEnvironmentType(environmentType);
        _instance.setEmissionType(EmissionType.SALE);
        _instance.setDocumentType(documentType);
        return _instance;
    }
    
    public long count() {
        return super.count(Invoice.class); 
    }
    
    public List<Invoice> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Invoice> query = builder.createQuery(Invoice.class);

        Root<Invoice> from = query.from(Invoice.class);
        query.select(from).orderBy(builder.desc(from.get(Invoice_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
}
