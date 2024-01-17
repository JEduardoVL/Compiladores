import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Interprete {

    static boolean existenErrores = false;

    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            System.out.println("Uso correcto: interprete [script]");
            System.exit(64);
        } else if (args.length == 1) {
            ejecutarArchivo(args[0]);
        } else {
            ejecutarPrompt();
        }
    }

    private static void ejecutarArchivo(String path) throws Exception {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()));
        if (existenErrores) System.exit(65);
    }

    private static void ejecutarPrompt() throws Exception {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print(">>> ");
            String linea = reader.readLine();
            if (linea == null) break;
            ejecutar(linea);
            existenErrores = false;
        }
    }

    private static void ejecutar(String source) throws Exception {
        Scanner scanner = new Scanner(source);
        List<Token> tokens = scanner.scan();

        Program program = new ASDR(tokens);
        List<Statement> declaraciones = program.progra();
        if (declaraciones != null) {
            for (Statement statement : declaraciones) {
                try {
                    statement.execute();
                } catch (RuntimeException e) {
                    System.err.println("Error durante la ejecución: " + e.getMessage());
                    existenErrores = true;
                }
            }
            if (!existenErrores) {
                System.out.println("ASDR correcto y ejecución completada exitosamente.");
            }
        } else {
            System.err.println("Error durante el análisis del programa.");
        }
    }

    static void error(int linea, String posicion, String mensaje) {
        reportar(linea, posicion, mensaje);
    }

    public static void reportar(int linea, String posicion, String mensaje) {
        System.err.println(
                "[linea " + linea + "] Error " + posicion + ": " + mensaje
        );
        existenErrores = true;
    }
}

