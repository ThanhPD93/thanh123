package mockProject.team3.Vaccination_20.utils;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InjectionScheduleIdGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor session, Object obj) {
        String prefix = "IS";  // Set the prefix to "IS" for InjectionSchedule
        final String[] lastId = {null};

        // Use session.doWork() to obtain the JDBC connection
        session.doWork(connection -> {
            String query = "SELECT injection_schedule_id FROM injection_schedule ORDER BY injection_schedule_id DESC LIMIT 1";
            try (Statement statement = connection.createStatement()) {
                ResultSet rs = statement.executeQuery(query);
                if (rs.next()) {
                    lastId[0] = rs.getString(1);  // Retrieve the last ID
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error generating ID", e);
            }
        });

        if (lastId[0] != null) {
            int id = Integer.parseInt(lastId[0].substring(2)); // Extract numeric part from the last ID
            return prefix + String.format("%04d", id + 1);  // Increment the ID and format it
        } else {
            return prefix + "0001";  // Return the initial ID value if none exists
        }
    }
}
