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
    }
}

resource applicationGatewaySubnet 'Microsoft.Network/virtualNetworks/subnets@2024-07-01' = {
    parent: privateVirtualNetwork
    name: 'application-gateway-subnet'
    properties: {
        addressPrefix: '10.0.0.0/24'
    }
}

resource publicSubnet 'Microsoft.Network/virtualNetworks/subnets@2024-07-01' = {
    parent: privateVirtualNetwork
    name: 'public-subnet'
    properties: {
        addressPrefix: '10.0.1.0/24'
        delegations: [
            {
                name: 'aci-delegation'
                properties: {
                    serviceName: 'Microsoft.ContainerInstance/containerGroups'
                }
            }
        ]
    }
}

resource privateSubnet 'Microsoft.Network/virtualNetworks/subnets@2024-07-01' = {
    parent: privateVirtualNetwork
    name: 'private-subnet'
    properties: {
        addressPrefix: '10.0.2.0/24'
        delegations: [
            {
                name: 'aci-delegation'
                properties: {
                    serviceName: 'Microsoft.ContainerInstance/containerGroups'
                }
            }
        ]
    }
}

resource tourPlannerClientIp 'Microsoft.Network/publicIPAddresses@2024-07-01' = {
    name: publicIpAddressName
    location: location
    sku: {
        name: 'Standard'
    }
    properties: {
        publicIPAddressVersion: 'IPv4'
        dnsSettings: {
            domainNameLabel: 'tour-planner'
            fqdn: 'tour-planner.italynorth.cloudapp.azure.com'
        }
        publicIPAllocationMethod: 'Static'
    }
}

output applicationGatewaySubnetId string = applicationGatewaySubnet.id
output publicSubnetId string = publicSubnet.id
output privateSubnetId string = privateSubnet.id
output tourPlannerClientIpId string = tourPlannerClientIp.id
