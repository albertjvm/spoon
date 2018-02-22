package com.albertjvm.data;

import com.albertjvm.model.ShopifyAccessToken;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ShopifyAccessTokenMapper {

    @Select("SELECT * FROM ShopifyAccessToken WHERE shopName=#{shop}")
    ShopifyAccessToken getTokenByShopName(@Param("shop") String shop);

    @Insert("INSERT INTO ShopifyAccessToken (accessToken, scope, expiresIn, shopName) VALUES (#{accessToken}, #{scope}, #{expiresIn}, #{shopName})")
    void insertShopifyToken(ShopifyAccessToken shopifyAccessToken);

    @Update("UPDATE ShopifyAccessToken SET accessToken=#{accessToken}, scope=#{scope}, expiresIn=#{expiresIn} WHERE shopName=#{shopName}")
    void updateShopifyToken(ShopifyAccessToken shopifyAccessToken);
}
