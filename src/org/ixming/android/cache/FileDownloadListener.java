package org.ixming.android.cache;

public interface FileDownloadListener {

	void onLoadSuccess();
	
	void onPostProgress(int progress);
	
	void onLoadFailed(int failCode);
}
