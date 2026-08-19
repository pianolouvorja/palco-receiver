# Palco Receiver — LouvorJA

App receiver (Palco) para TVs — exibe projeção enviada pelo app mobile
PIANO LouvorJA (sender WebSocket, protocolo v2).

## Variantes

| Pasta | Plataforma | Empacotamento |
|---|---|---|
| `webos/` | LG webOS | IPK (`ares-package`) — instalar via Dev Mode |
| `androidtv/` | Android TV / box / Fire TV | APK Flutter (WebView + NSD) |

Ambas compartilham o MESMO receiver (HTML/JS) — paridade total de features:
scan automático do sender, controle por remote (play/pause/stop/back),
auto-relaunch, splash e identidade da org.

## Regras do receiver (HTML/JS)

- **NUNCA usar ES2020+** (`??`, `?.`) — Chromium antigo do webOS não parseia
  e o script inteiro morre silenciosamente (root cause das versões 0.1.3-0.1.5)
- Assets com paths RELATIVOS (funciona em `file://` do IPK e HTTP do sender)
- `receiver.html`/`index.html` são o MESMO arquivo — sincronizar sempre

## Releases

IPKs e APKs publicados em https://github.com/pianolouvorja/palco-receiver/releases
