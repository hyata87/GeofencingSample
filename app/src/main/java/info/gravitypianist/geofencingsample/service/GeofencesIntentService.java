package info.gravitypianist.geofencingsample.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.google.android.gms.location.LocationServices;

import info.gravitypianist.geofencingsample.MyActivity;
import info.gravitypianist.geofencingsample.R;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class GeofencesIntentService extends IntentService {

    private static final String TAG = GeofencesIntentService.class.getSimpleName();

    public GeofencesIntentService() {
        super("GeofencesIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "onHandleIntent");

        GeofencingEvent event = GeofencingEvent.fromIntent(intent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
        //タイトル
        builder.setContentTitle("東京ドームに近づきました");
        //テキスト(ここでは東京ドームまでの距離を取得)
        Location location = event.getTriggeringLocation();
        float[] result = new float[3];
        Location.distanceBetween(35.705640, 139.751891, location.getLatitude(), location.getLongitude(), result);
        builder.setContentText("東京ドームまで" + result[0] + "m");
        builder.setSmallIcon(R.drawable.ic_launcher);
        //遷移先
        Intent activityIntent = new Intent(this, MyActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, activityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        builder.setAutoCancel(true);
        //
        NotificationManager manager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
