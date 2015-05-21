package cc.wangchen.rabbitbasket;

public final class AppList {
	// Constants
	public static final int FAST_APP_NUMBER = 3;
	public static final int SLOW_APP_NUMBER = 3;
	public static final int TASK_APP_NUMBER = 8;
	public static String[] SLOW_APP_LIST = { "com.facebook.katana", // Facebook
			"com.google.android.youtube", // YouTube
			"com.android.vending" // Google Plays
	};
	public static String[][] FAST_APP_LIST = {
			{ "com.google.android.googlequicksearchbox", // Google Search
					"com.google.android.gm", // Google Maps
					"com.instagram.android" // Instagram
			}, { "com.google.android.gm", // Google Search
					"com.google.android.gm", // Google Maps
					"com.instagram.android" // Instagram
			}, { "com.instagram.android", // Google Search
					"com.google.android.gm", // Google Maps
					"com.instagram.android" // Instagram
			}, { "com.android.vending", // Google Search
					"com.android.vending", // Google Maps
					"com.android.vending" // Instagram
			}, { "com.google.android.googlequicksearchbox", // Google Search
					"com.google.android.gm", // Google Maps
					"com.instagram.android" // Instagram
			}, { "com.google.android.gm", // Google Search
					"com.google.android.gm", // Google Maps
					"com.instagram.android" // Instagram
			}, { "com.instagram.android", // Google Search
					"com.google.android.gm", // Google Maps
					"com.instagram.android" // Instagram
			}, { "com.android.vending", // Google Search
					"com.android.vending", // Google Maps
					"com.android.vending" // Instagram
			} };
	public static String[][] TASK_APP_LIST = {
			{ "Instagram", "com.instagram.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "Instagram", "com.instagram.android" },
			{ "Google Play", "com.android.vending" },
			{ "Instagram", "com.instagram.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "Instagram", "com.instagram.android" },
			{ "Google Play", "com.android.vending" }};
}
