package com.xneshi.billingservice.grpc;

import billing.BillingResponse;
import billing.BillingServiceGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
@Slf4j
public class BillingGrpcService extends BillingServiceGrpc.BillingServiceImplBase {

  @Override
  public void createBillingAccount(billing.BillingRequest billingRequest,
     StreamObserver<billing.BillingResponse> responseObserver) {
    log.info("GRPC Server: createBillingAccount called with request {}", billingRequest.toString());

    BillingResponse response = BillingResponse.newBuilder()
        .setAccountId("12345")
        .setStatus("ACTIVE")
        .build();

    responseObserver.onNext(response);
    responseObserver.onCompleted();
  }
}
