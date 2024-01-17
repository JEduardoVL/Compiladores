import java.util.Objects;

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
    Object solve(TablaSimbolos tabla) {
        switch (operator.tipo) {
            case OR:
                return evaluateLogical(left, right, tabla, (a, b) -> a || b);
            case AND:
                return evaluateLogical(left, right, tabla, (a, b) -> a && b);
            case BANG_EQUAL:
                return !evaluateEquality(left, right, tabla);
            case EQUAL_EQUAL:
                return evaluateEquality(left, right, tabla);
            case GREATER:
                return compare(left, right, tabla, (a, b) -> a > b);
            case GREATER_EQUAL:
                return compare(left, right, tabla, (a, b) -> a >= b);
            case LESS:
                return compare(left, right, tabla, (a, b) -> a < b);
            case LESS_EQUAL:
                return compare(left, right, tabla, (a, b) -> a <= b);
            default:
                throw new RuntimeException("Error semantico: Operador desconocido.");
        }
    }

    private boolean evaluateLogical(Expression left, Expression right, TablaSimbolos tabla,
                                    LogicalOperation operation) {
        return operation.apply((Boolean) left.solve(tabla), (Boolean) right.solve(tabla));
    }

    private boolean evaluateEquality(Expression left, Expression right, TablaSimbolos tabla) {
        Object leftResult = left.solve(tabla);
        Object rightResult = right.solve(tabla);
        return Objects.equals(leftResult, rightResult);
    }

    private boolean compare(Expression left, Expression right, TablaSimbolos tabla, 
                            ComparisonOperation operation) {
        Object leftResult = left.solve(tabla);
        Object rightResult = right.solve(tabla);
        if (leftResult instanceof Number && rightResult instanceof Number) {
            Double leftNumber = ((Number) leftResult).doubleValue();
            Double rightNumber = ((Number) rightResult).doubleValue();
            return operation.apply(leftNumber, rightNumber);
        } else {
            throw new RuntimeException("Error semantico: Operandos incompatibles.");
        }
    }

    @FunctionalInterface
    interface LogicalOperation {
        boolean apply(boolean a, boolean b);
    }

    @FunctionalInterface
    interface ComparisonOperation {
        boolean apply(double a, double b);
    }

    @Override
    public String toString() {
        return left + " " + operator.lexema + " " + right;
    }
}
