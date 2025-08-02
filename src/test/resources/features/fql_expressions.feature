Feature: Valid FQL Expressions

  Scenario Outline: Parsing valid FQL expressions
    When I parse the FQL expression <expression>
    Then no parse error should occur
    And the expression string should be <expression>

    Examples:
      | expression                                                                   |
      | "DATA(\"AAPL\", \"REVENUE\", LATEST())"                                      |
      | "DATA(\"TSLA\", \"SHARE_PRICE\", PERIOD(2023, \"Q4\"))"                      |
      | "DATA(\"MSFT\", \"EPS\", RANGE(PERIOD(2020, \"Q1\"), PERIOD(2023, \"Q4\")))" |
      | "DATA(\"V\", \"DIVIDEND\", LAST(10, \"QUARTER\"))"                           |
      | "42"                                                                         |
      | "\"Hello\""                                                                  |
      | "3.14"                                                                       |
      | "1 + 2"                                                                      |
      | "3 * 4"                                                                      |
      | "5 - 6"                                                                      |
      | "7 / 8"                                                                      |
      | "(1 + 2) * 3"                                                                |
      | "-10"                                                                        |
      | "+20"                                                                        |
      | "DATA(\"AAPL\", \"REVENUE\", LATEST()) + 100"                                |
      | "DATA(\"TSLA\", \"SHARE_PRICE\", PERIOD(2023, \"Q4\")) * 2"                  |
      | "10 * (20 + 30)"                                                             |
      | "PERIOD(2023, \"YEAR\")"                                                     |
      | "RANGE(PERIOD(2020, \"YEAR\"), PERIOD(2023, \"YEAR\"))"                      |
      | "LAST(5, \"QUARTER\")"                                                       |
      | "LATEST()"                                                                   |
      | "DATA(\"TICKER-WITH/DASH\", \"METRIC:WITH:COLON\", LATEST())"                |
      | "DATA(\"TICKER.WITH.DOTS\", \"METRIC WITH SPACE\", LATEST())"                |
      | "DATA(\"$TICKER^\", \"#METRIC@\", LATEST())"                                 |
      | "DATA(\"TICKER[123]\", \"METRIC<value>\", LATEST())"                         |

  Scenario Outline: Parsing invalid FQL expressions
    When I parse the FQL expression <expression>
    Then an FQL error should occur

    Examples:
      | expression                                         |
      | "DATA(\"AAPL\", \"REVENUE\", LATEST(\")"           |
      | "DATA(\"AAPL\", \"REVENUE\" LATEST())"             |
      | "DATA(\"AAPL\", \"REVENUE\", UNKNOWNFUNC())"       |
      | "DATA(\"AAPL\" \"REVENUE\", LATEST())"             |
      | "RANGE(PERIOD(2020, \"Q1\"), PERIOD(2023, \"Q4\")" |
      | "10 * ((2 + 3)"                                    |
      | "PERIOD(2023 \"YEAR\")"                            |
      | "LATEST(,)"                                        |
      | "DATA(\"AAPL\", \"REVENUE\", LATEST()) + "         |
      | "DATA(\"AAPL\", , LATEST())"                       |