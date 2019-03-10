package pansong291.floatedit.listener;

import android.view.View;
import android.view.View.OnClickListener;
import pansong291.floatedit.service.MainService;
import pansong291.floatedit.R;
import android.content.ClipboardManager;

public class MainServiceListener implements OnClickListener
{
 MainService ms;
 ClipboardManager clipboardManager;
 
 public MainServiceListener(MainService s)
 {
  ms = s;
  clipboardManager = (ClipboardManager)s.getSystemService(s.CLIPBOARD_SERVICE);
 }

 @Override
 public void onClick(View p1)
 {
  switch(p1.getId())
  {
   case R.id.btn_copy:
    clipboardManager.setText(ms.edt.getText().toString());
    break;
    
   case R.id.btn_cut:
    clipboardManager.setText(ms.edt.getText().toString());
    ms.edt.getText().clear();
    break;
    
   case R.id.btn_paste:
    ms.edt.setText(clipboardManager.getText());
    break;
    
   case R.id.btn_close:
    ms.removeEditView();
    break;
  }
 }
 
}
