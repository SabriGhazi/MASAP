package com.masim.utils;


	public class PS implements Runnable{

		public static DispersionEngine w;
		@Override
		public void run() {
			w.loop();
		}
}
