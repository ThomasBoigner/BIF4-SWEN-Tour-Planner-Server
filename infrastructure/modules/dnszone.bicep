param tourPlannerClientIp string
param tourPlannerServerIp string
param vnetId string

var privateDnsZonesName  = 'tour-planner.com'

resource tourPlannerCom 'Microsoft.Network/privateDnsZones@2024-06-01' = {
    name: privateDnsZonesName
    location: 'global'
    properties: {}
}

resource tourPlannerClientRecord 'Microsoft.Network/privateDnsZones/A@2024-06-01' = {
    parent: tourPlannerCom
    name: 'tour-planner-client'
    properties: {
        ttl: 300
        aRecords: [
            {
                ipv4Address: tourPlannerClientIp
            }
        ]
    }
}

resource tourPlannerServerRecord 'Microsoft.Network/privateDnsZones/A@2024-06-01' = {
    parent: tourPlannerCom
    name: 'tour-planner-server'
    properties: {
        ttl: 300
        aRecords: [
            {
                ipv4Address: tourPlannerServerIp
            }
        ]
    }
}


resource tourPlannerVirtualNetwork 'Microsoft.Network/privateDnsZones/virtualNetworkLinks@2024-06-01' = {
    parent: tourPlannerCom
    name: 'tour-planner-vnet-link'
    location: 'global'
    properties: {
        virtualNetwork: {
            id: vnetId
        }
        registrationEnabled: false
    }
}
