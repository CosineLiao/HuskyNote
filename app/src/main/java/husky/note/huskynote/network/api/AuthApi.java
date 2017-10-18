package husky.note.huskynote.network.api;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/10/18.
 */

public interface AuthApi
{
    @GET("auth/login")
    Call<String> login(@Query("email") String email, @Query("password") String password);

    @GET("auth/logout")
    Call<String> logout(@Query("token") String token);

    @GET("auth/register")
    Call<String> register(@Query("email") String email, @Query("password") String password);
}
