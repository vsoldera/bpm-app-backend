package com.app.springrolejwt.controller;

import com.app.springrolejwt.model.Dependency;
import com.app.springrolejwt.model.Health;
import com.app.springrolejwt.model.Role;
import com.app.springrolejwt.model.User;
import com.app.springrolejwt.model.enums.RoleEnum;
import com.app.springrolejwt.model.vo.MessageVo;
import com.app.springrolejwt.model.vo.userVos.DependentVo;
import com.app.springrolejwt.model.vo.userVos.MonitoredVo;
import com.app.springrolejwt.model.vo.userVos.ResponsibleVo;
import com.app.springrolejwt.model.vo.userVos.UserHealthVo;
import com.app.springrolejwt.repository.implementation.HealthServiceImpl;
import com.app.springrolejwt.repository.implementation.UserDetailsServiceImpl;
import com.app.springrolejwt.repository.interfaces.DependencyRepository;
import com.app.springrolejwt.repository.interfaces.HealthRepository;
import com.app.springrolejwt.repository.interfaces.RoleRepository;
import com.app.springrolejwt.repository.interfaces.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
@Log4j2
public class UserController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    HealthServiceImpl healthService;

    @Autowired
    HealthRepository healthRepository;

    @Autowired
    DependencyRepository dependencyRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/emergencyContacts")
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public Object emergencyContacts() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Optional<User> username = userRepository.findByUsername(auth.getName());

        List<Dependency> uuids = dependencyRepository.returnAllContactUuid(Objects.requireNonNull(username.orElse(null)).getUuid());
        List<User> users = new ArrayList<>();
        List<ResponsibleVo> usernames = new ArrayList<>();

        uuids.forEach(uuid -> {

            if (userRepository.existsByUuid(uuid.getUserUuid())) {
                User user = userRepository.returnAllContactUuid(uuid.getContactUuid());

                User usernameTwo = new User();
                usernameTwo.setCompleteName(user.getCompleteName());
                usernameTwo.setPhone(user.getPhone());
                usernameTwo.setUuid(user.getUuid());

                users.add(usernameTwo);
            }

        });

        users.forEach(userData -> {
            Optional<User> user = userRepository.findByUuid(userData.getUuid());

            ResponsibleVo responsibleVo = new ResponsibleVo();
            responsibleVo.setCompleteName(userData.getCompleteName());
            responsibleVo.setPhone(userData.getPhone());
            responsibleVo.setUuid(userData.getUuid());
            usernames.add(responsibleVo);
        });

        return usernames;
    }

    @PostMapping("/addContacts")
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addContacts(@RequestBody DependentVo
            dependentVo) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        AtomicReference<Boolean> added = new AtomicReference<>(false);
        final Boolean[] hasCAS = {false};
        Set<String> strUuid = dependentVo.getResponsible();

        Set<String> strGo = new HashSet<>();
        Set<String> strNotGo = new HashSet<>();

        Optional<User> username = userRepository.findByUsername(auth.getName());
        Set<Role> roles = new HashSet<>();

        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Error: Role was not found."));
        roles.add(userRole);

        Role responsibleRole = roleRepository.findByName(RoleEnum.ROLE_RESPONSIBLE)
                .orElseThrow(() -> new RuntimeException("Error: Role was not found."));
        roles.add(responsibleRole);

        strUuid.forEach(uuid -> {
            if(userRepository.existsByUuid(uuid) && !uuid.equals(Objects.requireNonNull(username.orElse(null)).getUuid())) {

                if(!dependencyRepository.existsByUserUuidAndContactUuid(username.get().getUuid(), uuid)) {
                    Dependency dependency = new Dependency();
                    dependency.setUserUuid(username.get().getUuid());
                    dependency.setContactUuid(uuid);
                    strGo.add(uuid);
                    dependencyRepository.save(dependency);

                    User user = userDetailsService.findByUuid(uuid);

                    user.setRoles(roles);
                    added.set(Boolean.TRUE);

                } else {
                    hasCAS[0] = true;

                }
            } else {
                strNotGo.add(uuid);
            }


        });

        if(added.get().equals(Boolean.TRUE)) {
            return ResponseEntity
                    .accepted()
                    .body(strGo);
        }

        if(hasCAS[0].equals(Boolean.TRUE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "O usuário já é seu responsável");
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "Os seguintes UUIDs digitados não são válidos/não existem: " + strNotGo);
    }

    @PostMapping("/status")
    @Transactional
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> returnCardiac(@RequestBody UserHealthVo userHealthVo) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> username = userRepository.findByUsername(auth.getName());

        String uuid =  Objects.requireNonNull(username.orElse(null)).getUuid();

        if(healthRepository.existsByUuid(uuid)) {
            Optional<Health> health = Optional.ofNullable(healthService.findByUuid(uuid));
            health.orElse(null).setHeartBeat(userHealthVo.getHeartBeat());
            health.orElse(null).setCardiacSteps(userHealthVo.getCardiacSteps());
            health.orElse(null).setLatitude(userHealthVo.getLatitude());
            health.orElse(null).setLongitude(userHealthVo.getLongitude());
            health.orElse(null).setStatus(userHealthVo.getStatus());
            health.orElse(null).setDate(ZonedDateTime.now());
            health.orElse(null).setHasData(true);

            healthRepository.save(health.get());

            return ResponseEntity.ok().body(new MessageVo("Dados atualizados com sucesso!"));
        } else
            if(userRepository.existsByUuid(uuid) && !healthRepository.existsByUuid(uuid)) {
                Health health = new Health();

                health.setCardiacSteps(userHealthVo.getCardiacSteps());
                health.setLatitude(userHealthVo.getLatitude());
                health.setLongitude(userHealthVo.getLongitude());
                health.setUpdated_at(ZonedDateTime.now());
                health.setStatus(userHealthVo.getStatus());

                healthRepository.save(health);

                return ResponseEntity.ok().body(new MessageVo("Dados adicionados com sucesso!"));
            }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Houve um erro! Por favor, contate o suporte");
    }

    @GetMapping("/monitored")
    @PreAuthorize("hasRole('USER') and hasRole('RESPONSIBLE')")
    public Object returnMonitored() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> username = userRepository.findByUsername(auth.getName());

        List<Dependency> uuids = dependencyRepository.returnAllUserUuid(Objects.requireNonNull(username.orElse(null)).getUuid());
        List<Health> health = new ArrayList<>();
        List<MonitoredVo> usernames = new ArrayList<>();

        uuids.forEach(uuid -> {

            if (userRepository.existsByUuid(uuid.getUserUuid()) && healthRepository.existsByUuid(uuid.getUserUuid())) {
                List<Health> healthList = healthRepository.returnAllUserUuid(uuid.getUserUuid());

                health.addAll(healthList);
            }
        });

        health.forEach(health1 -> {
            Optional<User> user = userRepository.findByUuid(health1.getUuid());

            String userName = user.get().getCompleteName();
            String phone = user.get().getPhone();
            MonitoredVo monitoredVo = new MonitoredVo();
            monitoredVo.setCompleteName(userName);
            monitoredVo.setPhone(phone);
            monitoredVo.setLatitude(health1.getLatitude());
            monitoredVo.setLongitude(health1.getLongitude());
            monitoredVo.setStatus(health1.getStatus());
            monitoredVo.setHeartBeat(health1.getHeartBeat());
            monitoredVo.setCardiacSteps(health1.getCardiacSteps());
            monitoredVo.setDate(ZonedDateTime.now());
            monitoredVo.setUuid(health1.getUuid());
            usernames.add(monitoredVo);
        });


        System.out.println(health);

        return usernames;
    }

}
