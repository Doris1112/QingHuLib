package com.doris.qinghulib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.qhsd.library.view.tab.BaseAdapter;
import com.qhsd.library.view.tab.TabContainerView;

/**
 * @author Doris
 * @date 2018/12/21
 **/
public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        TabContainerView tabContainerView = findViewById(R.id.main_tab_container);
        tabContainerView.setAdapter(new MainViewAdapter());
    }

    class MainViewAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public int hasMsgIndex() {
            return 0;
        }

        @Override
        public String[] getTextArray() {
            return new String[]{"home", "content", "mine"};
        }

        @Override
        public int[] getIconImageArray() {
            return new int[]{R.drawable.tab_home, R.drawable.tab_loan, R.drawable.tab_me};
        }

        @Override
        public int[] getSelectedIconImageArray() {
            return new int[]{R.drawable.tab_home_selected, R.drawable.tab_loan_selected, R.drawable.tab_me_selected};
        }

        @Override
        public Fragment[] getFragmentArray() {
            return new Fragment[]{new ItemFragment(), new ItemFragment(), new ItemFragment()};
        }

        @Override
        public FragmentManager getFragmentManager() {
            return getSupportFragmentManager();
        }
    }
}
