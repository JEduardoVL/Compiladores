public class ExprSet extends Expression{
    final Expression object;
    final Token name;
    final Expression value;

    ExprSet(Expression object, Token name, Expression value) {
        this.object = object;
        this.name = name;
        this.value = value;
    }

    @Override
    Object solve(TablaSimbolos tabla) {
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

}
