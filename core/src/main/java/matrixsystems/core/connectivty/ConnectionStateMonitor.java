package matrixsystems.core.connectivty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;

import matrixsystems.core.constants.AppConstants;


public class ConnectionStateMonitor extends LiveData<Boolean> {

    private final Context mContext;
    private ConnectivityManager.NetworkCallback networkCallback = null;
    private NetworkReceiver networkReceiver;
    private final ConnectivityManager connectivityManager;

    public ConnectionStateMonitor(Context context) {
        mContext = context;
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            networkCallback = new NetworkCallback(this);
        } else {
            networkReceiver = new NetworkReceiver();
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        updateConnection();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            connectivityManager.registerDefaultNetworkCallback(networkCallback);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest networkRequest = new NetworkRequest.Builder()
                    .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                    .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                    .build();
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback);
        } else {
            mContext.registerReceiver(networkReceiver, new IntentFilter(AppConstants.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback);
        } else {
            mContext.unregisterReceiver(networkReceiver);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    class NetworkCallback extends ConnectivityManager.NetworkCallback {

        private final ConnectionStateMonitor mConnectionStateMonitor;

        public NetworkCallback(ConnectionStateMonitor connectionStateMonitor) {
            mConnectionStateMonitor = connectionStateMonitor;
        }

        @Override
        public void onAvailable(Network network) {
            if (network != null) {
                mConnectionStateMonitor.postValue(true);
            }
        }

        @Override
        public void onLost(Network network) {
            mConnectionStateMonitor.postValue(false);
        }
    }

    private void updateConnection() {
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            postValue(activeNetwork != null && activeNetwork.isConnectedOrConnecting());
        }

    }

    class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AppConstants.CONNECTIVITY_ACTION)) {
                updateConnection();
            }
        }
    }
}
