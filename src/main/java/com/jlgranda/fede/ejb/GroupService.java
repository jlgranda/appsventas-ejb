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

import com.jlgranda.fede.SettingNames;
import java.util.ArrayList;
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
import org.jpapi.controller.BussinesEntityHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jpapi.model.Group;
import org.jpapi.model.Group_;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;

/**
 *
 * @author jlgranda
 */
@Stateless
public class GroupService extends BussinesEntityHome<Group> {

    private static final long serialVersionUID = 6625285171825651429L;

    Logger logger = LoggerFactory.getLogger(GroupService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityClass(Group.class);
        setEntityManager(em);
    }

    @Override
    public Group createInstance() {

        Group _instance = new Group();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    public Group createInstance(Group.Type type) {

        Group _instance = new Group();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setGroupType(type);
        return _instance;
    }

    //soporte para Lazy Data model
    public long count() {
        return super.count(Group.class);
    }

    public List<Group> find(int maxresults, int firstresult) {
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Group> query = builder.createQuery(Group.class);

        Root<Group> from = query.from(Group.class);
        query.select(from).orderBy(builder.desc(from.get(Group_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    public List<Group> findAll() {
        return this.find(-1, -1, "name", QuerySortOrder.ASC, null).getResult();
    }

    public Group findByCode(String code) {
        List<Group> groups = this.findByNamedQuery("BussinesEntity.findByCode", code);
        return groups.isEmpty() ? new Group(code, code) : groups.get(0);
    }

    public List<Group> findByType(Group.Type groupType) {
        Map<String, Object> params = new HashMap<>();
        params.put("groupType", groupType);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }
//    public List<Group> findByOrganizationAndType(Organization organization, Group.Type groupType) {
//        Map<String, Object> params = new HashMap<>();
//        params.put("organization", organization);
//        params.put("groupType", groupType);
//        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
//    }

    public List<Group> findByOwnerAndModuleAndType(Subject owner, String module, Group.Type groupType) {
        List<String> modules = new ArrayList<>();
        modules.add(SettingNames.GENERAL_MODULE);
        modules.add(module);
        Map<String, Object> params = new HashMap<>();
        params.put("owner", owner);
        params.put("module", modules);
        params.put("groupType", groupType);
        return this.find(-1, -1, "module, orden, name", QuerySortOrder.ASC, params).getResult();
    }

}
