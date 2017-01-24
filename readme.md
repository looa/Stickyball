#SI

SI is the sticky ball indicator. It is divided into two parts. It is divided into two parts, one is the indicator, the other is to indicate the ball.

##Usage

````code
gradle ...
````

The time interval for changing the current item is 540 ms.

````code
private DotIndicatorView dv;
...
dv = (DotIndicatorView) findViewById(R.id.dv_sample);
dv.setSelectedView(DotIndicatorView.STICKY_BALL);
...
dv.setCurrentItem(position);
````

