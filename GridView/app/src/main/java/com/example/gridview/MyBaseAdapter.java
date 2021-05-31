package com.example.gridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class MyBaseAdapter extends BaseAdapter {
    Context c;
    int items[];

    MyBaseAdapter(Context c, int arr[]){
        this.c = c;
        items = arr; // view에 연결할 resource
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.grid_layout, null); // 그리드뷰 객체화
        }
        ImageView imageView = view.findViewById(R.id.imageView); // 이미지뷰 객체 가져오기
        imageView.setImageResource(items[i]); // 이미지뷰에 resource 대입
        return view;
    }
}
