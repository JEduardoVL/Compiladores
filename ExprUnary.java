public class ExprUnary extends Expression {
    final Token operator;
    final Expression right;

    ExprUnary(Token operator, Expression right) {
        this.operator = operator;
        this.right = right;
    }

    @Override
    Object solve(TablaSimbolos tabla){
        try{
            switch (operator.tipo) {
                case BANG:
                    if(right.solve(tabla) instanceof Boolean)
                        return !((boolean) right.solve(tabla));
                    else
                        throw new RuntimeException( "Error semantico: Operando no compatible");
                case MINUS:
                    if(right.solve(tabla) instanceof Integer)
                        return -((Integer) right.solve(tabla));
                    else if(right.solve(tabla) instanceof Float)
                        return -((Float) right.solve(tabla));
                    else if(right.solve(tabla) instanceof Double)
                        return -((Double) right.solve(tabla));
                    else 
                        throw new RuntimeException("Error semantico: Operando no compatible");
                default:
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
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
