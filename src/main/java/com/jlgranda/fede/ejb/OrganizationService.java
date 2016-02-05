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
import org.jlgranda.fede.model.management.BalancedScoreCard;
import org.jlgranda.fede.model.management.Organization;
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
}
