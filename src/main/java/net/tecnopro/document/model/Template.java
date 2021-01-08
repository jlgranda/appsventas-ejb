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
package net.tecnopro.document.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "template")
@NamedQueries({
    @NamedQuery(name = "Template.findByCode", query = "select t FROM Template t where t.code=?1 ORDER BY t.id DESC"),
    })
public class Template extends BussinesEntity implements Comparable<Template>, Serializable {
    
    private static final long serialVersionUID = 6206887274643020128L;
    
    @Column(nullable = true, length = 1024)
    private String title;
    
    @Column(nullable = true, length = 4096)
    private String body;
    
    @Column(nullable = true, length = 2048)
    private String signature;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }


    @Override
    public int compareTo(Template t) {
        return this.getId().compareTo(t.getId());
    }
    
}
