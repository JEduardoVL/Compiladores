/*class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }
    public Object solve() {
        return TablaSimbolos.getInstance().get(this.name.getLexema());
    }
}*/

class ExprVariable extends Expression {
    final Token name;

    ExprVariable(Token name) {
        this.name = name;
    }

    @Override
    public Object solve() {
        return TablaSimbolos.getInstance().get(name.getLexema());
    }
}
