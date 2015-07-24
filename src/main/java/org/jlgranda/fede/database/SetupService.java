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
package org.jlgranda.fede.database;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.jpapi.model.Setting;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.CodeType;
import org.jpapi.model.Group;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;
import org.picketlink.idm.credential.Password;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
@Stateless
public class SetupService implements Serializable {

    private static Logger log = LoggerFactory.getLogger(SetupService.class);
    private static final long serialVersionUID = 235471466504044866L;

    @PersistenceContext
    EntityManager em;

    protected BussinesEntityHome bussinesEntityHome = null;

    private static final String[][] DATA = {
        {"0", "PLAN DE CUENTAS SEGUN NIIF COMPLETAS Y NIIF PARA PYMES", "SCHEMA", "Plan de cuentas según NIIF Completas y NIIF para PYMES"},
        {"1", "ACTIVO", "GENDER", "Conforman el estado de situación financiera, de flujo de efectivo y de evolución patrimonio"},
        {"10101", "EFECTIVO Y EQUIVALENTES AL EFECTIVO", "ACCOUNT", "Efectivo, comprende la caja y los depósitos bancarios a la vista.<br/>Equivalente al efectivo, son inversiones a corto plazo de gran liquidez (hasta 90 días), que son fácilmente convertibles en valores en efectivo, con riesgo insignificante de cambios en su valor. <br/>NIC 7p6, 7, 8, 9, 48, 49 NIIF para PYMES p7.2"},
        {"10102", "ACTIVOS FINANCIEROS", "ACCOUNT", "NIC 32 p11 - NIC 39 - NIIF 7 - NIIF 9 <br/>Secciones 11 y 12 NIIF para las PYMES"},
        {"1010201", "ACTIVOS FINANCIEROS A VALOR RAZONABLE CON CAMBIOS EN RESULTADOS", "ACCOUNT", "Son los activos financieros adquiridos para negociar activamente, con el objetivo de generar ganancia. <br/>Medición Inicial y Posterior:<br/>A valor razonable<br/>La variación se reconoce resultado del ejercicio"},
        {"2", "PASIVO", "GENDER", "Conforman el estado de situación financiera, de flujo de efectivo y de evolución patrimonio"},
        {"3", "PATRIMONIO", "GENDER", "Conforman el estado de situación financiera, de flujo de efectivo y de evolución patrimonio"},
        {"4", "CUENTAS DE RESULTADO ACREEDORAS", "ASSET", "Cuentas de gestión de partidas de resultados acreedoras y deudoras, indispensable para la elaboración del balance de perdidas y ganancias"},
        {"5", "CUENTAS DE RESULTADO DEUDORAS", "LIABILITY", "Cuentas de gestión de partidas de resultados acreedoras y deudoras, indispensable para la elaboración del balance de perdidas y ganancias"},
        {"6", "CUENTAS CONTINGENTES", "INCOME", "Agrupan las obligaciones eventuales"},
        {"7", "CUENTAS DE ORDEN", "EXPENSE", "Cuentas de orden y de control, indispensables para la buena administración"}
    };

    public void validate() {
        //bussinesEntityHome.setEntityManager(bussinesEntityRepository);
        validateDB();
        validateGroups();
        //validateIdentityObjectTypes();
        validateSecurity();
        validateSetting();

    }

    private void validateDB() {
        Setting singleResult = null;
        try {
            TypedQuery<Setting> query = getEntityManager().createQuery("from Setting s where s.name='schemaVersion'",
                    Setting.class);
            singleResult = query.getSingleResult();
        } catch (NoResultException e) {
            java.util.Date now = Dates.now();
            singleResult = new Setting("schemaVersion", "1");
            singleResult.setCreatedOn(now);
            singleResult.setLastUpdate(now);
            singleResult.setCodeType(CodeType.SYSTEM);
            getEntityManager().persist(singleResult);
            getEntityManager().flush();
        }

        log.info("Current database schema version is [" + singleResult.getValue() + "]");

    }

//    private void validateIdentityObjectTypes() {
//        if (getEntityManager().createQuery("select t from IdentityObjectType t where t.name = :name")
//                .setParameter("name", "USER")
//                .getResultList().isEmpty()) {
//
//            IdentityObjectType user = new IdentityObjectType();
//            user.setName("USER");
//            getEntityManager().persist(user);
//        }
//
//        if (getEntityManager().createQuery("select t from IdentityObjectType t where t.name = :name")
//                .setParameter("name", "GROUP")
//                .getResultList().isEmpty()) {
//
//            IdentityObjectType group = new IdentityObjectType();
//            group.setName("GROUP");
//            getEntityManager().persist(group);
//        }
//    }
    
    private void validateGroups() {
        
        Group singleResult = null;
        Map<String, String> props = new HashMap<String, String>();
        
        //email settings
        props.put("fede", "fede");
        props.put("salud", "Salud");
        props.put("alimentos", "Alimentos");
        props.put("ropa", "Ropa");
        props.put("educacion", "Educación");
        props.put("vivienda", "Vivienda");
        
        
        
        String value = null;
        for (String key : props.keySet()){
            value = props.get(key);
            try {
                TypedQuery<Group> query = getEntityManager().createQuery("from Group g where g.code='" + key + "'",
                        Group.class);
                singleResult = query.getSingleResult();
            } catch (NoResultException e) {
                java.util.Date now = Dates.now();
                singleResult = new Group(key, value);
                singleResult.setCreatedOn(now);
                singleResult.setLastUpdate(now);
                singleResult.setCodeType(CodeType.TAG);
                singleResult.setStatus(StatusType.ACTIVE.toString());
                getEntityManager().persist(singleResult);
                getEntityManager().flush();
            }

            log.info("Added group code: {} name: [{}]", key, singleResult.getName());
        }
    }
    
    private void validateSetting() {
        
        Setting singleResult = null;
        Map<String, String> props = new HashMap<String, String>();
        
        //email settings
        props.put("mail.from", "fede@jlgranda.com");
        props.put("mail.host", "jlgranda.com");
        props.put("mail.user", "fede");
        props.put("mail.password", "LieferQuireMidstUpends95");
        props.put("mail.inbox", "INBOX");
        props.put("mail.port", "25");
        props.put("mail.verbose", "true");
        props.put("mail.debug", "true");
        
        props.put("mail.store.protocol", "imap");
        props.put("mail.transport.protocol", "smtps");
        
        props.put("mail.imap.host", "jlgranda.com");
        props.put("mail.imap.port", "25");
        
        props.put("mail.smtp.host", "jlgranda.com");
        props.put("mail.smtp.user", "fede");
        props.put("mail.smtp.password", "LieferQuireMidstUpends95");
        
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtps.port", "465");
        props.put("mail.smtps.host", "smtp.gmail.com");
        props.put("mail.smtps.auth", "true");
        props.put("mail.imaps.ssl.trust", "*");
        
        //dashboard settings
        props.put("fede.dashboard.timeline.length", "10");
        
        //XML utils settings
        props.put("fede.xml.tag.numeroAutorizacion", "<numeroAutorizacion></numeroAutorizacion>");
        props.put("fede.xml.tag.fechaAutorizacion", "<fechaAutorizacion></fechaAutorizacion>");
        
        //Groups
        props.put("fede.group.default", "fede");
        
        //dates patterns
        props.put("fede.date.pattern", "dd/MM/yyyy");
        String value = null;
        for (String key : props.keySet()){
            value = props.get(key);
            try {
                TypedQuery<Setting> query = getEntityManager().createQuery("from Setting s where s.name='" + key + "'",
                        Setting.class);
                singleResult = query.getSingleResult();
            } catch (NoResultException e) {
                java.util.Date now = Dates.now();
                singleResult = new Setting(key, value);
                singleResult.setCreatedOn(now);
                singleResult.setLastUpdate(now);
                singleResult.setCodeType(CodeType.SYSTEM);
                singleResult.setStatus(StatusType.ACTIVE.toString());
                getEntityManager().persist(singleResult);
                getEntityManager().flush();
            }

            log.info("Current  {} is [{}]", key, singleResult.getValue());
        }
    }

    private void validateSecurity() {
        Subject singleResult = null;
        try {
            TypedQuery<Subject> query = getEntityManager().createQuery("from Subject s where s.username='admin'",
                    Subject.class);
            singleResult = query.getSingleResult();
        } catch (NoResultException e) {
            java.util.Date now = Dates.now();
            singleResult = new Subject();
            singleResult.setEmail("admin@fede.com");
            singleResult.setUsername("admin");
            singleResult.setPassword(new Password("f3d3").toString());
            singleResult.setUsernameConfirmed(true);
            singleResult.setCreatedOn(Dates.now());
            singleResult.setLastUpdate(Dates.now());
            singleResult.setCodeType(CodeType.NONE);
            getEntityManager().persist(singleResult);
        }
    }

    private EntityManager getEntityManager() {
        return em;
    }
}
