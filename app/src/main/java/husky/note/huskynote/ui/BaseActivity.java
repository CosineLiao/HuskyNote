package husky.note.huskynote.ui;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import husky.note.huskynote.R;

/**
 * Created by Administrator on 2017/10/18.
 * 初始化toolbar,检测SDK版本号，进行相应的设置
 */

public class BaseActivity extends AppCompatActivity
{
	@TargetApi(Build.VERSION_CODES.M)
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// 设置屏幕方向为纵向
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
		// 检测SDK版本号
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			// 设置手机状态栏的颜色
			getWindow().setStatusBarColor(getResources().getColor(R.color.toolbar, null));
		}
	}
	
	protected void initToolbar(Toolbar toolbar)
	{
		initToolbar(toolbar, false);
	}
	
	protected void initToolbar(Toolbar toolbar, boolean hasBackArrow)
	{
		if (toolbar != null)
		{
			setSupportActionBar(toolbar); // 设置Toolbar
			toolbar.setTitleTextColor(0xffFaFaFa); // 设置字体颜色
			toolbar.setTitle(getTitle()); // 设置标题

			ActionBar actionBar = getSupportActionBar();
			if (actionBar != null && hasBackArrow)
			{
				actionBar.setDisplayHomeAsUpEnabled(true);
				actionBar.setHomeAsUpIndicator(R.mipmap.ic_arrow_back_white);
			}
		}
	}
	
	/*
	
	public boolean onCreateOptionsMenu(Menu menu); 创建菜单栏中的项目
	
	public boolean onOptionsItemSelected(MenuItem item); 处理菜单被选中后的事件处理
	
	*/
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		if (item.getItemId() == android.R.id.home)
		{
			ActivityCompat.finishAfterTransition(this);
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
