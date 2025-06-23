package digit.service;

import digit.config.Configuration;
import digit.kafka.Producer;
import digit.repository.CensusRepository;
import digit.util.IdgenUtil;
import digit.web.models.Census;
import digit.web.models.CensusRequest;
import digit.web.models.CensusSearchCriteria;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CensusService {
    @Autowired
    private CensusRepository censusRepository;

    @Autowired
    private Producer producer;

    @Autowired
    private Configuration config;

    @Autowired
    private IdgenUtil idgenUtil;

    public List<Census> create(CensusRequest request){
        request.getCensus().forEach(c -> {
            List<String> ids = idgenUtil.getIdList(request.getRequestInfo(), c.getTenantId(), "census.id", "", 1);
            c.setId(ids.get(0));
        });
        if(Boolean.TRUE.equals(config.getKafkaEnabled())){
            producer.push("save-census", request);
            return request.getCensus();
        } else {
            for(Census c : request.getCensus())
                censusRepository.create(c);
            return request.getCensus();
        }
    }

    public List<Census> search(CensusSearchCriteria criteria){
        List<Census> censusList = censusRepository.search(criteria);
        if(CollectionUtils.isEmpty(censusList))
            return new ArrayList<>();
        return censusList;
    }

    public List<Census> update(CensusRequest request){
        if(Boolean.TRUE.equals(config.getKafkaEnabled())){
            producer.push("update-census", request);
            return request.getCensus();
        } else {
            for(Census c : request.getCensus())
                censusRepository.update(c);
            return request.getCensus();
        }
    }

}
