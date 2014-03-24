package cn.dm.videoDemo;

import android.media.AudioManager;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;

public class MainActivity extends Activity implements
		OnBufferingUpdateListener, OnCompletionListener, OnPreparedListener,
		SurfaceHolder.Callback {
	
	private static final String tag = "GameVideoDemo" ;

	private int width = 0;
	private int height = 0;
	private MediaPlayer mMediaPlayer = null;
	private SurfaceView mSurfaceView = null;
	private SurfaceHolder holder = null;
	//private String path = "http://www.domob-inc.cn:12345/ad_mock/source/domob.mp4";
	private String path = "http://www.domob-inc.cn:12345/ad_mock/source/101.mp4" ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mSurfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		holder = mSurfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// 设置风格
	}

	public void playVedio() {
		try {
//			path = android.os.Environment.getExternalStorageDirectory()
//					+ "/moto_0012.3gp";
			mMediaPlayer = new MediaPlayer();
			// mMediaPlayer.setDataSource(path);
			mMediaPlayer.setDataSource(this,
					Uri.parse(path));
			mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mMediaPlayer.setDisplay(holder);
			mMediaPlayer.prepare();// 准备
			Log.i(tag, "playVedio ===>>>" + mMediaPlayer.getDuration() + "");
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
		} catch (Exception ex) {

		}
	}

	/**
	 * 来自于MediaPlayer.OnPreparedListener接口 一旦调用该方法,MediaPlayer就进入了"准备就绪"
	 * 状态,准备开始播放. 此处可用于动态设置SurfaceView的宽和高!!!
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		width = mMediaPlayer.getVideoWidth();
		height = mMediaPlayer.getVideoHeight();
		if (width != 0 && height != 0) {
			holder.setFixedSize(width, height);// 设置视频高宽
			mMediaPlayer.start();
			Log.i(tag, "onPrepared ===>>>" + mMediaPlayer.getDuration() + "");
		}
	}

	/**
	 * 来自于MediaPlayer.OnCompletionListener接口 当MediaPlayer播放完文件时,会调用该方法.
	 * 此时可以进行一些其他的操作比如:播放下一个视频
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// 一个视频播放完时会调用
		Log.i(tag, "onCompletion ===>>>" +"Completion");
	}

	/**
	 * 视频的缓冲情况 percent 视频缓冲的百分比
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// 视频缓冲情况
		Log.i(tag,
				"onBufferingUpdate ===>>>" + percent + "|" + mMediaPlayer.getCurrentPosition());

	}

	// 来自于SurfaceHolder.Callback接口
	// 当SurfaceView的宽度,高度或其他参数发生变化时,会调用该方法
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	// 来自于SurfaceHolder.Callback接口
	// 创建SurfaceView完成时,会调用该方法
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// 创建完surfaceview，开始准备播放文件
		playVedio();
	}

	// 来自于SurfaceHolder.Callback接口
	// 当SurfaceView摧毁时,会调用该方法
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {

	}

	@Override
	protected void onPause() {
		super.onPause();
		if (mMediaPlayer != null) {
			if (mMediaPlayer.isPlaying()) {
				mMediaPlayer.stop();
			}
			mMediaPlayer.reset();
			mMediaPlayer.release();
			mMediaPlayer = null;
		}
	}

}
