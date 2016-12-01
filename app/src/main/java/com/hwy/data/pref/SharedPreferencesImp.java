package com.hwy.data.pref;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hwy.common.util.LogUtil;


/**
 * 功能描述：配置文件数据库
 * 创建人：heweiyun
 * 创建时间：2016/11/2 10:38
 * 修改人：heweiyun
 * 修改时间：2016/11/2 10:38
 * 修改备注：
 */

public class SharedPreferencesImp implements ISharedPreferences{
    private static final String TAG = SharedPreferencesImp.class.getSimpleName();
    private SharedPreferences sharedPreferences = null;
    private SharedPreferences.Editor editor = null;
    private Gson mGson;

    public SharedPreferencesImp(Context context, String name){
        if (TextUtils.isEmpty(name)){
            throw new RuntimeException("名称不能为空");
        }
        sharedPreferences = context.getSharedPreferences(name, Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        mGson = new Gson();
    }

    public void saveKeyStringValue(String key, String value) {
        if (null != editor) {
            editor.putString(key, value);
            editor.apply();
        }
    }

    public void saveKeyIntValue(String key, int value) {
        if (null != editor) {
            editor.putInt(key, value);
            editor.apply();
        }
    }

    public void saveKeyLongValue(String key, long value) {
        if (null != editor) {
            editor.putLong(key, value);
            editor.apply();
        }
    }

    public void saveKeyFloatValue(String key, float value) {
        if (null != editor) {
            editor.putFloat(key, value);
            editor.apply();
        }
    }

    public void saveKeyBooleanValue(String key, boolean value) {
        if (null != editor) {
            editor.putBoolean(key, value);
            editor.apply();
        }
    }

    @Override
    public void saveKeyObjectValue(String key, Object value, Class type) {
        if (null != value){
            try{
                String valueStr = mGson.toJson(value,type);
                if (null != editor){
                    editor.putString(key,valueStr);
                    editor.apply();
                }
            }catch (Exception e){}
        }
    }

    public String getKeyStringValue(String key, String defValue) {
        String value = defValue;
        if (null != sharedPreferences) {
            value = sharedPreferences.getString(key, defValue);
        }
        return value;
    }

    public long getKeyLongValue(String key, long defValue) {
        long value = defValue;
        if (null != sharedPreferences) {
            value = sharedPreferences.getLong(key, defValue);
        }
        return value;
    }

    public int getKeyIntValue(String key, int defValue) {
        int value = defValue;
        if (null != sharedPreferences) {
            value = sharedPreferences.getInt(key, defValue);
        }
        return value;
    }

    public boolean getKeyBooleanValue(String key, boolean defValue) {
        boolean value = defValue;
        if (null != sharedPreferences) {
            value = sharedPreferences.getBoolean(key, defValue);
        }
        return value;
    }

    public float getKeyFloatValue(String key, float defValue) {
        float value = defValue;
        if (null != sharedPreferences) {
            value = sharedPreferences.getFloat(key, defValue);
        }
        return value;
    }

    @Override
    public Object getKeyObjectValue(String key, Class type) {
        if (null != sharedPreferences){
            String value = sharedPreferences.getString(key,"");
            if (!TextUtils.isEmpty(value)){
                try{
                    return mGson.fromJson(value,type);
                }catch (Exception e){
                    LogUtil.e(TAG,e);
                }
            }
        }
        return null;
    }

    /**
     * 清除所有数据
     *
     * @author heweiyun
     */
    public void clear() {
        if (null != editor) {
            editor.clear();
            editor.apply();
        }
    }

    /**
     * 删掉某一条数据
     *
     * @param key
     * @author heweiyun
     */
    public void remove(String key) {
        if (null != editor) {
            editor.remove(key);
            editor.apply();
        }
    }


}
