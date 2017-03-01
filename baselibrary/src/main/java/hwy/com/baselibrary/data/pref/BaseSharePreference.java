package hwy.com.baselibrary.data.pref;

import android.content.Context;

/**
 * 功能描述：XML数据库文件的基类
 * 创建人：heweiyun
 * 创建时间：2016/11/2 11:03
 * 修改人：heweiyun
 * 修改时间：2016/11/2 11:03
 * 修改备注：
 */

public class BaseSharePreference implements ISharedPreferences{
    protected ISharedPreferences mSharedPreferencesDB;

    public BaseSharePreference(Context context, String name){
        mSharedPreferencesDB = new SharedPreferencesImp(context,name);
    }

    @Override
    public void saveKeyStringValue(String key, String value) {
        mSharedPreferencesDB.saveKeyStringValue(key,value);
    }

    @Override
    public void saveKeyIntValue(String key, int value) {
        mSharedPreferencesDB.saveKeyIntValue(key,value);
    }

    @Override
    public void saveKeyLongValue(String key, long value) {
        mSharedPreferencesDB.saveKeyLongValue(key,value);
    }

    @Override
    public void saveKeyFloatValue(String key, float value) {
        mSharedPreferencesDB.saveKeyFloatValue(key,value);
    }

    @Override
    public void saveKeyBooleanValue(String key, boolean value) {
        mSharedPreferencesDB.saveKeyBooleanValue(key,value);
    }

    @Override
    public void saveKeyObjectValue(String key, Object value, Class type) {
        mSharedPreferencesDB.saveKeyObjectValue(key,value,type);
    }

    @Override
    public String getKeyStringValue(String key, String defValue) {
        return mSharedPreferencesDB.getKeyStringValue(key,defValue);
    }

    @Override
    public long getKeyLongValue(String key, long defValue) {
        return mSharedPreferencesDB.getKeyLongValue(key,defValue);
    }

    @Override
    public int getKeyIntValue(String key, int defValue) {
        return mSharedPreferencesDB.getKeyIntValue(key,defValue);
    }

    @Override
    public boolean getKeyBooleanValue(String key, boolean defValue) {
        return mSharedPreferencesDB.getKeyBooleanValue(key,defValue);
    }

    @Override
    public float getKeyFloatValue(String key, float defValue) {
        return mSharedPreferencesDB.getKeyFloatValue(key,defValue);
    }

    @Override
    public Object getKeyObjectValue(String key, Class type) {
        return mSharedPreferencesDB.getKeyObjectValue(key,type);
    }

    @Override
    public void clear() {
        mSharedPreferencesDB.clear();
    }

    @Override
    public void remove(String key) {
        mSharedPreferencesDB.remove(key);
    }
}
