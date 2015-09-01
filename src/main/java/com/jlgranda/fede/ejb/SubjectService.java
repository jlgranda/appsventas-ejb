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

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.PartitionManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.Account;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;
import org.picketlink.idm.model.IdentityType;

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

    @Inject
    private PartitionManager partitionManager;

    IdentityManager identityManager = null;

    @PostConstruct
    private void init() {
        identityManager = partitionManager.createIdentityManager();
        setEntityManager(em);
        setEntityClass(Subject.class);
    }

    public boolean usersExist() {
        Query q = em.createQuery("SELECT U FROM AccountTypeEntity U");
        return !q.getResultList().isEmpty();
    }

    public void addUser(IdentityType user) {
        identityManager.add(user);
    }

    public void updateCredential(Account user, Password password) {
        identityManager.updateCredential(user, password);
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
