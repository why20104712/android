package cn.why.timepicker;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;

/**
 * ����TimePicker��DatePickerʹ��
 * @author Administrator
 */
public class MainActivity extends Activity {

	private TimePicker timePicker;
	private DatePicker datePicker;
	private Calendar calendar;
	private int year, month, day, hour, minute, second;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
       
        calendar = Calendar.getInstance(Locale.CHINA);
        calendar.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));//����ʱ��������Сʱ����8Сʱ
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;//�·���Ҫ��1�����ǵ�ǰ�·�
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);
        second = calendar.get(Calendar.SECOND);
        setTitle(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
        //����ѡ����������ʹ��init�������г�ʼ��ʹ��
        datePicker.init(year, Calendar.MONTH, Calendar.DAY_OF_MONTH, new OnDateChangedListener() {
			
			public void onDateChanged(DatePicker arg0, int year, int month, int day) {
				setTitle(year+"-"+(month+1)+"-"+day+" "+hour+":"+minute);
			}
		});
        //ʱ��ѡ��������ֱ��ʹ��setOnTimeChangedListener���г�ʼ��ʹ��
        timePicker.setOnTimeChangedListener(new OnTimeChangedListener() {
			
			public void onTimeChanged(TimePicker arg0, int hourOfDay, int minute) {
				setTitle(year+"-"+month+"-"+day+" "+hourOfDay+":"+minute);
			}
		});
        //����ѡ�����Ի���
//        new DatePickerDialog(this, new OnDateSetListener() {
//			public void onDateSet(DatePicker arg0, int year, int month, int day) {
//				setTitle(year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second);
//			}
//		}, year, (month+1), day).show();
        //ʱ��ѡ�����Ի���
        new TimePickerDialog(this, new OnTimeSetListener() {
			
			@Override
			public void onTimeSet(TimePicker arg0, int hourOfDay, int minute) {
				setTitle(year+"-"+month+"-"+day+" "+hourOfDay+":"+minute);
			}
		}, hour, minute, true).show();
        
    }
    
}
