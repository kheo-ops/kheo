require 'httparty'
require 'test/unit/assertions'

World(Test::Unit::Assertions)

Given(/^A server "(.*?)" with access enabled to "(.*?)" with "(.*?)"$/) do |host, user, password|
    @server = {
        "host" => host,
        "user" => user,
        "password" => password
    }
end

When(/^I add a "(.*?)" server with access enabled to "(.*?)" with "(.*?)"$/) do |host, user, password|
    HTTParty.post('http://localhost:8080/servers',
        {
            :body => @server.to_json,
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
end

Then(/^I can retrieve the server "(.*?)"$/) do |host|
    @response = HTTParty.get('http://localhost:8080/servers/' + host,
        {
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
end

Then(/^SSH connectivity is "(.*?)"$/) do |connectivity|
    assert_equal(JSON.parse(@response.body)['sshConnectionValidity'], connectivity)        
end
