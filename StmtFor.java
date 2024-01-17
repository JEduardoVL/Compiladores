public class StmtFor extends Statement {
    final Statement initializer;
    final Expression condition;
    final Expression increment;
    final Statement body;

    StmtFor(Statement initializer, Expression condition, Expression increment, Statement body) {
        this.initializer = initializer;
        this.condition = condition;
        this.increment = increment;
        this.body = body;
    }

    @Override
    void solve(TablaSimbolos tabla) {
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

  
}