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
package com.jlgranda.fede.ejb;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.model.profile.Subject_;
import org.jpapi.util.Dates;

/**
 * Servicios relacionados con entidad Subject
 *
 * @author jlgranda
 */
@Stateless
public class SubjectService extends BussinesEntityHome<Subject> {

    private static final long serialVersionUID = 4688557231355280889L;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Subject.class);
    }

    public boolean usersExist() {
        Query q = em.createQuery("SELECT U FROM AccountTypeEntity U");
        return !q.getResultList().isEmpty();
    }


    @Override
    public Subject createInstance() {

        Subject _instance = new Subject();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    public List<Subject> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Subject> query = builder.createQuery(Subject.class);

        Root<Subject> from = query.from(Subject.class);
        query.select(from).orderBy(builder.desc(from.get(Subject_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    public long count() {
        return super.count(Subject.class); 
    }
}
