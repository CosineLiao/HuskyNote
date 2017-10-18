package husky.note.huskynote.network;

import java.io.IOException;

import husky.note.huskynote.BuildConfig;
import husky.note.huskynote.network.api.AuthApi;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/10/18.
 */

public class ApiProvider
{
    private Retrofit mRetrofit;

    public static class SingleTonHolder{
        public static final ApiProvider INSTANCE = new ApiProvider();
    }

    private ApiProvider()
    {}

    public static ApiProvider getInstance()
    {

        if (SingleTonHolder.INSTANCE.mRetrofit == null)
        {
            SingleTonHolder.INSTANCE.init("https://leanote.com");
            return null;
        }

        return SingleTonHolder.INSTANCE;
    }

    public void init(String host)
    {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addNetworkInterceptor(new Interceptor()
                {
                    @Override
                    public Response intercept(Chain chain) throws IOException
                    {
                        Request request = chain.request();
                        HttpUrl url = request.url();
                        String path = url.encodedPath();

                        HttpUrl newUrl = url;
                        Request newRequest = request.newBuilder().url(newUrl).build();
                        return chain.proceed(newRequest);
                    }
                });

        OkHttpClient client = builder.build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(host + "/api/")
                .client(client)
                .build();
    }

    public AuthApi getAuthApi()
    {
        return mRetrofit.create(AuthApi.class);
    }
}
