package com.example.murphysl.todo;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by WQC on 2016/10/8.
 */

public class PickUtils {

    private static final String TAG = "TimePickUtils";
    private static StringBuilder res = new StringBuilder();

    /**
     * 获取时间
     * @param context
     * @return
     */
    public static void pickTime(Context context, final Toolbar toolbar) {
        final Toolbar toolbar1 = toolbar;
        //final StringBuilder res = new StringBuilder();
        final Map<String, Integer> valueMap = new HashMap<>();
        Calendar c = Calendar.getInstance();

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_datetime, null);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.dialog_datepicker);

        valueMap.put("year", datePicker.getYear());
        valueMap.put("monthOfYear", datePicker.getMonth());
        valueMap.put("dayOfMonth", datePicker.getDayOfMonth());

        datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                valueMap.put("year", year);
                valueMap.put("monthOfYear", monthOfYear);
                valueMap.put("dayOfMonth", dayOfMonth);
            }
        });

        //StringBuilder
        new AlertDialog.Builder(context).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                res.append(valueMap.get("year") + "年");
                res.append((valueMap.get("monthOfYear")+1) + "月");
                res.append(valueMap.get("dayOfMonth")+"日");
                toolbar1.setTitle(res.toString());
                Log.i(TAG, "onClick: " +res);
                new NewActivity().getDate(res.toString());
            }
        }).create().show();

    }

}
