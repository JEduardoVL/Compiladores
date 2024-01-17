class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value; 
    }

    @Override
    Object solve(TablaSimbolos tabla) {
        return this.value;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
