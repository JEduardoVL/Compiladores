public class ExprGet extends Expression{
    final Expression object;
    final Token name;

    ExprGet(Expression object, Token name) {
        this.object = object;
        this.name = name;
    }

    @Override
    Object solve(TablaSimbolos tabla) {
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

    
}
