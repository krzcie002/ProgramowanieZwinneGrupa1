
# Sprint 1 — Plan (2 tygodnie)

## Cel sprintu
Dostarczyć MVP: dodawanie wydatków, lista, podsumowanie 30 dni, zielone CI.

## Zakres (wybrane z BACKLOG.csv)
- US1, US2, US3, TECH1, TECH5

## Zadania techniczne
- Inicjalizacja repo i struktury
- Implementacja modelu i prostego storage (in-memory + zapis JSON/CSV)
- Endpointy: POST /expenses, GET /expenses, GET /summary
- Testy jednostkowe do kluczowych funkcji
- Konfiguracja CI (pytest, flake8, black, bandit)
- Testy wydajnościowe

## Definition of Done
- Testy zielone (>= 80% coverage mile widziane)
- Lint bez błędów
- PR z code review (>= 1 approval)
- Uaktualnione README i changelog sprintu

## Ryzyka sprintu
- Zbyt ambitny zakres – obciąć US3 na prostsze KPI
