package com.pm.patientservice.kafka;

import com.pm.patientservice.model.Patient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import patient.events.PatientEvent;

@Slf4j
@Service // it is a service
public class KafkaProducer {
//    this class will be responsible for sending events to Kafka topic

//    now we need kafka template
    private final KafkaTemplate<String, byte[]> kafkaTemplate;
//    our messages that we send to kafka topic from this producer will going to be in key value
//    pairs

//    we have to inject kafka template via constructor

    public KafkaProducer(KafkaTemplate<String, byte[]> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
//    we added config file to make it work , we can not pass string byte key value pairs directly

//    now we need first  kafka event to send to topic , we will use protobuf format
//     again protobuf format let us define properties and generate code based on protofile and
//     then use it here , done

    public void sendEvent(Patient patient){
        PatientEvent event =  PatientEvent.newBuilder()
                .setPatientId(patient.getId().toString())
                .setName(patient.getName())
                .setEmail(patient.getEmail())
                .setEventType("PATIENT_CREATED")
                .build(); // now we send the event to kafka topic using kafka template

        try {

            kafkaTemplate.send("patient" , event.toByteArray());
            log.info("Event sent to kafka topic: " + event);
        }catch (Exception e){
            log.error("Error while sending PatientCreated event : {}" , event);


        }

//         we will use this now in patient service when a patient is created like we did with grpc

//        now we need to add some configuration to patient-service  to make this interact with
//        kafka broker from kafka producer here , these config will be in app prop

//        then we need to tell the patient-service the address of kafka broker server so kafka
//        producer can connect to it , it is in docker compose file



    }



}
