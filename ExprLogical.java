/*public class ExprLogical extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}*/

public class ExprLogical extends Expression {
    final Expression left;
    final Token operator;
    final Expression right;

    ExprLogical(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object solve() {
        Object leftVal = left.solve();
        Object rightVal = right.solve();

        switch (operator.getTipo()) {
            case AND:
                return Boolean.TRUE.equals(leftVal) && Boolean.TRUE.equals(rightVal);
            case OR:
                return Boolean.TRUE.equals(leftVal) || Boolean.TRUE.equals(rightVal);
            default:
                throw new RuntimeException("Operador lógico desconocido: " + operator.getTipo());
        }
    }
}



