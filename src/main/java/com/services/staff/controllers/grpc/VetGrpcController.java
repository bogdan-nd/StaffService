package com.services.staff.controllers.grpc;

import com.services.grpc.server.vet.*;
import com.services.staff.entities.Vet;
import com.services.staff.services.VetService;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class VetGrpcController extends VetServiceGrpc.VetServiceImplBase {
    private final VetService vetService;

    @Override
    public void showVets(VetEmpty request, StreamObserver<VetResponse> responseObserver) {
        List<Vet> vets = vetService.getAll();
        List<ProtoVet> protoVets = transformVetsToProto(vets);

        VetResponse response = VetResponse.newBuilder()
                .addAllVets(protoVets).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void showVetById(VetIdRequest request, StreamObserver<VetResponse> responseObserver) {
        String id = request.getId();
        UUID vetId = UUID.fromString(id);
        try {
            Vet vet = vetService.getById(vetId);
            ProtoVet protoVet = vetToProto(vet);

            VetResponse response = VetResponse.newBuilder()
                    .addVets(protoVet).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void addVet(VetRequest request, StreamObserver<VetResponse> responseObserver) {
        Vet newVet = new Vet(request.getName(),request.getSalary(),request.getCarePrice());
        ProtoVet protoVet = vetToProto(newVet);
        VetResponse response = VetResponse.newBuilder()
                .addVets(protoVet).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteVet(VetIdRequest request, StreamObserver<VetEmpty> responseObserver) {
        String id = request.getId();
        UUID vetId = UUID.fromString(id);
        vetService.deleteById(vetId);
        responseObserver.onCompleted();
    }

    private ProtoVet vetToProto(Vet vet){
        UUID vetId = vet.getId();
        String id = vetId.toString();

        ProtoVet protoVet = ProtoVet.newBuilder()
                .setId(id)
                .setConsultationPrice(vet.getConsultationPrice())
                .setSalary(vet.getSalary())
                .setName(vet.getName())
                .setRecoveredHorsesNumber(vet.getRecoveredHorsesNumber())
                .build();

        return protoVet;
    }

    private List<ProtoVet> transformVetsToProto(List<Vet> vets){
        List<ProtoVet> protoVets = new ArrayList<>();
        for(Vet vet:vets){
            ProtoVet protoVet = vetToProto(vet);
            protoVets.add(protoVet);
        }
        return protoVets;
    }
}
