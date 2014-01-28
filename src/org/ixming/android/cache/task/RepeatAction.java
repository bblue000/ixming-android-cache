package org.ixming.android.cache.task;

/**
 * 
 * 加载Bitmap时，遇到重复时的动作
 * 
 * @author Yin Yong
 */
public enum RepeatAction {

	/**
	 * 不处理新添加的
	 */
	IgnoreNewOne,
	
	/**
	 * 使用新的替换原有的
	 */
	Replace,
	
	/**
	 * 都需要处理
	 */
	Append;
	
}
