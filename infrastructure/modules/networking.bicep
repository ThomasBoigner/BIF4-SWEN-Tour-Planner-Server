param location string

var publicIpAddressName = 'tour-planner-client-ip'

resource privateVirtualNetwork 'Microsoft.Network/virtualNetworks@2024-07-01' = {
    name: 'tour-planner-network'
    location: location
    properties: {
        addressSpace: {
            addressPrefixes: [
                '10.0.0.0/16'
            ]
        }
        subnets: [
            {
                name: 'application-gateway-subnet'
                properties: {
                    addressPrefix: '10.0.0.0/24'
                }
            }
            {
                name: 'public-subnet'
                properties: {
                    addressPrefix: '10.0.1.0/24'
                }
            }
            {
                name: 'private-subnet'
                properties: {
                    addressPrefix: '10.0.2.0/24'
                }
            }
        ]
    }
}

resource tourPlannerClientIp 'Microsoft.Network/publicIPAddresses@2024-07-01' = {
    name: publicIpAddressName
    location: location
    properties: {
        publicIPAddressVersion: 'IPv4'
        dnsSettings: {
            domainNameLabel: 'tour-planner'
            fqdn: 'tour-planner.italynorth.cloudapp.azure.com'
        }
    }
}
