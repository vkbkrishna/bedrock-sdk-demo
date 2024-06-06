package com.bk.bedrocksdkdemo.config;

public class Contexts {

   public static final String DATABASE_CONTEXT = """
            The genai database has 1 table
            1. genai.credit_card_fraud with following columns:
               DOMAIN varchar(50) NULL,
            	state varchar(50) NULL,
            	zipcode int4 NULL,
            	time1 int4 NULL,
            	time2 int4 NULL,
            	vis1 int4 NULL,
            	vis2 int4 NULL,
            	xrn1 int4 NULL,
            	xrn2 int4 NULL,
            	xrn3 int4 NULL,
            	xrn4 int4 NULL,
            	xrn5 int4 NULL,
            	var1 int4 NULL,
            	var2 int4 NULL,
            	var3 float4 NULL,
            	var4 int4 NULL,
            	var5 int4 NULL,
            	trn_amt float4 NULL,
            	total_trn_amt float4 NULL,
            	trn_type varchar(50) NULL
            	
            	Use the column DOMAIN with double quotes in sql command as "DOMAIN"
            	generate only the sql command with no explanations text.
            """;
}
