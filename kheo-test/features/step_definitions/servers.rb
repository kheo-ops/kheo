require 'httparty'
require 'test/unit/assertions'

World(Test::Unit::Assertions)

Given(/^A server "(.*?)" with access enabled to "(.*?)" with "(.*?)" on port "(.*?)"$/) do |host, user, password, port|
    @server = {
        "host" => host,
        "user" => user,
        "password" => password,
        "sshPort" => port
    }
end

When(/^I add a "(.*?)" server with user "(.*?)" and password "(.*?)" on port "(.*?)"$/) do |host, user, password, port|
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

Then(/^SSH connectivity is "(.*?)"$/) do |expectedSshConnectivity|
    assert_equal(expectedSshConnectivity, JSON.parse(@response.body)['sshConnectionValidity'].to_s)
end

Given(/^A server "(.*?)" with access disabled to "(.*?)" with "(.*?)" on port "(.*?)"$/) do |host, user, password, port|
    @server = {
        "host" => host,
        "user" => user,
        "password" => password,
        "sshPort" => port
    }
end


Given(/^An existing server "(.*?)"$/) do |host|
    HTTParty.post('http://localhost:8080/servers',
        {
            :body => { "host" => host, "user" => "user", "password" => "password" }.to_json,
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
end

When(/^I remove "(.*?)"$/) do |host|
    @response = HTTParty.delete('http://localhost:8080/servers/' + host)
end

When(/^I retrieve the server "(.*?)"$/) do |host|
    @response = HTTParty.get('http://localhost:8080/servers/' + host,
        {
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
end

Given(/^A non existing server "(.*?)"$/) do |host|
    @response = HTTParty.get('http://localhost:8080/servers/' + host,
        {
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
    assert_equal(404, @response.code)
end

Then(/^"(.*?)" is not found$/) do |host|
    assert_equal(404, @response.code)
end

When(/^I update "(.*?)" with user "(.*?)" and password "(.*?)"$/) do |host, user, password|
    @response = HTTParty.put('http://localhost:8080/servers/' + host,
        {
            :body => { "host" => host, "user" => user, "password" => password }.to_json,
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
end

Then(/^"(.*?)" user is "(.*?)" and password is "(.*?)"$/) do |host, user, password|
    assert_equal(user, JSON.parse(@response.body)['user'])
    assert_equal(password, JSON.parse(@response.body)['password'])
end

