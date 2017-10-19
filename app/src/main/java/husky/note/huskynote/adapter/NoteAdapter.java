package husky.note.huskynote.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import husky.note.huskynote.R;
import husky.note.huskynote.model.Note;
import husky.note.huskynote.widget.NoteList;

/**
 * Created by Administrator on 2017/10/19.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>
{
	private static final int TYPE_DETAIL_PALCEHOLDER = 123;

	private NoteAdapterListener mListener;

	// 创建Adapter的时候注入点击事件的监听
	public NoteAdapter(NoteAdapterListener listener)
	{
		mListener = listener;
	}

    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
		// 根据显示的需要不同，创建不同测contentView
		// 显示的需要有三种：简单显示-只显示标题  详情显示-显示一部分内容 滑动显示-在滑动过程中不加载内容
		
		int layoutId = -1;
		if (viewType == NoteList.TYPE_SIMPLE)
		{
			layoutId = R.layout.item_note_simple;
		}
		else if (viewType == NoteList.TYPE_DETAIL)
		{
			layoutId = R.layout.item_note_detail;
		}
		else if (viewType == TYPE_DETAIL_PALCEHOLDER)
		{
			layoutId = R.layout.item_note_detail_placeholder;
		}
		
		View itemView = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);

        return new NoteHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return 0;
    }

    static class NoteHolder extends RecyclerView.ViewHolder
    {
		View itemView;
		
        public NoteHolder(View itemView)
        {
			super(itemView);
            this.itemView = itemView;
        }
    }
	
	
	// 笔记点击事件接口，外部实现
	public interface NoteAdapterListener
	{
		void onClickNote(Note note);
		void onLongClickNote(Note note);
	}
}
