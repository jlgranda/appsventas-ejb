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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.sales.Product;
import org.jlgranda.fede.model.sales.ProductType;
import org.jlgranda.fede.model.sales.Product_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.model.TaxType;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;

/**
 * Objeto de servicio de productos.
 *
 * @author jlgranda
 */
@Stateless
public class ProductService extends BussinesEntityHome<Product> {

    private static final long serialVersionUID = -6428094275651428620L;

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Product.class);
    }

    @Override
    public Product createInstance() {

        Product _instance = new Product();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        _instance.setProductType(ProductType.RAW_MATERIAL);
        _instance.setTaxType(TaxType.IVA);
        return _instance;
    }
    
    @Override
    public Product save(Product product) {
        super.save(product);
        if (product.getId() != null) {
            this.setId(product.getId());
        } else {
            Map<String, Object> filters = new HashMap<>();
            filters.put("code", product.getCode()); //Recuperar por código
            product = this.find(filters).getResult().get(0); //debe ser único
        }
        return product;
    }

    //soporte para lazy data model
    public long count() {
        return super.count(Product.class);
    }

    public List<Product> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Product> query = builder.createQuery(Product.class);

        Root<Product> from = query.from(Product.class);
        query.select(from).orderBy(builder.desc(from.get(Product_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    public List<Product> findByOrganization(Organization organization) {
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }

    public List<Product> findByOrganizationAndType(Organization organization, ProductType productType) {
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        params.put("productType", productType);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }

    public List<Product> findByOrganizationAndTypesEspecifics(Organization organization, List<ProductType> tipos) {
        return this.findByNamedQuery("Product.findByOrganizationAndProductTypes", organization, tipos);
    }

    public List<Product> findByOrganizationAndName(Organization organization, String name) {
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        params.put("name", name);
        return this.find(-1, -1, "name", QuerySortOrder.ASC, params).getResult();
    }

    public List<Product> findWhithoutKardex(Organization organization) {
        return super.findByNamedQuery("Product.findWhithoutKardex", organization);
    }
}
