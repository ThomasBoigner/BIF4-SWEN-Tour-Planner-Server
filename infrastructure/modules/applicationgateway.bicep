// Work in progress, bin nicht fertig geworden
param location string

var applicationGatewayName = 'tour-planner-application-gateway'
param virtualNetworks_tour_planner_network_externalid string = '/subscriptions/779256dc-40a0-4f2b-a165-864f5b859ba5/resourceGroups/tour-planner/providers/Microsoft.Network/virtualNetworks/tour-planner-network'
param publicIPAddresses_tour_planner_client_ip_externalid string = '/subscriptions/779256dc-40a0-4f2b-a165-864f5b859ba5/resourceGroups/tour-planner/providers/Microsoft.Network/publicIPAddresses/tour-planner-client-ip'

resource applicationGateways_tour_planner_application_gateway_name_resource 'Microsoft.Network/applicationGateways@2024-07-01' = {
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
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/gatewayIPConfigurations/appGatewayIpConfig'
        properties: {
          subnet: {
            id: '${virtualNetworks_tour_planner_network_externalid}/subnets/application-gateway-subnet'
          }
        }
      }
    ]
    sslCertificates: []
    trustedRootCertificates: []
    trustedClientCertificates: []
    sslProfiles: []
    frontendIPConfigurations: [
      {
        name: 'appGwPublicFrontendIpIPv4'
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/frontendIPConfigurations/appGwPublicFrontendIpIPv4'
        properties: {
          privateIPAllocationMethod: 'Dynamic'
          publicIPAddress: {
            id: publicIPAddresses_tour_planner_client_ip_externalid
          }
        }
      }
    ]
    frontendPorts: [
      {
        name: 'port_80'
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/frontendPorts/port_80'
        properties: {
          port: 80
        }
      }
    ]
    backendAddressPools: [
      {
        name: 'tour-planner-backend-pool'
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/backendAddressPools/tour-planner-backend-pool'
        properties: {
          backendAddresses: [
            {
              ipAddress: '10.0.1.4'
            }
          ]
        }
      }
    ]
    loadDistributionPolicies: []
    backendHttpSettingsCollection: [
      {
        name: 'tour-planner-backend-settings'
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/backendHttpSettingsCollection/tour-planner-backend-settings'
        properties: {
          port: 80
          protocol: 'Http'
          cookieBasedAffinity: 'Disabled'
          pickHostNameFromBackendAddress: false
          requestTimeout: 20
        }
      }
    ]
    backendSettingsCollection: []
    httpListeners: [
      {
        name: 'tour-planner-listener'
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/httpListeners/tour-planner-listener'
        properties: {
          frontendIPConfiguration: {
            id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/frontendIPConfigurations/appGwPublicFrontendIpIPv4'
          }
          frontendPort: {
            id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/frontendPorts/port_80'
          }
          protocol: 'Http'
          hostNames: []
          requireServerNameIndication: false
          customErrorConfigurations: []
        }
      }
    ]
    listeners: []
    urlPathMaps: []
    requestRoutingRules: [
      {
        name: 'tour-planner-routing-rule'
        id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/requestRoutingRules/tour-planner-routing-rule'
        properties: {
          ruleType: 'Basic'
          priority: 100
          httpListener: {
            id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/httpListeners/tour-planner-listener'
          }
          backendAddressPool: {
            id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/backendAddressPools/tour-planner-backend-pool'
          }
          backendHttpSettings: {
            id: '${applicationGateways_tour_planner_application_gateway_name_resource.id}/backendHttpSettingsCollection/tour-planner-backend-settings'
          }
        }
      }
    ]
    routingRules: []
    probes: []
    rewriteRuleSets: []
    redirectConfigurations: []
    privateLinkConfigurations: []
    enableHttp2: true
    enableFIPS: false
    autoscaleConfiguration: {
      minCapacity: 0
      maxCapacity: 10
    }
  }
}
