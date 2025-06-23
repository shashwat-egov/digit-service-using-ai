package digit.repository.rowmapper;

import digit.web.models.Census;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class CensusRowMapper implements RowMapper<Census> {
    private final ObjectMapper mapper = new ObjectMapper();
    @Override
    public Census mapRow(ResultSet rs, int rowNum) throws SQLException {
        Census census = new Census();
        census.setId(rs.getString("id"));
        census.setTenantId(rs.getString("tenantId"));
        census.setHierarchyType(rs.getString("hierarchyType"));
        census.setBoundaryCode(rs.getString("boundaryCode"));
        census.setType(rs.getString("type"));
        census.setTotalPopulation(rs.getLong("totalPopulation"));
        census.setEffectiveFrom((Long)rs.getObject("effectiveFrom"));
        census.setEffectiveTo((Long)rs.getObject("effectiveTo"));
        census.setSource(rs.getString("source"));
        try {
            String json = rs.getString("populationByDemographics");
            if(json!=null) census.setPopulationByDemographics(mapper.readValue(json, java.util.List.class));
            String ad = rs.getString("additionalDetails");
            if(ad!=null) census.setAdditionalDetails(mapper.readValue(ad, Object.class));
        }catch(Exception e){
            // ignore parse errors
        }
        return census;
    }
}
