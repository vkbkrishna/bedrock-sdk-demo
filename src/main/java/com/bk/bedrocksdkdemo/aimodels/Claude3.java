package com.bk.bedrocksdkdemo.aimodels;

import com.bk.bedrocksdkdemo.config.Contexts;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.List;

@Component
@Slf4j
public class Claude3 {

    private static final String CLAUDE_3_SONNET = "anthropic.claude-3-sonnet-20240229-v1:0";

    @Autowired
    private BedrockRuntimeClient client;

    public String invokeModel(String prompt) {
        String claudeModelId = CLAUDE_3_SONNET;

        String payload = new JSONObject()
                .put("anthropic_version", "bedrock-2023-05-31")
                .put("max_tokens", 512)
                .put("temperature", 0.5)
                .put("top_p", 0.9)
                .put("messages", List.of(new JSONObject()
                        .put("role", "user")
                        .put("content", List.of(new JSONObject()
                                .put("type", "text")
                                .put("text", prompt)))))
                .toString();

       // log.info("input object *** : \n" + payload);


        InvokeModelRequest request = InvokeModelRequest.builder()
                .body(SdkBytes.fromUtf8String(payload))
                .modelId(claudeModelId)
                .contentType("application/json")
                .accept("application/json")
                .build();

        InvokeModelResponse response = client.invokeModel(request);
        JSONObject responseBody = new JSONObject(response.body().asUtf8String());
        JSONObject content = (JSONObject) responseBody.getJSONArray("content").get(0);
        String generatedText = content.getString("text");

        return generatedText;
    }

    public String invokeModelWithContext(String query){
       StringBuilder prompt = new StringBuilder();
        prompt.append("<context>").append(Contexts.DATABASE_CONTEXT).append("</context>")
               .append("<query>").append(query).append("</query>");
        return invokeModel(prompt.toString());
    }
}
