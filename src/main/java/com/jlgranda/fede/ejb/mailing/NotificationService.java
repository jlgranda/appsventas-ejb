/*
 * Copyright (C) 2016 jlgranda
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
package com.jlgranda.fede.ejb.mailing;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.tecnopro.mailing.Notification;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.util.Dates;

/**
 *
 * @author jlgranda
 */
@Stateless
public class NotificationService extends BussinesEntityHome<Notification>{
    
    private static final long serialVersionUID = -8507863476924951934L;
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Notification.class);
    }

    @Override
    public Notification createInstance() {

        Notification _instance = new Notification();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setFlag(Boolean.FALSE);
        _instance.setStatus(null);
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        return _instance;
    }
}
