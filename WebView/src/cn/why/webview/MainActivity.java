package cn.why.webview;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {

	private String url = "http://2014.qq.com/";
	private WebView webView;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web);

		// Uri uri = Uri.parse(url); // urlΪ��Ҫ���ӵĵ�ַ
		// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		// startActivity(intent);
		init();
	}

	private void init() {
		webView = (WebView) findViewById(R.id.webView);
		// WebView���ر�����Դ
		// webView.loadUrl("file:///android_asset/example.html");
		// WebView����web��Դ
		webView.loadUrl(url);
		// ����WebViewĬ��ͨ��������������ϵͳ���������ҳ����Ϊ��ʹ����ҳ������WebVIew�д�
		webView.setWebViewClient(new WebViewClient() {

			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// ����ֵ��true��ʱ�������ҳ��WebView��ȥ�򿪣����Ϊfalse����ϵͳ�����������������ȥ��
				view.loadUrl(url);
				return true;
			}
			// WebViewClient����WebViewȥ����һЩҳ����ƺ�����֪ͨ
		});
		// ����֧��JavaScript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		// WebView����ҳ������ʹ�û������
		settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webView.setWebChromeClient(new WebChromeClient() {
			public void onProgressChanged(WebView view, int newProgress) {
				// newProgress 1-100֮�������
				if (newProgress == 100) {
					// ��ҳ������ϣ��ر�ProgressDialog
					closeDialog();
				} else {
					// ��ҳ���ڼ���,��ProgressDialog
					openDialog(newProgress);
				}
			}
			private void closeDialog() {
				if (dialog != null && dialog.isShowing()) {
					dialog.dismiss();
					dialog = null;
				}
			}
			private void openDialog(int newProgress) {
				if (dialog == null) {
					dialog = new ProgressDialog(MainActivity.this);
					dialog.setTitle("���ڼ���");
					dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
					dialog.setProgress(newProgress);
					dialog.show();
				} else {
					dialog.setProgress(newProgress);
				}
			}
		});
	}
}
