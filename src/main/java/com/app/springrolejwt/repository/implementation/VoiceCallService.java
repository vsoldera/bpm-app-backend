package com.app.springrolejwt.repository.implementation;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Call;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.twiml.VoiceResponse;
import com.twilio.twiml.voice.Say;
import com.twilio.type.PhoneNumber;
import com.twilio.type.Twiml;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Log4j2

public class VoiceCallService {

    public static final String ACCOUNT_SID = "AC78158ea3f2947a31df767af743539a25";
    public static final String AUTH_TOKEN = "5cb1a761c0c85bbe600f4814bd262c71";

    @Transactional
    @Async
    public Call sendVoiceCall(String responsiblePhoneNumber, String completeName) throws InterruptedException {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);


        String helloTwiml = new VoiceResponse.Builder()
                .say(new Say.Builder("Falamos do aplicativo de monitoramento BP. Houve algum problema com " + completeName + "Por favor, verifique se está tudo bem.")
                        .voice(Say.Voice.ALICE).language(Say.Language.PT_BR).build())
                .build().toXml();
        log.info("Aoba to aqui");
        return Call.creator(
                        new PhoneNumber(responsiblePhoneNumber),
                        new PhoneNumber("+13152104249"),
                        new Twiml(helloTwiml))
                .create();



    }

}
