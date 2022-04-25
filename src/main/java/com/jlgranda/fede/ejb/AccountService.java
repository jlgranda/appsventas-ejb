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

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.accounting.Account;
import org.jlgranda.fede.model.accounting.Account_;
import org.jlgranda.fede.model.accounting.RecordDetail;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author author
 */
@Stateless
public class AccountService extends BussinesEntityHome<Account> {

    private static final long serialVersionUID = -6428094275651428620L;

    Logger logger = LoggerFactory.getLogger(AccountService.class);

    @PersistenceContext
    EntityManager em;

    @EJB
    RecordDetailService recordDetailService;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Account.class);
    }

    @Override
    public Account createInstance() {

        Account _instance = new Account();
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
        return super.count(Account.class);
    }

    public List<Account> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Account> query = builder.createQuery(Account.class);

        Root<Account> from = query.from(Account.class);
        query.select(from).orderBy(builder.desc(from.get(Account_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    public List<Account> findByOrganization(Organization organization) {
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }

//    /**
//     * Calcula el valor de la cuenta entre las fechas
//     * @param account
//     * @param organization
//     * @param desde fecha de inicio
//     * @param hasta fecha de final
//     * @return El valor de la mayorización de la cuenta
//     */
//    public BigDecimal mayorizar(Account account, Organization organization, Date desde, Date hasta){
//
//        BigDecimal balance = new BigDecimal(BigInteger.ZERO);
//        List<Account> childs =  this.findByNamedQuery("Account.findByParentId", account.getId(), organization);
//
//        if (childs.isEmpty()){
//            //Obtener balance la cuenta
//            BigDecimal debe = recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndType", account, RecordDetail.RecordTDetailType.DEBE, desde, hasta, organization);
//            
//            BigDecimal haber = recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndType", account, RecordDetail.RecordTDetailType.HABER, desde, hasta, organization);
//
//            balance = debe.subtract(haber);
//            
//        } else {
//            for (Account child :  childs ) {
//                balance =  balance.add(mayorizar(child, organization, desde, hasta));
//            }
//        }
//
//        return balance;
//    }
    /**
     * Calcula el valor de la cuenta entre las fechas
     *
     * @param accountName Nombre de la cuenta
     * @param organization
     * @param desde fecha de inicio
     * @param hasta fecha de final
     * @return El valor de la mayorización de la cuenta
     */
    public BigDecimal mayorizar(String accountName, Organization organization, Date desde, Date hasta) {

        Account account = this.findUniqueByNamedQuery("Account.findByNameAndOrganization", accountName, organization);

        return mayorizar(account, organization, desde, hasta);
    }

    /**
     * Calcula el valor de la cuenta entre las fechas
     *
     * @param accountName Nombre de la cuenta
     * @param organization
     * @param desde fecha de inicio
     * @param hasta fecha de final
     * @return El valor de la mayorización de la cuenta
     */
    public BigDecimal mayorizarPorTipo(RecordDetail.RecordTDetailType type, String accountName, Organization organization, Date desde, Date hasta) {

        Account account = this.findUniqueByNamedQuery("Account.findByNameAndOrganization", accountName, organization);

        return mayorizarPorTipo(type, account, organization, desde, hasta);
    }

    public BigDecimal mayorizarPorTipo(RecordDetail.RecordTDetailType type, Account account, Organization organization, Date desde, Date hasta) {

        return recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndType", account, type, desde, hasta, organization);

    }

    /**
     * Calcula el valor de la cuenta entre las fechas
     *
     * @param account
     * @param organization
     * @param desde fecha de inicio
     * @param hasta fecha de final
     * @return El valor de la mayorización de la cuenta
     */
    public BigDecimal mayorizar(Account account, Organization organization, Date desde, Date hasta) {

        BigDecimal balance = new BigDecimal(BigInteger.ZERO);
        List<Account> childs = this.findByNamedQuery("Account.findByParentId", account.getId(), organization);

        //Obtener balance la cuenta
        BigDecimal debe = recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndType", account, RecordDetail.RecordTDetailType.DEBE, desde, hasta, organization);

        BigDecimal haber = recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndType", account, RecordDetail.RecordTDetailType.HABER, desde, hasta, organization);
        if (debe != null && haber != null) {
            balance = balance.add(debe.subtract(haber));
        }
        if (!childs.isEmpty()) {
            for (Account child : childs) {
                balance = balance.add(mayorizar(child, organization, desde, hasta));
            }
        }
        return balance;
    }

    /**
     * Calcula el valor de la cuenta desde el primer asiento hasta la fecha
     *
     * @param account
     * @param organization
     * @param hasta fecha de final
     * @return El valor de la mayorización de la cuenta
     */
    public BigDecimal mayorizarTo(Account account, Organization organization, Date hasta) {

        BigDecimal balance = new BigDecimal(BigInteger.ZERO);
        List<Account> childs = this.findByNamedQuery("Account.findByParentId", account.getId(), organization);

        //Obtener balance la cuenta
        BigDecimal debe = recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndTypeTo", account, RecordDetail.RecordTDetailType.DEBE, hasta, organization);

        BigDecimal haber = recordDetailService.findBigDecimal("RecordDetail.findTotalByAccountAndTypeTo", account, RecordDetail.RecordTDetailType.HABER, hasta, organization);
        if (debe != null && haber != null) {
            balance = balance.add(debe.subtract(haber));
        }
        if (!childs.isEmpty()) {
            for (Account child : childs) {
                balance = balance.add(mayorizarTo(child, organization, hasta));
            }
        }
        return balance;
    }
}
