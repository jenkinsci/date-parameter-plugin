# Date Parameter Plugin (for jenkins)

[https://wiki.jenkins-ci.org/display/JENKINS/Date+Parameter+Plugin](https://wiki.jenkins-ci.org/display/JENKINS/Date+Parameter+Plugin)

### About parameter.

- Date Format

  This is date format like Java style like `yyyyMMdd`, `dd/MM/yyyy`, `yyyy-MM-dd HH:mm:ss` whatever you want.

- Default Value

  You can use three types. LocalDate style, LocalDateTime style, or Just string suitable for `Date Format`

  1. LocalDate style example.

    (Java LocalDate style but only contains plusXXX minusXXX methods.)

    - `LocalDate.now();`
    - `LocalDate.now().plusDays(1);`
    - `LocalDate.now().minusDays(1).minusMonths(5).minusYears(1)`

  2. LocalDateTime style example.

    (Java LocalDateTime style but only contains plusXXX minusXXX methods.)

    - `LocalDateTime.now();`
    - `LocalDateTime.now().minusHours(1);`
    - `LocalDateTime.now().minusDays(1).plusMonths(5).minusSeconds(50)`

  3. String example.

    (In this case, String must suitable for `Date Format`)

    - `20170501`
    - `12/03/1988`
    - `2017`
    - `2017-01-01 00:00:00`
