package digit.repository.rowmapper;

import digit.web.models.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Component
public class CensusRowMapper implements ResultSetExtractor<List<Census>> {
    @Override
    public List<Census> extractData(ResultSet rs) throws SQLException, DataAccessException {
        Map<String, Census> map = new LinkedHashMap<>();
        while(rs.next()){
            String id = rs.getString("id");
            Census census = map.get(id);
            if(census == null){
                AuditDetails audit = AuditDetails.builder()
                        .createdBy(rs.getString("createdBy"))
                        .createdTime(rs.getLong("createdTime"))
                        .lastModifiedBy(rs.getString("lastModifiedBy"))
                        .lastModifiedTime(rs.getLong("lastModifiedTime"))
                        .build();
                census = Census.builder()
                        .id(id)
                        .tenantId(rs.getString("tenantId"))
                        .hierarchyType(rs.getString("hierarchyType"))
                        .boundaryCode(rs.getString("boundaryCode"))
                        .type(rs.getString("type"))
                        .totalPopulation(rs.getLong("totalPopulation"))
                        .populationByDemographics(null) // population demographics not handled
                        .effectiveFrom(rs.getLong("effectiveFrom"))
                        .effectiveTo(rs.getLong("effectiveTo"))
                        .source(rs.getString("source"))
                        .additionalDetails(rs.getString("additionalDetails"))
                        .auditDetails(audit)
                        .build();
            }
            map.put(id, census);
        }
        return new ArrayList<>(map.values());
    }
}
