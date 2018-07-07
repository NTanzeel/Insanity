package com.insanity.rs2.net.decoder;

import com.insanity.Insanity;
import com.insanity.rs2.model.player.Details;
import com.insanity.rs2.net.packets.Builder;
import com.insanity.rs2.net.security.ISAACCipher;
import com.insanity.rs2.util.BufferUtility;
import com.insanity.rs2.util.NameUtility;
import com.insanity.rs2.world.World;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.security.SecureRandom;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author ntanzeel
 * @version 1.0.0
 * @since 24/06/2017.
 */
public class LoginDecoder extends ByteToMessageDecoder {

    /**
     * Logger instance.
     */
    private static final Logger logger = Logger.getLogger(LoginDecoder.class.getName());

    /**
     * Secure random number generator.
     */
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * Initial login response.
     */
    private static final byte[] INITIAL_RESPONSE = new byte[]{0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0, 0x0};

    private LoginState state = LoginState.OP_CODE;

    /*
     * We generated the server session key using a SecureRandom class for security.
     */
    private long serverKey = RANDOM.nextLong();

    private int loginSize = 0;
    private int loginEncryptSize = 0;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        switch (state) {
            case OP_CODE:
                if (!in.isReadable(1)) {
                    return;
                }

                /*
                 * Here we read the first opcode which indicates the type  of connection.
                 *
                 * 14 = game
                 * 15 = update
                 *
                 * Updating is disabled in the vast majority of 317 clients.
                 */
                int opcode = in.readByte() & 0xFF;

                if (opcode != 14) {
                    logger.info("Invalid opcode: " + opcode);
                    ctx.close();
                }

                state = LoginState.KEY_EXCHANGE;
                break;

            case KEY_EXCHANGE:
                /*
                 * The name hash is a simple hash of the name which is suspected to be used to select the
                 * appropriate login server.
				 */
                @SuppressWarnings("unused") int nameHash = in.readByte() & 0xFF;

				/*
                 * The initial response is just 0s which the client is set to ignore (probably some sort of
                 * modification).
				 */
                ctx.channel().writeAndFlush(new Builder().writeBytes(INITIAL_RESPONSE).writeByte((byte) 0).writeLong(serverKey).toPacket());
                state = LoginState.PRE_ENCRYPTION;
                break;

            case PRE_ENCRYPTION:
                if (!in.isReadable(2)) {
                    return;
                }
                /*
                 * We read the type of login.
				 *
				 * 16 = normal
				 * 18 = reconnection
				 */
                int loginOpcode = in.readByte() & 0xFF;
                if (loginOpcode != 16 && loginOpcode != 18) {
                    logger.info("Invalid login opcode : " + loginOpcode);
                    ctx.channel().close();
                    return;
                }

				/*
                 * We read the size of the login packet.
				 */
                this.loginSize = in.readByte() & 0xFF;

				/*
                 * And calculated how long the encrypted block will be.
				 */
                this.loginEncryptSize = loginSize - (36 + 1 + 1 + 2);

				/*
                 * This could be invalid so if it is we ignore it.
				 */
                if (loginEncryptSize <= 0) {
                    logger.info("Encrypted packet size zero or negative : " + loginEncryptSize);
                    ctx.channel().close();
                    return;
                }
                state = LoginState.Login;
                break;
            case Login:
                if (!in.isReadable(this.loginSize)) {
                    return;
                }

                /*
                 * We read the magic ID which is 255 (0xFF) which indicates this is the real login packet.
				 */
                int magicId = in.readByte() & 0xFF;
                if (magicId != 255) {
                    logger.info("Incorrect magic id : " + magicId);
                    ctx.channel().close();
                    return;
                }

				/*
                 * We now read a short which is the client version and check if it equals 317.
				 */
                int version = in.readUnsignedShort() & 0xFFFF;
                if (version != Insanity.getInstance().getVersion()) {
                    logger.info("Incorrect version : " + version);
                    ctx.channel().close();
                    return;
                }

				/*
                 * The following byte indicates if we are using a low memory version.
				 */
                @SuppressWarnings("unused") boolean lowMemoryVersion = (in.readByte() & 0xFF) == 1;

				/*
                 * We now read the cache indices.
				 */
                for (int i = 0; i < 9; i++) {
                    in.readInt();
                }

				/*
                 * The encrypted size includes the size byte which we don't
				 * need.
				 */
                this.loginEncryptSize--;

				/*
                 * We check if there is a mismatch in the sizing.
				 */
                int reportedSize = in.readByte() & 0xFF;
                if (reportedSize != this.loginEncryptSize) {
                    logger.info("Packet size mismatch (expected : " + this.loginEncryptSize + ", reported : " + reportedSize + ")");
                    ctx.channel().close();
                    return;
                }

				/*
                 * We now read the encrypted block opcode (although in most
				 * 317 clients and this server the RSA is disabled) and
				 * check it is equal to 10.
				 */
                int blockOpcode = in.readByte() & 0xFF;
                if (blockOpcode != 10) {
                    logger.info("Invalid login block opcode : " + blockOpcode);
                    ctx.channel().close();
                    return;
                }

				/*
                 * We read the client's session key.
				 */
                long clientKey = in.readLong();

				/*
                 * And verify it has the correct server session key.
				 */
                long reportedServerKey = in.readLong();
                if (reportedServerKey != this.serverKey) {
                    logger.info("Server key mismatch (expected : " + serverKey + ", reported : " + reportedServerKey + ")");
                    ctx.channel().close();
                    return;
                }

				/*
                 * The UID, found in random.dat in newer clients and
				 * uid.dat in older clients is a way of identifying a
				 * computer.
				 *
				 * However, some clients send a hardcoded or random UID,
				 * making it useless in the private server scene.
				 */
                int uid = in.readInt();

				/*
                 * We read and format the name and passwords.
				 */
                String username = NameUtility.formatName(BufferUtility.getRS2String(in));
                String password = BufferUtility.getRS2String(in);

				/*
                 * And setup the ISAAC cipher which is used to encrypt and
				 * decrypt opcodes.
				 *
				 * However, without RSA, this is rendered useless anyway.
				 */
                int[] sessionKey = new int[4];
                sessionKey[0] = (int) (clientKey >> 32);
                sessionKey[1] = (int) clientKey;
                sessionKey[2] = (int) (serverKey >> 32);
                sessionKey[3] = (int) serverKey;

                ISAACCipher inCipher = new ISAACCipher(sessionKey);
                for (int i = 0; i < 4; i++) {
                    sessionKey[i] += 50;
                }
                ISAACCipher outCipher = new ISAACCipher(sessionKey);

				/*
                 * Now, the login has completed, and we do the appropriate
				 * things to fire off the chain of events which will load
				 * and check the saved games etc.
				 */
                ctx.channel().pipeline().replace("loginDecoder", "decoder", new Decoder());

                World.getInstance().getEntities().load(new Details(ctx.channel(), uid, username, password, inCipher, outCipher));

                break;
        }
    }

    private enum LoginState {
        OP_CODE, KEY_EXCHANGE, PRE_ENCRYPTION, Login
    }
}
