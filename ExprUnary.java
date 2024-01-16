/*public class ExprUnary extends Expression{
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }
}*/

public class ExprUnary extends Expression {
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    public Object solve() {
        Object rightVal = right.solve();

        switch (operator.getTipo()) {
            case MINUS:
                if (rightVal instanceof Double) {
                    return -(Double) rightVal;
                }
                break;
            case BANG:
                return !Boolean.TRUE.equals(rightVal);
            default:
                throw new RuntimeException("Operador unario desconocido: " + operator.getTipo());
        }

        throw new RuntimeException("Tipo inválido para operador unario: " + rightVal.getClass().getSimpleName());
    }
}
