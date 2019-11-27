/*
 * Copyright (C) 2018 jlgranda
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
package org.jlgranda.fede.model.talentohumano;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.jpapi.model.PersistentObject;
import org.jpapi.util.Dates;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "JOURNAL")
@NamedQueries({
    @NamedQuery(name = "Journal.findById", query = "select j FROM Journal j WHERE j.id = ?1"),
    @NamedQuery(name = "Journal.findLastForOwner", query = "select j FROM Journal j WHERE j.owner = ?1 ORDER BY j.beginTime DESC"),
})
public class Journal  extends PersistentObject implements Comparable<Journal>, Serializable {

    private static final long serialVersionUID = -2746655196942302436L;
    
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "beginTime", nullable = false)
    protected Date beginTime;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "endTime", updatable = false, nullable = false)
    protected Date endTime;
    
    @ManyToOne(optional = true, cascade = {CascadeType.DETACH})
    @JoinColumn(name = "employee_id", insertable=false, updatable=false, nullable=false)
    private Employee employee;
    
    @Column(name = "employee_id", insertable=true, updatable=true, nullable=false)
    private Long employeeId;
    
    private String day;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }
    
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Transient
    public boolean isJustChecked(){
        return Dates.calculateNumberOfMinutesBetween(Dates.now(), getBeginTime()) < 1; //reciente si es menor a un minuto
    }
    
    @Transient
    public boolean isJustCreated(){
        return Dates.calculateNumberOfMinutesBetween(Dates.now(), getCreatedOn()) < 15; //reciente si es menor a un minuto
    }
    
    @Transient
    public String getDayHour(){
        if (getBeginTime() == null){
            return null;
        }
        return "" + Dates.get(getBeginTime(), Calendar.MONTH) + Dates.get(getBeginTime(), Calendar.DAY_OF_MONTH);
    }
    
    @Transient
    public String getDayOfWeek(){
        if (getBeginTime() == null){
            return null;
        }
        
        LocalDate localDate = LocalDate.of(Dates.get(getBeginTime(), Calendar.YEAR), Dates.get(getBeginTime(), Calendar.MONTH) + 1, Dates.get(getBeginTime(), Calendar.DAY_OF_MONTH));
        Locale spanishLocale=new Locale("es", "ES");
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        return dayOfWeek.getDisplayName(TextStyle.FULL, spanishLocale).toUpperCase();
    }

//    public static void main(String[] args) throws ParseException {
//        //2019-09-01 10:03:37.782
//        //Date date = new Date("2019-09-01 10:03:37.782");
//       // Date date = Dates.toDate("2019-09-01 10:03:37.782");
//        Date date = Dates.addDays(Dates.now(), -(27+30+30));
//        System.out.println(">>> date: " + date);
//        System.out.println(">>> month: " + Dates.get(date, Calendar.MONTH));
//        System.out.println(">>> day: " + Dates.get(date, Calendar.DAY_OF_MONTH));
//        System.out.println(">>> date: " + Dates.get(date, Calendar.DATE));
//        LocalDate localDate = LocalDate.of(Dates.get(date, Calendar.YEAR), Dates.get(date, Calendar.MONTH)+1, Dates.get(date, Calendar.DAY_OF_MONTH));
//        
//        //Day of week and month in Spanish
//        Locale spanishLocale=new Locale("es", "ES");
//       // String dateInSpanish = localDate.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM, yyyy", spanishLocale));
//        //System.out.println("'2019-09-01' in Spanish: "+dateInSpanish);
//    
//        
//        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
//        System.out.println(">>> " + dayOfWeek.getDisplayName(TextStyle.FULL, spanishLocale));
//        
//    }
    @Transient
    public String getDayOfMonth(){
        if (getBeginTime() == null){
            return null;
        }
        return "" + Dates.get(getBeginTime(), Calendar.DAY_OF_MONTH);
    }

    @Transient
    public String getDay() {
        return day;
    }

    @Transient
    public void setDay(String day) {
        this.day = day;
    }

    
    @Override
    public int compareTo(Journal t) {
        return getBeginTime().compareTo(t.getBeginTime());
    }

    @Override
    public String toString() {
//        return String.format("%s[id=%d, beginDate=%s, endDate=%s]", getClass().getSimpleName(), getId(), getBeginTime(), getEndTime());
        return String.format("%s[id=%d]", getClass().getSimpleName(), getId());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(getId());
        hash = 41 * hash + Objects.hashCode(this.beginTime);
        hash = 41 * hash + Objects.hashCode(this.employee);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Journal other = (Journal) obj;
        if (!Objects.equals(getId(), other.getId())) {
            return false;
        }
        if (!Objects.equals(this.beginTime, other.beginTime)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        return true;
    }

    
}
