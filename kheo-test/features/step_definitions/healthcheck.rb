require 'httparty'


Given(/^I am on the healthcheck page$/) do
  @response = HTTParty.get('http://localhost:8081/healthcheck')
end

Then(/^status code is (\d+)$/) do |expected_code|
  assert_equal(expected_code.to_i, @response.code)
end

