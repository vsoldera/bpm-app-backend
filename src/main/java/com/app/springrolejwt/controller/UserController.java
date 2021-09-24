package com.app.springrolejwt.controller;

import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.model.vo.MessageVo;
import com.app.springrolejwt.model.vo.userVos.UserHealthVo;
import com.app.springrolejwt.repository.implementation.HealthServiceImpl;
import com.app.springrolejwt.repository.implementation.UserDetailsServiceImpl;
import com.app.springrolejwt.repository.interfaces.HealthRepository;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    HealthServiceImpl healthService;

    @Autowired
    HealthRepository healthRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/cardiac")
    @Transactional
    @PreAuthorize("hasRole('USER') or hasRole('RESPONSIBLE')")
    public ResponseEntity<?> returnCardiac(@RequestParam String uuid, @RequestBody UserHealthVo userHealthVo) {

        if(healthRepository.existsByUuid(uuid)) {
            Optional<Health> health = Optional.ofNullable(healthService.findByUuid(uuid));
            health.get().setCardiacSteps(userHealthVo.getCardiacSteps());
            health.get().setDate(ZonedDateTime.now());
            health.get().setLatitute(userHealthVo.getLatitute());
            health.get().setLongitude(userHealthVo.getLongitude());
            health.get().setStatus(userHealthVo.getStatus());

            healthRepository.save(health.get());

            return ResponseEntity.ok().body(new MessageVo("Dados atualizados com sucesso!"));
        } else
            if(userRepository.existsByUuid(uuid)) {
                Optional<User> user = Optional.ofNullable(userDetailsService.findByUuid(uuid));
                Health health = new Health();

                health.setUuid(user.get().getUuid());
                health.setCardiacSteps(userHealthVo.getCardiacSteps());
                health.setDate(ZonedDateTime.now());
                health.setLatitute(userHealthVo.getLatitute());
                health.setLongitude(userHealthVo.getLongitude());
                health.setStatus(userHealthVo.getStatus());

                healthRepository.save(health);

                return ResponseEntity.ok().body(new MessageVo("Dados adicionados com sucesso!"));
            }

        return ResponseEntity.badRequest().body(new MessageVo("Houve um erro! Por favor, contate o suporte"));
    }

}
