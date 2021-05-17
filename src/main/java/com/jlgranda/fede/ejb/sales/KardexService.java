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

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.sales.Kardex;
import org.jlgranda.fede.model.sales.Kardex_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;

/**
 *
 * @author author
 */
@Stateless
public class KardexService extends BussinesEntityHome<Kardex>{
    
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    private void init(){
        setEntityManager(em);
        setEntityClass(Kardex.class);
    }
    
    @Override
    public Kardex createInstance(){
        Kardex _instance = new Kardex();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    //Soporte para lazy data model
    public long count(){
        return super.count(Kardex.class);
    }
    
    public List<Kardex> find(int maxresults, int firstresult){
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Kardex> query = builder.createQuery(Kardex.class);
        
        Root<Kardex> from = query.from(Kardex.class);
        query.select(from).orderBy(builder.desc(from.get(Kardex_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
}
