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
import org.jlgranda.fede.model.accounting.CashBoxGeneral;
import org.jlgranda.fede.model.accounting.CashBoxGeneral_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;

/**
 *
 * @author author
 */
@Stateless
public class CashBoxGeneralService extends BussinesEntityHome<CashBoxGeneral> {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(CashBoxGeneral.class);
    }

    @Override
    public CashBoxGeneral createInstance() {
        CashBoxGeneral _instance = new CashBoxGeneral();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }

    //soporte para Lazy Data Model
    public long count() {
        return super.count(CashBoxGeneral.class);
    }

    public List<CashBoxGeneral> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<CashBoxGeneral> query = builder.createQuery(CashBoxGeneral.class);

        Root<CashBoxGeneral> from = query.from(CashBoxGeneral.class);
        query.select(from).orderBy(builder.desc(from.get(CashBoxGeneral_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    public List<CashBoxGeneral> findByOrganization(Organization organization) {
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        return this.find(-1, -1, "createdOn", QuerySortOrder.DESC, params).getResult();
    }

    public Long findIdByLastCashBoxGeneral(Organization organization) {
        List<CashBoxGeneral> listCashBoxGeneral = this.findByOrganization(organization);
        if (!listCashBoxGeneral.isEmpty()) {
            return listCashBoxGeneral.get(0).getId();
        } else {
            return null;
        }
    }

}
