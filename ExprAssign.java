public class ExprAssign extends Expression {
    Token name;
    final Expression value;

    ExprAssign(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
    public String toString() {
        return name.lexema + " = " + value + ";\n";
    }

    @Override
    Object solve() {
        // Utiliza la instancia singleton para verificar y asignar valores en la tabla de símbolos
        TablaSimbolos tablaSimbolos = TablaSimbolos.getInstance();

        if (tablaSimbolos.isDeclared(name.lexema)) {
            // Asigna el valor resuelto de la expresión a la variable en la tabla de símbolos
            tablaSimbolos.declare(name.lexema, value.solve());
        } else {
            // Lanza una excepción si el identificador no existe en la tabla de símbolos
            throw new RuntimeException("La variable " + name.lexema + " no ha sido declarada.");
        }

        return null; // Retorna null ya que la asignación no produce un valor por sí misma
    }

    
}
