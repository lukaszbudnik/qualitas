package com.google.code.qualitas.integration.amqp;

import java.util.Date;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.qualitas.engines.api.core.ProcessType;
import com.google.code.qualitas.integration.api.InstallationService;
import com.google.code.qualitas.integration.api.InstallationException;

/**
 * The Class InstallationServiceImpl.
 */
@Service
public class InstallationServiceImpl implements InstallationService {

    /** The amqp template. */
    @Autowired
    private AmqpTemplate amqpTemplate;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.google.code.qualitas.integration.api.InstallationService#install(
     * byte[], java.lang.String,
     * com.google.code.qualitas.engines.api.core.ProcessType)
     */
    public void install(byte[] bundle, String contentType, ProcessType processType)
            throws InstallationException {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(contentType);
        messageProperties.setContentLength(bundle.length);
        messageProperties.setTimestamp(new Date());
        messageProperties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        messageProperties.getHeaders().put("QualitasProcessType", processType);
        Message message = new Message(bundle, messageProperties);

        amqpTemplate.send(message);
    }

}
