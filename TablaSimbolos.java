/*import java.util.*;

public class TablaSimbolos {
    private Map<String, Map<String, Object>> symbolTable;
    private String nombreAlcance;

    public TablaSimbolos(String nombreAlcance) {
        this.nombreAlcance = nombreAlcance;
        symbolTable = new HashMap<>();
    }

    public String getNombreAlcance() {
        return nombreAlcance;
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
*/
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Map<String, Map<String, Object>> symbolTable;
    private String nombreAlcanceActual;

    public TablaSimbolos() {
        symbolTable = new HashMap<>();
        nombreAlcanceActual = "global"; // Alcance global por defecto
        symbolTable.put(nombreAlcanceActual, new HashMap<>());
    }

    public void entrarNuevoAlcance(String nombreAlcance) {
        if (!symbolTable.containsKey(nombreAlcance)) {
            symbolTable.put(nombreAlcance, new HashMap<>());
        }
        nombreAlcanceActual = nombreAlcance;
    }

    public void salirAlcanceActual() {
        // Puedes agregar validaciones para evitar salir del alcance global
        if (!nombreAlcanceActual.equals("global")) {
            symbolTable.remove(nombreAlcanceActual);
            // Cambiar al alcance anterior (puede ser "global")
            nombreAlcanceActual = "global";
        }
    }

    public void asignar(String identificador, Object valor) {
        Map<String, Object> scope = symbolTable.get(nombreAlcanceActual);
        if (scope != null) {
            scope.put(identificador, valor);
        } else {
            throw new RuntimeException("Ámbito no encontrado: '" + nombreAlcanceActual + "'.");
        }
    }

    public boolean existeIdentificador(String identificador) {
        Map<String, Object> scope = symbolTable.get(nombreAlcanceActual);
        return scope != null && scope.containsKey(identificador);
    }

    public Object obtener(String identificador) {
        Map<String, Object> scope = symbolTable.get(nombreAlcanceActual);
        if (scope != null) {
            if (scope.containsKey(identificador)) {
                return scope.get(identificador);
            } else {
                throw new RuntimeException("Variable no definida '" + identificador + "'.");
            }
        }
        throw new RuntimeException("Ámbito no encontrado: '" + nombreAlcanceActual + "'.");
    }

    public String getNombreAlcance() {
        return nombreAlcanceActual;
    }
}
