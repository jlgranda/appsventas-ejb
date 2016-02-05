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
import org.jlgranda.fede.model.management.BalancedScoreCard;
import org.jlgranda.fede.model.management.BalancedScoreCard_;
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
public class BalancedScoreCardService  extends BussinesEntityHome<BalancedScoreCard> 
        implements Serializable {
    
    Logger  logger = LoggerFactory.getLogger(BalancedScoreCardService.class);
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init(){
        setEntityManager(em);
        setEntityClass(BalancedScoreCard.class);
    }
    
    public List<BalancedScoreCard> getBalancedScoreCards(){
        return this.findAll(BalancedScoreCard.class);
    }

    
    @Override
    public BalancedScoreCard createInstance() {

        BalancedScoreCard _instance = new BalancedScoreCard();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }

    public long count() {
        return super.count(BalancedScoreCard.class); 
    }
    
    public List<BalancedScoreCard> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<BalancedScoreCard> query = builder.createQuery(BalancedScoreCard.class);

        Root<BalancedScoreCard> from = query.from(BalancedScoreCard.class);
        query.select(from).orderBy(builder.desc(from.get(BalancedScoreCard_.name)));
        return getResultList(query, maxresults, firstresult);
    }

}

