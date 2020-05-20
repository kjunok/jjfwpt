package com.jjkj.support.file.excel;

import java.util.List;

/**
 * @author WangJun
 * @since 2019年4月4日 下午3:02:01
 */
public interface RowReader {
    /**
     * 业务逻辑实现方法
     *
     * @param sheetIndex
     * @param curRow
     * @param rowlist
     */
    void invoke(int sheetIndex, int curRow, List<String> rowlist);
}
