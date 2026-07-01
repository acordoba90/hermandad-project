package com.hermandadproject.gestionhermandades.service;

import com.hermandadproject.gestionhermandades.model.entity.CarismaHermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.HermandadEntity;
import com.hermandadproject.gestionhermandades.model.entity.TipoHermandadEntity;
import org.springframework.stereotype.Component;

@Component
public class IndicadoresHermandadCalculator {

    public Integer calcularPrestigio(HermandadEntity hermandad) {
        int tipo = safePrestigioTipo(hermandad.getTipoHermandad());
        int principal = safeInt(hermandad.getCarismaPrincipal() == null ? null : hermandad.getCarismaPrincipal().getPrestigioBase());
        int secundarios = hermandad.getCarismasSecundarios().stream()
                .mapToInt(c -> safeInt(c.getPrestigioBase()))
                .sum();
        return nonNegative(tipo + principal + secundarios);
    }

    public Integer calcularPopularidad(HermandadEntity hermandad) {
        int principal = safeInt(hermandad.getCarismaPrincipal() == null ? null : hermandad.getCarismaPrincipal().getPopularidadBase());
        int secundarios = hermandad.getCarismasSecundarios().stream()
                .mapToInt(c -> safeInt(c.getPopularidadBase()))
                .sum();
        return nonNegative(principal + secundarios);
    }

    public Integer calcularDevocion(HermandadEntity hermandad) {
        int principal = safeInt(hermandad.getCarismaPrincipal() == null ? null : hermandad.getCarismaPrincipal().getDevocionBase());
        int secundarios = hermandad.getCarismasSecundarios().stream()
                .mapToInt(c -> safeInt(c.getDevocionBase()))
                .sum();
        return nonNegative(principal + secundarios);
    }

    public Integer calcularSolemnidad(HermandadEntity hermandad) {
        int principal = safeInt(hermandad.getCarismaPrincipal() == null ? null : hermandad.getCarismaPrincipal().getSolemnidadBase());
        int secundarios = hermandad.getCarismasSecundarios().stream()
                .mapToInt(c -> safeInt(c.getSolemnidadBase()))
                .sum();
        return nonNegative(principal + secundarios);
    }

    public void recalcularIndicadores(HermandadEntity hermandad) {
        hermandad.setPrestigio(calcularPrestigio(hermandad));
        hermandad.setPopularidad(calcularPopularidad(hermandad));
        hermandad.setDevocion(calcularDevocion(hermandad));
        hermandad.setSolemnidad(calcularSolemnidad(hermandad));
    }

    private static int safePrestigioTipo(TipoHermandadEntity tipo) {
        return safeInt(tipo == null ? null : tipo.getPrestigioBase());
    }

    private static int safeInt(Integer value) {
        return value == null ? 0 : value;
    }

    private static int nonNegative(int value) {
        return Math.max(0, value);
    }
}

