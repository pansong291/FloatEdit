package pansong291.floatedit.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View.MeasureSpec;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import pansong291.floatedit.R;
import pansong291.floatedit.listener.MainServiceListener;
import pansong291.floatedit.notification.MainNotification;

public class MainService extends Service
{
 //创建浮动窗口设置布局参数的对象
 WindowManager mWindowManager;
 //定义浮动窗口布局
 LinearLayout mFloatLayout;
 LayoutParams wmParams;
 
 public EditText edt;
 Button btn_copy, btn_cut, btn_paste, btn_close;

 MainServiceListener listener;

 public static final String START_FROM_NOTIFICATION = "ssffnn";

 @Override
 public void onCreate() 
 {
  // TODO Auto-generated method stub
  super.onCreate();

  createFloatView();
  initEvent();
  initValue();
 }

 @Override
 public int onStartCommand(Intent intent, int flags, int startId)
 {
  MainNotification.startNotification(this);

  if(intent.getBooleanExtra(START_FROM_NOTIFICATION, false))
  {
   if(mFloatLayout == null) initLayout();
   if(wmParams == null) initwmParamms();
   try{
    mWindowManager.addView(mFloatLayout, wmParams);
    mFloatLayout.measure(MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));
   }catch(Exception e)
   {
    e.printStackTrace();
   }
  }else
  {
  }

  return super.onStartCommand(intent, flags, startId);
 }

 @Override
 public IBinder onBind(Intent intent)
 {
  // TODO Auto-generated method stub
  return null;
 }

 private void createFloatView()
 {
  //获取的是WindowManagerImpl.CompatModeWrapper
  mWindowManager = (WindowManager)getApplication().getSystemService(WINDOW_SERVICE);
  initwmParamms();

  //获取浮动窗口视图所在布局
  initLayout();
  edt = mFloatLayout.findViewById(R.id.edt);
  btn_copy = mFloatLayout.findViewById(R.id.btn_copy);
  btn_cut = mFloatLayout.findViewById(R.id.btn_cut);
  btn_paste = mFloatLayout.findViewById(R.id.btn_paste);
  btn_close = mFloatLayout.findViewById(R.id.btn_close);

  //添加mFloatLayout
  mWindowManager.addView(mFloatLayout,wmParams);
  mFloatLayout.measure(MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED),MeasureSpec.makeMeasureSpec(0,MeasureSpec.UNSPECIFIED));

 }

 private void initEvent()
 {
  listener = new MainServiceListener(this);
  btn_copy.setOnClickListener(listener);
  btn_cut.setOnClickListener(listener);
  btn_paste.setOnClickListener(listener);
  btn_close.setOnClickListener(listener);
 }

 private void initValue()
 {
  
 }

 public void removeEditView()
 {
  if(mFloatLayout != null)
  {
   try{
    mWindowManager.removeView(mFloatLayout);
   }catch(Exception e)
   {
    e.printStackTrace();
   }
  }
 }
 
 private void initLayout()
 {
  LayoutInflater inflater = LayoutInflater.from(getApplication());
  mFloatLayout = (LinearLayout)inflater.inflate(R.layout.flowin_main, null);
 }

 private void initwmParamms()
 {
  wmParams = new LayoutParams();
  //设置window type
  wmParams.type = LayoutParams.TYPE_PHONE;
  //设置图片格式，效果为背景透明
  wmParams.format = PixelFormat.RGBA_8888;
  //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
  //wmParams.flags = LayoutParams.FLAG_NOT_FOCUSABLE|LayoutParams.FLAG_NOT_TOUCH_MODAL|LayoutParams.FLAG_NOT_TOUCHABLE;
  //调整悬浮窗显示的停靠位置为左侧置顶
  wmParams.gravity = Gravity.LEFT | Gravity.TOP;
  //以屏幕左上角为原点，设置x、y初始值，相对于gravity
  wmParams.x = 0;
  wmParams.y = 0;

  //设置悬浮窗口长宽数据  
  wmParams.width = LayoutParams.WRAP_CONTENT;
  wmParams.height = LayoutParams.MATCH_PARENT;
 }
 
 @Override
 public void onDestroy()
 {
  // TODO Auto-generated method stub
  super.onDestroy();
  MainNotification.stopNotification(this, true);
  removeEditView();
 }
 
}
