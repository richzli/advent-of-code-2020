import java.io.File

fun main() {
    var mem = mutableMapOf<Long, Long>()
    var mask = ""

    File("14.in").forEachLine {
        if (it.startsWith("mask")) {
            mask = it.split(" = ")[1]
        } else {
            val vals = it.split("[", "] = ")
            val location = vals[1].toInt().toString(2).padStart(36, '0')
            val value = vals[2].toLong()

            for (loc in floating_values(mask, location)) {
                mem[loc.toLong(2)] = value
            }   
        }
    }

    println("${mem.map { it.value } .sum()}")
}

fun floating_values(mask: String, address: String): MutableList<String> {
    val addresses = mutableListOf<String>()

    if (mask.length >= 1) {
        val mask_r = mask.get(mask.lastIndex)
        val address_r = address.get(address.lastIndex)

        val recurse = floating_values(mask.substring(0, mask.lastIndex), address.substring(0, address.lastIndex))

        if (mask_r == '0') {
            addresses.addAll(recurse.map { "$it$address_r" })
        } else if (mask_r == '1') {
            addresses.addAll(recurse.map { "$it$mask_r" })
        } else if (mask_r == 'X') {
            addresses.addAll(recurse.map { "${it}0" })
            addresses.addAll(recurse.map { "${it}1" })
        }
    } else {
        addresses.add("")
    }

    return addresses
}