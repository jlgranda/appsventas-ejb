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

import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jpapi.controller.BussinesEntityHome;
import org.jlgranda.fede.model.document.FacturaElectronica;
import org.jlgranda.fede.model.document.FacturaElectronica_;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;
import org.jpapi.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jpapi.model.profile.Subject;

/**
 *
 * @author jlgranda
 */
@Stateless
public class FacturaElectronicaService extends BussinesEntityHome<FacturaElectronica> {

    Logger logger = LoggerFactory.getLogger(FacturaElectronicaService.class);

    private static final long serialVersionUID = -301572035656937837L;

    @PersistenceContext
    EntityManager em;

    public FacturaElectronicaService() {
    }

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(FacturaElectronica.class);
    }

    /**
     * Obtener el top de las facturas electrónicas para portada
     * @return 
     */
    public List<FacturaElectronica> listarFacturasElectronicas(int max) {
        return this.find(0, Strings.toInt(max), FacturaElectronica_.fechaEmision.getName(), QuerySortOrder.DESC, new HashMap<String, Object>()).getResult();
    }
    
    /**
     * Obtener facturas electrónicas por tag
     * @return 
     */
    public List<FacturaElectronica> listarFacturasElectronicas(String tag) {
        return this.findByNamedQuery("FacturaElectronica.findBussinesEntityByTag", tag);
    }
    
    /**
     * Obtener facturas electrónicas por tag y dueño
     * @return 
     */
    public List<FacturaElectronica> listarFacturasElectronicas(String tag, Subject owner) {
        return this.findByNamedQuery("FacturaElectronica.findBussinesEntityByTagAndOwner", tag, owner);
    }
    
    /**
     * Obtener el top de las facturas electrónicas para portada
     * @return 
     */
    public List<FacturaElectronica> listarFacturasElectronicas() {
        return this.findAll(FacturaElectronica.class);
    }
    
    @Override
    public FacturaElectronica createInstance() {

        FacturaElectronica _instance = new FacturaElectronica();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    public FacturaElectronica find(final long id, boolean lazily) {
        FacturaElectronica f = null;
        f = super.find(id);
        if (lazily){
            f.getMemberships().size(); //Forza la carga de memberships
            f.getAttributes().size();
        }
        
        return f;
    }

}
