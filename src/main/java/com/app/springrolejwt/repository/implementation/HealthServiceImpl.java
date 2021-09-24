package com.app.springrolejwt.repository.implementation;

import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.repository.interfaces.HealthRepository;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class HealthServiceImpl {

    @Autowired
    private HealthRepository healthRepository;

    public Health findByUuid(String uuid) {

        Health health =  healthRepository.findByUuid(uuid).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "O UUID é inexistente. Por favor, contate o suporte!"));
        return health;
    }

}
