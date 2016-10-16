/**
 * Created by scottmmjackson on 10/16/16.
 */

import spark.Spark.*
import com.natpryce.konfig.*
import org.apache.log4j.Logger
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.index.query.QueryBuilders.*
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.elasticsearch.index.IndexNotFoundException
import java.net.InetAddress

fun main(args: Array<String>) {
    val server = Server()
    server.run()
}

class Server() {
    object server : PropertyGroup() {
        val port by intType
        val elasticServer by stringType
        val elasticPort by intType
        val elasticIndex by stringType
    }
    val logger = Logger.getLogger(this.javaClass)
    val elastic = TransportClient
            .builder()
            .settings(Settings.EMPTY)
            .build()
    val config = EnvironmentVariables() overriding
            ConfigurationProperties
            .fromResource("defaults.properties")
    fun run() {
        val port = config[server.port]
        port(config[server.port])
        val elasticServer = config[server.elasticServer]
        val elasticPort = config[server.elasticPort]
        elastic.addTransportAddress(
                InetSocketTransportAddress(
                        InetAddress.getByName(elasticServer), elasticPort
                )
        )
        routes()
    }

    fun routes() {
        get("/") { req, res -> "it works!" }
        get("/search/:query") { req, res ->
            try {
                val query = req.params(":query")
                val index = config[server.elasticIndex]
                val elasticQuery = boolQuery()
                        .must(
                                matchQuery("_all", query)
                        )
                val response = elastic.prepareSearch(index)
                        .setQuery(elasticQuery)
                        .execute().actionGet()
                response.toString()
            } catch (e : Exception) {
                logger.error("""$e""")
                res.status(500)
                """{
                "success": "false",
                "error": "Internal Server Error",
                "code": 500
                }"""
            }
        }
    }
}