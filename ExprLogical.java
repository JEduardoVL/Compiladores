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
    public Object solve(TablaSimbolos tabla) {
        Object leftVal = left.solve(tabla);
        Object rightVal = right.solve(tabla);

        switch (operator.getTipo()) {
            case AND:
                return asBoolean(leftVal) && asBoolean(rightVal);
            case OR:
                return asBoolean(leftVal) || asBoolean(rightVal);
            case BANG_EQUAL:
                return !isEqual(leftVal, rightVal);
            case EQUAL_EQUAL:
                return isEqual(leftVal, rightVal);
            case GREATER:
            case GREATER_EQUAL:
            case LESS:
            case LESS_EQUAL:
                return compare(leftVal, rightVal, operator.getTipo());
            default:
                throw new RuntimeException("Operador lógico desconocido: " + operator.getTipo());
        }
    }

    private boolean asBoolean(Object value) {
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        throw new RuntimeException("Tipo de dato no booleano en expresión lógica.");
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

    private Object compare(Object a, Object b, TipoToken operatorType) {
        if (a instanceof Number && b instanceof Number) {
            double diff = ((Number) a).doubleValue() - ((Number) b).doubleValue();
            switch (operatorType) {
                case GREATER:
                    return diff > 0;
                case GREATER_EQUAL:
                    return diff >= 0;
                case LESS:
                    return diff < 0;
                case LESS_EQUAL:
                    return diff <= 0;
            }
        }
        throw new RuntimeException("Los operandos deben ser numéricos para comparación.");
    }

    @Override
    public String toString() {
        return left + " " + operator.lexema + " " + right;
    }
}
