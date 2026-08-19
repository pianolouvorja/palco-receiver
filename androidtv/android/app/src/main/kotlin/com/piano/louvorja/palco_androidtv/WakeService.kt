package com.piano.louvorja.palco_androidtv

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.ServerSocket

/**
 * F3.4 fase 3a (RF-001): serviço residente que escuta :7082 e traz o Palco
 * pro foreground ao receber "WAKE\n" — launch remoto pelo sender (APK),
 * sem Google Cast. TV não tem bateria: custo de manter residente = zero.
 *
 * BootReceiver (RF-002) inicia este serviço no boot da TV.
 */
class WakeService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        startForeground(NOTIF_ID, buildNotification())
        Thread { listen() }.start()
    }

    private fun listen() {
        try {
            val server = ServerSocket(7082)
            android.util.Log.d("PALCO-WAKE", "ouvindo :7082")
            while (true) {
                val sock = server.accept() ?: continue
                try {
                    val reader = BufferedReader(InputStreamReader(sock.getInputStream()))
                    val line = reader.readLine() ?: ""
                    sock.close()
                    if (line.trim().equals("WAKE", ignoreCase = true)) {
                        android.util.Log.d("PALCO-WAKE", "WAKE recebido — lançando Palco")
                        launchApp()
                    }
                } catch (e: Exception) {
                    android.util.Log.w("PALCO-WAKE", "conn erro: ${e.message}")
                }
            }
        } catch (e: Exception) {
            android.util.Log.e("PALCO-WAKE", "listen falhou: ${e.message}")
        }
    }

    private fun launchApp() {
        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        startActivity(intent)
    }

    private fun buildNotification(): Notification {
        val chanId = "palco_wake"
        val nm = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        nm.createNotificationChannel(
            NotificationChannel(chanId, "Palco sempre pronto", NotificationManager.IMPORTANCE_MIN)
        )
        val pi = PendingIntent.getActivity(
            this, 0,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
        return Notification.Builder(this, chanId)
            .setContentTitle("Palco LouvorJA")
            .setContentText("Aguardando o app do celular")
            .setSmallIcon(android.R.drawable.ic_media_play)
            .setContentIntent(pi)
            .build()
    }

    companion object {
        private const val NOTIF_ID = 7082
    }
}
