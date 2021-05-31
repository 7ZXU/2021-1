

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Page2CounterProvider with ChangeNotifier{
  int _counter;
  get counter => _counter;

  Page2CounterProvider(this._counter);
  void incrementCounter(){
    _counter++;
    notifyListeners();
  }
}

class Page2 extends StatelessWidget{

  // final Map<String, String> arguments;
  // Page1(this.arguments);

  @override
  Widget build(BuildContext context) {
    Page2CounterProvider counter = Provider.of<Page2CounterProvider>(context);
    return Scaffold(
      appBar: AppBar(
        title : Text("Page 2"),
      ),
      body: Center(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              // Text(arguments["user-msg1"]),
              // Text(arguments["user-msg2"]),
              Text('Hi Welcome to Page2'),
              Consumer<Page2CounterProvider>(
                builder: (context, counter2, child) => Text(
                  '${counter2.counter}',
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