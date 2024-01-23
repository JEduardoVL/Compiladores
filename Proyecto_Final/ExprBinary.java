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
    Object solve(TablaSimbolos tabla) {
        try {
            Object leftResult = left.solve(tabla);
            Object rightResult = right.solve(tabla);

            switch (operator.tipo) {
                case MINUS:
                    return binaryOperation(leftResult, rightResult, (a, b) -> a - b);
                case PLUS:
                    return leftResult instanceof String || rightResult instanceof String
                            ? leftResult.toString() + rightResult.toString()
                            : binaryOperation(leftResult, rightResult, (a, b) -> a + b);
                case STAR:
                    return binaryOperation(leftResult, rightResult, (a, b) -> a * b);
                case SLASH:
                    return binaryOperation(leftResult, rightResult, (a, b) -> a / b);
                default:
                    throw new RuntimeException("Error semantico: Operador no compatible");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Number binaryOperation(Object left, Object right, BinaryOperator<Double> operator) {
        if (left instanceof Number && right instanceof Number) {
            Double leftNumber = ((Number) left).doubleValue();
            Double rightNumber = ((Number) right).doubleValue();
            return operator.apply(leftNumber, rightNumber);
        } else {
            throw new RuntimeException("Operandos incompatibles");
        }
    }

    @FunctionalInterface
    interface BinaryOperator<T> {
        T apply(T a, T b);
    }

    @Override
    public String toString() {
        return left + " " + operator.lexema + " " + right;
    }
}
