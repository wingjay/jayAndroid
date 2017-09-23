package com.wingjay.jayandroid.skin;

import com.wingjay.jayandroid.BaseActivity;

/**
 * BaseSkinActivity
 *
 * @author wingjay
 * @date 2017/08/04
 */
public class BaseSkinActivity extends BaseActivity {
//public class BaseSkinActivity extends BaseActivity implements OnSkinUpdateListener {

    //private SkinInflaterFactory skinInflaterFactory;
    //
    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
    //    /**
    //     * this setFactory must before super.onCreate
    //     * 1. super.onCreate will put Factory1 inside -> mFactorySet=true -> deny more Factory1
    //     * 2. super.onCreate will put Factory2 inside -> even you put a Factory1 inside,
    //     * the Factory2 will execute inflate Before your Factory1.
    //     *
    //     * refer to {@link AppCompatDelegateImplV9#installViewFactory()}
    //     * refer to {@link LayoutInflater#createViewFromTag(int, ViewGroup)}
    //     */
    //    skinInflaterFactory = new SkinInflaterFactory(this);
    //    LayoutInflater inflater = LayoutInflater.from(this); // shouldn't use LayoutInflater.from(applicationContext)
    //    try {
    //        Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
    //        field.setAccessible(true);
    //        field.setBoolean(inflater, false);
    //        inflater.setFactory(skinInflaterFactory);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //
    //    super.onCreate(savedInstanceState);
    //}
    //
    //@Override
    //protected void onResume() {
    //    super.onResume();
    //    OkSkin.getInstance().listenSkinUpdate(this);
    //}
    //
    //@Override
    //protected void onDestroy() {
    //    super.onDestroy();
    //    OkSkin.getInstance().removeSkinUpdateListener(this);
    //    skinInflaterFactory.clean();
    //}
    //
    //@Override
    //public void onSkinUpdate() {
    //    skinInflaterFactory.updateSkin();
    //}
}
