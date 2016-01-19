package com.gkframework.orm.mybatis.query;

import com.gkframework.model.Operator;
import com.gkframework.model.page.Paginable;
import com.gkframework.model.utils.TypeCaseHelper;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * User: user
 * Date: 15/10/26
 * Version: 1.0
 */
abstract class BaseQuery extends HashMap<String, Object> implements Query{

    protected final static String _DEFAULT_ID ="defaultId";
    protected final static String _DEFAULT_OPERATOR ="defaultOperater";

    protected final static String _DEFAULT_IDS ="ids";
    private static final String _START_ = "start";
    private static final String _LIMIT_ = "limit";

    private Paginable page;

    /**
     * 是否相异
     */
    protected boolean distinct;

    public void setPage(Paginable page) {
        this.put(_START_,page.getStart());
        this.put(_LIMIT_,page.getPageSize());
        this.page = page;
    }

    /**
     * 获取第一个默认Pagination对象<br>
     * 为了方便存取(省去根据Key来存取和类型转换的过程)
     *
     */
    public Paginable getPage() {
        return this.page;
    }

    @Override
    public void setDefaultOperater(Operator operator) {
        this.put(_DEFAULT_OPERATOR, operator);
    }

    @Override
    public Operator getDefaultOperater() {
        return (Operator) get(_DEFAULT_OPERATOR);
    }

    @Override
    public void setDefaultId(Object id) {
        this.put(_DEFAULT_ID, id);
    }

    @Override
    public Object getDefaultId() {
        return get(_DEFAULT_ID);
    }



    @Override
    public void setDefaultIds(String ids) {
        this.put(_DEFAULT_IDS, ids);
    }

    @Override
    public String getDefaultIds() {
        return (String) get(_DEFAULT_IDS);
    }

    public String getParameter(String key, String defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return String.valueOf(value);
    }

    public double getParameter(String key, double defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Double)value;
    }

    public float getParameter(String key, float defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Float)value;
    }

    public long getParameter(String key, long defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Long)value;
    }

    public int getParameter(String key, int defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Integer)value;
    }

    public short getParameter(String key, short defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Short)value;
    }

    public byte getParameter(String key, byte defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Byte)value;
    }

    public boolean getParameter(String key, boolean defaultValue) {
        Object value = getParameter(key);
        if (value == null) {
            return defaultValue;
        }
        return (Boolean)value;
    }

    public Object getParameter(String key) {
        return get(key);
    }

    /**
     * 以BigDecimal类型返回键值
     *
     * @param key
     *            键名
     * @return BigDecimal 键值
     */
    public BigDecimal getAsBigDecimal(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "BigDecimal", null);
        if (obj != null)
            return (BigDecimal) obj;
        else
            return null;
    }

    /**
     * 以Date类型返回键值
     *
     * @param key
     *            键名
     * @return Date 键值
     */
    public Date getAsDate(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Date", "yyyy-MM-dd");
        if (obj != null)
            return (Date) obj;
        else
            return null;
    }

    /**
     * 以Integer类型返回键值
     *
     * @param key
     *            键名
     * @return Integer 键值
     */
    public Integer getAsInteger(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Integer", null);
        if (obj != null)
            return (Integer) obj;
        else
            return null;
    }

    /**
     * 以Long类型返回键值
     *
     * @param key
     *            键名
     * @return Long 键值
     */
    public Long getAsLong(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Long", null);
        if (obj != null)
            return (Long) obj;
        else
            return null;
    }

    /**
     * 以String类型返回键值
     *
     * @param key
     *            键名
     * @return String 键值
     */
    public String getAsString(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "String", null);
        if (obj != null)
            return (String) obj;
        else
            return "";
    }

    /**
     * 以Timestamp类型返回键值
     *
     * @param key
     *            键名
     * @return Timestamp 键值
     */
    public Timestamp getAsTimestamp(String key) {
        Object obj = TypeCaseHelper.convert(get(key), "Timestamp",
                "yyyy-MM-dd HH:mm:ss");
        if (obj != null)
            return (Timestamp) obj;
        else
            return null;
    }

    public void addParameters(Object... pairs) {
        if (pairs == null || pairs.length == 0) {
            return;
        }
        if (pairs.length % 2 != 0) {
            throw new IllegalArgumentException("Map pairs can not be odd number.");
        }
        Map<String, Object> map = new HashMap<String, Object>();
        int len = pairs.length / 2;
        for (int i = 0; i < len; i ++) {
            map.put((String)pairs[2 * i], pairs[2 * i + 1]);
        }
        putAll(map);
    }

    /**
     * Add parameters to a new url.
     *
     * @param parameters
     * @return A new URL
     */
    public void addParameters(Map<String, Object> parameters) {
        if (parameters == null || parameters.size() == 0) {
            return;
        }
        putAll(parameters);
    }



    @Override
    public void addParameters(String key, Object value) {
        put(key, value);
    }
}
