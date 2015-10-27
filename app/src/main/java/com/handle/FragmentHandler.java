package com.handle;

import com.common.MHandler;
import com.leo.base.activity.fragment.LFragment;
import com.leo.base.entity.LMessage;
import com.leo.base.exception.LLoginException;
import com.leo.base.util.T;

import org.json.JSONException;

public class FragmentHandler extends MHandler {

	public FragmentHandler(LFragment fragment) {
		super(fragment);
	}
	

	protected void onNetWorkExc() {
		T.ss("");
	}

	protected void onParseExc() {
		T.ss("");
	}

	protected void onLoginError() {
		T.ss("");
	}

	protected void onLoginNone() {
		T.ss("");
	}

	protected void onOtherExc() {
		T.ss("");
	}


	public LMessage onNetParse(String result, int requestId)
			throws JSONException, LLoginException {
		LMessage msg = new LMessage();
		msg.setStr(result);
		return msg;
	}

}
