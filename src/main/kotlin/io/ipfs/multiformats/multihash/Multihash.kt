package io.ipfs.multiformats.multihash

import org.apache.commons.codec.binary.Base32
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex
import org.komputing.kbase58.decodeBase58
import org.komputing.kbase58.encodeToBase58String
import java.math.BigInteger


/**
 * changjiashuai@gmail.com.
 *
 * Created by CJS on 2018/7/14.
 */
const val ERR_UNKNOWN_CODE = "unknown multihash code"
const val ERR_TOO_SHORT = "multihash too short. must be >= 2 bytes"
const val ERR_TOO_LONG = "multihash too long. must be < 129 bytes"
const val ERR_LEN_NOT_SUPPORTED = "multihash does not yet support digests longer than 127 bytes"
const val ERR_INVALID_MULTIHASH = "input isn't valid multihash"
const val ERR_VARINT_BUFFER_SHORT = "uvarint: buffer too small"
const val ERR_VARINT_TOO_LONG = "uvarint: varint too big (max 64bit)"

data class DecodedMultihash(val code: Long, val name: String, val length: Int, val digest: ByteArray)

/**
 * Multihash is byte array with the following form:
 * <hash function code><digest size><hash function output>.
 */
class Multihash(val raw: ByteArray) {

    /**
     * @return the hex-encoded representation of a multihash.
     */
    fun toHexString(): String {
        return Hex.encodeHexString(raw)
    }

    /**
     * @return returns the base32-encoded representation of a multihash.
     */
    fun toBase32String(): String {
        return Base32().encodeToString(raw)
    }

    /**
     * @return returns the base58-encoded representation of a multihash.
     */
    fun toBase58String(): String {
        return raw.encodeToBase58String()
    }

    /**
     * @return returns the base64-encoded representation of a multihash.
     */
    fun toBase64String(): String {
        return Base64.encodeBase64String(raw)
    }

    /**
     * @return is an alias toHexString().
     */
    override fun toString(): String {
        return toHexString()
    }

    companion object {

        const val MAX_VARINT_LEN_64 = 10

        /**
         * @param s hex-encoded string
         * @return parses a hex-encoded multihash.
         */
        fun fromHexString(s: String): Multihash {
            return cast(Hex.decodeHex(s))
        }

        /**
         * @param base32 base32 encoded string
         * @return parses a base32-encoded multihash
         */
        fun fromBase32String(base32: String): Multihash {
            return cast(Base32().decode(base32))
        }

        /**
         * @param base58 base58 encoded string
         * @return parses a base58-encoded multihash.
         */
        fun fromBase58String(base58: String): Multihash {
            val buf = base58.decodeBase58()
            return cast(buf)
        }

        /**
         * @param base64 base6 encoded string
         * @return parses a base64-encoded multihash.
         */
        fun fromBase64String(base64: String): Multihash {
            return cast(Base64.decodeBase64(base64))
        }

        fun cast(buf: ByteArray): Multihash {
            val dm = decode(buf)
            if (!isValidCode(dm.code)) {
                throw IllegalStateException(ERR_UNKNOWN_CODE)
            }
            return Multihash(buf)
        }

        /**
         * Encode a hash digest along with the specified function code.
         * Note: the length is derived from the length of the digest itself.
         *
         * @param hash hash function calc digest
         * @param code hash function code
         * @return <hash function code varint><digest size varint><hash function output>
         */
        fun encode(digest: ByteArray, code: Long): ByteArray {
            if (!isValidCode(code)) {
                throw IllegalStateException(ERR_UNKNOWN_CODE)
            }
            return VarInt.encodeVarintLong(code).plus(VarInt.encodeVarintLong(digest.size.toLong())).plus(digest)
        }

        fun encodeByName(buf: ByteArray, name: String): ByteArray {
            val code = Type.names[name] ?: throw IllegalStateException(ERR_UNKNOWN_CODE)
            return encode(buf, code)
        }

        fun decode(buf: ByteArray): DecodedMultihash {
            if (buf.size < 2) {
                throw IllegalStateException(ERR_TOO_SHORT)
            }
            val (code, buf1) = uvarint(buf)
            val (length, buf2) = uvarint(buf1)
            if (length > Int.MAX_VALUE) {
                throw IllegalStateException("digest too long, supporting only <= 2^31-1")
            }
            val name = Type.codes[code] ?: throw IllegalStateException(ERR_UNKNOWN_CODE)
            val dm = DecodedMultihash(code, name, length.toInt(), buf2)
            if (dm.digest.size != dm.length) {
                throw IllegalStateException("multihash length inconsistent: $dm")
            }
            return dm
        }

        fun uvarint(buf: ByteArray): Pair<Long, ByteArray> {
            val (result, shift) = VarInt.decodeVarInt(buf)
            if (shift == 0) {
                throw IndexOutOfBoundsException(ERR_VARINT_BUFFER_SHORT)
            } else {
                val size = buf.size
                return Pair(result, buf.sliceArray(IntRange(shift / 7, size - 1)))
            }
        }

        fun isValidCode(code: Long): Boolean {
            if (isAppCode(code)) return true
            if (Type.hasCode(code)) return true
            return false
        }

        fun isAppCode(code: Long): Boolean {
            return code in 0 until 0x10  //[0, 16)
        }
    }
}