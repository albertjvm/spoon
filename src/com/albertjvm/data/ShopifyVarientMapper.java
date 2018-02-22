package com.albertjvm.data;

import com.albertjvm.model.ShopifyVariant;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ShopifyVarientMapper {

    @Select("SELECT * FROM ShopifyVariant WHERE sku=#{sku}")
    ShopifyVariant getVariantBySku(@Param("sku") String sku);

    @Insert("INSERT INTO ShopifyVariant (id, sku) VALUES (#{id}, #{sku})")
    void insertVariant(ShopifyVariant shopifyVariant);

    @Update("UPDATE ShopifyVariant SET sku=#{sku} WHERE id=#{id}")
    void updateVariant(ShopifyVariant shopifyVariant);
}
