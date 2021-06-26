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
package com.jlgranda.fede.ejb.sales;

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
import org.jlgranda.fede.model.sales.Kardex;
import org.jlgranda.fede.model.sales.KardexDetail;
import org.jlgranda.fede.model.sales.KardexDetail_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;

/**
 *
 * @author author
 */
@Stateless
public class KardexDetailService extends BussinesEntityHome<KardexDetail> {
   
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    private void init(){
        setEntityManager(em);
        setEntityClass(KardexDetail.class);
    }
    
    @Override
    public KardexDetail createInstance(){
        KardexDetail _instance = new KardexDetail();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setOperationType(KardexDetail.OperationType.SALIDA_INVENTARIO);
        return _instance;
    }
    
    //Soporte para lazy data model
    public long count(){
        return super.count(KardexDetail.class);
    }
    
    public List<KardexDetail> find(int maxresults, int firstresult){
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<KardexDetail> query = builder.createQuery(KardexDetail.class);
        
        Root<KardexDetail> from = query.from(KardexDetail.class);
        query.select(from).orderBy(builder.desc(from.get(KardexDetail_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    public List<KardexDetail> findByKardexAndCode(Kardex kardex, String code){
        Map<String, Object> params = new HashMap<>();
        params.put("kardex", kardex);
        params.put("code", code);
        return this.find(-1,-1, "code", QuerySortOrder.ASC, params).getResult();
    }
}
