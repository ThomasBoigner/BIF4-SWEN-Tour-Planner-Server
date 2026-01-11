@minLength(5)
@maxLength(50)
param acrName string
param location string

resource acrResource 'Microsoft.ContainerRegistry/registries@2023-01-01-preview' = {
    name: acrName
    location: location
    sku: {
        name: 'Standard'
    }
    properties: {
        adminUserEnabled: false
    }
}