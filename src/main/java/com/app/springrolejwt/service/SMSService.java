package com.akhianand.springrolejwt.service;

import com.twilio.rest.api.v2010.account.Message;

public interface SMSService {
    public Message sendSms();
}
