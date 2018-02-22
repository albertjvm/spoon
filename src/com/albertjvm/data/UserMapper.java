package com.albertjvm.data;

import com.albertjvm.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    @Select("SELECT * FROM USER WHERE id=#{id}")
    User getUser(@Param("id") int id);

    @Select("SELECT * FROM USER WHERE email=#{email}")
    User getUserByEmail(@Param("email") String email);

    @Select("SELECT * FROM USER WHERE merchantId=#{merchantId}")
    User getUserByMerchantId(@Param("merchantId") String merchantId);

    @Insert("INSERT INTO User (email, password, firstName, lastName, merchantId, shopName) VALUES (#{email}, #{password}, #{firstName}, #{lastName}, #{merchantId}, #{shopName})")
    void createUser(User user);

    @Update("UPDATE User SET merchantId=#{merchantId}, shopName=#{shopName} WHERE id=#{id}")
    void updateUser(User user);
}
