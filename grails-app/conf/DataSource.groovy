hibernate {
    cache.use_second_level_cache = true
    cache.use_query_cache = true
    cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
	driverClassName = "com.mysql.jdbc.Driver"
}
// environment specific settings
environments {
    development {
        dataSource {
            pooled = true
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost:3306/pcc"
            username = "pcc"
            password = "pcc001"
            useUnicode = true
//            logSql = true
            characterEncoding = "utf-8"
        }
    }
	prodMintlab {
        dataSource {
            pooled = true
            dbCreate = "update"
			driverClassName = "org.postgresql.Driver"
			dialect = org.hibernate.dialect.PostgreSQLDialect
			url = "jdbc:postgresql://data1.zaaksysteem.nl:5432/bussum_publiekcc"
			username = "publiekcc"
			password = "SjEio!!778389"
            useUnicode = true
//            logSql = true
            characterEncoding = "utf-8"
        }
	}
	testMintlab {
        dataSource {
            pooled = true
            dbCreate = "update"
			driverClassName = "org.postgresql.Driver"
			dialect = org.hibernate.dialect.PostgreSQLDialect
			url = "jdbc:postgresql://data1.zaaksysteem.nl:5432/bussum_publiekcc_test"
			username = "publiekcc"
			password = "SjEio!!778389"
            useUnicode = true
//            logSql = true
            characterEncoding = "utf-8"
        }
	}
	devMintlab {
        dataSource {
            pooled = true
            dbCreate = "update"
			driverClassName = "org.postgresql.Driver"
			dialect = org.hibernate.dialect.PostgreSQLDialect
			url = "jdbc:postgresql://localhost:5432/pcc"
			username = "pcc"
			password = "pcc001"
            useUnicode = true
//            logSql = true
            characterEncoding = "utf-8"
        }
	}
    test {
		//nu nog gelijk aan development (voor webtest)
        dataSource {
            pooled = true
            dbCreate = "update"
            driverClassName = "com.mysql.jdbc.Driver"
            url = "jdbc:mysql://localhost:3306/pcc"
            username = "pcc"
            password = "pcc001"
            useUnicode = true
//            logSql = true
            characterEncoding = "utf-8"
        }
    }
    production {
        dataSource {
            pooled = true
            driverClassName = "com.mysql.jdbc.Driver"
            dbCreate = "update"
            username = "pcc"
            password = "Stein"
            url = "jdbc:mysql://vps280.directvps.nl:3306/pcc?autoReconnectForPools=true"
            useUnicode = true
            characterEncoding = "utf-8"
            properties {
                maxActive = 50
                maxIdle = 25
                minIdle = 1
                initialSize = 1
                minEvictableIdleTimeMillis = 60000
                timeBetweenEvictionRunsMillis = 60000
                numTestsPerEvictionRun = 3
                maxWait = 10000

                testOnBorrow = true
                testWhileIdle = true
                testOnReturn = false

                validationQuery = "SELECT 1"
            }
        }
    }
}
