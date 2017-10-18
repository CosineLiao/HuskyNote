package husky.note.huskynote.ui;

import android.database.Observable;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import husky.note.huskynote.R;
import husky.note.huskynote.model.Authentication;
import husky.note.huskynote.service.AccountService;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observables.AsyncOnSubscribe;
import rx.schedulers.Schedulers;

public class LoginActivity extends AppCompatActivity implements TextWatcher
{

    public static final String ACTION_ADD_ACCOUNT = "action_addAccount:";

    private static final String EXTRA_ACCOUNT_LOCAL_ID = "extra_account.LocalId";
    private static final String TAG = "LoginActivity";

    private static final String LEANOTE_HOST = "https://leanote.com";
    private static final String FIND_PASSWORD = "/findPassword";
    private static final String EXT_IS_CUSTOM_HOST = "ext_is_custom_host";
    private static final String EXT_HOST = "ext_host";
    private static final String EXT_ACTION_PANEL_OFFSET_Y = "ext_host_et_height";

    private static final String HOST_EXCEPTION = "host is incorrect!";

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.et_host_address)
    EditText etHostAddress;
    @BindView(R.id.btn_sign_up)
    Button btnSignUp;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    @BindView(R.id.layout_btn)
    LinearLayout layoutPanel;
    @BindView(R.id.tv_forget_password)
    TextView tvForgetPassword;
    @BindView(R.id.layout_login)
    RelativeLayout layoutLogin;
    @BindView(R.id.tv_use_leanote_com)
    TextView tvCustomHost;

    private boolean isCustomHost;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        initView();
    }

    private void initView()
    {
        etEmail.addTextChangedListener(this);
        etPassword.addTextChangedListener(this);

        etHostAddress.setPivotY(0);
        int actionPanelOffsetY = 0;
        String host = " ";

        tvCustomHost.setTag(isCustomHost);
        layoutPanel.setTag(actionPanelOffsetY);
        layoutPanel.setY(actionPanelOffsetY);

        etHostAddress.setScaleY(isCustomHost ? 1 : 0);
       // etHostAddress.setText(host);

    }

    @OnClick({R.id.btn_sign_up, R.id.btn_sign_in, R.id.layout_btn, R.id.tv_forget_password, R.id.tv_use_leanote_com})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_sign_up:
                onClickSignUp();
                break;
            case R.id.btn_sign_in:
                onClickSignIn();
                break;
            case R.id.layout_btn:
                break;
            case R.id.tv_forget_password:
                onClickFogotPassword();
                break;
            case R.id.tv_use_leanote_com:
                onClickCustomHost();
                break;
        }
    }

    private void onClickSignUp()
    {

    }

    // 登录
    private void onClickSignIn()
    {
        Observer<String> observer = new Observer<String>()
        {
            @Override
            public void onCompleted()
            {
                Log.d(TAG, "onClickSignIn: observer.onCompleted!" + "  thread name:" + Thread.currentThread().getName());
            }

            @Override
            public void onError(Throwable e)
            {
                e.printStackTrace();
                if (e instanceof IllegalHostException)
                {
                    Toast.makeText(LoginActivity.this, R.string.illegal_host, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNext(String s)
            {

            }
        };
        final String email = etEmail.getText().toString();
        final String password = etPassword.getText().toString();
        final String host = getHost();

        initObservable()
                .flatMap(new Func1<String, rx.Observable<String>>()
                {
                    @Override
                    public rx.Observable<String> call(String s)
                    {
                        Log.d(TAG, "onClickSignIn: observable.flatMap" + "  thread name:" + Thread.currentThread().getName());
                        // 在此处发起网络请求
                        return AccountService.login(email, password);
                    }
                })
                .subscribeOn(Schedulers.io()) // observable.call()调用线程
                .observeOn(AndroidSchedulers.mainThread()) // observer.call()执行线程
                .subscribe(observer);
    }

    private String getHost()
    {
        return (boolean)tvCustomHost.getTag() ? etHostAddress.getText().toString().trim() : LEANOTE_HOST;
    }

    // 定义被观察者
    private rx.Observable<String> initObservable()
    {
        return rx.Observable.unsafeCreate(new rx.Observable.OnSubscribe<String>()
        {
            @Override
            public void call(Subscriber<? super String> subscriber)
            {
                String host = getHost();
                if (host.matches("^(http|https)://[^\\s]+"))
                {
                    subscriber.onNext(host);
                    subscriber.onCompleted();
                }
                else
                {
                    subscriber.onError(new IllegalHostException(HOST_EXCEPTION));
                }
            }
        });
    }

    private void onClickFogotPassword()
    {

    }

    private void onClickCustomHost()
    {
        isCustomHost = !(boolean)tvCustomHost.getTag();
        tvCustomHost.setTag(isCustomHost);

        if (isCustomHost)
        {
            tvCustomHost.setText(R.string.use_leanote_com);

            etHostAddress.animate()
                    .scaleY(1)
                    .setDuration(500)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .start();

            layoutPanel.animate()
                    .yBy(etHostAddress.getHeight())
                    .setDuration(500)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 保存etHostAddress的高度,用来做什么？
                            layoutPanel.setTag(etHostAddress.getHeight());
                        }
                    })
                    .start();
        }
        else
        {
            tvCustomHost.setText(R.string.use_custom_host);


            etHostAddress.animate()
                    .scaleY(0)
                    .setDuration(500)
                    .start();

            layoutPanel.animate()
                    .yBy(-etHostAddress.getHeight())
                    .setDuration(500)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withEndAction(new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            // 保存etHostAddress的高度，用于恢复
                            layoutPanel.setTag(0);
                        }
                    })
                    .start();
        }
    }







    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after)
    {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count)
    {

    }

    @Override
    public void afterTextChanged(Editable s)
    {

    }

    private class IllegalHostException extends Exception
    {
        public IllegalHostException(String message)
        {
            super(message);
        }

    }
}
