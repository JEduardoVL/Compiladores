/*public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }
}*/

public class StmtVar extends Statement {
    final Token name;
    final Expression initializer;

    StmtVar(Token name, Expression initializer) {
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public void execute() {
        // Evalúa la expresión inicializadora, si existe, para obtener el valor inicial
        Object value = initializer != null ? initializer.solve() : null;

        // Obtiene la instancia global de TablaSimbolos y declara la variable con su valor inicial
        TablaSimbolos.getInstance().declare(name.getLexema(), value);
    }

    @Override
    public String toString() {
        String initializerString = initializer != null ? initializer.toString() : "null";
        return "var " + name.getLexema() + " = " + initializerString + ";";
    }
}


