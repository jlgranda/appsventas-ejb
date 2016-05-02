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
package net.tecnopro.document.ejb;

import java.io.Serializable;
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
import net.tecnopro.document.model.Template;
import net.tecnopro.document.model.Template_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Group;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.model.profile.Subject_;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Stateless
public class TemplateService extends BussinesEntityHome<Template> implements Serializable {

    private static final long serialVersionUID = 50292869154561898L;

    Logger logger = LoggerFactory.getLogger(TemplateService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityClass(Template.class);
        setEntityManager(em);
    }

    public Template findByCode(String code) {
        List<Template> templates = this.findByNamedQuery("Template.findByCode", code);
        return templates.isEmpty() ? null : templates.get(0);
    }

    public List<Template> findAll() {
        return this.find(-1, -1, "name", QuerySortOrder.ASC, null).getResult();
    }

    public List<Template> findAllByOwner(Subject owner) {
        Map<String, Object> params = new HashMap<>();
        params.put("owner", owner);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }

    @Override
    public Template createInstance() {

        Template _instance = new Template();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        return _instance;
    }
    
    public List<Template> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Template> query = builder.createQuery(Template.class);

        Root<Template> from = query.from(Template.class);
        query.select(from).orderBy(builder.desc(from.get(Template_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    public long count() {
        return super.count(Template.class); 
    }
}
