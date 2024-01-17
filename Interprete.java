/*import java.io.BufferedReader;
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

    private static void ejecutar(String source) {
        try {
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scan();
            Program program = new ASDR(tokens);
            List<Statement> declaraciones = program.progra();
            TablaSimbolos tablaSimbolos = new TablaSimbolos(null);
            for (Statement statement : declaraciones) {
                statement.solve(tablaSimbolos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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
*/
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Interprete {

    static boolean existenErrores = false;

    public static void main(String[] args) throws IOException {
        if (args.length > 1) {
            System.out.println("Uso correcto: interprete [archivo.txt]");
            System.exit(64);
        } else if (args.length == 1) {
            ejecutarArchivo(args[0]);
        } else {
            ejecutarPrompt();
        }
    }

    private static void ejecutarArchivo(String path) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(path));
        ejecutar(new String(bytes, Charset.defaultCharset()));
        if (existenErrores) System.exit(65);
    }

    private static void ejecutarPrompt() throws IOException {
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

    private static void ejecutar(String source) {
        try {
            Scanner scanner = new Scanner(source);
            List<Token> tokens = scanner.scan();
            Program parser = new ASDR(tokens);
            List<Statement> declaraciones = parser.progra();
            TablaSimbolos tablaSimbolos = new TablaSimbolos(null);
            for (Statement statement : declaraciones) {
                statement.solve(tablaSimbolos);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
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

