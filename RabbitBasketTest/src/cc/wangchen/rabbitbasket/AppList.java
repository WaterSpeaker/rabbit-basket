package cc.wangchen.rabbitbasket;

public final class AppList {
	// Constants
	public static final int FAST_APP_NUMBER = 3;
	public static final int SLOW_APP_NUMBER = 3;
	public static final int TASK_APP_NUMBER = 150;
	public static final int TRAIL_TASK_APP_NUMBER = 60;
	public static String[] SLOW_APP_LIST = { "com.facebook.katana", // Facebook
			"com.google.android.youtube", // YouTube
			"com.android.vending" // Google Plays
	};
	public static String[][] FAST_APP_LIST = { { "com.whatsapp", // Whatsapp
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.dropbox.android", // dropbox
			"com.google.android.apps.maps", // Google Maps
			"com.twitter.android" // Twitter
	}, { "com.evernote", // Evernote
			"com.google.android.gm", // Gmail
			"com.weather.Weather" // the weather channel
	}, { "com.whatsapp", // Whatsapp
			"com.google.android.apps.plus", // Google +
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.weather.Weather", // the weather channel
			"com.netflix.mediaclient", // Netflix
			"com.instagram.android" // Instagram
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.snapchat.android" // Snapchat
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.pinterest" // Pinterest
	}, { "com.google.android.apps.plus", // Google +
			"com.google.android.apps.maps", // Google Maps
			"com.skype.raider" // Skype
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // , Facebook Messenger
			"com.google.android.gm" // Gmail
	}, { "com.evernote", // Evernote
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.google.android.apps.plus", // Google +
			"com.google.android.gm", // Gmail
			"com.evernote" // Evernote
	}, { "com.whatsapp", // Whatsapp
			"com.google.android.apps.maps", // Google Maps
			"com.skype.raider" // Skype
	}, { "com.instagram.android", // Instagram
			"com.dropbox.android", // , Dropbox
			"com.whatsapp" // WhatsApp
	}, { "com.dropbox.android", // Dropbox
			"com.google.android.apps.maps", // Google Maps
			"com.whatsapp" // WhatsApp
	}, { "com.whatsapp", // WhatsApp
			"com.facebook.orca", // Facebook Messenger
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.pinterest" // Pinterest
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.google.android.apps.maps", // Google Maps
			"com.twitter.android", // Twitter
			"com.snapchat.android" // Snapchat
	}, { "com.evernote", // Evernote
			"com.whatsapp", // WhatsApp
			"com.netflix.mediaclient" // Netflix
	}, { "com.evernote", // Evernote
			"com.twitter.android", // Twitter
			"com.weather.Weather" // the weather channel
	}, { "com.evernote", // Evernote
			"com.google.android.gm", // Gmail
			"com.instagram.android" // Instagram
	}, { "com.whatsapp", // WhatsApp
			"com.evernote", // Evernote
			"com.weather.Weather" // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.google.android.gm", // Gmail
			"com.evernote" // Evernote
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.weather.Weather" // the weather channel
	}, { "com.google.android.apps.maps", // Google Maps
			"com.netflix.mediaclient", // Netflix
			"com.whatsapp" // WhatsApp
	}, { "com.whatsapp", // WhatsApp,
			"com.skype.raider", // Skype,
			"com.pinterest" // Pinterest
	}, { "com.google.android.apps.maps", // Google Maps
			"com.twitter.android", // Netflix
			"com.snapchat.android" // snapchat
	}, { "com.google.android.gm", // Gmail
			"com.facebook.orca", // Facebook Messenger
			"com.dropbox.android" // Dropbox
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.instagram.android" // Instagram
	}, { "com.evernote", // Evernote
			"com.whatsapp", // WhatsApp
			"com.facebook.orca" // Facebook Messenger
	}, { "com.google.android.apps.maps", // Google Maps
			"com.evernote", // Evernote
			"com.instagram.android" // Instagram
	}, { "com.google.android.apps.maps", // Google Maps
			"com.facebook.orca", // Facebook Messenger
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.evernote", // Evernote
			"com.dropbox.android", // Dropbox
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.weather.Weather", // the weather channel
			"com.facebook.orca" // Facebook Messenger
	}, { "com.com.google.android.gm", // Gmail
			"com.whatsapp", // WhatsApp
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.facebook.orca" // Facebook Messenger
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.apps.plus", // Google +
			"com.netflix.mediaclient" // Netflix
	}, { "com.instagram.android", // Instagram
			"com.evernote", // Evernote
			"com.snapchat.android" // Snapchat
	}, { "com.whatsapp", // WhatsApp
			"com.skype.raider", // Skype,
			"com.google.android.apps.plus" // Google +
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.gm", // Gmail
			"com.twitter.android" // Twitter
	}, { "com.weather.Weather", // the weather channel
			"com.skype.raider", // Skype
			"com.whatsapp" // WhatsApp
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.weather.Weather" // the weather channel
	}, { "com.google.android.apps.maps", // Google Maps
			"com.snapchat.android", // Snapchat
			"com.skype.raider" // Skype
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.pinterest" // Pinterest
	}, { "com.google.android.apps.maps", // Google Maps
			"com.dropbox.android", // Dropbox
			"com.google.android.apps.plus" // Google +
	}, { "com.whatsapp", // WhatsApp
			"com.evernote", // Evernote
			"com.skype.raider" // Skype
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.apps.plus", // Google +
			"com.pinterest" // Pinterest
	}, { "com.dropbox.android", // Dropbox
			"com.twitter.android", // Twitter
			"com.weather.Weather" // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.facebook.orca", // Facebook Messenger
			"com.netflix.mediaclient" // Netflix
	}, { "com.google.android.apps.maps", // Google Maps
			"com.whatsapp", // WhatsApp
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.google.android.apps.plus", // Google +
			"com.dropbox.android" // Dropbox
	}, { "com.snapchat.android", // Snapchat
			"com.instagram.android", // Instagram
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.whatsapp", // WhatsApp
			"com.snapchat.android", // Snapchat
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.instagram.android", // Instagram
			"com.twitter.android", // , Twitter
			"com.google.android.apps.plus" // Google +
	}, { "com.google.android.apps.maps", // Google Maps
			"com.evernote", // Evernote
			"com.instagram.android" // Instagram
	}, { "com.google.android.gm", // Gmail
			"com.netflix.mediaclient", // Netflix
			"com.dropbox.android" // Dropbox
	}, { "com.google.android.apps.maps", // Google Maps
			"com.amazon.mShop.android.shopping", // Amazon Shopping
			"com.pinterest" // Pinterest
	}, { "com.evernote", // Evernote
			"com.amazon.mShop.android.shopping", // Amazon Shopping
			"com.netflix.mediaclient" // Netflix
	}, { "com.whatsapp", // WhatsApp
			"com.twitter.android", // Twitter
			"com.snapchat.android" // Snapchat
	}, { "com.evernote", // Evernote
			"com.dropbox.android", // Dropbox
			"com.snapchat.android" // Snapchat
	}, { "com.whatsapp", // WhatsApp
			"com.google.android.gm", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.dropbox.android", // Dropbox
			"com.google.android.apps.maps", // Google Maps
			"com.twitter.android" // Twitter
	}, { "com.evernote", // Evernote
			"com.google.android.gm", // Gmail
			"com.weather.Weather " // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.google.android.apps.plus", // Google +
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.instagram.android", // Instagram
			"com.weather.Weather", // the weather channel
			"com.netflix.mediaclient" // Netflix
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.snapchat.android" // Snapchat
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.pinterest" // Pinterest
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.apps.plus", // Google +
			"com.skype.raider" // Skype
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.google.android.gm" // Gmail
	}, { "com.evernote", // Evernote
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.evernote", // Evernote
			"com.google.android.gm", // Gmail
			"com.google.android.apps.plus" // Google +
	}, { "com.whatsapp", // WhatsApp
			"com.skype.raider", // Skype
			"com.google.android.apps.maps" // Google Maps
	}, { "com.whatsapp", // WhatsApp
			"com.dropbox.android", // Dropbox
			"com.instagram.android" // Instagram
	}, { "com.whatsapp", // WhatsApp
			"com.dropbox.android", // Dropbox
			"com.google.android.apps.maps" // Google Maps
	}, { "com.whatsapp", // WhatsApp
			"com.facebook.orca", // Facebook Messenger
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.pinterest" // Pinterest
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.google.android.apps.maps", // Google Maps
			"com.twitter.android", // Twitter
			"com.snapchat.android" // Snapchat
	}, { "com.evernote", // Evernote
			"com.whatsapp", // WhatsApp
			"com.netflix.mediaclient" // Netflix
	}, { "com.evernote", // Evernote
			"com.twitter.android", // Twitter
			"com.weather.Weather " // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.dropbox.android", // Dropbox
			"com.google.android.apps.maps", // Google Maps
			"com.twitter.android" // Twitter
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.pinterest" // Pinterest
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.instagram.android" // Instagram
	}, { "com.netflix.mediaclient", // Netflix
			"com.weather.Weather", // the weather channel
			"com.instagram.android" // Instagram
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.snapchat.android" // Snapchat
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.pinterest" // Pinterest
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.apps.plus", // Google +
			"com.skype.raider" // Skype
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.google.android.gm" // Gmail
	}, { "com.evernote", // Evernote
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.google.android.gm", // Gmail
			"com.google.android.apps.plus", // Google +
			"com.evernote" // Evernote
	}, { "com.whatsapp", // WhatsApp
			"com.skype.raider", // Skype
			"com.google.android.apps.maps" // Google Maps
	}, { "com.whatsapp", // WhatsApp
			"com.instagram.android", // Instagram
			"com.dropbox.android" // Dropbox
	}, { "com.whatsapp", // WhatsApp
			"com.google.android.apps.maps", // Google Maps
			"com.dropbox.android" // Dropbox
	}, { "com.whatsapp", // WhatsApp
			"com.facebook.orca", // Facebook Messenger
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.pinterest" // Pinterest
	}, { "com.whatsapp", // WhatsApp
			"com.evernote", // Evernote
			"com.facebook.orca" // Facebook Messenger
	}, { "com.google.android.apps.maps", // Google Maps
			"com.evernote", // Evernote
			"com.instagram.android" // Instagram
	}, { "com.google.android.apps.maps", // Google Maps
			"com.facebook.orca", // Facebook Messenger
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.evernote", // Evernote
			"com.google.android.gm", // Gmail
			"com.weather.Weather" // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.google.android.apps.plus", // Google +
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.whatsapp", // WhatsApp
			"com.evernote", // Evernote
			"com.weather.Weather" // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.evernote", // Evernote
			"com.google.android.gm" // Gmail
	}, { "com.weather.Weather", // the weather channel
			"com.evernote", // Evernote
			"com.facebook.orca" // Facebook Messenger
	}, { "com.google.android.apps.maps", // Google Maps
			"com.netflix.mediaclient", // Netflix
			"com.whatsapp" // WhatsApp
	}, { "com.pinterest", // Pinterest
			"com.skype.raider", // Skype
			"com.whatsapp" // WhatsApp
	}, { "com.google.android.apps.maps", // Google Maps
			"com.netflix.mediaclient", // Netflix
			"com.google.android.gm" // Gmail
	}, { "com.dropbox.android", // Dropbox
			"com.facebook.orca", // Facebook Messenger
			"com.google.android.gm" // Gmail
	}, { "com.dropbox.android", // Dropbox
			"com.instagram.android", // Instagram
			"com.google.android.gm" // Gmail
	}, { "com.evernote", // Evernote
			"com.instagram.android", // Instagram
			"com.google.android.apps.maps" // Google Maps
	}, { "com.dropbox.android", // Dropbox
			"com.netflix.mediaclient", // Netflix
			"com.google.android.gm" // Gmail
	}, { "com.google.android.apps.maps", // Google Maps
			"com.amazon.mShop.android.shopping", // Amazon Shopping
			"com.pinterest" // Pinterest
	}, { "com.evernote", // Evernote
			"com.dropbox.android", // Dropbox
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.weather.Weather", // the weather channel
			"com.facebook.orca" // Facebook Messenger
	}, { "com.google.android.gm", // Gmail
			"com.whatsapp", // WhatsApp
			"com.twitter.android" // Twitter
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.facebook.orca" // Facebook Messenger
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.apps.plus", // Google +
			"com.netflix.mediaclient" // Netflix
	}, { "com.evernote", // Evernote
			"com.instagram.android", // Instagram
			"com.snapchat.android" // Snapchat
	}, { "com.whatsapp", // WhatsApp
			"skype.raider", // Skype
			"com.google.android.apps.plus" // Google +
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.google.android.apps.maps", // Google Maps
			"com.twitter.android", // Twitter
			"com.snapchat.android" // Snapchat
	}, { "com.evernote", // Evernote
			"com.whatsapp", // WhatsApp
			"com.netflix.mediaclient" // Netflix
	}, { "com.evernote", // Evernote
			"com.twitter.android", // Twitter
			"com.weather.Weather" // the weather channel
	}, { "com.evernote", // Evernote
			"com.google.android.gm", // Gmail
			"com.instagram.android" // Instagram
	}, { "com.google.android.apps.maps", // Google Maps
			"com.dropbox.android", // Dropbox
			"com.google.android.apps.plus" // Google +
	}, { "com.whatsapp", // WhatsApp
			"com.evernote", // Evernote
			"skype.raider" // Skype
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.apps.plus", // Google +
			"com.pinterest" // Pinterest
	}, { "com.dropbox.android", // Dropbox
			"com.twitter.android", // Twitter
			"com.weather.Weather" // the weather channel
	}, { "com.whatsapp", // WhatsApp
			"com.netflix.mediaclient", // Netflix
			"com.facebook.orca" // Facebook Messenger
	}, { "com.whatsapp", // WhatsApp
			"com.twitter.android", // Twitter
			"com.google.android.apps.maps" // Google Maps
	}, { "com.google.android.gm", // Gmail
			"com.google.android.apps.plus", // Google +
			"com.dropbox.android" // Dropbox
	}, { "com.instagram.android", // Instagram
			"com.snapchat.android", // Snapchat
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.whatsapp", // WhatsApp
			"com.snapchat.android", // Snapchat
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.instagram.android", // Instagram
			"com.twitter.android", // Twitter
			"com.google.android.apps.plus" // Google +
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.google.android.gm" // Gmail
	}, { "com.evernote", // Evernote
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.google.android.apps.plus", // Google +
			"com.google.android.apps.maps", // Google Maps
			"com.pinterest" // Pinterest
	}, { "com.evernote", // Evernote
			"com.netflix.mediaclient", // Netflix
			"com.amazon.mShop.android.shopping" // Amazon Shopping
	}, { "com.whatsapp", // WhatsApp
			"com.twitter.android", // Twitter
			"com.snapchat.android" // Snapchat
	}, { "com.evernote", // Evernote
			"com.dropbox.android", // Dropbox
			"com.snapchat.android" // Snapchat
	}, { "com.google.android.apps.maps", // Google Maps
			"com.google.android.gm", // Gmail
			"com.twitter.android" // Twitter
	}, { "com.whatsapp", // WhatsApp
			"com.skype.raider", // Skype
			"com.weather.Weather" // the weather channel
	}, { "com.google.android.gm", // Gmail
			"com.instagram.android", // Instagram
			"com.weather.Weather" // the weather channel
	}, { "com.google.android.apps.maps", // Google Maps
			"com.snapchat.android", // Snapchat
			"com.skype.raider" // Skype
	}, { "com.evernote", // Evernote
			"com.facebook.orca", // Facebook Messenger
			"com.pinterest" // Pinterest
	}, { "com.google.android.gm", // Gmail
			"com.dropbox.android", // Dropbox
			"com.instagram.android" // Instagram
	}, { "com.evernote", // Evernote
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.google.android.gm", // Gmail
			"com.netflix.mediaclient", // Netflix
			"com.dropbox.android" // Dropbox
	}, { "com.evernote", // Evernote
			"com.google.android.apps.maps", // Google Maps
			"com.instagram.android" // Instagram
	}, { "com.google.android.apps.plus", // Google +
			"com.google.android.apps.maps", // Google Maps
			"com.pinterest" // Pinterest
	} };
	public static String[][] TASK_APP_LIST = {
			{ "Youtube", "com.google.android.youtube" },
			{ "Twitter", "com.twitter.android" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Whatsapp", "com.whatsapp" }, { "Pinterest", "com.pinterest" },
			{ "Snapchat", "com.snapchat.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Instagram", "com.instagram.android" },
			{ "Amazon Shopping", "com.amazon.mShop.android.shopping" },
			{ "Google +", "com.google.android.apps.plus" },
			{ "Skype", "com.skype.raider" }, { "Evernote", "com.evernote" },
			{ "WhatsApp", "com.whatsapp" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "Dropbox", "com.dropbox.android" },
			{ "Evernote", "com.evernote" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Netflix", "com.netflix.mediaclient" },
			{ "Facebook", "com.facebook.katana" },
			{ "Skype", "com.skype.raider" },
			{ "Gmail", "com.google.android.gm" },
			{ "the weather channel", "com.weather.Weather" },
			{ "Instagram", "com.instagram.android" },
			{ "Netflix", "com.netflix.mediaclient" },
			{ "Facebook", "com.facebook.katana" },
			{ "Snapchat", "com.snapchat.android" },
			{ "Google +", "com.google.android.apps.plus" },
			{ "Instagram", "com.instagram.android" },
			{ "Gmail", "com.google.android.gm" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Amazon Shopping", "com.amazon.mShop.android.shopping" },
			{ "Facebook", "com.facebook.katana" },
			{ "Google Play", "com.android.vending" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Facebook", "com.facebook.katana" },
			{ "Twitter", "com.twitter.android" },
			{ "Evernote", "com.evernote" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "Dropbox", "com.dropbox.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "Gmail", "com.google.android.gm" },
			{ "WhatsApp", "com.whatsapp" }, { "Evernote", "com.evernote" },
			{ "Facebook", "com.facebook.katana" },
			{ "Google Play", "com.android.vending" },
			{ "Pinterest", "com.pinterest" },
			{ "Google Play", "com.android.vending" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Facebook", "com.facebook.katana" },
			{ "Gmail", "com.google.android.gm" },
			{ "Google Play", "com.android.vending" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Instagram", "com.instagram.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "WhatsApp", "com.whatsapp" },
			{ "Google Play", "com.android.vending" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Evernote", "com.evernote" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Whatsapp", "com.whatsapp" },
			{ "Facebook", "com.facebook.katana" },
			{ "Whatsapp", "com.whatsapp" },
			{ "Google Play", "com.android.vending" },
			{ "evernote", "com.evernote" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Facebook", "com.facebook.katana" },
			{ "Twitter", "com.twitter.android" },
			{ "evernote", "com.evernote" },
			{ "Gmail", "com.google.android.gm" },
			{ "Google +", "com.google.android.apps.plus" },
			{ "Instagram", "com.instagram.android" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Facebook", "com.facebook.katana" },
			{ "Dropbox", "com.dropbox.android" },
			{ "Youtube", "com.google.android.youtube" },
			{ "the weather channel", "com.weather.Weather" },
			{ "Google Play", "com.android.vending" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Dropbox", "com.dropbox.android" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "Whatsapp", "com.whatsapp" }, { "Pinterest", "com.pinterest" },
			{ "Instagram", "com.instagram.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "Gmail", "com.google.android.gm" },
			{ "Skype", "com.skype.raider" },
			{ "Instagram", "com.instagram.android" },
			{ "Gmail", "com.google.android.gm" },
			{ "Dropbox", "com.dropbox.android" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Facebook", "com.facebook.katana" },
			{ "Instagram", "com.instagram.android" },
			{ "Twitter", "com.twitter.android" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "Google Play", "com.android.vending" },
			{ "Amazon Shopping", "com.amazon.mShop.android.shopping" },
			{ "Twitter", "com.twitter.android" },
			{ "Google +", "com.google.android.apps.plus" },
			{ "Facebook", "com.facebook.katana" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Facebook", "com.facebook.katana" },
			{ "Google Play", "com.android.vending" },
			{ "Pinterest", "com.pinterest" },
			{ "Amazon Shopping", "com.amazon.mShop.android.shopping" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "evernote", "com.evernote" },
			{ "Instagram", "com.instagram.android" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Youtube", "com.google.android.youtube" },
			{ "the weather channel", "com.weather.Weather" },
			{ "Google Play", "com.android.vending" },
			{ "Instagram", "com.instagram.android" },
			{ "Netflix", "com.netflix.mediaclient" },
			{ "Instagram", "com.instagram.android" },
			{ "Whatsapp", "com.whatsapp" },
			{ "Facebook", "com.facebook.katana" },
			{ "Google Play", "com.android.vending" },
			{ "evernote", "com.evernote" },
			{ "the weather channel", "com.weather.Weather" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Snapchat", "com.snapchat.android" },
			{ "Facebook", "com.facebook.katana" },
			{ "Google Maps", "com.google.android.apps.maps" },
			{ "Dropbox", "com.dropbox.android" },
			{ "Whatsapp", "com.whatsapp" },
			{ "Facebook", "com.facebook.katana" },
			{ "Gmail", "com.google.android.gm" },
			{ "Google Play", "com.android.vending" },
			{ "Whatsapp", "com.whatsapp" },
			{ "Google Play", "com.android.vending" },
			{ "Twitter", "com.twitter.android" },
			{ "Youtube", "com.google.android.youtube" },
			{ "Google Play", "com.android.vending" },
			{ "evernote", "com.evernote" },
			{ "Facebook Messenger", "com.facebook.orca" },
			{ "Gmail", "com.google.android.gm" },
			{ "Netflix", "com.netflix.mediaclient" } };
}
