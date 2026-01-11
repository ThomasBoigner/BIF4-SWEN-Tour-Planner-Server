param vnetId string
param dataBaseSubnetId string
param databaseAdminUsername string
@secure()
param databaseAdminPassword string

var tourPlannerDatabaseName = 'tour-planner-database'
var privateDnsZoneName = 'tour-planner.postgres.database.azure.com'

resource tourPlannerDatabaseCom 'Microsoft.Network/privateDnsZones@2024-06-01' = {
    name: privateDnsZoneName
    location: 'global'
}

resource privateDnsZoneVnetLink 'Microsoft.Network/privateDnsZones/virtualNetworkLinks@2024-06-01' = {
    parent: tourPlannerDatabaseCom
    name: 'postgres-dns-link'
    location: 'global'
    properties: {
        virtualNetwork: {
            id: vnetId
        }
        registrationEnabled: false
    }
}

resource tourPlannerPostgresDatabase 'Microsoft.DBforPostgreSQL/flexibleServers@2024-08-01' = {
    name: tourPlannerDatabaseName
    location: 'Italy North'
    sku: {
        name: 'Standard_B1ms'
        tier: 'Burstable'
    }
    properties: {
        replica: {
            role: 'Primary'
        }
        storage: {
            iops: 120
            tier: 'P4'
            storageSizeGB: 32
            autoGrow: 'Disabled'
        }
        network: {
            publicNetworkAccess: 'Disabled'
            delegatedSubnetResourceId: dataBaseSubnetId
            privateDnsZoneArmResourceId: tourPlannerDatabaseCom.id
        }
        dataEncryption: {
            type: 'SystemManaged'
        }
        version: '17'
        administratorLogin: databaseAdminUsername
        administratorLoginPassword: databaseAdminPassword
        availabilityZone: '2'
        backup: {
            backupRetentionDays: 7
            geoRedundantBackup: 'Disabled'
        }
        highAvailability: {
            mode: 'Disabled'
        }
        maintenanceWindow: {
            customWindow: 'Disabled'
            dayOfWeek: 0
            startHour: 0
            startMinute: 0
        }
        replicationRole: 'Primary'
    }
}

resource tourPlannerDatabase 'Microsoft.DBforPostgreSQL/flexibleServers/databases@2024-08-01' = {
    parent: tourPlannerPostgresDatabase
    name: 'tour-planner-database'
    properties: {
        charset: 'UTF8'
        collation: 'en_US.utf8'
    }
}

output databaseFqdn string = tourPlannerPostgresDatabase.properties.fullyQualifiedDomainName