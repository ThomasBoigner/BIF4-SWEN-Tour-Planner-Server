param location string
param registryName string
param registryUsername string
@secure()
param registryPassword string

module networkModule 'modules/networking.bicep' = {
    name: 'networkDeploy'
    params: {
        location: location
    }
}

module containerInstanceModule 'modules/containerinstance.bicep' = {
    name: 'containerInstanceDeploy'
    dependsOn: [
    	networkModule
    ]
    params: {
        location: location
        registryName: registryName
        registryUsername: registryUsername
        registryPassword: registryPassword
        privateSubnetId: networkModule.outputs.privateSubnetId
        publicSubnetId: networkModule.outputs.publicSubnetId
    }
}

module dnsZoneModule 'modules/dnszone.bicep' = {
    name: 'dnsZoneDeploy'
    dependsOn: [
    	containerInstanceModule
    ]
    params: {
        tourPlannerClientIp: containerInstanceModule.outputs.tourPlannerClientIp
        tourPlannerServerIp: containerInstanceModule.outputs.tourPlannerServerIp
    }
}

module Identifier 'modules/applicationgateway.bicep' = {
    name: 'applicationGatewayDeploy'
    dependsOn: [
    	networkModule, dnsZoneModule
    ]
    params: {
        location: location
        applicationGatewaySubnetId: networkModule.outputs.applicationGatewaySubnetId
        tourPlannerClientIpId: networkModule.outputs.tourPlannerClientIpId
    }
}