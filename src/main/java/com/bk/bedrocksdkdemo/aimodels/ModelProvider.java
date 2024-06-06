package com.bk.bedrocksdkdemo.aimodels;

import com.bk.bedrocksdkdemo.model.GetFoundationModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.auth.credentials.ProfileCredentialsProvider;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.*;
import software.amazon.awssdk.services.bedrockruntime.BedrockRuntimeClient;

import java.util.List;

@Slf4j
public class ModelProvider {

   private static final BedrockClient client = bedrockClient();

    /**
     * Lists Amazon Bedrock foundation models that you can use.
     * You can filter the results with the request parameters.
     *
     * @param bedrockClient The service client for accessing Amazon Bedrock.
     * @return A list of objects containing the foundation models' details
     */
    public static List<FoundationModelSummary> listFoundationModels(BedrockClient bedrockClient) {

        try {
            ListFoundationModelsResponse response = bedrockClient.listFoundationModels(r -> {});

            List<FoundationModelSummary> models = response.modelSummaries();

            if (models.isEmpty()) {
                System.out.println("No available foundation models in ");
            } else {
                for (FoundationModelSummary model : models) {

                    System.out.println("Model ID: " + model.modelId());
                    System.out.println("Provider: " + model.providerName());
                    System.out.println("Name:     " + model.modelName());
                    System.out.println();
                }
            }

            return models;

        } catch (SdkClientException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }


    public static BedrockRuntimeClient client(){

        return  BedrockRuntimeClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(ProfileCredentialsProvider.create())
                .build();
    }

    public static BedrockClient bedrockClient(){
        return BedrockClient.builder().region(Region.US_EAST_1).credentialsProvider(ProfileCredentialsProvider.create()).build();
    }

    public static GetFoundationModel getFoundationModel(String modelId){
        try {

            GetFoundationModelRequest request = GetFoundationModelRequest.builder()
                    .modelIdentifier(modelId)
                    .build();
            GetFoundationModelResponse response = client.getFoundationModel(request);

            FoundationModelDetails model = response.modelDetails();

            return new GetFoundationModel(
                    model.modelArn(),
                    model.modelId(),
                    model.modelName(),
                    model.providerName(),
                    String.join(", ", model.customizationsSupportedAsStrings()),
                    String.join(", ", model.outputModalitiesAsStrings())
            );

        } catch (AccessDeniedException e) {

            log.error("Access Denied: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        } catch (Exception e) {
            log.error("Exception: %s".formatted(e.getMessage()));
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
