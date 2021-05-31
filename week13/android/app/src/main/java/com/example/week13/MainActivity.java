package com.example.week13;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.BatteryManager;
import android.os.Build;

import androidx.annotation.NonNull;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity {

    // 이벤트 채널 생성
    // channel이 똑같이 정의돼있어야 연결됨
    private static final String channel = "com.example.week13/Battery";
    private static final String eventChannel = "com.example.week13/Accelerometer";

    // 이벤트 스트림 핸들러

    class AccelerometerStreamHandler implements EventChannel.StreamHandler{


        private SensorEventListener sensorEventListener; // 이벤트 리스너 생성
        private SensorManager sensorManager;
        private Sensor sensor; // 센서로부터 이벤트 받아오는 변수

        // class constructor
        AccelerometerStreamHandler(){
            this.sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR); // 과제에서는 이 부분만 바꾸면 됨
        }

        // 이벤트 발생시 CALL
        // 리스너 constructor
        @Override
        public void onListen(Object arguments, EventChannel.EventSink events) {
            // 이벤트 리스너 생성
            sensorEventListener = new SensorEventListener() {
                // 이벤트 데이터가 변하면 sensorChanged 함수 call
                @Override
                public void onSensorChanged(SensorEvent event) {
                    double[] sensorValues = new double[event.values.length];
                    for(int i=0;i<event.values.length;i++){
                        sensorValues[i] = event.values[i];
                    }
                    events.success(sensorValues);
                }


                // 무시
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) { }
            };

            sensorManager.registerListener(sensorEventListener, sensor, sensorManager.SENSOR_DELAY_NORMAL);
        }

        // 스트림 핸들러가 없어질 때 CALL
        // 리스너 de-constructor
        @Override
        public void onCancel(Object arguments) {
            // 리스너 해제
            sensorManager.unregisterListener(sensorEventListener);
        }
    }


    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine){
        super.configureFlutterEngine(flutterEngine);
        // 새로운 methodchannel 생성
        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), channel)
                .setMethodCallHandler( //method call되면 실행할 내용
                        (call, result) ->{ //call:메시지 보낼때 call 정보 //result:result response 보낼 때 사용하는 argument
                            if(call.method.equals("getBatteryLevel")){ // Flutter invokeMethod 연결
                                int batteryLevel = getBatteryLevel();
                                if(batteryLevel != -1){
                                    result.success(batteryLevel); //성공 메시지는 flutter로 전송됨 
                                }else{
                                    result.notImplemented();
                                }                                
                            }
                        }
                );

        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), eventChannel)
                .setStreamHandler(
                        new AccelerometerStreamHandler()
                );
    }
    
    private int getBatteryLevel(){
        int batteryLevel = -1;
        BatteryManager batteryManager = (BatteryManager)getSystemService(BATTERY_SERVICE); // 배터리 받아오는 역할
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            batteryLevel = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY); // 현재 배터리 받아서 변수 저장
        }        
        return batteryLevel;  
    };
}
