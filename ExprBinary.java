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

        // Asegúrate de manejar correctamente los tipos de datos (Integer, Float, Double, etc.)
        switch (operator.tipo) {
            case PLUS:
                return sumar(leftVal, rightVal);
            case MINUS:
                return restar(leftVal, rightVal);
            case STAR:
                return multiplicar(leftVal, rightVal);
            case SLASH:
                return dividir(leftVal, rightVal);
            default:
                throw new RuntimeException("Operador desconocido: " + operator.tipo);
        }
    }

    private Object sumar(Object left, Object right) {
        if (left instanceof String || right instanceof String) {
            return left.toString() + right.toString();
        } else if (left instanceof Double || right instanceof Double) {
            return convertirADouble(left) + convertirADouble(right);
        } else if (left instanceof Float || right instanceof Float) {
            return convertirAFloat(left) + convertirAFloat(right);
        } else if (left instanceof Integer || right instanceof Integer) {
            return convertirAInteger(left) + convertirAInteger(right);
        } else {
            throw new RuntimeException("Tipos de operandos no soportados para la suma: " + left.getClass().getName() + " y " + right.getClass().getName());
        }
    }

    private Object restar(Object left, Object right) {
        if (left instanceof Double || right instanceof Double) {
            return convertirADouble(left) - convertirADouble(right);
        } else if (left instanceof Float || right instanceof Float) {
            return convertirAFloat(left) - convertirAFloat(right);
        } else if (left instanceof Integer || right instanceof Integer) {
            return convertirAInteger(left) - convertirAInteger(right);
        } else {
            throw new RuntimeException("Tipos de operandos no soportados para la resta: " + left.getClass().getName() + " y " + right.getClass().getName());
        }
    }

    private Object multiplicar(Object left, Object right) {
        if (left instanceof Double || right instanceof Double) {
            return convertirADouble(left) * convertirADouble(right);
        } else if (left instanceof Float || right instanceof Float) {
            return convertirAFloat(left) * convertirAFloat(right);
        } else if (left instanceof Integer || right instanceof Integer) {
            return convertirAInteger(left) * convertirAInteger(right);
        } else {
            throw new RuntimeException("Tipos de operandos no soportados para la multiplicación: " + left.getClass().getName() + " y " + right.getClass().getName());
        }
    }

    private Object dividir(Object left, Object right) {
        if (right instanceof Number && ((Number) right).doubleValue() == 0) {
            throw new RuntimeException("Error de división por cero");
        }

        if (left instanceof Double || right instanceof Double) {
            return convertirADouble(left) / convertirADouble(right);
        } else if (left instanceof Float || right instanceof Float) {
            return convertirAFloat(left) / convertirAFloat(right);
        } else if (left instanceof Integer || right instanceof Integer) {
            return convertirAInteger(left) / convertirAInteger(right);
        } else {
            throw new RuntimeException("Tipos de operandos no soportados para la división: " + left.getClass().getName() + " y " + right.getClass().getName());
        }
    }

    private double convertirADouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        } else {
            throw new RuntimeException("No se puede convertir a Double: " + obj.getClass().getName());
        }
    }

    private float convertirAFloat(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).floatValue();
        } else {
            throw new RuntimeException("No se puede convertir a Float: " + obj.getClass().getName());
        }
    }

    private int convertirAInteger(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).intValue();
        } else {
            throw new RuntimeException("No se puede convertir a Integer: " + obj.getClass().getName());
        }
    }

    @Override
    public String toString() {
        return left + " " +  operator.lexema + " " + right;
    }
}
