package com.sqh.flipcarddemo;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridView gridview;
    private GridViewAdapter gridViewAdapter;
    private boolean mFlag = true;
    List<String> data = new ArrayList<>();
    private int clickPosition = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridview = (GridView) findViewById(R.id.doubledealer_gv);
        gridViewAdapter = new GridViewAdapter();
        data.clear();
        data.add("3");
        data.add("6");
        data.add("9");
        data.add("5");
        data.add("2");
        data.add("4");
        data.add("24");
        data.add("33");
        data.add("11");
        gridViewAdapter.refreshData_Grid(data);
        gridview.setAdapter(gridViewAdapter);
    }

    public class GridViewAdapter extends BaseAdapter {

        List<String> headGVList = new ArrayList<>();


        public boolean refreshData_Grid(List<String> list) {

            if (list != null && list.size() > 0) {
                headGVList.clear();
                headGVList.addAll(list);
                notifyDataSetChanged();
                return true;
            } else {
                return false;
            }

        }

        @Override
        public int getCount() {
            return headGVList == null ? 0 : headGVList.size();
        }

        @Override
        public Object getItem(int position) {
            return headGVList == null ? null : headGVList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        GridViewAdapter.ViewHolder holder = null;

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            // 1、如果没有可利用item时，找出所有控件
            if (convertView == null) {
                holder = new GridViewAdapter.ViewHolder();
                convertView = LayoutInflater.from(MainActivity.this)
                        .inflate(R.layout.doubledealer_gv_item, parent, false);
                holder.textView = (TextView) convertView.findViewById(R.id.tv);
                holder.myLayout = (LinearLayout) convertView.findViewById(R.id.myLayout);
                holder.fcv = (FlipCardsView) convertView.findViewById(R.id.fcv);
                // 2、绑定
                convertView.setTag(holder);
            } else {// 3、有可利用的item时就获取赋值使用
                holder = (GridViewAdapter.ViewHolder) convertView.getTag();
            }

            if (mFlag) {
                // 监听点击事件
                holder.myLayout.setClickable(true);
                holder.myLayout.setOnClickListener(new MyClickListener(holder, headGVList, position));

            } else {
                // 监听点击事件
                holder.myLayout.setClickable(false);
                if(clickPosition == position){

                }else{
                    holder.fcv.start();
                    holder.fcv.setCardBackground(Color.parseColor("#B0E2FF"));
                    holder.fcv.setText(headGVList.get(position) + "");
                }

            }

            return convertView;
        }

        private class ViewHolder {

            TextView textView;
            LinearLayout myLayout;
            FlipCardsView fcv;
        }

    }

    class MyClickListener implements View.OnClickListener {

        GridViewAdapter.ViewHolder holder;
        int position;
        List<String> headGVList;

        private MyClickListener(GridViewAdapter.ViewHolder holder, List<String> headGVList, int position) {

            this.holder = holder;
            this.headGVList = headGVList;
            this.position = position;
        }

        @Override
        public void onClick(View v) {

            if (mFlag) {
                clickPosition = position;
                holder.fcv.start();
                holder.fcv.setCardBackground(Color.parseColor("#C1C1C1"));
                holder.fcv.setText(headGVList.get(position) + "");
                new Handler().postDelayed(new Runnable() {

                    public void run() {

                        gridViewAdapter.refreshData_Grid(data);
                        gridViewAdapter.notifyDataSetChanged();

                    }

                }, 1000);

                mFlag = false;
            }

        }
    }

}
