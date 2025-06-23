package digit.repository;

import digit.repository.querybuilder.CensusQueryBuilder;
import digit.repository.rowmapper.CensusRowMapper;
import digit.web.models.Census;
import digit.web.models.CensusSearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class CensusRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CensusQueryBuilder queryBuilder;

    @Autowired
    private CensusRowMapper rowMapper;

    public List<Census> search(CensusSearchCriteria criteria){
        List<Object> preparedStmtList = new ArrayList<>();
        String query = queryBuilder.getSearchQuery(criteria, preparedStmtList);
        log.info("Census search query: {}", query);
        return jdbcTemplate.query(query, preparedStmtList.toArray(), rowMapper);
    }

    public void save(List<Object[]> batchArgs){
        String sql = "INSERT INTO eg_census(id, tenantId, hierarchyType, boundaryCode, type, totalPopulation, populationByDemographics, effectiveFrom, effectiveTo, source, additionalDetails, createdBy, lastModifiedBy, createdTime, lastModifiedTime) VALUES (?, ?, ?, ?, ?, ?, ?::jsonb, ?, ?, ?, ?::jsonb, ?, ?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, batchArgs);
    }
}
