package com.piano.louvorja.palco_androidtv

import android.content.Intent
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity

class MainActivity : FlutterActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // F3.4 fase 3a (RF-001): garante o WakeService rodando — Palco
        // sempre pronto pra receber WAKE do celular.
        startForegroundService(Intent(this, WakeService::class.java))
    }
}
