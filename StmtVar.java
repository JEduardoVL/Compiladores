/*public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        // Evalúa la expresión inicializadora, si existe, para obtener el valor inicial
        Object value = initializer != null ? initializer.solve(tabla) : null;

        // Verifica si la variable ya ha sido declarada en la tabla de símbolos
        if (tabla.existeIdentificador(name.getLexema())) {
            throw new RuntimeException("Error: La variable " + name.getLexema() + " ya se declaró previamente.");
        }

        // Asigna la variable con su valor inicial en la tabla de símbolos
        tabla.asignar(name.getLexema(), value);
    }

    @Override
    public String toString() {
        String initializerString = initializer != null ? initializer.toString() : "null";
        return "var " + name.getLexema() + " = " + initializerString + ";";
    }
}*/

public class StmtVar extends Statement {
    Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }
    @Override
    public String toString() {
        return "var " + name.lexema + " = " + initializer + ";\n";
    }

    @Override
    void solve(TablaSimbolos tabla){
        if (initializer.solve(tabla) instanceof Integer || initializer.solve(tabla) instanceof Float || initializer.solve(tabla) instanceof Double || initializer.solve(tabla) instanceof String || initializer.solve(tabla) instanceof Boolean){
            if(tabla.existeIdentificador(name.lexema))
                throw new RuntimeException("Error: " + name.lexema + "se declaró previamente.");
            Object value = initializer.solve(tabla);
            tabla.asignar(name.lexema, value);
        }
        else
            throw new RuntimeException("Error semantico: Asignación incompatible");
    }
}

