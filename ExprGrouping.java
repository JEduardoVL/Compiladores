/*public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }
}*/

public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression;
    }

    @Override
    public Object solve() {
        // Resolver la expresión interna y devolver su resultado
        return expression.solve();
    }

    @Override
    public String toString() {
        // Devuelve la representación en cadena de la expresión, encerrada entre paréntesis
        return "(" + expression + ")";
    }
}


