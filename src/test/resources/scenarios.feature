Feature: Basic calculator
  As a user of the calculator
  I want to add two numbers
  So that I can see their sum

  Background:
    Given the calculator is cleared

  Scenario Outline: Add two numbers
    When I add <a> and <b>
    Then the result should be <sum>

    Examples:
      | a | b | sum |
      | 1 | 2 | 3   |
      | 5 | 7 | 12  |
      | 10 | -3 | 7 |
