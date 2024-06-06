package com.bk.bedrocksdkdemo.service;

import com.bk.bedrocksdkdemo.aimodels.Claude3;
import com.bk.bedrocksdkdemo.model.ModelInput;
import com.bk.bedrocksdkdemo.repository.BaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataBrowserService {
    @Autowired
    private Claude3 claude3;

    @Autowired
    private BaseRepository baseRepository;

    public Object getResults(){
      return  baseRepository.getResults("select * from genai.credit_card_fraud limit 10");
    }

    public Object queryResults(ModelInput modelInput){
        String sqlQuery = claude3.invokeModelWithContext(modelInput.getPrompt());
        log.info("SQL Query : {}" ,sqlQuery);
        return baseRepository.getResults(sqlQuery);
    }
}
