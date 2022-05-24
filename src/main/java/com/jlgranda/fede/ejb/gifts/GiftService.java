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
package com.jlgranda.fede.ejb.gifts;

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.util.Dates;
import org.jpapi.model.StatusType;
import org.jlgranda.fede.model.document.EmissionType;
import org.jlgranda.fede.model.gifts.GiftEntity;
import org.jlgranda.fede.model.gifts.GiftEntity_;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Generador de regalos para appsventas
 * @author jlgranda
 */
@Stateless
public class GiftService extends BussinesEntityHome<GiftEntity> {

    private static final long serialVersionUID = -127782184949712690L;

    Logger logger = LoggerFactory.getLogger(GiftService.class);

    @PersistenceContext
    EntityManager em;

    public GiftService() {
    }

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(GiftEntity.class);
    }


    @Override
    public GiftEntity createInstance() {

        GiftEntity _instance = new GiftEntity();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 7));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setShared(false);
        _instance.setSharingCount(0);
        _instance.setSharingDate(null);
        _instance.setDiscountValue(BigDecimal.ONE);
        _instance.setDiscountType(EmissionType.CASH_DISCOUNT.toString());
        return _instance;
    }
    
    public GiftEntity find(final long id, boolean lazily) {
        GiftEntity g = null;
        g = super.find(id);
        if (lazily){
            g.getMemberships().size(); //Fuerza la carga de memberships
            g.getAttributes().size();
        }
        
        return g;
    }

    public long count() {
        return super.count(GiftEntity.class); 
    }

    
    public GiftEntity findByName(final String name) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<GiftEntity> query = builder.createQuery(GiftEntity.class);

        Root<GiftEntity> giftEntity = query.from(GiftEntity.class);

        query.where(builder.equal(giftEntity.get(GiftEntity_.name), name));

        return getSingleResult(query);
    }
    
    
    public List<GiftEntity> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<GiftEntity> query = builder.createQuery(GiftEntity.class);

        Root<GiftEntity> from = query.from(GiftEntity.class);
        query.select(from).orderBy(builder.desc(from.get(GiftEntity_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
}
