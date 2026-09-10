# Effort log — recipe.me interventions

Evidence base for **H4** (benefit relative to implementation cost). This log is the only
record of implementation effort and cannot be reconstructed after the fact — fill in a row
at the moment the work is done, not at the end.

**Baseline: tag `thesis-baseline-b494df8`, commit `b494df8`, Gradle 8.14.5.**
This is the commit every measured series `step1`–`step5` ran on. The older tag
`thesis-baseline` points at `0b4c591`, which carries Gradle 8.0.2 and was never measured —
do not use it as the "before" state for anything in this log.

---

## ⚠️ The work is AI-assisted. Read this before recording a number.

H4 asks what a technique costs to implement. Section 2.2 makes recipe.me the instrument of
that hypothesis because "pełna znajomość projektu oraz dostęp do kodu źródłowego umożliwia
bezpośrednią ocenę nakładu pracy". Wall-clock time spent in an AI-assisted session does not
answer that question: it measures how fast the model types, not what the technique costs a
developer. Logging it as if it were the effort would silently substitute one quantity for
another.

**Two separate columns, never conflated:**

- **`czas sesji`** — actual wall-clock time of the session that produced the change,
  AI included. Recorded for transparency; it is *not* the H4 input.
- **`nakład człowieka`** — the estimate that feeds H4: what the same change would cost a
  developer working alone, including locating the files, writing the change, verifying the
  build and debugging.

**Estimation method — pre-registered wherever possible.** The audit
`audit/recipe/0b4c591.md`, section "Findings — ranked against chapter 1", carries an
`effort` column written on 2026-07-28 by static analysis, **before any of this work began
and without knowing the outcome**. Where a logged intervention corresponds to a backlog
item, its pre-registered figure is the human estimate and the `źródło estymacji` column
says `pre-reg #N`. This is far more defensible than estimating after the fact, because the
number could not have been tuned to the result.

Only interventions with no backlog entry need a post-hoc estimate. Mark those `post-hoc`
and write down in `uwagi` what the estimate is based on — comparable task, number of files
touched, whether debugging was involved. Record such an estimate **before** measuring the
benefit. A post-hoc estimate made after seeing the result is the weakest evidence in this
log and should be the exception.

**This is a declared limitation, not a hidden one.** Subsection 3.2.5 must state that the
effort axis of H4 rests on estimates rather than on measured developer time, and the
conclusion must repeat it among the study's limitations. An estimate honestly labelled is
usable evidence; an estimate presented as a measurement is not.

---

## Conventions

- **data** — ISO, the day the work was actually done.
- **interwencja** — one row per intervention, matching the identifier used in
  `dev/plan-interwencji-3.2.5.md` (I1…I7) and the name used in section 3.2.5.
- **gałąź** — `thesis/int-N-<slug>`, cut from `b494df8`, never stacked on another
  intervention. Record the branch head's SHA once the change lands.
- **czas sesji** — wall-clock, AI-assisted, including failed attempts and reverts.
- **nakład człowieka** — the H4 input; see above.
- **źródło estymacji** — `pre-reg #N` (backlog item N in `0b4c591.md`) or `post-hoc`.
- **wynik** — `landed`, `abandoned`, or `deferred`. Record failed attempts as their own row
  with `abandoned`; a technique too expensive to land is a result for H4, not missing data.

---

| data | interwencja | gałąź / SHA | czas sesji | nakład człowieka | źródło estymacji | wynik | uwagi |
|---|---|---|---|---|---|---|---|
| 2026-09-10 | **I1** — jawna sterta JVM demona Gradle'a i demona Kotlina | `thesis/int-1-jvm-memory` / `9d42fd3` | ~10 min | **~15 min** | `pre-reg #2` | `landed` | Zmiana w jednym pliku: `org.gradle.jvmargs` 2048m → 4096m oraz dopisane `kotlin.daemon.jvmargs=-Xmx2048m`. Bez debugowania. Weryfikacja kompilacji odłożona do przebiegu pomiarowego — Docker był zajęty diagnostyką projektu referencyjnego, a drugi kontener zafałszowałby czasy. Estymata pre-rejestrowana pokrywa się z czasem sesji, bo praca polega na wpisaniu dwóch wartości; wartości dobrano poniżej limitu kontenera 20g, żeby przedmiotem był rozmiar sterty, a nie niedobór pamięci |

---

## Pre-registered human estimates, transcribed before the work starts

Fixed here so the H4 input cannot drift once results are known. Source:
`audit/recipe/0b4c591.md`, "Actionable, ranked", `effort` column, written 2026-07-28.

| plan | backlog | pre-registered effort | intervention |
|---|---|---|---|
| I1 | #2 | ~15 min | JVM heap + `kotlin.daemon.jvmargs` |
| I2 | #2 | ~15 min | explicit `org.gradle.workers.max` |
| I3 | — | post-hoc required | `gradle.properties` hygiene |
| I4 | #1 | 1–2 h | configuration-time file inputs → `ValueSource` |
| I5 | #4 | 2–4 h | split `build-logic:convention` |
| I6 | — | post-hoc required | app version out of the catalogue into `version.properties` |
| I7 | — | post-hoc required | `buildConfig = false` where unused |

⚠️ I1 and I2 share one backlog entry (#2) but are separate interventions on separate
branches, so each carries that figure independently. In this log the figure covers **making
the change**, not running the measurement sweep.

⚠️ I3, I6 and I7 have no backlog entry — they came out of the 2026-09-10 analysis, after
the measurements existed. Their estimates are post-hoc by construction and must be labelled
as such.

---

## Log of preparatory work — NOT interventions, NOT H4 input

Kept separate so it never contaminates the table above. Study infrastructure, not
optimisation techniques; none of it changes how the project builds.

| data | czynność | wynik |
|---|---|---|
| 2026-09-10 | Ustalono, że rankingowany backlog interwencji nie zaginął — leży w `audit/recipe/0b4c591.md`, nie w katalogu skilla | — |
| 2026-09-10 | Zweryfikowano liczbę modułów z KSP na `b494df8`: **18**, zgodnie z drukowanym 2.2.2. Wcześniejsze „19" liczyło główny skrypt budowania, który deklaruje `apply false` i modułem nie jest | — |
| 2026-09-10 | Założono tag `thesis-baseline-b494df8` na commicie, na którym zmierzono `step1`–`step5`. Stary tag `thesis-baseline` zostawiony bez zmian, bo notatki z 03.09 powołują się na niego przy wypełnianiu ścieżek w scenariuszach | tag lokalny, niewypchnięty |
| 2026-09-10 | Poprawiono nagłówek tego pliku na `b494df8`; wprowadzono rozdział czasu sesji od nakładu człowieka wraz z metodą estymacji | — |
| 2026-09-10 | Pomiar diagnostyczny zasięgu unieważnienia po podbiciu wersji w `gradle/libs.versions.toml` (tryb trwały, 4 budowania, `_diag-20260910-222834-version-bump`) | **przewidywanie obalone** — patrz niżej |

### Wynik pomiaru diagnostycznego z 2026-09-10 — podbicie wersji w katalogu

Cztery budowania w trybie trwałym, te same limity kontenera i te same wolumeny co serie.
Liczenie modułów przez `count-recompiled-modules.py`, czyli tym samym narzędziem
i tymi samymi definicjami, których używa praca.

| budowanie | czas | pamięć konfiguracji | moduły rekompilowane | zadania wykonane |
|---|---|---|---|---|
| bez zmian | 5 s | `Reusing configuration cache` | 0 | 2 |
| wersja podbita | 19 s | **odrzucona** | **1 — `:app`** | 14 |
| wersja przywrócona | 13 s | odrzucona | 0 | 4 (+11 z cache) |

**Hipoteza robocza, że edycja katalogu rekompiluje `:convention` i przez to unieważnia
wszystkie 25 modułów, jest fałszywa.** Rekompiluje się wyłącznie moduł aplikacji, i to
z powodu własnego: `versionCode` i `versionName` wchodzą do jego `BuildConfig` i manifestu,
więc uruchamia się `generateDebugBuildConfig`, przetwarzanie manifestu, `compileDebugKotlin`,
dex i pakowanie. Zależność aplikacji od własnego numeru wersji jest nieusuwalna.

Co edycja katalogu unieważnia naprawdę, i co Gradle nazywa wprost:

```
Calculating task graph as configuration cache cannot be reused
because file 'gradle/libs.versions.toml' has changed.
```

To jest jedyny efekt wykraczający poza moduł aplikacji — i przeniesienie wersji do innego
pliku **go nie usunie**, bo nowy plik również byłby czytany w fazie konfiguracji, więc
również unieważniałby wpis. Faza konfiguracji na recipe.me to 0,6–1,1 s wg Tabel 10 i 11,
czyli ułamek zmierzonych czternastu sekund różnicy; reszta to nieusuwalna praca modułu
aplikacji.

**Wniosek dla I6: korzyść czasowa przewidywana na zero.** Wynik zgodny z twierdzeniem 3
i 4 z `synteza-pomiarow.md` — zakres nie przewiduje kosztu, a unikanie rekompilacji
działa również tutaj.
