package app.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final Logger log = LoggerFactory.getLogger(DatabaseConnection.class);
    private static final String URL = "jdbc:sqlite:db/usuarios.db";
    private static Connection connection;

    static {
        criarPastaDb();
    }

    private static void criarPastaDb() {
        try {
            java.io.File dbDir = new java.io.File("db");
            if (!dbDir.exists()) {
                dbDir.mkdirs();
                log.info("Pasta 'db' criada.");
            }
        } catch (Exception e) {
            log.error("Erro ao criar pasta 'db': " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        if (connection == null || isConnectionClosed()) {
            try {
                connection = DriverManager.getConnection(URL);
                criarTabelaSeNaoExistir();
                log.info("Conexão com SQLite estabelecida.");
            } catch (SQLException e) {
                log.error("Erro ao conectar ao banco: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    private static boolean isConnectionClosed() {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true;
        }
    }

    private static void criarTabelaSeNaoExistir() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nome TEXT NOT NULL,
                email TEXT NOT NULL UNIQUE,
                telefone TEXT,
                data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
        """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            log.info("Tabela 'usuarios' verificada/criada com sucesso.");
        } catch (SQLException e) {
            log.error("Erro ao criar tabela: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                    log.info("Conexão fechada.");
                }
            } catch (SQLException e) {
                log.error("Erro ao fechar conexão: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }
}