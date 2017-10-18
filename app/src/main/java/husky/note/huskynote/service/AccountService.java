package husky.note.huskynote.service;

import husky.note.huskynote.network.ApiProvider;
import husky.note.huskynote.utils.RetrofitUtils;
import rx.Observable;

/**
 * Created by Administrator on 2017/10/18.
 */

public class AccountService
{
    public static Observable<String> login(String email, String password)
    {
        return RetrofitUtils.create(ApiProvider.getInstance().getAuthApi().login(email, password));
    }
}
