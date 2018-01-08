package com.gorglucks.cubendk;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.view.MotionEvent;

public class GLView extends GLSurfaceView implements Renderer {

	private Context context;

	public GLView(Context context) {
		super(context);
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.context = context;
	}

	public void onDrawFrame(GL10 gl) {
		nativeRender();
	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		nativeResize(w, h); 
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		
		nativeInit();
	        
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.texture);
		int[] pixels = new int[bitmap.getWidth() * bitmap.getHeight()];
		bitmap.getPixels(pixels, 0, bitmap.getWidth(), 0, 0, bitmap.getWidth(), bitmap.getHeight());
		nativePushTexture(pixels, bitmap.getWidth(), bitmap.getHeight());
	} 
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		// Pause/Resume if you touch the screen
		if (event.getAction() == MotionEvent.ACTION_UP) {
			nativePause();
		}
		return true;
	}
	
	// Natives methods
	private static native void nativeInit(); 
	private static native void nativePause();
    private static native void nativeResize(int w, int h);
    private static native void nativeRender();
    private static native void nativePushTexture(int[] pixels, int w, int h);
}
