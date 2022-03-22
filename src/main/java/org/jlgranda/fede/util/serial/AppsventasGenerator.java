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
package org.jlgranda.fede.util.serial;

import java.io.Serializable;
import java.util.Calendar;
import org.jpapi.util.Strings;

/**
 * Generador de seriales para AppsVentas
 * @author jlgranda
 */
public final class AppsventasGenerator implements Generator, Serializable {

    private static final long serialVersionUID = 8609846404414924601L;
    
    private boolean addYear;
    private int digits;
    private String prefix;

    private Long seed = 0L;

    public AppsventasGenerator() {
        init();
    }
    
    public AppsventasGenerator(String prefix, boolean addYear, int digits, Long seed) {
        setPrefix(prefix);
        setAddYear(addYear);
        setDigits(digits);
        setSeed(seed);
    }

    @Override
    public boolean isAddYear() {
        return addYear;
    }

    public void setAddYear(boolean addYear) {
        this.addYear = addYear;
    }

    @Override
    public int getDigits() {
        return digits;
    }

    public void setDigits(int digits) {
        this.digits = digits;
    }

    @Override
    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getYear() {
        if (!isAddYear())
			return "-";

		Calendar c = Calendar.getInstance();
		return "-" + String.valueOf(c.get(Calendar.YEAR)) + "-";
    }

    public Long getSeed() {
        return seed;
    }

    public void setSeed(Long seed) {
        this.seed = seed;
    }

    private void init() {
        setPrefix("APPSV");
        setAddYear(true);
        setDigits(8);
        setSeed(Calendar.getInstance().getTimeInMillis());
    }

    @Override
    public String next() {
        this.setSeed(this.getSeed() + 1L);

        return this.getPrefix() + getYear()
                + Strings.complete(this.getDigits(), this.getSeed(), '0');
    }
    
    public static void main (String [] args){
        AppsventasGenerator ag = new AppsventasGenerator();
        AppsventasGenerator ag1 = new AppsventasGenerator("001-001", false, 9, 0L);
        System.err.println(">> " + ag.next());
        System.err.println(">> " + ag.next());
        System.err.println(">> " + ag.next());
        System.err.println(">> " + ag.next());
        System.err.println(">> " + ag.next());
        System.err.println(">> " + ag1.next());
        System.err.println(">> " + ag1.next());
        System.err.println(">> " + ag1.next());
        System.err.println(">> " + ag1.next());
        System.err.println(">> " + ag1.next());
    }
}
