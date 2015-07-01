/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jlgranda.fede.model.taxes;

import java.math.BigDecimal;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import org.jpapi.model.BussinesEntity;

/**
 *
 * @author jlgranda
 */
@Entity
@Table(name = "IMPUESTO")
@DiscriminatorValue(value = "TAX")
@PrimaryKeyJoinColumn(name = "taxId")
public class Impuesto extends BussinesEntity {
    private static final long serialVersionUID = 1139635351877573337L;
    
    private String codigoPorcentaje;
    private BigDecimal baseImponible;
    private BigDecimal valor;

    public String getCodigoPorcentaje() {
        return codigoPorcentaje;
    }

    public void setCodigoPorcentaje(String codigoPorcentaje) {
        this.codigoPorcentaje = codigoPorcentaje;
    }

    public BigDecimal getBaseImponible() {
        return baseImponible;
    }

    public void setBaseImponible(BigDecimal baseImponible) {
        this.baseImponible = baseImponible;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
    
}
