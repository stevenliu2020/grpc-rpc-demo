package com.steven.grpcdemo.server.impl;

import com.steven.grpcdemo.contract.dto.*;
import com.steven.grpcdemo.contract.service.DemoServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by steven on 2017/1/17.
 */
public class DemoServiceImpl extends DemoServiceGrpc.DemoServiceImplBase {

    @Override
    public void ping(PingRequest pingRequest, StreamObserver<PingResponse> streamObserver) {
        PingResponse reply = PingResponse.newBuilder().setOut("pong => " + pingRequest.getIn()).build();
        streamObserver.onNext(reply);
        streamObserver.onCompleted();
    }
    @Override
    public void getPersonList(QueryParameter queryParameter, StreamObserver<PersonList> streamObserver) {
        //System.out.println(queryParameter.getAgeStart() + "-" + queryParameter.getAgeEnd());
        PersonList.Builder personListBuilder = PersonList.newBuilder();
        Person.Builder builder = Person.newBuilder();
        List<Person> list = new ArrayList<Person>();
        for (short i = 0; i < 10; i++) {
            list.add(builder.setAge(i).setChildrenCount(i).setName("test" + i).setSex(true).build());
        }
        personListBuilder.addAllItems(list);
        streamObserver.onNext(personListBuilder.build());
        streamObserver.onCompleted();
    }
}
