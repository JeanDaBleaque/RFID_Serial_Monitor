package com.jean;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.jean.rfid.ui.Tab1;
import com.jean.rfid.ui.Tab2;
import com.jean.rfid.ui.Tab3;

public class PagerAdapter extends FragmentPagerAdapter {
    int mNoOfTabs;
    public PagerAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.mNoOfTabs = numberOfTabs;
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Tab1 frag1 = new Tab1();
                return frag1;
            case 1:
                Tab2 frag2 = new Tab2();
                return frag2;
            case 2:
                Tab3 frag3 = new Tab3();
                return frag3;
        }
        return null;
    }

    @Override
    public int getCount() {
        return mNoOfTabs;
    }
}
