package com.albertjvm.data;

import com.albertjvm.model.SquareAccessToken;
import org.apache.ibatis.annotations.*;

@Mapper
public interface SquareAccessTokenMapper {

    @Select("SELECT * FROM SquareAccessToken WHERE merchantId = #{merchantId}")
    SquareAccessToken getTokenByMerchantId(@Param("merchantId") String merchantId);

    @Insert("INSERT INTO SquareAccessToken (accessToken, merchantId, tokenType, expiresAt) VALUES (#{accessToken}, #{merchantId}, #{tokenType}, #{expiresAt})")
    void insertSquareToken(SquareAccessToken squareAccessToken);

    @Update("UPDATE SquareAccessToken SET accessToken=#{accessToken}, tokenType=#{tokenType}, expiresAt=#{expiresAt} WHERE merchantId=#{merchantId}")
    void updateSquareToken(SquareAccessToken squareAccessToken);
}
