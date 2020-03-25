package io.ipfs.multiformats.multihash

import okio.Buffer
import org.apache.commons.codec.binary.Base32
import org.apache.commons.codec.binary.Base64
import org.apache.commons.codec.binary.Hex
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.komputing.kvarint.writeVarUInt
import java.lang.IllegalArgumentException

fun Int.encodeVarUInt() : ByteArray {
    val buffer = Buffer()
    buffer.writeVarUInt(toUInt())
    return buffer.readByteArray()
}
/**
 * changjiashuai@gmail.com.
 *
 * Created by CJS on 2018/7/15.
 */
@OptIn(ExperimentalUnsignedTypes::class)
class MultihashTest {

    //code,name
    val tCodes = hashMapOf(
            0x00 to "id",
            0x11 to "sha1",
            0x12 to "sha2-256",
            0x13 to "sha2-512",
            0x14 to "sha3-512",
            0x15 to "sha3-384",
            0x16 to "sha3-256",
            0x17 to "sha3-224",
            0x56 to "dbl-sha2-256",
            0x22 to "murmur3",
            0x1A to "keccak-224",
            0x1B to "keccak-256",
            0x1C to "keccak-384",
            0x1D to "keccak-512",
            0x18 to "shake-128",
            0x19 to "shake-256"
    )

    //hex,code,name
    val testCases = listOf(
            Triple("2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae", 0x00, "id"),
            Triple("", 0x00, "id"),
            Triple("0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33", 0x11, "sha1"),
            Triple("0beec7b5", 0x11, "sha1"),
            Triple("2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae", 0x12, "sha2-256"),
            Triple("2c26b46b", 0x12, "sha2-256"),
            Triple("2c26b46b68ffc68ff99b453c1d30413413", 0xb240, "blake2b-512"),
            Triple("243ddb9e", 0x22, "murmur3"),
            Triple("f00ba4", 0x1b, "keccak-256"),
            Triple("f84e95cb5fbd2038863ab27d3cdeac295ad2d4ab96ad1f4b070c0bf36078ef08", 0x18, "shake-128"),
            Triple("1af97f7818a28edfdfce5ec66dbdc7e871813816d7d585fe1f12475ded5b6502b7723b74e2ee36f2651a10a8eaca72aa9148c3c761aaceac8f6d6cc64381ed39", 0x19, "shake-256"),
//            Triple("4bca2b137edc580fe50a88983ef860ebaca36c857b1f492839d6d7392452a63c82cbebc68e3b70a2a1480b4bb5d437a7cba6ecf9d89f9ff3ccd14cd6146ea7e7", 0x14, "sha3-512"),
            Triple("4bca2b137edc580fe50a88983ef860ebaca36c857b1f492839d6d7392452a63c82cbebc68e3b70a2a1480b4bb5d437a7cba6ecf9d89f9ff3ccd14cd6146ea7e7", 0x14, "sha3")
    )


    fun toMultihash(triple: Triple<String, Int, String>): Multihash {
        val raw = Hex.decodeHex(triple.first)
        val encode = Multihash.encode(raw, triple.second.toUInt())
        return Multihash.cast(encode)
    }

    @Test
    fun encode() {
        testCases.forEach {
            val ob = Hex.decodeHex(it.first)
            val excepted = it.second.encodeVarUInt().plus(ob.size.encodeVarUInt()).plus(ob)

            val actual = Multihash.encode(ob, it.second.toUInt())
            assertEquals(excepted.contentToString(), actual.contentToString())

            val actualByName = Multihash.encodeByName(ob, it.third)
            assertEquals(excepted.contentToString(), actualByName.contentToString())

            val mh = toMultihash(it)
            assertEquals(excepted.contentToString(), mh.raw.contentToString())
        }
    }

    @Test
    fun encodeByName() {
        val buf = Hex.decodeHex("0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33")
        val mhBuf = Multihash.encodeByName(buf, "sha1")
        val mhHex = Hex.encodeHexString(mhBuf)
        assertEquals("11140beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33", mhHex)
    }

    @Test
    fun decode() {
        testCases.forEach {
            val ob = Hex.decodeHex(it.first)
            val excepted = it.second.encodeVarUInt().plus(ob.size.encodeVarUInt()).plus(ob)

            val actual = Multihash.decode(excepted)
            assertEquals(it.second.toUInt(), actual.code)
            assertEquals(it.third, actual.name)
            assertEquals(ob.size.toUInt(), actual.length)
            assertEquals(ob.contentToString(), actual.digest.contentToString())
        }
    }

    @Test
    fun decode2() {
        val buf = Hex.decodeHex("0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33")
        val mhBuf = Multihash.encodeByName(buf, "sha1")
        val o = Multihash.decode(mhBuf)
        val mhHex = Hex.encodeHexString(o.digest)
        val actual = String.format("%s 0x%x %d %s", o.name, o.code.toInt(), o.length.toInt(), mhHex)
        val excepted = "sha1 0x11 20 0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33"
        assertEquals(excepted, actual)
    }

    @Test
    fun testTable() {
        tCodes.forEach { (code, name) ->
            assertEquals(code.toUInt(), Type.names[name])
            if (name != "sha3" && name != "sha3-512") {
                assertEquals(name, Type.codes[code.toUInt()])
            }
        }
    }

    @Test
    fun isValidCode() {
        for (i in 0..0xff) {
            val ok = tCodes[i] != null
            assertEquals(Multihash.isAppCode(i.toUInt()) || ok, Multihash.isValidCode(i.toUInt()))
        }
    }

    @Test
    fun isAppCode() {
        for (i in 0..0xff) {
            val b = i in 0..15
            assertEquals(b, Multihash.isAppCode(i.toUInt()))
        }
    }

    @Test(expected = IllegalStateException::class)
    fun cast() {
        testCases.forEach {
            val ob = Hex.decodeHex(it.first)
            val excepted = it.second.encodeVarUInt().plus(ob.size.encodeVarUInt()).plus(ob)
            assertNotNull(Multihash.cast(excepted))
            println("cast=${Multihash.cast(ob)}")
        }
    }

    @Test
    fun hex() {
        testCases.forEach {
            val ob = Hex.decodeHex(it.first)
            val buf = it.second.encodeVarUInt().plus(ob.size.encodeVarUInt()).plus(ob)
            val hexString = Hex.encodeHexString(buf)
            val mh = Multihash.fromHexString(hexString)
            assertEquals(buf.contentToString(), mh.raw.contentToString())
            assertEquals(hexString, mh.toHexString())
        }
    }


    @Test
    fun base32() {
        testCases.forEach {
            val base32 = Base32()
            val ob = base32.decode(it.first)
            val buf = it.second.encodeVarUInt().plus(ob.size.encodeVarUInt()).plus(ob)
            val base32String = base32.encodeToString(buf)
            val mh = Multihash.fromBase32String(base32String)
            assertEquals(buf.contentToString(), mh.raw.contentToString())
            assertEquals(base32String, mh.toBase32String())
        }
    }

    @Test
    fun base64() {
        testCases.forEach {
            val ob = Base64.decodeBase64(it.first)
            val buf = it.second.encodeVarUInt().plus(ob.size.encodeVarUInt()).plus(ob)
            val base64String = Base64.encodeBase64String(buf)
            val mh = Multihash.fromBase64String(base64String)
            assertEquals(buf.contentToString(), mh.raw.contentToString())
            assertEquals(base64String, mh.toBase64String())
        }
    }

    @Test(expected = NumberFormatException::class)
    fun decodeErrorInvalid() {
        val mh = Multihash.fromBase58String("/ipfs/QmQTw94j68Dgakgtfd45bG3TZG6CAfc427UVRH4mugg4q4")
    }

    @Test(expected = IllegalArgumentException::class)
    fun varintTooLong() {
        val tooLongBytes = byteArrayOf(129.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 128.toByte(), 129.toByte(), 1.toByte())
        Multihash.cast(tooLongBytes)
    }

}