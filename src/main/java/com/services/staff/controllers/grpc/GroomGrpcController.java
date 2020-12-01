package com.services.staff.controllers.grpc;

import com.services.grpc.server.groom.*;
import com.services.grpc.server.vet.ProtoVet;
import com.services.grpc.server.vet.VetResponse;
import com.services.staff.entities.Groom;
import com.services.staff.entities.Vet;
import com.services.staff.services.GroomService;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class GroomGrpcController extends GroomServiceGrpc.GroomServiceImplBase {
    private final GroomService groomService;

    @Override
    public void showGrooms(GroomEmpty request, StreamObserver<GroomResponse> responseObserver) {
        List<Groom> grooms = groomService.getAll();
        List<ProtoGroom> protoGrooms = transformGroomsToProto(grooms);

        GroomResponse response = GroomResponse.newBuilder()
                .addAllGrooms(protoGrooms).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void showGroomById(IdRequest request, StreamObserver<GroomResponse> responseObserver) {
        String id = request.getId();
        UUID groomId = UUID.fromString(id);
        try {
            Groom groom = groomService.getById(groomId);
            ProtoGroom protoGroom = groomToProto(groom);

            GroomResponse response = GroomResponse.newBuilder()
                    .addGrooms(protoGroom).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void addGroom(GroomRequest request, StreamObserver<GroomResponse> responseObserver) {
        Groom newGroom = new Groom(request.getName(),request.getSalary(),request.getCarePrice());
        ProtoGroom protoGroom = groomToProto(newGroom);
        GroomResponse response = GroomResponse.newBuilder()
                .addGrooms(protoGroom).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteGroom(IdRequest request, StreamObserver<GroomEmpty> responseObserver) {
        String id = request.getId();
        UUID groomId = UUID.fromString(id);
        groomService.deleteById(groomId);
        responseObserver.onCompleted();
    }

    private ProtoGroom groomToProto(Groom groom){
        UUID groomId = groom.getId();
        String id = groomId.toString();

        ProtoGroom protoGroom = ProtoGroom.newBuilder()
                .setId(id)
                .setSalary(groom.getSalary())
                .setName(groom.getName())
                .setCarePrice(groom.getCarePrice())
                .setHorseFedNumber(groom.getHorseFedNumber())
                .build();

        return protoGroom;
    }

    private List<ProtoGroom> transformGroomsToProto(List<Groom> grooms){
        List<ProtoGroom> protoGrooms = new ArrayList<>();
        for(Groom groom:grooms){
            ProtoGroom protoGroom = groomToProto(groom);
            protoGrooms.add(protoGroom);
        }
        return protoGrooms;
    }
}
