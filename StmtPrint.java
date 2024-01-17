public class StmtPrint extends Statement {
    final Expression expression;

    StmtPrint(Expression expression) {
        this.expression = expression;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        System.out.println(expression.solve(tabla));
    }

    @Override
    public String toString() {
        return "print " + expression + ";\n";
    }
}
