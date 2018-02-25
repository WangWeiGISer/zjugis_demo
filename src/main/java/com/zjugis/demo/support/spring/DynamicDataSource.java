package com.zjugis.demo.support.spring;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @ClassName: DynamicDataSource
 * @Description: ${TODO}(用一句话描述该文件)
 * @Author: Unclemiki <a href='mailto:unclemiki@163.com'>UncleMiki</a>
 * @Version V1.0
 * Date           17/2/16  下午5:20
 **/
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * Determine the current lookup key. This will typically be
     * implemented to check a thread-bound transaction context.
     * <p>Allows for arbitrary keys. The returned key needs
     * to match the stored lookup key type, as resolved by the
     * {@link #resolveSpecifiedLookupKey} method.
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataBaseContextHolder.getDataBaseType();
    }
}
