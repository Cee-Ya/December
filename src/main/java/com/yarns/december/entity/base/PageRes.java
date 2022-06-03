package com.yarns.december.entity.base;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Yarns
 * @date 2022/6/3
 */
@SuppressWarnings("unchecked")
@Data
@AllArgsConstructor
public class PageRes<T> {
    /**
     * 总数
     */
    private Integer total;
    /**
     * 结果
     */
    private List<T> rows;

    public static PageRes init(IPage<?> page){
        return new PageRes(Math.toIntExact(page.getTotal()),page.getRecords());
    }
}
