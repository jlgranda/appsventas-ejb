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
package com.jlgranda.fede.ejb.talentohumano;

import com.jlgranda.shiro.Roles;
import com.jlgranda.shiro.Roles_;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jpapi.controller.BussinesEntityHome;

/**
 *
 * @author usuario
 */
@Stateless
public class RolesService extends BussinesEntityHome<Roles> {

    private static final long serialVersionUID = -250129071295524266L;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Roles.class);
    }

    public Roles crearInstance() {
        Roles _instance = new Roles();
        return _instance;
    }

    public long count() {
        return super.count(Roles.class);
    }

    public List<Roles> find(int maxresults, int firstresult) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Roles> query = builder.createQuery(Roles.class);

        Root<Roles> from = query.from(Roles.class);
        query.select(from).orderBy(builder.desc(from.get(Roles_.name)));
        return getResultList(query, maxresults, firstresult);
    }

}