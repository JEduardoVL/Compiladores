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
                // Asumimos que tanto leftVal como rightVal son instancias de Boolean
                return Boolean.TRUE.equals(leftVal) && Boolean.TRUE.equals(rightVal);
            case OR:
                return Boolean.TRUE.equals(leftVal) || Boolean.TRUE.equals(rightVal);
            case BANG_EQUAL:
                return !isEqual(leftVal, rightVal);
            case EQUAL_EQUAL:
                return isEqual(leftVal, rightVal);
            case GREATER_EQUAL:
                return compare(leftVal, rightVal) >= 0;
            case LESS:
                return compare(leftVal, rightVal) < 0;
            case LESS_EQUAL:
                return compare(leftVal, rightVal) <= 0;
            default:
                throw new RuntimeException("Operador lógico desconocido: " + operator.getTipo());
        }
    }

    private int compare(Object a, Object b) {
        if (a instanceof Number && b instanceof Number) {
            double diff = ((Number) a).doubleValue() - ((Number) b).doubleValue();
            return (diff > 0) ? 1 : (diff < 0) ? -1 : 0;
        }
        throw new RuntimeException("Los operandos deben ser numéricos para comparación.");
    }

    private boolean isEqual(Object a, Object b) {
        if (a == null && b == null) {
            return true;
        }
        if (a == null) {
            return false;
        }
        return a.equals(b);
    }

    @Override
    public String toString() {
        return left + " " + operator.lexema + " " + right;
    }
}

