package com.bk.bedrocksdkdemo.utils;

import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClient;
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest;
import com.amazonaws.services.secretsmanager.model.GetSecretValueResult;

public class SecretUtils {

    private static final String _secretName = "";

    public static String getSecret() {
        AWSSecretsManager client = AWSSecretsManagerClient.builder()
                .build();

        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest();
        getSecretValueRequest.setSecretId(_secretName);

        GetSecretValueResult getSecretValueResult;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (Exception e) {
            throw e;
        }

        String secret = getSecretValueResult.getSecretString();
        return secret;
    }
}
