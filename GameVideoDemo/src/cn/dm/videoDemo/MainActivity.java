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
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);// ���÷��
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
			mMediaPlayer.prepare();// ׼��
			Log.i(tag, "playVedio ===>>>" + mMediaPlayer.getDuration() + "");
			mMediaPlayer.setOnBufferingUpdateListener(this);
			mMediaPlayer.setOnCompletionListener(this);
			mMediaPlayer.setOnPreparedListener(this);
		} catch (Exception ex) {

		}
	}

	/**
	 * ������MediaPlayer.OnPreparedListener�ӿ� һ�����ø÷���,MediaPlayer�ͽ�����"׼������"
	 * ״̬,׼����ʼ����. �˴������ڶ�̬����SurfaceView�Ŀ�͸�!!!
	 */
	@Override
	public void onPrepared(MediaPlayer mp) {
		width = mMediaPlayer.getVideoWidth();
		height = mMediaPlayer.getVideoHeight();
		if (width != 0 && height != 0) {
			holder.setFixedSize(width, height);// ������Ƶ�߿�
			mMediaPlayer.start();
			Log.i(tag, "onPrepared ===>>>" + mMediaPlayer.getDuration() + "");
		}
	}

	/**
	 * ������MediaPlayer.OnCompletionListener�ӿ� ��MediaPlayer�������ļ�ʱ,����ø÷���.
	 * ��ʱ���Խ���һЩ�����Ĳ�������:������һ����Ƶ
	 */
	@Override
	public void onCompletion(MediaPlayer mp) {
		// һ����Ƶ������ʱ�����
		Log.i(tag, "onCompletion ===>>>" +"Completion");
	}

	/**
	 * ��Ƶ�Ļ������ percent ��Ƶ����İٷֱ�
	 */
	@Override
	public void onBufferingUpdate(MediaPlayer mp, int percent) {
		// ��Ƶ�������
		Log.i(tag,
				"onBufferingUpdate ===>>>" + percent + "|" + mMediaPlayer.getCurrentPosition());

	}

	// ������SurfaceHolder.Callback�ӿ�
	// ��SurfaceView�Ŀ��,�߶Ȼ��������������仯ʱ,����ø÷���
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	// ������SurfaceHolder.Callback�ӿ�
	// ����SurfaceView���ʱ,����ø÷���
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// ������surfaceview����ʼ׼�������ļ�
		playVedio();
	}

	// ������SurfaceHolder.Callback�ӿ�
	// ��SurfaceView�ݻ�ʱ,����ø÷���
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
