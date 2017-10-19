package husky.note.huskynote.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import husky.note.huskynote.R;

public class MainActivity extends BaseActivity
{
    private static final String TAG_NOTE_FRAGMENT = "tag_note_fragment";

    @BindView(R.id.id_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.frame_container)
    FrameLayout frameContainer;
    @BindView(R.id.fresh_layout)
    SwipeRefreshLayout freshLayout;
    @BindView(R.id.btn_float)
    FloatingActionButton btnFloat;
    @BindView(R.id.layout_drawer)
    DrawerLayout mNavigationView;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 初始设化toolbar,添加返回按钮，置按钮资源
        initToolbar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_menu_white);

        // 将fragmen添加到活动中
        NoteFragment noteFragment = NoteFragment.getInstance();

        getSupportFragmentManager().beginTransaction().add(R.id.frame_container, noteFragment, TAG_NOTE_FRAGMENT).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {

        }
        else if (item.getItemId() == R.id.action_search)
        {
            // 打开搜索界面
            Intent searchIntent = new Intent(this, SearchActivity.class);
            startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.fresh_layout, R.id.btn_float, R.id.layout_drawer})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.fresh_layout:
                break;
            case R.id.btn_float:
                break;
            case R.id.layout_drawer:
                break;
        }
    }
}
