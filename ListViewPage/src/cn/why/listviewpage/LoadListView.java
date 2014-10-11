package cn.why.listviewpage;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadListView extends ListView implements OnScrollListener{
	
	private View footer;//�ײ�����
	private View header;//��������
	private int headerHeight;// ���������ļ��ĸ߶ȣ�
	private int firstVisibleItem;// ��ǰ��һ���ɼ���item��λ�ã�
	private int scrollState;// listview ��ǰ����״̬��
	private boolean isRemark;// ��ǣ���ǰ����listview������µģ�
	private int startY;// ����ʱ��Yֵ��

	private int state;// ��ǰ��״̬��
	private final int NONE = 0;// ����״̬��
	private final int PULL = 1;// ��ʾ����״̬��
	private final int RELESE = 2;// ��ʾ�ͷ�״̬��
	private final int REFLASHING = 3;// ˢ��״̬��
	
	private int lastVisibleItem;//���һ���ɼ���item
	private int totalItemCount;//�ܹ���item����
	private boolean isLoading;// ���ڼ��أ�
	private ILoadListener iLoadListener;

	public LoadListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public LoadListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public LoadListView(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * ��ӵײ�������ʾ���ֵ�listview
	 */
	private void initView(Context context){
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		//��ȡ�ײ�����
		footer = layoutInflater.inflate(R.layout.footer_layout, null);
		//��ȡ��������
		header = layoutInflater.inflate(R.layout.header_layout, null);
		//���õײ�����Ĭ�ϲ���ʾ�����ϻ����ײ���ʱ����ʾ
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);
		//���ö�������Ĭ�ϲ���ʾ�����Ӷ����»���ʱ����ʾ
//		header.findViewById(R.id.head_layout).setVisibility(View.GONE);
		this.addFooterView(footer);
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		topPadding(-headerHeight);
		this.addHeaderView(header);
		this.setOnScrollListener(this);//���ù�������
	}

	/**
	 * ֪ͨ�����֣�ռ�õĿ��ߣ��˴��ǡ����ơ�headView��width�Լ�height  
	 * @param view
	 */
	private void measureView(View view) {
		ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
		if (layoutParams == null) {
			layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,	ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		int width = ViewGroup.getChildMeasureSpec(0, 0, layoutParams.width);
		int height;
		int tempHeight = layoutParams.height;
		if (tempHeight > 0) {
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
		} else {
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}

	/**
	 * ����header�����ϱ߾ࣻ
	 * @param topPadding
	 */
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding, header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();// �ػ�һ�� 
	}
	
	@Override
	public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		this.firstVisibleItem = firstVisibleItem;
		this.lastVisibleItem = firstVisibleItem + visibleItemCount;
		this.totalItemCount = totalItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int scrollState) {
		this.scrollState = scrollState;
		if(scrollState==SCROLL_STATE_IDLE&&lastVisibleItem==totalItemCount){
			if (!isLoading) {
				isLoading = true;
				footer.findViewById(R.id.load_layout).setVisibility(View.VISIBLE);//��ʾ�ײ�����
				//���ظ���
				iLoadListener.onLoad();
			}
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (firstVisibleItem == 0) {
				isRemark = true;
				startY = (int) ev.getY();
			}
			break;

		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP:
			if (state == RELESE) {
				state = REFLASHING;
				// �����������ݣ�
				reflashViewByState();
				iLoadListener.onReflash();
			} else if (state == PULL) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}/**
	 * �ж��ƶ����̲�����
	 * 
	 * @param ev
	 */
	private void onMove(MotionEvent ev) {
		if (!isRemark) {
			return;
		}
		int tempY = (int) ev.getY();
		int space = tempY - startY;
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			if (space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);
			if (space > headerHeight + 30
					&& scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELESE;
				reflashViewByState();
			}
			break;
		case RELESE:
			topPadding(topPadding);
			if (space < headerHeight + 30) {
				state = PULL;
				reflashViewByState();
			} else if (space <= 0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
	}
	
	/**
	 * ���ݵ�ǰ״̬���ı������ʾ��
	 */
	private void reflashViewByState() {
		TextView tip = (TextView) header.findViewById(R.id.tip);
		ImageView arrow = (ImageView) header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar) header.findViewById(R.id.progress);
		RotateAnimation anim = new RotateAnimation(0, 180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		switch (state) {
		case NONE:
			arrow.clearAnimation();
			topPadding(-headerHeight);
			break;

		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("��������ˢ�£�");
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
			break;
		case RELESE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("�ɿ�����ˢ�£�");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFLASHING:
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("����ˢ��...");
			arrow.clearAnimation();
			break;
		}
	}
	
	/**
	 * ��ȡ�����ݣ�
	 */
	public void reflashComplete() {
		state = NONE;
		isRemark = false;
		reflashViewByState();
		TextView lastupdatetime = (TextView) header
				.findViewById(R.id.lastupdate_time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastupdatetime.setText(time);
	}
	
	/**
	 * ���ظ������ݵĻص��ӿ�
	 * @author Administrator
	 */
	public interface ILoadListener{
		public void onLoad();

		public void onReflash();
	}
	
	public void setInterface(ILoadListener iLoadListener){
		this.iLoadListener = iLoadListener;
	}
	
	/**
	 * �������
	 */
	public void loadComplete(){
		isLoading = false;
		footer.findViewById(R.id.load_layout).setVisibility(View.GONE);//������ϣ����صײ�����
	}
}
