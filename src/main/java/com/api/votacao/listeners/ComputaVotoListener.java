package com.api.votacao.listeners;

import com.api.votacao.dtos.VotoRequest;
import com.api.votacao.services.VotoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComputaVotoListener {

    @Autowired
    private VotoService votoService;

    @RabbitListener(queues = "computar-voto.queue")
    public void processMessage(VotoRequest request) {
        votoService.save(request);
    }
}
