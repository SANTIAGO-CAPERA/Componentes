package casco;

public class CascoHierroSellado implements ProteccionElemental {

    private static final String ATAQUE_FISICO = "FISICO";

    @Override
    public String resistirAtaque(String elemento) {
        if (ATAQUE_FISICO.equalsIgnoreCase(elemento)) {
            return "Golpe fisico bloqueado";
        }
        return "El casco de hierro no soporta ataques de " + elemento;
    }
}