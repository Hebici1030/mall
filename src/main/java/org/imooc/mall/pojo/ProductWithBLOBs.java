package org.imooc.mall.pojo;

public class ProductWithBLOBs extends Product {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_product.sub_images
     *
     * @mbg.generated
     */
    private String subImages;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column mall_product.detail
     *
     * @mbg.generated
     */
    private String detail;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_product.sub_images
     *
     * @return the value of mall_product.sub_images
     *
     * @mbg.generated
     */
    public String getSubImages() {
        return subImages;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_product.sub_images
     *
     * @param subImages the value for mall_product.sub_images
     *
     * @mbg.generated
     */
    public void setSubImages(String subImages) {
        this.subImages = subImages == null ? null : subImages.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column mall_product.detail
     *
     * @return the value of mall_product.detail
     *
     * @mbg.generated
     */
    public String getDetail() {
        return detail;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column mall_product.detail
     *
     * @param detail the value for mall_product.detail
     *
     * @mbg.generated
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }
}