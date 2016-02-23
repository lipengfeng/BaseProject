package com.sour.sks.baseproject.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;


import com.sour.sks.baseproject.R;
import com.sour.sks.baseproject.activity.BaseFragment;

import java.util.List;

/**
 * Initialization the menu view. handle the menu event.
 */
public class MenuTabAdapter implements OnClickListener {

    private List<Fragment> mMenuFragment;
    private List<View> mMenuView;
    private FragmentActivity mFragmentActivity;
    private int mFragmentContentId;
    private int mCurrentMenu = -1;
    private boolean mAnimationFlag = false;

    /**
     * @param activity          fragment activity
     * @param view              menu view list
     * @param list              menu fragment list
     * @param fragmentContentId main fragment content
     */
    public MenuTabAdapter(FragmentActivity activity, List<View> view,
                          List<Fragment> list, int fragmentContentId) {
        this.mMenuFragment = list;
        this.mMenuView = view;
        this.mFragmentActivity = activity;
        this.mFragmentContentId = fragmentContentId;

        // add this first fragment to default
        // FragmentTransaction ft = this.mFragmentActivity
        // .getSupportFragmentManager().beginTransaction();
        // ft.add(fragmentContentId, this.mMenuFragment.get(0), "home");
        // ft.commit();

        int menuSize = this.mMenuView.size();
        for (int i = 0; i < menuSize; i++) {
            this.mMenuView.get(i).setOnClickListener(this);
        }
        // changeMenuStatus(0);
        onClick(this.mMenuView.get(0));
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        Fragment fragment = getCurrentMenuFragment();
        if (fragment instanceof BaseFragment) {
            ((BaseFragment) fragment).onKeyDown(KeyEvent.KEYCODE_BACK, null);
        }

//        switch (v.getId()) {
//            case R.id.mCustomerTab:// 我的客户
//                if (mCurrentMenu == 0) {
//                    return;
//                }
//                changeMenuStatus(0);
//                changeMenu(0);
//                break;
//            case R.id.mFindingTab:// 发现
//                if (mCurrentMenu == 1) {
//                    return;
//                }
//                changeMenuStatus(1);
//                changeMenu(1);
//                break;
//            case R.id.mMessageTab:// 消息
//                if (mCurrentMenu == 2) {
//                    return;
//                }
//                changeMenuStatus(2);
//                changeMenu(2);
//                break;
//            case R.id.mMineTab:// 个人中心
//                if (mCurrentMenu == 3) {
//                    return;
//                }
//                changeMenuStatus(3);
//                changeMenu(3);
//                break;
//        }
    }

    /**
     * change the menu status.
     *
     * @param index the menu view list index
     */
    public void changeMenuStatus(int index) {
        int menuSize = this.mMenuView.size();
        for (int i = 0; i < menuSize; i++) {
            if (i == index) {
                this.mMenuView.get(i).setSelected(true);
            } else {
                this.mMenuView.get(i).setSelected(false);
            }
        }
    }

    /**
     * when click the menu button,if the view has been added in the fragment ,
     * it will be exec method onResume(),then show it, if not,it will be added
     * into the fragment and show it.
     *
     * @param index the menu view list index
     */
    public void changeMenu(int index) {
        Fragment fragment = this.mMenuFragment.get(index);
        FragmentTransaction ft = obtainFragmentTransaction(index);

        Fragment currFragment = getCurrentMenuFragment();
        if (currFragment != null) {
            currFragment.setUserVisibleHint(false);
            currFragment.onPause();
        }

        if (fragment.isAdded()) {
            fragment.setUserVisibleHint(true);
            fragment.onResume();
        } else {
            String tag = "";
            switch (index) {
                case 0:
                    tag = "customer";
                    break;
                case 1:
                    tag = "find";
                    break;

                case 2:
                    tag = "message";
                    break;
                case 3:
                    tag = "mine";
                    break;
            }
            ft.add(mFragmentContentId, fragment, "" + tag);
            fragment.setUserVisibleHint(true);
        }
        showMenuContent(index);
        ft.commit();
    }

    /**
     * show the selected fragment and hide others
     *
     * @param index the menu view list index
     */
    private void showMenuContent(int index) {
        int size = this.mMenuFragment.size();
        for (int i = 0; i < size; i++) {
            Fragment fragment = this.mMenuFragment.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(i);

            if (index == i) {
                // home
                // if (i == 0) {
                // List<Fragment> childFList = fragment
                // .getChildFragmentManager().getFragments();
                // if (childFList != null
                // && childFList.size() > 0
                // && fragment.getChildFragmentManager()
                // .getBackStackEntryCount() > 0) {
                // for (Fragment f : childFList) {
                // fragment.getChildFragmentManager().popBackStack();
                // fragment.getChildFragmentManager()
                // .beginTransaction().remove(f).commit();
                // }
                // }
                // }
                ft.show(fragment);
            } else {
                ft.hide(fragment);
            }
            ft.commit();
        }
        mCurrentMenu = index; // set current menu
    }

    /**
     * set animation flag.
     *
     * @param b boolean
     */
    public void setIsAnimation(boolean b) {
        this.mAnimationFlag = b;
    }

    /**
     * if the mAnimationFlag is true,when two fragment are exchanging,it will
     * have animation.
     *
     * @param index the menu view list index
     * @return object of FragmentTransaction
     */
    private FragmentTransaction obtainFragmentTransaction(int index) {
        FragmentTransaction ft = this.mFragmentActivity
                .getSupportFragmentManager().beginTransaction();
        if (this.mAnimationFlag) {
            // set animation
            if (index > mCurrentMenu) {
                ft.setCustomAnimations(R.anim.slide_left_in,
                        R.anim.slide_left_out);
            } else {
                ft.setCustomAnimations(R.anim.slide_right_in,
                        R.anim.slide_right_out);
            }
        }

        return ft;
    }

    /**
     * get the id which have been selected
     *
     * @return the selected index of the menu list
     */
    public int getCurrentMenu() {
        return mCurrentMenu;
    }

    /**
     * get the fragment which have been selected
     *
     * @return the selected fragment
     */
    public Fragment getCurrentMenuFragment() {
        if (mCurrentMenu < 0 || mCurrentMenu >= mMenuFragment.size()) {
            return null;
        }
        return this.mMenuFragment.get(mCurrentMenu);
    }

    public Fragment getMenuFragment(int i) {
        return this.mMenuFragment.get(i);
    }

}
