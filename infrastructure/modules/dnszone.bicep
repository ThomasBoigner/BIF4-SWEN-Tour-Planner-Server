param virtualNetwork string

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
        aRecords: [
            {
                ipv4Address: '10.0.1.1'
            }
        ]
    }
}

resource tourPlannerServerRecord 'Microsoft.Network/privateDnsZones/A@2024-06-01' = {
    parent: tourPlannerCom
    name: 'tour-planner-server'
    properties: {
        aRecords: [
            {
                ipv4Address: '10.0.2.1'
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
            id: virtualNetwork
        }
    }
}
