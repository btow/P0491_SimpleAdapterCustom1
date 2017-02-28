package com.example.samsung.p0491_simpleadaptercustom1;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    //имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text",
                 ATTRIBUTE_NAME_VALUE = "value",
                 ATTRIBUTE_NAME_IMAGE = "image";
    //картинки для отображения динамики
    final int positive = android.R.drawable.arrow_up_float,
              negative = android.R.drawable.arrow_down_float;
    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        /**Lанные находятся в файле "strings.xml" в массиве "values".
         * Упаковываем их в понятную для адаптера структуру
         */
        ArrayList<Map<String,Object>> data = new ArrayList<Map<String, Object>>();
        Map<String, Object> map;
        int index = 0;
        for (int value : getResources().getIntArray(R.array.values)) {
            map = new HashMap<String, Object>();
            map.put(ATTRIBUTE_NAME_TEXT, "Day " + (index + 1));
            map.put(ATTRIBUTE_NAME_VALUE, value);
            map.put(ATTRIBUTE_NAME_IMAGE, value == 0 ? 0 :(value > 0 ? positive : negative));
            data.add(map);
            index++;
        }

        //массив имён атрибутов, из которого они будут считываться
        String[] from = {ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_VALUE, ATTRIBUTE_NAME_IMAGE};
        //массив соответствующих именам атрибутов ID View-компонетов, в которые будут помещаться данные
        int[] to = {R.id.tvText, R.id.tvValue, R.id.ivImg};
        //создаём адаптер
        MySimpleAdapter mySimpleAdapter = new MySimpleAdapter(this, data, R.layout.item, from, to);
        //определяем список и устанавливаем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(mySimpleAdapter);

    }

    class MySimpleAdapter extends SimpleAdapter {

        /**
         * Constructor
         *
         * @param context  The context where the View associated with this SimpleAdapter is running
         * @param data     A List of Maps. Each entry in the List corresponds to one row in the list. The
         *                 Maps contain the data for each row, and should include all the entries specified in
         *                 "from"
         * @param resource Resource identifier of a view layout that defines the views for this list
         *                 item. The layout file should include at least those named views defined in "to"
         * @param from     A list of column names that will be added to the Map associated with each
         *                 item.
         * @param to       The views that should display column in the "from" parameter. These should all be
         *                 TextViews. The first N views in this list are given the values of the first N columns
         */
        public MySimpleAdapter(Context context, List<? extends Map<String, ?>> data,
                               int resource, String[] from, int[] to) {
            super(context, data, resource, from, to);
        }

        @Override
        public void setViewText (TextView textView, String text) {
            //метод super-класса, который вставляет текст
            super.setViewText(textView, text);
            //если TextView нужный нам, то раскрашиваем его
            if (textView.getId() == R.id.tvValue) {
                int value = Integer.parseInt(text);
                if (value < 0) {
                    textView.setTextColor(Color.RED);
                } else if (value > 0) {
                    textView.setTextColor(Color.GREEN);
                }
            }
        }

        @Override
        public void setViewImage (ImageView imageView, int value) {
            //метод super-класса
            super.setViewImage(imageView, value);
            //раскрашиваем ImageView
            if (value == negative) {
                imageView.setBackgroundColor(Color.RED);
            } else if (value == positive) {
                imageView.setBackgroundColor(Color.GREEN);
            }
        }
    }
}
