package pansong291.floatedit.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import pansong291.floatedit.R;
import pansong291.floatedit.service.MainService;

public class MainActivity extends Zactivity 
{
 Intent it;
 
 @Override
 protected void onCreate(Bundle savedInstanceState)
 {
  super.onCreate(savedInstanceState);
  setContentView(R.layout.activity_main);
  
  it = new Intent(this, MainService.class);
  requestFloatWindow(this);
 }
 
 public void onStartClick(View v)
 {
  startService(it);
 }
 
 public void onStopClick(View v)
 {
  stopService(it);
 }
 
 public static void requestFloatWindow(final Zactivity ac)
 {
  if(Build.VERSION.SDK_INT >= 23)
  {
   if(!Settings.canDrawOverlays(ac))
   {
    //若没有权限，提示获取.
    //AlertDialog wad = 
    new AlertDialog.Builder(ac)
     .setTitle("提示")
     .setMessage("需要为本应用开启悬浮窗权限，请在弹出的页面中设置为允许。\n\noppo、vivo等设备若重新打开软件仍显示此对话框，请自行前往设置-更多设置-权限管理，为本软件开启悬浮窗权限。")
     .setCancelable(false)
     .setNegativeButton("取消", null)
     .setPositiveButton("确定", new Dialog.OnClickListener()
     {
      @Override
      public void onClick(DialogInterface p1, int p2)
      {
       Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
       ac.startActivity(intent);
       ac.finish();
      }
     }).show();
    //wad.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
    //wad.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);
   }
  }
 }
 
}
