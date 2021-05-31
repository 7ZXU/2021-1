package com.example.week6practice;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.textView);
        final Context context = this;

        @SuppressLint("StaticFieldLeak")
        AsyncTask<Integer, Double, String> asyncTask = new AsyncTask<Integer, Double, String>() {
            @SuppressLint("StaticFieldLeak")

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                Toast.makeText(context, "Start AsyncTask", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Toast.makeText(context, "End AsyncTask", Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onProgressUpdate(Double... values) {
                super.onProgressUpdate(values);
                textView.setText(Double.toString(values[0]));
            }


            @Override
            protected String doInBackground(Integer... integers) {
                // Monte-carello
                // 변수 선언
                double x;
                double y;
                int Pin = 0;
                int Pout = 0;
                int Ptotal = 0;
                double expectedPI = 0.0;
                Random random = new Random();


                // 오차가 0.000001 이하면 종료한다
                for (int i=1;i<integers[0];i++) { //integers[0] //execute로 전달받은 integer
                        try {
                            Thread.sleep(100); //estimation update 100ms

                            //Math.random(); //0이상 1미만의 double 무작위 리턴
                            x = random.nextDouble();
                            y = random.nextDouble();

                            // Math.pow(m,n) : m의 n제곱
                            if (Math.pow(x, 2) + Math.pow(y, 2) <= 1){
                                Pin=Pin+1;
                            }
                            else{
                                Pout=Pout+1;
                            }

                            Ptotal = Pin + Pout;

                            //예측 PI값 구하기
                            // 나누는 수 중 하나 이상은 double 이어야 나눈 결과값도 double
                            expectedPI = (double)Pin / (double)Ptotal * 4;
                            publishProgress(expectedPI); //onProgressUpdate(values) //value 값 전달

                            //오차가 0.000001 이하이면 반복 중단
                            //break문으로 for문 탈출
                            if (Math.abs(expectedPI - Math.PI) <= 0.000001)
                                break;


                        } catch (InterruptedException e) {//예외처리
                            e.printStackTrace();
                        }
                }
                //for문 종료 시 null 반환
                return null;
            }//doInBackground
        };//AsyncTask
        asyncTask.execute(100); //integer값 전달 //실행횟수
    }
}