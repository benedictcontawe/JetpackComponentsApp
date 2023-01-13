package com.example.language;

import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    int topDetector, bottomDetector;

    public void setTopDetector(int topDetector) {
        this.topDetector = topDetector;
    }

    public void setBottomDetector(int bottomDetector) {
        this.bottomDetector = bottomDetector;
    }

    public int getTopDetector() {
        return topDetector;
    }

    public int getBottomDetector() {
        return bottomDetector;
    }
}
