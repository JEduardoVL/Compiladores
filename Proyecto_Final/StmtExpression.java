public class StmtExpression extends Statement {
    final Expression expression;

    StmtExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    void solve(TablaSimbolos tabla){
        expression.solve(tabla);
    }

    @Override
    public String toString(){
        return expression.toString();
    }
}
