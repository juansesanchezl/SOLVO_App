package com.dynamodb;

import android.content.Context;

import com.amazonaws.mobileconnectors.cognito.CognitoSyncManager;
import com.amazonaws.mobileconnectors.cognito.Dataset;
import com.amazonaws.mobileconnectors.cognito.DefaultSyncCallback;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;

public class ManagerClass {

    public static AmazonDynamoDBClient dynamoDBClient = null;
    public static DynamoDBMapper dynamoDBMapper = null;

    CognitoCachingCredentialsProvider credentialsProvider = null;
    CognitoSyncManager syncManager = null;
    AmazonS3Client s3Client = null;
    TransferUtility transferUtility = null;
    final static String IDENTITY_POOL_ID = "us-east-1:a22f778a-533b-4a40-8d52-78ac17263a31";

    public CognitoCachingCredentialsProvider getCredentialsProvider(Context context) {
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context, // Context
                IDENTITY_POOL_ID, // Identity Pool ID
                Regions.US_EAST_1 // Region
        );
        syncManager = new CognitoSyncManager(context,Regions.US_EAST_1,credentialsProvider);
        Dataset dataset = syncManager.openOrCreateDataset("Mydataset");
        dataset.put("mykey","myvalue");
        dataset.synchronize(new DefaultSyncCallback());
        return credentialsProvider;

    }

    public AmazonS3Client inits3clients(Context context){
        if(credentialsProvider==null){
            getCredentialsProvider(context);
            s3Client = new AmazonS3Client(credentialsProvider);
            s3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
        }

        return s3Client;
    }

    public TransferUtility checkTransferUtility(AmazonS3Client s3Client, Context context){
        if (transferUtility == null) {
            transferUtility = new TransferUtility(s3Client,context);
            return transferUtility;
        }else{
            return transferUtility;
        }
    }

    public DynamoDBMapper initDynamoClient (CognitoCachingCredentialsProvider credentialsProvider){
        if(dynamoDBClient == null){
            dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);
            dynamoDBClient.setRegion(Region.getRegion(Regions.US_EAST_1));
            dynamoDBMapper = new DynamoDBMapper(dynamoDBClient);
        }
        return dynamoDBMapper;
    }

}
