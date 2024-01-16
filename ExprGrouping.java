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
        return expression.solve();
    }
}

