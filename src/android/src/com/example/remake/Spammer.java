package com.example.remake;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import android.util.Log;

public class Spammer implements Runnable {

	private Semaphore s;
	private Socket sock;
	private PrintWriter out;
	private int x, y;
	private boolean enabled;

	public Spammer() {
		s = new Semaphore(1);
		enabled = false;
		x = y = 0;
		new Thread(this).start();

	}

	public void enable(boolean e) {
		try {
			s.acquire();
			enabled = e;
			s.release();
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	public void setSpeed(int sx, int sy) {
		try {
			s.acquire();
			x = sx;
			y = sy;
			s.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void click(int button, int nClick) {
		try {
			s.acquire();
			try {
				out.println("C " + nClick + " " + button);
			} catch (Exception e) {
				e.printStackTrace();
			}

			s.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void sendText(String s) {
		Scanner scanner = new Scanner(s);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			out.print("T ");
			/** DO we have to put a newline? */
			if (scanner.hasNextLine()) {
				out.print("1 ");
			} else {
				out.print("0 ");
			}
			out.println(line);

		}
		scanner.close();
	}

	@Override
	public void run() {
		try {

			sock = new Socket("192.168.1.102", 9999);
			out = new PrintWriter(sock.getOutputStream(), true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		long millis = System.currentTimeMillis(), oldmillis = millis;
		while (true) {
			try {
				if (millis - oldmillis < 100) {
					Thread.sleep(100 - (millis - oldmillis));
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				s.acquire();
				if (enabled) {
					try {
						out.println("R " + x + " " + y);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				s.release();
				oldmillis = millis;
				millis = System.currentTimeMillis();

			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
