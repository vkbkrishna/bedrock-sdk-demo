package com.bk.bedrocksdkdemo.model;

public record GetFoundationModel(
        String modelArn,
        String modelId,
        String modelName,
        String providerName,
        String customizationsSupported,
        String outputModalities
) {
}
