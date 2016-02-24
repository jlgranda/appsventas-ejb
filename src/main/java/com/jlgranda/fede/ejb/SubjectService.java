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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
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

    public List<Subject> buscarPorCriterio(Subject subjectLogin) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> parametros = new HashMap<>();
        boolean existeFiltro = false;
        sql.append("SELECT s FROM Subject s  WHERE 1=1 ");
        if (subjectLogin.getUsername() != null) {
            sql.append(" and LOWER(s.username) like concat('%',LOWER(:username),'%')");
            parametros.put("username", subjectLogin.getUsername());
            existeFiltro = true;
        }
        if (subjectLogin.getFirstname() != null) {
            sql.append(" and LOWER(s.firstname) like concat('%',LOWER(:firstname),'%')");
            parametros.put("firstname", subjectLogin.getFirstname());
            existeFiltro = true;
        }
        if (subjectLogin.getSurname() != null) {
            sql.append(" and LOWER(s.surname) like concat('%',LOWER(:surname),'%')");
            parametros.put("surname", subjectLogin.getSurname());
            existeFiltro = true;
        }
        if (!existeFiltro) {
            return new ArrayList<>();
        }
        final Query q = em.createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            q.setParameter(key, parametros.get(key));
        }
        return q.getResultList();
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
}
