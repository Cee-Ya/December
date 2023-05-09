package com.yarns.december.support.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Yarns
 */
public abstract class AspectSupport {


    private ObjectMapper objectMapper;

    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected Method resolveMethod(ProceedingJoinPoint point) {
        MethodSignature signature = (MethodSignature)point.getSignature();
        Class<?> targetClass = point.getTarget().getClass();

        Method method = getDeclaredMethod(targetClass, signature.getName(),
                signature.getMethod().getParameterTypes());
        if (method == null) {
            throw new IllegalStateException("无法解析目标方法: " + signature.getMethod().getName());
        }
        return method;
    }

    private Method getDeclaredMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            return clazz.getDeclaredMethod(name, parameterTypes);
        } catch (NoSuchMethodException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getDeclaredMethod(superClass, name, parameterTypes);
            }
        }
        return null;
    }
    @SuppressWarnings("rawtypes")
    public boolean isFilterObject(final Object o) {
        Class<?> clazz = o.getClass();
        if (clazz.isArray()) {
            return clazz.getComponentType().isAssignableFrom(MultipartFile.class);
        }
        else if (Collection.class.isAssignableFrom(clazz)) {
            Collection collection = (Collection) o;
            for (Object value : collection) {
                return value instanceof MultipartFile;
            }
        }
        else if (Map.class.isAssignableFrom(clazz)) {
            Map map = (Map) o;
            for (Object value : map.entrySet()) {
                Map.Entry entry = (Map.Entry) value;
                return entry.getValue() instanceof MultipartFile;
            }
        }
        return o instanceof MultipartFile || o instanceof HttpServletRequest || o instanceof HttpServletResponse;
    }


    @SuppressWarnings("all")
    protected StringBuilder handleJsonParams(StringBuilder params, Object[] args, String[] paramNames) throws JsonProcessingException {
        if (null != args) {
            params.append("[");
            StringBuilder sb1 = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                if(isFilterObject(args[i])){
                    continue;
                }
                if (args[i] instanceof HttpServletResponse) {
                    if (i == 0) {
                        sb1.append("{").append("\"").append(paramNames[i]).append("\"").append(":").append("response流").append("}");
                    }else {
                        sb1.append(",{").append("\"").append(paramNames[i]).append("\"").append(":").append("response流").append("}");
                    }
                }else {
                    if (i == 0) {
                        sb1.append("{").append("\"").append(paramNames[i]).append("\"").append(":").append(objectMapper.writeValueAsString(args[i])).append("}");
                    } else {
                        sb1.append(",{").append("\"").append(paramNames[i]).append("\"").append(":").append(objectMapper.writeValueAsString(args[i])).append("}");
                    }
                }
            }
            params.append(sb1).append("]");
        }
        return params;
    }

    @SuppressWarnings("all")
    protected StringBuilder handleParams(StringBuilder params, Object[] args, List paramNames) {
        try {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof Map) {
                    Set set = ((Map) args[i]).keySet();
                    List<Object> list = new ArrayList<>();
                    List<Object> paramList = new ArrayList<>();
                    for (Object key : set) {
                        list.add(((Map) args[i]).get(key));
                        paramList.add(key);
                    }
                    return handleParams(params, list.toArray(), paramList);
                } else {
                    if (args[i] instanceof Serializable) {
                        Class<?> aClass = args[i].getClass();
                        try {
                            aClass.getDeclaredMethod("toString", new Class[]{null});
                            // 如果不抛出 NoSuchMethodException 异常则存在 toString 方法 ，安全的 writeValueAsString ，否则 走 Object的 toString方法
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i]));
                        } catch (NoSuchMethodException e) {
                            params.append(" ").append(paramNames.get(i)).append(": ").append(objectMapper.writeValueAsString(args[i].toString()));
                        }
                    } else if (args[i] instanceof MultipartFile) {
                        MultipartFile file = (MultipartFile) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(file.getName());
                    } else if (args[i] instanceof HttpServletRequest) {
                        HttpServletRequest request = (HttpServletRequest) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append(request.getMethod());
                    } else if (args[i] instanceof HttpServletResponse) {
                        HttpServletResponse response = (HttpServletResponse) args[i];
                        params.append(" ").append(paramNames.get(i)).append(": ").append("response流");
                    } else {
                        params.append(" ").append(paramNames.get(i)).append(": ").append(args[i]);
                    }
                }
            }
        } catch (Exception ignore) {
            params.append("参数解析失败");
        }
        return params;
    }
}
