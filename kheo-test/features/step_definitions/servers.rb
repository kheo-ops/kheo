require 'httparty'
require 'test/unit/assertions'

World(Test::Unit::Assertions)

Given(/^A server "(.*?)" with access enabled to "(.*?)" with "(.*?)"$/) do |host, user, password|
    @response = HTTParty.post('http://localhost:8080/servers',
        {
            :body => { "host" => host, "user" => user, "password" => password }.to_json,
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
end

When(/^I add a "(.*?)" server with access enabled to "(.*?)" with "(.*?)"$/) do |arg1, arg2, arg3|
  pending # express the regexp above with the code you wish you had
end

Then(/^I can retrieve the server "(.*?)"$/) do |arg1|
  pending # express the regexp above with the code you wish you had
end

Then(/^SSH connectivity is "(.*?)"$/) do |arg1|
  pending # express the regexp above with the code you wish you had
end
