param location string
param registryName string
param registryUsername string
param publicSubnetId string
param privateSubnetId string

var tourPlannerClientName = 'tour-planner-client'
var tourPlannerServerName = 'tour-planner-server'

resource tourPlannerClientContainer 'Microsoft.ContainerInstance/containerGroups@2023-05-01' = {
    name: tourPlannerClientName
    location: location
    properties: {
        containers: [
            {
                name: tourPlannerClientName
                properties: {
                    image: '${registryName}/bif4-swen-tour-planner-tour-planner-client:latest'
                    ports: [
                        {
                            protocol: 'TCP'
                            port: 80
                        }
                    ]
                    environmentVariables: []
                    resources: {
                        requests: {
                            cpu: 1
                            memoryInGB: 2
                        }
                    }
                }
            }
        ]
        imageRegistryCredentials: [
            {
                server: registryName
                username: registryUsername
            }
        ]
        restartPolicy: 'OnFailure'
        ipAddress: {
            ports: [
                {
                    protocol: 'TCP'
                    port: 80
                }
            ]
            ip: '10.0.1.1'
            type: 'Private'
        }
        osType: 'Linux'
        subnetIds: [
            {
                id: publicSubnetId
            }
        ]
    }
}



resource tourPlannerServerContainer 'Microsoft.ContainerInstance/containerGroups@2023-05-01' = {
    name: tourPlannerServerName
    location: location
    properties: {
        containers: [
            {
                name: tourPlannerServerName
                properties: {
                    image: '${registryName}/bif4-swen-tour-planner-tour-planner-server:latest'
                    ports: [
                        {
                            protocol: 'TCP'
                            port: 80
                        }
                        {
                            protocol: 'TCP'
                            port: 8080
                        }
                    ]
                    environmentVariables: []
                    resources: {
                        requests: {
                            memoryInGB: 2
                            cpu: 1
                        }
                    }
                }
            }
        ]
        imageRegistryCredentials: [
            {
                server: registryName
                username: registryUsername
            }
        ]
        restartPolicy: 'OnFailure'
        ipAddress: {
            ports: [
                {
                    protocol: 'TCP'
                    port: 80
                }
                {
                    protocol: 'TCP'
                    port: 8080
                }
            ]
            ip: '10.0.2.1'
            type: 'Private'
        }
        osType: 'Linux'
        subnetIds: [
            {
                id: privateSubnetId
            }
        ]
    }
}