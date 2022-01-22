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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.jlgranda.fede.model.accounting.Record;
import org.jlgranda.fede.model.accounting.Record_;
import org.jpapi.controller.BussinesEntityHome;
import org.jpapi.model.CodeType;
import org.jpapi.model.StatusType;
import org.jpapi.util.Dates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author author
 */
@Stateless
public class RecordService extends BussinesEntityHome<Record> {

    private static final long serialVersionUID = -6428094275651428620L;

    Logger logger = LoggerFactory.getLogger(RecordService.class);

    @PersistenceContext
    EntityManager em;

    @PostConstruct
    private void init() {
        setEntityManager(em);
        setEntityClass(Record.class);
    }

    @Override
    public Record createInstance() {

        Record _instance = new Record();
        _instance.setCode(UUID.randomUUID().toString());
        _instance.setCodeType(CodeType.SYSTEM);
        _instance.setEmissionDate(Dates.now());
        _instance.setCreatedOn(Dates.now());
        _instance.setLastUpdate(Dates.now());
        _instance.setStatus(StatusType.ACTIVE.toString());
        _instance.setActivationTime(Dates.now());
        _instance.setExpirationTime(Dates.addDays(Dates.now(), 364));
        return _instance;
    }

    @Override
    public Record save(Record record) {
        super.save(record);
        if (record.getId() != null) {
            this.setId(record.getId());
        } else {
            Map<String, Object> filters = new HashMap<>();
            filters.put("code", record.getCode()); //Recuperar por código
            record = this.find(filters).getResult().get(0); //debe ser único
        }
        return record;
    }

    //soporte para Lazy Data Model
    public long count() {
        return super.count(Record.class);
    }

    public List<Record> find(int maxresults, int firstresult) {

        CriteriaBuilder builder = getCriteriaBuilder();
        CriteriaQuery<Record> query = builder.createQuery(Record.class);

        Root<Record> from = query.from(Record.class);
        query.select(from).orderBy(builder.desc(from.get(Record_.name)));
        return getResultList(query, maxresults, firstresult);
    }

    /**
     * Marcar como eliminado el registro actual para la entidad identificada por
     * Nombre e Id en el diario dado
     *
     * @param generalJournalId
     * @param bussinesEntityName
     * @param bussinesEntityId
     * @return
     */
    public List<Record> deleteLastRecords(Long generalJournalId, String bussinesEntityName, Long bussinesEntityId) {
        List<Record> records = findByNamedQuery("Record.findByBussinesEntityTypeAndId", generalJournalId, bussinesEntityName, bussinesEntityId);
        if (!records.isEmpty()) {
            records.stream().map(record -> {
                record.getRecordDetails().forEach(rd -> {
                    rd.setDeleted(true);
                });
                return record;
            }).map(record -> {
                record.setDeleted(true);
                return record;
            }).forEachOrdered(record -> {
                this.save(record); //Guardar el registro y sus detalles
            });
        }

        return records;
    }

    /**
     * Marcar como eliminado el registro actual para la entidad identificada por
     * Nombre e Id en el diario dado
     *
     * @param generalJournalId
     * @param bussinesEntityName
     * @param bussinesEntityId
     * @param bussinesEntityHashCode
     * @return
     */
    public List<Record> deleteLastRecords(Long generalJournalId, String bussinesEntityName, Long bussinesEntityId, int bussinesEntityHashCode) {
        List<Record> records = findByNamedQuery("Record.findByBussinesEntityTypeAndIdAndHashCode", generalJournalId, bussinesEntityName, bussinesEntityId, bussinesEntityHashCode);
        if (!records.isEmpty()) {
            records.stream().map(record -> {
                record.getRecordDetails().forEach(rd -> {
                    rd.setDeleted(true);
                });
                return record;
            }).map(record -> {
                record.setDeleted(true);
                return record;
            }).forEachOrdered(record -> {
                this.save(record); //Guardar el registro y sus detalles
            });
        }

        return records;
    }

    public Record deleteRecord(Record record) {
        record.getRecordDetails().forEach(rd -> {
            rd.setDeleted(true);
            rd.setDeletedOn(Dates.now());
        });
        record.setDeleted(true);
        record.setDeletedOn(Dates.now());
        this.save(record.getId(), record);
        return record;
    }

    public Record deleteRecord(Long recordId) {
        return deleteRecord(this.find(recordId));
    }
}
