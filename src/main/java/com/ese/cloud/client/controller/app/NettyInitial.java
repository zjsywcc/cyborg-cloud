package com.ese.cloud.client.controller.app;

/**
 * Created by wangchengcheng on 2017/12/9.
 */

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import javax.net.ssl.SSLException;
import javax.servlet.ServletContext;
import java.security.cert.CertificateException;

/**
 *服务端启动Netty
 */
@Controller
public class NettyInitial implements InitializingBean, ServletContextAware {

    static final boolean SSL = System.getProperty("ssl") != null;
    //服务端netty的端口
    static final int NETTYPORT = Integer.parseInt(System.getProperty("port", "9800"));

    @Override
    public void setServletContext(ServletContext arg0) {
        //使用线程启动Netty服务端，否则会阻塞线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    startNettyConnect();
                }catch (Exception e) {
                }
            }
        }).start();
    }
    @Override
    public void afterPropertiesSet() throws Exception {
    }

    public void startNettyConnect() throws CertificateException, SSLException {
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(),
                    ssc.privateKey());
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(10);
        EventLoopGroup workerGroup = new NioEventLoopGroup(10);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 2048)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch)
                                throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast("idle", new IdleStateHandler(300, 300, 300));
                            p.addLast(new ObjectEncoder(), new ObjectDecoder(
                                            ClassResolvers.cacheDisabled(null)),
                                    new NettyServerHandler());
                        }
                    });
            b.option(ChannelOption.TCP_NODELAY, true);
            //保持长连接状态
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            // Start the server.
            ChannelFuture f = b.bind(NETTYPORT).sync();
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        }catch (Exception e) {
            // TODO: handle exception
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}


/**
 * Netty模块消息处理
 * @author Administrator
 *
 */
class NettyServerHandler extends ChannelInboundHandlerAdapter{


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ctx.writeAndFlush("连接成功="+msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        super.userEventTriggered(ctx, evt);
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                // 读超时

            }
            if(event.state().equals(IdleState.WRITER_IDLE)){
                //写超时
                ctx.writeAndFlush("y");
            }
        }
    }
}
