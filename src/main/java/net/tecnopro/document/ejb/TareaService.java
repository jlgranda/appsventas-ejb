/*
 * Copyright (C) 2016 Jorge
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
package net.tecnopro.document.ejb;

import com.jlgranda.fede.ejb.GroupService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import net.tecnopro.document.model.Tarea;
import org.jlgranda.fede.model.document.FacturaElectronica;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Group;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Jorge
 */
@Stateless
public class TareaService extends BussinesEntityHome<Tarea> {

    Logger logger = LoggerFactory.getLogger(TareaService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityClass(Tarea.class);
        setEntityManager(em);
    }

    public Group findByCode(String code) {
        List<Group> groups = this.findByNamedQuery("BussinesEntity.findByCode", code);
        return groups.isEmpty() ? new Group(code, code) : groups.get(0);
    }

    public List<Tarea> findAll() {
        return this.find(-1, -1, "name", QuerySortOrder.ASC, null).getResult();
    }

    public List<Tarea> findAllByOwner(Subject owner) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("owner", owner);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }

    @Override
    public Tarea createInstance() {

        Tarea _instance = new Tarea();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }

    public long count() {
        return super.count(Tarea.class);
    }
}
