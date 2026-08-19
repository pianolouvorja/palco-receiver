package com.piano.louvorja.palco_androidtv

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * F3.4 fase 3a (RF-002): inicia o WakeService no boot da TV — o Palco
 * fica sempre pronto (residente), esperando o WAKE do celular.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            context.startForegroundService(Intent(context, WakeService::class.java))
        }
    }
}
