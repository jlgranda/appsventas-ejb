/*
 * Copyright (C) 2021 author
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

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.accounting.GeneralJournal;
import org.jlgranda.fede.model.accounting.GeneralJournal_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author author
 */

@Stateless
public class GeneralJournalService extends BussinesEntityHome<GeneralJournal> {

    private static final long serialVersionUID = -6428094275651428620L;
    
    Logger logger = LoggerFactory.getLogger(GeneralJournalService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(GeneralJournal.class);
    }

    @Override                                     
    public GeneralJournal createInstance() {

        GeneralJournal _instance = new GeneralJournal();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    //soporte para Lazy Data Model
    public long count() {
        return super.count(GeneralJournal.class);
    }

    public List<GeneralJournal> find(int maxresults, int firstresult) {
        
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<GeneralJournal> query = builder.createQuery(GeneralJournal.class);

        Root<GeneralJournal> from = query.from(GeneralJournal.class);
        query.select(from).orderBy(builder.desc(from.get(GeneralJournal_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    /**
     * Servicio para encontrar o construir la instancia de <tt>GeneralJournal</tt> de la organización para la fecha indicada
     * @param date
     * @param organization
     * @param owner
     * @param generalJournalPrefix
     * @param timestampPattern
     * @return 
     */
    public GeneralJournal find(Date date, Organization organization, Subject owner, String generalJournalPrefix, String timestampPattern ){
        //Buscar el registro hasta antes del fin del dia
        GeneralJournal generalJournal = this.findUniqueByNamedQuery("GeneralJournal.findByCreatedOnAndOrganization", Dates.minimumDate(date), Dates.maximumDate(date), organization);
        
        if (generalJournal == null){//Crear el libro diario para la fecha
            generalJournal = this.createInstance();
            generalJournal.setCode(UUID.randomUUID().toString());
            generalJournal.setName(generalJournalPrefix + ": " + Dates.toString(date, timestampPattern));
            generalJournal.setDescription(generalJournal.getName() + "\n" + organization + "\n" + owner);
            generalJournal.setOrganization(organization);
            generalJournal.setOwner(owner);
            generalJournal.setAuthor(owner);
            generalJournal = this.save(generalJournal);
        }
        
        return generalJournal;
    }

}
