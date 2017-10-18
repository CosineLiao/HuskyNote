package husky.note.huskynote.utils;

import java.io.IOException;
import java.util.IllegalFormatCodePointException;

import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by Administrator on 2017/10/18.
 * 用于将Call封装成Observable
 */

public class RetrofitUtils
{
    /**
     * 将Retrofit.Call对象封装成
     *
     * @param call
     * @param <T>
     * @return
     */
    public static <T> Observable<T> create(final Call<T> call)
    {
        return Observable.unsafeCreate(new Observable.OnSubscribe<T>()
        {
            @Override
            public void call(Subscriber<? super T> subscriber)
            {
                if (!subscriber.isUnsubscribed())
                {
                    try
                    {
                        Response<T> response = call.execute();
                        if (response.isSuccessful())
                        {
                            subscriber.onNext(response.body());
                            subscriber.onCompleted();
                        }
                        else
                        {
                            subscriber.onError(new IllegalStateException("code=" + response.body()));
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                        throw new IllegalStateException(e);
                    }
                }
            }
        });
    }

}
