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
                // Negación numérica
                if (rightVal instanceof Number) {
                    return -convertToDouble(rightVal);
                }
                break;
            case BANG:
                // Negación lógica
                return !Boolean.TRUE.equals(rightVal);
            default:
                throw new RuntimeException("Operador unario desconocido: " + operator.getTipo());
        }

        throw new RuntimeException("Tipo inválido para operador unario: " + rightVal.getClass().getSimpleName());
    }

    private double convertToDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        throw new RuntimeException("No se puede convertir a Double: " + obj.getClass().getName());
    }

    @Override
    public String toString() {
        return operator.lexema + right;
    }
}

