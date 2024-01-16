/*public class ExprAssign extends Expression{
    final Token name;
    final Expression value;

    ExprAssign(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }
}*/

public class ExprAssign extends Expression {
    final Token name;
    final Expression value;

    ExprAssign(Token name, Expression value) {
        this.name = name;
        this.value = value;
    }

    @Override
public Object solve() {
    Object solvedValue = value.solve();
    TablaSimbolos.getInstance().declare(name.getLexema(), solvedValue);
    return solvedValue;
}
}

