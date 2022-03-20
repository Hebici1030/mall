package org.imooc.mall.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.ToString;
import org.imooc.mall.emnu.ResponseEmnu;
import org.springframework.validation.BindingResult;

import javax.xml.ws.Response;

/*
将JSON请求打包成类
 */
@Data
//@JsonSerialize(include = )
//选择注解类不为空的进入序列化
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseVo<T> {
    private Integer status;

    private String mag;

    private T data;

    private ResponseVo(Integer status, String mag) {
        this.status = status;
        this.mag = mag;
        
    }
    public ResponseVo(Integer status,T data){
        this.status=status;
        this.data=data;
    }
    public static <T> ResponseVo<T> successByMag(String msg){
        return new ResponseVo<T>(0,msg);
    }
    public static <T> ResponseVo<T> success(T data){
        return new ResponseVo<T>(0,data);
    }
    public static <T> ResponseVo<T> success(){
        return new ResponseVo<T>(ResponseEmnu.SUCCES.getCode(),ResponseEmnu.SUCCES.getStuts());
    }
    public static <T> ResponseVo<T> error(ResponseEmnu responseEmnu){
        return new ResponseVo<T>(responseEmnu.getCode(),responseEmnu.getStuts());
    }
    public static <T> ResponseVo<T> error(ResponseEmnu responseEmnu, BindingResult bindingResult){
        return new ResponseVo<T>(responseEmnu.getCode(), bindingResult.getFieldError().getField()+bindingResult.getFieldError().getArguments());
    }
    public static <T> ResponseVo<T> error(ResponseEmnu responseEmnu,String s){
        return new ResponseVo<T>(responseEmnu.getCode(), s);
    }

}
