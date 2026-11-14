package app;

import armas.EspadaExplorador;
import botas.BotasEnlace;
import casco.CascoHierroSellado;
import casco.CascoRunico;
import casco.ProteccionElemental;
import peto.PetoEnlace;

public class Main {

    private static final String ATAQUE_FUEGO = "FUEGO";

    public static void main(String[] args) {
        System.out.println("PRUEBA FINAL COMPONENT QUEST");
        probarEspada();
        probarPeto();
        probarCasco();
    }

    private static void probarEspada() {
        mostrarTitulo("Ronda 1 Dragon Ignis");
        EspadaExplorador espada = new EspadaExplorador();
        System.out.println("Ataque con espada: " + espada.atacar() + " pts");
    }

    private static void probarPeto() {
        mostrarTitulo("Ronda 2 Golem Sismico");
        PetoEnlace peto = new PetoEnlace(new BotasEnlace());
        System.out.println("Con botas, escudo activo: " + peto.escudoActivo());
        System.out.println("Con botas, proteccion: " + peto.calcularProteccion());
        peto.retirarGema();
        System.out.println("Sin botas, escudo activo: " + peto.escudoActivo());
        System.out.println("Sin botas, proteccion: " + peto.calcularProteccion());
    }

    private static void probarCasco() {
        mostrarTitulo("Ronda 4 Espectro Cambiante");
        ProteccionElemental cascoHierro = new CascoHierroSellado();
        ProteccionElemental cascoRunico = new CascoRunico();
        System.out.println("Casco de hierro ante fuego: " + cascoHierro.resistirAtaque(ATAQUE_FUEGO));
        System.out.println("Casco runico ante fuego: " + cascoRunico.resistirAtaque(ATAQUE_FUEGO));
    }

    private static void mostrarTitulo(String titulo) {
        System.out.println();
        System.out.println(titulo);
    }
}