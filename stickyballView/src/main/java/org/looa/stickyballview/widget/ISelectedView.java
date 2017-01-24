package org.looa.stickyballview.widget;

import android.view.View;

/**
 * Created by ran on 17/1/22.
 */

public interface ISelectedView {
    void onCreatedIndicator(DotIndicatorInfo info);

    void onSelected(int position);

    View getView(View parent);
}
