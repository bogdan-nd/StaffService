package com.services.staff.controllers.grpc;

import com.services.grpc.server.trainer.*;
import com.services.staff.entities.Trainer;
import com.services.staff.entities.enums.SportsCategory;
import com.services.staff.services.TrainerService;
import io.grpc.stub.StreamObserver;
import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@GrpcService
@AllArgsConstructor
public class TrainerGrpcController extends TrainerServiceGrpc.TrainerServiceImplBase {
    private final TrainerService trainerService;

    @Override
    public void showTrainers(TrainerEmpty request, StreamObserver<TrainerResponse> responseObserver) {
        List<Trainer> trainers = trainerService.getAll();
        List<ProtoTrainer> protoTrainers = transformTrainerToProto(trainers);

        TrainerResponse response = TrainerResponse.newBuilder()
                .addAllTrainers(protoTrainers).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getSuitableTrainer(SportCategoryRequest request, StreamObserver<TrainerResponse> responseObserver) {
        String category = request.getSportCategory();
        SportsCategory sportsCategory = SportsCategory.valueOf(category);
        try {
            Trainer trainer = trainerService.getSuitable(sportsCategory);
            ProtoTrainer protoTrainer = trainerToProto(trainer);
            TrainerResponse response = TrainerResponse.newBuilder()
                    .addTrainers(protoTrainer).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void showTrainerById(TrainerIdRequest request, StreamObserver<TrainerResponse> responseObserver) {
        String id = request.getId();
        UUID trainerId = UUID.fromString(id);
        try {
            Trainer trainer = trainerService.getById(trainerId);
            ProtoTrainer protoTrainer = trainerToProto(trainer);

            TrainerResponse response = TrainerResponse.newBuilder()
                    .addTrainers(protoTrainer).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        catch (NotFoundException e){
            responseObserver.onError(e);
        }
    }

    @Override
    public void addTrainer(TrainerRequest request, StreamObserver<TrainerResponse> responseObserver) {
        String category = request.getSportCategory();
        SportsCategory sportsCategory = SportsCategory.valueOf(category);
        Trainer newTrainer = new Trainer(request.getName(),request.getSalary(),sportsCategory, request.getCarePrice());
        ProtoTrainer protoTrainer = trainerToProto(newTrainer);
        TrainerResponse response = TrainerResponse.newBuilder()
                .addTrainers(protoTrainer).build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void deleteTrainer(TrainerIdRequest request, StreamObserver<TrainerEmpty> responseObserver) {
        String id = request.getId();
        UUID trainersId = UUID.fromString(id);
        trainerService.deleteById(trainersId);
        responseObserver.onCompleted();
    }

    private ProtoTrainer trainerToProto(Trainer trainer){
        UUID trainerId = trainer.getId();
        String id = trainerId.toString();
        String sportCategory = trainer.getSportCategory().toString();

        ProtoTrainer protoTrainer = ProtoTrainer.newBuilder()
                .setId(id)
                .setSalary(trainer.getSalary())
                .setName(trainer.getName())
                .setSportCategory(sportCategory)
                .setTrainingPrice(trainer.getTrainingPrice())
                .build();

        return protoTrainer;
    }

    private List<ProtoTrainer> transformTrainerToProto(List<Trainer> trainers){
        List<ProtoTrainer> protoTrainers = new ArrayList<>();
        for(Trainer trainer:trainers){
            ProtoTrainer protoTrainer = trainerToProto(trainer);
            protoTrainers.add(protoTrainer);
        }
        return protoTrainers;
    }
}
