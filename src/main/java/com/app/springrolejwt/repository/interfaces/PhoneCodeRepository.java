package com.app.springrolejwt.repository.interfaces;

import com.app.springrolejwt.model.PhoneCode;
import com.app.springrolejwt.model.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneCodeRepository extends JpaRepository<PhoneCode, Integer> {
    PhoneCode findByPhoneAndCode(@Param("phone") String phone, @Param("code") String code);
}
