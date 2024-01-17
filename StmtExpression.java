/*public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }
}*/

public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public void execute() {
        // Ejecuta la expresión. En este contexto, "ejecutar" generalmente significa "evaluar".
        expression.solve();
    }

    @Override
    public String toString() {
        // Devuelve la representación en cadena de la expresión seguida de un punto y coma.
        return expression.toString() + ";";
    }
}

