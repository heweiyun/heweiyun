package com.hwy.data.pref;

/**
 * 功能描述：XML文件数据库接口
 * 创建人：heweiyun
 * 创建时间：2016/11/2 10:51
 * 修改人：heweiyun
 * 修改时间：2016/11/2 10:51
 * 修改备注：
 */

public interface ISharedPreferences {

    void saveKeyStringValue(String key, String value);
    void saveKeyIntValue(String key, int value);
    void saveKeyLongValue(String key, long value);
    void saveKeyFloatValue(String key, float value);
    void saveKeyBooleanValue(String key, boolean value);
    void saveKeyObjectValue(String key, Object value, Class type);
    String getKeyStringValue(String key, String defValue);
    long getKeyLongValue(String key, long defValue);
    int getKeyIntValue(String key, int defValue);
    boolean getKeyBooleanValue(String key, boolean defValue);
    float getKeyFloatValue(String key, float defValue);
    Object getKeyObjectValue(String key, Class type);
    void clear();
    void remove(String key);

}
