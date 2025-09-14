Feature: Address checking
  As a user
  I want to check certain zip codes
  So I receive info about that address

  Background:
    Given the API is up and ready

  Scenario Outline: Get an address
    When I call the endpoint informing zip code <zipCode>, my document number <docNum> and email <email>
    Then the response code must be <code>

    Examples:
      | zipCode | docNum      |  | email                   | code |
      |         | 12345678900 |  | andre_yabiku@icloud.com | 200  |
      |         | invalid     |  | andre_yabiku@icloud.com | 400  |
      |         | 12345678900 |  | invalid                 | 400  |