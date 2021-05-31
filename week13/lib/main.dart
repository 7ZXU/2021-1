import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

const eventChannel = const EventChannel("com.example.week13/Accelerometer");
class AccelerometerEvent{
  final double x;
  final double y;
  final double z;
  AccelerometerEvent(this.x, this.y, this.z);
  @override
  String toString() => '[AccelerometerEvent (x: $x, y: $y, z: $z)]';
}

//연속적인 데이터를 받아오는 stream
Stream<AccelerometerEvent> _accelerometerEvents;

//stream getter
Stream<AccelerometerEvent> get accelerometerEventStream{ //이벤트 결과를 host로부터 반환받음
  Stream<AccelerometerEvent> accelerometerEvents = _accelerometerEvents;
  if(accelerometerEvents == null){
    accelerometerEvents=
        eventChannel.receiveBroadcastStream().map( //이벤트 채널에서 받아온 값을 스트림에 매핑
            //event 내용을 class에 대입
            (dynamic event) => AccelerometerEvent(event[0] as double, event[1] as double, event[2] as double)
        );
    _accelerometerEvents = accelerometerEvents;
  }
  return accelerometerEvents;
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(

        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Flutter Demo Home Page'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  MyHomePage({Key key, this.title}) : super(key: key);

  // This widget is the home page of your application. It is stateful, meaning
  // that it has a State object (defined below) that contains fields that affect
  // how it looks.

  // This class is the configuration for the state. It holds the values (in this
  // case the title) provided by the parent (in this case the App widget) and
  // used by the build method of the State. Fields in a Widget subclass are
  // always marked "final".

  final String title;

  @override
  _MyHomePageState createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String _batteryLevel = "no result";
  List<String> _accelerometerValues;
  StreamSubscription<dynamic> _streamSubscription;


  @override
  void initState(){
    super.initState();
    // 이벤트를 리슨하다가, 이벤트가 발생하면 setState 실행해서 값 바꿈
    _streamSubscription = accelerometerEventStream.listen((AccelerometerEvent event) { //스트림에 있는 하나를 꺼내서 처리
      setState(() {
        // 값과 main 재설정
        _accelerometerValues = <String>[event.x.toStringAsFixed(3), event.y.toStringAsFixed(3), event.z.toStringAsFixed(3)];
      });
    });
  }

  @override
  void dispose(){ //dispose 발생시 중단
    super.dispose();
    _streamSubscription.cancel();
  }

  static const platform = const MethodChannel("com.example.week13/Battery");
  Future<void> _getBatteryLevel() async{  // 비동기이므로 결과를 바로 받지 않음
    String batteryLevel;
    final int result = await platform.invokeMethod("getBatteryLevel");
    // onclickListener 로 작동
    // invokeMetho가 실행되면서 host에게 메시지를 보내고
    // 응답을 받으면 result 변수에 저장
    setState(() {
      _batteryLevel = 'Battery level is $result %.';
    });
    // setState를 실행한 이후 항상 rebuild를 한다
    // 따라서 _batteryLevel 도 다시 호출하므로 변수값이 refresh 된다

  }

  // 가속도계값(센서데이터)은 원할 때 불러오는 것이 아님
  // 센서데이터가 변하면 이벤트 발생
  // 이벤트마다 연속적인 데이터 받아옴

  @override
  Widget build(BuildContext context) {

    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Rotation Vector: $_accelerometerValues',
            ),
            Text(
              '$_batteryLevel',
            ),
            ElevatedButton(onPressed: _getBatteryLevel, child: Text("GetBatteryLevel")),
          ],
        ),
      ),
    );
  }
}
