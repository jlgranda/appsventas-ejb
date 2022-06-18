/*
 * Copyright (C) 2015 jlgranda
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
package com.jlgranda.fede.ejb.url.reader;

import com.jlgranda.fede.ejb.mail.reader.FacturaReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;
import org.jlgranda.fede.util.FacturaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
public class FacturaElectronicaURLReader {

    Logger logger = LoggerFactory.getLogger(FacturaElectronicaURLReader.class);

    /**
     * Obtiene la lista de objetos factura para el sujeto en fede
     *
     * @param urls URLs hacia los archivo XML o ZIP a leer
     * @return una lista de instancias FacturaReader
     */
    public static List<FacturaReader> getFacturasElectronicas(List<String> urls) throws Exception {
        List<FacturaReader> result = new ArrayList<>();
        for (String url : urls) {
            result.add((FacturaReader) FacturaElectronicaURLReader.getFacturaElectronica(url));
        }
        return result;
    }

    /**
     * Obtiene la lista de objetos factura para el sujeto en fede
     *
     * @param url
     * @return una lista de instancias FacturaReader
     * @throws java.lang.Exception
     */
    public static FacturaReader getFacturaElectronica(String url) throws Exception {
        FacturaReader facturaReader = null;

        if (url.endsWith(".xml")) {
            String xml = FacturaElectronicaURLReader.read(url);
            facturaReader = new FacturaReader(FacturaUtil.read(xml), xml, url);
        } else if (url.endsWith(".zip")) {
            URL url_ = new URL(url);
            InputStream is = url_.openStream();
            ZipInputStream zis = new ZipInputStream(is);
            try {

                ZipEntry entry = null;
                String tmp = null;
                ByteArrayOutputStream fout = null;
                while ((entry = zis.getNextEntry()) != null) {
                    if (entry.getName().toLowerCase().endsWith(".xml")) {
                        fout = new ByteArrayOutputStream();
                        for (int c = zis.read(); c != -1; c = zis.read()) {
                            fout.write(c);
                        }

                        tmp = new String(fout.toByteArray(), Charset.defaultCharset());
                        facturaReader = new FacturaReader(FacturaUtil.read(tmp), tmp, url);
                        fout.close();
                    }
                    zis.closeEntry();
                }
                zis.close();

            } catch (IOException ioe) {
                ioe.printStackTrace();
            } finally {
                IOUtils.closeQuietly(is);
                IOUtils.closeQuietly(zis);
            }
        }

        return facturaReader;
    }

    /**
     * Leer contenido texto UTF-8 desde el URL dado
     *
     * @param _url el URL a leer
     * @return el contenido del URL como String
     * @throws Exception
     */
    public static String read(String _url)
            throws Exception {

        StringBuilder b = new StringBuilder();
        try {

            //logger.info("HTTP request: " + _url);
            final URL url = new URL(_url);
            final HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection
                    .setRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_5) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
            b.append(slurp(connection.getInputStream(), 1024));

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return b.toString();

    }

    private static String slurp(final InputStream is, final int bufferSize) {
        final char[] buffer = new char[bufferSize];
        final StringBuilder out = new StringBuilder();
        try (Reader in = new InputStreamReader(is, "UTF-8")) {
            for (;;) {
                int rsz = in.read(buffer, 0, buffer.length);
                if (rsz < 0) {
                    break;
                }
                out.append(buffer, 0, rsz);
            }
        } catch (UnsupportedEncodingException ex) {
            /* ... */
        } catch (IOException ex) {
            /* ... */
        }
        return out.toString();
    }

    public static void main(String args[]) throws Exception {
        List<String> urls = new ArrayList<>();
        urls.add("http://www.tecsicom.net/fet/docs/2307201501180145025300120010320000195431234567811.zip");
        System.out.println(FacturaElectronicaURLReader.getFacturasElectronicas(urls));
    }
}
