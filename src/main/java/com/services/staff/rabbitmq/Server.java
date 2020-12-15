package com.services.staff.rabbitmq;

import com.services.staff.controllers.rest.dto.GroomDTO;
import com.services.staff.controllers.rest.dto.TrainerDTO;
import com.services.staff.controllers.rest.dto.VetDTO;
import com.services.staff.entities.Groom;
import com.services.staff.entities.Trainer;
import com.services.staff.entities.Vet;
import com.services.staff.services.GroomService;
import com.services.staff.services.TrainerService;
import com.services.staff.services.VetService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class Server {
    private final VetService vetService;
    private final GroomService groomService;
    private final TrainerService trainerService;

    @RabbitListener(queues = "${rabbitmq.vetQueue}")
    public void addClient(VetDTO vetDTO){
        Vet vet = new Vet(vetDTO.getName(),vetDTO.getSalary(),vetDTO.getConsultationPrice());
        vetService.saveVet(vet);
    }

    @RabbitListener(queues = "${rabbitmq.groomQueue}")
    public void addGroom(GroomDTO groomDTO){
        Groom groom = new Groom(groomDTO.getName(),groomDTO.getSalary(),groomDTO.getCarePrice());
        groomService.saveGroom(groom);
    }

    @RabbitListener(queues = "${rabbitmq.trainerQueue}")
    public void addTrainer(TrainerDTO trainerDTO){
        Trainer trainer = new Trainer(trainerDTO.getName(),trainerDTO.getSalary(),
                trainerDTO.getSportCategory(),trainerDTO.getTrainingPrice());

        trainerService.saveTrainer(trainer);
    }
}