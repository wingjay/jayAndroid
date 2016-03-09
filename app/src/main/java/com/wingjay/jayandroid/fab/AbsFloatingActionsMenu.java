package com.wingjay.jayandroid.fab;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.google.common.base.Preconditions;
import com.wingjay.jayandroid.R;

/**
 * This menu can hide itself and support blur overlay.
 */
public abstract class AbsFloatingActionsMenu extends FloatingActionsMenu implements
    FloatingActionsMenu.OnFloatingActionsMenuUpdateListener,
    OnListScrollListener {

  private Context context;

  // targetView: 1. once touch, collapse current fabMenu; 2. click menu, blur; touch it, dismiss blur.
  private ViewGroup blurTargetView;
  private ImageView blurImageView;
  private static final int BLUR_RADIUS = 18;
  private boolean blurByPureColor = false;
  private int blurPureColor = 0xDDEEEEEE;

  private int fabMenuHeight;
  private ObjectAnimator showFabMenuAnimator;
  private ObjectAnimator hideFabMenuAnimator;
  private boolean isFabMenuShowing = true;

  private ObjectAnimator fadeoutFabMenuAnimator;
  private ObjectAnimator fadeInFabMenuAnimator;

  private int childFabNormalColor;
  private int childFabPressedColor;

  public AbsFloatingActionsMenu(Context context) {
    this(context, null);
  }

  public AbsFloatingActionsMenu(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public AbsFloatingActionsMenu(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    this.context = context;
    init();
  }

  private void init() {
    setOnFloatingActionsMenuUpdateListener(this);

    setupShowHideAnimation();
    setupFadeInOutAnimation();

    setupStyle();

    blurImageView = createBlurImageView();

  }

  private void setupStyle() {
    childFabNormalColor = context.getResources().getColor(R.color.fab_normal_white);
    childFabPressedColor = context.getResources().getColor(R.color.fab_pressed_white);
  }

  public void setBlurTargetView(ViewGroup blurTargetView) {
    this.blurTargetView = blurTargetView;
    setupBlurImageView();
  }

  private void setupBlurImageView() {
    blurImageView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        collapseImmediately();
        return true;
      }
    });
    blurImageView.setVisibility(GONE);
    Log.i("fab", "before add, menu index is " + blurTargetView.indexOfChild(this));
    blurTargetView.addView(blurImageView, blurTargetView.indexOfChild(this));
    Log.i("fab", "after add, menu index is " + blurTargetView.indexOfChild(this)
        + ", and blurimageView index is " + blurTargetView.indexOfChild(blurImageView));
  }

  private ImageView createBlurImageView() {
    ImageView blurLayerImageView = new ImageView(context);
    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT);
    blurLayerImageView.setLayoutParams(layoutParams);
    return blurLayerImageView;
  }

  private void setupFadeInOutAnimation() {
    fadeoutFabMenuAnimator = ObjectAnimator.ofFloat(this, "alpha", 1, 0).setDuration(300);
    fadeoutFabMenuAnimator.addListener(new SimpleAnimatorListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        AbsFloatingActionsMenu.this.setVisibility(View.GONE);
      }
    });
    fadeInFabMenuAnimator = ObjectAnimator.ofFloat(this, "alpha", 0, 1).setDuration(300);
    fadeInFabMenuAnimator.addListener(new SimpleAnimatorListener() {
      @Override
      public void onAnimationEnd(Animator animation) {
        AbsFloatingActionsMenu.this.setVisibility(View.VISIBLE);
      }
    });
  }

  protected void setupShowHideAnimation() {
    getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
      @Override
      public void onGlobalLayout() {
        fabMenuHeight = getMeasuredHeight();
        showFabMenuAnimator = ObjectAnimator
            .ofFloat(AbsFloatingActionsMenu.this, "translationY", fabMenuHeight, 0)
            .setDuration(300);
        showFabMenuAnimator.setInterpolator(new DecelerateInterpolator());
        showFabMenuAnimator.addListener(new SimpleAnimatorListener() {
          @Override
          public void onAnimationEnd(Animator animation) {
            isFabMenuShowing = true;
          }
        });

        hideFabMenuAnimator = ObjectAnimator
            .ofFloat(AbsFloatingActionsMenu.this, "translationY", 0, fabMenuHeight)
            .setDuration(300);
        hideFabMenuAnimator.setInterpolator(new AccelerateInterpolator());
        hideFabMenuAnimator.addListener(new SimpleAnimatorListener() {
          @Override
          public void onAnimationEnd(Animator animation) {
            isFabMenuShowing = false;
          }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
          AbsFloatingActionsMenu.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        } else {
          AbsFloatingActionsMenu.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
      }
    });
  }

  public abstract void assembleAllFabs();

  protected FloatingActionButton assembleFabByDefault(int titleId, int iconId) {
    String title = context.getResources().getString(titleId);
    Drawable iconDrawable = getResources().getDrawable(iconId);

    return assembleFabByDefault(title, iconDrawable);
  }

  /**
   * Based on provided title and icon, it will create a fab and add it into the menu.
   * @param title the label which will display close to this fab
   * @param iconDrawable the icon which will display into this fab
   * @return the new fab object
   */
  protected FloatingActionButton assembleFabByDefault(String title, Drawable iconDrawable) {
    FloatingActionButton fab = new FloatingActionButton(context);
    fab.setColorNormal(childFabNormalColor);
    fab.setColorPressed(childFabPressedColor);
    fab.setSize(FloatingActionButton.SIZE_MINI);
    fab.setTitle(title);

    Preconditions.checkNotNull(iconDrawable);
//    iconDrawable.setColorFilter(getResources().getColor(R.color.purple), PorterDuff.Mode.SRC_IN);
    fab.setIconDrawable(iconDrawable);

    addButton(fab);
    return fab;
  }

  /**
   * User can customize their fabNormalColor, and fabPressedColor. And based on these info,
   * it will create a fab and add into menu.
   * @param title
   * @param iconDrawable
   * @param fabNormalColor
   * @param fabPressedColor
   * @return
   */
  protected FloatingActionButton assembleFab(String title,
                                             Drawable iconDrawable,
                                             int fabNormalColor,
                                             int fabPressedColor) {
    this.childFabNormalColor = fabNormalColor;
    this.childFabPressedColor = fabPressedColor;
    return assembleFabByDefault(title, iconDrawable);
  }

  /**
   * This method will create a AbsListViewScrollUpDownDetector
   * and by setOnScrollListener with this absListViewScrollUpDownDetector to listview,
   * it will help to listen scroll up/down event in listview and call back onListScrollListener
   * @param listView
   */
  public void attachListView(ListView listView) {
    listView.setOnScrollListener(new AbsListViewScrollUpDownDetector(listView, this));
  }

  /**
   * The function of this method is like the one above.
   * But this support a customized AbsListViewScrollUpDownDetector, which means user also want
   * to listen scroll.
   * 1. listview scroll
   * 2. absListViewScrollUpDownDetector will detecte the scroll up/down
   * 3. if the OnListScrollListener in user's absListViewScrollUpDownDetector is AbsFloatingActionsmenu,
   * then the absListViewScrollUpDownDetector will dispatch this scroll up/down event
   * to AbsFloatingActionsmenu and it will auto hide.
   * @param listView
   * @param absListViewScrollUpDownDetector
   */
  public void attachListView(ListView listView,
                             AbsListViewScrollUpDownDetector absListViewScrollUpDownDetector) {
    Preconditions.checkArgument(absListViewScrollUpDownDetector.isEqualOnListScrollListener(this),
        "If you want this FloatingActionsMenu supports auto-hide by the scroll of listview, " +
            "your absListViewScrollUpDownDetector must use your FloatingActionsMenu as OnListScrollListener");

    listView.setOnScrollListener(absListViewScrollUpDownDetector);
  }

  @Override
  public void collapse() {
    super.collapse();
    blurImageView.setVisibility(GONE);
  }

  @Override
  public void collapseImmediately() {
    super.collapseImmediately();
    blurImageView.setVisibility(GONE);
  }

  @Override
  public void onMenuExpanded() {
    // blur root view
    if (blurTargetView == null) {
      return;
    }
    Preconditions.checkNotNull(blurImageView);

    Bitmap result;
    if (!blurByPureColor) {
      blurTargetView.setDrawingCacheEnabled(true);
      Bitmap screen = blurTargetView.getDrawingCache(true);
      result = BlurImageHelper.blurImageWithRs(context, screen, BLUR_RADIUS);
    } else {
      result = Bitmap.createBitmap(blurTargetView.getWidth(), blurTargetView.getHeight(), Bitmap.Config.ARGB_8888);
      Canvas canvas = new Canvas(result);
      canvas.drawColor(blurPureColor);
    }

    blurImageView.setImageBitmap(result);
    blurImageView.setVisibility(View.VISIBLE);
  }

  public void setBlurByPureColor(int pureColorWithAlpha) {
    Preconditions.checkNotNull(blurTargetView,
        "if you want blur effect, you need to set the blurTargetView by calling method : setBlurTargetView()");
    blurByPureColor = true;
    this.blurPureColor = pureColorWithAlpha;
  }

  @Override
  public void onMenuCollapsed() {
    if (blurTargetView == null) {
      return;
    }
    Preconditions.checkNotNull(blurImageView);
    blurImageView.setVisibility(View.GONE);
  }

  @Override
  public void onListScrollUp() {
    showFabMenuWithAnimation(false);
  }

  @Override
  public void onListScrollDown() {
    showFabMenuWithAnimation(true);
  }

  public void showFabMenuWithAnimation(boolean show) {
    if (showFabMenuAnimator == null || hideFabMenuAnimator == null) {
      return;
    }
    if (show) {
      if (isFabMenuShowing || showFabMenuAnimator.isRunning()) {
        return;
      }
      if (hideFabMenuAnimator.isRunning()) {
        hideFabMenuAnimator.cancel();
      }
      showFabMenuAnimator.start();
    } else {
      if (!isFabMenuShowing || hideFabMenuAnimator.isRunning()) {
        return;
      }
      if (showFabMenuAnimator.isRunning()) {
        showFabMenuAnimator.cancel();
      }
      hideFabMenuAnimator.start();
    }
  }

  public void fadeInFabMenu() {
    if (fadeInFabMenuAnimator.isRunning()) {
      return;
    }
    if (fadeoutFabMenuAnimator.isRunning()) {
      fadeoutFabMenuAnimator.cancel();
    }
    fadeInFabMenuAnimator.start();
  }

  public void fadeOutFabMenu() {
    if (fadeoutFabMenuAnimator.isRunning()) {
      return;
    }
    if (fadeInFabMenuAnimator.isRunning()) {
      fadeInFabMenuAnimator.cancel();
    }
    fadeoutFabMenuAnimator.start();
  }

}
