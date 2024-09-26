package mockProject.team3.Vaccination_20.utils.IdGenerator;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String prefix = "CU";
        final String[] lastId = {null};

        // Use session.doWork() to obtain the JDBC connection
        session.doWork(connection -> {
            String query = "SELECT customer_id FROM customer ORDER BY customer_id DESC LIMIT 1";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    lastId[0] = rs.getString(1);
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error generating ID", e);
            }
        });

        if (lastId[0] != null) {
            int id = Integer.parseInt(lastId[0].substring(2)); // Extract numeric part
            return prefix + String.format("%04d", id + 1);  // Increment and format
        } else {
            return prefix + "0001";  // Initial value
        }
    }
}
