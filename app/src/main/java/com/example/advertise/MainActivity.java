package com.example.advertise;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class MainActivity extends AppCompatActivity {
    // Banner Add
    private AdView mAdView;
    // Interstitial Add
    private InterstitialAd mInterstitialAd;
    private Button btnInterstitial;
    // Rewarded Add
    private RewardedAd mRewardedAd;
    private final String TAG = "MainActivity";
    private Button btnRewards;

    SharedPreferences sharedPreferences;
    public static final int REQUEST_CODE = 33;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnInterstitial = findViewById(R.id.btn_interstitial);
        btnRewards = findViewById(R.id.btn_rewards);

        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


        // Initialize our mobile adds so we can implement the type of banner we want to make..
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });


        // Load the BANNER Add
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        // INTERSTITIAL Add
        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        Log.i(TAG, "onAdLoaded");
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
//                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });

        btnInterstitial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitialAd != null) {
                    mInterstitialAd.show(MainActivity.this);
//                    Intent intent = new Intent(MainActivity.this, InterstitialAddActivity.class);
//                    startActivity(intent);
                } else {
                    Log.d("TAG", "The interstitial ad wasn't ready yet.");
                }
            }
        });

        // REWARD Add
//        AdRequest adRequest = new AdRequest.Builder().build();
        // MY NUMBER RewardedAd.load(this, "ca-app-pub-5550814697393931/7694046869",
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });
        // Here we load the adds on button click
        btnRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisplayRewardedAds();
            }
        });

        // Shared Preferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        loadChanges();
    }

        // Load The Changes
        public void loadChanges(){
            TextView intro = findViewById(R.id.tv_intro);
            Button earnRewards = findViewById(R.id.btn_rewards);
            Button interstitialAdd = findViewById(R.id.btn_interstitial);
            RelativeLayout mainBackground = findViewById(R.id.main_layout);

//            RelativeLayout mainBackground = findViewById(R.id.parentView);
//            TextView world = findViewById(R.id.tv_world_news);
//            TextView canada = findViewById(R.id.tv_can_news);
//            ImageButton imgWorldButton = findViewById(R.id.ib_worldNews);
//            ImageButton imgSportButton = findViewById(R.id.ib_sports);
//            ImageButton imgCanadaButton = findViewById(R.id.ib_canNews);


            String fontSize = sharedPreferences.getString(getString(R.string.text_size), "tst");
            String fontColour = sharedPreferences.getString(getString(R.string.font_color), "tst");
            String backGroundColor = sharedPreferences.getString(getString(R.string.dark_mode), "tst");
            String changeImages = sharedPreferences.getString(getString(R.string.change_images), "tst");



            if(fontSize.equals("big")){
                intro.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
//                world.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
//                canada.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
            }
            else{
                intro.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//                world.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
//                canada.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            }

            if(fontColour.equals("yellow")){
                intro.setTextColor(Color.parseColor("#0000FF"));
//                world.setTextColor(Color.parseColor("#0000FF"));
//                canada.setTextColor(Color.parseColor("#0000FF"));
            }
//        else if(fontColour.equals("pink")){
            else{
                intro.setTextColor(Color.parseColor("#000000"));
//                world.setTextColor(Color.parseColor("#000000"));
//                canada.setTextColor(Color.parseColor("#000000"));
            }

            if(backGroundColor.equals("dark")){
//                mainBackground.setBackgroundColor(Color.parseColor("#72726e"));

                mainBackground.setBackgroundResource(R.drawable.background_2);

//                imgCanadaButton.setBackgroundColor(Color.parseColor("#72726e"));
//                imgSportButton.setBackgroundColor(Color.parseColor("#72726e"));
//                imgWorldButton.setBackgroundColor(Color.parseColor("#72726e"));

            }
            else{
//                mainBackground.setBackgroundColor(Color.parseColor("#80ff80"));

                mainBackground.setBackgroundResource(R.drawable.background_1);

//                imgCanadaButton.setBackgroundColor(Color.parseColor("#80ff80"));
//                imgSportButton.setBackgroundColor(Color.parseColor("#80ff80"));
//                imgWorldButton.setBackgroundColor(Color.parseColor("#80ff80"));
            }
//            if(changeImages.equals("change")){
//                imgCanadaButton.setImageResource(R.drawable.rocket);
//                imgSportButton.setImageResource(R.drawable.rocket);
//                imgWorldButton.setImageResource(R.drawable.rocket);
//            }
//        else{
//            imgCanadaButton.setImageResource(R.drawable.can_news);
//            imgSportButton.setImageResource(R.drawable.sports);
//            imgWorldButton.setImageResource(R.drawable.world_news);
//        }

        }


    private void DisplayRewardedAds(){
        // directs us to the next activity and displays a reward video
        Intent i = new Intent(this, InterstitialAddActivity.class);
        startActivity(i);

        // display the add
        if (mRewardedAd != null) {
            Activity activityContext = MainActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();

                    Log.d(TAG, "The user earned." + rewardAmount);
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the options menu
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }


    private void DisplayRewardedAdsSettings(){
        // directs us to the next activity and displays a reward video
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivityForResult(intent, REQUEST_CODE);

        // display the add
        if (mRewardedAd != null) {
            Activity activityContext = MainActivity.this;
            mRewardedAd.show(activityContext, new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                    // Handle the reward.
                    int rewardAmount = rewardItem.getAmount();
                    String rewardType = rewardItem.getType();

                    Log.d(TAG, "The user earned." + rewardAmount);
                }
            });
        } else {
            Log.d(TAG, "The rewarded ad wasn't ready yet.");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.to_options){
            Toast.makeText(this, "Go to options menu", Toast.LENGTH_SHORT).show();
//          Comment Out The two lines when uncommenting the line to display the add!!
//            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivityForResult(intent, REQUEST_CODE);
//            Uncomment this line to display add!
            DisplayRewardedAdsSettings();
        }
        return super.onOptionsItemSelected(item);
    }
}




