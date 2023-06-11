package com.example.arboretum.utils

interface Mapper<Data, Domain> {
    fun dataToDomain(data:Data): Domain
    fun domainToData(domain: Domain): Data
}