package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.model.PhoneCode;
import com.app.springrolejwt.repository.interfaces.PhoneCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhoneCodeServiceImpl {

    @Autowired
    private PhoneCodeRepository mapper;

    public PhoneCode findPhoneCode(String phone, String code) {
        return mapper.findByPhoneAndCode(phone, code);
    }
}
