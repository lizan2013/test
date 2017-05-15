package cn.mondora.appreciationzone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Environment;
import android.util.Log;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

@SuppressLint("NewApi")
public class MyWebClient extends BridgeWebViewClient {
	private Context mContext;
	public static final  String JS_CACHE_PATH = Environment.getExternalStorageDirectory()
			.getPath() + "/mondora/cache";

	public MyWebClient(BridgeWebView webView, Context mContext) {
		super(webView);
		this.mContext = mContext;
	}

	public void MyWebClient(Context context) {
		mContext = context;
	}

	@Override
	public WebResourceResponse shouldInterceptRequest(WebView view,
			WebResourceRequest request) {
		if (!"get".equals(request.getMethod().toLowerCase())) {
			return super.shouldInterceptRequest(view, request);
		}
		String url = request.getUrl().toString();
		Log.d("MyWebClient","uri="+url);
		if (url.contains(".js") || url.contains(".css") || url.contains(".png")
				|| url.contains(".jpeg") || url.contains(".jpg")
				|| url.contains(".bmp") || url.contains(".gif")
				|| url.contains(".ico")) {
			WebResourceResponse resourceResponse = returnData(url);
			if (resourceResponse == null) {
				return super.shouldInterceptRequest(view, request);
			} else {
				return resourceResponse;
			}

		}
		return super.shouldInterceptRequest(view, request);
	}

	@Override
	public void onLoadResource(WebView view, String url) {
		super.onLoadResource(view, url);
	}

	public WebResourceResponse returnData() {
		try {
			InputStream localCopy;
			WebResourceResponse response = null;
			localCopy = mContext.getAssets().open("angular.min.js");
			response = new WebResourceResponse("application/javascript",
					"UTF-8", localCopy);
			return response;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler,
			SslError error) {
		handler.proceed();
	}

	public WebResourceResponse returnData(final String url) {
		String md5URL = YUtils.md5(url);
		String appCacheDir =JS_CACHE_PATH;
		File file1 = new File(appCacheDir);
		if (!file1.exists()) {
			file1.mkdir();
		}
		// 读取缓存的html页面
		final File file = new File(appCacheDir + File.separator + md5URL);
		if (file.exists()) {
			FileInputStream fileInputStream = null;
			try {
				fileInputStream = new FileInputStream(file);
				return new WebResourceResponse(
						YUtils.readBlock(fileInputStream),
						YUtils.readBlock(fileInputStream), fileInputStream);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return null;
		}
		new Thread(new Runnable() {

			@Override
			public void run() {
				InputStream uristream = null;
				FileOutputStream output = null;
				try {
					if (url != null && url.equals("wvjbscheme")) {
						return;
					}
					URL uri = new URL(url);
					URLConnection connection = uri.openConnection();
					uristream = connection.getInputStream();
					String contentType = connection.getContentType();
					String mimeType = "";
					String encoding = "";
					if (contentType != null && !"".equals(contentType)) {
						if (contentType.indexOf(";") != -1) {
							String[] args = contentType.split(";");
							mimeType = args[0];
							String[] args2 = args[1].trim().split("=");
							if (args.length == 2
									&& args2[0].trim().toLowerCase()
											.equals("charset")) {
								encoding = args2[1].trim();
							} else {
								encoding = "utf-8";
							}
						} else {
							mimeType = contentType;
							encoding = "utf-8";
						}
					}

					output = new FileOutputStream(file);
					int read_len;
					byte[] buffer = new byte[1024];

					YUtils.writeBlock(output, mimeType);
					YUtils.writeBlock(output, encoding);
					while ((read_len = uristream.read(buffer)) > 0) {
						output.write(buffer, 0, read_len);
					}
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						if (output != null)
							output.close();
						if (uristream != null)
							uristream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}).start();
		return null;
	}
}
