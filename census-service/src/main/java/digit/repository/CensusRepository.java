package digit.repository;

import digit.repository.querybuilder.CensusQueryBuilder;
import digit.repository.rowmapper.CensusRowMapper;
import digit.web.models.Census;
import digit.web.models.CensusSearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
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

    public void create(Census census){
        String sql = "INSERT INTO eg_cs_census(id, tenantId, hierarchyType, boundaryCode, type, totalPopulation, populationByDemographics, effectiveFrom, effectiveTo, source, additionalDetails, createdBy, lastModifiedBy, createdTime, lastModifiedTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Object[] args = new Object[]{census.getId(), census.getTenantId(), census.getHierarchyType(), census.getBoundaryCode(), census.getType(), census.getTotalPopulation(), new com.fasterxml.jackson.databind.ObjectMapper().valueToTree(census.getPopulationByDemographics()), census.getEffectiveFrom(), census.getEffectiveTo(), census.getSource(), new com.fasterxml.jackson.databind.ObjectMapper().valueToTree(census.getAdditionalDetails()), census.getAuditDetails()!=null?census.getAuditDetails().getCreatedBy():null, census.getAuditDetails()!=null?census.getAuditDetails().getLastModifiedBy():null, census.getAuditDetails()!=null?census.getAuditDetails().getCreatedTime():null, census.getAuditDetails()!=null?census.getAuditDetails().getLastModifiedTime():null};
        jdbcTemplate.update(sql, args);
    }

    public void update(Census census){
        String sql = "UPDATE eg_cs_census SET totalPopulation=?, populationByDemographics=?, effectiveFrom=?, effectiveTo=?, source=?, additionalDetails=?, lastModifiedBy=?, lastModifiedTime=? WHERE id=?";
        Object[] args = new Object[]{census.getTotalPopulation(), new com.fasterxml.jackson.databind.ObjectMapper().valueToTree(census.getPopulationByDemographics()), census.getEffectiveFrom(), census.getEffectiveTo(), census.getSource(), new com.fasterxml.jackson.databind.ObjectMapper().valueToTree(census.getAdditionalDetails()), census.getAuditDetails()!=null?census.getAuditDetails().getLastModifiedBy():null, census.getAuditDetails()!=null?census.getAuditDetails().getLastModifiedTime():null, census.getId()};
        jdbcTemplate.update(sql, args);
    }
    public List<Census> search(CensusSearchCriteria criteria){
        List<Object> params = new ArrayList<>();
        String query = queryBuilder.getSearchQuery(criteria, params);
        return jdbcTemplate.query(query, params.toArray(), rowMapper);
    }
}
