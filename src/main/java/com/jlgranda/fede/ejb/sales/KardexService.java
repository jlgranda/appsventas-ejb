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
package com.jlgranda.fede.ejb.sales;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.jlgranda.fede.model.Detailable;
import org.jlgranda.fede.model.sales.Kardex;
import org.jlgranda.fede.model.sales.KardexDetail;
import org.jlgranda.fede.model.sales.Kardex_;
import org.jlgranda.fede.model.sales.Product;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.Organization;
import org.jpapi.model.StatusType;
import org.jpapi.model.profile.Subject;
import org.jpapi.util.Dates;
import org.jpapi.util.QuerySortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author author
 */
@Stateless
public class KardexService extends BussinesEntityHome<Kardex>{
    
    Logger logger = LoggerFactory.getLogger(KardexService.class);
    
    @PersistenceContext
    EntityManager em;
    
    @EJB
    KardexDetailService kardexDetailService;
    
    @PostConstruct
    private void init(){
        setEntityManager(em);
        setEntityClass(Kardex.class);
    }
    
    @Override
    public Kardex createInstance(){
        Kardex _instance = new Kardex();
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        _instance.setAuthor(null); //Establecer al usuario actual
        return _instance;
    }
    
    //Soporte para lazy data model
    public long count(){
        return super.count(Kardex.class);
    }
    
    public List<Kardex> find(int maxresults, int firstresult){
        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Kardex> query = builder.createQuery(Kardex.class);
        
        Root<Kardex> from = query.from(Kardex.class);
        query.select(from).orderBy(builder.desc(from.get(Kardex_.name)));
        return getResultList(query, maxresults, firstresult);
    }
    
    public List<Kardex> findByOrganization(Organization organization){
        Map<String, Object> params = new HashMap<>();
        params.put("organization", organization);
        return this.find(-1,-1, "name", QuerySortOrder.ASC, params).getResult();
    }
    
    
    /**
     *
     * @param details
     * @param prefix
     * @param subject
     * @param organization
     * @param operationType
     * @return
     */
    public List<Kardex> save(List<Detailable> details, String prefix, Subject subject, Organization organization, KardexDetail.OperationType operationType){
        List<Kardex> kardexs = new ArrayList<>();
        Kardex kardex = null;
        KardexDetail kardexDetail = null;
        int factor = KardexDetail.OperationType.COMPRA.equals(operationType) ? 1 : -1; //sumar o restar
        for ( Detailable detail : details ) {
            
            if (!isValid(detail)){
                logger.error("El detalle no es válido {1}", detail);
            } else {
                kardex = this.findByProductAndOrganization(prefix, detail.getProduct(), subject, organization);

                kardexDetail = kardex.findKardexDetail(detail.getBussinesEntityType(), detail.getBussinesEntityId(), operationType); //Encuentra el Detalle correspondiente a la factura
                if (kardexDetail == null) {
                    kardexDetail = kardexDetailService.createInstance();
                    kardexDetail.setOwner(subject);
                    kardexDetail.setAuthor(subject);
                    kardexDetail.setBussinesEntityId(detail.getBussinesEntityId());
                    kardexDetail.setBussinesEntityType(detail.getBussinesEntityType());
                    kardexDetail.setOperationType(operationType);

                } else {
                    //Disminuir los valores acumulados de cantidad y total para al momento de modificar no se duplique el valor a aumentar por la venta
                    if (kardexDetail.getQuantity() != null && kardexDetail.getTotalValue() != null) {
                        kardex.setQuantity( kardex.getQuantity().add( kardexDetail.getQuantity().multiply(BigDecimal.valueOf(factor)) ) );
                        kardex.setFund( kardex.getFund().add(kardexDetail.getTotalValue().multiply(BigDecimal.valueOf(factor))) );
                        kardexDetail.setCummulativeQuantity(kardex.getQuantity());
                        kardexDetail.setCummulativeTotalValue(kardex.getFund());
                    }
                    kardexDetail.setAuthor(subject); //Saber quien lo modificó por última vez
                    kardexDetail.setLastUpdate(Dates.now()); //Saber la hora que modificó por última vez
                }

                //Actualizar cantidades
                kardexDetail.setCode(detail.getBussinesEntityCode());
                kardexDetail.setUnitValue(detail.getPrice());
                kardexDetail.setQuantity(detail.getAmount());
                kardexDetail.setTotalValue(kardexDetail.getUnitValue().multiply(kardexDetail.getQuantity()));

                if (kardex.isPersistent()) {
                    kardexDetail.setCummulativeQuantity(kardexDetail.getQuantity().multiply(BigDecimal.valueOf(factor)));
                    kardexDetail.setCummulativeTotalValue(kardexDetail.getTotalValue().multiply(BigDecimal.valueOf(factor)));
                } else {
                    if (kardex.getQuantity() != null && kardex.getFund() != null) {
                        kardexDetail.setCummulativeQuantity(kardex.getQuantity().add(kardexDetail.getQuantity().multiply(BigDecimal.valueOf(factor))));
                        kardexDetail.setCummulativeTotalValue(kardex.getFund().subtract(kardexDetail.getTotalValue()));
                    }
                }

                kardex.addKardexDetail(kardexDetail);

                if (kardex.getCode() == null) {
                    kardex.setCode(prefix + detail.getProduct().getId());
                }
                kardex.setQuantity(kardexDetail.getCummulativeQuantity());
                kardex.setFund(kardexDetail.getCummulativeTotalValue());

                kardexs.add(save(kardex.getId(), kardex)); //Para regresar los valores creados/modificados
            }
        }
        
        return kardexs;
    }
    
    /**
     * Retorna el kardex activo para el producto en la organización.Si el Kardex no existe lo crea.
     * @param prefix 
     * @param product
     * @param subject
     * @param organization
     * @return 
     */
    public Kardex findByProductAndOrganization(String prefix, Product product, Subject subject, Organization organization) {
        Kardex kardex = this.findUniqueByNamedQuery("Kardex.findByProductAndOrg", product, organization);
        
        if (kardex == null) {
            kardex = this.createInstance();
            kardex.setCode(prefix + product.getId());
            kardex.setOwner(subject);
            kardex.setAuthor(subject);
            kardex.setOrganization(organization);
            kardex.setProduct(product);
            kardex.setName(product.getName());
            kardex.setUnitMinimum(BigDecimal.ONE);
            kardex.setUnitMaximum(BigDecimal.ONE);
            
            kardex = save(kardex); //persistir y recuperar instancia
        } 
        
        return kardex;
    }

    private boolean isValid(Detailable detail) {
        return detail.getProduct() != null 
                && detail.getAmount() != null
                && detail.getPrice() != null
                && detail.getBussinesEntityType() != null
                && detail.getBussinesEntityId() != null
                && detail.getBussinesEntityCode() != null;
    }
    
}
