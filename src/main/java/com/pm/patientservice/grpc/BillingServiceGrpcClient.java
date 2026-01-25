package com.pm.patientservice.grpc;


import billing.BillingRequest;
import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service  //not grpc service but grpc client
public class BillingServiceGrpcClient {
//    it is a nested class within billing service grpc class that provides blocking (synchronous) stub methods for making grpc calls
//    in other words , anytime it makes a grpc call to billing service it will wait for the response before moving to the next line of code
    private final BillingServiceGrpc.BillingServiceBlockingStub blockingStub;


//    we will pass parameter that will be injected by spring framework , and will be environment
//    variables point to where billing service is running
    public BillingServiceGrpcClient(@Value("${billing.service.address:localhost}") String serverAddress ,@Value("${billing.service.port:9001}") int serverPort) {
//        we adding environment variables to docker container , and passing billing service address and port
//        when we tested the billing service we used
//        localhost:9002/BillingService/CreateBillingAccount this is for local testing
//        but later in production env it will be like this aws.grpc:12343/BillingService/CreateBillingAccount


        log.info("Connecting to Billing Service at {}:{}", serverAddress, serverPort);
//      initialize our blocking stub to use  in other parts of our code
        ManagedChannel channel =
                ManagedChannelBuilder.forAddress(serverAddress, serverPort).usePlaintext().build();
        blockingStub = BillingServiceGrpc.newBlockingStub(channel);

//        now when we start our app , spring will register this grpc client as a service and
//        going to run all of this code and start client behind the scenes
    }

    public BillingResponse createBillingAccount(String patientId, String name , String email) {
//        these all the parameters that grpc billing request is expecting
        BillingRequest request =
                BillingRequest.newBuilder().setPatientId(patientId).setName(name).setEmail(email).build();

        BillingResponse response = blockingStub.createBillingAccount(request);
        log.info("Received Response from billing service via GRPC: {}", response);
        return  response;
    }

//    so now we add a call to this method inside patient service when we create a new patient
}
