import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class TablaSimbolos {
    private Map<String, Map<String, Object>> symbolTable;
    private List<String> scopeStack;

    public TablaSimbolos() {
        symbolTable = new HashMap<>();
        scopeStack = new ArrayList<>();
        enterNewScope("global"); // Scope global por defecto
    }

    public void enterNewScope(String scopeName) {
        if (!symbolTable.containsKey(scopeName)) {
            symbolTable.put(scopeName, new HashMap<>());
        }
        scopeStack.add(scopeName);
    }
    
    public void exitCurrentScope() {
        if (!scopeStack.isEmpty() && !getCurrentScope().equals("global")) {
            String currentScope = scopeStack.remove(scopeStack.size() - 1);
            symbolTable.remove(currentScope);
        } else {
            throw new RuntimeException("No se puede salir del ámbito global.");
        }
    }

    public void declare(String identifier, Object value) {
        String currentScope = getCurrentScope();
        symbolTable.get(currentScope).put(identifier, value);
    }

    public boolean isDeclared(String identifier) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            String scope = scopeStack.get(i);
            if (symbolTable.get(scope).containsKey(identifier)) {
                return true;
            }
        }
        return false;
    }

    public Object get(String identifier) {
        for (int i = scopeStack.size() - 1; i >= 0; i--) {
            String scope = scopeStack.get(i);
            if (symbolTable.get(scope).containsKey(identifier)) {
                return symbolTable.get(scope).get(identifier);
            }
        }
        throw new RuntimeException("Variable no definida: " + identifier);
    }

    private String getCurrentScope() {
        if (scopeStack.isEmpty()) {
            throw new RuntimeException("No hay alcance actual.");
        }
        return scopeStack.get(scopeStack.size() - 1);
    }

    public String getNombreAlcance() {
        return getCurrentScope();
    }
}



