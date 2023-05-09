package com.yarns.december.support.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.lionsoul.ip2region.xdb.Searcher;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author Yarns
 * @Date 19:50
 * @Version 1.0
 **/
@Slf4j
public class AddressUtils {
    public static String getCityInfo(String ip) {
        String dbPath = Objects.requireNonNull(AddressUtils.class.getResource("/ip2region/ip2region.xdb")).getPath();
        // 1、从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用。
        byte[] vIndex;
        try {
            vIndex = Searcher.loadVectorIndexFromFile(dbPath);
        } catch (Exception e) {
            log.error("failed to load vector index from `{}`: {}", dbPath, e);
            return StringUtils.EMPTY;
        }

        // 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
        Searcher searcher;
        try {
            searcher = Searcher.newWithVectorIndex(dbPath, vIndex);
        } catch (Exception e) {
            log.error("failed to create vectorIndex cached searcher with `{}`: {}", dbPath, e);
            return StringUtils.EMPTY;
        }

        // 3、查询
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            log.info("region: {}, ioCount: {}, took: {} μs", region, searcher.getIOCount(), cost);
            return region;
        } catch (Exception e) {
            log.error("failed to search({}): {}", ip, e);
        }
        return StringUtils.EMPTY;
    }

}
