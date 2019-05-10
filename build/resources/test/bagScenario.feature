Feature: Public cart functionallity testing

	Scenario: Succesfull price update
		Given I navigate to "https://www.public.gr/"
		When I search for "dan brown"
		And I add two specific products to my cart
		And I open my cart
		And I increase the quantity of the second product by one
		Then I should see that the price has been correctly updated