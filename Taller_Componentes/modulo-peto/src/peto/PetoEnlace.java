package peto;

import core.GemaEnergia;

public class PetoEnlace {

    private static final int PROTECCION_BASE = 60;
    private static final int PROTECCION_POTENCIADA = 90;

    private GemaEnergia gema;

    public PetoEnlace(GemaEnergia gema) {
        this.gema = gema;
    }

    public void retirarGema() {
        this.gema = null;
    }

    public boolean escudoActivo() {
        return true;
    }

    public int calcularProteccion() {
        return tieneGemaCargada() ? PROTECCION_POTENCIADA : PROTECCION_BASE;
    }

    private boolean tieneGemaCargada() {
        return gema != null && gema.tieneCarga();
    }
}
