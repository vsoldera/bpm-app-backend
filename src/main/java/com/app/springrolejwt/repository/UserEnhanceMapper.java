package com.app.springrolejwt.repository;

import com.app.springrolejwt.model.User;
import org.apache.ibatis.annotations.Param;

public interface UserEnhanceMapper {
    User findByLoginName(@Param("loginName") String loginName);


    User findByPhone(@Param("phone") String phone);
}
