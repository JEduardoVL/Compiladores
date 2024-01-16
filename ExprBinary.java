/*public class ExprBinary extends Expression{
    final Expression left;
    final Token operator;
    final Expression right;

    ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

}*/

public class ExprBinary extends Expression {
    final Expression left;
    final Token operator;
    final Expression right;

    ExprBinary(Expression left, Token operator, Expression right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object solve() {
        Object leftVal = left.solve();
        Object rightVal = right.solve();

        switch (operator.getTipo()) {
            case PLUS:
                // Asumiendo que ambos son números
                return (Double)leftVal + (Double)rightVal;
            case MINUS:
                return (Double)leftVal - (Double)rightVal;
            // Añadir casos para otros operadores...
            default:
                throw new RuntimeException("Operador desconocido: " + operator.getTipo());
        }
    }
}
