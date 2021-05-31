package edu.skku.map.pa1slidingpuzzle;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import static edu.skku.map.pa1slidingpuzzle.MainActivity.bitmaps3;
import static edu.skku.map.pa1slidingpuzzle.MainActivity.bitmaps4;

public class ImageAdapter extends BaseAdapter {

    private int displayWidth; //화면크기
    private int size; //이미지 크기
    private int pad; //패딩
    private int length;

    //final MainActivity mainActivity = new MainActivity();
    //private Bitmap[] bitmaps3;

    private Context context;


    public ImageAdapter(Context c, int displayWidth, int num){//매개변수

        context = c;
        this.displayWidth = displayWidth;
        size = displayWidth/num ;  //화면크기를 / 3으로 나누어서 이미지 사이즈를 구한다.
        pad = 2;
        length = num;


        //System.out.println("size="+size);
    }




    @Override
    public int getCount() {
        return length*length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(size, size)); //85,85
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(pad, pad, pad, pad); //8,8,8,8

        }else{
            imageView = (ImageView) convertView;
        }
        //이미지뷰에 주어진 위치의 이미지를 설정함
        if (length==3) { imageView.setImageBitmap(bitmaps3[position]);}
        else if(length==4) { imageView.setImageBitmap(bitmaps4[position]);}

        return imageView;
    }
}
