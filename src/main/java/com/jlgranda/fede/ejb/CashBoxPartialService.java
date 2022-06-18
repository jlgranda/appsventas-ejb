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

import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.accounting.CashBoxGeneral;
import org.jlgranda.fede.model.accounting.CashBoxPartial;
import org.jlgranda.fede.model.accounting.CashBoxPartial_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;

/**
 *
 * @author author
 */
@Stateless
public class CashBoxPartialService extends BussinesEntityHome<CashBoxPartial> {

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(CashBoxPartial.class);
    }

    @Override
    public CashBoxPartial createInstance() {

        CashBoxPartial _instance = new CashBoxPartial();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setCashPartial(BigDecimal.ZERO);
        _instance.setTotalCashBills(BigDecimal.ZERO);
        _instance.setTotalCashMoneys(BigDecimal.ZERO);
        _instance.setTotalCashBreakdown(BigDecimal.ZERO);
        _instance.setMissCash(BigDecimal.ZERO);
        _instance.setExcessCash(BigDecimal.ZERO);
        _instance.setCashFinally(BigDecimal.ZERO);
        _instance.setStatusComplete(Boolean.FALSE);
        _instance.setStatusFinally(Boolean.FALSE);
        _instance.setTypeVerification(CashBoxPartial.Verification.NOT_VERIFIED);
        return _instance;
    }

    //soporte para Lazy Data Model
    public long count() {
        return super.count(CashBoxPartial.class);
    }

    public List<CashBoxPartial> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<CashBoxPartial> query = builder.createQuery(CashBoxPartial.class);

        Root<CashBoxPartial> from = query.from(CashBoxPartial.class);
        query.select(from).orderBy(builder.desc(from.get(CashBoxPartial_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    public int getPriority(CashBoxGeneral cashBoxGeneral) {
        if (cashBoxGeneral.getId() == null) {
            return 0;
        } else {
            return (int) this.count("CashBoxPartial.countCashBoxPartialByCashBoxGeneral", cashBoxGeneral);
        }
    }

    public Long getLastCashBoxPartialByCashBoxGeneral(CashBoxGeneral cashBoxGeneral) {
        List<CashBoxPartial> list = this.findByNamedQuery("CashBoxPartial.findByCashBoxGeneral", cashBoxGeneral);
        if (!list.isEmpty()) {
            return list.get(0).getId();
        }
        return null;
    }

    public CashBoxPartial findByOrganizationAndLastId(Organization organization) {
        List<CashBoxPartial> listCashBoxPartial = this.findByNamedQuery("CashBoxPartial.findByOrganizationAndLastId", organization);
        if (!listCashBoxPartial.isEmpty()) {
            return listCashBoxPartial.get(0);
        } else {
            return null;
        }
    }

}
