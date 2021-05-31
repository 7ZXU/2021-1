
package edu.skku.map.pa1slidingpuzzle;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.GridView;

public class MainActivity extends AppCompatActivity {

    public static Bitmap[] splitBitmap(Bitmap bitmap, int xCount, int yCount){
        Bitmap[] bitmaps = new Bitmap[xCount*yCount];
        int width, height;
        //행, 열 개수로 가로, 세로 나눠서 셀 하나 크기 얻기
        width=bitmap.getWidth()/xCount;
        height=bitmap.getHeight()/yCount;
        //create Bitmap
        int i=0;
        for (int y=0;y<yCount;++y){
            for(int x=0;x<xCount;++x){
                bitmaps[i] = Bitmap.createBitmap(bitmap, x*width, y*height, width, height);
                //(원본 비트맵, 사진에서의 x좌표, 사진에서의 y좌표, 셀 가로, 셀 세로)
                i++;
            }
        }
        return bitmaps;
    }

    //public static Bitmap[] bitmaps3;
    //public static Bitmap[] bitmaps4;
    public static Bitmap[] bitmaps3 = new Bitmap[9];
    public static Bitmap[] bitmaps4 = new Bitmap[16];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 16;
        Bitmap orgImage = BitmapFactory.decodeResource(getApplicationContext().getResources(),R.drawable.pa1img,options);


        bitmaps3 = splitBitmap(orgImage,3, 3);
        bitmaps4 = splitBitmap(orgImage,4, 4);
        bitmaps3[8]=null;
        bitmaps4[15]=null;

        //화면크기 얻기
        Display display = ((WindowManager)getSystemService(WINDOW_SERVICE)).getDefaultDisplay();
        int displayWidth = display.getWidth();
        int displayHeight = display.getHeight();

        //create button
        Button btn3 = findViewById(R.id.button3);
        Button btn4 = findViewById(R.id.button4);
        Button btns = findViewById(R.id.shuffle);


        /**Make GridView**/
        //girdview instance
        GridView gridView = findViewById(R.id.grid_view);
        ImageAdapter adapter1 = new ImageAdapter(this,displayWidth,3); //가로크기의 정보를 같이 넘긴다.
        ImageAdapter adapter2 = new ImageAdapter(this,displayWidth,4);
        //button click event divide to 3*3
        btn3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gridView.setNumColumns(3);
                gridView.setAdapter(adapter1);
            }
        });

        //button click event divide to 4*4
        btn4.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                gridView.setNumColumns(4);
                gridView.setAdapter(adapter2);

            }

        });





    }
}