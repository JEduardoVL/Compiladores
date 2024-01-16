//import javax.swing.plaf.nimbus.State;
import java.util.List;

/*public class StmtBlock extends Statement{
    final List<Statement> statements;

    StmtBlock(List<Statement> statements) {
        this.statements = statements;
    }
}*/

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
}
