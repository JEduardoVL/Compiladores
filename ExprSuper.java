public class ExprSuper extends Expression {
    // final Token keyword;
    final Token method;

    ExprSuper(Token method) {
        // this.keyword = keyword;
        this.method = method;
    }

    @Override
    Object solve(TablaSimbolos tabla) {
        throw new UnsupportedOperationException("Unimplemented method 'solve'");
    }

   
}