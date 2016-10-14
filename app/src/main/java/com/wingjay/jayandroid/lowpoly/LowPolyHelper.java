package com.wingjay.jayandroid.lowpoly;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay on 9/20/16.
 */
public class LowPolyHelper {

  public static Bitmap generate(Bitmap input, int blur,
                                boolean fill, int pointCount) {
    return generate(input, blur, 1, false, fill, pointCount);
  }

  /**
   * 生成low poly风格的图片
   *
   * @param input       源图片
   * @param accuracy     精度值，越小精度越高
   * @param scale        缩放，源图片和目标图片的尺寸比例
   * @param fill         是否填充颜色，为false时只绘制线条
   * @param antiAlias    是否抗锯齿
   * @param pointCount   随机点的数量
   * @return Bitmap 输出
   */
  public static Bitmap generate(Bitmap input,
                              int accuracy, float scale, boolean antiAlias,
                              boolean fill,
                              int pointCount) {
    if (input == null) {
      return null;
    }
    int width = input.getWidth();
    int height = input.getHeight();

    final ArrayList<int[]> collectors = new ArrayList<>();
    ArrayList<int[]> particles = new ArrayList<>();

    // 找出所有边缘点存入collectors中
    SobelEdgeDetector.detect(input, new SobelEdgeDetector.SobelCallback() {
      @Override
      public void call(int magnitude, int x, int y) {
        //只选取大于40 rgb(40,40,40)的色值点,这个值越小,越暗
        if (magnitude > 40) {
          collectors.add(new int[]{x, y});
        }
      }
    });

    // 添加随机点
    for (int i = 0; i < pointCount; i++) {
      particles.add(new int[]{(int) (Math.random() * width), (int) (Math.random() * height)});
    }

    // 添加随机边缘点, 数量为边缘点除以accuracy
    int len = collectors.size() / accuracy;
    for (int i = 0; i < len; i++) {
      int random = (int) (Math.random() * collectors.size());
      particles.add(collectors.get(random));
      collectors.remove(random);
    }
    // 四个顶点坐标
    particles.add(new int[]{0, 0});
    particles.add(new int[]{0, height});
    particles.add(new int[]{width, 0});
    particles.add(new int[]{width, height});

    // 利用上面的点来获取排好顺序的三角坐标数组
    List<Integer> triangles = Delaunay.triangulate(particles);

    // 对数组里的点连线填色
    // ARGS
    Bitmap output = Bitmap.createBitmap((int) (width * scale), (int) (height * scale), Bitmap.Config.ARGB_8888);
    Canvas canvas = new Canvas(output);
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    paint.setAntiAlias(antiAlias);
    if (fill) {
      paint.setStyle(Paint.Style.FILL);
    } else {
      paint.setStyle(Paint.Style.STROKE);
    }

    float x1, x2, x3, y1, y2, y3, cx, cy;
    for (int i = 0; i < triangles.size(); i += 3) {
      // 三角形顶点
      x1 = particles.get(triangles.get(i))[0];
      y1 = particles.get(triangles.get(i))[1];
      x2 = particles.get(triangles.get(i + 1))[0];
      y2 = particles.get(triangles.get(i + 1))[1];
      x3 = particles.get(triangles.get(i + 2))[0];
      y3 = particles.get(triangles.get(i + 2))[1];

      // 三角形中心坐标
      cx = (x1 + x2 + x3) / 3;
      cy = (y1 + y2 + y3) / 3;

      // 取中心点颜色作为三角形背景色
      int color = input.getPixel((int) cx, (int) cy);
      Path path = new Path();
      path.moveTo((int) (x1 * scale), (int) (y1 * scale));
      path.lineTo((int) (x2 * scale), (int) (y2 * scale));
      path.lineTo((int) (x3 * scale), (int) (y3 * scale));
      path.close();
      paint.setColor(color);
      canvas.drawPath(path, paint);
    }

    return output;
  }
}
