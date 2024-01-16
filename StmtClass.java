import java.util.List;

/*public class StmtClass extends Statement {
    final Token name;
    final ExprVariable superclass;
    final List<StmtFunction> methods;

    StmtClass(Token name, ExprVariable superclass, List<StmtFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }
}*/

public class StmtClass extends Statement {
    final Token name;
    final ExprVariable superclass;
    final List<StmtFunction> methods;

    StmtClass(Token name, ExprVariable superclass, List<StmtFunction> methods) {
        this.name = name;
        this.superclass = superclass;
        this.methods = methods;
    }

    @Override
    public void execute() {
        // Aquí implementas cómo se ejecutan las clases, por ejemplo, registrar la clase en la tabla de símbolos
    }
}

