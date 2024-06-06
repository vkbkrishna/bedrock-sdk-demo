package com.bk.bedrocksdkdemo.aimodels;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelRequest;
import software.amazon.awssdk.services.bedrockruntime.model.InvokeModelResponse;

import java.util.List;

@Service
public class Claude {
    private static final String CLAUDE_3_SONNET = "anthropic.claude-3-sonnet-20240229-v1:0";
    private static final String CLAUDE_2 = "anthropic.claude-v2";

    @Autowired
    private BedrockRuntimeClient client;

    public String invokeModel(String prompt) {
        String claudeModelId = CLAUDE_2;

        // Claude requires you to enclose the prompt as follows:
        String enclosedPrompt = "Human: " + prompt + "\n\nAssistant:";

        String payload = new JSONObject()
                .put("anthropic_version", "bedrock-2023-05-31")
                .put("prompt", enclosedPrompt)
                .put("max_tokens_to_sample", 200)
                .put("temperature", 0.5)
                .put("stop_sequences", List.of("\n\nHuman:"))
                .toString();

        InvokeModelRequest request = InvokeModelRequest.builder()
                .body(SdkBytes.fromUtf8String(payload))
                .modelId(claudeModelId)
                .contentType("application/json")
                .accept("application/json")
                .build();

        InvokeModelResponse response = client.invokeModel(request);
        JSONObject responseBody = new JSONObject(response.body().asUtf8String());
        String generatedText = responseBody.getString("completion");

        return generatedText;
    }
}
