/*public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        // Evalúa la condición y ejecuta el cuerpo del bucle mientras la condición sea verdadera
        while ((Boolean) condition.solve(tabla)) {
            body.solve(tabla);
        }
    }

    @Override
    public String toString() {
        return "while (" + condition + ") " + body;
    }
}*/

public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
    }

    @Override
    public String toString() {
        return "while( " + condition + " )" + body;
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
}


