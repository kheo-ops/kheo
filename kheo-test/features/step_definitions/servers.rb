require 'httparty'
require 'test/unit/assertions'
require 'json'

World(Test::Unit::Assertions)

Given(/^A server "(.*?)" with access enabled to "(.*?)" with "(.*?)" on port "(.*?)"$/) do |host, user, password, port|
    @server = {
        "host" => host,
        "user" => user,
        "password" => password,
        "sshPort" => port
    }
end

When(/^I add a "(.*?)" server with access enabled to "(.*?)" with "(.*?)" on port "(.*?)"$/) do |host, user, password, port|
    @toto = HTTParty.post('http://localhost:8080/servers',
        {
            :body => @server.to_json,
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })
    puts @toto.request.inspect
    puts @toto.inspect
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

