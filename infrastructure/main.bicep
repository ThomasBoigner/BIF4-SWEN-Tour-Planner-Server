param location string
param databaseAdminUsername string
@secure()
param databaseAdminPassword string
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

module databaseModule 'modules/database.bicep' = {
    name: 'databaseDeploy'
    params: {
        vnetId: networkModule.outputs.vnetId
        dataBaseSubnetId: networkModule.outputs.databaseSubnetId
        databaseAdminUsername: databaseAdminUsername
        databaseAdminPassword: databaseAdminPassword
    }
}

module containerInstanceModule 'modules/containerinstance.bicep' = {
    name: 'containerInstanceDeploy'
    params: {
        location: location
        registryName: registryName
        registryUsername: registryUsername
        registryPassword: registryPassword
        privateSubnetId: networkModule.outputs.privateSubnetId
        publicSubnetId: networkModule.outputs.publicSubnetId
        databaseFqdn: databaseModule.outputs.databaseFqdn
        databaseAdminUsername: databaseAdminUsername
        databaseAdminPassword: databaseAdminPassword
    }
}

module dnsZoneModule 'modules/dnszone.bicep' = {
    name: 'dnsZoneDeploy'
    params: {
        vnetId: networkModule.outputs.vnetId
        tourPlannerClientIp: containerInstanceModule.outputs.tourPlannerClientIp
        tourPlannerServerIp: containerInstanceModule.outputs.tourPlannerServerIp
    }
}

module Identifier 'modules/applicationgateway.bicep' = {
    name: 'applicationGatewayDeploy'
    dependsOn: [
        dnsZoneModule
    ]
    params: {
        location: location
        applicationGatewaySubnetId: networkModule.outputs.applicationGatewaySubnetId
        tourPlannerClientIpId: networkModule.outputs.tourPlannerClientIpId
    }
}