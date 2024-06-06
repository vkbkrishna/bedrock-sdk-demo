package com.bk.bedrocksdkdemo;

import com.bk.bedrocksdkdemo.aimodels.Claude;
import com.bk.bedrocksdkdemo.aimodels.Claude3;
import com.bk.bedrocksdkdemo.aimodels.ModelProvider;
import com.bk.bedrocksdkdemo.aimodels.Titan;
import com.bk.bedrocksdkdemo.model.GetFoundationModel;
import com.bk.bedrocksdkdemo.model.ModelInput;
import com.bk.bedrocksdkdemo.service.DataBrowserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;

import java.util.List;

@RestController
public class BedrockSDKDemoController {

    @Autowired
    private Titan titanmodel;

    @Autowired
    private Claude claude;

    @Autowired
    private Claude3 claude3;

    @Autowired
    private DataBrowserService service;

    @GetMapping("/ai/list/models")
    public int listModels() {
        List<FoundationModelSummary> list = ModelProvider.listFoundationModels(ModelProvider.bedrockClient());
        return list.size();
    }


    @GetMapping("/ai/foundation-models/model/{modelId}")
    public GetFoundationModel getFoundationModel(@PathVariable String modelId) {
        return ModelProvider.getFoundationModel(modelId);
    }

    @PostMapping(value = "/ai/models/invoke", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public String invokeModel(@RequestBody ModelInput modelInput){
        return titanmodel.titanText(modelInput.getPrompt());
    }

    @PostMapping(value = "/ai/models/claude2/invoke", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public String invokeClaudeModel(@RequestBody ModelInput modelInput){
        return claude.invokeModel(modelInput.getPrompt());
    }

    @PostMapping(value = "/ai/models/claude3/invoke", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public String invokeClaude3Model(@RequestBody ModelInput modelInput){
        return claude3.invokeModel(modelInput.getPrompt());
    }

    @GetMapping("/ai/data/query")
    public Object queryResults() {
        return service.getResults();
    }

    @PostMapping(value = "/ai/data/query1", consumes = MimeTypeUtils.APPLICATION_JSON_VALUE)
    public Object queryResultsWithContext(@RequestBody ModelInput modelInput) {
        return service.queryResults(modelInput);
    }
}