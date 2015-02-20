require 'httparty'
require 'test/unit/assertions'

World(Test::Unit::Assertions)

Given(/^A server "(.*?)" with access enabled to "(.*?)" with "(.*?)" on port "(.*?)"$/) do |host, user, password, port|
    @server = {
        "host" => host,
        "user" => user,
        "password" => password,
        "sshPort" => port,
        "sudo" => true
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

Then(/^user is "(.*?)"$/) do |expectedUser|
    assert_equal(expectedUser, JSON.parse(@response.body)['user'].to_s)
end

Then(/^password is "(.*?)"$/) do |expectedPassword|
    assert_equal(expectedPassword, JSON.parse(@response.body)['password'].to_s)
end

Then(/^port is "(.*?)"$/) do |expectedPort|
    assert_equal(expectedPort, JSON.parse(@response.body)['sshPort'].to_s)
end


Then(/^"(.*?)" process listens on port "(.*?)" with protocol "(.*?)"$/) do |programName, port, protocol|    
    properties = JSON.parse(@response.body)['serverProperties']
    processes = properties.select { |property| property['type'] == 'ListeningProcessServerProperty' }
    puts processes

    foundProcesses = processes.select { |process| process['programName'] == '/' + programName && process['port'] == port && process['protocol'] == protocol }
    assert_equal(1, foundProcesses.length)
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


