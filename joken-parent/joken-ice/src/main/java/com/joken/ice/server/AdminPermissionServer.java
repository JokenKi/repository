package com.joken.ice.server;

import Glacier2._PermissionsVerifierDisp;
import Ice.Application;
import Ice.Current;
import Ice.StringHolder;

/**
 * 注册中心管理登录验证服务
 * 
 * @author wangby
 * @date 2016年5月24日上午10:18:55
 */
public class AdminPermissionServer extends Application {

	class PermissionsVerifierI extends _PermissionsVerifierDisp {
		private static final long serialVersionUID = -7124389182613149294L;

		@Override
		public boolean checkPermissions(String userId, String password,
				StringHolder reason, Current current) {
			if (!userId.equalsIgnoreCase("admin")
					|| !password.equals("lDif4ZbLM6Q7AWvY")) {
				return false;
			}
			return true;
		}
	}

	@Override
	public int run(String[] arg0) {
		if (arg0.length > 0) {
			System.err.println(appName() + ": too many arguments");
			return 1;
		}
		Ice.ObjectAdapter adapter = communicator().createObjectAdapter(
				"AdminServer");
		adapter.add(new PermissionsVerifierI(), communicator()
				.stringToIdentity("AdminPermissionVerifier"));
		adapter.activate();
		communicator().waitForShutdown();
		return 0;
	}

	public static void main(String[] args) {
		AdminPermissionServer server = new AdminPermissionServer();
		server.main("AdminPermissionServer", args, "adminserver.conf");
	}
}
