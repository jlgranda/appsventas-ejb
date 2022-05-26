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
import java.util.ArrayList;
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
import org.jlgranda.appsventas.data.AggregationData;
import org.jlgranda.appsventas.data.AggregationDetailData;
import org.jlgranda.fede.model.production.Aggregation;
import org.jlgranda.fede.model.production.AggregationDetail;
import org.jlgranda.fede.model.production.Aggregation_;
import org.jlgranda.fede.model.sales.Product;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
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
public class AggregationService extends BussinesEntityHome<Aggregation> {
    
    Logger logger = LoggerFactory.getLogger(AggregationService.class);
    
    private BigDecimal costTotal;
    
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Aggregation.class);
    }
    
    @Override
    public Aggregation createInstance() {
        Aggregation _instance = new Aggregation();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }

    //Soporte para lazy data model
    public long count() {
        return super.count(Aggregation.class);
    }
    
    public List<Aggregation> find(int maxresults, int firstresult) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Aggregation> query = builder.createQuery(Aggregation.class);
        
        Root<Aggregation> from = query.from(Aggregation.class);
        query.select(from).orderBy(builder.desc(from.get(Aggregation_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    public List<Aggregation> findByOrganization(Organization organization) {
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        return this.find(-1, -1, "name", QuerySortOrder.DESC, params).getResult();
    }
    public List<Aggregation> findByProduct(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put("product", product);
        params.put("deleted", false);
        return this.find(-1, -1, "name", QuerySortOrder.DESC, params).getResult();
    }
    
    public List<AggregationData> buildDatafindByOrganization(Organization organization) {
        List<AggregationData> aggregationsData = new ArrayList<>();
        List<Aggregation> aggregations = this.findByOrganization(organization);
        if (!aggregations.isEmpty()) {
            aggregations.forEach(agg -> {
                AggregationData aggregationData = new AggregationData();
                aggregationData.setId(agg.getId());
                aggregationData.setProductName(agg.getProduct().getName());
                aggregationData.setDescription(agg.getDescription());
                aggregationData.setAggregationDetails(buildDataAggregationDetail(agg.getAggregationDetails()));
                aggregationData.setCostTotal(this.costTotal);
                aggregationsData.add(aggregationData);
            });
        }
        return aggregationsData;
    }
    
    public List<AggregationDetailData> buildDataAggregationDetail(List<AggregationDetail> aggregationDetails) {
        this.costTotal = BigDecimal.ZERO;
        List<AggregationDetailData> aggregationDetailsData = new ArrayList<>();
        if (!aggregationDetails.isEmpty()) {
            aggregationDetails.forEach(aggd -> {
                AggregationDetailData aggregationDetailData = new AggregationDetailData();
                aggregationDetailData.setCost(aggd.getCost());
                aggregationDetailData.setQuantity(aggd.getQuantity());
                aggregationDetailData.setElementName(aggd.getProduct().getName());
                this.costTotal = this.costTotal.add(aggd.getCost());
                aggregationDetailsData.add(aggregationDetailData);
            });
        }
        return aggregationDetailsData;
    }

}
