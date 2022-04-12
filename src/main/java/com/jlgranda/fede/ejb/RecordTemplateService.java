/*
 * Copyright (C) 2021 author
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

import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.accounting.RecordTemplate;
import org.jlgranda.fede.model.accounting.RecordTemplate_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.CodeType;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author author
 */

@Stateless
public class RecordTemplateService extends BussinesEntityHome<RecordTemplate> {

    private static final long serialVersionUID = -6428094275651428620L;
    
    Logger logger = LoggerFactory.getLogger(RecordTemplateService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(RecordTemplate.class);
    }

    @Override
    public RecordTemplate createInstance() {

        RecordTemplate _instance = new RecordTemplate();
        _instance.setCode(UUID.randomUUID().toString());
        _instance.setCodeType(CodeType.SYSTEM);
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        return _instance;
    }
    
    //soporte para Lazy Data Model
    public long count() {
        return super.count(RecordTemplate.class);
    }

    public List<RecordTemplate> find(int maxresults, int firstresult) {
        
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<RecordTemplate> query = builder.createQuery(RecordTemplate.class);

        Root<RecordTemplate> from = query.from(RecordTemplate.class);
        query.select(from).orderBy(builder.desc(from.get(RecordTemplate_.name)));
        return getResultList(query, maxresults, firstresult);
    }

}