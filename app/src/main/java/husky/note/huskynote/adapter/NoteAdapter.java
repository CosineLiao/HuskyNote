package husky.note.huskynote.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import husky.note.huskynote.R;
import husky.note.huskynote.database.NotebookDataSqlite;
import husky.note.huskynote.model.Account;
import husky.note.huskynote.model.Note;
import husky.note.huskynote.model.NoteBook;
import husky.note.huskynote.model.NoteFile;
import husky.note.huskynote.service.NoteFileService;
import husky.note.huskynote.utils.FileUtils;
import husky.note.huskynote.utils.HtmlUtils;
import husky.note.huskynote.utils.TimeUtils;
import husky.note.huskynote.widget.NoteList;

/**
 * Created by Administrator on 2017/10/19.
 */

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder>
{
	private static final int TYPE_DETAIL_PALCEHOLDER = 123;

	private NoteAdapterListener mListener;
	
	private List<Note> mData;
	private List<Long> mSelectedNotes = new ArrayList<>();

	private Map<String, String> mNotebookId2TitleMaps;

	private boolean isScrolling;
	private int mCurrentType = NoteList.TYPE_SIMPLE;
	private Pattern mTitleHighlight;

	// 创建Adapter的时候注入点击事件的监听
	public NoteAdapter(NoteAdapterListener listener)
	{
		mListener = listener;
	}

	public void load(List<Note> source)
	{
		mData = source;
		updateNotebookMap();
		notifyDataSetChanged();
	}

	private void updateNotebookMap()
	{
		List<NoteBook> noteBooks = NotebookDataSqlite.getAllNotebook(Account.getCurrent().getUserId());
		mNotebookId2TitleMaps = new HashMap<>();

		for (NoteBook noteBook : noteBooks)
		{
			mNotebookId2TitleMaps.put(noteBook.getNotebookId(), noteBook.getTitle());
		}
	}

	@Override
	public int getItemViewType(int position)  // 获取itemview的类型，此处是显示类型
	{
		if (isScrolling && mCurrentType == NoteList.TYPE_DETAIL)
		{
			return TYPE_DETAIL_PALCEHOLDER;
		}

		return mCurrentType;
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
		final Note note = mData.get(position);
		int type = getItemViewType(position);
		
		switch(type)
		{
			case NoteList.TYPE_DETAIL:
			case TYPE_DETAIL_PALCEHOLDER:
				renderDetailMeta(holder, note); // 初始化要显示的数据
				if (type == NoteList.TYPE_DETAIL)
				{
					renderDetailContent(holder, note);
				}
				break;
			case NoteList.TYPE_SIMPLE:
				renderSimple(holder, note);
				break;
			default:
				break;
		}
		
		// 给itemView设置点击监听
		holder.itemView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (mListener != null)
				{
					mListener.onClickNote(note);
				}
			}
		});
		
		holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
		{
			@Override
			public boolean onLongClick(View v)
			{
				if (mListener != null)
				{
					mListener.onLongClickNote(note);
				}

				return false;
			}
		});
		
		// 设置note是否已被选中
		holder.container.setSelected(mSelectedNotes.contains(note));
		
    }


    @Override
    public int getItemCount()
    {
        return mData == null ? 0 : mData.size();
    }
	
	private void renderDetailMeta(NoteHolder holder, final Note note)
	{
		if (holder.ivImageView != null)
		{
			holder.ivImageView.setImageDrawable(null);
		}
		
		if (TextUtils.isEmpty(note.getTitle()))
		{
			holder.tvTitle.setText(R.string.untitled);
		}
		else
		{
			holder.tvTitle.setText(note.getTitle());
		}
		
		holder.tvNotebook.setText(mNotebookId2TitleMaps.get(note.getNoteBookId()));
		long updateTime = note.getUpdatedTimeVal();
		String time = "2017.10.23 10.57";
		if (updateTime >= TimeUtils.getToday().getTimeInMillis())
		{
			time = TimeUtils.toTimeFormat(updateTime);
		}
		else if (updateTime >= TimeUtils.getYesterday().getTimeInMillis())
		{
			time = holder.tvUpdateTime.getContext().getString(R.string.time_yesterday, TimeUtils.toTimeFormat(updateTime));
		}
		else
		{
			time = TimeUtils.toYearFormat(updateTime);
		}
		
		holder.tvUpdateTime.setText(time);
		
		holder.tvDirty.setVisibility(note.isDirty() ? View.VISIBLE : View.GONE);
	}
	
	
	private void renderDetailContent(NoteHolder holder, final Note note)
	{
		List<NoteFile> notesFiles = NoteFileService.getRelatedNoteFiles(note.getId());
		holder.ivImageView.setImageDrawable(null);
		
		for (NoteFile noteFile : notesFiles)
		{
			if (TextUtils.isEmpty(noteFile.getLocalPath()))
			{
				continue;
			}
			
			File file = new File(noteFile.getLocalPath());
			if (FileUtils.isImageFile(file))
			{
				/*Glide.with(holder.container.getContext())
						.load(file)
						.fitCenter()
						.into(holder.ivImageView);*/
				break;
			}
		}
		
		if (note.isMarkDown())
		{
			holder.tvContent.setText(note.getNoteAbstract());
		}
		else
		{
			// 这是在做什么？
			/*Spanned spannedContent = Html.from(note.getNoteAbstract());
			String contentStr = spannedContent.toString();
			contentStr = contentStr.repalceAll("\\n\\n+", "\n");
			holder.tvContent.setText(contentStr);*/
		}
	}
	
	private void renderSimple(NoteHolder holder, final Note note)
	{
		if (TextUtils.isEmpty(note.getTitle()))
		{
			holder.tvTitle.setText(R.string.untitled);
		}
		else
		{
			// 为什么标题要高亮
			holder.tvTitle.setText(getHighLightedText(note.getTitle()));
		}
		
		holder.tvNotebook.setText(mNotebookId2TitleMaps.get(note.getNoteBookId()));
		
		long updateTime = note.getUpdatedTimeVal();
		String time;
		
		if (updateTime >= TimeUtils.getToday().getTimeInMillis())
		{
			time = TimeUtils.toTimeFormat(updateTime);
		}
		else if (updateTime >= TimeUtils.getYesterday().getTimeInMillis())
		{
			time = holder.tvUpdateTime.getContext().getString(R.string.time_yesterday, TimeUtils.toTimeFormat(updateTime));
		}
		else
		{
			time = TimeUtils.toYearFormat(updateTime);
		}
		
		holder.tvUpdateTime.setText(time);
		
		holder.tvDirty.setVisibility(note.isDirty() ? View.VISIBLE : View.GONE);
	}
	
	
	private CharSequence getHighLightedText(String text)
	{
		if (mTitleHighlight == null)
		{
			return HtmlUtils.delHTMLTag(text);
		}
		
		SpannableStringBuilder builder = new SpannableStringBuilder(HtmlUtils.delHTMLTag(text));
		Matcher matcher = mTitleHighlight.matcher(text);
		int color = 0xFFFDD835;
		
		while (matcher.find())
		{
			builder.setSpan(new BackgroundColorSpan(color),
					matcher.start(), matcher.end(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
		}
		
		return builder;
	}
	
	
	// 删除笔记
	public void delete(Note note)
	{
		int index = mData.indexOf(note);
		
		if (index >= 0)
		{
			mData.remove(index);
			notifyItemRemoved(index);
		}
	}

	public void setType(int type)
	{
		mCurrentType = type;
	}

	public void setScrolling(boolean scrolling)
	{
		isScrolling = scrolling;
	}

	public void setSelected(Note note, boolean isSelected)
	{
		if (!isSelected)
		{
			mSelectedNotes.remove(note.getId());
			notifyDataSetChanged();
		}
		else if (!mSelectedNotes.contains(note.getId()))
		{
			mSelectedNotes.add(note.getId());
			notifyDataSetChanged();
		}
	}

	public void invalidateAllSelected()
	{
		mSelectedNotes.clear();
		notifyDataSetChanged();
	}

	public void setHightLight(String keyword)
	{
		if (TextUtils.isEmpty(keyword))
		{
			mTitleHighlight = null;
		}
		else
		{
			mTitleHighlight = Pattern.compile(keyword, Pattern.CASE_INSENSITIVE);
		}
	}

	static class NoteHolder extends RecyclerView.ViewHolder
    {
		View itemView;			// itemView
		View container;
		
		TextView tvTitle;		// 笔记标题
		TextView tvContent; 	// 笔记内容
		TextView tvNotebook;	// 笔记所属笔记本
		TextView tvUpdateTime;	// 笔记更新时间

		TextView tvDirty;
		ImageView ivImageView;	// 图片
		
		
        public NoteHolder(View itemView)
        {
			super(itemView);
			container = itemView.findViewById(R.id.item_container);
			tvTitle = (TextView) itemView.findViewById(R.id.tv_note_title);
			tvContent = (TextView) itemView.findViewById(R.id.tv_note_content);
			tvNotebook = (TextView) itemView.findViewById(R.id.tv_note_book_name);
			tvUpdateTime = (TextView) itemView.findViewById(R.id.tv_note_edit_time);

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
