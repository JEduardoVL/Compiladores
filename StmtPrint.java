/*public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }
}*/

public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }

    @Override
    void execute() {
        Object value = expression.solve();
        System.out.println(value);
    }
}
