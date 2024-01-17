public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        // Resuelve la expresión utilizando la tabla de símbolos.
        // En este contexto, "resolver" significa evaluar la expresión en el contexto de la tabla de símbolos.
        expression.solve(tabla);
    }

    @Override
    public String toString() {
        // Devuelve la representación en cadena de la expresión seguida de un punto y coma.
        return expression.toString() + ";";
    }
}
