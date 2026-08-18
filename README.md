# palco-receiver

Receivers de TV ("dumb display") do LouvorJA Palco. Cada diretório é uma plataforma de empacotamento do MESMO receiver HTML/JS que fala o protocolo WS (v1/v2) definido em `pianolouvorja/apk` (`lib/core/services/palco/palco_models.dart`).

Princípio (Rafael): a TV só renderiza projection/audio/video/timer. Toda inteligência fica no APK/Web/Electron. Nunca duplicar recursos do app na TV.

## Plataformas

| Diretório | Alvo | Pacote | Distribuição |
|-----------|------|--------|--------------|
| `webos/` | LG webOS 3.x+ (Blink) | `.ipk` (ares-package) | LG Content Store (Seller Lounge) |
| `tizen/` | Samsung Tizen (futuro) | `.wgt` | Samsung Seller Office |
| `androidtv/` | Android TV (futuro) | APK/WebView | Play Store |
| `web/` | Browser genérico (futuro) | estático | fallback / dev / TVs antigas |

## Versionamento

- SemVer por plataforma: versão no manifest (`webos/appinfo.json` → `version`) + tag git `webos-vX.Y.Z`
- O receiver segue o protocolo do APK; versões podem divergir do APK desde que o range de compatibilidade no handshake aceite
- Toda mudança no receiver re-triggers QA da loja correspondente (LG: 5-10d úteis) — receiver muda pouco de propósito

## Compatibilidade de protocolo

Fonte de verdade: `pianolouvorja/apk` → `palco_models.dart` (v1 = hello/hystm/hyreq/hystp; v2 = range headers, re-encode proxy). Receiver aceita range de versões no handshake.

## Build (webOS)

```bash
cd webos
# DevMode TV: ares-package depois ares-install -d <device>
~/webOS_SDK/ares-package .   # gera com.louvorja.palco_X.Y.Z_all.ipk
```

DevMode TV LG UM7510: 192.168.1.174. Spec de distribuição: `~/.hermes/specs/2026-08-17-palco-f4-lg-content-store.md`
