package learn.field_agent.data;

import learn.field_agent.data.mappers.SecurityClearanceMapper;
import learn.field_agent.models.SecurityClearance;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class SecurityClearanceJdbcTemplateRepository implements SecurityClearanceRepository {

    private final JdbcTemplate jdbcTemplate;

    public SecurityClearanceJdbcTemplateRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SecurityClearance> findAll() {

        final String sql = "select security_clearance_id, `name` security_clearance_name "
                + "from security_clearance limit 1000;";
        return jdbcTemplate.query(sql, new SecurityClearanceMapper());

    }

    @Override
    @Transactional
    public SecurityClearance findById(int securityClearanceId) {

        final String sql = "select security_clearance_id, `name` security_clearance_name "
                + "from security_clearance "
                + "where security_clearance_id = ?;";

        return jdbcTemplate.query(sql, new SecurityClearanceMapper(), securityClearanceId)
                .stream()
                .findFirst().orElse(null);
    }

    @Override
    public SecurityClearance add(SecurityClearance securityClearance) {
        final String sql = "insert into security_clearance (`name`) " +
                "values (?);";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        int rowsAffected = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1,securityClearance.getName());
            return ps;
        } ,keyHolder);

        if (rowsAffected <= 0) {
            return null;
        }

        securityClearance.setSecurityClearanceId(keyHolder.getKey().intValue());
        return securityClearance;

    }


    @Override
    public boolean update(SecurityClearance securityClearance) {
        final String sql = "update security_clearance set " +
                "`name` = ? " +
                "where security_clearance_id = ?;";
        return jdbcTemplate.update(sql,
                securityClearance.getName(),
                securityClearance.getSecurityClearanceId()) > 0;
    }

    @Override
    @Transactional
    public boolean deleteById(int securityClearanceId) {

        return jdbcTemplate.update("delete from security_clearance " +
                "where security_clearance_id = ? " +
                "and security_clearance_id not in " +
                "(select distinct security_clearance_id from agency_agent);",
                securityClearanceId) > 0;

        // this blocks any deletion from agency_agent where a security clearance @ securityClearanceId exists in the data layer
            // seemed safer to me, but comment out lines 84-85 to test service layer
    }

    @Override
    public int countClearancesInAgencyAgent(int securityClearanceId) {

        return jdbcTemplate.queryForObject(
                "select count(*) from agency_agent " +
                        "where security_clearance_id = ?;", Integer.class, securityClearanceId);

        // long yellow line cannot be ideal, but not sure how to make this work otherwise
    }

}
