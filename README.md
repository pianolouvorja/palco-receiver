# Palco Receiver — LouvorJA

App receiver (Palco) para TVs — exibe a projeção enviada pelo app
PIANO LouvorJA (celular/desktop) via WebSocket próprio. **Não é
Chromecast/DLNA**: a TV é cliente WS que conecta no sender do operador
(HTTP :7080 / WS :7081, ou porta do slot em multi-TV).

Cada subdiretório tem README próprio com instruções completas de build e
instalação:

| Pasta | Plataforma | Empacotamento | Instalação na TV |
|---|---|---|---|
| [`webos/`](webos/README.md) | LG webOS 4.x+ | IPK (`ares-package`) | Dev Mode + `ares-install` — ver [webos/README.md](webos/README.md) |
| [`tizen/`](tizen/README.md) | Samsung Tizen 4.0+ | WGT (zip + config.xml) | Dev Mode Samsung + `sdb install` — ver [tizen/README.md](tizen/README.md) |
| [`androidtv/`](androidtv/README.md) | Android TV / box / Fire TV | APK Flutter (WebView) | `adb install` / Play Store — ver [androidtv/README.md](androidtv/README.md) |

Todas compartilham o **MESMO receiver** (HTML/JS único) — paridade total de
features, validada no CI: scan automático do sender, IP/porta manual
(tecla vermelha), controle por remote (play/pause/stop/back/setas),
autoplay-mudo com desmute no 1º gesto, auto-relaunch, splash e identidade.

## Regras do receiver (HTML/JS) — valem pra TODAS as plataformas

- **NUNCA usar ES2020+** (`??`, `?.`) — Chromium antigo (webOS/Tizen) não
  parseia e o script inteiro morre silenciosamente (root cause das versões
  0.1.3-0.1.5). O CI bloqueia por regex.
- Assets com paths **RELATIVOS** (funciona em `file://` do IPK/WGT e HTTP
  do sender).
- `webos/index.html` == `tizen/index.html` ==
  `androidtv/assets/palco/receiver.html` — são o MESMO arquivo;
  sincronizar sempre (o CI de paridade falha o PR se divergir).
- Bump de versão em TODOS os manifestos (`appinfo.json`, `config.xml`,
  `pubspec.yaml`) junto do receiver — e no fallback hardcode da versão no
  idle do HTML.

## Arquitetura de conexão (leia antes de debugar)

O **sender** (celular APK ou desktop) é o servidor HTTP+WS. A **TV** é
cliente que conecta no IP do operador. Portas: HTTP 7080 / WS 7081
(principal), 7082/7083+ (slots multi-TV). Detalhes, pitfalls de proxy de
mídia, autoplay e sync nas skills internas do projeto.

## Instalação em TVs Samsung (resumo — detalhe em tizen/README.md)

Caminho prático sem parceira Samsung: app **Developer Mode** (da Samsung,
na TV) + `sdb install` via Tizen Studio CLI — TV e PC na mesma rede.
Validação sem TV física: **Remote Test Lab** (TVs reais na nuvem, grátis).
Escala: **TV Seller Office** (nota: Public Seller só distribui nos EUA;
Brasil exige Partner Seller).

## Releases

IPKs e APKs publicados em
https://github.com/pianolouvorja/palco-receiver/releases
(repo privado: asset URL exige token — para download direto da TV use Dev
Mode/scp, ou aguarde publicação em loja).
