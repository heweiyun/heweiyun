package com.hwy.data.net.client.core;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 功能描述：
 * 创建人：heweiyun
 * 创建时间：2016/11/30 19:28
 * 修改人：heweiyun
 * 修改时间：2016/11/30 19:28
 * 修改备注：
 */

public class StringConvertterFactory extends Converter.Factory {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    public static StringConvertterFactory create(){
        return new StringConvertterFactory();
    }

    private StringConvertterFactory(){}

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new Converter<ResponseBody, String>() {
            @Override
            public String convert(ResponseBody value) throws IOException {
                return value.string();
            }
        };
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[]
            methodAnnotations, Retrofit retrofit) {
        return new Converter<String, RequestBody>() {
            @Override
            public RequestBody convert(String value) throws IOException {
                Buffer buffer = new Buffer();
                Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
                writer.write(value);
                writer.close();
                return RequestBody.create(MEDIA_TYPE, buffer.readByteString());
            }
        };
    }

}
