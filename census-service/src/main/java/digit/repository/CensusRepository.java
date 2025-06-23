package digit.repository;

import digit.repository.querybuilder.CensusQueryBuilder;
import digit.repository.rowmapper.CensusRowMapper;
import digit.web.models.Census;
import digit.web.models.CensusSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.postgresql.util.PGobject;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CensusRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CensusQueryBuilder queryBuilder;

    @Autowired
    private CensusRowMapper rowMapper;

    private PGobject toPgObject(Object data) throws SQLException {
        if (data == null) return null;
        PGobject jsonObject = new PGobject();
        jsonObject.setType("jsonb");
        jsonObject.setValue(new ObjectMapper().writeValueAsString(data));
        return jsonObject;
    }

    public void create(Census census) {
        String sql = "INSERT INTO eg_cs_census(id, tenantId, hierarchyType, boundaryCode, type, totalPopulation, populationByDemographics, effectiveFrom, effectiveTo, source, additionalDetails, createdBy, lastModifiedBy, createdTime, lastModifiedTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            Object[] args = new Object[]{
                    census.getId(),
                    census.getTenantId(),
                    census.getHierarchyType(),
                    census.getBoundaryCode(),
                    census.getType(),
                    census.getTotalPopulation(),
                    toPgObject(census.getPopulationByDemographics()),
                    census.getEffectiveFrom(),
                    census.getEffectiveTo(),
                    census.getSource(),
                    toPgObject(census.getAdditionalDetails()),
                    census.getAuditDetails() != null ? census.getAuditDetails().getCreatedBy() : null,
                    census.getAuditDetails() != null ? census.getAuditDetails().getLastModifiedBy() : null,
                    census.getAuditDetails() != null ? census.getAuditDetails().getCreatedTime() : null,
                    census.getAuditDetails() != null ? census.getAuditDetails().getLastModifiedTime() : null
            };
            jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Census census) {
        String sql = "UPDATE eg_cs_census SET totalPopulation=?, populationByDemographics=?, effectiveFrom=?, effectiveTo=?, source=?, additionalDetails=?, lastModifiedBy=?, lastModifiedTime=? WHERE id=?";
        try {
            Object[] args = new Object[]{
                    census.getTotalPopulation(),
                    toPgObject(census.getPopulationByDemographics()),
                    census.getEffectiveFrom(),
                    census.getEffectiveTo(),
                    census.getSource(),
                    toPgObject(census.getAdditionalDetails()),
                    census.getAuditDetails() != null ? census.getAuditDetails().getLastModifiedBy() : null,
                    census.getAuditDetails() != null ? census.getAuditDetails().getLastModifiedTime() : null,
                    census.getId()
            };
            jdbcTemplate.update(sql, args);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public List<Census> search(CensusSearchCriteria criteria){
        List<Object> params = new ArrayList<>();
        String query = queryBuilder.getSearchQuery(criteria, params);
        return jdbcTemplate.query(query, params.toArray(), rowMapper);
    }
}
