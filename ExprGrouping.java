public class ExprGrouping extends Expression {
    final Expression expression;

    ExprGrouping(Expression expression) {
        this.expression = expression; 
    }

    @Override
    Object solve(TablaSimbolos tabla) {
        return expression.solve(tabla);
    }

    @Override
    public String toString() {
        return "(" + expression + ")";
    }

}

