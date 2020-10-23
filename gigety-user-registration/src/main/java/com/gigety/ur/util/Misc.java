package com.gigety.ur.util;

import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Misc {

	public static void pause(int secs) {
		try {
			
			log.debug("PAUSING THREAD {} for {} seconds.....", Thread.currentThread().getName(), secs);
			long then = System.nanoTime();
			TimeUnit.SECONDS.sleep(secs);
			long now = System.nanoTime();
			long duration = (now - then) / 1_000_000;
			
			log.debug("DONE PAUSING THREAD {} : Time Elapsed: {}", Thread.currentThread().getName(), duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
