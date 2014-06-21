package otto;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by Peggy on 2014/6/21.
 * potm@163.com
 */
public class BusProvider {

	public static Bus mBus;

	public static Bus getInstance(){
		if (mBus == null){
			mBus = new Bus(ThreadEnforcer.ANY);
		}
		return mBus;
	}
}
