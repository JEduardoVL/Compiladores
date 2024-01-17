public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        try {
            if ((boolean) condition.solve(tabla))
                thenBranch.solve(tabla);
            else if (elseBranch != null)
                elseBranch.solve(tabla);
        } catch (Exception e) {
            throw new RuntimeException("Error semantico: estructura de la condicional.");
        }
    }

    @Override
    public String toString() {
        String result = "if (" + condition + ") " + thenBranch;
        if (elseBranch != null) {
            result += " else " + elseBranch;
        }
        return result;
    }
}
