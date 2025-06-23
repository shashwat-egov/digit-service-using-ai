package digit.repository.querybuilder;

import digit.web.models.CensusSearchCriteria;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Component
public class CensusQueryBuilder {
    private static final String BASE_QUERY = "SELECT * FROM eg_census";

    public String getSearchQuery(CensusSearchCriteria criteria, List<Object> preparedStmtList){
        StringBuilder query = new StringBuilder(BASE_QUERY);

        if(!ObjectUtils.isEmpty(criteria.getTenantId())){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" tenantId = ?");
            preparedStmtList.add(criteria.getTenantId());
        }

        if(!CollectionUtils.isEmpty(criteria.getAreaCodes())){
            addClauseIfRequired(query, preparedStmtList);
            query.append(" boundaryCode IN (").append(createPlaceHolders(criteria.getAreaCodes().size())).append(")");
            preparedStmtList.addAll(criteria.getAreaCodes());
        }

        return query.toString();
    }

    private void addClauseIfRequired(StringBuilder query, List<Object> params){
        if(params.isEmpty())
            query.append(" WHERE");
        else
            query.append(" AND");
    }

    private String createPlaceHolders(int count){
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<count;i++){
            sb.append("?");
            if(i != count-1) sb.append(",");
        }
        return sb.toString();
    }
}
