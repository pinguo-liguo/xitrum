package xitrum.server

import java.net.InetSocketAddress
import java.util.concurrent.Executors

import org.jboss.netty.bootstrap.ServerBootstrap
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory

import xitrum.{Logger, Config}
import xitrum.action.routing.Routes

class Server extends Logger {
  def start {
    Routes.collectAndCompile

    val pipelineFactory = new ChannelPipelineFactory
    val bootstrap =
      new ServerBootstrap(new NioServerSocketChannelFactory(
        Executors.newCachedThreadPool, Executors.newCachedThreadPool))
    bootstrap.setPipelineFactory(pipelineFactory)
    bootstrap.setOption("reuseAddress",     true)
    bootstrap.setOption("child.tcpNoDelay", true)
    bootstrap.setOption("child.keepAlive",  true)
    bootstrap.bind(new InetSocketAddress(Config.httpPort))

    val mode = if (Config.isProductionMode) "production" else "development"
    logger.info("Xitrum started on port {} in {} mode", Config.httpPort, mode)
  }
}