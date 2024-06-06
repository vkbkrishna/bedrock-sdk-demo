package com.bk.bedrocksdkdemo.aimodels;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

@Component
public class Titan {

    @Autowired
    private BedrockRuntimeClient bedrockRuntimeClient;

    public Titan(){

    }

    public JSONObject titan(){

        // Set the model ID, e.g., Titan Text Embeddings V2.
       // var modelId = "amazon.titan-embed-text-v1";//amazon.titan-embed-text-v1:2:8k
        var modelId = "amazon.titan-embed-text-v1";

        // The text to convert into an embedding.
        var inputText = "Please recommend books with a theme similar to the movie 'Inception'.";

        // Create a JSON payload using the model's native structure.
        var request = new JSONObject().put("inputText", inputText);

        // Encode and send the request.
        var response = bedrockRuntimeClient.invokeModel(req -> req
                .body(SdkBytes.fromUtf8String(request.toString()))
                .modelId(modelId));

        // Decode the model's native response body.
        var nativeResponse = new JSONObject(response.body().asUtf8String());

        // Extract and print the generated embedding.
        var embedding = nativeResponse.getJSONArray("embedding");
        System.out.println(embedding);

        return nativeResponse;
    }

    public String titanText(String prompt){
        var modelId = "amazon.titan-text-lite-v1";

        // The text to convert into an embedding.
        var inputText = "create a sql query to count the maximum salary form employee table";
        // Create a JSON payload using the model's native structure.
        var request = new JSONObject().put("inputText", prompt);

        // Encode and send the request.
        var response = bedrockRuntimeClient.invokeModel(req -> req
                .body(SdkBytes.fromUtf8String(request.toString()))
                .modelId(modelId));
        var nativeResponse = new JSONObject(response.body().asUtf8String());

       return  nativeResponse.getJSONArray("results").getJSONObject(0).get("outputText").toString();

    }
}
