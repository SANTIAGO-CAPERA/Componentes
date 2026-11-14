package casco;

import java.util.Set;

public class CascoRunico implements ProteccionElemental {

    private static final Set<String> ATAQUES_RESISTIDOS = Set.of("FUEGO", "HIELO", "RAYO", "MAGICO");

    @Override
    public String resistirAtaque(String elemento) {
        if (elemento != null && ATAQUES_RESISTIDOS.contains(elemento.toUpperCase())) {
            return "Ataque de " + elemento + " absorbido por las runas";
        }
        return "El casco runico no protege contra " + elemento;
    }
}