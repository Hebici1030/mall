package org.imooc.mall.pojo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class OrderItme {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.id
     *
     * @mbg.generated
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.user_id
     *
     * @mbg.generated
     */
    private Integer userId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.order_no
     *
     * @mbg.generated
     */
    private Long orderNo;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.product_id
     *
     * @mbg.generated
     */
    private Integer productId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.product_name
     *
     * @mbg.generated
     */
    private String productName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.product_image
     *
     * @mbg.generated
     */
    private String productImage;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.current_unit_price
     *
     * @mbg.generated
     */
    private BigDecimal currentUnitPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.quantity
     *
     * @mbg.generated
     */
    private Integer quantity;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.total_price
     *
     * @mbg.generated
     */
    private BigDecimal totalPrice;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.create_time
     *
     * @mbg.generated
     */
    private Date createTime;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_order_itme.update_time
     *
     * @mbg.generated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.id
     *
     * @return the value of mall_order_itme.id
     *
     * @mbg.generated
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.id
     *
     * @param id the value for mall_order_itme.id
     *
     * @mbg.generated
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.user_id
     *
     * @return the value of mall_order_itme.user_id
     *
     * @mbg.generated
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.user_id
     *
     * @param userId the value for mall_order_itme.user_id
     *
     * @mbg.generated
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.order_no
     *
     * @return the value of mall_order_itme.order_no
     *
     * @mbg.generated
     */
    public Long getOrderNo() {
        return orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.order_no
     *
     * @param orderNo the value for mall_order_itme.order_no
     *
     * @mbg.generated
     */
    public void setOrderNo(Long orderNo) {
        this.orderNo = orderNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.product_id
     *
     * @return the value of mall_order_itme.product_id
     *
     * @mbg.generated
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.product_id
     *
     * @param productId the value for mall_order_itme.product_id
     *
     * @mbg.generated
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.product_name
     *
     * @return the value of mall_order_itme.product_name
     *
     * @mbg.generated
     */
    public String getProductName() {
        return productName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.product_name
     *
     * @param productName the value for mall_order_itme.product_name
     *
     * @mbg.generated
     */
    public void setProductName(String productName) {
        this.productName = productName == null ? null : productName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.product_image
     *
     * @return the value of mall_order_itme.product_image
     *
     * @mbg.generated
     */
    public String getProductImage() {
        return productImage;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.product_image
     *
     * @param productImage the value for mall_order_itme.product_image
     *
     * @mbg.generated
     */
    public void setProductImage(String productImage) {
        this.productImage = productImage == null ? null : productImage.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.current_unit_price
     *
     * @return the value of mall_order_itme.current_unit_price
     *
     * @mbg.generated
     */
    public BigDecimal getCurrentUnitPrice() {
        return currentUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.current_unit_price
     *
     * @param currentUnitPrice the value for mall_order_itme.current_unit_price
     *
     * @mbg.generated
     */
    public void setCurrentUnitPrice(BigDecimal currentUnitPrice) {
        this.currentUnitPrice = currentUnitPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.quantity
     *
     * @return the value of mall_order_itme.quantity
     *
     * @mbg.generated
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.quantity
     *
     * @param quantity the value for mall_order_itme.quantity
     *
     * @mbg.generated
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.total_price
     *
     * @return the value of mall_order_itme.total_price
     *
     * @mbg.generated
     */
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.total_price
     *
     * @param totalPrice the value for mall_order_itme.total_price
     *
     * @mbg.generated
     */
    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.create_time
     *
     * @return the value of mall_order_itme.create_time
     *
     * @mbg.generated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.create_time
     *
     * @param createTime the value for mall_order_itme.create_time
     *
     * @mbg.generated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_order_itme.update_time
     *
     * @return the value of mall_order_itme.update_time
     *
     * @mbg.generated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_order_itme.update_time
     *
     * @param updateTime the value for mall_order_itme.update_time
     *
     * @mbg.generated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}