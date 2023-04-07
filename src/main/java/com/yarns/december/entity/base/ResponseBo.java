package com.yarns.december.entity.base;

import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author Yarns
 */
@Alias("res")
public class ResponseBo extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = -8713837118340960775L;

    public ResponseBo() {
        this.put("success", true);
        this.put("timestamp", String.valueOf(System.currentTimeMillis()));
    }

    public static ResponseBo cus(Integer code, String message) {
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("message",message);
        responseBo.put("code",code);
        responseBo.put("success",false);
        return responseBo;
    }

    @Override
    public ResponseBo put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static ResponseBo ok(){
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("message","操作成功");
        responseBo.put("code",200);
        return responseBo;
    }

    public static ResponseBo result(Object rs){
        return ok().put("data",rs);
    }

    public static ResponseBo fail(){
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("message","操作失败");
        responseBo.put("code",500);
        responseBo.put("success",false);
        return responseBo;
    }
    public static ResponseBo fail(String msg){
        ResponseBo responseBo = new ResponseBo();
        responseBo.put("message",msg);
        responseBo.put("code",500);
        responseBo.put("success",false);
        return responseBo;
    }

    public String getMessage() {
        return String.valueOf(get("message"));
    }

    public Object getData() {
        return get("data");
    }

    public Integer getCode() {
        return Integer.valueOf(get("code").toString());
    }
}
