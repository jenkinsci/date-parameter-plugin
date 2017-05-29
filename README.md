# Date Parameter Plugin (for jenkins)

### About parameter.

- Date Format

  This is date format like Java style like `yyyyMMdd`, `dd/MM/yyyy` whatever you want.

- Default Value

  You can use two types. LocalDate style or Just string suitable for `Date Format`

  1. LocalDate style example.

    (Java LocalDate style but only contains plusXXX minusXXX methods.)

    - `LocalDate.now();`
    - `LocalDate.now().plusDays(1);`
    - `LocalDate.now().minusDays(1).minusMonths(5).minusYears(1)`

  2. String example.

    (In this case, String must suitable for `Date Format`)

    - `20170501`
    - `12/03/1988`
    - `2017`
