package com.grottworkshop.gwsbaselibrary.widget.glassactionbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.util.Log;

/**
 *
 *
 * Created by fgrott on 10/9/2014.
 */
public class BlurTask {
    /**
     * The constant TAG.
     */
    protected static final String TAG = "BlurTask";
    private Bitmap source;
    private Canvas canvas;
    private AsyncTask<Void, Void, Void> task;
    private Bitmap blurred;
    private Listener listener;
    private Context context;
    private int radius;

    /**
     * The interface Listener.
     */
    public interface Listener {
        /**
         * On blur operation finished.
         */
        void onBlurOperationFinished();
    }

    /**
     * Instantiates a new Blur task.
     *
     * @param context the context
     * @param listener the listener
     * @param source the source
     */
    public BlurTask(Context context, Listener listener, Bitmap source) {
        this(context, listener, source, GlassActionBar.DEFAULT_BLUR_RADIUS);
    }

    /**
     * Instantiates a new Blur task.
     *
     * @param context the context
     * @param listener the listener
     * @param source the source
     * @param radius the radius
     */
    public BlurTask(Context context, Listener listener, Bitmap source, int radius) {
        this.context = context;
        this.listener = listener;
        this.source = source;
        this.radius = radius;
        canvas = new Canvas(source);
        startTask();
    }

    private void startTask() {
        task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... args) {
                blurSourceBitmap();
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                canvas.drawBitmap(blurred, 0, 0, null);
                blurred.recycle();
                listener.onBlurOperationFinished();
            }
        };
        task.execute();
    }

    private void blurSourceBitmap() {
        Bitmap section = source;
        if (section == null) {
            // Probably indicates we've reached the end.
            return;
        }
        long start = System.nanoTime();
        blurred = Blur.apply(context, source, radius);
        long delta = System.nanoTime() - start;
        if (GlassActionBar.verbose) Log.v("BlurTask", "Blurring took " + delta / 1e6f + " ms");
    }

    /**
     * Cancel void.
     */
    public void cancel() {
        if (task != null) {
            task.cancel(true);
        }
        task = null;
    }
}
