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
package com.jlgranda.fede.ejb.compras;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.compras.Proveedor;
import org.jlgranda.fede.model.compras.Proveedor_;
import org.jlgranda.fede.model.document.EmissionType;
import org.jlgranda.fede.model.gifts.GiftEntity;
import org.jlgranda.fede.model.gifts.GiftEntity_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Stateless
public class ProveedorService  extends BussinesEntityHome<Proveedor> implements Serializable {
    
    Logger logger = LoggerFactory.getLogger(ProveedorService.class);

    @PersistenceContext
    EntityManager em;

    public ProveedorService() {
    }

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Proveedor.class);
    }


    @Override
    public Proveedor createInstance() {

        Proveedor _instance = new Proveedor();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 7));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    public long count() {
        return super.count(Proveedor.class); 
    }

    
    public Proveedor findByName(final String name) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Proveedor> query = builder.createQuery(Proveedor.class);

        Root<Proveedor> proveedor = query.from(Proveedor.class);

        query.where(builder.equal(proveedor.get(Proveedor_.name), name));

        return getSingleResult(query);
    }
    
    
    public List<Proveedor> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Proveedor> query = builder.createQuery(Proveedor.class);

        Root<Proveedor> from = query.from(Proveedor.class);
        query.select(from).orderBy(builder.desc(from.get(Proveedor_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
}
