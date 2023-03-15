package Shop.database.repository;

import Shop.database.mapper.CustomerRowMapper;
import Shop.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Component
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer getCustomer(int id) {
        final String sql = "SELECT * FROM customer WHERE customer.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, customerRowMapper);
        }
        catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer addCustomer(Customer customer) {
        final String sql = "INSERT INTO customer(name, surname, email, address, age, phone_number) values (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        System.out.println(keyHolder.getKey());
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getName());
                ps.setString(2, customer.getSurname());
                ps.setString(3, customer.getEmail());
                ps.setString(4, customer.getAddress());
                if (customer.getAge() != null) {
                    ps.setInt(5, customer.getAge());
                }
                else {
                    ps.setNull(5, Types.INTEGER);
                }
                ps.setString(6, customer.getPhoneNumber());
                return ps;
            }
        }, keyHolder);

        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        }
        else {
            return null;
        }
    }

    public List<Customer> getCustomers() {
        final String sql = "SELECT * FROM customer";
        return jdbcTemplate.query(sql, customerRowMapper);
    }
}
