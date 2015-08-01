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
package org.jlgranda.fede.util;

import com.jlgranda.fede.ejb.mail.reader.FacturaElectronicaMailReader;
import java.io.Serializable;
import java.io.StringReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import org.jlgranda.fede.sri.jaxb.factura.v110.Factura;
import org.jlgranda.fede.sri.jaxb.factura.v110.ObjectFactory;
import org.slf4j.LoggerFactory;

/**
 *
 * @author jlgranda
 */
public class FacturaUtil implements Serializable {
    
    public static final String INICIO_TAG_COMPROBANTE = "<comprobante><![CDATA["; 
    public static final String FIN_TAG_COMPROBANTE = "]]></comprobante>"; 
    public static final String INICIO_TAG_COMPROBANTE_SIN_CDATA = "<comprobante>"; 
    public static final String FIN_TAG_COMPROBANTE_SIN_CDATA = "</comprobante>"; 
    
       
    /**
     * Lee el contenido XML de la factura electrónica a un objeto factura
     * @param xml contenido XML de la factura electrónica
     * @return la instancia factura representada por el XML
     */
    public static Factura read(String xml){
        Factura factura = null;
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            String comprobante = extraerComprobante(corregir(xml));
            if (comprobante != null){
                StringReader reader = new StringReader(comprobante);
                factura = (Factura) unmarshaller.unmarshal(reader);
            } else {
                Logger.getLogger(FacturaUtil.class.getName()).log(Level.INFO, "No se pudo obtener el contendio del comprobante. El contenido XML es: ");
                Logger.getLogger(FacturaUtil.class.getName()).log(Level.INFO, xml);
            }
        } catch (JAXBException ex) {
            Logger.getLogger(FacturaUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return factura;
    }
    
    public static String read(String xml, String tag){
        String tagInicio = tag.split("><")[0] + ">";
        String tagFinal = "<" + tag.split("><")[1];
        int indiceInicioTag = xml.indexOf(tagInicio) + tagInicio.length();
        int indiceFinTag = xml.indexOf(tagFinal);
        
        return xml.substring(indiceInicioTag, indiceFinTag);
        
    }

    public static String extraerComprobante(String xml) {
        int indiceInicioTagComprobante = xml.indexOf(INICIO_TAG_COMPROBANTE);
        int indiceFinTagComprobante = -1;
        if (indiceInicioTagComprobante > -1){
            indiceInicioTagComprobante = indiceInicioTagComprobante + INICIO_TAG_COMPROBANTE.length();
            indiceFinTagComprobante = xml.indexOf(FIN_TAG_COMPROBANTE);
        } else {
            indiceInicioTagComprobante =xml.indexOf(INICIO_TAG_COMPROBANTE_SIN_CDATA);
            if (indiceInicioTagComprobante > -1){
                indiceInicioTagComprobante = indiceInicioTagComprobante + INICIO_TAG_COMPROBANTE_SIN_CDATA.length();
                indiceFinTagComprobante = xml.indexOf(FIN_TAG_COMPROBANTE_SIN_CDATA);
            }
        } 
        return (indiceInicioTagComprobante == -1 || indiceFinTagComprobante == -1) ? null : xml.substring(indiceInicioTagComprobante, indiceFinTagComprobante);
    }
    
    private static String corregir(String xml) {
        if (xml.contains("&lt;")){
            xml = xml.replace("&lt;", "<");
        }
        if (xml.contains("&gt;")){
            xml = xml.replace("&gt;", ">");
        }
        
        return xml;
    }
    
    public static void main(String args[]){
        System.err.println(read("<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:autorizacionComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.autorizacion\"><RespuestaAutorizacionComprobante><claveAccesoConsultada>3105201501119000221300120010260000866211234567814</claveAccesoConsultada><numeroComprobantes>1</numeroComprobantes><autorizaciones><autorizacion><estado>AUTORIZADO</estado><numeroAutorizacion>0706201512424111900022130016097370826</numeroAutorizacion><fechaAutorizacion>2015-06-07T12:42:41.700-05:00</fechaAutorizacion><ambiente>PRODUCCION</ambiente><comprobante>&lt;?xml version=\"1.0\" encoding=\"UTF-8\"?&gt;\n" +
"&lt;factura id=\"comprobante\" version=\"1.1.0\"&gt; &lt;infoTributaria&gt; &lt;ambiente&gt;2&lt;/ambiente&gt; &lt;tipoEmision&gt;1&lt;/tipoEmision&gt; &lt;razonSocial&gt;BANCO DE LOJA S.A.&lt;/razonSocial&gt; &lt;nombreComercial&gt;BANCO DE LOJA S.A.&lt;/nombreComercial&gt; &lt;ruc&gt;1190002213001&lt;/ruc&gt; &lt;claveAcceso&gt;3105201501119000221300120010260000866211234567814&lt;/claveAcceso&gt; &lt;codDoc&gt;01&lt;/codDoc&gt; &lt;estab&gt;001&lt;/estab&gt; &lt;ptoEmi&gt;026&lt;/ptoEmi&gt; &lt;secuencial&gt;000086621&lt;/secuencial&gt; &lt;dirMatriz&gt;BOLIVAR Y ROCAFUERTE&lt;/dirMatriz&gt; &lt;/infoTributaria&gt; &lt;infoFactura&gt; &lt;fechaEmision&gt;31/05/2015&lt;/fechaEmision&gt; &lt;dirEstablecimiento&gt;Bolivar s/n y Rocafuerte&lt;/dirEstablecimiento&gt; &lt;contribuyenteEspecial&gt;3997&lt;/contribuyenteEspecial&gt; &lt;obligadoContabilidad&gt;SI&lt;/obligadoContabilidad&gt; &lt;tipoIdentificacionComprador&gt;05&lt;/tipoIdentificacionComprador&gt; &lt;razonSocialComprador&gt;GRANDA SIVISAPA, JOSE LUIS&lt;/razonSocialComprador&gt; &lt;identificacionComprador&gt;1103826960&lt;/identificacionComprador&gt; &lt;totalSinImpuestos&gt;2.24&lt;/totalSinImpuestos&gt; &lt;totalDescuento&gt;0.00&lt;/totalDescuento&gt; &lt;totalConImpuestos&gt; &lt;totalImpuesto&gt; &lt;codigo&gt;2&lt;/codigo&gt; &lt;codigoPorcentaje&gt;2&lt;/codigoPorcentaje&gt; &lt;baseImponible&gt;2.24&lt;/baseImponible&gt; &lt;valor&gt;0.27&lt;/valor&gt; &lt;/totalImpuesto&gt; &lt;/totalConImpuestos&gt; &lt;propina&gt;00.00&lt;/propina&gt; &lt;importeTotal&gt;2.51&lt;/importeTotal&gt; &lt;moneda&gt;DOLAR&lt;/moneda&gt; &lt;/infoFactura&gt; &lt;detalles&gt; &lt;detalle&gt; &lt;codigoPrincipal&gt;4053013A1&lt;/codigoPrincipal&gt; &lt;descripcion&gt;COSTO POR RETIRO EN CAJEROS DE OTRA ENTIDAD&lt;/descripcion&gt; &lt;cantidad&gt;1&lt;/cantidad&gt; &lt;precioUnitario&gt;0.45&lt;/precioUnitario&gt; &lt;descuento&gt;00.00&lt;/descuento&gt; &lt;precioTotalSinImpuesto&gt;0.45&lt;/precioTotalSinImpuesto&gt; &lt;impuestos&gt; &lt;impuesto&gt; &lt;codigo&gt;2&lt;/codigo&gt; &lt;codigoPorcentaje&gt;2&lt;/codigoPorcentaje&gt; &lt;tarifa&gt;12&lt;/tarifa&gt; &lt;baseImponible&gt;0.45&lt;/baseImponible&gt; &lt;valor&gt;0.05&lt;/valor&gt; &lt;/impuesto&gt; &lt;/impuestos&gt; &lt;/detalle&gt; &lt;detalle&gt; &lt;codigoPrincipal&gt;4053069B2&lt;/codigoPrincipal&gt; &lt;descripcion&gt;COSTO TRANSFERENCIA WEB&lt;/descripcion&gt; &lt;cantidad&gt;1&lt;/cantidad&gt; &lt;precioUnitario&gt;0.45&lt;/precioUnitario&gt; &lt;descuento&gt;00.00&lt;/descuento&gt; &lt;precioTotalSinImpuesto&gt;0.45&lt;/precioTotalSinImpuesto&gt; &lt;impuestos&gt; &lt;impuesto&gt; &lt;codigo&gt;2&lt;/codigo&gt; &lt;codigoPorcentaje&gt;2&lt;/codigoPorcentaje&gt; &lt;tarifa&gt;12&lt;/tarifa&gt; &lt;baseImponible&gt;0.45&lt;/baseImponible&gt; &lt;valor&gt;0.05&lt;/valor&gt; &lt;/impuesto&gt; &lt;/impuestos&gt; &lt;/detalle&gt; &lt;detalle&gt; &lt;codigoPrincipal&gt;4053084C3&lt;/codigoPrincipal&gt; &lt;descripcion&gt;N/D COSTO RECAUDACION CNT WEB&lt;/descripcion&gt; &lt;cantidad&gt;2&lt;/cantidad&gt; &lt;precioUnitario&gt;0.31&lt;/precioUnitario&gt; &lt;descuento&gt;00.00&lt;/descuento&gt; &lt;precioTotalSinImpuesto&gt;0.62&lt;/precioTotalSinImpuesto&gt; &lt;impuestos&gt; &lt;impuesto&gt; &lt;codigo&gt;2&lt;/codigo&gt; &lt;codigoPorcentaje&gt;2&lt;/codigoPorcentaje&gt; &lt;tarifa&gt;12&lt;/tarifa&gt; &lt;baseImponible&gt;0.62&lt;/baseImponible&gt; &lt;valor&gt;0.07&lt;/valor&gt; &lt;/impuesto&gt; &lt;/impuestos&gt; &lt;/detalle&gt; &lt;detalle&gt; &lt;codigoPrincipal&gt;4244056D4&lt;/codigoPrincipal&gt; &lt;descripcion&gt;SRVP TRANSFERNCIA BCE&lt;/descripcion&gt; &lt;cantidad&gt;1&lt;/cantidad&gt; &lt;precioUnitario&gt;0.27&lt;/precioUnitario&gt; &lt;descuento&gt;00.00&lt;/descuento&gt; &lt;precioTotalSinImpuesto&gt;0.27&lt;/precioTotalSinImpuesto&gt; &lt;impuestos&gt; &lt;impuesto&gt; &lt;codigo&gt;2&lt;/codigo&gt; &lt;codigoPorcentaje&gt;2&lt;/codigoPorcentaje&gt; &lt;tarifa&gt;12&lt;/tarifa&gt; &lt;baseImponible&gt;0.27&lt;/baseImponible&gt; &lt;valor&gt;0.03&lt;/valor&gt; &lt;/impuesto&gt; &lt;/impuestos&gt; &lt;/detalle&gt; &lt;detalle&gt; &lt;codigoPrincipal&gt;4244067E5&lt;/codigoPrincipal&gt; &lt;descripcion&gt;SRVP TRAN INTER WEB&lt;/descripcion&gt; &lt;cantidad&gt;1&lt;/cantidad&gt; &lt;precioUnitario&gt;0.45&lt;/precioUnitario&gt; &lt;descuento&gt;00.00&lt;/descuento&gt; &lt;precioTotalSinImpuesto&gt;0.45&lt;/precioTotalSinImpuesto&gt; &lt;impuestos&gt; &lt;impuesto&gt; &lt;codigo&gt;2&lt;/codigo&gt; &lt;codigoPorcentaje&gt;2&lt;/codigoPorcentaje&gt; &lt;tarifa&gt;12&lt;/tarifa&gt; &lt;baseImponible&gt;0.45&lt;/baseImponible&gt; &lt;valor&gt;0.05&lt;/valor&gt; &lt;/impuesto&gt; &lt;/impuestos&gt; &lt;/detalle&gt; &lt;/detalles&gt; &lt;ds:Signature xmlns:ds=\"http://www.w3.org/2000/09/xmldsig#\" xmlns:etsi=\"http://uri.etsi.org/01903/v1.3.2#\" Id=\"Signature89175\"&gt;\n" +
"&lt;ds:SignedInfo Id=\"Signature-SignedInfo702093\"&gt;\n" +
"&lt;ds:CanonicalizationMethod Algorithm=\"http://www.w3.org/TR/2001/REC-xml-c14n-20010315\"&gt;&lt;/ds:CanonicalizationMethod&gt;\n" +
"&lt;ds:SignatureMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#rsa-sha1\"&gt;&lt;/ds:SignatureMethod&gt;\n" +
"&lt;ds:Reference Id=\"SignedPropertiesID814922\" Type=\"http://uri.etsi.org/01903#SignedProperties\" URI=\"#Signature89175-SignedProperties346146\"&gt;\n" +
"&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"&gt;&lt;/ds:DigestMethod&gt;\n" +
"&lt;ds:DigestValue&gt;XnJhAhdDPMMTFERrxoDGdZi9r0A=&lt;/ds:DigestValue&gt;\n" +
"&lt;/ds:Reference&gt;\n" +
"&lt;ds:Reference URI=\"#Certificate1235323\"&gt;\n" +
"&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"&gt;&lt;/ds:DigestMethod&gt;\n" +
"&lt;ds:DigestValue&gt;GLa6Zkhh3pgvq7KYJrnbmoDgSZw=&lt;/ds:DigestValue&gt;\n" +
"&lt;/ds:Reference&gt;\n" +
"&lt;ds:Reference Id=\"Reference-ID-998763\" URI=\"#comprobante\"&gt;\n" +
"&lt;ds:Transforms&gt;\n" +
"&lt;ds:Transform Algorithm=\"http://www.w3.org/2000/09/xmldsig#enveloped-signature\"&gt;&lt;/ds:Transform&gt;\n" +
"&lt;/ds:Transforms&gt;\n" +
"&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"&gt;&lt;/ds:DigestMethod&gt;\n" +
"&lt;ds:DigestValue&gt;mCypBx4N+4WZqzKmPFaXLCX0J8s=&lt;/ds:DigestValue&gt;\n" +
"&lt;/ds:Reference&gt;\n" +
"&lt;/ds:SignedInfo&gt;\n" +
"&lt;ds:SignatureValue Id=\"SignatureValue97757\"&gt;\n" +
"iRt7WaumuKdHEH1GZlqm0PZQVo7utiWSQ3Ghp1CseWhQvmcszSdhGyf6s7ZG8nZLEhgJuPoCsykR\n" +
"fiBzJEm38vV3+aL6ryao0A9jT5uCDblPfSRBRVLwnzI5xgSsDhNHSLcMssTrgFoj5KfSluDomd97\n" +
"gYl1kBHFf+4R3oHlIsB3vn9JVpPLM8FSq76TUmY9NkjhuH+M47/6YxUhlcg8aZ04+i7hAWzB4RKG\n" +
"2x4qpwtCcssREKwy7pUIP1knD2mOwCzcJ+zrwRT7G8lybs5PLIrEyZN7V1Pe1T8oZKBumf9U251d\n" +
"7vDx1Sw5QY0EUiyh0G8/xnYseE4thilKCsZvTg==\n" +
"&lt;/ds:SignatureValue&gt;\n" +
"&lt;ds:KeyInfo Id=\"Certificate1235323\"&gt;\n" +
"&lt;ds:X509Data&gt;\n" +
"&lt;ds:X509Certificate&gt;\n" +
"MIIJVDCCBzygAwIBAgIETkQp/TANBgkqhkiG9w0BAQsFADCBoTELMAkGA1UEBhMCRUMxIjAgBgNV\n" +
"BAoTGUJBTkNPIENFTlRSQUwgREVMIEVDVUFET1IxNzA1BgNVBAsTLkVOVElEQUQgREUgQ0VSVElG\n" +
"SUNBQ0lPTiBERSBJTkZPUk1BQ0lPTi1FQ0lCQ0UxDjAMBgNVBAcTBVFVSVRPMSUwIwYDVQQDExxB\n" +
"QyBCQU5DTyBDRU5UUkFMIERFTCBFQ1VBRE9SMB4XDTE1MDIyMDIwMDEzN1oXDTE2MDIyMDIwMzEz\n" +
"N1owgbMxCzAJBgNVBAYTAkVDMSIwIAYDVQQKExlCQU5DTyBDRU5UUkFMIERFTCBFQ1VBRE9SMTcw\n" +
"NQYDVQQLEy5FTlRJREFEIERFIENFUlRJRklDQUNJT04gREUgSU5GT1JNQUNJT04tRUNJQkNFMQ4w\n" +
"DAYDVQQHEwVRVUlUTzE3MBEGA1UEBRMKMDAwMDA0MjgyODAiBgNVBAMTG0VOQSBNQVJJQU5FTEEg\n" +
"R1VFUlJFUk8gTU9SQTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAKV3Xwfz/JgsTCMB\n" +
"GTE8wdVhoAx9uU7Xn4Udub3eSJImf26jTvydtkfZvYYhiRQEV7pgyA01HpcF6jtPu8tQaCtEeAOx\n" +
"mr819tSpH996dPCEUoT1nBAzfm3jRw4wxEcT6BceHGFbj/SgyReSvxhYPz0vcdOMMCJ65SwdkhEh\n" +
"vNBgnWJI9u4yIXLANf807sRr//1mAgGyolQ6j2ZM70ioJb4lGcFCQFRqyhB4BoSI8MnDnTsS4nzi\n" +
"8GEyfGq0g547shxNAVsFyPSzXdJKBIcp1Qt5F3RuI3RUgx0OEMwx0PIEpAlv2mb4jVxEJkMqXlEQ\n" +
"7MxjYkwjfLFiGWfq4WWGDG0CAwEAAaOCBH4wggR6MIGRBggrBgEFBQcBAQSBhDCBgTA+BggrBgEF\n" +
"BQcwAYYyaHR0cDovL29jc3AuZWNpLmJjZS5lYy9lamJjYS9wdWJsaWN3ZWIvc3RhdHVzL29jc3Aw\n" +
"PwYIKwYBBQUHMAGGM2h0dHA6Ly9vY3NwMS5lY2kuYmNlLmVjL2VqYmNhL3B1YmxpY3dlYi9zdGF0\n" +
"dXMvb2NzcDAgBgorBgEEAYKoOwMKBBITEEJBTkNPIERFIExPSkEgU0EwHQYKKwYBBAGCqDsDCwQP\n" +
"Ew0xMTkwMDAyMjEzMDAxMBoGCisGAQQBgqg7AwEEDBMKMTEwMzU5NjUyMjAdBgorBgEEAYKoOwMC\n" +
"BA8TDUVOQSBNQVJJQU5FTEEwGAYKKwYBBAGCqDsDAwQKEwhHVUVSUkVSTzAUBgorBgEEAYKoOwME\n" +
"BAYTBE1PUkEwJAYKKwYBBAGCqDsDBQQWExRKRUZFIERFIENPTlRBQklMSURBRDAkBgorBgEEAYKo\n" +
"OwMHBBYTFEJPTElWQVIgWSBST0NBRlVFUlRFMBkGCisGAQQBgqg7AwgECxMJMDczNzAxNjAwMBQG\n" +
"CisGAQQBgqg7AwkEBhMETG9qYTAXBgorBgEEAYKoOwMMBAkTB0VDVUFET1IwIAYKKwYBBAGCqDsD\n" +
"MwQSExBTT0ZUV0FSRS1BUkNISVZPMCoGA1UdEQQjMCGBH2VuYV9ndWVycmVyb0BiYW5jb2RlbG9q\n" +
"YS5maW4uZWMwggHfBgNVHR8EggHWMIIB0jCCAc6gggHKoIIBxoaB1WxkYXA6Ly9iY2VxbGRhcHN1\n" +
"YnAxLmJjZS5lYy9jbj1DUkwyMDQsY249QUMlMjBCQU5DTyUyMENFTlRSQUwlMjBERUwlMjBFQ1VB\n" +
"RE9SLGw9UVVJVE8sb3U9RU5USURBRCUyMERFJTIwQ0VSVElGSUNBQ0lPTiUyMERFJTIwSU5GT1JN\n" +
"QUNJT04tRUNJQkNFLG89QkFOQ08lMjBDRU5UUkFMJTIwREVMJTIwRUNVQURPUixjPUVDP2NlcnRp\n" +
"ZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZYY0aHR0cDovL3d3dy5lY2kuYmNlLmVjL0NSTC9lY2lf\n" +
"YmNlX2VjX2NybGZpbGVjb21iLmNybKSBtTCBsjELMAkGA1UEBhMCRUMxIjAgBgNVBAoTGUJBTkNP\n" +
"IENFTlRSQUwgREVMIEVDVUFET1IxNzA1BgNVBAsTLkVOVElEQUQgREUgQ0VSVElGSUNBQ0lPTiBE\n" +
"RSBJTkZPUk1BQ0lPTi1FQ0lCQ0UxDjAMBgNVBAcTBVFVSVRPMSUwIwYDVQQDExxBQyBCQU5DTyBD\n" +
"RU5UUkFMIERFTCBFQ1VBRE9SMQ8wDQYDVQQDEwZDUkwyMDQwCwYDVR0PBAQDAgUgMB8GA1UdIwQY\n" +
"MBaAFBj58PvmMhyZZjkqyouyaX1JJ7/OMB0GA1UdDgQWBBRUB9wgmpPagGTT9cLSQ+1y/fpZmTAJ\n" +
"BgNVHRMEAjAAMBkGCSqGSIb2fQdBAAQMMAobBFY4LjEDAgSQMA0GCSqGSIb3DQEBCwUAA4ICAQBz\n" +
"tttB1ojXNhoBIedLIk5DzhuQkjuIZaUG0xNhnuvjmZhl3ocfM/a2Ccic87FzZAr9DypY3deE9R01\n" +
"4QJnCppoKai8HG0tFYeCecJ0yLbFZZVW8OYw0GjeqycqLEX1duERQYk8HO+KbWMSnJlHZL6w+Q54\n" +
"6euOkLu7rZFjWph4GUWHWqODKThoQs1Upg61htg2ziemrAmUjpSixVhu+wsJ/N6B+po1gmR5uUP5\n" +
"RAlBd9MkcVqFgUg8Zv1U0ctm48F30wkVFHRogAcyJNQSg2R/a+sTfF9Y2qKRMTdoq+1OzfdKbFvh\n" +
"36QvgNDbDOcmnoUhkjPXFwoONYSxxcQIbpsLX0Ki/Z4uMEnYeGF2hrRAgbNSUKWZCajA3KOavK89\n" +
"H1hGmz1uVixdmhxr9YEKdUqp+ESUqP/fbVXtKrrXqOAkbEAH86nFMTDq9HzsPhRtCnlbkVPIfZX/\n" +
"gwmMQcfuWnNmNb+8y9v5ael9+DUahGYLKqIrRjpxidEBaOenmuZqfxCbmhMF2jHEUd09/K7WM+Xa\n" +
"u643F1rJtY4AgHZrdP9Cx9KRAzXC2+lrg8Q9TET6NWYey1+5xXg4SMQFOSukDUS65tKBL81a40Ie\n" +
"UGJcu8q8Ow1DVkB452/ROMATbWT+Rp+JiWe9bCWnz42rCN/B+T3FfZ9gLkNreuHCLKZnFV2RqA==\n" +
"&lt;/ds:X509Certificate&gt;\n" +
"&lt;/ds:X509Data&gt;\n" +
"&lt;ds:KeyValue&gt;\n" +
"&lt;ds:RSAKeyValue&gt;\n" +
"&lt;ds:Modulus&gt;\n" +
"pXdfB/P8mCxMIwEZMTzB1WGgDH25TtefhR25vd5IkiZ/bqNO/J22R9m9hiGJFARXumDIDTUelwXq\n" +
"O0+7y1BoK0R4A7GavzX21Kkf33p08IRShPWcEDN+beNHDjDERxPoFx4cYVuP9KDJF5K/GFg/PS9x\n" +
"04wwInrlLB2SESG80GCdYkj27jIhcsA1/zTuxGv//WYCAbKiVDqPZkzvSKglviUZwUJAVGrKEHgG\n" +
"hIjwycOdOxLifOLwYTJ8arSDnjuyHE0BWwXI9LNd0koEhynVC3kXdG4jdFSDHQ4QzDHQ8gSkCW/a\n" +
"ZviNXEQmQypeURDszGNiTCN8sWIZZ+rhZYYMbQ==\n" +
"&lt;/ds:Modulus&gt;\n" +
"&lt;ds:Exponent&gt;AQAB&lt;/ds:Exponent&gt;\n" +
"&lt;/ds:RSAKeyValue&gt;\n" +
"&lt;/ds:KeyValue&gt;\n" +
"&lt;/ds:KeyInfo&gt;\n" +
"&lt;ds:Object Id=\"Signature89175-Object734576\"&gt;&lt;etsi:QualifyingProperties Target=\"#Signature89175\"&gt;&lt;etsi:SignedProperties Id=\"Signature89175-SignedProperties346146\"&gt;&lt;etsi:SignedSignatureProperties&gt;&lt;etsi:SigningTime&gt;2015-06-07T12:42:34-05:00&lt;/etsi:SigningTime&gt;&lt;etsi:SigningCertificate&gt;&lt;etsi:Cert&gt;&lt;etsi:CertDigest&gt;&lt;ds:DigestMethod Algorithm=\"http://www.w3.org/2000/09/xmldsig#sha1\"&gt;&lt;/ds:DigestMethod&gt;&lt;ds:DigestValue&gt;jryjvMvaZuC8v5yowhrUJFaaZys=&lt;/ds:DigestValue&gt;&lt;/etsi:CertDigest&gt;&lt;etsi:IssuerSerial&gt;&lt;ds:X509IssuerName&gt;CN=AC BANCO CENTRAL DEL ECUADOR,L=QUITO,OU=ENTIDAD DE CERTIFICACION DE INFORMACION-ECIBCE,O=BANCO CENTRAL DEL ECUADOR,C=EC&lt;/ds:X509IssuerName&gt;&lt;ds:X509SerialNumber&gt;1313090045&lt;/ds:X509SerialNumber&gt;&lt;/etsi:IssuerSerial&gt;&lt;/etsi:Cert&gt;&lt;/etsi:SigningCertificate&gt;&lt;etsi:SignerRole&gt;&lt;etsi:ClaimedRoles&gt;&lt;etsi:ClaimedRole&gt;Rol de firma&lt;/etsi:ClaimedRole&gt;&lt;/etsi:ClaimedRoles&gt;&lt;/etsi:SignerRole&gt;&lt;/etsi:SignedSignatureProperties&gt;&lt;etsi:SignedDataObjectProperties&gt;&lt;etsi:DataObjectFormat ObjectReference=\"#Reference-ID-998763\"&gt;&lt;etsi:Description&gt;contenido firma&lt;/etsi:Description&gt;&lt;etsi:MimeType&gt;text/xml&lt;/etsi:MimeType&gt;&lt;/etsi:DataObjectFormat&gt;&lt;/etsi:SignedDataObjectProperties&gt;&lt;/etsi:SignedProperties&gt;&lt;/etsi:QualifyingProperties&gt;&lt;/ds:Object&gt;&lt;/ds:Signature&gt;&lt;/factura&gt;</comprobante><mensajes/></autorizacion></autorizaciones></RespuestaAutorizacionComprobante></ns2:autorizacionComprobanteResponse></soap:Body></soap:Envelope>", "<numeroAutorizacion></numeroAutorizacion>"));
    }

}
