/*
 * Copyright (C) 2022 jlgranda
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
package com.jlgranda.fede.ejb.compras;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.jlgranda.fede.model.compras.Proveedor;
import org.jlgranda.fede.model.compras.Proveedor_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jpapi.util.QueryData;
import org.jpapi.util.QuerySortOrder;

import javax.persistence.Parameter;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.jpapi.model.profile.Subject;
import org.jpapi.model.profile.Subject_;

/**
 *
 * @author jlgranda
 */
@Stateless
public class ProveedorService  extends BussinesEntityHome<Proveedor> implements Serializable {
    
    Logger logger = LoggerFactory.getLogger(ProveedorService.class);

    @PersistenceContext
    EntityManager em;

    public ProveedorService() {
    }

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Proveedor.class);
    }


    @Override
    public Proveedor createInstance() {

        Proveedor _instance = new Proveedor();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 7));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setFechaPreferidaPago(Dates.now());
        return _instance;
    }
    
    public long count() {
        return super.count(Proveedor.class); 
    }

    
    public Proveedor findByName(final String name) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Proveedor> query = builder.createQuery(Proveedor.class);

        Root<Proveedor> proveedor = query.from(Proveedor.class);

        query.where(builder.equal(proveedor.get(Proveedor_.name), name));

        return getSingleResult(query);
    }
    
    
    public List<Proveedor> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Proveedor> query = builder.createQuery(Proveedor.class);

        Root<Proveedor> from = query.from(Proveedor.class);
        query.select(from).orderBy(builder.desc(from.get(Proveedor_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    /**
     * Busqueda por filtros dinámicos especializada en entidad Proveedor
     * @param start
     * @param end
     * @param sortField
     * @param order
     * @param filters
     * @return 
     */
    @Override
    public QueryData<Proveedor> find(int start, int end, String sortField,
            QuerySortOrder order, Map<String, Object> filters) {

        QueryData<Proveedor> queryData = new QueryData<>();

        CriteriaBuilder cb = getCriteriaBuilder();
        CriteriaQuery<Proveedor> c = cb.createQuery(this.getEntityClass());
        Root<Proveedor> root = c.from(this.getEntityClass());
        c.select(root);

        CriteriaQuery<Long> countQ = cb.createQuery(Long.class);
        Root<Proveedor> rootCount = countQ.from(this.getEntityClass());
        countQ.select(cb.count(rootCount));

        List<Predicate> criteria = new ArrayList<>();
        List<Predicate> predicates = null;
        if (filters != null) {
            for (String filterProperty : filters.keySet()) {
                Object filterValue = filters.get(filterProperty);
                //System.err.println("---------------> filterProperty: " + filterProperty + ", filterValue: " + filterValue);
                if ("keyword".equalsIgnoreCase(filterProperty)) {
                    Root<Proveedor> bussinesEntity = (Root<Proveedor>) root;

                    //juntar con owner para hacer busqueda de propietarios de personas
                    Join<Proveedor, Subject> joinSubject = bussinesEntity.join(Proveedor_.owner, JoinType.LEFT);
                    //Join<BussinesEntity, Subject> joinAuthor = bussinesEntity.join(BussinesEntity_.author, JoinType.LEFT);

                    //Agregar relación a rootCount
                    ((Root<Proveedor>) rootCount).join(Proveedor_.owner, JoinType.LEFT);
                    //((Root<BussinesEntity>) rootCount).join(BussinesEntity_.author, JoinType.LEFT);

                    Path<String> ownerFirstnamePath = joinSubject.get(Subject_.firstname); // mind these Path objects
                    Path<String> ownerSurnamePath = joinSubject.get(Subject_.surname); // mind these Path objects
                    Path<String> ownerInitialsPath = joinSubject.get(Subject_.surname); // mind these Path objects
                    Path<String> ownerCodePath = joinSubject.get(Subject_.code); // mind these Path objects
//                    Path<String> authorFirstnamePath = joinAuthor.get(Subject_.firstname); // mind these Path objects
//                    Path<String> authorSurnamePath = joinAuthor.get(Subject_.surname); // mind these Path objects
//                    Path<String> authorInitialsPath = joinAuthor.get(Subject_.initials); // mind these Path objects
                    Path<String> namePath = bussinesEntity.get(Proveedor_.name); // mind these Path objects
                    Path<String> codePath = bussinesEntity.get(Proveedor_.code); // mind these Path objects
                    Path<String> descriptionPath = bussinesEntity.get(Proveedor_.description); // mind these Path objects
                    ParameterExpression<String> pexpOwner = cb.parameter(String.class,
                            "onwerName");
//                    ParameterExpression<String> pexpAuthor = cb.parameter(String.class,
//                            "authorName");
                    ParameterExpression<String> pexpName = cb.parameter(String.class,
                            "name");
                    ParameterExpression<String> pexpCode = cb.parameter(String.class,
                            "code");
                    ParameterExpression<String> pexpDescription = cb.parameter(String.class,
                            "description");
                    Predicate predicate = cb.or(cb.like(cb.lower(ownerFirstnamePath), pexpOwner), 
                            cb.like(cb.lower(ownerSurnamePath), pexpOwner), 
                            cb.like(cb.lower(ownerInitialsPath), pexpOwner),
                            cb.like(cb.lower(ownerCodePath), pexpOwner),
//                            cb.like(cb.lower(authorFirstnamePath), pexpAuthor), 
//                            cb.like(cb.lower(authorSurnamePath), pexpAuthor), 
//                            cb.like(cb.lower(authorInitialsPath), pexpAuthor), 
                            cb.like(cb.lower(namePath), pexpName), 
                            cb.like(cb.lower(codePath), pexpCode),  
                            cb.like(cb.lower(descriptionPath), pexpDescription));
                    criteria.add(predicate);
                } else if (filterValue instanceof Map) { //has multiples values
                    predicates = new ArrayList<>();
                    for (Object key : ((Map) filterValue).keySet()) {
                        Object value = ((Map) filterValue).get((String) key);
                        //Verify data content for build
                        if (value instanceof Date) {
                            Path<Date> filterPropertyPath = root.<Date>get(filterProperty);
                            ParameterExpression<Date> pexpStart = cb.parameter(Date.class,
                                    "start");
                            ParameterExpression<Date> pexpEnd = cb.parameter(Date.class,
                                    "end");
                            Predicate predicate = cb.between(filterPropertyPath, pexpStart, pexpEnd);
                            criteria.add(predicate);
                            break;
                        } else if (value instanceof String) { //busqueda de palabra clave en varias columnas
                            Path<String> filterPropertyPath = root.<String>get((String) key);
                            ParameterExpression<String> pexp = cb.parameter(String.class,
                                    (String) key);
                            Predicate predicate = cb.like(cb.lower(filterPropertyPath), pexp);
                            predicates.add(predicate);
                        }
                    }
                    //Si se han definido 
                    if (!predicates.isEmpty()) {
                        Predicate[] array = new Predicate[predicates.size()];
                        criteria.add(cb.or(predicates.toArray(array)));
                    }
                } else if (filterValue instanceof List) { //has multiples values for a column
                    Path<String> filterPropertyPath = root.<String>get((String) filterProperty);
                    ParameterExpression<List> pexp = cb.parameter(List.class,
                            filterProperty);
                    Predicate predicate = filterPropertyPath.in(pexp);
                    criteria.add(predicate);

                } else if (filterValue instanceof String) {
                    ParameterExpression<String> pexp = cb.parameter(String.class,
                            filterProperty);
                    Predicate predicate = cb.like(cb.lower(root.<String>get(filterProperty)), pexp);
                    criteria.add(predicate);
                } else {
                    if (!"summary".equalsIgnoreCase(filterProperty)){
                        Class clazz = filterValue != null ? filterValue.getClass() : Object.class;
                        ParameterExpression<?> pexp = cb.parameter(clazz ,
                                filterProperty);
                        Predicate predicate = null;
                        if (filterValue == null) {
                            predicate = cb.isNull(root.get(filterProperty));
                        } else {
                            predicate = cb.equal(root.get(filterProperty), pexp);
                        }

                        criteria.add(predicate);
                    }
                }
            }
        }

        if (criteria.size() == 1) {
            c.where(criteria.get(0));
            countQ.where(criteria.get(0));
        } else if (criteria.size() > 1) {
            c.where(cb.and(criteria.toArray(new Predicate[0])));
            countQ.where(cb.and(criteria.toArray(new Predicate[0])));
        }

        if (sortField != null) {
            List<Order> orders = new ArrayList<>();
            Path<String> path = null;
            if (!sortField.contains(",")) {
                path = root.get(sortField);
                if (order == QuerySortOrder.ASC) {
                    orders.add(cb.asc(path));
                } else {
                    orders.add(cb.desc(path));
                }
                
            } else {
                for (String field : sortField.split(",")) {
                    path = root.get(field.trim());
                    if (order == QuerySortOrder.ASC) {
                        orders.add(cb.asc(path));
                    } else {
                        orders.add(cb.desc(path));
                    }
                }
            }
            
            c.orderBy(orders);
            
        }

        TypedQuery<Proveedor> q = (TypedQuery<Proveedor>) createQuery(c);
        q.setHint("org.hibernate.cacheable", true);
        TypedQuery<Long> countquery = (TypedQuery<Long>) createQuery(countQ);
        countquery.setHint("org.hibernate.cacheable", true);

        if (filters != null) {
            for (String filterProperty : filters.keySet()) {
                Object filterValue = filters.get(filterProperty);
                //System.err.println("---------------> filterProperty: " + filterProperty + ", filterValue: " + filterValue);
                if ("keyword".equalsIgnoreCase(filterProperty)) {
                    //Establecer dinamicamente los parametros de búsqueda
                    filterValue = "%" + filterValue.toString().toLowerCase() + "%";
                    for (Parameter parameter : q.getParameters()) {
                        if (parameter.getParameterType().equals(String.class)){
                            //System.err.println("---------------> parameter.getName(): " + parameter.getName() + ", filterValue: " + filterValue);
                            q.setParameter(parameter.getName(), filterValue);
                            countquery.setParameter(parameter.getName(), filterValue);
                        }
                    }
                    
                } else if (filterValue instanceof Map) {
                    for (Object key : ((Map) filterValue).keySet()) {
                        Object value = ((Map) filterValue).get((String) key);
                        //Verify data content for build
                        if (value instanceof Date) {
                            q.setParameter(q.getParameter((String) key, Date.class), (Date) value, TemporalType.TIMESTAMP);
                            countquery.setParameter(q.getParameter((String) key, Date.class), (Date) value, TemporalType.TIMESTAMP);
                        } else {
                            String _filterValue = "%" + (String) value + "%";
                            q.setParameter(q.getParameter((String) key, String.class), _filterValue);
                            countquery.setParameter(q.getParameter((String) key, String.class), _filterValue);
                        }
                    }
                } else if (filterValue instanceof List) { //has multiples values for a column
                    q.setParameter(filterProperty, filterValue);
                    countquery.setParameter(filterProperty, filterValue);
                } else if (filterValue instanceof String) {
                    filterValue = "%" + filterValue + "%";
                    q.setParameter(filterProperty, filterValue);
                    countquery.setParameter(filterProperty, filterValue);
                } else if (filterValue != null) {
                    //System.err.println("--------------->Setted filterProperty: " + filterProperty + ", filterValue: " + filterValue);
                    if (!"summary".equalsIgnoreCase(filterProperty)){
                        q.setParameter(filterProperty, filterValue);
                        countquery.setParameter(filterProperty, filterValue);
                    }
                }
            }
        }

        if (start != -1 && end != -1) {
            q.setMaxResults(end - start);
            q.setFirstResult(start);
        }

        queryData.setResult(q.getResultList());
        Long totalResultCount = countquery.getSingleResult();
        queryData.setTotalResultCount(totalResultCount);

        return queryData;
    }
    
}
