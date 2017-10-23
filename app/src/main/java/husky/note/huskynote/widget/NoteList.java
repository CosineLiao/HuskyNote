package husky.note.huskynote.widget;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import husky.note.huskynote.adapter.NoteAdapter;
import husky.note.huskynote.model.Note;
import husky.note.huskynote.utils.DispalyUtils;

/**
 * Created by Administrator on 2017/10/19.
 */

public class NoteList
{
	public static final String DEFAULT_TYPE = "simple";
    public static final int TYPE_SIMPLE = 0;
    public static final int TYPE_DETAIL = TYPE_SIMPLE + 1;


    private RecyclerView mNoteListView;
	private NoteAdapter mAdapter;
	
	private int mScrollPosition;
	private int mCurrentType = TYPE_DETAIL;
	
	private RecyclerView.ItemDecoration mItemDecoration;

	
    public NoteList(Context context, View recyclerView, NoteAdapter.NoteAdapterListener adapterlistener)
    {
        this.mNoteListView =  (RecyclerView) recyclerView;
		
		// 设置recyclerview的布局为线性布局
		RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
		mNoteListView.setLayoutManager(layoutManager);
		mNoteListView.setItemAnimator(new DefaultItemAnimator());
		mAdapter = new NoteAdapter(adapterlistener);
		mNoteListView.setAdapter(mAdapter);
		
		// 给recyclerView设置滚动监听
		mNoteListView.setOnScrollListener(new RecyclerView.OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState)
			{
				super.onScrollStateChanged(recyclerView, newState);
				mAdapter.setScrolling(newState != RecyclerView.SCROLL_STATE_IDLE);

				// 如果为空闲状态，则更新数据
				if (newState == RecyclerView.SCROLL_STATE_IDLE)
				{
					mAdapter.notifyDataSetChanged();
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy)
			{
				super.onScrolled(recyclerView, dx, dy);
				// 标记滚动的位置
				mScrollPosition = dy;
			}
		});
		
		mItemDecoration = new DividerDecoration(DispalyUtils.dp2px(8));
		
		// 设置数据显示的格式：simple & detail
		setType(mCurrentType);
    }
	
	private void setType(int type)
	{
		mCurrentType = type;
		// 将type告诉datapter
		mAdapter.setType(type);
		mNoteListView.removeItemDecoration(mItemDecoration);
		
		if (type == TYPE_DETAIL)
		{
			mNoteListView.addItemDecoration(mItemDecoration);
		}
		
		// 通知adapter数据改变
		mAdapter.notifyDataSetChanged();
	}
	
	// 为查找关键字设置高亮
	public void setHighLight(String keyword)
	{
		mAdapter.setHightLight(keyword);
	}
	
	public void toggleType()
	{
		int newType = (mCurrentType == TYPE_SIMPLE) ? TYPE_SIMPLE : TYPE_DETAIL;
		setType(newType);
	}
	
	public String getType()
	{
		switch(mCurrentType)
		{
			case TYPE_SIMPLE:
				return "simple";
			case TYPE_DETAIL:
				return "detail";
			default:
				return DEFAULT_TYPE;
		}
	}
	
	public void setType(String type)
	{
		int newType;
		switch(type)
		{
			case "detail":
				newType = TYPE_DETAIL;
				break;
			case "simple":
			default:
				newType = TYPE_SIMPLE;
		}
		
		setType(newType);
	}
	
	public void render(List<Note> notes)
	{
		mAdapter.load(notes);
	}
	
	public void remove(Note note)
	{
		mAdapter.delete(note);
	}
	
	public void setSelected(Note note, boolean isSelected)
	{
		mAdapter.setSelected(note, isSelected);
	}
	
	public int getScrollPosotion()
	{
		return mScrollPosition;
	}
	
	// 撤销所有的选中
	public void invalidateAllSelected()
	{
		mAdapter.invalidateAllSelected();
	}
	
	public void setScrollPosition(int position)
	{
		mScrollPosition = position;
		mNoteListView.scrollTo(0, position);
	}
}



