/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jlgranda.fede.ejb;

import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Setting;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;

/**
 *
 * @author jlgranda
 */
@Stateless
public class SettingService extends BussinesEntityHome<Setting> {

    private static final long serialVersionUID = 4575953142699951180L;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityClass(Setting.class);
        setEntityManager(em);
    }

    public Setting findByName(String name) {
        return findByName(name, null);
    }

    public Setting findByName(String name, Subject owner) {
        List<Setting> settings = this.findByNamedQuery("Setting.findByName", name);
        return settings.isEmpty() ? null : settings.get(0);
    }

    public List<Setting> findByCriteria(Setting setting) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> parametros = new HashMap<>();
        sql.append("Select setting from Setting setting where 1=1 ");
        if (setting.getName() != null) {
            sql.append(" and LOWER(setting.name) like concat ('%',LOWER(:name),'%') ");
            parametros.put("name", setting.getName());
        }
        if (setting.getOwner() != null) {
            sql.append(" and setting.owner=:owner ");
            parametros.put("owner", setting.getOwner());
        }
        sql.append(" and setting.active=True order by setting.name");
        final Query q = em.createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            q.setParameter(key, parametros.get(key));
        }
        return q.getResultList();
    }

    public List<Setting> findByCriteriaOwnerNone(Setting setting) {
        StringBuilder sql = new StringBuilder();
        HashMap<String, Object> parametros = new HashMap<>();
        sql.append("Select setting from Setting setting where 1=1 ");
        if (setting.getName() != null) {
            sql.append(" and LOWER(setting.name) like concat ('%',LOWER(:name),'%') ");
            parametros.put("name", setting.getName());
        }
        sql.append(" and setting.owner is null");
        sql.append(" and setting.active=True order by setting.name");
        final Query q = em.createQuery(sql.toString());
        for (String key : parametros.keySet()) {
            q.setParameter(key, parametros.get(key));
        }
        return q.getResultList();
    }

    @Override
    public Setting createInstance() {

        Setting _instance = new Setting();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        return _instance;
    }
}
