/*
 * Copyright (C) 2015 jlgranda
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
package com.jlgranda.fede.ejb;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.document.EmissionType;
import org.jpapi.controller.BussinesEntityHome;
import org.jlgranda.fede.model.document.FacturaElectronica;
import org.jlgranda.fede.model.document.FacturaElectronica_;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Stateless
public class FacturaElectronicaService extends BussinesEntityHome<FacturaElectronica> {

    Logger logger = LoggerFactory.getLogger(FacturaElectronicaService.class);

    private static final long serialVersionUID = -301572035656937837L;

    @PersistenceContext
    EntityManager em;

    public FacturaElectronicaService() {
    }

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(FacturaElectronica.class);
    }
    
    @Override
    public FacturaElectronica save(FacturaElectronica facturaElectronica) {
        super.save(facturaElectronica);
        if (facturaElectronica.getId() != null) {
            this.setId(facturaElectronica.getId());
        } else {
            Map<String, Object> filters = new HashMap<>();
            filters.put("code", facturaElectronica.getCode()); //Recuperar por código
            facturaElectronica = this.find(filters).getResult().get(0); //debe ser único
        }
        return facturaElectronica;
    }


    @Override
    public FacturaElectronica createInstance() {

        FacturaElectronica _instance = new FacturaElectronica();
        _instance.setFechaEmision(Dates.now());
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setEmissionType(EmissionType.PURCHASE_CASH); //Establecer al usuario actual
        _instance.setTotalDescuento(BigDecimal.ZERO);
        _instance.setTotalIVA12(BigDecimal.ZERO);
        _instance.setTotalSinImpuestos(BigDecimal.ZERO);
        _instance.setImporteTotal(BigDecimal.ZERO);
        _instance.setDocumentType(FacturaElectronica.DocumentType.FACTURA);
        return _instance;
    }
    
//    @Override
//    public FacturaElectronica save(FacturaElectronica facturaElectronica){
//        super.save(facturaElectronica);
//        Long id = (Long) getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(facturaElectronica);
//        this.setId(id);
//        return this.find(); //Recarga el objeto
//    }
    
    public FacturaElectronica find(final long id, boolean lazily) {
        FacturaElectronica f = null;
        f = super.find(id);
//        if (lazily){
//            f.getMemberships().size(); //Forza la carga de memberships
//            f.getAttributes().size();
//        }
        
        return f;
    }

    public long count() {
        return super.count(FacturaElectronica.class); 
    }

    
    public FacturaElectronica findByName(final String name) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<FacturaElectronica> query = builder.createQuery(FacturaElectronica.class);

        Root<FacturaElectronica> bussinesEntity = query.from(FacturaElectronica.class);

        query.where(builder.equal(bussinesEntity.get(FacturaElectronica_.name), name));

        return getSingleResult(query);
    }
    
    public List<FacturaElectronica> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<FacturaElectronica> query = builder.createQuery(FacturaElectronica.class);

        Root<FacturaElectronica> from = query.from(FacturaElectronica.class);
        query.select(from).orderBy(builder.desc(from.get(FacturaElectronica_.name)));
        return getResultList(query, maxresults, firstresult);
    }
}
