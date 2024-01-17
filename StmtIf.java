/*public class StmtIf extends Statement {
    final Expression condition;
    final Statement thenBranch;
    final Statement elseBranch;

    StmtIf(Expression condition, Statement thenBranch, Statement elseBranch) {
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
}*/

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
    public void execute() {
        // Evalúa la condición
        boolean conditionValue = (Boolean) condition.solve();

        // Ejecuta la rama correspondiente según el resultado de la condición
        if (conditionValue) {
            thenBranch.execute();
        } else if (elseBranch != null) {
            elseBranch.execute();
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

