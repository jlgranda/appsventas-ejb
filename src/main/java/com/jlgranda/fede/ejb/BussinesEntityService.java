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

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.BussinesEntity;
import org.jpapi.model.BussinesEntity_;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Stateless
public class BussinesEntityService extends BussinesEntityHome<BussinesEntity> 
        implements Serializable {
    
    Logger  logger = LoggerFactory.getLogger(BussinesEntityService.class);
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init(){
        setEntityManager(em);
        setEntityClass(BussinesEntity.class);
    }
    
    public List<BussinesEntity> getBussinesEntitys(){
        return this.findAll(BussinesEntity.class);
    }

    
    @Override
    public BussinesEntity createInstance() {

        BussinesEntity _instance = new BussinesEntity();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }

    public long count() {
        return super.count(BussinesEntity.class); 
    }
    
    public BussinesEntity findByName(final String name) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<BussinesEntity> query = builder.createQuery(BussinesEntity.class);

        Root<BussinesEntity> bussinesEntity = query.from(BussinesEntity.class);

        query.where(builder.equal(bussinesEntity.get(BussinesEntity_.name), name));

        return getSingleResult(query);
    }

    public List<BussinesEntity> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<BussinesEntity> query = builder.createQuery(BussinesEntity.class);

        Root<BussinesEntity> from = query.from(BussinesEntity.class);
        query.select(from).orderBy(builder.desc(from.get(BussinesEntity_.name)));
        return getResultList(query, maxresults, firstresult);
    }
}
