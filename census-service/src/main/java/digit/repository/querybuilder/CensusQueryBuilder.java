package digit.repository.querybuilder;

import digit.web.models.CensusSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import java.util.List;

@Component
public class CensusQueryBuilder {
    private static final String BASE_QUERY = "SELECT * FROM eg_cs_census";

    public String getSearchQuery(CensusSearchCriteria criteria, List<Object> params){
        StringBuilder query = new StringBuilder(BASE_QUERY);
        boolean addWhere = true;
        if(!ObjectUtils.isEmpty(criteria.getTenantId())){
            query.append(addWhere?" WHERE":" AND").append(" tenantId = ?");
            params.add(criteria.getTenantId());
            addWhere = false;
        }
        if(!CollectionUtils.isEmpty(criteria.getAreaCodes())){
            query.append(addWhere?" WHERE":" AND").append(" boundaryCode IN (")
                .append(createPlaceholders(criteria.getAreaCodes().size())).append(")");
            params.addAll(criteria.getAreaCodes());
        }
        query.append(" ORDER BY createdTime DESC");
        return query.toString();
    }

    private String createPlaceholders(int count){
        return String.join(",", java.util.Collections.nCopies(count, "?"));
    }
}
