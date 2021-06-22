package io.ipfs.multiformats.multihash;

enum class Type(val code: UInt, val named: String, val length: UInt) {

    ID(0x0U, "id", 0U),
    SHA1(0x11U, "sha1", 20U),
    SHA2_256(0x12U, "sha2-256", 32U),
    SHA2_512(0x13U, "sha2-512", 64U),
    SHA3_224(0x17U, "sha3-224", 28U),
    SHA3_256(0x16U, "sha3-256", 32U),
    SHA3_384(0x15U, "sha3-384", 48U),
    SHA3_512(0x14U, "sha3-512", 64U),
    SHA3(0x14U, "sha3", 64U),
    KECCAK_224(0x1AU, "keccak-224", 28U),
    KECCAK_256(0x1BU, "keccak-256", 32U),
    KECCAK_384(0x1CU, "keccak-384", 48U),
    KECCAK_512(0x1DU, "keccak-512", 64U),

    SHAKE_128(0x18U, "shake-128", 32U),
    SHAKE_256(0x19U, "shake-256", 64U),

    DBL_SHA2_256(0x56U, "dbl-sha2-256", 32U),
    MURMUR3(0x22U, "murmur3", 4U),

    BLAKE2B_8(0xb201U, "blake2b-8", 1U),
    BLAKE2B_16(0xb202U, "blake2b-16", 2U),
    BLAKE2B_24(0xb203U, "blake2b-24", 3U),
    BLAKE2B_32(0xb204U, "blake2b-32", 4U),
    BLAKE2B_40(0xb205U, "blake2b-40", 5U),
    BLAKE2B_48(0xb206U, "blake2b-48", 6U),
    BLAKE2B_56(0xb207U, "blake2b-56", 7U),
    BLAKE2B_64(0xb208U, "blake2b-64", 8U),
    BLAKE2B_72(0xb209U, "blake2b-72", 9U),
    BLAKE2B_80(0xb20aU, "blake2b-80", 10U),
    BLAKE2B_88(0xb20bU, "blake2b-88", 11U),
    BLAKE2B_96(0xb20cU, "blake2b-96", 12U),
    BLAKE2B_104(0xb20dU, "blake2b-104", 13U),
    BLAKE2B_112(0xb20eU, "blake2b-112", 14U),
    BLAKE2B_120(0xb20fU, "blake2b-120", 15U),
    BLAKE2B_128(0xb210U, "blake2b-128", 16U),
    BLAKE2B_136(0xb211U, "blake2b-136", 17U),
    BLAKE2B_144(0xb212U, "blake2b-144", 18U),
    BLAKE2B_152(0xb213U, "blake2b-152", 19U),
    BLAKE2B_160(0xb214U, "blake2b-160", 20U),
    BLAKE2B_168(0xb215U, "blake2b-168", 21U),
    BLAKE2B_176(0xb216U, "blake2b-176", 22U),
    BLAKE2B_184(0xb217U, "blake2b-184", 23U),
    BLAKE2B_192(0xb218U, "blake2b-192", 24U),
    BLAKE2B_200(0xb219U, "blake2b-200", 25U),
    BLAKE2B_208(0xb21aU, "blake2b-208", 26U),
    BLAKE2B_216(0xb21bU, "blake2b-216", 27U),
    BLAKE2B_224(0xb21cU, "blake2b-224", 28U),
    BLAKE2B_232(0xb21dU, "blake2b-232", 29U),
    BLAKE2B_240(0xb21eU, "blake2b-240", 30U),
    BLAKE2B_248(0xb21fU, "blake2b-248", 31U),
    BLAKE2B_256(0xb220U, "blake2b-256", 32U),
    BLAKE2B_264(0xb221U, "blake2b-264", 33U),
    BLAKE2B_272(0xb222U, "blake2b-272", 34U),
    BLAKE2B_280(0xb223U, "blake2b-280", 35U),
    BLAKE2B_288(0xb224U, "blake2b-288", 36U),
    BLAKE2B_296(0xb225U, "blake2b-296", 37U),
    BLAKE2B_304(0xb226U, "blake2b-304", 38U),
    BLAKE2B_312(0xb227U, "blake2b-312", 39U),
    BLAKE2B_320(0xb228U, "blake2b-320", 40U),
    BLAKE2B_328(0xb229U, "blake2b-328", 41U),
    BLAKE2B_336(0xb22aU, "blake2b-336", 42U),
    BLAKE2B_344(0xb22bU, "blake2b-344", 43U),
    BLAKE2B_352(0xb22cU, "blake2b-352", 44U),
    BLAKE2B_360(0xb22dU, "blake2b-360", 45U),
    BLAKE2B_368(0xb22eU, "blake2b-368", 46U),
    BLAKE2B_376(0xb22fU, "blake2b-376", 47U),
    BLAKE2B_384(0xb230U, "blake2b-384", 48U),
    BLAKE2B_392(0xb231U, "blake2b-392", 49U),
    BLAKE2B_400(0xb232U, "blake2b-400", 50U),
    BLAKE2B_408(0xb233U, "blake2b-408", 51U),
    BLAKE2B_416(0xb234U, "blake2b-416", 52U),
    BLAKE2B_424(0xb235U, "blake2b-424", 53U),
    BLAKE2B_432(0xb236U, "blake2b-432", 54U),
    BLAKE2B_440(0xb237U, "blake2b-440", 55U),
    BLAKE2B_448(0xb238U, "blake2b-448", 56U),
    BLAKE2B_456(0xb239U, "blake2b-456", 57U),
    BLAKE2B_464(0xb23aU, "blake2b-464", 58U),
    BLAKE2B_472(0xb23bU, "blake2b-472", 59U),
    BLAKE2B_480(0xb23cU, "blake2b-480", 60U),
    BLAKE2B_488(0xb23dU, "blake2b-488", 61U),
    BLAKE2B_496(0xb23eU, "blake2b-496", 62U),
    BLAKE2B_504(0xb23fU, "blake2b-504", 63U),
    BLAKE2B_512(0xb240U, "blake2b-512", 64U),

    BLAKE2S_8(0xb241U, "blake2s-8", 1U),
    BLAKE2S_16(0xb242U, "blake2s-16", 2U),
    BLAKE2S_24(0xb243U, "blake2s-24", 3U),
    BLAKE2S_32(0xb244U, "blake2s-32", 4U),
    BLAKE2S_40(0xb245U, "blake2s-40", 5U),
    BLAKE2S_48(0xb246U, "blake2s-48", 6U),
    BLAKE2S_56(0xb247U, "blake2s-56", 7U),
    BLAKE2S_64(0xb248U, "blake2s-64", 8U),
    BLAKE2S_72(0xb249U, "blake2s-72", 9U),
    BLAKE2S_80(0xb24aU, "blake2s-80", 10U),
    BLAKE2S_88(0xb24bU, "blake2s-88", 11U),
    BLAKE2S_96(0xb24cU, "blake2s-96", 12U),
    BLAKE2S_104(0xb24dU, "blake2s-104", 13U),
    BLAKE2S_112(0xb24eU, "blake2s-112", 14U),
    BLAKE2S_120(0xb24fU, "blake2s-120", 15U),
    BLAKE2S_128(0xb250U, "blake2s-128", 16U),
    BLAKE2S_136(0xb251U, "blake2s-136", 17U),
    BLAKE2S_144(0xb252U, "blake2s-144", 18U),
    BLAKE2S_152(0xb253U, "blake2s-152", 19U),
    BLAKE2S_160(0xb254U, "blake2s-160", 20U),
    BLAKE2S_168(0xb255U, "blake2s-168", 21U),
    BLAKE2S_176(0xb256U, "blake2s-176", 22U),
    BLAKE2S_184(0xb257U, "blake2s-184", 23U),
    BLAKE2S_192(0xb258U, "blake2s-192", 24U),
    BLAKE2S_200(0xb259U, "blake2s-200", 25U),
    BLAKE2S_208(0xb25aU, "blake2s-208", 26U),
    BLAKE2S_216(0xb25bU, "blake2s-216", 27U),
    BLAKE2S_224(0xb25cU, "blake2s-224", 28U),
    BLAKE2S_232(0xb25dU, "blake2s-232", 29U),
    BLAKE2S_240(0xb25eU, "blake2s-240", 30U),
    BLAKE2S_248(0xb25fU, "blake2s-248", 31U),
    BLAKE2S_256(0xb260U, "blake2s-256", 32U);

    companion object {
        /**
         * maps the name of a hash to the code
         */
        val names = hashMapOf<String, UInt>()
        /**
         * maps a hash code to it's name
         */
        val codes = hashMapOf<UInt, String>()
        /**
         * maps a hash code to it's default length
         */
        val defaultLengths = hashMapOf<UInt, UInt>()

        init {
            Type.values().forEach {
                names[it.named] = it.code
                codes[it.code] = it.named
                defaultLengths[it.code] = it.length
            }
        }

        fun hasCode(code: UInt): Boolean {
            return codes.containsKey(code)
        }
    }
}