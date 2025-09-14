package com.api.votacao.listeners;

import com.api.votacao.dtos.VotoRequest;
import com.api.votacao.services.VotoService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener responsável por consumir mensagens da fila de votos no RabbitMQ
 * e delegar o processamento ao {@link VotoService}.
 * <p>
 * Esta classe é anotada com {@link Component} para ser gerenciada pelo Spring
 * e utiliza {@link RabbitListener} para escutar a fila {@code computar-voto.queue}.
 * Sempre que uma nova mensagem do tipo {@link VotoRequest} chega à fila,
 * o método {@link #processMessage(VotoRequest)} é invocado automaticamente.
 * </p>
 *
 * @author Pierri Alexander Vidmar
 * @since 09/2025
 */
@Component
public class ComputaVotoListener {

    /**
     * Serviço responsável por processar e persistir os votos recebidos.
     */
    @Autowired
    private VotoService votoService;

    /**
     * Método listener que processa mensagens da fila {@code computar-voto.queue}.
     * <p>
     * O objeto {@link VotoRequest} recebido é delegado ao {@link VotoService}
     * para validação e persistência.
     * </p>
     *
     * @param request objeto contendo os dados do voto recebido do RabbitMQ
     */
    @RabbitListener(queues = "computar-voto.queue")
    public void processMessage(VotoRequest request) {
        votoService.save(request);
    }
}
