package org.imooc.mall.dao;

import org.imooc.mall.pojo.Order;

import java.util.List;

public interface OrderMapper {
    Order selectByOrderNo(Long OrderNo);
    List<Order> selectByUid (Integer uid);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_order
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_order
     *
     * @mbg.generated
     */
    int insert(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_order
     *
     * @mbg.generated
     */
    int insertSelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_order
     *
     * @mbg.generated
     */
    Order selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Order record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table mall_order
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Order record);
}