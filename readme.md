#SI

SI is the sticky ball indicator. It is divided into two parts. It is divided into two parts, one is the indicator, the other is to indicate the ball.

##Usage

````code
gradle ...
````

The time interval for changing the current item is 540 ms.

XML
````xml
<org.looa.stickyballview.widget.DotIndicatorView
    android:id="@+id/dv_sample"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_centerInParent="true"
    android:padding="30dp"
    app:color_selected="#666666"
    app:color_unselected="#a9a9a9"
    app:dot_center_distance="20dp"
    app:dot_count="5"
    app:dot_radius="4dp" />
````

Java

````groovy
private DotIndicatorView dv;
...
dv = (DotIndicatorView) findViewById(R.id.dv_sample);
dv.setSelectedView(DotIndicatorView.STICKY_BALL);
...
dv.setCurrentItem(position);
````

