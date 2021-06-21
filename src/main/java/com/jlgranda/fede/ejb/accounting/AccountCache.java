/*
 * Copyright (C) 2019 jlgranda
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
package com.jlgranda.fede.ejb.accounting;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.jlgranda.fede.ejb.AccountService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.jlgranda.fede.model.accounting.Account;
import org.jpapi.model.Organization;
import org.jpapi.util.Strings;

/**
 * Cache de productos de venta al público
 * @author jlgranda
 */
@Singleton
public class AccountCache {
    
    private static final Integer REFRESH_PRODUCT_AFTER_5_SECONDS = 5;
    private static final Integer EXPIRE_PRODUCT_AFTER_1_DAY = 1;
    private final LoadingCache<String, Optional<Account>> cache;

    @EJB
    AccountService accountService;
    
    private final Map<Long, Account> accounts = new HashMap<>();
    
    @PostConstruct
    public void init() {
        load();
    }
    
    /**
     * Cargar productos en el cache.
     */
    public void load(){
        
        List<Account> accountList = accountService.findByNamedQuery("Account.findAll");
        accountList.forEach(p -> {
            accounts.put(p.getId(), p);
        });
    }
    
    public Account lookup(Long key) {
        Account account = null;
        if (accounts.containsKey(key)) {
            account = accounts.get(key);
        } 
        return account; //devolver por defecto la clave buscada
    }

    public AccountCache() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_PRODUCT_AFTER_1_DAY, TimeUnit.DAYS)
                .refreshAfterWrite(REFRESH_PRODUCT_AFTER_5_SECONDS, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Optional<Account>>() {
                    @Override
                    public Optional<Account> load(String itemId) throws Exception {
                        return loadCache(Long.valueOf(itemId));
                    }
                }
                );
    }

    public Optional<Account> getEntry(String itemId) {
        return cache.getUnchecked(itemId);
    }

    private Optional<Account> loadCache(Long productId) {
        Account item = lookup(productId);
        return Optional.fromNullable(item);
    }
    
    public List<Account> filterByOrganization(Organization organization){
        List<Account> matches = new ArrayList<>();
        if (organization == null){
            return matches; //vacio
        }
        accounts.values().stream().filter(account -> ( organization.equals(account.getOrganization()) )).forEachOrdered(account -> {
                    matches.add(account);
        }); 
        return matches; //devolver por defecto la clave buscada
    }
    
    public List<Account> filterByParentId(Long parentAccountId){
        List<Account> matches = new ArrayList<>();
        if (parentAccountId == null){
            return matches; //vacio
        }
        accounts.values().stream().filter(account -> ( parentAccountId.equals(account.getParentAccountId()) )).forEachOrdered(account -> {
                    matches.add(account);
        }); 
        return matches; //devolver por defecto la clave buscada
    }
    
    public Account lookupByName(String name, Organization organization){
        List<Account> matches = new ArrayList<>();
        if (Strings.isNullOrEmpty(name)){
            return null; //vacio
        }
        accounts.values().stream().filter(account -> ( name.equalsIgnoreCase(account.getName()) && organization.equals(account.getOrganization()))).forEachOrdered(account -> {
                    matches.add(account);
        }); 
        return matches.get(0); //devolver el 1er elemento
    }
    
    
    public String genereNextCode(Long parentAccountId){
        
        String nextCode = "";
        if (parentAccountId == null) {
            return nextCode; //TODO generar algo más útil
        }
        
        List<Account> result =  this.filterByParentId(parentAccountId);
        
        if (result.isEmpty()){
            //No hay hijos, generar el 1ero
            Account current = this.lookup(parentAccountId);
            nextCode = Strings.toNextCode(current.getCode() + ".00", ".");
        } else {
            Collections.sort(result);
            Account current = result.get(result.size() - 1); //El ultimo
            nextCode = Strings.toNextCode(current.getCode(), ".");
        }
        return nextCode;
        
    }
}
