# Effort log — recipe.me interventions

Evidence base for **H4** (benefit relative to implementation cost). This log is the only
record of implementation effort and cannot be reconstructed after the fact — fill in a row
at the moment the work is done, not at the end.

**Baseline: tag `thesis-baseline-b494df8`, commit `b494df8`, Gradle 8.14.5.**
This is the commit every measured series `step1`–`step5` ran on. The older tag
`thesis-baseline` points at `0b4c591`, which carries Gradle 8.0.2 and was never measured —
do not use it as the "before" state for anything in this log.

---

## How the effort figures are arrived at

H4 asks what a technique costs to implement, and section 2.2 makes recipe.me the instrument
of that hypothesis because "pełna znajomość projektu oraz dostęp do kodu źródłowego
umożliwia bezpośrednią ocenę nakładu pracy".

**The figures in this log are estimates of developer effort, not stopwatch readings**, and
they are recorded as such. An estimate honestly labelled is usable evidence; an estimate
presented as a measurement is not.

**Pre-registered wherever possible.** The audit `audit/recipe/0b4c591.md`, section
"Findings — ranked against chapter 1", carries an `effort` column written on 2026-07-28 by
static analysis — **before this work began and without knowing any outcome**. Where a
logged intervention corresponds to a backlog item, that figure is the estimate and the
`źródło estymacji` column says `pre-reg #N`. A number fixed before the result exists could
not have been tuned to it, which is what makes the effort axis of H4 worth anything.

Only interventions with no backlog entry need a post-hoc estimate. Mark those `post-hoc`
and say in `uwagi` what the estimate rests on — a comparable task, the number of files
touched, whether debugging was involved. Record it **before** the benefit is measured. A
post-hoc estimate made after seeing the result is the weakest evidence in this log and
should stay the exception.

**Declared, not hidden.** Subsection 3.2.5 states that the effort axis of H4 rests on
estimates rather than on stopwatch readings, and the conclusion repeats it among the
study's limitations.

---

## Conventions

- **data** — ISO, the day the work was actually done.
- **interwencja** — one row per intervention, matching the identifier used in
  `dev/plan-interwencji-3.2.5.md` (I1…I7) and the name used in section 3.2.5.
- **gałąź** — `thesis/int-N-<slug>`, cut from `b494df8`, never stacked on another
  intervention. Record the branch head's SHA once the change lands.
- **nakład (estymata)** — estimated developer effort for the change itself: locating the
  files, writing it, verifying the build, debugging. Failed attempts and reverts count.
- **źródło estymacji** — `pre-reg #N` (backlog item N in `0b4c591.md`) or `post-hoc`.
- **wynik** — `landed`, `abandoned`, or `deferred`. Record failed attempts as their own row
  with `abandoned`; a technique too expensive to land is a result for H4, not missing data.

---

| data | interwencja | gałąź / SHA | nakład (estymata) | źródło estymacji | wynik | uwagi |
|---|---|---|---|---|---|---|
| 2026-09-10 | **I1** — jawna sterta JVM demona Gradle'a i demona Kotlina | `thesis/int-1-jvm-memory` / `9d42fd3` | **~15 min** | `pre-reg #2` | `landed` | Jeden plik: `org.gradle.jvmargs` 2048m → 4096m oraz dopisane `kotlin.daemon.jvmargs=-Xmx2048m`. Bez debugowania. Wartości dobrano znacznie poniżej limitu kontenera 20g, żeby przedmiotem był rozmiar sterty, a nie niedobór pamięci. Weryfikacja kompilacji w przebiegu pomiarowym |
| 2026-09-10 | **I2** — jawny `org.gradle.workers.max` | `thesis/int-2-workers-max` / `35eee19` | **~15 min** | `pre-reg #2` | `landed` | Jeden plik, jedna właściwość. Wartość 8 celowo równa liczbie rdzeni kontenera: przedmiotem jest sam akt zadeklarowania liczby, nie strojenie jej do innej — strojenie pokrywa scenariusz `workers_mismatched`. Przewidywanie: zero, z powodu strukturalnego |
| 2026-09-10 | **I3** — higiena `gradle.properties` i katalogu | `thesis/int-3-properties-hygiene` / `bdb3553` | **~20 min** | `post-hoc` | `landed` | Dwa pliki: `org.gradle.unsafe.configuration-cache` → `org.gradle.configuration-cache` oraz usunięty nieużywany alias `kotlin-kapt`. ⚠️ Pierwotne uzasadnienie mówiło, że stara pisownia emituje ostrzeżenie deprecjacji — **sprawdzone `--warning-mode all` na baseline: nie emituje żadnego**. Stara nazwa jest honorowana milcząco, więc zmiana jest wyłącznie nazewnicza i nie ma żadnego obserwowalnego efektu. Komunikat commita poprawiony. Estymata post-hoc oparta na I1 i I2, zapisana przed pomiarem korzyści |
| 2026-09-10 | **I4** — wejścia konfiguracyjne przez `providers.fileContents` zamiast bezpośredniego odczytu | `thesis/int-4-config-time-inputs` / `a2a8129` | **1–2 h** | `pre-reg #1` | `landed` | Cztery pliki: trzy skrypty `retrofit-*` porzucają `gradleLocalProperties` (wewnętrzne API AGP spod `com.android.build.gradle.internal`), `SigningPlugin` porzuca `FileInputStream` wraz z sondą `exists()`. **Pierwsze podejście nie zbudowało się** — `buildConfigField` wymaga `String`, a odczyt przez provider zwraca `String?`; oryginał przechodził tylko dlatego, że API AGP zwracało typ platformowy. Poprawione przez jawną obsługę braku wpisu: brakująca wartość zatrzymuje teraz konfigurację i podaje nazwę właściwości, zamiast wkompilować literał `null` do `BuildConfig` i ujawnić się dopiero w czasie działania. Weryfikacja: buduje się, wszystkie pięć pól generuje się jak poprzednio |
| 2026-09-10 | **I5** — podział `build-logic` na `:plugins` i `:tools` | `thesis/int-5-build-logic-split` / `ec118f6` | **2–4 h** | `pre-reg #4` | `landed` | Podprojekt `:convention` rozdzielony: `:tools` niesie pomocnicze funkcje rozwiązujące wersję i nie wie nic o wtyczkach, `:plugins` niesie cztery klasy wtyczek i zależy od `:tools`. Identyfikatory wtyczek i klasy implementacji bez zmian, więc żaden moduł konsumujący nie wymagał edycji. **Koszt uboczny do odnotowania:** funkcje pomocnicze były `internal`, co wystarczało, dopóki dzieliły jednostkę kompilacji z jedynym wywołującym; przez granicę modułu `internal` przestaje być widoczne, więc podział wymusza poszerzenie widoczności. Węższa granica unieważniania kupiona ceną szerszej granicy API |
| 2026-09-10 | **I7** — `buildConfig = false` tam, gdzie pola nieużywane | — | — | — | **`abandoned`** | **Brak celu.** Wszystkie pięć pól w trzech modułach `retrofit-*` jest faktycznie używanych w kodzie: `BASE_URL` i `API_KEY` w `RetrofitBaseModule` i `ApiKeyInterceptor`, `DEEPL_BASE_URL` i `DEEPL_API_KEY` w `TranslationModule` i `AuthorizationInterceptor`, `OFF_BASE_URL` w `BarcodeProductsModule`. Nie ma czego wyłączyć. Potwierdzony negatyw, w kategorii tej samej co negatywy z audytu |

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
| 2026-09-10 | Poprawiono nagłówek tego pliku na `b494df8`; ustalono metodę estymacji nakładu i przepisano ją z audytu z 28.07 | — |
| 2026-09-10 | Przebieg weryfikacyjny gałęzi I1–I4 (`_verification-20260910-225012` i `-225502`): każda buduje się do `BUILD SUCCESSFUL`; sprawdzono też `--warning-mode all` na baseline | I4 wymagało poprawki, reszta bez uwag |
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

---

## Wynik diagnostyki z 2026-09-10 — projekt referencyjny, przejście na kolejny commit

Wykonana, żeby rozstrzygnąć, czy mechanizm zidentyfikowany w projekcie autorskim (wartość
zmienna wkompilowana w `BuildConfig` dużego modułu) występuje w projekcie referencyjnym
i co unieważnia. Trzy budowania w kontenerze, para commitów `ab67fcd0ac → 22eb24837a`
dobrana tak, by nie ruszała żadnego skryptu budowania — zmieniają się wyłącznie
wygenerowane profile bazowe oraz sam wskaźnik HEAD. Projekt referencyjny przywrócono do
zamrożonego `9b2c2ed66d`; nic w nim nie zacommitowano.

| budowanie | zadania niebędące aktualnymi |
|---|---|
| bez zmian, ten sam commit | podpisywanie, pakowanie, zmiana nazwy APK, `assemble` |
| **po przejściu na kolejny commit** | **`generateBuildConfig`, `compileKotlin`, `compileJavaWithJavac`, `dexBuilder`, `mergeProjectDex`** plus powyższe |

Gradle nazywa przyczynę wprost:

```
Calculating task graph as configuration cache cannot be reused
because output of the external process 'git' has changed.
```

**Cały łańcuch kompilacji modułu aplikacji zostaje unieważniony przez samo przejście na
kolejny commit** — bo `GIT_HASH` i `BUILD_TIMESTAMP` są polami `buildConfigField`
w `defaultConfig`, czytanymi z `git` w fazie konfiguracji. Moduł ten mieści 72% źródeł
projektu.

Zastrzeżenia, bez których wyniku nie wolno cytować:

1. Zadania nie wykonały się ponownie, tylko zostały **odtworzone z pamięci podręcznej
   budowania** (`FROM-CACHE`) — wpisy pochodzą z wcześniejszych serii, które budowały na
   tym commicie. Przy commicie nigdy wcześniej niebudowanym odtworzenia nie byłoby.
   Unieważnienie jest faktem; koszt jego wykonania nie został tu zmierzony.
2. Budowanie kontrolne bez zmian również odrzuciło wpis pamięci konfiguracji, ale
   z przyczyny zewnętrznej wobec projektu — znacznika instalacyjnego zestawu SDK w systemie
   plików kontenera. Część porównania dotycząca pamięci konfiguracji jest przez to
   nieważna; część dotycząca aktualności zadań pozostaje w mocy, bo liczy się ona z wejść
   zadań, nie z pamięci konfiguracji.
3. Pojedyncze budowania, nie seria. Czasów nie podawać.

Wniosek: mechanizm jest w projekcie referencyjnym obecny i dotyczy największego modułu,
ale **żaden scenariusz z wydrukowanego katalogu go nie widzi**, bo wszystkie serie idą na
jednym zamrożonym commicie. Wycena kosztu wymagałaby serii idącej naprzód, na commit
wcześniej niebudowany, i bez wpisów w pamięci podręcznej dla commita docelowego.

---

## Ustalenie uboczne z weryfikacji — katalog wersji unieważnia różnie

Dwa pomiary z tego samego dnia dają razem wynik, którego żaden osobno nie pokazuje.

| zmiana w `gradle/libs.versions.toml` | co się unieważnia |
|---|---|
| zmiana **wartości** wpisu (podbicie wersji aplikacji) | wpis pamięci konfiguracji; rekompiluje się **jeden moduł** — aplikacja, przez własny `BuildConfig` |
| zmiana **struktury** katalogu (usunięcie aliasu wtyczki w I3) | akcesory typu logiki budowania, a przez to `:convention` i **wszystkie moduły stosujące wtyczki konwencji**: 349 zadań wykonanych wobec 2 w budowaniu bez zmian |

Katalog jest wejściem logiki budowania, ale kosztowne jest wyłącznie ruszenie jego
**kształtu**, nie wartości. To wyjaśnia, dlaczego przeniesienie numeru wersji do osobnego
pliku nie miało szans nic dać — numer wersji jest wartością.

⚠️ Konsekwencja dla pomiaru I3: wejście na tę gałąź kosztuje jednorazową pełną przebudowę.
W trybie efemerycznym bez znaczenia, bo oba ramiona startują od zera; w trybie trwałym musi
ją pochłonąć rozgrzewka, inaczej zostanie zmierzony koszt przełączenia gałęzi zamiast
interwencji.

---

## Wynik pomiaru I5 — zakres unieważnienia po dotknięciu wtyczki konwencji

Dwa ramiona, po trzy budowania każde, ta sama zmiana non-ABI (prywatna składowa dodana do
ciała klasy) wniesiona do `AndroidLibraryPlugin.kt` pod adresem właściwym dla ramienia.
Miernikiem jest zakres, nie czas: mediana trybu trwałego tego projektu jest rzędu sekundy,
a `touch_build_logic` nie przechodzi kontroli stabilności, więc porównanie czasów
raportowałoby szum. Zakres jest wielkością deterministyczną.

| ramię | budowanie | moduły rekompilujące Kotlina | zadania wykonane |
|---|---|---|---|
| monolit (`b494df8`) | bez zmian | 0 | 3 |
| monolit | **po dotknięciu wtyczki** | **1** — `:build-logic:convention` | 11 |
| po podziale (`ec118f6`) | bez zmian | 0 | 3 |
| po podziale | **po dotknięciu wtyczki** | **1** — `:build-logic:plugins` | 11 |

**Wynik: identyczny w obu ramionach.** Rekompiluje się wyłącznie sam podprojekt logiki
budowania; **ani jeden moduł aplikacji nie jest unieważniany** — ani przed podziałem, ani
po nim.

Podział nie zawęził zakresu, bo **nie było czego zawężać**: zmiana nienaruszająca interfejsu
publicznego wtyczki konwencji nie unieważnia jej konsumentów już w układzie monolitycznym.
Unikanie rekompilacji działa na granicy logiki budowania tak samo, jak działa między
modułami aplikacji.

Zestawione z obserwacją z weryfikacji I3 daje to obraz pełny:

| zmiana w logice budowania | konsumenci unieważnieni |
|---|---|
| nienaruszająca interfejsu (prywatna składowa) | **żaden** |
| naruszająca kształt katalogu (usunięty alias wtyczki → regeneracja akcesorów) | **wszyscy** — 349 zadań wykonanych |

Podział `build-logic` nie wpływa na to, który z tych dwóch przypadków zachodzi.

**Konsekwencja dla H4:** interwencja o pre-rejestrowanym nakładzie 2–4 h daje korzyść
zmierzoną na dokładnie zero, przy mierniku, na który zezwala 2.5. Zgodne z wynikiem
`step3`, gdzie istniejący podział w projekcie referencyjnym również nie zlokalizował
unieważnienia. Wynik negatywny wobec praktyki zalecanej w literaturze, do zaraportowania
wprost.

---

## Przebieg pomiarowy z 2026-09-10/11 — pierwsza część

Sześć serii efemerycznych scenariusza `ref_clean`, `--repeat 10`, osiem rdzeni, 20 GB bez
przestrzeni wymiany. Ramiona są gałęziami, nie scenariuszami, więc profiler nie może ich
przepleść wewnątrz jednego wywołania; baseline zmierzono na obu końcach jako najtańszy
dostępny substytut. Katalog: `runs/_pass-20260910-231746-interventions`.

| ramię | mediana [ms] | Q1–Q3 | IQR % | kontrola stabilności |
|---|---|---|---|---|
| baseline, pierwszy | 16315 | 15123–17100 | 12,1 | **fail**, dryf +13,3% |
| I1 sterta JVM | 15470 | 15318–16341 | 6,6 | **fail**, dryf −7,1% |
| I2 `workers.max` | 15279 | 15109–15573 | 3,0 | pass |
| I3 higiena | 15731 | 15345–15821 | 3,0 | pass |
| I4 wejścia konfiguracyjne | 15752 | 15492–15789 | 1,9 | pass |
| baseline, ostatni | 15802 | 15764–16108 | 2,2 | pass |

### Ustalenie metodyczne: pierwsza seria wieczoru jest nieporównywalna

Rozrzut spada monotonicznie przez cały przebieg: 12,1 → 6,6 → 3,0 → 3,0 → 1,9 → 2,2
procent. Host rozgrzewał się mimo trybu efemerycznego — kontener startuje od zera, ale
warstwy obrazu, pamięć podręczna stron systemu plików i `ro-dep-cache` po stronie hosta
już nie.

Zawęża to obserwację z `synteza-pomiarow.md`, że serie efemeryczne są najbardziej
powtarzalne w całym badaniu przy IQR 0,8–5,3%: **z wyjątkiem pierwszej serii po
bezczynności hosta.** Do dopisania w 2.5 albo w ograniczeniach zakończenia; przy planowaniu
kolejnych przebiegów pierwsza seria idzie do odrzucenia.

### Odczyt

Podrozdział 2.5 wymaga powtórzenia w całości serii, która nie przeszła kontroli, więc
ramiona pierwsze i drugie są nie do użytku. Odniesieniem zostaje baseline ostatni, jedyny
ważny.

| interwencja | wobec baseline ostatniego | werdykt |
|---|---|---|
| I2 `workers.max` | −3,3%, rozstępy **rozłączne** | różnica mierzalna, ale **poniżej progu 5%** — praktycznie nieodczuwalna |
| I3 higiena | rozstępy nakładają się | **n.r.**, wartości procentowej nie podaje się |
| I4 wejścia konfiguracyjne | rozstępy nakładają się | **n.r.** |
| I1 sterta JVM | seria nieważna | do powtórzenia |

⚠️ **Kontroli dryfu całego przebiegu nie da się orzec**: jej pierwszy koniec jest serią,
która oblała własną kontrolę stabilności. Nominalnie −3,1%, czyli poniżej progu, ale
zbudowane na nieważnym wejściu.

⚠️ **Zastrzeżenie do I2.** Deklarowana wartość `workers.max=8` jest liczbowo równa
wartości domyślnej przy ośmiu rdzeniach kontenera, więc strukturalnie jest to
nie-operacja. Rozłączne rozstępy oznaczają, że różnica jest realna **w tych dwóch
seriach**, ale serie dzieli czterdzieści pięć minut, a kontrola stabilności widzi wyłącznie
dryf wewnątrz serii, nie między nimi. Ramiona są gałęziami, więc przeplecenie — które
przed tym właśnie chroni — jest niewykonalne. Różnica poniżej progu istotności praktycznej
przy gałce przewidzianej jako nie-operacja jest prawdopodobniej zmiennością międzyseryjną
niż efektem, a badanie nie ma przyrządu, żeby je rozdzielić. Materiał do ograniczeń, nie do
rankingu H4.
