require 'httparty'    


After do
    $servers = HTTParty.get('http://localhost:8080/servers',
        {
            :headers => { 'Content-Type' => 'application/json', 'Accept' => 'application/json' }
        })

    $servers.each do |server|
        $retry = 0
        begin
            $response = HTTParty.delete('http://localhost:8080/servers/' + server['host'])
        end until $response.code != 204
    end
end

