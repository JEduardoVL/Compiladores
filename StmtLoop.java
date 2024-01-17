/*public class StmtLoop extends Statement {
    final Expression condition;
    final Statement body;

    StmtLoop(Expression condition, Statement body) {
        this.condition = condition;
        this.body = body;
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
    public void execute() {
        // Evalúa la condición y ejecuta el cuerpo del bucle mientras la condición sea verdadera
        while ((Boolean) condition.solve()) {
            body.execute();
        }
    }

    @Override
    public String toString() {
        return "while (" + condition + ") " + body;
    }
}
