/*
 * Copyright (C) 2022 TOSHIBA
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
package com.jlgranda.fede.ejb.sri;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.sri.SRICatastrosEmpresaFantasma;
import org.jlgranda.fede.model.sri.SRICatastrosEmpresaFantasma_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;

/**
 *
 * @author TOSHIBAextends BussinesEntityHome<Subject> 
 */
@Stateless
public class SRICatastrosEmpresaFantasmaService extends BussinesEntityHome<SRICatastrosEmpresaFantasma> {

    private static final long serialVersionUID = -4487467890746594655L;
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(SRICatastrosEmpresaFantasma.class);
    }


    public boolean usersExist() {
        Query q = em.createQuery("SELECT U FROM sri_catastros_empresa_fantasma U");
        return !q.getResultList().isEmpty();
    }

    public long count() {
        return super.count(SRICatastrosEmpresaFantasma.class);
    }
    
    @Override
    public SRICatastrosEmpresaFantasma createInstance() {

        SRICatastrosEmpresaFantasma _instance = new SRICatastrosEmpresaFantasma();
        return _instance;
    }

    
    public List<SRICatastrosEmpresaFantasma> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<SRICatastrosEmpresaFantasma> query = builder.createQuery(SRICatastrosEmpresaFantasma.class);

        Root<SRICatastrosEmpresaFantasma> from = query.from(SRICatastrosEmpresaFantasma.class);
        query.select(from).orderBy(builder.desc(from.get(SRICatastrosEmpresaFantasma_.NUMERO_RUC)));
        return getResultList(query, maxresults, firstresult);
    }

    public void save(Long id, SRICatastrosEmpresaFantasma _subject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
