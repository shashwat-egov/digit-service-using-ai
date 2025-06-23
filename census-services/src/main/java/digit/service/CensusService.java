package digit.service;

import digit.config.CensusConfiguration;
import digit.repository.CensusRepository;
import digit.util.IdgenUtil;
import digit.web.models.*;
import org.egov.common.contract.request.RequestInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CensusService {

    @Autowired
    private CensusRepository censusRepository;

    @Autowired
    private IdgenUtil idgenUtil;

    @Autowired
    private CensusConfiguration config;

    public List<Census> createCensus(CensusRequest censusRequest){
        Census census = censusRequest.getCensus();
        RequestInfo requestInfo = censusRequest.getRequestInfo();
        String tenantId = census.getTenantId();
        List<String> ids = idgenUtil.getIdList(requestInfo, tenantId, "census.id", null, 1);
        census.setId(ids.get(0));
        Long currentTime = System.currentTimeMillis();
        AuditDetails audit = AuditDetails.builder().createdBy(requestInfo.getUserInfo().getUuid()).createdTime(currentTime).lastModifiedBy(requestInfo.getUserInfo().getUuid()).lastModifiedTime(currentTime).build();
        census.setAuditDetails(audit);
        List<Object[]> batch = new ArrayList<>();
        batch.add(new Object[]{census.getId(), census.getTenantId(), census.getHierarchyType(), census.getBoundaryCode(), census.getType(), census.getTotalPopulation(), census.getPopulationByDemographics()==null?null:census.getPopulationByDemographics().toString(), census.getEffectiveFrom(), census.getEffectiveTo(), census.getSource(), census.getAdditionalDetails()==null?null:census.getAdditionalDetails().toString(), audit.getCreatedBy(), audit.getLastModifiedBy(), audit.getCreatedTime(), audit.getLastModifiedTime()});
        censusRepository.save(batch);
        List<Census> res = new ArrayList<>();
        res.add(census);
        return res;
    }

    public List<Census> searchCensus(CensusSearchCriteria criteria){
        return censusRepository.search(criteria);
    }

    public List<Census> updateCensus(CensusRequest censusRequest){
        Census census = censusRequest.getCensus();
        RequestInfo requestInfo = censusRequest.getRequestInfo();
        AuditDetails audit = census.getAuditDetails();
        if(audit==null){
            audit = AuditDetails.builder().build();
        }
        audit.setLastModifiedBy(requestInfo.getUserInfo().getUuid());
        audit.setLastModifiedTime(System.currentTimeMillis());
        census.setAuditDetails(audit);
        List<Object[]> batch = new ArrayList<>();
        batch.add(new Object[]{census.getId(), census.getTenantId(), census.getHierarchyType(), census.getBoundaryCode(), census.getType(), census.getTotalPopulation(), census.getPopulationByDemographics()==null?null:census.getPopulationByDemographics().toString(), census.getEffectiveFrom(), census.getEffectiveTo(), census.getSource(), census.getAdditionalDetails()==null?null:census.getAdditionalDetails().toString(), audit.getCreatedBy(), audit.getLastModifiedBy(), audit.getCreatedTime(), audit.getLastModifiedTime()});
        censusRepository.save(batch);
        List<Census> res = new ArrayList<>();
        res.add(census);
        return res;
    }
}
