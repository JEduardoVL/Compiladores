/*class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    public Object solve() {
        return this.value;
    }
}
*/

class ExprLiteral extends Expression {
    final Object value;

    ExprLiteral(Object value) {
        this.value = value;
    }

    @Override
    public Object solve() {
        return value;
    }
}
