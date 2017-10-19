package husky.note.huskynote.model;

import com.google.gson.annotations.SerializedName;
import com.raizlabs.android.dbflow.annotation.Column;

/**
 * Created by Administrator on 2017/10/18.
 */

public class Authentication extends BaseResponse
{
    @SerializedName("UserId")
    String userId = "";

    @SerializedName("Username")
    String userName = "";

    @Column(name = "email")
    @SerializedName("Email")
    String email = "";

    @SerializedName("Token")
    String acessToken = "";

    public String getUserId()
    {
        return userId;
    }

    public String getUserName()
    {
        return userName;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAcessToken()
    {
        return acessToken;
    }
}
