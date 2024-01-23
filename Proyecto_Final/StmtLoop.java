public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        try {
            while ((boolean) condition.solve(tabla)) {
                body.solve(tabla);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error semantico: estructura del bucle");
        }
    }

    @Override
    public String toString() {
        return "loop( " + condition + " )" + body;
    }
}


