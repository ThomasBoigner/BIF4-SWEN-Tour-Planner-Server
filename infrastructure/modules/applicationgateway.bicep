param location string
param applicationGatewaySubnetId string
param tourPlannerClientIpId string

var applicationGatewayName = 'tour-planner-application-gateway'

resource tourPlannerApplicationGateway 'Microsoft.Network/applicationGateways@2024-07-01' = {
    name: applicationGatewayName
    location: location
    properties: {
        sku: {
            name: 'Standard_v2'
            tier: 'Standard_v2'
        }
        gatewayIPConfigurations: [
            {
                name: 'appGatewayIpConfig'
                properties: {
                    subnet: {
                        id: applicationGatewaySubnetId
                    }
                }
            }
        ]
        frontendIPConfigurations: [
            {
                name: 'appGwPublicFrontendIpIPv4'
                properties: {
                    privateIPAllocationMethod: 'Dynamic'
                    publicIPAddress: {
                        id: tourPlannerClientIpId
                    }
                }
            }
        ]
        frontendPorts: [
            {
                name: 'port_80'
                properties: {
                    port: 80
                }
            }
        ]
        backendAddressPools: [
            {
                name: 'tour-planner-backend-pool'
                properties: {
                    backendAddresses: [
                        {
                            fqdn: 'tour-planner-client.tour-planner.com'
                        }
                    ]
                }
            }
        ]
        backendHttpSettingsCollection: [
            {
                name: 'tour-planner-backend-settings'
                properties: {
                    port: 80
                    protocol: 'Http'
                    cookieBasedAffinity: 'Disabled'
                    pickHostNameFromBackendAddress: false
                    requestTimeout: 20
                }
            }
        ]
        httpListeners: [
            {
                name: 'tour-planner-listener'
                properties: {
                    frontendIPConfiguration: {
                        id: resourceId('Microsoft.Network/applicationGateways/frontendIPConfigurations', applicationGatewayName, 'appGwPublicFrontendIpIPv4')
                    }
                    frontendPort: {
                        id: resourceId('Microsoft.Network/applicationGateways/frontendPorts', applicationGatewayName, 'port_80')
                    }
                    protocol: 'Http'
                    requireServerNameIndication: false
                }
            }
        ]
        requestRoutingRules: [
            {
                name: 'tour-planner-routing-rule'
                properties: {
                    ruleType: 'Basic'
                    priority: 1
                    httpListener: {
                        id: resourceId('Microsoft.Network/applicationGateways/httpListeners', applicationGatewayName, 'tour-planner-listener')
                    }
                    backendAddressPool: {
                        id: resourceId('Microsoft.Network/applicationGateways/backendAddressPools', applicationGatewayName, 'tour-planner-backend-pool')
                    }
                    backendHttpSettings: {
                        id: resourceId('Microsoft.Network/applicationGateways/backendHttpSettingsCollection', applicationGatewayName, 'tour-planner-backend-settings')
                    }
                }
            }
        ]
        enableHttp2: true
        autoscaleConfiguration: {
            minCapacity: 0
            maxCapacity: 10
        }
    }
}
