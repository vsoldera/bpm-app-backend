package com.app.springrolejwt.model.vo.userVos;

import lombok.Data;

import java.util.List;

@Data
public class DependentResponsibleVo {

    String dependent;
    String uuid;
    String phone;
    List<ResponsibleVo> responsibles;

}
