package com.app.springrolejwt.model.vo.userVos;

import com.app.springrolejwt.model.Health;
import lombok.Data;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class MonitoredVo {

    @NotNull
    @NotBlank
    private String completeName;

    private String uuid;

    private ZonedDateTime date;

    @NotNull
    @NotBlank
    private String status;

    @NotNull
    private Integer hearthBeat;

    @NotNull
    private Float latitude;

    @NotNull
    private Float longitude;

    @NotNull
    private Integer cardiacSteps;

}
