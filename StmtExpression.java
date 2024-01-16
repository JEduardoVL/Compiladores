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
        expression.solve();
    }
}

