package pl.edu.agh.student.adlyclient.beacon;

import android.app.Service;
import android.content.Intent;
import android.content.res.Resources;
import android.os.IBinder;
import android.util.Log;

import com.kontakt.sdk.android.ble.connection.OnServiceReadyListener;
import com.kontakt.sdk.android.ble.manager.ProximityManager;
import com.kontakt.sdk.android.ble.manager.ProximityManagerContract;
import com.kontakt.sdk.android.ble.manager.listeners.IBeaconListener;
import com.kontakt.sdk.android.common.KontaktSDK;
import com.kontakt.sdk.android.common.log.LogLevel;
import com.kontakt.sdk.android.common.profile.IBeaconDevice;
import com.kontakt.sdk.android.common.profile.IBeaconRegion;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.Request;
import pl.edu.agh.student.adlyclient.BuildConfig;
import pl.edu.agh.student.adlyclient.R;
import pl.edu.agh.student.adlyclient.UuidService;
import pl.edu.agh.student.adlyclient.config.Constants;
import pl.edu.agh.student.adlyclient.helpers.AdlyUrlHelper;
import pl.edu.agh.student.adlyclient.helpers.SharedPreferenceHelper;
import pl.edu.agh.student.adlyclient.networking.AsyncGet;

public class BeaconMonitorService extends Service {

    private ProximityManagerContract proximityManager;
    private int beaconSyncTime;
    @Override
    public void onCreate() {
        super.onCreate();

        final Resources r = getResources();
        beaconSyncTime = r.getInteger(R.integer.beacon_period_milis);

        KontaktSDK.initialize(getApplicationContext())
                .setDebugLoggingEnabled(BuildConfig.DEBUG)
                .setLogLevelEnabled(LogLevel.DEBUG, true);
        proximityManager = new ProximityManager(getApplicationContext());

        proximityManager.connect(new OnServiceReadyListener() {
            @Override
            public void onServiceReady() {
                proximityManager.startScanning();
            }
        });

        proximityManager.setIBeaconListener(new IBeaconListener() {

            private String lastUid;
            private Long lastUidTime;

            @Override
            public void onIBeaconDiscovered(IBeaconDevice iBeacon, IBeaconRegion region) {
                Log.d(Constants.TAG, "Beacon discovered: " + iBeacon.getUniqueId());
            }

            @Override
            public void onIBeaconsUpdated(List<IBeaconDevice> iBeacons, IBeaconRegion region) {

                IBeaconDevice closest = Collections.min(iBeacons, comparator);

                String storedUuid = SharedPreferenceHelper.getSharedPreferenceString(getApplicationContext(), "adly.uuid", null);
                if(storedUuid == null){
                    return;
                }

                if(closest.getDistance() > r.getInteger(R.integer.beacon_minimum_distance)){
                    Log.i(Constants.TAG, "Closest beacon is too far " + closest.getDistance() + " " + closest.getUniqueId());
                    return;
                }

                if(!closest.getUniqueId().equals(lastUid) || System.currentTimeMillis() - lastUidTime > beaconSyncTime) {

                    lastUid = closest.getUniqueId();
                    lastUidTime = System.currentTimeMillis();


                    Request request = new Request.Builder()
                            .url(AdlyUrlHelper.getEndpoint(getApplicationContext()) + Constants.BEACON_SYNC_REQUEST_URL +
                                    "?uuid=" + UuidService.getInstance(getApplicationContext()).getUuid() +
                                    "&uid=" + closest.getUniqueId() +
                                    "&major=" + closest.getMajor() +
                                    "&minor=" + closest.getMinor() +
                                    "&puuid=" + closest.getProximityUUID())
                            .get()
                            .build();

                    AsyncGet.execute(request);

                }

                for (IBeaconDevice iBeacon : iBeacons) {
                    Log.i(Constants.TAG, "Beacon updated: " + iBeacon.getUniqueId());
                }
            }

            private final Comparator<IBeaconDevice> comparator = new Comparator<IBeaconDevice>() {
                @Override
                public int compare(IBeaconDevice lhs, IBeaconDevice rhs) {
                    if (lhs.getDistance() > rhs.getDistance()) {
                        return 1;
                    } else if (rhs.getDistance() > lhs.getDistance()) {
                        return -1;
                    }
                    return 0;
                }
            };

            @Override
            public void onIBeaconLost(IBeaconDevice iBeacon, IBeaconRegion region) {
                Log.d(Constants.TAG, "Beacon lost");
            }

        });

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        proximityManager.disconnect();
        proximityManager = null;
        super.onDestroy();
    }


}
