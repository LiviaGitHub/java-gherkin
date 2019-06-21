Feature: Apply a car for sale

  As a customer, I want to select the type of car I want to sell,
  so that I can receive an offer from X company.

  Scenario: Receive an offer from X company
        Given that the user has access to a list of car brands in Berlin
        When User selects the Aston Martin option
        And User selects the DBS car in Berlin
        And in Berlin with the Aston Martin - DBS selected, the user selects the year
        Then the user can receive offers

