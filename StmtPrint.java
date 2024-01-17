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
    public void execute() {
        Object value = expression.solve();
        System.out.println(value); // Imprime el valor evaluado
    }

    @Override
    public String toString() {
        return "print " + expression + ";";
    }
}
