/*
 * Copyright (C) 2021 jlgranda
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
package org.jlgranda.fede.model.config.accounting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.jpapi.model.PersistentObject;

/**
 * Basado en documentación: https://youtu.be/eyZ-p9UCKOY?t=841
 * @author jlgranda
 */
@Entity
@Table(name = "GeneralJournal")
@NamedQueries({ @NamedQuery(name = "Journal.findByName", query = "select s FROM Journal s WHERE s.name = ?1 and s.owner is null ORDER BY 1"),
@NamedQuery(name = "Journal.findByNameAndOwner", query = "select s FROM Journal s WHERE s.name = ?1 and s.owner = ?2 ORDER BY 1")})
public class GeneralJournal extends PersistentObject<GeneralJournal> implements Comparable<GeneralJournal>, Serializable {
    
    @OneToMany(mappedBy = "journal")
    List<Record> records = new ArrayList<>();

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }
    
    public Record addRecord(Record record){
        record.setJournal(this);
        this.records.add(record);
        return record;
    }

    @Override
    public int compareTo(GeneralJournal t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
