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
import org.jpapi.controller.BussinesEntityHome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jpapi.model.Group;
import org.jpapi.util.QuerySortOrder;

/**
 *
 * @author jlgranda
 */
@Stateless
public class GroupService extends BussinesEntityHome<Group>{
    
    private static final long serialVersionUID = 6625285171825651429L;
     
    Logger  logger = LoggerFactory.getLogger(GroupService.class);
    

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init(){
        setEntityClass(Group.class);
        setEntityManager(em);
    }
    
    
    public Group findByCode(String code){
        List<Group> groups = this.findByNamedQuery("BussinesEntity.findByCode", code);
        return groups.isEmpty() ? new Group(code, code) : groups.get(0);
    }
    
    public List<Group> findAll(){
        return this.find(-1, -1, "name", QuerySortOrder.ASC, null).getResult();
    }
}
