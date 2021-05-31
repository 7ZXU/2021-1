package com.example.week7practice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText;
        Button button;
        TextView textView;

        editText = (EditText) findViewById(R.id.editText);
        button = (Button) findViewById(R.id.button);
        textView = (TextView) findViewById(R.id.textView);



        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient(); //클라이언트 생성

                //GET request 전송할 url 생성
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://www.omdbapi.com").newBuilder();
                //query 작성
                urlBuilder.addQueryParameter("apikey","4f497cde");
                urlBuilder.addQueryParameter("t", editText.getText().toString());

                //query와 합친 url을 문자열로 변환
                String url = urlBuilder.build().toString();
                //생성한 url 바탕으로 request 생성
                Request req = new Request.Builder()
                        .url(url)
                        .build();

                //request 보내기
                //백그라운드에서 실행하고 callback 반환
                //enqueue가 callback을 파라미터로 받아서 성공 및 실패 상황 처리
                client.newCall(req).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {

                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                        //request로 가져온 데이터를 저장할 myResponse 생성
                        final String myResponse = response.body().string();
                        //gson 생성
                        //파싱 담당 API
                        Gson gson = new GsonBuilder().create();
                        // request 결과로 받아온 데이터는 response.body() 에 저장된다
                        // 그렇게 저장된 json 파일 데이터를 myResponse에 저장한다
                        // fromJson으로 myResponse에 저장된 json 내용을 java로 변환한다
                        // 변환한 java 파일 내용을 DataModel에 저장한다
                        final DataModel data1 = gson.fromJson(myResponse, DataModel.class);

                        //백그라운드 스레드에서 실행되기에
                        // UI를 바꾸고 싶다면 Runnable을 전송해서 처리해야한다
                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                textView.setText(
                                        "Title : " + data1.getTitle() +"\n"
                                        +"Year : " + data1.getYear() +"\n"
                                        +"Released Date : " + data1.getReleased() +"\n"
                                        +"Runtime : " + data1.getRuntime() +"\n"
                                        +"Genre : " + data1.getGenre() +"\n"
                                        +"IMDB Rates : " + data1.getImdbRating() +"\n"
                                        +"Meatascore : " + data1.getMetascore() +"\n"
                                );
                            }
                        });

                    }
                });


            }
        });



    }
}