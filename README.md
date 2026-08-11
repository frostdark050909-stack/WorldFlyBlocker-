# WorldFlyBlocker

Plugin Paper untuk menonaktifkan **fly** player di world tertentu (misal `world` / spawn dan `world_the_end`).

## Cara Kerja
- Jika player mencoba mengaktifkan fly (double-jump di creative, atau via plugin lain seperti Essentials `/fly`) di world yang di-blok, fly langsung dibatalkan.
- Jika player pindah world (portal, `/tp`, `/mv`), join server, atau respawn di world yang di-blok, fly otomatis dimatikan.
- Player dengan izin `worldflyblocker.bypass` tidak terkena batasan ini (misal untuk admin/staff).
- Player dalam gamemode Creative/Spectator tetap bisa fly seperti biasa — plugin ini hanya menghentikan aksi terbang aktif (`setFlying`), bukan mengubah gamemode.

## Build
Butuh Java 17+ dan Maven, lalu jalankan di folder project:

```
mvn clean package
```

Hasil `.jar` akan ada di folder `target/`. Pindahkan ke folder `plugins/` server Paper kamu, lalu restart/reload server.

## Konfigurasi (`config.yml`)
```yaml
disabled-worlds:
  - world
  - world_the_end

deny-message: "&cFly dinonaktifkan di world ini!"
force-disable-allow-flight: true
```

- `disabled-worlds`: daftar nama world (case-insensitive) yang melarang fly. Sesuaikan dengan nama world server kamu (cek dengan `/world list` jika pakai Multiverse, atau lihat folder server).
- `deny-message`: pesan ke player saat fly mereka dimatikan. Mendukung kode warna `&`.
- `force-disable-allow-flight`: jika `true`, izin fly (allowFlight) juga dicabut untuk player survival/adventure yang mendapat fly dari plugin lain.

## Permission
| Permission | Default | Keterangan |
|---|---|---|
| `worldflyblocker.bypass` | false | Player tetap boleh fly di world manapun |
| `worldflyblocker.admin` | op | Boleh menjalankan `/wfb reload` |

## Command
- `/wfb reload` — memuat ulang `config.yml` tanpa restart server.
