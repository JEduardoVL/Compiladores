//import javax.swing.plaf.nimbus.State;
import java.util.List;

/*public class StmtBlock extends Statement{
    final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }
}*/

//import java.util.List;

public class StmtBlock extends Statement {
    final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }

    @Override
    void execute() {
        for (Statement statement : statements) {
            statement.execute();
        }
    }

    @Override
    public String toString() {
        StringBuilder stmt = new StringBuilder("{\n");
        for (Statement statement : statements) {
            stmt.append(statement.toString()).append("\n");
        }
        stmt.append("}\n");
        return stmt.toString();
    }
}
