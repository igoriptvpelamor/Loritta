package com.mrpowergamerbr.loritta.utils.misc

import com.github.andrewoma.kwery.core.DefaultSession
import com.github.andrewoma.kwery.core.dialect.PostgresDialect
import com.google.gson.Gson
import com.mrpowergamerbr.loritta.userdata.ServerConfig
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource

// TODO: Remove this!!!
fun main(args: Array<String>) {
	val config = HikariConfig()

	config.jdbcUrl = "jdbc:postgresql://localhost/postgres"
	config.username = "postgres"
	config.password = "admin"

	config.maximumPoolSize = 10
	config.isAutoCommit = false
	config.addDataSourceProperty("cachePrepStmts", "true")
	config.addDataSourceProperty("prepStmtCacheSize", "250")
	config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048")

	val dataSource = HikariDataSource(config)

	val connection = dataSource.getConnection();

	connection.autoCommit = true

	val randomId = Long.MAX_VALUE - 1

	var dummy = ServerConfig().apply{
		guildId = randomId.toString()
		this.commandPrefix = "postgresql"
	}

	val session = DefaultSession(connection, PostgresDialect()) // Standard JDBC connection

	session.update("""INSERT INTO public.servers (id, data)
VALUES (:guildId, cast(:jsonConfig as json))
ON CONFLICT (id) DO UPDATE
  SET data = cast(:jsonConfig as json);""", mapOf("guildId" to randomId, "jsonConfig" to Gson().toJson(dummy, ServerConfig::class.java)))
	/* println("Generated ID: " + randomId)

	val session = DefaultSession(connection, PostgresDialect()) // Standard JDBC connection

	val insertSql = """INSERT INTO public.servers (id, data) VALUES (:guildId, cast(:jsonConfig as json))""";

	session.insert(insertSql, mapOf("guildId" to randomId, "jsonConfig" to Gson().toJson(dummy))) {

	}

	val servers = session.select("""SELECT * FROM public.servers WHERE id = :guildId""", mapOf("guildId" to randomId)) { row ->
		val json = row.string("data")

		val config = Gson().fromJson(json, ServerConfig::class.java)

		println(config.guildId)
	} */
	/* var pstmt2 = connection.prepareStatement("""SELECT data FROM public.servers WHERE data->>'guildId' = ?""")
	pstmt2.setString(1, randomId.toString())

	var resultSet = pstmt2.executeQuery()

	while (resultSet.next()) {
		val json = resultSet.getString(1)

		val config = Gson().fromJson(json, ServerConfig::class.java)

		println(config.guildId)
	} */
	/* var pstmt = connection.prepareStatement("""SELECT info FROM public.orders
where info->>'customer' LIKE 'L%' """);

	var resultSet = pstmt.executeQuery();
	while (resultSet.next())
	{
		val json = resultSet.getString(1)

		println(json)
		val gson = Gson()

		val orderInfo = gson.fromJson(json, OrderInfo::class.java)

		println("Customer: " + orderInfo.customer);
		println(orderInfo.items.product + " - " + orderInfo.items.product)
		// mSystem.out.println(resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
	} */
}

data class OrderInfo(
		val customer: String,
		val items: ProductInfo
)

data class ProductInfo(
		val product: String,
		val qty: Int
)