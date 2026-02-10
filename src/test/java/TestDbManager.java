import com.dashapp.diabeticsystem.models.DbManager;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDbManager extends DbManager {

    private static final String URL = "jdbc:h2:mem:testdb;MODE=MySQL";

    public TestDbManager() throws SQLException { super(URL, "sa", "", DriverManager.getConnection(URL, "sa", "")); initSchema(); }

    private void initSchema() {
        try (Connection conn = DriverManager.getConnection(URL, "sa", "")) {

            InputStream is = getClass().getClassLoader().getResourceAsStream("ddl.sql");

            if (is == null) {
                throw new RuntimeException("File ddl.sql NON TROVATO");
            }

            String schema = new String(is.readAllBytes());

            for (String stmt : schema.split(";")) {
                if (!stmt.trim().isEmpty()) {
                    conn.createStatement().execute(stmt);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Errore caricando ddl.sql", e);
        }
    }
}
