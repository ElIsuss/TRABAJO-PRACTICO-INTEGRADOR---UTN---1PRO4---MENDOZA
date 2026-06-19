package integrado.prog2.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnectionPool {

    private static HikariDataSource dataSource;

    static {
        try {
            
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mysql://localhost:3306/food_store_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            config.setUsername("root");
            config.setPassword("1234567");
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");

            
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            config.setConnectionTimeout(30000); 
            config.setIdleTimeout(600000);      
            config.setPoolName("MiPoolHikari");

            
            dataSource = new HikariDataSource(config);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al inicializar el pool de conexiones HikariCP", e);
        }
    }

    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}