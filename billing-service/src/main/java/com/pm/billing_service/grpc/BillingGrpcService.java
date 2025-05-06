package com.pm.billing_service.grpc;


import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@GrpcService
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {
    private static final Logger log = LoggerFactory.getLogger(BillingGrpcService.class);

    @Override
    public void createBillingAccount(
            billing.BillingRequest request,
            StreamObserver<billing.BillingResponse> responseObserver) {

        log.info("Create billing account request: {}", request.toString());

        billing.BillingResponse response = billing.BillingResponse.newBuilder()
                .setAccountId(request.getPatientId())
                .setStatus("ACTIVE")
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
