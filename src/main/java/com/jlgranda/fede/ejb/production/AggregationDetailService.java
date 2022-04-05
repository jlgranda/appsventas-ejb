/*
 * Copyright (C) 2022 usuario
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
package com.jlgranda.fede.ejb.production;

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
import org.jlgranda.fede.model.production.Aggregation;
import org.jlgranda.fede.model.production.AggregationDetail;
import org.jlgranda.fede.model.production.AggregationDetail_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author usuario
 */
@Stateless
public class AggregationDetailService extends BussinesEntityHome<AggregationDetail> {

    Logger logger = LoggerFactory.getLogger(AggregationDetailService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(AggregationDetail.class);
    }

    @Override
    public AggregationDetail createInstance() {
        AggregationDetail _instance = new AggregationDetail();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setQuantity(BigDecimal.ONE);
        _instance.setCost(BigDecimal.ZERO);
        _instance.setPriceUnit(BigDecimal.ZERO);
        return _instance;
    }

    //Soporte para lazy data model
    public long count() {
        return super.count(AggregationDetail.class);
    }

    public List<AggregationDetail> find(int maxresults, int firstresult) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<AggregationDetail> query = builder.createQuery(AggregationDetail.class);

        Root<AggregationDetail> from = query.from(AggregationDetail.class);
        query.select(from).orderBy(builder.desc(from.get(AggregationDetail_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    public List<AggregationDetail> findByAggregation(Aggregation aggregation) {
        Map<String, Object> params = new HashMap<>();
        params.put("aggregation", aggregation);
        return this.find(-1, -1, "createdOn", QuerySortOrder.DESC, params).getResult();
    }

}
