
/*import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class TablaSimbolos {
    private static TablaSimbolos instance = null;
    private Map<String, Map<String, Object>> symbolTables;
    private Stack<String> scopeStack;

    private TablaSimbolos() {
        symbolTables = new HashMap<>();
        scopeStack = new Stack<>();
        enterNewScope("global"); // Alcance global por defecto
    }

    public static TablaSimbolos getInstance() {
        if (instance == null) {
            instance = new TablaSimbolos();
        }
        return instance;
    }

    public void enterNewScope(String scopeName) {
        if (!symbolTables.containsKey(scopeName)) {
            symbolTables.put(scopeName, new HashMap<>());
        }
        scopeStack.push(scopeName);
    }

    public void exitCurrentScope() {
        if (!scopeStack.isEmpty() && !getCurrentScope().equals("global")) {
            String currentScope = scopeStack.pop();
            symbolTables.remove(currentScope);
        } else {
            throw new RuntimeException("No se puede salir del ámbito global.");
        }
    }

    public void declare(String identifier, Object value) {
        String currentScope = getCurrentScope();
        symbolTables.get(currentScope).put(identifier, value);
    }

    public boolean isDeclared(String identifier) {
        for (String scope : scopeStack) {
            if (symbolTables.get(scope).containsKey(identifier)) {
                return true;
            }
        }
        return false;
    }

    public Object get(String identifier) {
        for (String scope : scopeStack) {
            if (symbolTables.get(scope).containsKey(identifier)) {
                return symbolTables.get(scope).get(identifier);
            }
        }
        throw new RuntimeException("Variable no definida: " + identifier);
    }

    private String getCurrentScope() {
        if (!scopeStack.isEmpty()) {
            return scopeStack.peek();
        }
        throw new RuntimeException("No hay un alcance actual definido.");
    }
}
*/
import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {

    private final Map<String, Object> values = new HashMap<>();
    final TablaSimbolos anterior;

    TablaSimbolos(TablaSimbolos anterior) {
        this.anterior = anterior;
    }

    boolean existeIdentificador(String identificador){
        if(!values.containsKey(identificador)){
            return buscarEnAnteriores(identificador, this.anterior);
        }
        else
            return true;
    }

    Object obtener(String identificador) {
        if (values.containsKey(identificador)) {
            return values.get(identificador);
        }
        return obtenerDeAnteriores(identificador, this.anterior);
    }


    void asignar(String identificador, Object valor){
        values.put(identificador, valor);
    }

    private boolean buscarEnAnteriores(String identificador, TablaSimbolos tablaAnterior){
        if(tablaAnterior == null) 
            return false;
        if(!tablaAnterior.values.containsKey(identificador))
            return buscarEnAnteriores(identificador, tablaAnterior.anterior);
        else
            return true;
    }

    private Object obtenerDeAnteriores(String identificador, TablaSimbolos tablaAnterior){
        if(tablaAnterior == null)
            throw new RuntimeException("Variable no definida '" + identificador + "'.");
        if(!tablaAnterior.values.containsKey(identificador))
            return obtenerDeAnteriores(identificador, tablaAnterior.anterior);
        else
            return tablaAnterior.values.get(identificador);
    }

    public TablaSimbolos obtenerTabla(String identificador, TablaSimbolos tablaAnterior){
        if(tablaAnterior == null)
            throw new RuntimeException("Variable no definida '" + identificador + "'.");
        if(!tablaAnterior.values.containsKey(identificador))
            return obtenerTabla(identificador, tablaAnterior.anterior);
        else
            return tablaAnterior;
    }
}
