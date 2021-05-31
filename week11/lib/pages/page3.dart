

import 'package:flutter/material.dart';
import 'package:provider/provider.dart';

class Page3CounterProvider with ChangeNotifier{
  int _counter;
  get counter => _counter;

  Page3CounterProvider(this._counter);
  void incrementCounter(){
    _counter++;
    notifyListeners();
  }
}

class Page3 extends StatelessWidget{

  // final Map<String, String> arguments;
  // Page1(this.arguments);

  @override
  Widget build(BuildContext context) {
    Page3CounterProvider counter = Provider.of<Page3CounterProvider>(context);
    return Scaffold(
      appBar: AppBar(
        title : Text("Page 3"),
      ),
      body: Center(
        child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: <Widget>[
              // Text(arguments["user-msg1"]),
              // Text(arguments["user-msg2"]),
              Text('Hi Welcome to Page3'),
              Consumer<Page3CounterProvider>(
                builder: (context, counter3, child) => Text(
                  '${counter3.counter}',
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