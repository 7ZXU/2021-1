package com.example.week7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //make instance
    Button button, button2;
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewById
        textView1= (TextView)findViewById(R.id.url);
        textView2= (TextView)findViewById(R.id.response);
        button = (Button)findViewById(R.id.sendingGET);
        button2 = (Button)findViewById(R.id.sendingPOST);

        //button2 click event
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // make client
                OkHttpClient client = new OkHttpClient();

                // make DataModel first
                // java 파일을 먼저 작성하고 이를 json 파일로 변환한다
                DataModel data = new DataModel();
                data.setName("sam");
                data.setJob("programmer");

                // create gson
                Gson gson = new Gson();
                // turn string to json
                String json = gson.toJson(data, DataModel.class); //data를 json 형태로 변환해 반환한다

                // URL build : request를 보내고 싶은 url을 지정한다
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://reqres.in/api/users").newBuilder();
                // url -> string 변환
                String url = urlBuilder.build().toString();
                // textView에 url 보여주기
                textView1.setText(url);

                // make request
                // query문과 함께 작성된 url을 토대로 request를 생성한다
                Request req = new Request.Builder()
                        .url(url)
                        .post(RequestBody.create(MediaType.parse("application/json"), json))  //위에서 지정한 json
                        //MediaType.parse("application/json") //입력 데이터가 json 형식임을 지정
                        //post request 생성
                        .build();

                client.newCall(req).enqueue(new Callback() {
                    @Override //callback(request) 실패
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }

                    @Override //callback(requset) 성공
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String myResponse = response.body().string(); //request의 body 내용을 지정하는데 이는 requset의 요청 데이터를 담고 있다.
                        Gson gson = new GsonBuilder().create(); //gson 생성

                        //백그라운드 스래드에서 실행되기 때문에
                        //UI를 바꾸고 싶다면 runnable을 main 스레드로 전송해야 한다.
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(myResponse);//textView를 바꾼다
                            }
                        });

                    }
                });//enqueue
            }
        });//button2


        //button click event
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // make client
                OkHttpClient client = new OkHttpClient();

                // URL build : request를 보내고 싶은 url을 지정한다
                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://reqres.in/api/users").newBuilder();
                // query를 작성한다
                // URL ? 뒤의 값 = key parameter
                urlBuilder.addQueryParameter("page","2");// KEY, VALUE

                // url -> string 변환
                String url = urlBuilder.build().toString();

                // textView에 url 보여주기
                textView1.setText(url);

                // make request
                // query문과 함께 작성된 url을 토대로 request를 생성한다
                Request req = new Request.Builder()
                        .url(url)
                        .build();
                // request를 서버로 전송할 client 생성
                // MainThread에서 request를 보낼 수 없으므로
                // 백그라운드 쓰레드에서 request을 수행한 후 콜백을 보내고
                // enqueue는 callback을 파라미터로 받아 통신 성공 및 실패 경우를 처리한다

                client.newCall(req).enqueue(new Callback() {
                    @Override //callback(request) 실패
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        e.printStackTrace();
                    }//ofailure

                    @Override //callback(requset) 성공
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        final String myResponse = response.body().string(); //request의 body 내용을 지정하는데 이는 requset의 요청 데이터를 담고 있다.
                        Gson gson = new GsonBuilder().create(); //gson 생성
                        final DataModel data1 = gson.fromJson(myResponse, DataModel.class);
                        // DataModel data1 //데이터 모델 생성
                        // fromJson // json -> java // myResponse(json body string) -> DataModel(java class)
                        // 참고 // toJson // java -> json

                        //백그라운드 스래드에서 실행되기 때문에
                        //UI를 바꾸고 싶다면 runnable을 main 스레드로 전송해야 한다.
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                textView2.setText(data1.getPage()); //textView를 바꾼다
                            }//run
                        });//mainactivity

                    }//onrResponse
                });//enqueue
            }//onclick
        });//button setOnClick

    }//oncreate
}//class