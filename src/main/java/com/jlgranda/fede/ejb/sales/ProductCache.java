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
package com.jlgranda.fede.ejb.sales;

import com.google.common.base.Optional;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.jlgranda.fede.model.sales.Product;
import org.jlgranda.fede.model.sales.ProductType;
import org.jpapi.model.Organization;
import org.jpapi.util.Strings;

/**
 * Cache de productos de venta al público
 *
 * @author jlgranda
 */
@Singleton
public class ProductCache {

    @EJB
    ProductService productService;

    private final Map<Long, Product> products = new HashMap<>();

    @PostConstruct
    public void init() {
        load();
    }

    /**
     * Cargar productos en el cache.
     */
    public void load() {

        List<Product> productList = productService.findByNamedQuery("Product.findByOrganization");
        productList.forEach(p -> {
            products.put(p.getId(), p);
        });
    }

    public Product lookup(Long key) {
        Product product = null;
        if (products.containsKey(key)) {
            product = products.get(key);
        }
        return product; //devolver por defecto la clave buscada
    }

    private static final Integer REFRESH_PRODUCT_AFTER_5_SECONDS = 5;
    private static final Integer EXPIRE_PRODUCT_AFTER_1_DAY = 1;
    private final LoadingCache<String, Optional<Product>> cache;

    public ProductCache() {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_PRODUCT_AFTER_1_DAY, TimeUnit.DAYS)
                .refreshAfterWrite(REFRESH_PRODUCT_AFTER_5_SECONDS, TimeUnit.SECONDS)
                .build(new CacheLoader<String, Optional<Product>>() {
                    @Override
                    public Optional<Product> load(String itemId) throws Exception {
                        return loadCache(Long.valueOf(itemId));
                    }
                }
                );
    }

    public Optional<Product> getEntry(String itemId) {
        return cache.getUnchecked(itemId);
    }

    private Optional<Product> loadCache(Long productId) {
        Product item = lookup(productId);
        return Optional.fromNullable(item);
    }

    public List<Product> lookup(String key, int attempt) {
        List<Product> matches = new ArrayList<>();

        products.values().stream().filter(product -> (product.getName().toLowerCase().matches(Strings.toRegex(key.toLowerCase())))).forEachOrdered(product -> {
            matches.add(product);
        });
        return matches; //devolver por defecto la clave buscada
    }

    public List<Product> lookup(String key, ProductType productType, Organization organization) {
        return lookup(key, productType, organization, 0);
    }

    public List<Product> lookupContains(String key, ProductType productType, Organization organization, List<Product> productsContains) {
        return lookup(key, productType, organization, 0).stream()
                .filter(element -> productsContains.contains(element))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param key
     * @param productType
     * @param organization
     * @param attempt
     * @return
     */
    public List<Product> lookup(String key, ProductType productType, Organization organization, int attempt) {
        List<Product> matches = new ArrayList<>();
        products.values().stream().filter(product -> (!Strings.isNullOrEmpty(product.getName()) && product.getName().toLowerCase().matches(Strings.toRegex(key.toLowerCase()))
                && productType.equals(product.getProductType()) && organization.equals(product.getOrganization()))).forEachOrdered(product -> {
            matches.add(product);
        });
        return matches; //devolver por defecto la clave buscada
    }

    public List<Product> filterByOrganization(Organization organization) {
        List<Product> matches = new ArrayList<>();
        if (organization == null) {
            return matches; //vacio
        }
        products.values().stream().filter(product -> (organization.equals(product.getOrganization()))).forEachOrdered(product -> {
            matches.add(product);
        });
        return matches; //devolver por defecto la clave buscada
    }
}
