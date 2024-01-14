import java.util.*;

public class TablaSimbolos {
    private Map<String, Map<String, Object>> symbolTable;

    public TablaSimbolos() {
        symbolTable = new HashMap<>();
        symbolTable.put("global", new HashMap<>()); // Tabla global
    }

    public void entrarNuevoAlcance(String nombreAlcance) {
        symbolTable.put(nombreAlcance, new HashMap<>());
    }

    public void salirAlcanceActual(String nombreAlcance) {
        symbolTable.remove(nombreAlcance);
    }

    public void asignar(String identificador, Object valor, String nombreAlcance) {
        Map<String, Object> scope = symbolTable.get(nombreAlcance);
        if (scope != null) {
            scope.put(identificador, valor);
        } else {
            throw new RuntimeException("Ámbito no encontrado: '" + nombreAlcance + "'.");
        }
    }

    public boolean existeIdentificador(String identificador, String nombreAlcance) {
        Map<String, Object> scope = symbolTable.get(nombreAlcance);
        return scope != null && scope.containsKey(identificador);
    }

    public Object obtener(String identificador, String nombreAlcance) {
        Map<String, Object> scope = symbolTable.get(nombreAlcance);
        if (scope != null) {
            if (scope.containsKey(identificador)) {
                return scope.get(identificador);
            } else {
                throw new RuntimeException("Variable no definida '" + identificador + "'.");
            }
        }
        throw new RuntimeException("Ámbito no encontrado: '" + nombreAlcance + "'.");
    }
}
