package com.albertjvm.data;

import com.albertjvm.model.OauthState;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OauthStateMapper {

    @Select("SELECT * FROM OauthState WHERE id=#{id}")
    OauthState getOauthState(@Param("id") String id);

    @Delete("DELETE FROM OauthState WHERE id=#{id}")
    void deleteOauthState(@Param("id") String id);

    @Insert("INSERT INTO OauthState (id, user_id) VALUES (#{id}, #{userId})")
    void createOauthState(OauthState oauthState);
}
