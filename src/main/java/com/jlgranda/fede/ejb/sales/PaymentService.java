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
package com.jlgranda.fede.ejb.sales;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jlgranda.fede.model.sales.Payment;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;

/**
 *
 * @author jlgranda
 */
@Stateless
public class PaymentService extends BussinesEntityHome<Payment> {

    private static final long serialVersionUID = 4837861037443488043L;
    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Payment.class);
    }

    @Override
    public Payment createInstance() {

        return createInstance("EFECTIVO", 0, 0, 0);
    }

    public Payment createInstance(String method, double amount, double cash, double discount) {

        Payment _instance = new Payment();
        _instance.setMethod(method);
        _instance.setAmount(BigDecimal.valueOf(amount));
        _instance.setCash(BigDecimal.valueOf(cash));
        _instance.setDiscount(BigDecimal.valueOf(discount));
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
//        _instance.setUuid(UUID.randomUUID().toString());
        return _instance;
    }

    public Payment createInstance(String method, BigDecimal amount, BigDecimal cash, BigDecimal discount) {

        Payment _instance = new Payment();
        _instance.setMethod(method);
        _instance.setAmount(amount);
        _instance.setCash(cash);
        _instance.setDiscount(discount);
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setUuid(UUID.randomUUID().toString());
        return _instance;
    }

    public Payment findByCode(Payment payment) {
        Map<String, Object> filters = new HashMap<>();
        filters.put("uuid", payment.getUuid()); //Recuperar por código
        if (!this.find(filters).getResult().isEmpty()) {
            payment = this.find(filters).getResult().get(0); //debe ser único
        }
        return payment;
    }

}
