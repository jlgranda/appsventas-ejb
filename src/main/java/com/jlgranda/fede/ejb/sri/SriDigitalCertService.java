/*
 * Copyright (C) 2022 TOSHIBA
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
package com.jlgranda.fede.ejb.sri;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.sri.SriDigitalCert;
import org.jlgranda.fede.model.sri.SriDigitalCert_;
import org.jpapi.controller.BussinesEntityHome;

/**
 *
 * @author TOSHIBA
 */
@Stateless
public class SriDigitalCertService extends BussinesEntityHome<SriDigitalCert> {

    private static final long serialVersionUID = -4487467890746594655L;
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(SriDigitalCert.class);
    }

    public long count() {
        return super.count(SriDigitalCert.class);
    }

    public List<SriDigitalCert> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SriDigitalCert> query = builder.createQuery(SriDigitalCert.class);

        Root<SriDigitalCert> from = query.from(SriDigitalCert.class);
        query.select(from).orderBy(builder.desc(from.get(SriDigitalCert_.ACTIVE)));
        return getResultList(query, maxresults, firstresult);
    }
}
