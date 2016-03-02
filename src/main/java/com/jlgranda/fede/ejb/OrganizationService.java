/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jlgranda.fede.ejb;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.management.Organization;
import org.jlgranda.fede.model.management.Organization_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Stateless
public class OrganizationService extends BussinesEntityHome<Organization>{
     
    Logger  logger = LoggerFactory.getLogger(OrganizationService.class);
    
    private static final long serialVersionUID = 6654364438741958096L;
    
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init(){
        setEntityClass(Organization.class);
        setEntityManager(em);
    }
    
    public List<Organization> getOrganizations(){
        return this.findAll(Organization.class);
    }

    
    @Override
    public Organization createInstance() {

        Organization _instance = new Organization();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    public long count() {
        return super.count(Organization.class); 
    }
    
    public List<Organization> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Organization> query = builder.createQuery(Organization.class);

        Root<Organization> from = query.from(Organization.class);
        query.select(from).orderBy(builder.desc(from.get(Organization_.name)));
        return getResultList(query, maxresults, firstresult);
    }
}
