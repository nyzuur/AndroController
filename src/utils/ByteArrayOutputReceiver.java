package utils;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

import com.android.ddmlib.IShellOutputReceiver;

/**
 * This class implements a direct bytebuffer to shell output 
 * with initial capacity of 1 MB
 */
public class ByteArrayOutputReceiver implements IShellOutputReceiver {
	byte[] data = new byte[1024*1024*3];//3 MB
	ByteBuffer b = ByteBuffer.wrap(data); 
	AtomicBoolean isCancelled = new AtomicBoolean(false);
	
	public ByteArrayOutputReceiver(){
	}
	
	public ByteBuffer getOutput(){
		return b;
	}
	@Override
	public void addOutput(byte[] data, int offset, int length) {
		if(!isCancelled()){
			b.put(data, offset, length);
		}
	}

	@Override
	public void flush() {
	}

	@Override
	public boolean isCancelled() {
		return isCancelled.get();
	}

}
