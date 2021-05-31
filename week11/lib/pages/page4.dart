

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Page4CounterProvider with ChangeNotifier{
  int _counter;
  get counter => _counter;

  Page4CounterProvider(this._counter);
  void incrementCounter(){
    _counter++;
    notifyListeners();
  }
}

class Page4 extends StatelessWidget{

  // final Map<String, String> arguments;
  // Page1(this.arguments);

  @override
  Widget build(BuildContext context) {
    Page4CounterProvider counter = Provider.of<Page4CounterProvider>(context);
    return Scaffold(
      appBar: AppBar(
        title : Text("Page 4"),
      ),
      body: Center(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              // Text(arguments["user-msg1"]),
              // Text(arguments["user-msg2"]),
              Text('Hi Welcome to Page4'),
              Consumer<Page4CounterProvider>(
                builder: (context, counter4, child) => Text(
                  '${counter4.counter}',
                  style: Theme.of(context).textTheme.headline4,
                ),
              ),

            ]
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: ()=>counter.incrementCounter(),
        tooltip: 'increment',
        child: Icon(Icons.add),
      ),
    );
  }
}