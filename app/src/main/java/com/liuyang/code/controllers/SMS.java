package com.liuyang.code.controllers;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

import com.liuyang.code.R;

import java.io.File;

/**
 * @author Liuyang 2016/2/3.
 */
public class SMS extends BaseFragment {

    private static final String SEND_SMS_ACTION = "com.liuyang.code.controller.SMS_SEND";
    private static final String DELIVERED_SMS_ACTION = "com.liuyang.code.controller.SMS_DELIVERED";
    private static final String SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private SentReceiver sentReceiver;
    private DeliveredReceiver deliveredReceiver;
    private ReceiveSMS receiveSMS;

    @Override
    protected int layoutId() {
        return R.layout.sms;
    }

    @Override
    protected void init() {
        find(R.id.send_sms_via_other_app).setOnClickListener(l -> {
            // send sms
            Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:88888888888"));
            intent.putExtra("sms_body", "Press send to send me");
            startActivity(intent);
        });

        find(R.id.send_mms_via_other_app).setOnClickListener(l -> {
            // send mms
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/jpeg");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File("/sdcard/1.jpg")));
            intent.putExtra("address", "88888888888");
            intent.putExtra("sms_body", "Press see the attached image");
            startActivity(intent);
        });

        find(R.id.send_sms_via_sms_manager).setOnClickListener(l -> {
            sentReceiver = new SentReceiver();
            deliveredReceiver = new DeliveredReceiver();
            host.registerReceiver(sentReceiver, new IntentFilter(SEND_SMS_ACTION));
            host.registerReceiver(deliveredReceiver, new IntentFilter(DELIVERED_SMS_ACTION));
            SmsManager manager = SmsManager.getDefault();
            Intent sent = new Intent(SEND_SMS_ACTION);
            PendingIntent sentPI = PendingIntent.getBroadcast(host.getApplicationContext(), 0, sent, PendingIntent.FLAG_UPDATE_CURRENT);
            Intent delivered = new Intent(SEND_SMS_ACTION);
            PendingIntent deliveredPI = PendingIntent.getBroadcast(host.getApplicationContext(), 0, delivered, PendingIntent.FLAG_UPDATE_CURRENT);
            manager.sendTextMessage("88888888888", null, "send message via SmsManager", sentPI, deliveredPI);
            // if the text is too long, use method below
//            ArrayList<String> messageArray = manager.divideMessage("a long text");
//            ArrayList<PendingIntent> sentIntents = new ArrayList<>();
//            for(int i = 0; i < messageArray.size(); i++) {
//                sentIntents.add(sentPI);
//            }
//            manager.sendMultipartTextMessage("88888888888", null, messageArray, sentIntents, null);
        });
        receiveSMS = new ReceiveSMS();
        host.registerReceiver(receiveSMS, new IntentFilter(SMS_RECEIVED));
    }

    @Override
    protected void refresh() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (sentReceiver != null) {
            host.unregisterReceiver(sentReceiver);
        }

        if (deliveredReceiver != null) {
            host.unregisterReceiver(deliveredReceiver);
        }

        if (receiveSMS != null) {
            host.unregisterReceiver(receiveSMS);
        }
    }

    private class SentReceiver extends BroadcastReceiver {
        private String resultText = "UNKNOWN";

        @Override
        public void onReceive(Context context, Intent intent) {
            switch (getResultCode()) {
                case Activity.RESULT_OK:
                    resultText = "successful";
                    break;
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                    resultText = "failure";
                    break;
                case SmsManager.RESULT_ERROR_RADIO_OFF:
                    resultText = "failure: radio off";
                    break;
                case SmsManager.RESULT_ERROR_NULL_PDU:
                    resultText = "failure: pdu not specified";
                    break;
                case SmsManager.RESULT_ERROR_NO_SERVICE:
                    resultText = "failure: no service";
                    break;
                default:
                    break;
            }
            show(resultText);
        }
    }

    private class DeliveredReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            show("SMS Delivered");
        }
    }

    private class ReceiveSMS extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SMS_RECEIVED.equals(intent.getAction())) {
                // receiving messages
                Object[] pdus = (Object[]) intent.getExtras().get("pdus");
                if (pdus == null) return;
                SmsMessage[] messages = new SmsMessage[pdus.length];
                for (int i = 0; i < pdus.length; i++) {
                    messages[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                }

                for (SmsMessage msg : messages) {
                    show(msg.getMessageBody() + " " + msg.getOriginatingAddress());
                }
            }
        }
    }
}
