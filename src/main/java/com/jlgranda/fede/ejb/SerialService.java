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
package com.jlgranda.fede.ejb;

import javax.ejb.Stateless;
import org.jlgranda.fede.util.serial.AppsventasGenerator;
import org.jlgranda.fede.util.serial.Generator;

/**
 *
 * @author jlgranda
 */
@Stateless
public class SerialService {
    /**
     * singletone instance
     */
    private static volatile Generator singleton;

    /**
     * get the singleton Generator
     * @return 
     */
    public static Generator getGenerator() {
        Generator singleton = SerialService.singleton;
        if (singleton == null) {
            synchronized (SerialService.class) {
                singleton = SerialService.singleton;
                if (singleton == null) {
                    SerialService.singleton = singleton = new AppsventasGenerator();
                }
            }
        }
        return singleton;
    }

    public static void main(String[] args) {
        System.out.print(SerialService.getGenerator().next());
    }
}
